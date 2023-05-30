package engine;

public class Tools {
    public static int[] Coords2Int(int[][] grid, Coords[] coords) {
        int[] r=new int[coords.length];
        for (int i = 0; i < coords.length; i++) {
            r[i]=getGridValue(grid, coords[i]);
        }
        return  r;
    }

    enum Direction {
        H1, H2, H3, V1, V2, V3, D1, D2, NONE
    } //ps: così è troppo vincolato a questo gioco, meglio solo H, V, D1, D2

    public static Coords[] gridToCoords(int[][] grid, Direction direction) {
        switch (direction) {
            case H1 -> {
                return new Coords[]{Coords.get(0, 0), Coords.get(0, 1), Coords.get(0, 2)};
            }
            case H2 -> {
                return new Coords[]{Coords.get(1, 0), Coords.get(1, 1), Coords.get(1, 2)};
            }
            case H3 -> {
                return new Coords[]{Coords.get(2, 0), Coords.get(2, 1), Coords.get(2, 2)};
            }
            case V1 -> {
                return new Coords[]{Coords.get(0, 0), Coords.get(1, 0), Coords.get(2, 0)};
            }
            case V2 -> {
                return new Coords[]{Coords.get(0, 1), Coords.get(1, 1), Coords.get(2, 1)};
            }
            case V3 -> {
                return new Coords[]{Coords.get(0, 2), Coords.get(1, 2), Coords.get(2, 2)};
            }
            case D1 -> {
                return new Coords[]{Coords.get(0, 0), Coords.get(1, 1), Coords.get(2, 2)};
            }
            case D2 -> {
                return new Coords[]{Coords.get(0, 2), Coords.get(1, 1), Coords.get(2, 0)};
            }
            case NONE -> {
                return new Coords[]{};
            }
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public static boolean isContained(int[][] grid, Coords[] row, Coords move){
        for (Coords coords : row) {
            if (coords.equals(move)) {
                return true;
            }
        }
        return false;
    }

    public static int[] gridToRow(int[][] grid, Direction direction) {
        Coords[] coords = gridToCoords(grid, direction);
        int[] row = new int[coords.length];
        for (int i = 0; i < coords.length; i++) {
            row[i] = getGridValue(grid, coords[i]);
        }
        return row;

    }
    public static void printToConsole(int[][] grid, String preText) {
        if (preText != null)
            System.out.println(preText);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(gridToText(grid[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    public static String gridToText(int gridValue) {
        switch (gridValue) {
            case Game.PLAYER_1:
                return "X";
            case Game.PLAYER_2:
                return "O";
            default:
                return "-";
        }
    }

    public static void printToConsole(int[][] game) {
        printToConsole(game, null);
    }
    public static int getGridValue(int[][] grid, Coords move) {
        return grid[move.x][move.y];
    }

    public static void setGridValue(int[][] grid, Coords move, int player) {
        grid[move.x][move.y] = player;
    }
}
