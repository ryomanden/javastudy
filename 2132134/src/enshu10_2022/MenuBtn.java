package enshu10_2022;

import java.awt.Dimension;

import javax.swing.JButton;

class MenuBtn extends JButton {
	public static void main(String[] args) {
		
	}
	public MenuBtn(String text,String command) {
		addActionListener(actionListener);
		setText(text);
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
	}

}
