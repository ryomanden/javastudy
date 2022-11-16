package report;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Figure {
	int size;
	Color color;
	
	Rectangle(int size) {
		this.size = size;
		this.color = new Color(0,0,0);
	}	
	Rectangle(int size,Color color) {
		this.size = size;
		this.color = color;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawRect(x - size/2, y - size/2, size, size);
		
	}
}
