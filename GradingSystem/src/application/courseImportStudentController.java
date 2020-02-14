package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import object.Student;
import connectDB.connectDB;
import function.ReadStudentList;

public class courseImportStudentController implements Initializable {
	
	@FXML
	public TextField inputFilePath;
	
	@FXML
	public void Import(ActionEvent event) {

		String choosenCourseID = mainController.getSelectedCourseID();
		
		String filePath = inputFilePath.getText();
		//System.out.println(filePath);
		
		List<Student> studentList = new ArrayList<>();
		File inputFile = new File(filePath);
		
		if (inputFile.exists() == false) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("The file does not exist !!!");
			alert.setContentText("Please check the file path and rewrite !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
			Start.getInstance().courseImportStudent();
		}
		
		else {
			studentList = ReadStudentList.readStudentFromXlsx(inputFile);
			Connection connection = connectDB.connectSqlite();
			
			//input in database
			String sql = "INSERT INTO student (ID, name, courseID) VALUES (?, ?, ?);";
			try {
				for(Student tempStudent:studentList) {
					PreparedStatement pStatement = connection.prepareStatement(sql);
					pStatement.setString(1, tempStudent.getStudentID());
					pStatement.setString(2, tempStudent.getStudentName());
					pStatement.setString(3, choosenCourseID);
					
					pStatement.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO: handle exception
			}
			
			sql = "INSERT INTO grade VALUES (0, 'F', ?, ?);";
			try {
				for(Student tempStudent:studentList) {
					PreparedStatement pStatement = connection.prepareStatement(sql);
					pStatement.setString(1, tempStudent.getStudentID());
					pStatement.setString(2, choosenCourseID);
					
					pStatement.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO: handle exception
			}
			
			
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			Start.getInstance().jump();
		}
	}
	
	
	@FXML
	public void chooseFile(ActionEvent event) {
		Stage mainStage = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select xlsx file to import");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Xlsx Files", "*.xlsx"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		
		File selectedFile = fileChooser.showOpenDialog(mainStage);
		String path = selectedFile.getPath();
		
		System.out.println(path);
		inputFilePath.setText(path);
	}
	
	
	@FXML
	public void cancel(ActionEvent event) {
		Start.getInstance().jump();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
	}
	
}
