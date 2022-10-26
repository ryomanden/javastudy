package enshu6_2022;

public class Coord {
	int x, y;
	Coord(){
		x = y = 10;
	}
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	public void moveto(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
