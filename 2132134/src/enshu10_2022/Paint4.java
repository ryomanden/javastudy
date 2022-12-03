package enshu10_2022;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Paint4 extends Frame implements MouseListener, MouseMotionListener,ActionListener, WindowFocusListener ,ComponentListener{
	int x, y;
	
	ArrayList<enshu10_2022.Figure> objList;

	int mode = 0;
	enshu10_2022.Figure obj;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int width = screenSize.width;
	static int height = screenSize.height;
	
	static toolbar toolbar = null;
	
	public static void main(String[] args) {
		
		Paint4 f = new Paint4();
		f.setBounds(width / 4, height / 4, 640, 480);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
		
		toolbar= new toolbar(f);
		
		
		//if(args .length == 1) f.load(args[0]);
		toolbar.setBounds(width / 4 + 645, height / 4, 200, 480);
	}
	
	Paint4(){
		objList = new ArrayList<enshu10_2022.Figure>();
		addMouseListener(this);
		addMouseMotionListener(this);
		addWindowFocusListener(this);//ウィンドウがアクティブか見る
		addComponentListener(this);//ウィンドウのサイズ変更を見る
		setLayout(null);
		
	}
	
	public void clear() {
		objList.clear();
		repaint();
	}
	
	public void save(String fname) {
		try {
			FileOutputStream fos = new FileOutputStream(fname);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objList);
			oos.close();
			fos.close();
		} catch(IOException e) {
		}
		System.out.println("saved");//debug
		repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void load(String fname) {
		try {
			FileInputStream fis = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			objList = (ArrayList<enshu10_2022.Figure>)ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException e) {
		} catch (ClassNotFoundException e) {
		}
		System.out.println("loaded");//debug
		repaint();
	}

	
	@Override public void paint(Graphics g) {
		enshu10_2022.Figure f;
		for(int i = 0; i < objList.size(); i++) {
			f = objList.get(i);
			f.paint(g);
		}
		if(mode >= 1) obj.paint(g);
	}
	
	@Override public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		
		switch (toolbar.getObjMode()) {
			case "dott":
				mode = 1;
				obj = new Dot(toolbar.getColor());
				break;
				
			case "circle":
				mode = 2;
				obj = new enshu10_2022.Circle(toolbar.getColor());
				break;
				
			case "rect":
				mode = 2;
				obj = new Rect(toolbar.getColor());
				break;
				
			case "line":
				mode = 2;
				obj = new Line(toolbar.getColor());
				break;
				
			default:
				break;
		}
		if(obj != null) {
			obj.moveto(x, y);
			repaint();
		}
	}
	@Override public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		if(mode == 1) obj.moveto(x, y);
		else if(mode == 2) obj.setWH(x -obj.x, y -obj.y);
		if(mode >= 1) {
			objList.add(obj);
			obj = null;
		}
		mode = 0;
		repaint();
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		if(mode == 1) {
			obj.moveto(x, y);
		} else if(mode == 2) {
			obj.setWH(x - obj.x, y - obj.y);
		}
		repaint();

	}
	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void windowGainedFocus(WindowEvent e) {}
	@Override public void windowLostFocus(WindowEvent e) {}
	@Override public void componentResized(ComponentEvent e) {
		if(toolbar != null) {toolbar.setLocation(getX()+ getWidth() + 10, getY());}
	}
	@Override public void componentMoved(ComponentEvent e) {
		if(toolbar != null) {toolbar.setLocation(getX()+ getWidth() + 10, getY());}
	}
	@Override public void componentShown(ComponentEvent e) {}
	@Override public void componentHidden(ComponentEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
}