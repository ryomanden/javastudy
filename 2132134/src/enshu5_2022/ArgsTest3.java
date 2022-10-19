package enshu5_2022;

public class ArgsTest3 {
	public static void main(String[] args) {
		Character topChar = args[0].charAt(0);
		
		for(int i = 0; i < args.length; i++) {
			System.out.print(args[i].replace(topChar.toString(), "") +" ");
		}
	}
}
