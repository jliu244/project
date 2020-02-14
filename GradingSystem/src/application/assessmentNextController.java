package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import connectDB.connectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import object.Assessment;
import object.Question;
import object.Rubric;

public class assessmentNextController implements Initializable {
	
	@FXML
	private TextField questionNumber;
	
	ObservableList<Float> weight = FXCollections.observableArrayList();
	@FXML
	private ComboBox<Float> weightChoice = new ComboBox<Float>(weight);
	
	ObservableList<Rubric> rubric = FXCollections.observableArrayList();
	@FXML
	private ComboBox<Rubric> rubricList = new ComboBox<Rubric>(rubric);
	
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	
	@FXML
	public void save(ActionEvent event) {
		Rubric chooseRubric = rubricList.getSelectionModel().getSelectedItem();
		if(chooseRubric==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("You have not assigned any rubric to this question !!!");
			alert.setContentText("Please assign a rubric to the question first !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
		}
		else {
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="update question set percentage = '" + weightChoice.getValue() + "', rubricID = '" + chooseRubric.getRubricID() + "' where ID = '" + mainController.getChosenQuestionID() + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
			} catch(Exception e) {
				// TODO: handle exception
			}
		
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="delete from question_student where questionID = '" + mainController.getChosenQuestionID() + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
			} catch(Exception e) {
				// TODO: handle exception
			}
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="select * from item where rubricID = '" + chooseRubric.getRubricID() + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
				while(rs.next()) {
					list2.add(rs.getString(2));
				}
			} catch(Exception e) {
				// TODO: handle exception
			}
			for (Object p : list2) {
				for (Object o : list1) {
					try {
						Connection connection = connectDB.connectSqlite();	
						String DB_url = "INSERT INTO question_student VALUES (NULL, ?, ?, 0, ?);";		
						PreparedStatement statement = connection.prepareStatement(DB_url);
						statement.setString(1, o.toString());
						statement.setString(2, mainController.getChosenQuestionID());
						statement.setString(3, p.toString());			
						statement.executeUpdate();	
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		
			float check = 0;
			try {			    	
				Statement stmt = connectDB.connectSqlite().createStatement();
				String sqlRefresh="select * from question where assessmentID = '" + mainController.getAssessmentIDforQuestion() + "'";
				ResultSet rs=stmt.executeQuery(sqlRefresh);
				while(rs.next()){
					check = check + rs.getFloat(3);
				}
			} catch(Exception e) {
				// TODO: handle exception
			}		
			if((int)check!=100) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("The total weight of all the questions is currently not 100% !!!");
				alert.setContentText("Please modify the weight of other questions before grading !!!");
				ButtonType buttonTypeOK = new ButtonType("OK");
				alert.getButtonTypes().setAll(buttonTypeOK);
				alert.showAndWait();
				}
			Start.getInstance().jump();
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
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from question where ID = '" + mainController.getChosenQuestionID() + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		questionNumber.setText(rs.getString(2));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		
		for(float i = 5; i <= 100; i = i + 5) {
			weight.add(i);
		}
		weightChoice.setItems(weight);
		weightChoice.getSelectionModel().select(0);
		
		try {	    	
	    	Statement stmt = connectDB.connectSqlite().createStatement();
	    	String sql="select * from rubric where courseID = '" + mainController.getCourseIDforAssessment() + "'";
	    	ResultSet rs=stmt.executeQuery(sql);
	    	while(rs.next()){
	    		rubric.add(new Rubric(rs.getString(1), rs.getString(2)));
			}
		} catch(Exception e) {
			// TODO: handle exception
		}
		rubricList.setItems(rubric);
		rubricList.getSelectionModel().select(0);
		
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
	}
	
}
