package enshu5_2022;

public class ArgsTsuika2 {
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++) {
			for(int j = (args[i].length() - 1); j >= 0; j--) {
				System.out.print(args[i].charAt(j));
			}
			System.out.println();
		}
	}
}
