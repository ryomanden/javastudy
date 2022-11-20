---
title:第1回レポート
header:
author:Ryo Mitsuda
---

<div style="text-align:right;font-size:0.8em;color:var(--theme-400);">
満田 瞭<br>
2132134番<br>
11月19日
</div>


# 第1回レポート
## 1. 目的
ペイントプログラムへの機能追加を行い，クラス・インスタンス・メソッドなどのJavaおよびオブジェクト指向言語の特徴を理解する．

## 2. 課題
本レポートでは，配布されたプログラムを改変し機能拡充を図る．
実装する課題は以下の通りである．

1. 画面上に描画される円の数を30個に制限する．
2. 円を描画するたびに，円の直径を10pixel，50pixel と交互に変化させる．
3. 円に適当な色を付ける．
4. 2個目以降の円描画時に，直前に描画した円との中心間に線を引く．
5. 四角形を描画できるようにする．
6. 現在描画されている円および四角形の数をGUI上に表示する．
7. 現在描画されている図形をすべて削除するボタンを追加する．
8. 2.で実装した機能を改変し，GUI上で任意の描画数を指定できるようにする．
9. 3.で実装した機能を改変し，GUI上で任意の描画サイズを指定できるようにする．
10. 4.で実装した機能を改変し，GUI上で任意の色を選択できるようにする.

## 3. 理論
<!--- ここに理論 --->

## 4. プログラム
パッケージ内の構成，および実行に係るファイルの階層は以下の通りである．

```Shell : Tree
report
├── Circle.java
├── Coord.java
├── Figure.java
├── Line.java
├── PaintReport.java
└── Rectangle.java
```

ウィンドウへの描画を行うメインのクラス`PaintReport`を以下に示す．提示した課題の実装箇所はソースコード内にコメント形式で記述する．なお，特にコメントの記述がない場合は，配布プログラムから変更を加えていない．

```java : PaintReport.java
package report;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintReport extends Frame implements MouseListener, MouseMotionListener {
	int x, y, objSize = 30; //描画する図形の初期サイズをobjSize変数にて定義する．
	boolean sizeChange = false;
	boolean sizeBig = false;
	ArrayList<Figure> objList;
	ArrayList<Figure> lineList;
	Figure obj;
	
	static Button clearButton = new Button("Clear");
	static Button sizeincButton = new Button("+");
	static Button sizedecButton = new Button("-");
	static Button applyButton = new Button("Apply");
	static Label objcountLabel = new Label();
	static Label sizeLabel = new Label();
	static Label countLabel = new Label("MaxObjectCount");
	static TextField objcountField = new TextField();
	static CheckboxGroup cbg = new CheckboxGroup();
	static Checkbox staticsizeCheckbox = new Checkbox("Static size",cbg, true);
	static Checkbox changesizeCheckbox = new Checkbox("Change size",cbg, false);
	
	static int drawCnt = 30;
	public static void main(String[] args) {
		if(args.length > 0) drawCnt = Integer.parseInt(args[0]);
		PaintReport f = new PaintReport();
		f.setSize(1280,720);
		f.setLayout(null);
		f.setTitle("Report Sample");
		f.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		clearButton.setBounds(20, 40, 80, 30);
		objcountLabel.setBounds(20, 70, 120, 30);
		
		sizeincButton.setBounds(20, 120, 50, 20);
		sizeLabel.setBounds(20, 145, 120, 30);
		sizedecButton.setBounds(20, 180, 50, 20);
		
		staticsizeCheckbox.setBounds(20, 210, 100, 20);
		changesizeCheckbox.setBounds(20, 240, 100, 20);
		
		countLabel.setBounds(20, 280, 100, 20);
		objcountField.setBounds(20, 310, 50, 20);
		applyButton.setBounds(20, 340, 70, 20);

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
		f.btnEvent();
		f.labelUpdate();
		objcountField.setText(Integer.valueOf(drawCnt).toString());
		f.setVisible(true);
	}
	void btnEvent() {
		clearButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear();
				lineList.clear();
				labelUpdate();
				repaint();
			}
		});
		sizeincButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objSize += 10;
				labelUpdate();
			}
		});
		sizedecButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(objSize > 0) {
					objSize -= 10;					
				}
				labelUpdate();
			}
		});
		applyButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				objList.clear();
				lineList.clear();
				fieldUpdate();
				labelUpdate();
				repaint();
			}
		});
		
        staticsizeCheckbox.addItemListener(new ItemListener() {  
            public void itemStateChanged(ItemEvent e) {               
            	sizeChange = false;
            }  
         });  
        changesizeCheckbox.addItemListener(new ItemListener() {  
            public void itemStateChanged(ItemEvent e) {               
               sizeChange = true;
            }  
         });
	}
	void labelUpdate() {
		objcountLabel.setText("ObjectCount is " +objList.size());
		sizeLabel.setText("Size " +objSize+ "px");
	}
	void fieldUpdate() {
		String drawMax = objcountField.getText();
		drawCnt = Integer.parseInt(drawMax);
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
		for(int i = 0; i < lineList.size(); i++) {

			f = lineList.get(i);
			f.paint(g);
		}
		if(obj != null) obj.paint(g);
	}
	@Override public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		
		if(sizeChange == false) {
			obj = new Circle(objSize,new Color(255,0,0));
		} else {
			if (sizeBig == false) {
				obj = new Circle(10,new Color(255,0,0));
				sizeBig = !sizeBig;
			} else {
				obj = new Circle(50,new Color(255,0,0));
				sizeBig = !sizeBig;
			}
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
		if(objList.size() > drawCnt) {
			objList.remove(0);
			lineList.remove(0);// 引数で指定された数を超えた分remove
		}
		
		if(objList.size()-1 > 0) {
			obj = new Line(objList.get(objList.size()-2),objList.get(objList.size()-1));
			lineList.add(obj);
			obj = null;
		}
		
		System.out.println(objList.size());
		labelpdate();
		System.out.println();
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
```



## 5. 動作検証

## 6. 感想

ボタン等を実装するのにあたり調査したところ，awt・swing・JavaFXといったGUIに関するフレームワークがあることが分かった．awt以外のフレームワークでは今回のプログラムとの相性が悪いようだったため，swingとJavaFXの使用は見送ったが，実際にJavaでアプリケーションを構築する場合にはawtでは不十分であると実感した．アプリケーションを構築する際，フレームワーク同士の相性なども考慮し，事前に目標の動作とそれに必要なライブラリを検討しておかなければ，開発途中でフレームワークを選びなおさなくてはいけなくなることがあると分かった．
