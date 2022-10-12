package enshu3_2022;

public class kuku_n {
	public static void main(String[] args) {
		
		int posit = Integer.parseInt(args[0]),y = 1;

		header(9,3);
		while(y <= 9) {
			
			printHex2(y,3); // print NUM
			System.out.print( "| ");
			
			for(int x = 1; x <= 9; x++) { // <--- MAIN
				printHex2(x * y,3);
			}
			System.out.println();
			y++;
		}
	}

	/*--- method ---*/
	public static void printHex(int v) {
		if(v < 10) System.out.print(v);
		else	System.out.print((char)('a' + v - 10));
	}

	public static void printHex2(int v) {
		printHex(v / 16);
		printHex(v % 16);
		System.out.print(" ");
	}

	public static void printHex2(int v,int n) {
		int x = 1, w;

		for(int i = 1;i < n; i++)	x = x * 16;
		for(int i = 0;i < n; i++) {
			//System.out.println("[x = " + x + "]"); // DEBUG
			w = v / x;5
			v = v % x;
			printHex(w);
			x = x / 16;
		}
		System.out.print(" ");
	}

	public static void header(int y,int n) {
		for(int i = 0;i < n;i++)	System.out.print(" "); //add space to head
		System.out.print(" | ");
		
		for(int i = 1; i <= y; i++)	printHex2(i,n); //print NUM
		System.out.println();
		
		for(int i = 0; i <= (y * (n + 1) + 4); i++)	System.out.print("-"); //print "-"
		System.out.println();
	}
}
