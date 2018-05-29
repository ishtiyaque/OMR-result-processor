import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Processor {
	
	private String datFileName;
	private int desiredLength=151;

	public Processor(String datFileName) {
		
		this.datFileName = datFileName;
	}

	public void process() {
		
		try {
			Scanner sc = new Scanner(new File(datFileName));
			
			while(sc.hasNext()) {
				
				String line = sc.nextLine();
				if(line.length()!=desiredLength) continue;
				Result result =  processSingleLine(line);
				System.out.println(result);
				Configuration.getDao().insertResult(result);
				//break;
			}
			sc.close();
			
			sc = new Scanner(new File("duplicate.txt"));
			
			while(sc.hasNext()) {
				String line= sc.nextLine();
				String rollNo= line.split("\\t")[0];
				String examType= line.split("\\t")[1];
				Configuration.getDao().deleteDuplicates(rollNo,examType);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}

	private Result processSingleLine(String line) {
	
		int correct = 0; 
		int incorrect = 0; 
		int unanswered = 0;
		
		float marks = 0;
		
		Result result = parse(line);
		
		String correctAnswer = Configuration.getCodeAnswerMap().getOrDefault(result.getSetCode(),null);
		
		if(correctAnswer==null) {
			System.out.println("No correct Answer");
		}
		else {			
			for(int i=0;i<result.getGivenAnswer().length();i++) {
				if(result.getGivenAnswer().charAt(i) == correctAnswer.charAt(i)) {
					correct++;
				}
				else if (result.getGivenAnswer().charAt(i) == ' ') {
					unanswered++;
				}
				else incorrect++;
			}
			marks = correct * Configuration.getCorrrectWeight() - incorrect * Configuration.getIncorrecWeight();
		}
		
		result.setCorrect(correct);
		result.setIncorrect(incorrect);
		result.setUnanswered(unanswered);
		result.setMark(marks);
		
		return result;
	}
	
	private Result parse(String line) {
		
		Result result = new Result();
		
		/***************To be populated by parsing in future**********************/
		String header = line.substring(0, 40);
		String rollNo = line.substring(40, 48);//"12345678";
		String setCode = line.substring(48, 50);//"01";
		String examType = line.substring(50, 51);//Configuration.getExamType();
		String givenAnswer = line.substring(51);//"BC ADBCBDBDBD BCBCBAADBADACABABCBCADACBCCBDACACBDBACADBDBCACDCBCBCBDBCBCBBCBCBCBCBDADACBBADCBDBDBBDA";
		
		/*************************************************************************/
		
		result.setRollNo(rollNo);
		result.setSetCode(setCode);
		result.setGivenAnswer(givenAnswer);
		result.setExamType(examType);
		
		return result;
		
	}
	
}
