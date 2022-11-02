package oekaki;

public class Circle extends Coord {
	int color, size;
	Circle() {
		color = 0;
		size = 10;
		
	}
	@Override public void move(int dx, int dy) {
		x -= dx;
		y -= dy;
		System.out.println("move2");
	}
	public void setSize(int s) {
		size = s;
	}
}
