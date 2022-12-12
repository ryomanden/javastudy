package report2;

import java.awt.Color;
import java.awt.Graphics;

public class Figure extends Coord {
	Color color;
	boolean fillStatus = false,isPerfect = false;
	int w, h;
	Figure(){
		w = h = 0;
	}
	public void paint(Graphics g) {}
	public void setWH(int w, int h) {
		this.w = w;
		this.h = h;
	}
	public void setPerfect(boolean isPerfect) {
		this.isPerfect = isPerfect;
	}
}
