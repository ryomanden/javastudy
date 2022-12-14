---
title:第2回レポート
header:ネットワークプログラミング応用 - 第2回レポート
author:Ryo Mitsuda
---

# 第2回レポート

## 1. 目的

ペイントアプリケーションの開発を通し，前回の課題に引き続きオブジェクト指向言語の特性を利用したプログラムを作成し，より効率的なプログラム作成を学ぶ．

とくに，前回の課題では理解が完全ではなかった継承などを積極的に取り入れたプログラム制作し理解を深める．

また，前回使用できなかったSwingなども取り入れ，より実践的なプログラム制作を行う．

## 2. 課題

1. 各図形の線に個別に色をつけられる様にする．
2. どの方向へマウスを動かしても四角形が描画されるようにする．
3. 楕円の描画を可能にする．
4. 折れ線の描画を可能にする．
5. Undo機能を実装する．
6. Redo機能を実装する．
7. 塗りつぶし図形の描画を可能にする．
8. ファイル名を指定した保存・読み込みを可能にする．
9. 2.を拡張し，Shiftキーを押すことで正方形の描画を可能にする．
10. 4.を拡張し，Shiftキーを押すことで正円の描画を可能にする．
11. 描画するオブジェクトに合わせて最適なカーソルに切り替える．
12. プログラム内にステータスバーを配置し，アプリケーションの状態を表示する．
13. プログラムを終了する際，保存を促すダイアログを表示するプログラムを実装する．
14. 描画中の図形を全て削除するボタンを実装する．
15. ツールバーを追加し以下の機能を実装する．
    1. ボタンにツールチップを追加する．
    2. ボタンにアイコンを追加する．
    3. ボタンホバー時のカーソルを変更する．

## 3. 理論

上記課題を実装するにあたり，必要となるクラス・およびインターフェースを調査した．以下はその一覧である．

- `swing`クラス．awtに代わりGUIを実装するために使用．
- `filechooser`クラス．ファイルの読み込み・書き込みの際のファイル選択ウィンドウを実装するために使用．
- `JOptionPane`クラス．終了時のダイアログを実装するために使用．

## 4.プログラム

本プログラムの実行に必要なファイルは，以下の通りである．

```bash : tree
report2
├── Circle.java
├── Coord.java
├── Dot.java
├── Figure.java
├── Line.java
├── MenuBtn.java
├── MenuIconBtn.java
├── MenuTButton.java
├── PaintReport.java
├── Rect.java
└── toolbar.java
```

```java : PaintReport.java
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
```

PaintReportクラスは主にメインウィンドウ内のイベントに応じた処理を行うクラスである．

１３−１４：PCの画面の大きさを取得し，格納する変数．

１５−１６：インスタンスを格納しておくための変数．

１７：マウスの座標を取得し，格納するための変数．

１８：`mode`は描画モードを格納するための変数，`undo`は描画履歴をいくつ遡るかを格納するための変数．主に課題５を実装するために用いる．

１９： `isShift`，`isEnter`はキーが押されているかどうかを格納するための変数．`isDrawing`は折れ線を描画中かどうかを格納する変数．

２２：ステータスバーに表示させるテキスト．課題１２を実現させるために用いる．

２９：ウィンドウのサイズ変更イベントを受け取るための宣言．

３０：キー入力イベントを受け取るための宣言．

３１：起動直後には描画が行えないため，準備中を示すためにカーソルを変更する．

３３−３７：課題１２．ステータスバーを生成し，ウィンドウの下部に追加する処理．

４２：画面の大きさから中心を計算し，画面の中央に640x480のメインウィンドウを配置する．

４４−４９：ウィンドウの閉じるボタンを押した際の処理．実際にプログラムを終了する処理は，`quit`メソッド内で行う．

５２：ツールバーをインスタン化し，変数に格納する．

５４：画面の大きさからメインウィンドウの位置を計算し，メインウィンドウの右側に配置する．

５９：課題１３．`quit`メソッドが実行されると，ダイアログを表示する．ダイアログ内のどのボタンが押されたかは，戻り値で判断する．

６０−６２：Yesが押された際の処理．ファイルに保存する処理を行うため，`saveDialog`メソッドを呼び出す．

６３−６５：Noが押された際の処理．アプリケーションを正常終了する．

６６：Cancelが押された際の処理．何も行わない．

７３−７９：課題５．`undo`変数を加算する処理．過去に描画した図形の数より少ない場合にのみ，`undo`変数を加算する．

８２−８８：課題６．`undo`変数を減算する処理．`undo`が押された数よりも少ない場合に減算する．

９１−９８：描画されている図形などを削除し，各パラメーターをリセットする．おもに課題１４．

１０１−１１５：課題８．ファイルへの保存を行うメソッド．引数で指定された絶対パスで保存する．例外処理として，何らかのエラーが発生した場合にはステータスバーに表示する．

