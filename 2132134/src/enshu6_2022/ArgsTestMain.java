package enshu6_2022;

public class ArgsTestMain {
	public static void main(String[] args) {
		int i = 2132999;
		ArgsTest6 kodai = new ArgsTest6();
		ArgsTest6.setNo(kodai, i);
		System.out.println("1: no = " +kodai.no+ " i = " +i+ " ArgsTest6.count = " +ArgsTest6.count);
		ArgsTest6.setNo(kodai, 2132888);
		System.out.println("2: no = " +kodai.no+ " i = " +i+ " ArgsTest6.count = " +ArgsTest6.count);
		ArgsTest6.setNo(kodai, 2132888);
		System.out.println("3: no = " +kodai.no+ " i = " +i+ " ArgsTest6.count = " +ArgsTest6.count);
	}
}
