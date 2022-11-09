package oekaki;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Figure {
	int size;
	Color color;
	
	Circle(int size) {
		this.size = size;
		this.color = new Color(0,0,0);
	}	
	Circle(int size,Color color) {
		this.size = size;
		this.color = color;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawOval(x - size/2, y - size/2, size, size);
		
	}
}
