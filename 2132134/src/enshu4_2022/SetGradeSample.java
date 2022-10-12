package enshu4_2022;

public class SetGradeSample {
	public static void main(String[] args) {
		CitStudent kodai = new CitStudent();
		kodai.no = 2132999;
		kodai.name = "工大  太郎";
		kodai.year = 2;
		kodai.grade = 80;
		kodai.department = (kodai.no / 1000) % 10;
		showGrade(kodai);
	}
	
	public static void showGrade(CitStudent data) {
		String dep[] =  {"情報工学科","情報ネットワーク学科"};
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
		System.out.println("No: " +data.no+ " " +data.name+ " " +data.grade+ " " +dep[data.department-1]+ " " +grade);
	}
}