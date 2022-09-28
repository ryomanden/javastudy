package enshu2_2022;

public class kuku {
	public static void main(String[] args) {
		for(int a = 0; a < 2; a++) {
			for(int i = 0; i < 11; i++) {
				sub(i);
				System.out.print(" ");
				sub(i);
				System.out.println();
			}
			System.out.println();
		}
	}
	public static void sub(int line) {
		if(line == 0) { System.out.print(" | 1  2  3  4  5  6  7  8  9");}
		else if(line == 1) {System.out.print("----------------------------");}
		else {
			int y = line - 1;
			System.out.print(y + "|");
			for(int x = 1; x < 10; x++) {
				
				if(x >= y) {
					print(x * y);
				} else {
					System.out.print("   ");
				}
			}
		}
	}
	public static void print(int num) {
		if(num < 10) {
			System.out.print("0" + num + " ");
		} else {
			System.out.print(num + " ");
		}
	}
}
