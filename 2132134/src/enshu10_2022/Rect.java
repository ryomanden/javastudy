package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Rect extends enshu10_2022.Figure {
	Rect(Color color){this.color = color;}
	Rect(Color color,Boolean fs){
		this.color = color;
		this.fillStatus = fs;
	}	
	@Override public void paint(Graphics g) {
		g.setColor(color);
		if(fillStatus == true) {
			g.fillRect(x, y, w, h);			
		} else {
			g.drawRect(x, y, w, h);
		}
	}
}
