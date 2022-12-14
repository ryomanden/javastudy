package report2;

import java.awt.*;

public class Rect extends report2.Figure {

    // 変数の宣言 //
    int X, Y, W, H, Width, Height;

    // コンストラクタ //
    Rect(Color color, Boolean fs) {
        this.color = color;
        this.fillStatus = fs;
    }

    // 描画を行うメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);
        if (isPerfect) { //正方形へ切り替え

            // 幅と高さの大きい方の値を採用する //
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

        // <--- 四方向どこに動かしても描画されるようにする処理 ---> //

        // マウスを上側に動かした際の処理 //
        if (W < 0) {
            X = x + W;
            Width = -W;
        } else {
            X = x;
            Width = W;
        }

        // マウスを左側に動かした際の処理 //
        if (H < 0) {
            Y = y + H;
            Height = -H;
        } else {
            Y = y;
            Height = H;
        }
        // 塗りつぶしの処理 //
        if (fillStatus) {//塗りつぶし図形を描画する
            g.fillRect(X, Y, Width, Height);
        } else {//線のみの図形を描画する．
            g.drawRect(X, Y, Width, Height);
        }
    }
}
