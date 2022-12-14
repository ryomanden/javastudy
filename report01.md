---
title:第1回レポート
header:ネットワークプログラミング応用 - 第1回レポート
author:Ryo Mitsuda
---

<div style="text-align:right;font-size:0.8em;color:var(--theme-500);">
情報ネットワーク学科<br>
2132134番<br>
満田 瞭<br>
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
10. 4.で実装した機能を改変し，GUI上で任意の色を指定できるようにする.

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

## 3. 理論

上記課題を実装するにあたり，必要となるクラス・およびインターフェースを調査した．以下はその一覧である．

1. `java.awt.button`クラス．ボタンの生成などを行う．
2. `java.awt.Label`クラス.GUI上にテキストを表示させるために用いる．
3. `java.awt.TextField`クラス．テキスト入力欄の生成や受け取りに用いる．
4. `java.awt.Checkbox`クラス．チェックボックスの生成などを行う．
5. `java.awt.CheckboxGroup`クラス．生成したチェックボックスを追加することで，ラジオボタンとして扱える．
6. `java.awt.event.ActionListener`インタフェース．ボタンの入力を受け取る．
7. `java.awt.event.ItemListener`インタフェース．チェックボックスの入力を受け取る．
8. `java.awt.Color`クラス．色の情報を格納する際などに用いる．

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

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

以下はウィンドウへの描画を行うメインのクラス`PaintReport`である．提示した課題の実装箇所はソースコード内にコメント形式で記述する．なお，特にコメントの記述がない場合は，配布プログラムから変更を加えていない．

```java : PaintReport.java
package report;

import java.awt.*;
import java.awt.event.*;
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
		changesizeCheckbox.setBounds(20, 240, 100, 20); //課題2.モード

		countLabel.setBounds(20, 280, 100, 20); //最大表示数ラベル
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
```

以下は円を生成する`Circle`クラスである．

```java : Circle.java
package report;

import java.awt.*;

public class Circle extends Figure {
	int size;
	Color color;
	
	Circle(int size) { //第二引数が指定されなかった場合，黒で円を生成する．
		this.size = size;
		this.color = new Color(0,0,0);
	}	
	Circle(int size,Color color) { //第二引数が指定された場合，指定された色で円を生成する．
		this.size = size;
		this.color = color;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawOval(x - size/2, y - size/2, size, size);
	}
}
```

以下は四角形を生成する`Rectangle`クラスである．`Circle`クラスを参考に記述した．

```java : Rectangle.java
package report;

import java.awt.*;

public class Rectangle extends Figure {
	int size;
	Color color;
	
	Rectangle(int size) { //第二引数が指定されていない場合，黒で四角形を生成する．
		this.size = size;
		this.color = new Color(0,0,0);
	}	
	Rectangle(int size,Color color) { //第二引数が指定されている場合，指定された色で四角形を生成する．
		this.size = size;
		this.color = color;
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawRect(x - size/2, y - size/2, size, size); //四角形を生成する．
		
	}
}
```

以下は線を生成する`Line`クラスである．`Circle`クラスを参考に記述した．

```java : Line.java
package report;

import java.awt.*;

public class Line extends Figure { //Figureクラスを継承し，Lineクラスを作成する．
	Color color;
	Coord startPoint, endPoint; //線の開始・終了位置を入れるCoord型のインスタンスを作成．
	
	Line(Coord startPoint,Coord endPoint) { //第一引数で線の開始位置，第二引数で線の終了位置を受け取る．
		this.color = new Color(0,0,0); //色を黒に設定．
		this.startPoint = startPoint; //受け取った開始位置を８行目で作成したインスタンスに代入．
		this.endPoint = endPoint; //受け取った終了位置を８行目で作成したインスタンスに代入．
	}
	@Override public void paint(Graphics g) {
		g.setColor(color);
		g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y); //開始位置から終了位置まで線を引く
	}
}
```
以下はポジションに関するクラス`Coord`である．
```java : Coord.java
package report;

public class Coord {
	int x, y;
	Coord(){
		x = y = 0;
	}
	Coord(int x , int y){ //引数で座標x,yを受け取り，ローカル変数に代入する
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

以下は`Figure`クラスである．配布プログラムから変更は加えていない．
```java : Figure.java
package report;

