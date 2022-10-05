package enshu3_2022;

public class kuku_hex {
	public static void main(String[] args) {
		int x, y, v, w;
		y = 1;
		
		while(y < 16) {
			for(x = 1; x < 16; x++) {
				v = x * y;
				
				if(v < 16) {
					System.out.print(" ");
					printHex(v);
					System.out.print(" ");
			} else {
					w = v / 16;
					v = v % 16;
					
					printHex(w);
					printHex(v);
					System.out.print(" ");
				}
			}
			System.out.println();
			y++;
		}
	}
	public static void printHex(int v) {
		if(v < 10) System.out.print(v);
		else	System.out.print((char)('a' + v - 10));
	}
}
