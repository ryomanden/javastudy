package report2;

import java.awt.*;

public class Figure extends Coord {

    // 変数wの宣言 //
    Color color;
    boolean fillStatus = false, isPerfect = false;
    int w, h;

    // コンストラクタ //
    Figure() {
        w = h = 0;
    }

    public void paint(Graphics g) {
    }

    public void setWH(int w, int h) {
        this.w = w;
        this.h = h;
    }

    // 正方形・正円を設定するセッター //
    public void setPerfect(boolean isPerfect) {
        this.isPerfect = isPerfect;
    }
}
