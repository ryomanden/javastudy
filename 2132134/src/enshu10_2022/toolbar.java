package enshu10_2022;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

class toolbar extends JFrame implements MouseListener, ActionListener{
	
	ButtonGroup btnGroup = new ButtonGroup();
	
	public toolbar() {
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		//setAlwaysOnTop(true);
		setTitle("Toolbar");
		addMouseListener(this);
		

		btnEvent();
		setVisible(true);
	}
	public static void main(String [] args) {
		new toolbar();
	}
	
	void btnEvent() {
		JButton clearButton = new JButton("Clear");
		JButton saveButton = new JButton("Save");
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");
		JToggleButton dotToggleButton = new JToggleButton("dot",true);
		dotToggleButton.addActionListener(this);
		dotToggleButton.setActionCommand("dott");
		JToggleButton lineToggleButton = new JToggleButton("line",false);
		lineToggleButton.setActionCommand("line");
		
		btnGroup.add(dotToggleButton);
		btnGroup.add(lineToggleButton);

		add(dotToggleButton);
		add(lineToggleButton);
		add(clearButton);
		add(saveButton);
		add(closeButton);
	}
	
	
	//---> DEBUG 
	@Override public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "dott": {
			System.out.println("dott");
			break;	
		} case "line": {
			System.out.println("line");
		} case "close": {
			System.out.println("close");
		}
		default:
			break;
		}
	}
	//<--- DEBUG
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}

