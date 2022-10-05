package enshu3_2022;

public class kuku_oct {
	public static void main(String[] args) {
		int y = 1;

		header(16,3);
		while(y < 16) {
			
			printOct(y,3); // print NUM
			System.out.print( "| ");
			
			for(int x = 1; x < 16; x++) { // <--- MAIN
				printOct(x * y,3);
			}
			System.out.println();
			y++;
		}
	}

	/*--- method ---*/
	public static void printOct0(int v) {
		if(v < 10) System.out.print(v);
	}

	public static void printOct(int v) {
		System.out.print(v / 8);
		System.out.print(v % 8);
		System.out.print(" ");
	}

	public static void printOct(int v,int n) {
		int x = 1, w;

		for(int i = 1;i < n; i++)	x = x * 8;
		for(int i = 0;i < n; i++) {
			//System.out.println("[x = " + x + "]"); // DEBUG
			w = v / x;
			v = v % x;
			printOct0(w);
			x = x / 8;
		}
		System.out.print(" ");
	}

	public static void header(int y,int n) {
		for(int i = 0;i < n;i++)	System.out.print(" "); //add space to head
		System.out.print(" | ");
		
		for(int i = 1; i < y; i++)	printOct(i,n); //print NUM
		System.out.println();
		
		for(int i = 0; i < (y * (n+1) + 1); i++)	System.out.print("-"); //print "-"
		System.out.println();
	}
}
