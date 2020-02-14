package object;

public class Question {
	
	private String QuestionID;
	private int QuestionNumber; 
	private float QuestionWeight; 
	private String RubricID;
	private String AssessmentID;
	
	private Rubric currentRubric;
	
	public Question() {};
	
	public Question(String QuestionID, int QuestionNumber, float QuestionWeight, String RubricID, String AssessmentID) {
		this.QuestionID = QuestionID;
		this.QuestionNumber = QuestionNumber;
		this.QuestionWeight = QuestionWeight;
		this.RubricID = RubricID;
		this.AssessmentID = AssessmentID;
	}
	
	public String getQuestionID() {
		return QuestionID;
	}
	
	public int getQuestionNumber() {
		return QuestionNumber;
	}
	
	public float getQuestionWeight() {
		return QuestionWeight;
	}
	
	public void setQuestionID(String QuestionID) {
		this.QuestionID = QuestionID;
	}
	
	public void setQuestionNumber(int QuestionNumber) {
		this.QuestionNumber = QuestionNumber;
	}
	
	public void setQuestionWeight(float QuestionWeight) {
		this.QuestionWeight = QuestionWeight;
	}
	
	public Rubric getRubric() {
		return currentRubric;
	}
	
	public void setBubric(Rubric rubric) {
		currentRubric = rubric;
	}
	
	public String toString() {
		return "Question Number " + QuestionNumber;
	}
	
}
