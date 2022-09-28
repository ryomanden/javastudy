package enshu2_2022;

public class kukue {
	public static void main(String[] args) {
		
		//int x, y;
		y = 1;
		
		while(y < 10) {
			for(x = 1; x < 10; x++) {
		
				if(x * y < 10) {
					System.out.print(" " + x * y  + " ");
				} else {
					System.out.print(x * y + " ");
				}
			}
			System.out.println();
			y++;
		}
	}
}
