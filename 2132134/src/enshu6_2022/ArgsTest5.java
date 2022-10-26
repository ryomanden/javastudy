package enshu6_2022;

public class ArgsTest5 {
	int no;
	public static void main(String[] args) {
		int i = 2132999;
		ArgsTest5 kodai = new ArgsTest5();
		setNo(kodai, i);
		System.out.println("1: no = " +kodai.no+ " i = " +i);
		setNo(kodai, 2132999);
		System.out.println("2: no = " +kodai.no+ " i = " +i);
	}
	public static void setNo(ArgsTest5 student, int no) {
		student.no = no;
		student.no++;
		student = new ArgsTest5();
		student.no  = 2132666;
	}
}
