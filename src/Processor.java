import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Processor {

	//private String datFileName;

	public Processor() {

		//this.datFileName = datFileName;
	}

	public void processFile(String fileName) {

		try {
			Scanner sc = new Scanner(new File(fileName));

			while (sc.hasNext()) {

				String line = sc.nextLine();
				if (line.length() != Configuration.getDesiredlength())
					continue;
				ResultDetails result = processSingleLine(line);
				if (result == null) {
					continue;
				}
				System.out.println(result);
				Configuration.getDao().insertResultDetails(result);
				// break;
			}
			sc.close();
			Configuration.getDao().detectDuplicates();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultDetails processSingleLine(String line) {

		ResultDetails result = parse(line);
		result = evaluate(result);

		return result;
	}

	public ResultDetails evaluate(ResultDetails result) {
		
		int correct = 0;
		int incorrect = 0;
		int unanswered = 0;
		int multipleAnswered = 0;

		String correctAnswer = Configuration.getCodeAnswerMap().getOrDefault(result.getSetCode(), null);

		if (correctAnswer == null) {
			result.setErrorCode(ErrorTypes.INVALID_SET_CODE);
			return result;
		} else {
			for (int i = 0; i < result.getGivenAnswer().length(); i++) {
				if (result.getGivenAnswer().charAt(i) == correctAnswer.charAt(i)) {
					correct++;
				} else if (result.getGivenAnswer().charAt(i) == ' ') {
					unanswered++;
				} else if (result.getGivenAnswer().charAt(i) == '*') {
					multipleAnswered++;
				} else
					incorrect++;
			}

		}

		result.setCorrect(correct);
		result.setIncorrect(incorrect);
		result.setUnanswered(unanswered);
		result.setMultipleAnswered(multipleAnswered);

		return result;
	}

	private ResultDetails parse(String line) {

		ResultDetails result = new ResultDetails();

		String omrHeader = line.substring(0, 40);

		String rollNo = line.substring(40, 48);
		if (rollNo.contains("*")) {
			result.setErrorCode(ErrorTypes.ROLL_CONTAINS_ASTERISK);
		}
		if (rollNo.contains(" ")) {
			result.setErrorCode(ErrorTypes.ROLL_CONTAINS_SPACE);
		}
		String setCode = line.substring(48, 50);
		if (setCode.contains("*")) {
			result.setErrorCode(ErrorTypes.SET_CODE_CONTAINS_ASTERISK);

		}
		if (setCode.contains(" ")) {
			result.setErrorCode(ErrorTypes.SET_CODE_CONTAINS_SPACE);
		}
		String examType = line.substring(50, 51);
		if (examType.contains("*")) {
			result.setErrorCode(ErrorTypes.EXAM_TYPE_CONTAINS_ASTERISK);
		}
		if (examType.contains(" ")) {
			result.setErrorCode(ErrorTypes.EXAM_TYPE_CONTAINS_SPACE);
		}
		if (Configuration.getExamType().equals(examType) == false) {
			result.setErrorCode(ErrorTypes.EXAM_TYPE_MISMATCH);
		}
		String givenAnswer = line.substring(51);

		result.setOmrHeader(omrHeader);
		result.setRollNo(rollNo);
		result.setSetCode(setCode);
		result.setGivenAnswer(givenAnswer);
		result.setExamType(examType);

		return result;

	}

}
