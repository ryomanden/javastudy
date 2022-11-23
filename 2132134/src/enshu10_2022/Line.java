package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．
	Color color;
	Coord startPoint, endPoint; //線の開始・終了位置を入れるCoord型のインスタンスを作成．
	
	Line(Coord startPoint,Coord endPoint) { //第一引数で線の開始位置，第二引数で線の終了位置を受け取る．
		this.color = new Color(0,0,0); //色を黒に設定．
		this.startPoint = startPoint; //受け取った開始位置を８行目で作成したインスタンスに代入．
		this.endPoint = endPoint; //受け取った終了位置を８行目で作成したインスタンスに代入．
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y); //開始位置から終了位置まで線を引く
	}
}
