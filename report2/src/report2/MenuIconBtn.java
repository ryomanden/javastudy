package report2;

import java.awt.*;

class MenuIconBtn extends MenuBtn {

    // コンストラクタ //
    public MenuIconBtn(String text, String command, String tooltip) {
        super(text, command, tooltip);//継承元に渡す
        setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
    }
}
