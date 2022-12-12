package report2;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JButton;

class MenuBtn extends JButton {
	public MenuBtn(String text,String command) {
		super(text);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
	}
	public MenuBtn(String text,String command,String tooltip) {
		super(text);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
		setToolTipText(tooltip);
	}
}
