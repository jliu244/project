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

import javax.print.DocFlavor.STRING;

import connectDB.connectDB;
import function.ImportMultiRubric;
import function.ReadRubricList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import object.RubricItem;

public class courseImportRubricController implements Initializable {
	
	@FXML
	private TextField inputFilePath;
	
	@FXML
	public void Import(ActionEvent event) {
		
		String filePath = inputFilePath.getText();
		String courseID = mainController.getSelectedCourseID();
		
		File inputFile = new File(filePath);
		
		List<RubricItem> rubricList = new ArrayList<>();
		
		if (inputFile.exists() == false) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("The file does not exist !!!");
			alert.setContentText("Please check the file path and rewrite !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
			Start.getInstance().courseImportRubric();
		}
		
		else {
			/*
			rubricList = ReadRubricList.ReadRubricFromXlsx(inputFile);
			
			String sql = "INSERT INTO item (title, percentage) VALUES (?, ?);";
			
			try {
				Connection connection = connectDB.connectSqlite();
				
				for (RubricItem item: rubricList) {
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					
					preparedStatement.setString(1, item.getItemName());
					preparedStatement.setFloat(2, item.getItemPer());
					
					preparedStatement.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			*/
			
			//multiRubtic import
			ImportMultiRubric.ImportMulitRubric(inputFile, courseID);
			
			Start.getInstance().jump();
		}
	}
	
	
	@FXML
	public void cancel(ActionEvent event) {
		Start.getInstance().jump();
	}
	
	
	@FXML
	public void chooseFile(ActionEvent event) {
		Stage mainStage = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select xlsx file to import");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Xlsx Files", "*.xlsx"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		
		File selectedFile = fileChooser.showOpenDialog(mainStage);
		String path = selectedFile.getPath();
		
		System.out.println(path);
		inputFilePath.setText(path);
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
	}
	
}
