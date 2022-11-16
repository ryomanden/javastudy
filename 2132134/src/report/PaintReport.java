package report;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener {
	int x, y, objSize = 30;
	boolean sizeChange = false;
	
	ArrayList<Figure> objList;
	ArrayList<Figure> lineList;
	Figure obj;
	
	static Button clearButton = new Button("Clear");
	static Button sizeincButton = new Button("+");
	static Button sizedecButton = new Button("-");
	static Button applyButton = new Button("Apply");
	static Label objcountLabel = new Label();
	static Label sizeLabel = new Label();
	static TextField objcountField= new TextField();
	
	static int drawCnt = 30;
	public static void main(String[] args) {
		if(args.length > 0) drawCnt = Integer.parseInt(args[0]);
		PaintReport f = new PaintReport();
		f.setSize(1280,720);
		f.setLayout(null);
		f.setTitle("Report Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		clearButton.setBounds(10, 40, 80, 30);
		objcountLabel.setBounds(10, 70, 120, 30);
		sizeincButton.setBounds(10, 120, 50, 20);
		sizeLabel.setBounds(10, 145, 120, 30);
		sizedecButton.setBounds(10, 180, 50, 20);
		objcountField.setBounds(10, 220, 50, 20);
		applyButton.setBounds(10, 250, 70, 20);
		f.add(clearButton);
		f.add(objcountLabel);
		f.add(sizeincButton);
		f.add(sizeLabel);
		f.add(sizedecButton);
		f.add(objcountField);
		f.add(applyButton);
		f.btnEvent();
		f.labelUpdate();
		f.setVisible(true);
	}
	void btnEvent() {
		clearButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear();
				lineList.clear();
				labelUpdate();
				repaint();
			}
		});
		sizeincButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objSize += 10;
				labelUpdate();
			}
		});
		sizedecButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(objSize > 0) {
					objSize -= 10;					
				}
				labelUpdate();
			}
		});
		applyButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				fieldUpdate();
			}
		});
	}
	void labelUpdate() {
		objcountLabel.setText("ObjectCount is " +objList.size());
		sizeLabel.setText("Size " +objSize+ "px");
	}
	void fieldUpdate() {
		String drawMax = objcountField.getText();
		drawCnt = Integer.parseInt(drawMax);
	}
	
	PaintReport(){
		objList = new ArrayList<Figure>();
		lineList = new ArrayList<Figure>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paint(Graphics g) {
		Figure f;
		for(int i = 0; i < objList.size(); i++) {
			f = objList.get(i);
			f.paint(g);
		}
		for(int i = 0; i < lineList.size(); i++) {

			f = lineList.get(i);
			f.paint(g);
		}
		if(obj != null) obj.paint(g);
	}
	@Override public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		obj = new Circle(objSize,new Color(255,0,0));
		sizeChange = !sizeChange;
		obj.moveto(x, y);
		repaint();
	}
	@Override public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		obj.moveto(x,y);
		objList.add(obj);
		obj = null;
		if(objList.size() > drawCnt) {
			objList.remove(0);
			lineList.remove(0);// 引数で指定された数を超えた分remove
		}
		
		if(objList.size()-1 > 0) {
			obj = new Line(objList.get(objList.size()-2),objList.get(objList.size()-1));
			lineList.add(obj);
			obj = null;
		}
		
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