１１７−１３０：課題８．保存したファイルの読み込みを行うメソッド．引数で指定された絶対パスから読み込む．例外処理として，エラーが発生した場合にはステータスバーに表示する．

１３７：課題５．６．`undo`変数に応じて，`objList`内の描画される図形の数を減らすことでUndo，Redo機能を実装している．

１４１：ツールバーが表示されるまで`setUndo`などのメソッドを実行できないため，表示されていない時には実行されない様にする処理．

１４２−１４３：課題５．６．Undo，Redoの限界になった時，ボタンを無効化する処理．

１５４−１５８：課題５．新たな図形を描画しようとした場合，Undo が行われていれば，`undo`変数に応じてobjListから図形を削除する．

１６１−１９６：`getObjMode`メソッドでツールバーにて選択されている図形モードを取得し，モードに応じた図形を生成する．

１８０−１８６：課題４．線の生成を行うメソッド．最初のクリックは開始点を決めるための動作のため，1回目のクリックでは点を描画する．`drawStatus`が`false`の場合は1回目のクリック，`true`は折れ線の描画中を表す．

１９３，２２７：課題９．１０．Shiftキーが押されている場合円と四角形を１：１にするため，`setPerfect`メソッドにShiftキーの状況を代入する．

２３２−２４３：課題４．線描画モードで，直前のクリック位置からカーソルまでの線（プレビュー線）を引くための処理．objListに保存されている一つ前の図形の位置を開始位置，現在のカーソルの位置を終了位置として線を引く．

２４６−２５０：課題１５．メインウィンドウのサイズが変更された際，ツールバーがウィンドウの右横に来る様にツールバーを移動させる処理．

２５３−２５７：課題１５．メインウィンドウの移動に合わせて，ツールバーも移動させるための処理．

２６３−２８０：課題４．９．１０．キーが押された際に，押されたキーに応じて処理を行うメソッド，

２６７−２７０：Shiftキーが押されている際の処理．`isShift`を`true`に変更し，ステータスバーに状態を表示する．

２７１−２７８：Enterが押されている際の処理．`isEnter`を`true`にし，`isDrawing`を`false`にする．描画されているプレビュー線を削除するなどを行う．ステータスバーに状態を表示する．

２８３−２９６：キーが離された際の処理．ステータスバーに状態を表示し，それぞれの押下状態を表す変数に`false`を代入する．

３０１−３０３：課題１２．ステータスバーに表示するテキストを設定するためのセッターメソッド．

３０６−３０９：課題１１．メインウィンドウ内のカーソルを設定するためのセッターメソッド．

