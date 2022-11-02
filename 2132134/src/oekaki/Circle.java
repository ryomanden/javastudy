package oekaki;

import java.awt.Graphics;

public class Circle extends Figure {
	int size;
	Circle() {
		size = 10;
	}
	@Override public void paint(Graphics g) {
		g.drawOval(x - size/2, y - size/2, size, size);
	}
}
