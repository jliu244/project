package application;

import java.net.URL;
import java.util.ResourceBundle;

import function.ImportMultiRubric;

import java.io.File;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class rubricImportController implements Initializable {
	
	@FXML
	private TextField inputfilePath;
	
	@FXML
	public void Import(ActionEvent event) {
		
		String filePath = inputfilePath.getText();
		String currentCourseID = mainController.getCourseIDforRubric();
		File inputFile = new File(filePath);
		
		if(inputFile.exists() == false) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("The file does not exist !!!");
			alert.setContentText("Please check the file path and rewrite !!!");
			ButtonType buttonTypeOK = new ButtonType("OK");
			alert.getButtonTypes().setAll(buttonTypeOK);
			alert.showAndWait();
			Start.getInstance().rubricImport();
		}else {
			ImportMultiRubric.ImportMulitRubric(inputFile, currentCourseID);
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
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("xlsx Files", "*.xlsx"));
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		
		File selectedFile = fileChooser.showOpenDialog(mainStage);
		String path = selectedFile.getPath();
		
		System.out.println(path);
		inputfilePath.setText(path);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
	}
	
}