```java : toolbar.java
package report2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class toolbar extends JFrame implements ActionListener {

    // 変数・配列などの宣言 //
    PaintReport paintReport;
    MenuIconBtn fillButton = new MenuIconBtn("\uf5c7", "fill", "Object fill ON/OFF");
    MenuIconBtn undoButton = new MenuIconBtn("\uf3e5", "undo", "Return to previous step");
    MenuIconBtn redoButton = new MenuIconBtn("\uf064", "redo", "Return to next step");
    JLabel nowColor = new JLabel("\uf0c8");
    private final ButtonGroup objModeGroup = new ButtonGroup();
    private final JFileChooser file = new JFileChooser(".");
    private Color selectColor = Color.black;
    private boolean fillStatus = false;

    // コンストラクタ //
    public toolbar(PaintReport paintReport) {
        this.paintReport = paintReport;
        getContentPane().setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Toolbar");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PaintReport.quit();
            }
        });

        file.setFileFilter(new FileNameExtensionFilter("PaintData(*.dat)", "dat"));

        ui();
        setVisible(true);
    }

    // ボタンのレイアウト・配置を行うメソッド //
    void ui() {
        GridLayout col2Layout = new GridLayout(0, 2, 5, 5);

        // Undo button //
        undoButton.addActionListener(this);
        undoButton.setEnabled(false);
        add(undoButton);

        // Redo button //
        redoButton.addActionListener(this);
        redoButton.setEnabled(false);
        add(redoButton);

        // Object style selecter group //
        TitledBorder styleBorder = new TitledBorder("ObjectStyle");
        JPanel panel1 = new JPanel();
        panel1.setLayout(col2Layout);
        panel1.setBorder(styleBorder);
        add(panel1);

        // Menu group //
        TitledBorder toolBorder = new TitledBorder("Menu");
        JPanel panel2 = new JPanel();
        panel2.setLayout(col2Layout);
        panel2.setBorder(toolBorder);
        add(panel2);

        // Color setting group //
        TitledBorder colorBorder = new TitledBorder("Color");
        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(180, 110));
        panel3.setBorder(colorBorder);
        add(panel3);

        // Dott button //
        MenuTButton dotToggleButton = new MenuTButton("Dot", "dott", true);
        dotToggleButton.addActionListener(this);
        objModeGroup.add(dotToggleButton);
        panel1.add(dotToggleButton);

        // Circle button //
        MenuTButton circleButton = new MenuTButton("Circle", "circle", false);
        circleButton.addActionListener(this);
        objModeGroup.add(circleButton);
        panel1.add(circleButton);

        // Rectangle button //
        MenuTButton rectToggleButton = new MenuTButton("Rect", "rect", false);
        rectToggleButton.addActionListener(this);
        objModeGroup.add(rectToggleButton);
        panel1.add(rectToggleButton);

        // Line button //
        MenuTButton lineToggleButton = new MenuTButton("Line", "line", false);
        lineToggleButton.addActionListener(this);
        objModeGroup.add(lineToggleButton);
        panel1.add(lineToggleButton);

        // Clear button //
        MenuIconBtn clearButton = new MenuIconBtn("\uf1f8", "clear", "Clear all objects");
        clearButton.addActionListener(this);
        panel2.add(clearButton);

        // File import button //
        MenuIconBtn loadButton = new MenuIconBtn("\uf07c", "load", "Load the drawn object from file");
        loadButton.addActionListener(this);
        panel2.add(loadButton);

        // File export button //
        MenuIconBtn saveButton = new MenuIconBtn("\uf0c7", "save", "Save the drawn object to file");
        saveButton.addActionListener(this);
        panel2.add(saveButton);

        // Close button //
        MenuIconBtn closeButton = new MenuIconBtn("\uf011", "close", "Quit this program");
        closeButton.addActionListener(this);
        panel2.add(closeButton);

        // Color picker button //
        MenuIconBtn colorButton = new MenuIconBtn("\uf5c3", "color", "Select object color");
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(50, 50));
        panel3.add(colorButton);

        // Fill object switch //
        fillButton.addActionListener(this);
        fillButton.setPreferredSize(new Dimension(50, 50));
        panel3.add(fillButton);

        // Color prev //
        JLabel nowColorTitle = new JLabel("Selected color : ");
        panel3.add(nowColorTitle);

        nowColor.setForeground(getColor());
        nowColor.setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
        panel3.add(nowColor);

        // Change-to Toolbar ready //
        paintReport.setCursor_this(Cursor.DEFAULT_CURSOR);
        paintReport.setStatus("Ready.");
    }

    // ボタンのイベント処理を行うメソッド //
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "undo":
                paintReport.undo();
                break;

            case "redo":
                paintReport.redo();
                break;

            case "color":
                selectColor = JColorChooser.showDialog(this, "Color Selector", Color.black);
                if(selectColor != null) {
                    paintReport.setStatus("R:" + getColor().getRed() + "  G:" +  getColor().getGreen() + "  B:" + getColor().getBlue());
                    nowColor.setForeground(getColor());
                }
                break;

            case "fill":
                if (fillStatus) {
                    fillButton.setText("\uf5c7");
                    fillStatus = !fillStatus;
                    paintReport.setStatus("False");
                } else {
                    fillButton.setText("\uf043");
                    fillStatus = !fillStatus;
                    paintReport.setStatus("True");
                }
                break;

            case "clear":
                paintReport.clear();
                paintReport.setStatus("Cleared all objects");
                break;

            case "load":
                if (file.showOpenDialog(paintReport) == JFileChooser.APPROVE_OPTION) {
                    paintReport.clear();
                    paintReport.load(file.getSelectedFile().getPath());
                    paintReport.setStatus(file.getSelectedFile().getPath());
                } else {
                    paintReport.setStatus("canceled or error");
                }
                break;

            case "save":
                saveDialog();
                break;

            case "close":
                PaintReport.quit();
                break;

            case "dott":
                paintReport.setCursor_this(Cursor.DEFAULT_CURSOR);
                break;

            case "circle":
            case "rect":
            case "line":
                paintReport.setCursor_this(Cursor.CROSSHAIR_CURSOR);
                break;

            default:
                paintReport.setStatus("not-set ActionCommand");
                break;
        }
    }

    // 保存用のファイル選択画面を呼び出すメソッド //
    public void saveDialog() {
        if (file.showSaveDialog(paintReport) == JFileChooser.APPROVE_OPTION) {
            paintReport.save(file.getSelectedFile().getPath());
        } else {
            paintReport.setStatus("canceled or error");
        }
    }

    // <--- getter & setter ---> //

    // 選択されている図形モードを受け取れるゲッター //
    public String getObjMode() {
        return objModeGroup.getSelection().getActionCommand();
    }

    // 塗りつぶしがオンになっているか受け取れるゲッター //
    public Boolean getFillStatus() {
        return this.fillStatus;
    }

    // 指定された色を受け取れるゲッター //
    public Color getColor() {
        return this.selectColor;
    }

    // Undoボタンの無効・有効を設定するセッター //
    public void setUndo(boolean undo) {
        undoButton.setEnabled(undo);
    }

    // Redoボタンの無効・有効を設定するセッター //
    public void setRedo(boolean redo) {
        redoButton.setEnabled(redo);
    }
}
```

