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
	
	private ButtonGroup btnGroup = new ButtonGroup();
	
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
		JToggleButton dotToggleButton = new JToggleButton("dot",true);
		dotToggleButton.setActionCommand("dott");
		btnGroup.add(dotToggleButton);
		add(dotToggleButton);
		
		JToggleButton lineToggleButton = new JToggleButton("line",false);
		lineToggleButton.setActionCommand("line");
		btnGroup.add(lineToggleButton);
		add(lineToggleButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		clearButton.setActionCommand("clear");
		add(clearButton);

		JButton saveButton = new JButton("Save");
		add(saveButton);

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");
		add(closeButton);
	}
	
	
	//---> DEBUG 
	@Override public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "clear": {
			System.out.println("clear");
		} 
		case "save": {
			System.out.println("save");
		}
		case "close": {
			System.out.println("close");
		}
		default:
			System.out.println(btnGroup.getSelection().getActionCommand());
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

