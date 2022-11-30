package enshu10_2022;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

class toolbar extends JFrame implements ActionListener{
	public toolbar() {
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		//setAlwaysOnTop(true);
		setTitle("JFrameTest");
		
		JButton btn1 = new JButton("Close");
		btn1.addActionListener(this);
		getContentPane().add(btn1);
		
		setVisible(true);
	}
	public static void main(String [] args) {
		new toolbar();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicked");
	}
}
