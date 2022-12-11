package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Figure {
	
	int width, height;
	
	Circle(Color color) {this.color = color;}
	Circle(Color color,Boolean fs) {
		this.color = color;
		this.fillStatus = fs;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		if(isPerfect) {
			width = (int)Math.sqrt((double)(w * w + h * h));
			height = (int)Math.sqrt((double)(w * w + h * h));
		} else {
			width = (int)(Math.sqrt(2.0) * Math.abs(w));
			height = (int)(Math.sqrt(2.0) * Math.abs(h));
		}
		if(fillStatus) {
			g.fillOval(x - width, y - height, width * 2, height * 2);
		} else {
			g.drawOval(x - width, y - height, width * 2, height * 2);
		}
	}
}
