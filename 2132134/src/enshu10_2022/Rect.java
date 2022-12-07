package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Rect extends enshu10_2022.Figure {
	int X, Y, W, H;
	Rect(Color color){this.color = color;}
	Rect(Color color,Boolean fs){
		this.color = color;
		this.fillStatus = fs;
	}	
	@Override public void paint(Graphics g) {
		g.setColor(color);
		if(w < 0) {
			X = x + w;
			W = -w;
		} else {
			X = x;
			W = w;
		}
		if(h < 0) {
			Y = y + h;
			H = -h;
		} else {
			Y = y;
			H = h;
		}
		if(fillStatus == true) {
			g.fillRect(X, Y, W, H);
		} else {
			g.drawRect(X, Y, W, H);
		}
	}
}
