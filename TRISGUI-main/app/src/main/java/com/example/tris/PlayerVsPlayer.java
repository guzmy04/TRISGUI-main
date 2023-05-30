package com.example.tris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerVsPlayer extends AppCompatActivity implements View.OnClickListener{

    //grid
    Button[][] buttons = new Button[3][3];

    TextView textViewPlayer = null;
    TextView textViewBot = null;
    Game game=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_vs_player);
        game=new Game(Game.PLAYER_1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textViewPlayer = findViewById(R.id.textView);
        textViewBot = findViewById(R.id.textView2);
        buttons[0][0]=findViewById(R.id.button_00);
        buttons[0][1]=findViewById(R.id.button_01);
        buttons[0][2]=findViewById(R.id.button_02);
        buttons[1][0]=findViewById(R.id.button_10);
        buttons[1][1]=findViewById(R.id.button_11);
        buttons[1][2]=findViewById(R.id.button_12);
        buttons[2][0]=findViewById(R.id.button_20);
        buttons[2][1]=findViewById(R.id.button_21);
        buttons[2][2]=findViewById(R.id.button_22);

        //Integrazione con Coords e Tools
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Coords move=buttonToCoords( (Button)v);
                        game.move(move);
                        refreshBoard();
                        checkWinners();
                    }
                });
            }
        }



        Button buttonReset = findViewById(R.id.resetButton);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        Button buttonBack = findViewById(R.id.toHome);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerVsPlayer.this, MainActivity.class));
            }
        });
    }

    private void checkWinners() {
        if (game.hasDraw())
            draw();
        if (game.hasWinner()){
          switch (game.getPlayer()) {
              case 1:
                  player2Win();
                  break;
              case -1:
                  player1Win();
                  break;
          }
        }
    }

    private Coords buttonToCoords(Button b) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b==buttons[i][j])
                    return Coords.get(i, j);
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
       /* if (!((Button)view).getText().toString().equals("")){
            return;
        }*/
        if (view==findViewById(R.id.resetButton))
            resetGame();

        refreshBoard();


    }


    public void player1Win(){
        Toast.makeText(this, "Player1 win!", Toast.LENGTH_SHORT).show();
        refreshBoard();
    }

    public void player2Win(){
        Toast.makeText(this, "Player2 win!", Toast.LENGTH_SHORT).show();
        refreshBoard();
    }

    public void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        refreshBoard();
    }



    //metodo Reset
    public void refreshBoard(){
          for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value=game.getGrid()[i][j];
                String buttonText=Tools.gridToText(value)  ;
                buttons[i][j].setText(buttonText.replace('-', ' '));
                buttons[i][j].setEnabled(value==Game.NO_MOVE);
            }
        }
    }

    public void resetGame(View v)
    {
        game.resetGame();
        refreshBoard();
    }
    //
    public void resetGame(){
        game.resetGame();
    }
}