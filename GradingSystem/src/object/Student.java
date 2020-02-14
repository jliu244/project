package object;

public class Student {
	private String StudentName;
	private String StudentID;
	
	public Student() {};
	
	public Student(String ID, String studentName) {
		this.StudentID = ID;
		this.StudentName = studentName;
	}
	
	public String getStudentName() {
		return StudentName;
	}
	
	public String getStudentID() {
		return StudentID;
	}

	public String toString() {
		return "ID:   " + StudentID + "                    Name:   " + StudentName;
	}
	
}
