package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．
	Line(Color color){this.color = color;}
	@Override public void paint(Graphics g) {
		g.drawLine(x, y, x + w, y + h); //開始位置から終了位置まで線を引く
	}
}
