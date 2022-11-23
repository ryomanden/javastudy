package enshu10_2022;

public class Coord {
	int x, y;
	Coord(){
		x = y = 0;
	}
	Coord(int x , int y){ //引数で座標x,yを受け取り，ローカル変数に代入する
		this.x = x;
		this.y = y;
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
