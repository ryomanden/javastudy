package report2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener, ComponentListener, KeyListener {
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = screenSize.width;
    static int height = screenSize.height;
    static toolbar toolbar = null;
    static PaintReport f = new PaintReport();
    int x, y;
    boolean isShift = false, isEnter = false, isDrawing = false;
    ArrayList<report2.Figure> objList;
    int mode = 0, undo = 0;
    report2.Figure obj;
    Label statusLabel = new Label("Loading...");

    public static void main(String[] args) {
        f.setBounds(width / 4, height / 4, 640, 480);
        f.setTitle("Paint Sample");
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);

        toolbar = new toolbar(f);


        //if(args .length == 1) f.load(args[0]);
        toolbar.setBounds(width / 4 + 645, height / 4, 200, 480);
    }

    PaintReport() {
        objList = new ArrayList<report2.Figure>();
        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);//ウィンドウのサイズ変更を見る
        addKeyListener(this);
        setLayout(new BorderLayout());
        Panel statusPanel = new Panel();
        statusPanel.add(statusLabel);
        statusPanel.setBackground(Color.LIGHT_GRAY);
        add(statusPanel, BorderLayout.SOUTH);
    }

    void undo() {
        if (undo < objList.size()) {// <---undo
            undo += 1;
            setStatus("undo" + undo);//debug
            repaint();
        }
    }

    void redo() {
        if (undo > 0) {
            undo -= 1;
            setStatus("redo" + undo);//debug
            repaint();
        }
    }

    public void clear() {
        objList.clear();
        undo = 0;
        isDrawing = false;
        repaint();
    }

    public void save(String fname) {
        try {
            FileOutputStream fos = new FileOutputStream(fname);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(objList);
            oos.close();
            fos.close();
        } catch (IOException e) {
        }
        setStatus("saved");//debug
        repaint();
    }

    @SuppressWarnings("unchecked")
    public void load(String fname) {
        try {
            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);
            objList = (ArrayList<report2.Figure>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        setStatus("loaded");//debug
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        report2.Figure f;
        for (int i = 0; i < (objList.size() - undo); i++) {
            f = objList.get(i);
            f.paint(g);
        }
        if (toolbar != null) {
			toolbar.setUndo(0 < objList.size() && undo < objList.size());
			toolbar.setRedo(undo > 0);
        }
        if (mode >= 1) obj.paint(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        if (undo != 0) {// <---undo delete
            for (; undo > 0; undo--) {
                objList.remove(objList.size() - 1);
            }
        }

        switch (toolbar.getObjMode()) {
            case "dott":
                mode = 1;
                obj = new Dot(toolbar.getColor(), toolbar.getFillStatus());
                break;

            case "circle":
                mode = 2;
                obj = new report2.Circle(toolbar.getColor(), toolbar.getFillStatus());
                break;

            case "rect":
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

    @Override
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (mode == 1) obj.moveto(x, y);
        else if (mode == 2) obj.setWH(x - obj.x, y - obj.y);
        if (mode >= 1) {
            objList.add(obj);
            obj = null;
        }
        mode = 0;
        if (isDrawing) {
            obj = new Line(objList.get(objList.size() - 1), toolbar.getColor());
            obj.moveto(x, y);
            mode = 1;
        }
        repaint();
    }

	// override //
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
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
    @Override public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (isDrawing) {
            obj.moveto(x, y);
            repaint();
        }
    }
    @Override public void componentResized(ComponentEvent e) {
        if (toolbar != null) {
            toolbar.setLocation(getX() + getWidth() + 10, getY());
        }
    }
    @Override public void componentMoved(ComponentEvent e) {
        if (toolbar != null) {
            toolbar.setLocation(getX() + getWidth() + 10, getY());
        }
    }
    @Override public void componentShown(ComponentEvent e) {}
    @Override public void componentHidden(ComponentEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
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
    @Override public void keyReleased(KeyEvent e) {
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

	// setter & getter //
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
    public void setCursor(int Cursor) {
        f.setCursor(new Cursor(Cursor));
    }
}
