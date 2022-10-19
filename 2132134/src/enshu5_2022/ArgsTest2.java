package enshu5_2022;

public class ArgsTest2 {
	public static void main(String[] args) {
		String topArg = "";
		if(args.length >= 1) topArg = args[0];

		for(int i = 0; i < topArg.length(); i++) {
			System.out.print("[" +topArg.charAt(i)+ "]");
		}




		System.out.println();
		for(int i = 0; i < args.length; i++) {
			System.out.print("arg " +i+ " : " +args[i]);

			if(args[i].equals(topArg)) {
				System.out.println("  same");
			} else {
				System.out.println("  diff");
			}

		}
	}
}
