package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Rect extends enshu10_2022.Figure {
	Rect(Color color){this.color = color;}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, w, h);
	}
}
