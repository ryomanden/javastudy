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

    PaintReport paintReport;
    MenuIconBtn fillButton = new MenuIconBtn("\uf5c7", "fill", "Object fill ON/OFF");
    MenuIconBtn undoButton = new MenuIconBtn("\uf3e5", "undo", "Return to previous step");
    MenuIconBtn redoButton = new MenuIconBtn("\uf064", "redo", "Return to next step");
    JLabel nowColor = new JLabel("\uf0c8");
    private final ButtonGroup objModeGroup = new ButtonGroup();
    private final JFileChooser file = new JFileChooser(".");
    private Color selectColor = Color.black;
    private boolean fillStatus = false;

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

        btnEvent();
        setVisible(true);
    }

    void btnEvent() {
        GridLayout col2Layout = new GridLayout(0, 2, 5, 5);

        undoButton.addActionListener(this);
        undoButton.setEnabled(false);
        add(undoButton);

        redoButton.addActionListener(this);
        redoButton.setEnabled(false);
        add(redoButton);

        TitledBorder styleBorder = new TitledBorder("ObjectStyle");
        JPanel panel1 = new JPanel();
        panel1.setLayout(col2Layout);
        panel1.setBorder(styleBorder);
        add(panel1);

        TitledBorder toolBorder = new TitledBorder("Menu");
        JPanel panel2 = new JPanel();
        panel2.setLayout(col2Layout);
        panel2.setBorder(toolBorder);
        add(panel2);

        TitledBorder colorBorder = new TitledBorder("Color");
        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(180, 110));
        panel3.setBorder(colorBorder);
        add(panel3);

        MenuTButton dotToggleButton = new MenuTButton("Dot", "dott", true);
        dotToggleButton.addActionListener(this);
        objModeGroup.add(dotToggleButton);
        panel1.add(dotToggleButton);

        MenuTButton circleButton = new MenuTButton("Circle", "circle", false);
        circleButton.addActionListener(this);
        objModeGroup.add(circleButton);
        panel1.add(circleButton);

        MenuTButton rectToggleButton = new MenuTButton("Rect", "rect", false);
        rectToggleButton.addActionListener(this);
        objModeGroup.add(rectToggleButton);
        panel1.add(rectToggleButton);

        MenuTButton lineToggleButton = new MenuTButton("Line", "line", false);
        lineToggleButton.addActionListener(this);
        objModeGroup.add(lineToggleButton);
        panel1.add(lineToggleButton);

        MenuIconBtn clearButton = new MenuIconBtn("\uf1f8", "clear", "Clear all objects");
        clearButton.addActionListener(this);
        panel2.add(clearButton);

        MenuIconBtn loadButton = new MenuIconBtn("\uf07c", "load", "Load the drawn object from file");
        loadButton.addActionListener(this);
        panel2.add(loadButton);

        MenuIconBtn saveButton = new MenuIconBtn("\uf0c7", "save", "Save the drawn object to file");
        saveButton.addActionListener(this);
        panel2.add(saveButton);

        MenuIconBtn closeButton = new MenuIconBtn("\uf011", "close", "Quit this program");
        closeButton.addActionListener(this);
        panel2.add(closeButton);

        MenuIconBtn colorButton = new MenuIconBtn("\uf5c3", "color", "Select object color");
        colorButton.addActionListener(this);
        colorButton.setPreferredSize(new Dimension(50, 50));
        panel3.add(colorButton);

        fillButton.addActionListener(this);
        fillButton.setPreferredSize(new Dimension(50, 50));
        panel3.add(fillButton);

        JLabel nowColorTitle = new JLabel("Selected color : ");
        panel3.add(nowColorTitle);

        nowColor.setForeground(getColor());
        nowColor.setFont(new Font("Font Awesome 6 Free", Font.PLAIN, 15));
        panel3.add(nowColor);

        paintReport.setCursor_this(Cursor.DEFAULT_CURSOR);
        paintReport.setStatus("Ready.");
    }


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
                break;

            case "load":
                if (file.showOpenDialog(paintReport) == JFileChooser.APPROVE_OPTION) {
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

    public void saveDialog() {
        if (file.showSaveDialog(paintReport) == JFileChooser.APPROVE_OPTION) {
            paintReport.save(file.getSelectedFile().getPath());
        } else {
            paintReport.setStatus("canceled or error");
        }
    }

    // getter & setter //
    public String getObjMode() {
        return objModeGroup.getSelection().getActionCommand();
    }

    public Boolean getFillStatus() {
        return this.fillStatus;
    }

    public Color getColor() {
        return this.selectColor;
    }

    public void setUndo(boolean undo) {
        undoButton.setEnabled(undo);
    }

    public void setRedo(boolean redo) {
        redoButton.setEnabled(redo);
    }
}
