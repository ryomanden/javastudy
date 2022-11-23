package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Figure {
	int size;
	Color color;
	
	Rectangle(int size) { //第二引数が指定されていない場合，黒で四角形を生成する．
		this.size = size;
		this.color = new Color(0,0,0);
	}	
	Rectangle(int size,Color color) { //第二引数が指定されている場合，指定された色で四角形を生成する．
		this.size = size;
		this.color = color;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawRect(x - size/2, y - size/2, size, size);
		
	}
}
