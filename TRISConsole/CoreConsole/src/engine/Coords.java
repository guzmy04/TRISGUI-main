package engine;

import java.util.Objects;

public class Coords {
    int x;
    int y;

    public Coords(int pos) {
        pos--;
        this.x = pos / 3;
        this.y = pos % 3;

    }

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coords)) return false;
        Coords coords = (Coords) o;
        return x == coords.x && y == coords.y;
    }

    public static Coords get(int x, int y) {
        return new Coords(x, y);
    }

    @Override
    public String toString() {
        return "engine.Coords{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
