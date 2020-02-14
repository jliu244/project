package object;

import java.util.ArrayList;

public class Rubric {
	
	private String RubricID;
	private String RubricTitle;
	private String CourseID;
	private ArrayList<RubricItem> items;
	
	public Rubric() {};
	
	public Rubric(String rubricID, String rubricTitle) {
		this.RubricID = rubricID;
		this.RubricTitle = rubricTitle;
	}
	
	public String getRubricID() {
		return RubricID;
	}
	
	public void setRubricID(String rubricID) {
		RubricID = rubricID;
	}
	
	public String getRubricTitle() {
		return RubricTitle;
	}
	
	public void setRubricTitle(String rubricTitle) {
		RubricTitle = rubricTitle;
	}
	
	public ArrayList<RubricItem> getItems(){
		return items;
	}
	
	public void addRubricItem(RubricItem item) {
		items.add(item);
	}

	public String toString() {
		return RubricTitle;
	}
	
}
