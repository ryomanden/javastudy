package enshu4_2022;

public class SetGradeSample {
	public static void main(String[] args) {
		CitStudent kodai = new CitStudent();
		kodai.no = 2132999;
		kodai.name = "工大  太郎";
		kodai.year = 2;
		kodai.grade = 80;
		showGrade(kodai);
	}
	
	public static void showGrade(CitStudent data) {
		System.out.println("No: " +data.no+ " " +data.name+ " " +data.grade);
	}
}
