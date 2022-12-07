package enshu10_2022;

import java.awt.Dimension;

import javax.swing.JButton;

class MenuBtn extends JButton {
	public MenuBtn(String text,String command) {
		super(text);
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
	}
	public MenuBtn(String text,String command,String tooltip) {
		super(text);
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
		setToolTipText(tooltip);
	}
}
