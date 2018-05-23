import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.CodingErrorAction;
import java.util.Scanner;

public class Processor {
	String datFileName;

	public Processor(String datFileName) {
		
		this.datFileName = datFileName;
	}

	public void process() {
		
		try {
			Scanner sc = new Scanner(new File(datFileName));
			
			while(sc.hasNext()) {
				
				String line = sc.nextLine();
				Result result =  parse(line);
				System.out.println(result);
				Configuration.dao.insertResult(result);
				break;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	private Result parse(String line) {
		// TODO Auto-generated method stub
		int i,j,k;
		int correct, incorrect, unanswered,length;
		float marks;
		
		correct = incorrect = unanswered = 0;
		marks = 0;
		String rollNo = "12345678";
		String setcode = "01";
		String givenAnswer = "BC ADBCBDBDBD BCBCBAADBADACABABCBCADACBCCBDACACBDBACADBDBCACDCBCBCBDBCBCBBCBCBCBCBDADACBBADCBDBDBBDA";
		
		String correctAnswer = Configuration.codeAnswerMap.getOrDefault(setcode,null);
		if(correctAnswer==null) {
			System.out.println("No correct Answer");
		}
		else {
			length = givenAnswer.length();
			
			
			for(i=0;i<length;i++) {
				if(givenAnswer.charAt(i)==correctAnswer.charAt(i)) {
					correct++;
				}
				else if (givenAnswer.charAt(i)==' ') {
					unanswered++;
				}
				else incorrect++;
			}
			marks = correct * Configuration.corrrectWeight - incorrect * Configuration.incorrecWeight;
		}
		Result result = new Result(rollNo, givenAnswer, Configuration.examType, setcode, marks, correct, incorrect, unanswered);
		
		return result;
	}
	
}
