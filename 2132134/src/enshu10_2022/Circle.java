package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Figure {
	
	Circle(Color color) {this.color = color;}	
	@Override public void paint(Graphics g) {
		int r = (int)Math.sqrt((double)(w * w + h * h));
		g.setColor(color);
		g.drawOval(x - r, y - r, r * 2, r * 2);
	}
}
