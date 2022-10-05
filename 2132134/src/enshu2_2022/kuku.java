package enshu2_2022;

public class kuku {
	public static void main(String[] args) {
		sub1(10);//nxn で出力
	}
	public static void sub1(int n) {
		for(int a = 0; a < 2; a++) {
			for(int i = 0; i < (n+2); i++) {
				sub(i,n);
				System.out.print(" ");
				sub(i,n);
				System.out.println();
			}
			System.out.println();
		}
	}
	public static void sub(int line,int n) {
		if(line == 0) {
			System.out.print(" |");
			for(int i = 0; i < n; i++) {
				System.out.print("  " + (i+1));
			}
		}
		else if(line == 1) {
			System.out.print("---");
			for(int i = 0; i < n; i++) {				
				System.out.print("---");
			}
		}
		else {
			int y = line - 1;
			if(y < 10) {System.out.print(" ");}
			System.out.print(y + "|");
			for(int x = 1; x < (n+1); x++) {
				
				if(x >= y) {
					if(x < (n+1)) { System.out.print(" ");}
					print(x * y);
				} else {
					System.out.print("   ");
				}
			}
		}
	}
	public static void print(int num) {
		if(num < 10) {
			System.out.print("0" + num);
		} else {
			System.out.print(num);
		}
	}
}
