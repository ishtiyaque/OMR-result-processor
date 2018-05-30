import java.util.ArrayList;

public class ResultGenerator {
	
	public ResultGenerator() {
		
	}
	
	public void calculateMarks(ArrayList<String> setCodes) {
		ArrayList<ResultDetails> resultDetailsList = Configuration.getDao().getResultDetails(setCodes);
		
		for(ResultDetails resultDetails : resultDetailsList ) {
			String rollNo = resultDetails.getRollNo();
			String setCode = resultDetails.getSetCode();
			
			int correct = resultDetails.getCorrect();
			int incorrect = resultDetails.getIncorrect();
			int multipleAnswered = resultDetails.getMultipleAnswered();
			//int unanswered = resultDetails.getUnanswered();
			
			float marks = (correct * Configuration.getCorrrectWeight())
							- ((incorrect + multipleAnswered) * Configuration.getIncorrecWeight());
			
			Configuration.getDao().insertResult(rollNo, marks, setCode);
			
		}
		
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration.loadConfiguration("conf.txt");
		//Configuration.getDao().insertResult("12345", 10.25f, "03");
		ArrayList<String> setCodes = new ArrayList<>();
		setCodes.add("01");
		setCodes.add("03");
		ResultGenerator temp = new ResultGenerator();
		temp.calculateMarks(setCodes);

	}
	*/
	


}
