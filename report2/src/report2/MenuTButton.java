package report2;

import javax.swing.*;
import java.awt.*;

class MenuTButton extends JToggleButton {

    // コンストラクタ //
    public MenuTButton(String text, String command, Boolean selected) {
        super(text, selected);//継承元に渡す
        setActionCommand(command);
        setPreferredSize(new Dimension(80, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
