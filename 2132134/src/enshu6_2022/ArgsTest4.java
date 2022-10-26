package enshu6_2022;

public class ArgsTest4 {
	int no;
	public static void main(String[] args) {
		int i = 2132999;
		ArgsTest4 kodai = new ArgsTest4();
		kodai.setNo(i);
		System.out.println("1: no = " +kodai.no+ " i = " +i);
		kodai.setNo(2132999);
		System.out.println("2: no = " +kodai.no+ " i = " +i);
		
	}
	public void setNo(int p_no) {
		no = p_no;
	}
}
