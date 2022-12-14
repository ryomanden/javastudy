package report2;

import java.io.Serializable;

public class Coord implements Serializable {

    // 変数の宣言 //
    int x, y;
    Coord() {x = y = 0;}

    // コンストラクタ //
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
