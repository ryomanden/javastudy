package enshu10_2022;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;

public class AppFrame extends Frame {
	@Override public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawString("Hello Java!", 70, 120);
		g.drawRect(50, 50, 100, 100);
	}
}
