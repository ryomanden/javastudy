package enshu2_2022;

public class kuku {
	public static void main(String[] args) {
		
		int x, y;
		y = 1;
		
		System.out.println(" | 1  2  3  4  5  6  7  8  9");
		System.out.println("----------------------------");
		for(y = 1; y < 10;y++) {
			System.out.print(y + "|");
			for(x = 1; x < 10; x++) {
				
				if(x >= y) {
					if(x * y < 10) {
						System.out.print(" " + x * y  + " ");
					} else {
						System.out.print(x * y + " ");
					}
					
				} else {
					System.out.print("   ");
				}
			}
			System.out.println();
		}
	}
}
