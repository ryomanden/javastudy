package report2;

import java.awt.Color;
import java.awt.Graphics;

public class Dot extends Figure {
	int size;
	Dot(Color color) {
		this.color = color;
		size = 10;
	}
	Dot(Color color,Boolean fs) {
		this.color = color;
		this.fillStatus = fs;
		size = 10;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		if(fillStatus == true) {
			g.fillOval(x - size/2, y - size/2, size, size);
		} else {
			g.drawOval(x - size/2, y - size/2, size, size);
		}
	}
}
