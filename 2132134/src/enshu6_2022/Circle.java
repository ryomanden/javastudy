package enshu6_2022;

import java.awt.Graphics;

public class Circle extends Coord {
	int color, size;
	Circle() {
		color = 0;
		size = 10;
		
	}
	@Override public void paint(Graphics g) {
		g.drawOval(x - size/2, y - size/2, size, size);
	}
	@Override public void move(int dx, int dy) {
		x -= dx;
		y -= dy;
	}
}
