package report;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Figure {
	Color color;
	Coord startPoint, endPoint;
	
	Line(Coord startPoint,Coord endPoint) {
		this.color = new Color(0,0,0);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
	}
}
