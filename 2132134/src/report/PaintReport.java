package report;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener {
	int x, y, objSize = 10;
	boolean sizeChange = false;
	
	ArrayList<Figure> objList;
	Figure obj;
	
	static Button clearButton = new Button("Clear");
	static Button sizeincButton = new Button("+");
	static Button sizedecButton = new Button("-");
	static Label objcountLabel = new Label();
	static Label sizeLabel = new Label();
	
	static int drawCnt = 0;
	public static void main(String[] args) {
		if(args.length > 0) drawCnt = Integer.parseInt(args[0]);
		PaintReport f = new PaintReport();
		f.setSize(640,480);
		f.setLayout(null);
		f.setTitle("Report Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		clearButton.setBounds(10, 40, 80, 30);
		objcountLabel.setBounds(10, 70, 120, 30);
		sizeincButton.setBounds(10, 100, 50, 50);
		sizeLabel.setBounds(10, 150, 120, 30);
		sizedecButton.setBounds(10, 180, 50, 50);
		f.add(clearButton);
		f.add(objcountLabel);
		f.add(sizeincButton);
		f.add(sizeLabel);
		f.add(sizedecButton);
		f.btnEvent();
		f.labelUpdate();
		f.setVisible(true);
	}
	void btnEvent() {
		clearButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear();
				labelUpdate();
				repaint();
			}
		});
		sizeincButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objSize += 10;
				labelUpdate();
				repaint();
			}
		});
		sizedecButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(objSize > 0) {
					objSize -= 10;					
				}
				labelUpdate();
				repaint();
			}
		});
	}
	void labelUpdate() {
		objcountLabel.setText("ObjectCount is " +objList.size());
		sizeLabel.setText("Size " +objSize+ "px");
	}
	
	PaintReport(){
		objList = new ArrayList<Figure>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paint(Graphics g) {
		Figure f;
		for(int i = 0; i < objList.size(); i++) {
			f = objList.get(i);
			f.paint(g); 
		}
		if(obj != null) obj.paint(g);
	}
	@Override public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(sizeChange == false) {// 10px 50px kougoniiiii
			obj = new Circle(objSize);
			sizeChange = !sizeChange;
		} else {
			obj = new Rectangle(objSize,new Color(255,0,0)); 
			sizeChange = !sizeChange;
		}
		obj.moveto(x, y);
		repaint();
	}
	@Override public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		obj.moveto(x,y);
		objList.add(obj);
		obj = null;
		if(objList.size() > drawCnt)objList.remove(0); // 引数で指定された数を超えた分remove
		
		new Line(new Coord(320,240),objList.get(objList.size()-1));
		
		System.out.println(objList.size());
		labelUpdate();
		System.out.println();
		repaint();
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(obj != null)obj.moveto(x,y);
		repaint();
	}
	@Override public void mouseMoved(MouseEvent e) {}
	
}
