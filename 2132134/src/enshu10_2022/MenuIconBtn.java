package enshu10_2022;

import java.awt.Font;

class MenuIconBtn extends MenuBtn{
	public MenuIconBtn(String text, String command) {
		super(text, command);
		setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
	}
	public MenuIconBtn(String text, String command,String tooltip) {
		super(text, command,tooltip);
		setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
	}
}
