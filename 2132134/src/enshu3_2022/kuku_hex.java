package enshu3_2022;

public class kuku_hex {
	public static void main(String[] args) {

		int y = 1;

		header(16);
		while(y < 16) {
			printHex3(y,2);
			System.out.print( "| ");
			for(int x = 1; x < 16; x++) {
				printHex3(x * y,2);
			}
			System.out.println();
			y++;
		}
	}

	public static void printHex(int v) {
		if(v < 10) System.out.print(v);
		else	System.out.print((char)('a' + v - 10));
	}

	public static void printHex2(int v) {
		printHex(v / 16);
		printHex(v % 16);
		System.out.print(" ");
	}

	public static void printHex3(int v,int n) {
		int x = 1, w;

		for(int i = 1;i < n; i++)	x = x * 16;
		for(int i = 0;i < n; i++) {
			w = v / x;
			v = v % x;
			printHex(w);
			x = x / 16;
		}
		System.out.print(" ");
	}

	public static void header(int y) {
		System.out.print("   | ");
		for(int i = 1; i < y; i++)	printHex2(i);
		System.out.println();
		for(int i = 0; i < (y * 3 + 1); i++)	System.out.print("-");
		System.out.println();
	}
}
