package enshu4_2022;

public class SetGradeSample {
	public static void main(String[] args) {
		CitStudent2 kodai = new CitStudent2(2132999,"工大  太郎");
		// kodai.setNo();
		// kodai.name = ;
		kodai.year = 2;
		kodai.grade = 80;
		showGrade(kodai);
	}
	
	public static void showGrade(CitStudent2 data) {
		Character grade = 'D';
		if(90 <= data.grade) {
			grade = 'S';
		}else if (80 <= data.grade) {
			grade = 'A';
		}else if (70 <= data.grade) {
			grade = 'B';
		}else if (60 <= data.grade) {
			grade = 'C';
		}else {
			grade = 'D';
		}
		System.out.println("No: " +data.getNo()+ " " +data.name+ " " +data.grade);
	}
}