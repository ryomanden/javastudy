package report2;

import java.awt.*;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．

    // 変数の宣言 //
    Coord sPoint;

    // コンストラクタ //
    Line(Coord sPoint, Color color) {
        this.color = color;
        this.sPoint = sPoint;
    }

    // 描画するメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);
        g.drawLine(sPoint.x, sPoint.y, x, y); //開始位置から終了位置まで線を引く
    }
}
