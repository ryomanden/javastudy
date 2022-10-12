package enshu4_2022;

public class CitStudent2 {
	int no;
	String name;
	int year;
	int grade;
	int department;

	int high, low, total, num;
	double ave;

	public CitStudent2() {
		no = year = grade = department = high = low = total = num = 0;
		ave = 0.0;
		name = "";
	}

	CitStudent2(int p_no, String p_name){
		no = p_no;
		name = p_name;
		year = grade = department = 0;
	}
	CitStudent2(int p_no, String p_name, int p_year){
		no = p_no;
		name = p_name;
		year = p_year;
		grade = department = 0;
	}

	public void incNum() {
		num += 1;
	}

	public void addScore(int score) {	
	}

	public void setNo(int p_no) {
		no = p_no;
	}

	public void setHigh(int p_high) {
		if(!((high >= ave) && (0 <= high) && (high <= 100))) {
			System.err.println(" ERROR dayo----");
		} else {
			high = p_high;
		}
	}
	public int getNo() {
		return no;
	}
	public int getNum() {
		return num;
	}
}
