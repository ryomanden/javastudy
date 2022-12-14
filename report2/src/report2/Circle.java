package report2;

import java.awt.*;

public class Circle extends Figure {

    // 変数の宣言 //
    int width, height;

    // コンストラクタ //
    Circle(Color color, Boolean fs) {
        this.color = color;
        this.fillStatus = fs;
    }

    // 描画を行うメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);

        // 楕円・正円を切り替える //
        if (isPerfect) {
            width = (int) Math.sqrt(w * w + h * h);
            height = (int) Math.sqrt(w * w + h * h);
        } else {
            width = (int) (Math.sqrt(2.0) * Math.abs(w));
            height = (int) (Math.sqrt(2.0) * Math.abs(h));
        }

        // 塗りつぶしを切り替える //
        if (fillStatus) {
            g.fillOval(x - width, y - height, width * 2, height * 2);
        } else {
            g.drawOval(x - width, y - height, width * 2, height * 2);
        }
    }
}
