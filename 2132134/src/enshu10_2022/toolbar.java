package enshu10_2022;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class toolbar extends JFrame implements MouseListener, ActionListener{
	
	private ButtonGroup objModeGroup = new ButtonGroup();
	private JFileChooser file = new JFileChooser( "." );
	private Color selectColor = Color.black;
	Paint4 paint4 = null;
	
	MenuIconBtn fillButton = new MenuIconBtn("\uf5c7","fill");
	MenuIconBtn undoButton = new MenuIconBtn("\uf3e5","undo");
	MenuIconBtn redoButton = new MenuIconBtn("\uf064", "redo");
	
	private boolean fillStatus = false;
	
	public toolbar(Paint4 paint4) {
		this.paint4 = paint4;
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		//setAlwaysOnTop(true);
		setTitle("Toolbar");
		addMouseListener(this);
		
		file.setFileFilter(new FileNameExtensionFilter("PaintData(*.dat)","dat"));
		
		btnEvent();
		setVisible(true);
	}
	public static void main() {
		//new toolbar();
	}
	
	void btnEvent() {
		GridLayout col2Layout = new GridLayout(0,2,5,5);
		
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
		panel3.setPreferredSize(new Dimension(180,100));
		panel3.setBorder(colorBorder);
		add(panel3);
		
		MenuTButton dotToggleButton = new MenuTButton("Dot","dott",true);
		objModeGroup.add(dotToggleButton);
		panel1.add(dotToggleButton);
		
		MenuTButton circleButton = new MenuTButton("Circle","circle",false);
		objModeGroup.add(circleButton);
		panel1.add(circleButton);

		MenuTButton rectToggleButton = new MenuTButton("Rect","rect",false);
		objModeGroup.add(rectToggleButton);
		panel1.add(rectToggleButton);
		
		MenuTButton lineToggleButton = new MenuTButton("Line","line",false);
		objModeGroup.add(lineToggleButton);
		panel1.add(lineToggleButton);
		
		MenuIconBtn clearButton = new MenuIconBtn("\uf12d", "clear");
		clearButton.addActionListener(this);
		panel2.add(clearButton);

		MenuIconBtn loadButton = new MenuIconBtn("\uf07c","load");
		loadButton.addActionListener(this);
		panel2.add(loadButton);

		MenuIconBtn saveButton = new MenuIconBtn("\uf0c7","save");
		saveButton.addActionListener(this);
		panel2.add(saveButton);

		MenuIconBtn closeButton = new MenuIconBtn("\uf057","close");
		closeButton.addActionListener(this);
		panel2.add(closeButton);

		MenuIconBtn colorButton = new MenuIconBtn("\uf53f","color");
		colorButton.addActionListener(this);
		colorButton.setPreferredSize(new Dimension(50,50));
		panel3.add(colorButton);

		fillButton.addActionListener(this);
		fillButton.setPreferredSize(new Dimension(50,50));
		panel3.add(fillButton);
		
		paint4.setStatus("Ready.");
	}
	
	
	@Override public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "undo":
				paint4.undo();
				break;
				
			case "redo":
				paint4.redo();
				break;
				
			case "color":
				selectColor = JColorChooser.showDialog(this,"Color Selector", Color.black);
				break;
			
			case "fill":
				if(fillStatus == true) {
					fillButton.setText("\uf5c7");
					fillStatus = !fillStatus;
					paint4.setStatus("False");
				} else {
					fillButton.setText("\uf043");
					fillStatus = !fillStatus;
					paint4.setStatus("True");
				}
				break;
				
			case "clear": 
				paint4.clear();
				break;
				
			case "load":
				if(file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					paint4.load(file.getSelectedFile().getPath());
					paint4.setStatus(file.getSelectedFile().getPath());
				} else {
					paint4.setStatus("canceled or error");
				}
				break;
				
			case "save":
				if(file.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					paint4.save(file.getSelectedFile().getPath());
				} else {
					paint4.setStatus("canceled or error");
				}
				break;
				
			case "close":
				System.exit(0);
				break;
			
			case "test":
				paint4.setStatus("test");
				break;
				
			default:
				paint4.setStatus("notset ActionCommand");
				break;
		}
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
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

