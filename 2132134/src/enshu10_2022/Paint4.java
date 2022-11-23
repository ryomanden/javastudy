package enshu10_2022;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Paint4 extends Frame implements MouseListener, MouseMotionListener,ActionListener {
	int x, y;
	
	ArrayList<enshu10_2022.Figure> objList;
	CheckboxGroup cbg;
	Checkbox c1, c2, c3 ,c4;
	Button end;
	int mode = 0;
	enshu10_2022.Figure obj;	
	
	public static void main(String[] args) {
		Paint4 f = new Paint4();
		f.setSize(640,480);
		f.setTitle("Paint Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
		if(args .length == 1) f.load(args[0]);
	}
	Paint4(){
		objList = new ArrayList<enshu10_2022.Figure>();
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);
		cbg = new CheckboxGroup();
		c1 = new Checkbox("丸",cbg,true);
		c1.setBounds(560,30,60,30);
		add(c1);
		c2 = new Checkbox("円",cbg,false);
		c2.setBounds(560,60,60,30);
		add(c2);
		c3 = new Checkbox("四角",cbg,false);
		c3.setBounds(560,90,60,30);
		add(c3);
		c4 = new Checkbox("線",cbg,false);
		c4.setBounds(560,120,60,30);
		add(c4);
		
		end = new Button("終了");
		end.setBounds(560,300,60,30);
		add(end);
		
		end.addActionListener(this);
		
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
		repaint();
	}
	
	@SuppressWarnings("unchecked")
	public void load(String fname) {
		try {
			FileInputStream fis = new FileInputStream(fname);
			ObjectInputStream ois = new ObjectInputStream(fis);
			objList = (ArrayList<Figure>)ois.readObject();
			ois.close();
			fis.close();
		} catch(IOException e) {
		} catch (ClassNotFoundException e) {
		}
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
	
	@Override public void actionPerformed(ActionEvent e) {
		save("paint.dat");
		System.exit(0);
	}
	
	@Override public void mousePressed(MouseEvent e) {
		Checkbox c;
		x = e.getX();
		y = e.getY();
		
		c = cbg.getSelectedCheckbox();
		obj = null;
		if (c == c1) {
			mode = 1;
			obj = new Dot();
		} else if(c == c2) {
			mode = 2;
			obj = new enshu10_2022.Circle();
		} else if(c == c3) {
			mode = 2;
			obj = new Rect();
		} else if(c == c4) {
			mode = 2;
			obj = new Line();
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
	
}
