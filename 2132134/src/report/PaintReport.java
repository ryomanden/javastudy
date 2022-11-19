package report;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener {
	int x, y, objSize = 30;
	boolean sizeChange = false;
	boolean sizeBig = false;

	ArrayList<Figure> objList;
	ArrayList<Figure> lineList;
	Figure obj;
	

	static Button clearButton = new Button("Clear");
	static Button sizeincButton = new Button("+");
	static Button sizedecButton = new Button("-");
	static Button applyButton = new Button("Apply");
	static Label objcountLabel = new Label();
	static Label sizeLabel = new Label();
	static Label countLabel = new Label("MaxObjectCount");
	static Label redLabel = new Label("R :");
	static Label greenLabel = new Label("G :");
	static Label blueLabel = new Label("B :");
	static TextField objcountField = new TextField();
	static TextField redField = new TextField();
	static TextField greenField = new TextField();
	static TextField blueField = new TextField();
	static CheckboxGroup cbg = new CheckboxGroup();
	static Checkbox staticsizeCheckbox = new Checkbox("Static size",cbg, true);
	static Checkbox changesizeCheckbox = new Checkbox("Change size",cbg, false);

	static int drawCnt = 30;
	static Color color = new Color(0,0,0);
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
		clearButton.setBounds(20, 40, 80, 30);
		objcountLabel.setBounds(20, 70, 120, 30);

		sizeincButton.setBounds(20, 120, 50, 20);
		sizeLabel.setBounds(20, 145, 120, 30);
		sizedecButton.setBounds(20, 180, 50, 20);

		staticsizeCheckbox.setBounds(20, 210, 100, 20);
		changesizeCheckbox.setBounds(20, 240, 100, 20);

		countLabel.setBounds(20, 280, 100, 20);
		objcountField.setBounds(20, 310, 50, 20);
		redLabel.setBounds(20, 340, 20, 20);
		redField.setBounds(40, 340, 30, 20);
		greenLabel.setBounds(80, 340, 20, 20);
		greenField.setBounds(100, 340, 30, 20);
		blueLabel.setBounds(140, 340, 20, 20);
		blueField.setBounds(160, 340, 30, 20);
		applyButton.setBounds(20, 370, 70, 20);

		f.add(clearButton);
		f.add(objcountLabel);
		f.add(sizeincButton);
		f.add(sizeLabel);
		f.add(sizedecButton);
		f.add(countLabel);
		f.add(objcountField);
		f.add(applyButton);
		f.add(staticsizeCheckbox);
		f.add(changesizeCheckbox);
		f.add(redLabel);
		f.add(redField);
		f.add(greenLabel);
		f.add(greenField);
		f.add(blueLabel);
		f.add(blueField);
		f.btnEvent();
		f.labelUpdate();

		objcountField.setText(Integer.valueOf(drawCnt).toString());
		redField.setText("0");
		greenField.setText("0");
		blueField.setText("0");

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
				objList.clear();
				lineList.clear();
				fieldUpdate();
				labelUpdate();
				repaint();
			}
		});

		staticsizeCheckbox.addItemListener(new ItemListener() {  
			public void itemStateChanged(ItemEvent e) {               
				sizeChange = false;
			}  
		});  
		changesizeCheckbox.addItemListener(new ItemListener() {  
			public void itemStateChanged(ItemEvent e) {               
				sizeChange = true;
			}  
		});
	}
	void labelUpdate() {
		objcountLabel.setText("ObjectCount is " +objList.size());
		sizeLabel.setText("Size " +objSize+ "px");
	}
	void fieldUpdate() {
		String str = objcountField.getText();
		drawCnt = Integer.parseInt(str);
		
		color = new Color(Integer.parseInt(redField.getText()),Integer.parseInt(greenField.getText()),Integer.parseInt(blueField.getText()));
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

		if(sizeChange == false) {
			obj = new Circle(objSize,color);
		} else {
			if (sizeBig == false) {
				obj = new Circle(10,color);
				sizeBig = !sizeBig;
			} else {
				obj = new Circle(50,color);
				sizeBig = !sizeBig;
			}
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
		if(objList.size() > drawCnt) {
			objList.remove(0);// 引数で指定された数を超えた分remove
			lineList.remove(0);
		}

		if(objList.size()-1 > 0) {
			obj = new Line(objList.get(objList.size()-2),objList.get(objList.size()-1));// 前に追加された図と今追加された図の座標の間に線を引く
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
