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
import javax.swing.ImageIcon;
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
	public static void main(String [] args) {
		//new toolbar();
	}
	
	JButton undoButton = new JButton(new ImageIcon("./img/undo.png"));
	JButton redoButton = new JButton(new ImageIcon("./img/redo.png"));
	JToggleButton dotToggleButton = new JToggleButton("Dot",true);
	JToggleButton circleButton = new JToggleButton("Circle",false);
	JToggleButton rectToggleButton = new JToggleButton("Rect",false);
	JToggleButton lineToggleButton = new JToggleButton("Line",false);
	JButton clearButton = new JButton(new ImageIcon("./img/clear.png"));
	JButton loadButton = new JButton(new ImageIcon("./img/load.png"));
	JButton saveButton = new JButton(new ImageIcon("./img/save.png"));
	JButton closeButton = new JButton(new ImageIcon("./img/exit.png"));
	JButton colorButton = new JButton(new ImageIcon("./img/color.png"));
	JButton fillButton = new JButton(new ImageIcon("./img/fillOff.png"));

	void btnEvent() {
		Dimension btnSize = new Dimension(80,40);
		GridLayout col2Layout = new GridLayout(0,2,5,5);

		undoButton.addActionListener(this);
		//undoButton.setIcon(icon);
		undoButton.setPreferredSize(btnSize);
		undoButton.setActionCommand("undo");
		add(undoButton);
		
		redoButton.addActionListener(this);
		redoButton.setPreferredSize(btnSize);
		redoButton.setActionCommand("redo");
		add(redoButton);
		
		TitledBorder styleBorder = new TitledBorder("ObjectStyle");
		JPanel panel1 = new JPanel();
		panel1.setLayout(col2Layout);
		//panel1.setPreferredSize(new Dimension(182,110));
		panel1.setBorder(styleBorder);
		add(panel1);

		TitledBorder toolBorder = new TitledBorder("Menu");
		JPanel panel2 = new JPanel();
		panel2.setLayout(col2Layout);
		//panel2.setPreferredSize(new Dimension(180,0));
		panel2.setBorder(toolBorder);
		add(panel2);

		TitledBorder colorBorder = new TitledBorder("Color");
		JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(180,100));
		panel3.setBorder(colorBorder);
		add(panel3);
		
		dotToggleButton.setActionCommand("dott");
		dotToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(dotToggleButton);
		panel1.add(dotToggleButton);
		
		circleButton.setActionCommand("circle");
		circleButton.setPreferredSize(btnSize);
		objModeGroup.add(circleButton);
		panel1.add(circleButton);

		rectToggleButton.setActionCommand("rect");
		rectToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(rectToggleButton);
		panel1.add(rectToggleButton);
		
		lineToggleButton.setActionCommand("line");
		lineToggleButton.setPreferredSize(btnSize);
		objModeGroup.add(lineToggleButton);
		panel1.add(lineToggleButton);
		
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(this);
		clearButton.setPreferredSize(btnSize);
		panel2.add(clearButton);

		loadButton.addActionListener(this);
		loadButton.setPreferredSize(btnSize);
		loadButton.setActionCommand("load");
		panel2.add(loadButton);

		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		saveButton.setPreferredSize(btnSize);
		panel2.add(saveButton);

		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);
		closeButton.setPreferredSize(btnSize);
		panel2.add(closeButton);

		colorButton.setActionCommand("color");
		colorButton.addActionListener(this);
		colorButton.setPreferredSize(new Dimension(50,50));
		colorButton.setBackground(getColor());
		panel3.add(colorButton);

		fillButton.setActionCommand("fill");
		fillButton.addActionListener(this);
		fillButton.setPreferredSize(new Dimension(50,50));
		fillButton.setBackground(Color.WHITE);
		//fillButton.setBorder(new LineBorder(Color.RED, 10, false));
		panel3.add(fillButton);

		
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
					fillButton.setIcon(new ImageIcon("./img/fillOff.png"));
					fillStatus = !fillStatus;
					System.out.println(fillStatus);
				} else {
					fillButton.setIcon(new ImageIcon("./img/fillOn.png"));
					fillStatus = !fillStatus;
					System.out.println(fillStatus);
				}
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
	public boolean getFillStatus() {
		return fillStatus;
	}
	public Color getColor() {
		System.out.println(selectColor);//debug
		return this.selectColor;
	}
}