課題１５．Toolbarクラスは，ツールバーの生成，およびツールバー内のイベントを処理するクラスである．

１５：PaintReportクラスをインスタンス化し，格納しておくための変数．

１６−１９：グローバルで操作が必要なUIの部品を宣言する．

２０：図形モードを選択するトグルボタンのグループ．

２１：課題８．読み込み・保存の際に保存先とファイル名を指定するためのファイルチューザーを格納した変数．

２２：課題１．選択された色を格納する変数．黒で初期化している．

２３：課題７．塗りつぶし状態を格納する変数．`false`で初期化しているので，起動時は塗りつぶしされない．

２６−４２：主にウィンドウの生成に関する処理を行うコンストラクタ．

２８：UIの要素をウィンドウ内で整列させるためのレイアウトを設定している．

２９：課題１３．アプリケーションの終了の際，ダイアログを表示させる必要があるので，ウィンドウのバツボタンが押された際にアプリケーションが終了してしまわないよう，デフォルトの終了処理を無効化する．

３８：ファイルの選択の際，デフォルトで`.dat`のみが選択されるよう，フィルターを設定．

４０：ツールバーのボタンを配置するメソッドを呼び出す．

４５−１４５：ツールバーにボタンなどのUI要素を追加するメソッド．

４６：ボタンのレイアウトを変数に格納する．

４８−５６：Undoボタン，Redoボタンをツールバーに追加する．起動時は何も図形が描画されていないので，初期状態は無効化している．アイコンはFontAwesomeのアイコンを文字コードを使い指定している．クリックイベントが走るよう，アクションリスナーを定義している．

５９−７７：各ボタンを行う処理ごとにグループ化し見やすくするため，タイトルを設定した囲み線を生成し，ツールバーに追加している．レイアウトは４６で定義したレイアウトを用いる．

８０−１０１：図形を切り替えるトグルボタンを生成し，囲み線内に追加する．選択状態はドットボタンに`true`を与え，それ以外は`false`にしているので，起動時はドットボタンがアクティブになる．

１０４−１２１：プログラムの操作を行うボタンを生成し，囲み線内に追加する．アイコンはFontAwesomeのアイコンを文字コードを使い指定している．クリックイベントを取得できるよう，アクションリスナーを定義している．

１２４−１２７：課題１．カラーピッカーを表示するボタンを生成し，囲み線内に追加している．アクションリスナー，アイコンは他のボタンと同様に設定している．

１３０−１３２：課題７．塗りつぶしの切り替えを行うボタンを生成し，囲み線内に追加している．今回はあえて`JToggleButton`を用いず，アイコンを変更することで状態を表すこととした．

１３５−１４０：課題１．選択した色を確認するためのラベルを生成し，囲み線内に追加している．FontAwesomeの四角形アイコンを文字コードを用いて指定し，ラベルの前景色（フォントの色）を`getColor`で取得した色に変更することでプレビュー作成している．

１４８−２１６：ボタンが押された際のイベント処理を行うメソッド．

１５０：`e.getActionCommand()`でクリックされたボタンに設定されたアクションコマンドを取得し，`switch`文でボタンごとの処理を条件分岐している．

１６０−１６５：課題１．色選択ボタンが押された際にカラーピッカーを表示させる処理．選択された色を`selectColor`変数に格納し，`selectColor`が`null`でない時，ステータスバーに選択された色を表示し，選択色のプレビューラベルにも色を反映させる．

１６８−１７７：課題７．塗りつぶしボタンの処理．`fillStatus`が`true`の場合，塗りつぶしでないことを表すアイコンに変更し`fillStatus`を反転，ステータスバーに塗りつぶしが無効であることを表示する．`fillStatus`が`true`の場合は，全く逆の処理を行う．

１８４−１９２：課題８．ファイル読み込みボタンを押した際，ファイルチューザーを表示する処理．正常終了した場合にのみ，現在描画されている図形を全て削除，ステータスバーに読み込んだファイルのパスを表示し，`PaintReport`クラス内の`load`メソッドをファイルパスを引数に代入して呼び出す．キャンセルやエラーによってファイルチューザーが正常終了しなかった場合には，ステータスバーにエラーメッセージを表示する．

１９５−１９６：課題８．保存ボタンが押された際の処理．ファイルチューザーを表示させる`saveDialog`メソッドを呼び出す.

１９９−２００：課題１３．閉じるボタンが押された際の処理．終了の際にダイアログを表示させるメソッドを呼び出す．

２０３−２１０：課題１１．選択された図形モードに適したカーソルを設定する処理．

