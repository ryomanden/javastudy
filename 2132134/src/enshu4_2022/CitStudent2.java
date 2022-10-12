package enshu4_2022;

public class CitStudent2 {
	int no;
	String name;
	int year;
	int grade;
	
	public void setNo(int p_no) {
		no = p_no;
	}
	public void setName(String p_name) {
		name = p_name;
	}
	public void setYear(int p_year) {
		year = p_year;
	}
	public void setGrade(int p_grade) {
		grade = p_grade;
	}
	
	public int getNo() {
		return no;
	}
	public String getName() {
		return name;
	}
	public int getYear() {
		return year;
	}
	public int getGrade() {
		return grade;
	}
	public String getDepartment() {
		String dep[] =  {"情報工学科","情報ネットワーク学科"};
		return dep[((no / 1000) % 10) - 1];
	}
}