import java.awt.Graphics;

public class Figure extends Coord {
	int color;
	Figure(){
		color = 0;
	}
	public void paint(Graphics g) {
	}
}
```

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

## 5. 動作検証，および実行環境

動作の検証にあたり，下記条件の通り動作する場合にのみ正常動作とみなす．

1. アプリケーションを起動し，何も変更しない状態で図形が30個以上描画されない．また，図形を２個以上描画し，図形の間に線が描かれる．
2. [`Change size`]チェックボックスを選択し，図形が10px，50pxと交互に切り替わる．
3. \[`R`]\[`G`]\[`B`]に`0~255`の適当な数値を入力し，\[`apply`]ボタンを押した後に描画した図形の色が変わる．なお，３つの値が0に近いほど黒に近くなるため，入力する値が小さいと変化がわかりにくい場合があるが，正常である．
4. 線を除く，現在描画されている図形の数が*`ObjectCount is 図形の数`*の形式でGUI上に表示される．
5. \[`Clear`]ボタンを押し，描画されていた全ての図形が削除される．
6. \[`MaxObjectCoun`t]に１以上の適当な数値を入力し`\[Apply`]ボタンを押すと，描かれる図形の最大数が指定した数以上描画されない．
7. \[`Static size`]チェックボックを選択し\[`+`]\[`-`]ボタンを押した際，図形の大きさが10pxずつ増減する．また，現在のサイズが*`Size 図形の大きさ`*の形式で表示される．なお，図形の大きさは常に0pxを下回らない．
8. \[`Rectangle`]チェックボックスを選択し，四角形が描画される．また，上記全ての条件で円と同様に描画される．


本プログラムの制作，および動作の検証は以下の環境で行なった．

| Device | Macbook Air |
| ---: | :--- |
| OS   | MacOS Ventura Version 13.0.1 |
| CPU  | Apple M1 |
| Memory | 16GB |
| Java version | Java17 |
| eclipse version | 2022-09 (4.25.0) |

## 6.実行結果

### 検証1.

|  | |
| :---: | :---: |
| ![](./screenshot/Screenshot 2022-11-21 at 23.53.41.png)  <div style="font-size:0.8rem"><i>図１：図形を３０個描画．</i></div> | ![](./screenshot/Screenshot 2022-11-21 at 23.53.47.png)<div style="font-size:0.8rem"><i>図２：図形を３１個描画．</i></div> |
|  |  |

図１，図２のように，値に変更を加えず実行した場合には３０個を超えて図形が描画されることはない．

### 検証2.

| |
| :---:|
| <img src="./screenshot/Screenshot 2022-11-22 at 0.24.06.png" style="width:50%;" /><div style="font-size:0.8rem"><i>図３；[Change Size]を選択し，実行．</i></div> |
|  |

図３のように，\[Change Size]を選択し実行すると，10px・50pxと交互にサイズを変えて描画された．

### 検証3.

| | |
| :---:| :---:|
| ![](./screenshot/Screenshot 2022-11-22 at 0.31.39.png) <div style="font-size:0.8rem"><i>図４：Rを`255`で実行．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 0.31.52.png)<div style="font-size:0.8rem"><i>図５：Gを`255`で実行．</i></div> |
| ![](./screenshot/Screenshot 2022-11-22 at 0.32.03.png)<div style="font-size:0.8rem"><i>図６：Bを`255`で実行．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 0.33.06.png)<div style="font-size:0.8rem"><i>図７：Rを`255`，Gを`200`，Bを`0`で実行</i></div> |
| | |

図４，図５，図６，図７ののように，赤，緑，青をそれぞれ256段階で調整できた．

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

### 検証4.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](./screenshot/Screenshot 2022-11-22 at 0.51.41.png)<div style="font-size:0.8rem"><i>図８：図形を何も描画しない状態．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 0.51.45.png)<div style="font-size:0.8rem"><i>図９：図形を３つ描画．</i></div> |
|                                                              |                                                              |

図８，図９のように，`ObjectCount`の数値が描画されている図形に応じて変わった．

### 検証5.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](./screenshot/Screenshot 2022-11-22 at 0.58.56.png)<div style="font-size:0.8rem"><i>図１０：図形を複数個描画した．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 0.59.01.png)<div style="font-size:0.8rem"><i>図１１：\[Clear]を押した．</i></div> |
|                                                              |                                                              |

図１０，図１１のように，\[Clear]を押すことで描画されている図形を削除できた．

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

### 検証6.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](./screenshot/Screenshot 2022-11-22 at 1.02.24.png)<div style="font-size:0.8rem"><i>図１２：最大描画数を`15`に設定し，図形を15個描画．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 1.02.29.png)<div style="font-size:0.8rem"><i>図１３：同設定で図形を16個描画．</i></div> |
|                                                              |                                                              |

図１２，図１３のように，`MaxObjectCount`で指定した描画数を超えて描画されることはない．

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

### 検証7.

|                                                              |                                                              |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](./screenshot/Screenshot 2022-11-22 at 1.07.15.png)<div style="font-size:0.8rem"><i>図１４：大きさ50pxで実行．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 1.07.25.png)<div style="font-size:0.8rem"><i>図１５：大きさ20pxで実行．</i></div> |
| ![](./screenshot/Screenshot 2022-11-22 at 1.07.39.png)<div style="font-size:0.8rem"><i>図１６：大きさ80pxで実行．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 1.07.48.png)<div style="font-size:0.8rem"><i>図１７：大きさ0pxで実行．</i></div> |
|                                                              |                                                              |

図１４，図１５，図１６，図１７のように，指定した大きさで図形が描画される．また，0pxでは図形は何も描画されない．

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

### 検証8.

| | |
| :---: | :---: |
| ![](./screenshot/Screenshot 2022-11-22 at 0.46.49.png)<div style="font-size:0.8rem"><i>図１８：\[Rectangle]を選択し図形を描画．</i></div> | ![](./screenshot/Screenshot 2022-11-22 at 1.43.04.png)<div style="font-size:0.8rem"><i>図１９：\[Change Size]で図形を６個描画->10pxで６個描画</i></div> |
| ![](./screenshot/Screenshot 2022-11-22 at 1.43.49.png)<div style="font-size:0.8rem"><i>図２０：Rを`255`に設定し，大きさを50px->0px->Change Sizeと変更し描画．</i></div> | |
| | |

図１８のように，\[Rectangle]を選択することで四角形を描画できた．
図１９，図２０のように，\[Circle]選択時と\[Rectangle]選択時とでは図形の形以外に動作の違いはなかった．

<div style="page-break-after:always;color:lightgray" class="no-print">─────改ページ─────</div>

## 7.まとめ

インスタンス・クラス・メソッドなどのオブジェクト指向言語特有の記述を用いてプログラムの制作を行い，その特徴を学んだ．
目標とするアプリケーションの制作には成功したものの，オブジェクト指向言語の特徴を活かした最も効率的なプログラムを制作することは今後の課題であると感じる．

## 8. 感想

ボタン等を実装するにあたり調査したところ，awt・swing・JavaFXといったGUIに関するフレームワークがあることが分かった．awt以外のフレームワークでは今回のプログラムとの相性が悪いようだったため，swingとJavaFXの使用は見送ったが，実際にJavaでアプリケーションを構築する場合にはawtでは不十分であると実感した．アプリケーションを構築する際，フレームワーク同士の相性なども考慮し，事前に目標の動作とそれに必要なライブラリを検討しておかなければ，開発途中でフレームワークを選びなおさなくてはいけなくなる場合があると分かった．
