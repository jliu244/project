package object;

import java.util.ArrayList;

public class Assessment {
	
	private String AssessmentID;
	private String AssessmentTitle;
	private float AssessWeight; 
	private String CourseID;
	private String QuesNum; 
	private ArrayList<Question> questions;
	
	public Assessment() {};
	
	// Constructor
	public Assessment(String assessID, String assessTitle, float assessWeight, String courseID, String quesNum) {
		this.AssessmentID = assessID;
		this.AssessmentTitle = assessTitle;
		this.AssessWeight = assessWeight;
		this.CourseID = courseID;
		this.QuesNum = quesNum;
	}
	
	public void setAssessID(String assessID) {
		AssessmentID = assessID;
	}
	
	public void setAssessTitle(String assessTitle) {
		AssessmentTitle = assessTitle;
	}
	
	public void setAssessWeight(float assessWeight) {
		AssessWeight = assessWeight;
	}
	
	public String getAssessID() {
		return AssessmentID;
	}
	
	public String getAssessTitle() {
		return AssessmentTitle;
	}
	
	public float getAssessWeight() {
		return AssessWeight;
	}
	
	public void setQuesNum(String quesNum) {
		QuesNum = quesNum;
	}
	
	public String getQuesNum() { 
		return QuesNum;
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}
	
	public String toString() {
		return AssessmentTitle;
	}
	
}
