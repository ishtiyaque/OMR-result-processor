
public class ResultDetails {
	
	private String omrHeader;
	private String rollNo;
	private String givenAnswer;
	private String examType;
	private String setCode;
	
	//private float mark;
	
	private int correct;
	private int incorrect;
	private int multipleAnswered;
	private int unanswered;
	
	private int errorCode;

	public ResultDetails() {
		
		this.correct = -1;
		this.incorrect = -1;
		this.multipleAnswered = -1;
		this.unanswered = -1;
		
		this.errorCode = ErrorTypes.NO_ERROR;
	}

	
	
	public ResultDetails(String omrHeader, String rollNo, String givenAnswer, String examType, String setCode, int correct,
			int incorrect, int multipleAnswered, int unanswered) {
		super();
		this.omrHeader = omrHeader;
		this.rollNo = rollNo;
		this.givenAnswer = givenAnswer;
		this.examType = examType;
		this.setCode = setCode;
		this.correct = correct;
		this.incorrect = incorrect;
		this.multipleAnswered = multipleAnswered;
		this.unanswered = unanswered;
		this.errorCode = ErrorTypes.NO_ERROR;
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
	
	public int getMultipleAnswered() {
		return multipleAnswered;
	}

	public void setMultipleAnswered(int multipleAnswered) {
		this.multipleAnswered = multipleAnswered;
	}


	public int getUnanswered() {
		return unanswered;
	}

	public void setUnanswered(int unanswered) {
		this.unanswered = unanswered;
	}
	
	


	public int getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}



	@Override
	public String toString() {
		return "ResultDetails [omrHeader=" + omrHeader + ", rollNo=" + rollNo + ", givenAnswer=" + givenAnswer
				+ ", examType=" + examType + ", setCode=" + setCode + ", correct=" + correct + ", incorrect="
				+ incorrect + ", multipleAnswered=" + multipleAnswered + ", unanswered=" + unanswered + ", errorCode="
				+ errorCode + "]";
	}
	

}
