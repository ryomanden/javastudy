package enshu6_2022;

class ArgsTest6 {
	int no;
	static int count = 0;
	public static void setNo(ArgsTest6 student, int p_no) {
		student.no = p_no;
		student.no++;
		student = new ArgsTest6();
		student.no = 2132777;
	}
	ArgsTest6(){
		count++;
	}
}
