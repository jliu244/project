package object;

public class Grade {

	private float finalGrade;
	private String letterGrade;
	private int studentID;
	
	public Grade(float finalGrade, int studentID) {
		this.finalGrade = finalGrade;
		this.letterGrade = null;
		this.studentID = studentID;
	}
	
	public Grade(float finalGrade, String letterGrade, int studentID) {
		this.finalGrade = finalGrade;
		this.letterGrade = letterGrade;
		this.studentID = studentID;
	}
	
	
	public float getFinalGrade() {
		return finalGrade;
	}
	
	public String getLetterGrade() {
		return letterGrade;
	}
	
	public int getStudentID() {
		return studentID;
	}
	
	public void setFinalGrade(float finalGrade) {
		this.finalGrade = finalGrade;
	}
	
	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
}
