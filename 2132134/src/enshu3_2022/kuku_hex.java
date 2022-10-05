package enshu3_2022;

public class kuku_hex {
	public static void main(String[] args) {
		int x, y, v, w;
		y = 1;

		while(y < 16) {
			for(x = 1; x < 16; x++) {
				v = x * y;

				printHex2(v);
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
		int w;
		w = v / 16;
//		if(w != 0)	printHex(w);
//		else System.out.print(" ");
		printHex(w);
		printHex(v % 16);
		System.out.print(" ");
	}
}
