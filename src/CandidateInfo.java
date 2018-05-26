
public class CandidateInfo {

	private String postCode;
	private String rollNo;
	private String applicantName;
	private String fathersName;
	private String mothersName;
	private String quota;
	
	
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getMothersName() {
		return mothersName;
	}
	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public CandidateInfo(String postCode, String rollNo, String applicantName, String fathersName, String mothersName,
			String quota) {
		super();
		this.postCode = postCode;
		this.rollNo = rollNo;
		this.applicantName = applicantName;
		this.fathersName = fathersName;
		this.mothersName = mothersName;
		this.quota = quota;
	}
	public CandidateInfo() {
	}
	@Override
	public String toString() {
		return "CandidateInfo [postCode=" + postCode + ", rollNo=" + rollNo + ", applicantName=" + applicantName
				+ ", fathersName=" + fathersName + ", mothersName=" + mothersName + ", quota=" + quota + "]";
	}
	
	
	
}
