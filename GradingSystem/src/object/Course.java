package object;

public class Course {
	private String CourseTitle = "";
	private String CourseID;
	private StudentList studentList;
	private RubricList rubricList;
	
	public Course() {};
	
	// Constructor
	public Course(String courseTitle) {
		this.CourseTitle = courseTitle;
	}
	
	public Course(String courseID, String courseTitle) {
		this.CourseID = courseID;
		this.CourseTitle = courseTitle;
	}
	
	public void setCourseTitle(String courseTitle) {
		CourseTitle = courseTitle;
	}
	
	public void setCourseID(String ID) {
		CourseID = ID;
	}
	
	public String getCourseID() {
		return CourseID;
	}
	
	public String getCourseTitle() {
		return CourseTitle;
	}
	
	public StudentList getStudentList() {
		return studentList;
	}
	
	public RubricList getRubricList() {
		return rubricList;
	}
	
	public String toString() {
		return CourseTitle;
	}

}