２１３−２１４：まだ定義されていないボタンのイベントが発生した場合，そのことをステータスバーに表示する．

２１９−２２５：課題８．保存の際にファイルチューザーを表示させるメソッド．ファイルを開く際の処理とほとんど同じであるが，ファイルパスを代入するのは`save`メソッドである．

２３１−２３３：図形モードの選択されているボタンに設定されているアクションコマンドを返すゲッターメソッド．

２３６−２３８：課題７．塗りつぶしを行うかを返すゲッターメソッド．

２４１−２４３：課題１．カラーピッカーで選択された色を返すゲッター

２４６−２５３：課題５．Undo，Redoボタンの有効・無効を切り替えるセッター．

```java : Rect.java
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
```

`Rect`クラスは`Figure`クラスを継承した四角形を描画するクラスである．

８：このクラスで使う変数の宣言．

１１−１４：課題１．７．コンストラクタ．引数で色と塗りつぶしか否かを受け取る．

１７−５２：四角形の描画を行うメソッド．

１９−３２：課題９．変数`isPerfect`は正方形を表す変数であり，`true`の場合には幅と高さの大きい方の値を幅と高さ両方に代入することで正方形を描く．`false`の場合には値に変更を加えていない．

３７−４３：課題２．クリックした点よりカーソルを上側に動かした際の処理．高さ変数の符号を反転し，描画の開始点をカーソルの上方向への移動分ずらす．下側へカーソルを移動する場合には値を変更しない．

４６−５２：課題２．クリックした点よりカーソルを左側に動かした際の処理．幅変数の符号を反転し，描画の開始点をカーソルの左方向への移動分ずらす．右側へカーソルを移動する場合には値を変更しない．

５４−５９：課題７．`fillStatus`変数が`true`の場合，塗りつぶしの図形を描画する．`false`の場合には線の図形を描画する．

```java : Dot.java
package report2;

import java.awt.*;

public class Dot extends Figure {

    // 変数の宣言 //
    int size;

    // コンストラクタ //
    Dot(Color color, Boolean fs) {
        this.color = color;
        this.fillStatus = fs;
        size = 10;
    }

    // 描画するメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);
        if (fillStatus) {
            g.fillOval(x - size / 2, y - size / 2, size, size);
        } else {
            g.drawOval(x - size / 2, y - size / 2, size, size);
        }
    }
}
```

`Dot`クラスは，Figure`クラスを継承したドットを描画するクラスである．

１１−１５：課題７．引数で色と塗りつぶしの可否を受け取り，変数に代入する．

１９：課題１．描画色を引数で指定された色に設定する．

２０−２５：課題７．`fillStatus`が`true`の場合塗りつぶしで描画し，`false`の場合は線で描画する．

```java : Line.java
package report2;

import java.awt.*;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．

    // 変数の宣言 //
    Coord sPoint;

    // コンストラクタ //
    Line(Coord sPoint, Color color) {
        this.color = color;
        this.sPoint = sPoint;
    }

    // 描画するメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);
        g.drawLine(sPoint.x, sPoint.y, x, y); //開始位置から終了位置まで線を引く
    }
}
```

`Line`クラスは，`Figure`クラスを継承した折れ線を描画するクラスである．

８：線の開始点を格納する変数．

１１−１４：線の開始点と色を引数で受けとり，変数に代入する．

１８：課題１．引数で受け取った色を設定する．

１９：開始点から終了点までの線を描く．

```java :Circle.java
package report2;

import java.awt.*;

public class Circle extends Figure {

    // 変数の宣言 //
    int width, height;

    // コンストラクタ //
    Circle(Color color, Boolean fs) {
        this.color = color;
        this.fillStatus = fs;
    }

    // 描画を行うメソッド //
    @Override public void paint(Graphics g) {
        g.setColor(color);

        // 楕円・正円を切り替える //
        if (isPerfect) {
            width = (int) Math.sqrt(w * w + h * h);
            height = (int) Math.sqrt(w * w + h * h);
        } else {
            width = (int) (Math.sqrt(2.0) * Math.abs(w));
            height = (int) (Math.sqrt(2.0) * Math.abs(h));
        }
        
        // 塗りつぶしを切り替える //
        if (fillStatus) {
            g.fillOval(x - width, y - height, width * 2, height * 2);
        } else {
            g.drawOval(x - width, y - height, width * 2, height * 2);
        }
    }
}
```

`Circle`クラスは，`Figure`クラスを継承した円を描くクラスである．

８：幅，高さを格納する変数を宣言する．

１１−１４：課題７．引数で色と塗りつぶしの可否を受け取り，変数に代入する．

２１−２７：`isPerfect`が`true`の場合，クリックした位置からの移動距離（半径）を求め，幅と高さの変数に同一の値を代入する．`false`の場合，楕円を描く．

３０−３４：`fillStatus`が`true`の場合，塗りつぶしの図形を描画する．`false`の場合，線のみの図形を描画する．

```java : Figure.java
package report2;

