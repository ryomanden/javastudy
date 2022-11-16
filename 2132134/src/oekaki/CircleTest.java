package oekaki;

import report.Circle;

public class CircleTest {
	public static void main(String[] args) {
		Circle c = new Circle();
		c.move(100, 100);
		c.print();		
		c.move(100, 100);
		c.print();		
		c.moveto(100, 100);
		c.print();
		
	}
}
