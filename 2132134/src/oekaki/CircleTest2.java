package oekaki;

import java.util.ArrayList;

class CircleTest2 {
	public static void main(String[] args) {
		ArrayList<Coord> objList = new ArrayList<Coord>();
		Coord c = new Circle();
		objList.add(c);
		c.move(100, 100);
		System.out.println("x = " +c.x+ " y = " +c.y);
		// c.setSize(20);
	}
}
