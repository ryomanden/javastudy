package enshu10_2022;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JToggleButton;

class MenuTButton extends JToggleButton{
	public MenuTButton(String text,String command,Boolean selected) {
		super(text,selected);
		setActionCommand(command);
		setPreferredSize(new Dimension(80,40));
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setActionCommand(command);
	}
}
