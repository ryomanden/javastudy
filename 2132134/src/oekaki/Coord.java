package oekaki;

public class Coord {
	int x, y;
	Coord(){
		x = y = 0;
	}
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
		System.out.println("move1");
	}
}
