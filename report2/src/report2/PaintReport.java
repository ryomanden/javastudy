package report2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener, ComponentListener, KeyListener {

    // 変数，配列などの宣言 //
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = screenSize.width, height = screenSize.height;
    static toolbar toolbar = null;
    static PaintReport f = new PaintReport();
    int x, y;
    int mode = 0, undo = 0;
    boolean isShift = false, isEnter = false, isDrawing = false;
    ArrayList<report2.Figure> objList;
    report2.Figure obj;
    Label statusLabel = new Label("Wait...");

    // コンストラクタ //
    PaintReport() {
        objList = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);//ウィンドウのサイズ変更を取得する
        addKeyListener(this);
        setCursor(new Cursor(Cursor.WAIT_CURSOR));//起動直後，カーソルを読み込み状態に変更する
        setLayout(new BorderLayout());
        Panel statusPanel = new Panel();
        statusPanel.add(statusLabel);
        statusPanel.setLayout(new GridLayout());
        statusPanel.setBackground(Color.LIGHT_GRAY);
        add(statusPanel, BorderLayout.SOUTH);
    }

    // メインメソッド //
    public static void main(String[] args) {
        f.setBounds(width / 4, height / 4, 640, 480);
        f.setTitle("Main Window");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });
        f.setVisible(true);

        toolbar = new toolbar(f);

        toolbar.setBounds(width / 4 + 645, height / 4, 200, 480);
    }

    // アプリケーションの終了を行うメソッド //
    public static void quit() {
        switch (JOptionPane.showConfirmDialog(f, "Save before exiting?")) {
            case JOptionPane.YES_OPTION:
                toolbar.saveDialog();
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;
            case JOptionPane.CANCEL_OPTION:
            default:
                break;
        }
    }

    // Undoを行うメソッド //
    void undo() {
        if (undo < objList.size()) {// <---undo
            undo += 1;
            setStatus("Undo : " + undo);//status
            repaint();
        }
    }

    // Redoを行うメソッド //
    void redo() {
        if (undo > 0) {
            undo -= 1;
            setStatus("Undo : " + undo);//status
            repaint();
        }
    }

    // 描画されている図形を削除するメソッド //
    public void clear() {
        objList.clear();
        undo = 0;
        obj = null;
        mode = 0;
        isDrawing = false;
        repaint();
    }

    // ファイルを保存するメソッド //
    public void save(String fname) {
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(objList);
            oos.close();
            fos.close();
            setStatus("file saved.");//status
            fos.close();
        } catch (IOException e) {
            setStatus("ERROR : failed to save.");//status
        }
        repaint();
    }

    // ファイルの読み込みを行うメソッド //
    @SuppressWarnings("unchecked")
    public void load(String fname) {
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            objList = (ArrayList<report2.Figure>) ois.readObject();
            ois.close();
            fis.close();
            setStatus("loaded");//status
        } catch (IOException e) {
            setStatus("ERROR : failed to load.");//status
        } catch (ClassNotFoundException e) {
            setStatus("ERROR : class not found.");//status
        }
        repaint();
    }

    // 画面に図形を描画するメソッド //
    @Override public void paint(Graphics g) {
        report2.Figure f;
        for (int i = 0; i < (objList.size() - undo); i++) {
            f = objList.get(i);
            f.paint(g);
        }
        if (toolbar != null) {//ツールバーが表示されるまで処理を行わない様にする
            toolbar.setUndo(0 < objList.size() && undo < objList.size());
            toolbar.setRedo(undo > 0);
            if (mode >= 1) obj.paint(g);
        }
    }

    // メインウィンドウでクリックイベントが発生した際の処理 //
    @Override public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        // Undoを押した後に画面がクリックされた場合，Undoした分のオブジェクトを削除する //
        if (undo != 0) {// <---undo delete
            for (; undo > 0; undo--) {
                objList.remove(objList.size() - 1);
            }
        }

        // ツールバーで選択されている図形モードを確認し，選択に応じた図形を生成する //
        switch (toolbar.getObjMode()) {
            case "dott":
                isDrawing = false;
                mode = 1;
                obj = new Dot(toolbar.getColor(), toolbar.getFillStatus());
                break;

            case "circle":
                isDrawing = false;
                mode = 2;
                obj = new report2.Circle(toolbar.getColor(), toolbar.getFillStatus());
                break;

            case "rect":
                isDrawing = false;
                mode = 2;
                obj = new Rect(toolbar.getColor(), toolbar.getFillStatus());
                break;

            case "line":
                mode = 2;
                if (!isDrawing) {
                    obj = new Line(new Coord(x, y), toolbar.getColor());
                    isDrawing = true;
                }
                break;

            default:
                break;
        }
        if (obj != null) {
            obj.moveto(x, y);
            obj.setPerfect(isShift);
            repaint();
        }
    }

    // マウスのクリックが解除された際の処理 //
    @Override public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (mode == 1) obj.moveto(x, y);
        else if (mode == 2) obj.setWH(x - obj.x, y - obj.y);
        if (mode >= 1) {
            objList.add(obj);
            obj = null;
        }
        mode = 0;
        repaint();
    }

	// override //
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // マウスがクリック状態で移動している際の処理 //
    @Override public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if (mode == 1) {
            obj.moveto(x, y);
        } else if (mode == 2) {
            obj.setWH(x - obj.x, y - obj.y);
        }
        obj.setPerfect(isShift);
        repaint();
    }

    // マウスカーソルを動かしている際の処理 //
    @Override public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        // 線描画モードで，線とカーソルとの間にプレビュー線を引くための処理 //
        if (isDrawing && Objects.equals(toolbar.getObjMode(), "line")) {
            obj = new Line(objList.get(objList.size() - 1 - undo), toolbar.getColor());
            obj.moveto(x, y);
            mode = 1;
            repaint();
        }
    }

    // メインウィンドウがリサイズされた際の処理 //
    @Override public void componentResized(ComponentEvent e) {
        if (toolbar != null) {
            toolbar.setLocation(getX() + getWidth() + 10, getY());
        }
    }

    // メインウィンドウが移動された際の処理 //
    @Override public void componentMoved(ComponentEvent e) {
        if (toolbar != null) {
            toolbar.setLocation(getX() + getWidth() + 10, getY());
        }
    }
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // キーボードのキーが押された際の処理 //
    @Override public void keyPressed(KeyEvent e) {

        // 押されたキーに応じて処理を行う //
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                isShift = true;
                setStatus("shift: true");
                break;
            case KeyEvent.VK_ENTER:
                isEnter = true;
                isDrawing = false;
                setStatus("enter: true");
                obj = null;
                mode = 0;
                repaint();
                break;
        }
    }

    // キーボードのキーが離された際の処理 //
    @Override public void keyReleased(KeyEvent e) {

        // 離されたキーに応じて処理を行う //
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                isShift = false;
                setStatus("shift: false");
                break;
            case KeyEvent.VK_ENTER:
                isEnter = false;
                setStatus("enter: false");
                break;
        }
    }

    // <--- getter & setter ---> //

    // ステータスバーの内容を設定するセッター //
    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    // メインウィンドウ内のカーソルを設定するセッター //
    public void setCursor_this(int Cursor) {
        f.setCursor(new Cursor(Cursor));
    }
}
