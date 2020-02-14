package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import connectDB.connectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class assessmentEditController implements Initializable {
	
	@FXML
	private TextField assessmentTitle;
	
	ObservableList<Float> weight = FXCollections.observableArrayList();
	@FXML
	private ComboBox<Float> weightChoice = new ComboBox<Float>(weight);
	
	@FXML
	public void save(ActionEvent event) {
		String sql = "UPDATE assessment SET percentage = ? WHERE ID = ?;";
		try {
			PreparedStatement statement = connectDB.connectSqlite().prepareStatement(sql);
			statement.setFloat(1, weightChoice.getValue());
			statement.setString(2, mainController.getForAssessmentID());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
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
		
		assessmentTitle.setText(mainController.getForAssessmentTitle());
		for(float i = 5; i <= 100 - mainController.getForAssessmentRemainingWeight(); i = i + 5) {
			weight.add(i);
		}
		weightChoice.setItems(weight);
		weightChoice.getSelectionModel().select(0);
		
	}
	
}
