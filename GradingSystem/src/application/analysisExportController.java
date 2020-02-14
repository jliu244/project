package application;

import java.io.File;
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
import function.ExportAnalysis;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import object.Course;
import object.Grade;

public class analysisExportController implements Initializable {
	
	@FXML
	private TextField inputFilePath;
	@FXML
	private TextField inputFileName;
	@FXML
	private Label CourseShow;
	
	@FXML
	public void Export(ActionEvent event) {
		
		Course courseChoosen = mainController.getCourseIDChoosen();
		
		String filePath = inputFilePath.getText() + "/" + inputFileName.getText() + ".xlsx";
		File outputFile = new File(filePath);
		
		if (outputFile.exists()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("The file already exist !!!");
			alert.setContentText("Please check the file path choose again !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
			Start.getInstance().analysisExport();
		}
		
		else {
			//write the data to xlsx file
			
			List<Grade> gradeList = new ArrayList<>();
			Connection connection = connectDB.connectSqlite();
			
			String sql = "SELECT * FROM grade WHERE courseID = ?;";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, Integer.parseInt(courseChoosen.getCourseID()));
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					gradeList.add(new Grade(resultSet.getFloat(1), resultSet.getString(2), resultSet.getInt(3)));
				}
				
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			ExportAnalysis.ExportAnalysisToXlsx(gradeList, outputFile);
			
		}
		
		Start.getInstance().jump();
	}
	@FXML
	public void cancel(ActionEvent event) {
		Start.getInstance().jump();
	}
	
	@FXML
	public void chooseDir(ActionEvent event) {
		Stage mainStage = null;
		
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Choose directory");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
				
		File selectedFile = dirChooser.showDialog(mainStage);
		String path = selectedFile.getPath();
		
		//System.out.println(path);
		inputFilePath.setText(path);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		Course courseChoosen = mainController.getCourseIDChoosen();
		CourseShow.setText(courseChoosen.getCourseTitle());
	}
	
}
