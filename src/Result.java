
public class Result {
	
	private String omrHeader;
	private String rollNo;
	private String givenAnswer;
	private String examType;
	private String setCode;
	
	//private float mark;
	
	private int correct;
	private int incorrect;
	private int multipleAnswer;
	private int unanswered;

	public Result() {

	}

	public Result(String rollNo, String givenAnswer, String examType, String setCode, float mark, int correct,
			int incorrect, int unanswered) {

		this.rollNo = rollNo;
		this.givenAnswer = givenAnswer;
		this.examType = examType;
		this.setCode = setCode;
		//this.mark = mark;
		this.correct = correct;
		this.incorrect = incorrect;
		this.unanswered = unanswered;
	}

	
	public String getOmrHeader() {
		return omrHeader;
	}

	public void setOmrHeader(String omrHeader) {
		this.omrHeader = omrHeader;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}

	/*public float getMark() {
		return mark;
	}*/

	/*public void setMark(float mark) {
		this.mark = mark;
	}*/

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}
	
	public int getMultipleAnswer() {
		return multipleAnswer;
	}

	public void setMultipleAnswer(int multipleAnswer) {
		this.multipleAnswer = multipleAnswer;
	}


	public int getUnanswered() {
		return unanswered;
	}

	public void setUnanswered(int unanswered) {
		this.unanswered = unanswered;
	}

	@Override
	public String toString() {
		return "Result [omrHeader=" + omrHeader + ", rollNo=" + rollNo + ", givenAnswer=" + givenAnswer + ", examType="
				+ examType + ", setCode=" + setCode + ", correct=" + correct + ", incorrect=" + incorrect
				+ ", multipleAnswer=" + multipleAnswer + ", unanswered=" + unanswered + "]";
	}
	
	

}
