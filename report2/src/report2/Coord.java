package report2;

import java.io.Serializable;

public class Coord implements Serializable {
    int x, y;

    Coord() {x = y = 0;}

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void moveto(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