import java.awt.*;

public class Figure extends Coord {

    // 変数wの宣言 //
    Color color;
    boolean fillStatus = false, isPerfect = false;
    int w, h;

    // コンストラクタ //
    Figure() {
        w = h = 0;
    }

    public void paint(Graphics g) {
    }

    public void setWH(int w, int h) {
        this.w = w;
        this.h = h;
    }

    // 正方形・正円を設定するセッター //
    public void setPerfect(boolean isPerfect) {
        this.isPerfect = isPerfect;
    }
}
```

`Figure`クラスは，`Coord`メソッドを継承したメソッドである．

８：課題１．引数で受け取った色を格納するための，Color型の変数．

９：課題７．９．１０．`fillStatus`は引数で受け取った塗りつぶしの可否を格納する変数，`isPerfect`は正円・正方形を描く際に`true`にする変数．

２６−２８：正方形・正円を描画するかどうか，`isPerfect`に代入するためのセッター．

```java : Coord.java
package report2;

import java.io.Serializable;

public class Coord implements Serializable {
    
    // 変数の宣言 //
    int x, y;
    Coord() {x = y = 0;}

    // コンストラクタ //
    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void moveto(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

`Coord`クラスは，配布のプログラムから変更を加えていない．

```java : MenuIconBtn.java
package report2;

import java.awt.*;

class MenuIconBtn extends MenuBtn {
    
    // コンストラクタ //
    public MenuIconBtn(String text, String command, String tooltip) {
        super(text, command, tooltip);//継承元に渡す
        setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
    }
}
```

`MenuIconBtn`クラスは，ツールバーにてアイコン付きのボタンを生成する際に用いるクラスである．`MenuBtn`クラスを継承する．

８：引数で表示するテキスト，アクションコマンド，ツールチップに表示する内容をString型で受け取る．

９：継承元のコンストラクタに引数で受け取ったテキスト，アクションコマンド，ツールチップの内容を渡す．

１０：課題１５−２．ボタンのフォントを`Font Awesome 6 Free`に設定し，アイコンの表示を可能にする．

```java : MenuTButton.java
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
```

MenuTButtonクラスは，ツールバーに表示するトグルボタンを生成する際に用いるクラスである．`JToggleButton`クラスを継承する．

９：引数で表示するテキスト，アクションコマンド，選択状態を受け取る．

１０：受け取ったボタンに表示するテキストと選択状態を継承元に渡す．

１１：引数で指定されたアクションコマンドをボタンに追加する．

１２：ボタンの大きさを設定する．

１３：課題１５−１．ボタンにホバーした際のカーソルを手のカーソルに変える．

```java : MenuBtn.java
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
```

`MenuBtn`クラスは，ツールバーに表示するボタンを生成する際に用いるクラスである．`JToggleButton`クラスを継承する．

９：引数で表示するテキスト，アクションコマンド，ツールチップの内容を受け取る．

１０：受け取ったボタンに表示するテキストを継承元に渡す．

１１：課題１５−３．ボタンにホバーした際のカーソルを手のカーソルに変える．

１２：引数で指定されたアクションコマンドをボタンに追加する．

１３：ボタンの大きさを設定する．

１３：課題１５−３．ボタンにホバーした際のツールチップを設定する．

## 5. 動作検証，および実行環境

1. 色選択ボタンを押し，カラーピッカーが起動することを確認する．図形が選択した色で描画されることを確認する．

2. どの方向へ動かしても四角形が描画されることを確認する．

3. 楕円が描画されるか確認する．

4. 折れ線が描画されることを確認する．Enterを押して折れ線の描画がリセットされることを確認する．

5. Undoボタンを押して，直前に描画された図形が消えることを確認する．図形が何も表示されていない場合，Undoボタンが無効化されていることを確認する．

6. Redoボタンを押して，Undoされていた図形が再度表示されることを確認する．最新の図形が描画されている場合，Redoボタンが無効化されていることを確認する．

7. 塗りつぶしボタンを押すことで，描画する図形の塗りつぶし切り替えが可能であることを確認する．

8. ファイル保存ボタンを押し，描画されている図形を保存した状態で全てのオブジェクトを削除し保存したファイルを読み込んだ際，先ほど描画した図形が表示されることを確認する．

9. 四角形描画モードにて，Shiftキーを押している間だけ正方形が描画されることを確認する．

10. 円描画モードにて，Shiftキーを押している間だけ正円が描画されることを確認する．

11. 描画する図形モードを切り替えた際，カーソルが切り替わることを確認する．カーソルは以下の様に切り替わる
    - ドット描画モード -> 矢印
    - 円描画モード -> クロスヘア
    - 四角形描画モード -> クロスヘア
    - 線描画モード -> クロスヘア

12. アプリケーションの操作に応じて，アプリケーション下部に表示されているステータスバーに現在のアプリケーションの状態が表示されることを確認する．

13. いずれかの方法でアプリケーションを終了した場合，保存を促すダイアログが表示され，選択したボタンに応じて以下の様に処理されることを確認する．
    - Yes -> 保存ダイアログが開く．
    - No -> 何も表示されずに終了する．
    - Cancel -> プログラムは終了されず，ダイアログが閉じる．

14. 削除ボタンを押し，描画されていた図形が削除されることを確認する．

15. メインウィンドウの右側にツールバーが表示されることを確認し，以下の操作が行えることも併せて確認する．
    1. ボタンにカーソルがホバーした際，機能説明のツールチップが表示される．

    2. ボタンにアイコンが表示されている．

    3. ボタンにカーソルがホバーした際，手のカーソルに変わる．

       

本プログラムの制作，および動作の検証は以下の環境で行なった．

なお，アイコンの表示にはフォントを使用しているため，下記のフォントが実行環境にインストールされていることが動作の必須要件となる．

> フォントの入手ページ：https://fontawesome.com/download
> 上記リンクから，`Free for Desktop` を選択してダウンロードが可能

また，`Solid-900`,`.otf`以外の同シリーズフォントがインストールされている場合，アイコンが表示されない場合がある．

|       Device | Macbook Air                                       |
| -----------: | :------------------------------------------------ |
|          CPU | Apple M1                                          |
|           OS | macOS Ventura 13.0.1                              |
|       Memory | 16GB                                              |
| Java version | Java17                                            |
|          IDE | IntelliJ IDEA 2022.1.4                            |
|    Icon Font | Font Awesome 6 Free-Solid-900.otf (Version 6.2.1) |

## 6. 実行結果

### 検証1.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.16.36.png" style="zoom: 33%;" />  <div style="font-size:0.8rem"><i>図１：色選択ボタンを押してカラーピッカーが表示された．</i></div> | <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.23.42.png" style="zoom:67%;" /><div style="font-size:0.8rem"><i>図２：指定した色で描画された．．</i></div> |
|                                                              |                                                              |

図１のように，カラーパレットアイコンの色選択ボタンを押すとカラーピッカーが表示されるた．

また，図２のようにカラーピッカーで指定した色を使い，描画することができた．

### 検証2.

|                                                              |
| :----------------------------------------------------------: |
| <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.28.50.png" style="zoom: 50%;" /><div style="font-size:0.8rem"><i>図３：中心から四方向へマウスをドラッグした．</i></div> |
|                                                              |

図３のようにウィンドウの中心から四方向にマウスをドラッグし，四角形を描画することができた．

### 検証3.

|                                                              |
| :----------------------------------------------------------: |
| <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.31.57.png" style="zoom:50%;" />  <div style="font-size:0.8rem"><i>図４：楕円を描画．</i></div> |
|                                                              |

図４のように，円描画モードでマウスをドラッグすることにより，楕円の描画が行えた．

### 検証4.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.35.43.png)<div style="font-size:0.8rem"><i>図５：折れ線の描画．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 5.36.10.png)<div style="font-size:0.8rem"><i>図６：Enterで一度描画をリセットし，再度描画した．</i></div> |
|                                                              |                                                              |

図５のように連続した折れ線をびょうができた．

また，図６のように折れ線の描画，Enterを押して描画を完了することで複数の折れ線の描画が行えた．

### 検証5.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.20.55.png)<div style="font-size:0.8rem"><i>図７：ドットを１０個描画．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.21.23.png)<div style="font-size:0.8rem"><i>図８：Undoボタンを5回押した．</i></div> |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.22.00.png)<div style="font-size:0.8rem"><i>図９：Undoを10回押した．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.23.00.png)<div style="font-size:0.8rem"><i>図１０：ドットを10個描画したのち，削除ボタンを押した．</i></div> |
|                                                              |                                                              |

図７の様に，図形を描画するとUndoボタンが押せるようになった．

図８，図９のように，Undoボタンを押すことで描画されていた図形が消えていった．

また，図９，図１０のように，ウィンドウ上に図形が何も描画されていない状況でUndoボタンを押すことはできない．

### 検証6.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.38.14.png)<div style="font-size:0.8rem"><i>図１１：ドットを10個描画したのち，Undoを4回押した．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.38.25.png)<div style="font-size:0.8rem"><i>図１２：Redoを２回押した．</i></div> |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.38.32.png)<div style="font-size:0.8rem"><i>図１３：Redoを4回押した．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.38.41.png)<div style="font-size:0.8rem"><i>図１４：削除ボタンを押した．</i></div> |
|                                                              |                                                              |

図１１のように，Undoを押して遡るとReどボタンが押せるようになった．

図１２，図１３のように，Redoを押すことでUndoによって表示されていなかった図形が再び描画された．

図１３，図１４のように，ウィンドウの表示が最新の状態ではRedoボタンは押せない．

### 検証7.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.49.24.png)<div style="font-size:0.8rem"><i>図１５：塗りつぶしをオフにして描画．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.50.08.png)<div style="font-size:0.8rem"><i>図１６：塗りつぶしをオンにして描画’．</i></div> |
|                                                              |                                                              |

図１５，図１６のように，塗りつぶしのオン・オフを切り替えて図形を描画可能．

### 検証8.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.57.30.png)<div style="font-size:0.8rem"><i>図１７：図を描画し，保存．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 22.57.17.png)<div style="font-size:0.8rem"><i>図１８：一度プログラムを終了後，被再度開いた．</i></div> |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.01.07.png)<div style="font-size:0.8rem"><i>図１９：ファイルを開くダイアログ．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.01.17.png)<div style="font-size:0.8rem"><i>図２０：ファイルを保存するダイアログ．</i></div> |
|                                                              |                                                              |

図１７，図１８のように，描画した図の位置や色なども保存され，読み込むと再度描画された．

図１９，図２０のように，開くボタン，保存ボタンを押すとダイアログが表示された．

### 検証9.　検証10.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.07.13.png)<div style="font-size:0.8rem"><i>図２１：Shiftを押さずに描画．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.07.53.png)<div style="font-size:0.8rem"><i>図２２：Shiftを押して描画．</i></div> |
|                                                              |                                                              |

図２１のように，Shiftを押さずに描画すると楕円と長方形が描ける．

図２２のように，Shiftを押して描画すると正円と正方形を描画できる．

### 検証11.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.14.08.png)<div style="font-size:0.8rem"><i>図２３：ドットを選択．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.14.20.png)<div style="font-size:0.8rem"><i>図２４：円を選択．</i></div> |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.14.28.png)<div style="font-size:0.8rem"><i>図２５：四角形を選択．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.14.35.png)<div style="font-size:0.8rem"><i>図２６：線を選択．</i></div> |
|                                                              |                                                              |

図２３〜図２６からわかるように，図の種類に適したカーソルに変わっている．

### 検証12.

上記１から１１の検証を確認すると，ウィンドウ下部に表示されているステータスバーが，行った操作を表示していることがわかる．

### 検証13.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.24.16.png)<div style="font-size:0.8rem"><i>図２７：閉じる操作を行った．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.24.29.png)<div style="font-size:0.8rem"><i>図２８：表示されたダイアログのYesを押した．</i></div> |
|                                                              |                                                              |

図２７のように，閉じる操作を行うと，確認メッセージとともにダイアログが表示された．

図２８のように，Yesを押すことで保存を行える．

また，写真に収められないため図はないが，Noを押した場合にはプログラムが終了し，Cancelを押した場合には再びアプリケーションの使用を続行できた．

なお，ウィンドウ上部のバツボタンにてプログラムを終了した場合も同様である．

### 検証14.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.30.26.png)<div style="font-size:0.8rem"><i>図２９：図を描画．</i></div> | ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.30.39.png)<div style="font-size:0.8rem"><i>図３０：削除ボタンを押した．</i></div> |
|                                                              |                                                              |

図２９，図３０からわかるように，削除ボタンを押すことで描画されていた図形を全て削除できる．

### 検証15.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.34.25.png)<div style="font-size:0.8rem"><i>図３１：起動した直後の画面．</i></div> | <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.34.34.png" style="zoom:33%;" /><br><div style="font-size:0.8rem"><i>図３２：ツールバー．</i></div> |
| <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.35.48.png" style="zoom:33%;" /><br><div style="font-size:0.8rem"><i>図３３：削除ボタンにホバーした．</i></div> | <img src="/Users/ryo_mitsuda/Documents/github/javastudy/screenshot2/Screenshot 2022-12-14 at 23.36.07.png" style="zoom:33%;" /><br><div style="font-size:0.8rem"><i>図３４：塗りつぶしボタンにホバーした．</i></div> |
|                                                              |                                                              |

図３１のように，起動するとメインウィンドウの右側にツールバーが表示された．

図３２〜図３４のように，ツールバーのボタンにはアイコンが表示され，ボタンにホバーすると，そのボタンの簡単な動作説明がツールチップにて表示される．

## 7. まとめ

継承などを積極的に利用し，Javaのオブジェクト指向言語としての特性を学んだ．また，awtでは実現できなかったUIの細かな動作をSwingを使い構築することで，より実践的なアプリケーションを開発するスキルを身につけることができた．ボタンにアイコンを付けるなど，視覚的にわかりやすいUIを制作することで，ユーザビリティが格段に向上することがわかった．図形の移動や変形，回転など，実装できなかった機能に関しては今後の課題である．
