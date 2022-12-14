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
                    paintReport.setStatus("Fill : false");
                } else {
                    fillButton.setText("\uf043");
                    fillStatus = !fillStatus;
                    paintReport.setStatus("Fill : true");
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
            paintReport.setStatus(file.getSelectedFile().getPath());
        } else {
            paintReport.setStatus("canceled or error");
        }
    }

    // <--- getter & setter ---> //

    // 選択されている図形モードを返すゲッター //
    public String getObjMode() {
        return objModeGroup.getSelection().getActionCommand();
    }

    // 塗りつぶしがオンになっているか返すゲッター //
    public Boolean getFillStatus() {
        return this.fillStatus;
    }

    // 指定された色を返すゲッター //
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
