package enshu2_2022;

public class kuku2 {
	public static void main(String[] args) {
		
		System.out.println(" | 1  2  3  4  5  6  7  8  9");
		System.out.println("----------------------------");
		for(int y = 1; y < 10;y++) {
			System.out.print(y + "|");
			sub(y);
			System.out.println();
		}
	}
	
	public static void sub(int z) {
		for(int x = 1; x < 10; x++) {
			

			if(x * z < 10) {
				System.out.print(" " + x * z  + " ");
			} else {
				System.out.print(x * z + " ");
			}
		}
		z = 0;
	}
}
