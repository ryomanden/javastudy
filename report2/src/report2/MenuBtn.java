package report2;

import javax.swing.*;
import java.awt.*;

class MenuBtn extends JButton {

    // コンストラクタ //
    public MenuBtn(String text, String command, String tooltip) {
        super(text);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setActionCommand(command);
        setPreferredSize(new Dimension(80, 40));
        setToolTipText(tooltip);
    }
}
