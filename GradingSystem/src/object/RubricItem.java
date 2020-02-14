package object;

import javafx.beans.property.SimpleObjectProperty;
import application.mainController.Grade;

public class RubricItem {
	
	private int ItemIndex;
	private String ItemName;
	private float ItemPercentage;
	private SimpleObjectProperty<Grade> grade;
	
	public RubricItem() {};
	
	public RubricItem(String itemName, Float per) {
		this.ItemName = itemName;
		this.ItemPercentage = per;
	}
	
	public RubricItem(String itemName, Float per, int index) {
		this.ItemName = itemName;
		this.ItemPercentage = per;
		this.ItemIndex = index;
	}
	
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	
	public void setItemPer(Float per) {
		ItemPercentage = per;
	}
	
	public String getItemName() {
		return ItemName;
	}
	
	public float getItemPer() {
		return ItemPercentage;
	}
	
	public void setGrade(Grade g) {
		grade.set(g);
	}
	
	public SimpleObjectProperty<Grade> getGrade(){
		return grade;
	}
	
	public void setIndex(int index) {
		ItemIndex = index;
	}
	
	public int getIndex() {
		return ItemIndex;
	}
}
