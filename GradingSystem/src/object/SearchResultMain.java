package object;

public class SearchResultMain {

	private String studentID;
	private String studentName;
	private String courseName;
	private String courseFinalGrade;
	private String courseLetterGrade;
	
	public SearchResultMain(String studentID, String studentName, String courseName, String courseFinalGrade, String courseLetterGrade) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.courseName = courseName;
		this.courseFinalGrade = courseFinalGrade;
		this.courseLetterGrade = courseLetterGrade;
	}
	
	public void setID(String studentID) {
		this.studentID = studentID;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void setCourseFinalGrade(String courseFinalGrade) {
		this.courseFinalGrade = courseFinalGrade;
	}
	
	public void setLetterGrade(String courseLetterGrade) {
		this.courseLetterGrade = courseLetterGrade;
	}
	
	public String getStudentID() {
		return studentID;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public String getCourseFinalGrade() {
		return courseFinalGrade;
	}

	public String getCourseLetterGrade() {
		return courseLetterGrade;
	}
	
	public String toString() {
		return "ID: " + studentID + "  Name:   " + studentName + "  CourseTitle:  " + courseName + "   FinalGrade: " + courseFinalGrade + "  LetterGrade:  " + courseLetterGrade;
	}
}
