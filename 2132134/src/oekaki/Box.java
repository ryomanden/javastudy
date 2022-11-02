package oekaki;

import java.awt.Graphics;

public class Box extends Coord {
		int color, size;
		Box() {
			color = 0;
			size = 50;
			
		}
		@Override public void paint(Graphics g) {
			g.drawRect(x - size/2, y - size/2, size, size);
		}
}
