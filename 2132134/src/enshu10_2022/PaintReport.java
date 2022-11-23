package enshu10_2022;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener {
	int x, y, objSize = 30; //図形の初期サイズを変数objSizeにて定義する．
	boolean sizeChange = false; //課題2.で実装した機能と任意のサイズを切り替えるため，GUIの指定状況を格納する変数．trueは課題2.を表す．
	boolean sizeBig = false; //falseを10px，trueを50pxとし，サイズを交互に変更する際に利用する変数．
	boolean objRect = false; //図形を切り替える変数．

	ArrayList<Figure> objList;
	ArrayList<Figure> lineList; //図形間に引いた線の情報を格納する．
	Figure obj;
	
	/* GUI上に表示するボタン・テキスト・フォームなどを宣言する. */
	static Button clearButton = new Button("Clear"); //クリアボタン
	static Button sizeincButton = new Button("+"); //プラスボタン
	static Button sizedecButton = new Button("-"); //マイナスボタン
	static Button applyButton = new Button("Apply"); //変更適応ボタン
	static Label objcountLabel = new Label(); //図形数表示ラベル
	static Label sizeLabel = new Label(); //図形サイズラベル
	static Label countLabel = new Label("MaxObjectCount"); //最大表示数のラベル
	static Label redLabel = new Label("R :"); //赤色入力フォームのラベル
	static Label greenLabel = new Label("G :"); //緑色入力フォームのラベル
	static Label blueLabel = new Label("B :"); //青色入力フォームのラベル
	static TextField objcountField = new TextField(); //最大表示数入力フォーム
	static TextField redField = new TextField(); //赤色入力フォーム
	static TextField greenField = new TextField(); //緑色入力フォーム
	static TextField blueField = new TextField(); //青色入力フォーム
	static CheckboxGroup cbg = new CheckboxGroup(); //サイズ変更モードを切り替えるチェックボックスのグループ
	static CheckboxGroup cbg2 = new CheckboxGroup(); //図形を切り替えるチェックボックスのグループ
	static Checkbox staticsizeCheckbox = new Checkbox("Static size",cbg, true); //サイズ指定モードを有効にするチェックボックス
	static Checkbox changesizeCheckbox = new Checkbox("Change size",cbg, false); //課題2.モードを有効にするチェックボックス
	static Checkbox circleCheckbox = new Checkbox("Circle",cbg2, true); //円モードを有効にするチェックボックス
	static Checkbox rectCheckbox = new Checkbox("Rectangle",cbg2, false); //四角形モードを有効にするチェックボックス

	static int drawCnt = 30; //最大図形表示数を指定する変数．
	static Color color = new Color(0,0,0); //図形色を指定するColorインスタンス変数．
	
	public static void main(String[] args) {
		PaintReport f = new PaintReport();
		f.setSize(1280,720);
		f.setLayout(null);
		f.setTitle("Report Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		
		/* ボタンなどのインターフェースを配置する座標を設定する． */
		clearButton.setBounds(20, 40, 80, 30); //クリアボタン
		objcountLabel.setBounds(20, 70, 120, 30); //図形数表示ラベル

		sizeincButton.setBounds(20, 120, 50, 20); //プラスボタン
		sizeLabel.setBounds(20, 145, 120, 30); //図形サイズラベル
		sizedecButton.setBounds(20, 180, 50, 20); //マイナスボタン

		staticsizeCheckbox.setBounds(20, 210, 100, 20); //サイズ指定モード
		changesizeCheckbox.setBounds(20, 240, 150, 20); //課題2.モード

		countLabel.setBounds(20, 280, 150, 20); //最大表示数ラベル
		objcountField.setBounds(20, 310, 50, 20); //最大表示数フォーム
		redLabel.setBounds(20, 340, 20, 20); //Redラベル
		redField.setBounds(40, 340, 30, 20); //Red入力フォーム
		greenLabel.setBounds(80, 340, 20, 20); //Greenラベル
		greenField.setBounds(100, 340, 30, 20); //Greenフォーム
		blueLabel.setBounds(140, 340, 20, 20); //Blueラベル
		blueField.setBounds(160, 340, 30, 20); //Blueフォーム
		applyButton.setBounds(20, 370, 70, 20); //変更適応ボタン
		
		circleCheckbox.setBounds(20, 400, 100, 20); //円モード
		rectCheckbox.setBounds(20, 430, 100, 20); //四角形モード
		
		/* ボタンなどのインターフェースをウィンドウに追加する */
		f.add(clearButton);
		f.add(objcountLabel);
		f.add(sizeincButton);
		f.add(sizeLabel);
		f.add(sizedecButton);
		f.add(countLabel);
		f.add(objcountField);
		f.add(applyButton);
		f.add(staticsizeCheckbox);
		f.add(changesizeCheckbox);
		f.add(redLabel);
		f.add(redField);
		f.add(greenLabel);
		f.add(greenField);
		f.add(blueLabel);
		f.add(blueField);
		f.add(circleCheckbox);
		f.add(rectCheckbox);

		f.btnEvent(); //ボタン入力に応じた処理を行うメソッドを呼び出す．
		f.checkEvent(); //チェックボックスに応じた処理を行うメソッドを呼び出す
		f.labelUpdate(); //ラベルの内容を変更するメソッドを呼び出す．

		objcountField.setText(Integer.valueOf(drawCnt).toString()); //最大表示数を変数の初期値に設定する．入力フォームはString型のみ対応なので，String->int変換も行う．
		
		/* RGBそれぞれの入力フォームを0で初期化する． */
		redField.setText("0"); 
		greenField.setText("0");
		blueField.setText("0");

		f.setVisible(true);
	}
	
	/* ボタンの入力に応じた処理を定義するメソッド． */
	void btnEvent() {
		clearButton.addActionListener(new ActionListener() { //Clearボタンの処理．
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear(); //objList内の図形を消す．
				lineList.clear(); //lineList内の線を消す．
				labelUpdate(); //ラベルの更新を行う．
				repaint(); //図形を再描画する（何も表示しない）．
			}
		});
		sizeincButton.addActionListener(new ActionListener() { //プラスボタンの処理．
			@Override public void actionPerformed(ActionEvent e) {
				objSize += 10; //objSize変数に10を足す．
				labelUpdate(); //ラベルの更新を行う．
			}
		});
		sizedecButton.addActionListener(new ActionListener() { //マイナスボタンの処理．
			@Override public void actionPerformed(ActionEvent e) {
				if(objSize > 0) { //objSizeがマイナスになった場合は処理を行わない．
					objSize -= 10; //objSize変数を10減らす．
				}
				labelUpdate(); //ラベルの更新を行う．
			}
		});
		applyButton.addActionListener(new ActionListener() { //適応ボタンの処理．
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear(); //最大描画数が変わった場合，一度描画された図形は表示され続けてしまうため，一度すべての図形を消す．
				lineList.clear(); //上に同じ
				fieldUpdate(); //入力フォームの変更を取得する．
				labelUpdate(); //ラベルの更新を行う．
				repaint();
			}
		});
	}
	
	void checkEvent() {
		staticsizeCheckbox.addItemListener(new ItemListener() {  //サイズ指定モードの処理，
			public void itemStateChanged(ItemEvent e) {               
				sizeChange = false; //課題2.モードではない事を表すため，falseを代入する．
			}  
		});  
		changesizeCheckbox.addItemListener(new ItemListener() {  //課題2.モードの処理．
			public void itemStateChanged(ItemEvent e) {               
				sizeChange = true; //課題2.モードを表すtrueを代入する．
			}  
		});
		circleCheckbox.addItemListener(new ItemListener() {  //円モードの処理，
			public void itemStateChanged(ItemEvent e) {               
				objRect = false; //円を表すfalseを代入する．
			}  
		});  
		rectCheckbox.addItemListener(new ItemListener() {  //四角形モードの処理．
			public void itemStateChanged(ItemEvent e) {               
				objRect = true; //四角形を表すtrueを代入する．
			}  
		});
		
	}
	
	/* ラベルの内容を変更するメソッド */
	void labelUpdate() {
		objcountLabel.setText("ObjectCount is " +objList.size()); //objListの長さを取得し，図形数としてラベルに表示する．
		sizeLabel.setText("Size " +objSize+ "px"); //現在指定されているサイズをobjSize変数で取得し，ラベルに表示する．
	}
	
	/* 入力フォームの内容を取得するメソッド */
	void fieldUpdate() {
		int in = Integer.parseInt(objcountField.getText()); //最大表示数フォームを取得し，int型に変換する．
		if(0 < in)drawCnt = in; //入力された数が0より大きければ，最大表示数として設定する．
		
		int Red = Integer.parseInt(redField.getText()); //赤色フォームを取得し，int型に変換する．
		int Green = Integer.parseInt(greenField.getText()); //上に同じ．
		int Blue = Integer.parseInt(blueField.getText()); //上に同じ．
		if(!(Red < 0 || 255 < Red || Green < 0 || 255 < Green || Blue < 0 || 255 < Blue)) {
			color = new Color(Red, Green, Blue); //RGBに入力された数が 0 <= RGB <= 255 を満たしていれば，colorに代入する．
		}
	}
	
	PaintReport(){
		objList = new ArrayList<Figure>();
		lineList = new ArrayList<Figure>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	@Override public void paint(Graphics g) {
		Figure f;
		for(int i = 0; i < objList.size(); i++) {
			f = objList.get(i);
			f.paint(g);
		}
		
		for(int i = 0; i < lineList.size(); i++) { //lineListに保存された線を描画する．
			f = lineList.get(i);
			f.paint(g);
		}
		if(obj != null) obj.paint(g);
	}
	@Override public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		/* GUIで選択されたサイズ変更モードに応じた処理を行う */
		if(sizeChange == true) { //サイズ指定モードが選択されている時の処理．
			if (sizeBig == false) { //sizeBigがfalseのとき，大きさを10pxにしてsizeBigを反転する．
				objSize = 10;
				sizeBig = !sizeBig;
			} else { //sizeBigがtrueのとき，大きさを50pxにしてsizeBigを反転する．
				objSize = 50;
				sizeBig = !sizeBig;
			}
		}
		
		if(objRect == true) { //objRectがtrueのとき，四角形で図形を描画する．
			obj = new Rectangle(objSize,color); //描画の際，GUIで指定されたobjSizeとcolorを用いる．
		} else { //objRectがfalseのとき，円で図形を描画する
			obj = new Circle(objSize,color); //描画の際，GUIで指定されたobjSizeとcolorを用いる．
		}

		obj.moveto(x, y);
		repaint();
	}
	@Override public void mouseReleased(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		obj.moveto(x,y);
		objList.add(obj);
		obj = null;
		if(objList.size() > drawCnt) { //描画されている図形が指定された数を超えた際の処理．
			objList.remove(0); //リストの先頭の図形を削除．
			lineList.remove(0); //リストの先頭の線を削除．
		}

		if(objList.size() >= 2) { //図形が二つ以上ある時の処理
			obj = new Line(objList.get(objList.size()-2),objList.get(objList.size()-1));// 前に追加された図と今追加された図の座標の間に線を引く．
			lineList.add(obj);
			obj = null;
		}

		System.out.println(objList.size()); //コンソールに描画されている図形の数を表示．
		labelUpdate(); //ラベルをアップデート．
		repaint();
	}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if(obj != null)obj.moveto(x,y);
		repaint();
	}
	@Override public void mouseMoved(MouseEvent e) {}

}
