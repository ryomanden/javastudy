package enshu6_2022;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Paint extends Frame implements MouseListener, MouseMotionListener {
	int x, y;
	//Circle c;
	
	public static void main(String[] args) {
		Paint f = new Paint();
		f.setSize(640,480);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
	}
	Paint(){
		c = null;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paint(Graphics g) {
		if(c != null) c.paint(g);
	}
	@Override public void mousePressed(MouseEvent e) {
		if(c == null) {
			//c = new Circle();
			c = new Box();
			c.moveto(e.getX(), e.getY());
		}
		x = e.getX();
		y = e.getY();
	}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {
		if(c != null) {
			c.move(e.getX() - x, e.getY() -y);
			repaint();
		}
		x = e.getX();
		y = e.getY();
	}
	@Override public void mouseMoved(MouseEvent e) {}
	
}
