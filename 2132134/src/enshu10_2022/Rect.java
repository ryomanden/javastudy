package enshu10_2022;

import java.awt.Color;
import java.awt.Graphics;

public class Rect extends enshu10_2022.Figure {
	int X, Y, W, H, Width, Height;
	Rect(Color color){this.color = color;}
	Rect(Color color,Boolean fs){
		this.color = color;
		this.fillStatus = fs;
	}	
	@Override public void paint(Graphics g) {
		g.setColor(color);
		if(isPerfect){
			if(Math.abs(w)>Math.abs(h)) {
				W = w;
				H = w;
			} else {
				W = h;
				H = h;
			}
		} else {
			W = w;
			H = h;
		}
		
		if(W < 0) {
			X = x + W;
			Width = -W;
		} else {
			X = x;
			Width = W;
		}
		if(H < 0) {
			Y = y + H;
			Height = -H;
		} else {
			Y = y;
			Height = H;
		}
		if(fillStatus) {
			g.fillRect(X, Y, Width, Height);
		} else {
			g.drawRect(X, Y, Width, Height);
		}
	}
}
