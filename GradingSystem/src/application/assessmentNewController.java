package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import connectDB.connectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import object.Course;
import object.Student;

public class assessmentNewController implements Initializable {
	
	private static String assessmentID;
	
	@FXML
	private TextField assessmentTitle;
	
	ObservableList<Float> weight = FXCollections.observableArrayList();
	@FXML
	private ComboBox<Float> weightChoice = new ComboBox<Float>(weight);
	
	ObservableList<Integer> numberOfQuestions = FXCollections.observableArrayList();
	@FXML
	private ComboBox<Integer> numberOfQuestionsList = new ComboBox<Integer>(numberOfQuestions);
	
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	
	@FXML
	public void next(ActionEvent event) {
		
		String assess_title = assessmentTitle.getText();
		if(assess_title.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You are to add an assessment !!!");
			alert.setContentText("Please input assessment title !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			boolean check = true;
			try {			    	
		    	Statement stmt = connectDB.connectSqlite().createStatement();
		    	String sql="select * from assessment where courseID = '" +  mainController.getCourseIDforAssessment() + "'";
		    	ResultSet rs=stmt.executeQuery(sql);
		    	while(rs.next()){
		    		if(assess_title.equals(rs.getString(2)))
		    			check = false;
				}
			} catch(SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if(check==false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("You are to add an assessment !!!");
				alert.setContentText("This assessment already exists !!!");
				ButtonType buttonTypeOK = new ButtonType("OK");
				alert.getButtonTypes().setAll(buttonTypeOK);
				alert.showAndWait();
			}
			else {
				Connection connection = connectDB.connectSqlite();	
				String DB_url = "INSERT INTO assessment VALUES (NULL, ?, ?, ?, ?);";		
				try {
					PreparedStatement statement = connection.prepareStatement(DB_url);
					statement.setString(1, assess_title);
					statement.setFloat(2, weightChoice.getValue());			
					statement.setString(3, mainController.getCourseIDforAssessment());
					statement.setInt(4, numberOfQuestionsList.getValue());	
					statement.executeUpdate();				
					Statement stmt = connectDB.connectSqlite().createStatement();
			    	String sql="select * from assessment where courseID = '" +  mainController.getCourseIDforAssessment() + "'";
			    	ResultSet rs=stmt.executeQuery(sql);
			    	while(rs.next()){
			    		assessmentID = rs.getString(1);
					}
				} catch(SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
					
				try {
					Statement stmt = connectDB.connectSqlite().createStatement();
			    	String sql="select * from student where courseID = '" + mainController.getCourseIDforAssessment() + "'";
			    	ResultSet rs=stmt.executeQuery(sql);
			    	while(rs.next()){
			    		list1.add(rs.getString(1));
			    	}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				for (Object o : list1) {
					try {
						DB_url = "INSERT INTO assessment_student VALUES (NULL, ?, ?, 0);";		
						PreparedStatement statement = connection.prepareStatement(DB_url);
						statement.setString(1, o.toString());
						statement.setString(2, assessmentID);			
						statement.executeUpdate();	
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				for(int i = 1; i <= numberOfQuestionsList.getValue(); i++) {
					DB_url = "INSERT INTO question VALUES (NULL, ?, 0, 0, ?);";		
					try {
						PreparedStatement statement = connection.prepareStatement(DB_url);
						statement.setInt(1, i);
						statement.setString(2, assessmentID);			
						statement.executeUpdate();				
					} catch(SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				try {
					Statement stmt = connectDB.connectSqlite().createStatement();
			    	String sql="select * from question where assessmentID = '" + assessmentID + "'";
			    	ResultSet rs=stmt.executeQuery(sql);
			    	while(rs.next()){
			    		list2.add(rs.getString(1));
			    	}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				Start.getInstance().jump();
				
				for (Object p : list2) {
					for (Object o : list1) {
						try {
							DB_url = "INSERT INTO question_student VALUES (NULL, ?, ?, 0, '');";		
							PreparedStatement statement = connection.prepareStatement(DB_url);
							statement.setString(1, o.toString());
							statement.setString(2, p.toString());			
							statement.executeUpdate();	
						} catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
							
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
		
		float check = mainController.getForAssessmentRemainingWeight();
		for(float i = 5; i <= 100 - check; i = i + 5) {
			weight.add(i);
		}
		weightChoice.setItems(weight);
		weightChoice.getSelectionModel().select(0);
		
		for(int i = 1; i <= 10; i++) {
			numberOfQuestions.add(i);
		}
		numberOfQuestionsList.setItems(numberOfQuestions);
		numberOfQuestionsList.getSelectionModel().select(4);
		
	}
	
}
