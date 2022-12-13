package report2;

import java.awt.*;

public class Rect extends report2.Figure {
    int X, Y, W, H, Width, Height;

    Rect(Color color, Boolean fs) {
        this.color = color;
        this.fillStatus = fs;
    }

    @Override public void paint(Graphics g) {
        g.setColor(color);
        if (isPerfect) { //正方形へ切り替え
            if (Math.abs(w) > Math.abs(h)) {
                W = w;
                H = w;
            } else {
                W = h;
                H = h;
            }
        } else { //長方形へ切り替え
            W = w;
            H = h;
        }

        if (W < 0) {
            X = x + W;
            Width = -W;
        } else {
            X = x;
            Width = W;
        }
        if (H < 0) {
            Y = y + H;
            Height = -H;
        } else {
            Y = y;
            Height = H;
        }
        if (fillStatus) {
            g.fillRect(X, Y, Width, Height);
        } else {
            g.drawRect(X, Y, Width, Height);
        }
    }
}
