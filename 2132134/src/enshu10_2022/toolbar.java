package enshu10_2022;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class toolbar extends JFrame implements MouseListener, ActionListener{
	
	private ButtonGroup objModeGroup = new ButtonGroup();
	private JFileChooser file = new JFileChooser( "." );
	
	private Color selectColor = Color.black;
	
	Paint4 paint4 = null;
	
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
	public static void main(String [] args) {
		//new toolbar();
	}
	
	void btnEvent() {
		Dimension btnSize = new Dimension(80,30);

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(this);
		undoButton.setPreferredSize(btnSize);
		undoButton.setActionCommand("undo");
		add(undoButton);
		
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(this);
		redoButton.setPreferredSize(btnSize);
		redoButton.setActionCommand("redo");
		add(redoButton);
		
		TitledBorder styleBorder = new TitledBorder("ObjectStyle");
		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(180,100));
		panel1.setBorder(styleBorder);
		add(panel1);

		TitledBorder toolBorder = new TitledBorder("");
		JPanel panel2 = new JPanel();
		panel2.setPreferredSize(new Dimension(180,120));
		panel2.setBorder(toolBorder);
		add(panel2);
		
		JToggleButton dotToggleButton = new JToggleButton("Dot",true);
		dotToggleButton.setActionCommand("dott");
		dotToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(dotToggleButton);
		panel1.add(dotToggleButton);
		
		JToggleButton circleButton = new JToggleButton("Circle",false);
		circleButton.setActionCommand("circle");
		circleButton.setPreferredSize(btnSize);
		objModeGroup.add(circleButton);
		panel1.add(circleButton);

		JToggleButton rectToggleButton = new JToggleButton("Rect",false);
		rectToggleButton.setActionCommand("rect");
		rectToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(rectToggleButton);
		panel1.add(rectToggleButton);
		
		JToggleButton lineToggleButton = new JToggleButton("Line",false);
		lineToggleButton.setActionCommand("line");
		lineToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(lineToggleButton);
		panel1.add(lineToggleButton);
		
		JButton colorButton = new JButton("Color");
		colorButton.setActionCommand("color");
		colorButton.addActionListener(this);
		colorButton.setPreferredSize(btnSize);
		panel2.add(colorButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(this);
		clearButton.setPreferredSize(btnSize);
		panel2.add(clearButton);

		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		loadButton.setPreferredSize(btnSize);
		loadButton.setActionCommand("load");
		panel2.add(loadButton);

		JButton saveButton = new JButton("Save");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		saveButton.setPreferredSize(btnSize);
		panel2.add(saveButton);

		JButton closeButton = new JButton("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		closeButton.setPreferredSize(btnSize);
		panel2.add(closeButton);
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
				
			case "clear": 
				paint4.clear();
				break;
				
			case "load":
				if(file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					paint4.load(file.getSelectedFile().getPath());
					System.out.println(file.getSelectedFile().getPath());
				} else {
					System.out.println("canceled or error");					
				}
				break;
				
			case "save":
				if(file.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					paint4.save(file.getSelectedFile().getPath());
				} else {
					System.out.println("canceled or error");										
				}
				break;
				
			case "close":
				System.exit(0);
				break;
				
			default:
				System.out.println("notset ActionCommand");
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
	public Color getColor() {
		System.out.println(selectColor);//debug
		return this.selectColor;
	}
}

