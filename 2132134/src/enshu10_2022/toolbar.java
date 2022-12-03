package enshu10_2022;

import java.awt.FlowLayout;

import javax.swing.JFrame;

class toolbar extends JFrame {
	public toolbar() {
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		//setAlwaysOnTop(true);
		setTitle("Toolbar");
		
		setVisible(true);
	}
	public static void main(String [] args) {
		new toolbar();
	}
	
}
