package enshu10_2022;

import java.awt.Graphics;

public class Dot extends Figure {
	int size;
	Dot() {
		size = 10;
	}
	@Override public void paint(Graphics g) {
		g.drawOval(x - size/2, y - size/2, size, size);
	}
}
