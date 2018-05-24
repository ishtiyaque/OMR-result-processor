import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Processor {
	
	private String datFileName;

	public Processor(String datFileName) {
		
		this.datFileName = datFileName;
	}

	public void process() {
		
		try {
			Scanner sc = new Scanner(new File(datFileName));
			
			while(sc.hasNext()) {
				
				String line = sc.nextLine();
				Result result =  processSingleLine(line);
				System.out.println(result);
				Configuration.dao.insertResult(result);
				break;
			}
			sc.close();
			
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
		
		String correctAnswer = Configuration.codeAnswerMap.getOrDefault(result.getSetCode(),null);
		
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
			marks = correct * Configuration.corrrectWeight - incorrect * Configuration.incorrecWeight;
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
		
		String rollNo = "12345678";
		String setCode = "01";
		String givenAnswer = "BC ADBCBDBDBD BCBCBAADBADACABABCBCADACBCCBDACACBDBACADBDBCACDCBCBCBDBCBCBBCBCBCBCBDADACBBADCBDBDBBDA";
		String examType = Configuration.examType;
		
		/*************************************************************************/
		
		result.setRollNo(rollNo);
		result.setSetCode(setCode);
		result.setGivenAnswer(givenAnswer);
		result.setExamType(examType);
		
		return result;
		
	}
	
}
