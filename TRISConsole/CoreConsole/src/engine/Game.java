package engine;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public static final int PLAYER_1 = 1, PLAYER_2 = -1, NO_MOVE = 0;
    private int[][] grid = new int[3][3];
    private int depth = 3;

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    private int player;

    public Game(int player) {
        resetGame();
        this.player = player;
    }

    public void move(Coords move) {
        Tools.setGridValue(grid, move, player);
        player = -player;
    }

    void resetGame() {
        grid = new int[3][3];
    }

    public Tools.Direction getWinnerDirection() {
        for (Tools.Direction dir : Tools.Direction.values()) {
            int[] row = Tools.gridToRow(grid, dir);
            int sum = 0;
            for (int i = 0; i < row.length; i++) {
                sum += row[i];
            }
            if (sum == PLAYER_1 * 3 || sum == PLAYER_2 * 3)
                return dir;
        }
        return Tools.Direction.NONE;
    }

    public boolean hasWinner() {
        Tools.Direction dir = getWinnerDirection();
        return dir != Tools.Direction.NONE;
    }

    public boolean hasDraw() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == NO_MOVE)
                    return false;
            }
        }
        return true;
    }


    public static Coords suggestMove(int[][] grid, int player, int depth) {
        int[][] out = new int[3][3];
        int max = 0;
        Coords maxC = new Coords(0, 0);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                out[i][j] = evaluateMove(grid, Coords.get(i, j), player, depth);
                if (out[i][j] > max) {
                    max = out[i][j];
                    maxC = Coords.get(i, j);
                }
            }
        }
        System.out.println(Arrays.deepToString(out));
        return maxC;
    }


    public static int evaluateMove(int[][] grid, Coords move, int player, int depth) {
        /* recursive analysis profonda depth,
        pesi positivi (possibilità di vittoria) e
        negativi (possibilità di vittoria dell'altro giocatore)
        */
        int sum = 0;


        if (Tools.getGridValue(grid, move) == 0) {
            ArrayList<Coords[]> validDir = new ArrayList<>();

            for (Tools.Direction dir : Tools.Direction.values()) {
                if (Tools.isContained(grid, Tools.gridToCoords(grid, dir), move)) {
                    validDir.add(Tools.gridToCoords(grid, dir));
                }
            }

            for (int i = 0; i < validDir.size(); i++) {

                int weight = getWeightRow(Tools.Coords2Int(grid, validDir.get(i)), player);
                //if (weight > max)
                    sum += weight;
            }
        }
        else return -10000;

        if (depth == 0)
            return sum;
        else {
            int[][] out = new int[3][3];
            int m = -10000;
            depth--;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    out[i][j] = evaluateMove(grid, Coords.get(i, j), player, depth);
                    if (out[i][j] > m) {
                        m = out[i][j];
                    }
                }
            }
            sum += m;
            return sum;
        }
    }

    public static int getWeightRow(int[] row, int player) {
        int nPlayer1 = 0;
        int nPlayer2 = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i] == player) {
                nPlayer1++;
            } else if (row[i] == -player) {
                nPlayer2++;
            }
        }
        if (nPlayer1 == 0 && nPlayer2 == 0)
            return 1;
        if (nPlayer1 == 1 && nPlayer2 == 0)
            return 10;
        if (nPlayer1 == 2 && nPlayer2 == 0)
            return 1000;
        if (nPlayer1 == 0 && nPlayer2 == 1)
            return 5;
        if (nPlayer1 == 0 && nPlayer2 == 2)
            return 50;
        if (nPlayer1 == 1 && nPlayer2 == 1)
            return 0;
        System.err.println("Caso non contemplato");
        return -12000;
    }

    /* possibili pesi
    x = coord da valutare
    1 = giocatore per cui si valuta
    x   0   0   =1 and !occupata  1       potenziale tris
    1   x   0   =2                10      potenziale tris
    1   1   x   =3                1000    vittoria!
    -1  x   0   =0                5       blocco potenziale tris avversario
    -1  -1  x   = -1              50      blocco tris avversario
    1   -1  x   =1 and occupata   0       nessun vantaggio per nessuno

     */
}
