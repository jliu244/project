package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connectDB.connectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import object.Rubric;
import object.RubricList;

public class rubricNewController implements Initializable {
	
	@FXML
	private TextField rubricTitle;
	
	ObservableList<Integer> numberOfItems = FXCollections.observableArrayList();
	
	ArrayList<String> rubricList = new ArrayList<String>();
	
	@FXML
	private ComboBox<Integer> chooseItemNum = new ComboBox<Integer>(numberOfItems);
	
	@FXML
	public void create(ActionEvent event) {
		
		String newRubricTitle = rubricTitle.getText();
		String currentCourseID = mainController.getCourseIDforRubric();
		int itemsNum = chooseItemNum.getSelectionModel().getSelectedItem();
		int currentRubricID = 0;
		
		if(newRubricTitle.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not input rubric title !!!");
			alert.setContentText("Please input a rubric title first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			int check = 1;
			try {
				Statement statement = connectDB.connectSqlite().createStatement();
				String sql = "Select title from rubric where courseID='" + currentCourseID + "'";
				ResultSet rSet = statement.executeQuery(sql);
				while (rSet.next()) {
					rubricList.add(rSet.getString(1));
				}
				for (String str : rubricList) {
					if(str.equals(newRubricTitle)) {
						check = 0;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(check==0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("This rubric title has already existed !!!");
				alert.setContentText("Please use other titles !!!");
				ButtonType buttonTypeOK = new ButtonType("OK");
				alert.getButtonTypes().setAll(buttonTypeOK);
				alert.showAndWait();
			}
			else {
				try {
					Connection connection = connectDB.connectSqlite();
					String sql = "INSERT INTO rubric VALUES(NULL, ?, ?)";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, newRubricTitle);
					statement.setString(2, currentCourseID);
					statement.executeUpdate();
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				try {
					Statement statement = connectDB.connectSqlite().createStatement();
					String sql = "Select ID from rubric where title='" + newRubricTitle + "'";
					ResultSet resultSet = statement.executeQuery(sql);
					currentRubricID = resultSet.getInt(1);
					statement.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			
				float per = 100/(float)itemsNum;
				try {
					Connection connection = connectDB.connectSqlite();
					String sql = "INSERT INTO item VALUES(NULL, ?, ?, ?)";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, "ItemName");
					statement.setFloat(2, per);
					statement.setInt(3, currentRubricID);
					for(int i=0; i<itemsNum; i++) {
						statement.executeUpdate();
					}
					statement.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
				Start.getInstance().jump();
			}
		}
		
	}
	@FXML
	public void cancel(ActionEvent event) {
		Start.getInstance().jump();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		// TODO Auto-generated method stub
		for(int i=1; i<=10; i++) {
			numberOfItems.add(i);
		}
		chooseItemNum.setItems(numberOfItems);
		chooseItemNum.getSelectionModel().select(4);
	}
	
}