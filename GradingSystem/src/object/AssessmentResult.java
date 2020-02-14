package object;

public class AssessmentResult {
	
	private int assessmentID;
	private String title;
	private String weight;
	private String grade;
	
	public AssessmentResult(int assessmentID, String title, String weight, String grade) {
		this.assessmentID = assessmentID;
		this.grade = grade;
		this.title = title;
		this.weight = weight;
	}
	
	
	public int getID() {
		return assessmentID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getWeight() {
		return weight;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public void setID(int assessmentID) {
		this.assessmentID = assessmentID;
	}
}
