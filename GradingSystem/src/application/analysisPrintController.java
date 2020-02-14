package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class analysisPrintController implements Initializable {
	
	@FXML
	public void print(ActionEvent event) {
		
		Start.getInstance().jump();
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
