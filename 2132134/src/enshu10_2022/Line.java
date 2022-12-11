package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．
	Coord sPoint,ePoint;
	Line(Coord sPoint, Color color){
		this.color = color;
		this.sPoint = sPoint;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawLine(sPoint.x, sPoint.y, x, y); //開始位置から終了位置まで線を引く
	}
}
