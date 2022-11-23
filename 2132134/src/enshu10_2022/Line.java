package enshu10_2022;

import java.awt.Graphics;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．
	Line(){}
	@Override public void paint(Graphics g) {
		g.drawLine(x, y, x + w, y + h); //開始位置から終了位置まで線を引く
	}
}
