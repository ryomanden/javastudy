package enshu4_2022;

public class SetGradeSample {
	public static void main(String[] args) {
		CitStudent2 kodai = new CitStudent2();
		kodai.setNo(2132999);
		kodai.setName("工大  太郎");
		kodai.setYear(2);
		kodai.setGrade(80);
		showGrade(kodai);
	}
	
	public static void showGrade(CitStudent2 data) {
		Character grade = 'D';
		if(90 <= data.getGrade()) {
			grade = 'S';
		}else if (80 <= data.getGrade()) {
			grade = 'A';
		}else if (70 <= data.getGrade()) {
			grade = 'B';
		}else if (60 <= data.getGrade()) {
			grade = 'C';
		}else {
			grade = 'D';
		}
		System.out.println("No: " +data.getNo()+ " " +data.getName()+ " " +data.getGrade()+ " " +data.getDepartment()+ " " +grade);
	}
}