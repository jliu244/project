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

import connectDB.connectDB;

public class loginController implements Initializable {
	
	private static String currentUsername;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	@FXML
	private void login(ActionEvent event)
	{
		String un = username.getText();
		String pw = password.getText();
		try {
			Connection connection = connectDB.connectSqlite();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery( "select * from user;" );
			while ( rs.next() ) {
				String  name = rs.getString("username");
				String  password = rs.getString("password");		         
				if(un.equals(name)&&pw.equals(password)) {
					currentUsername = name;
					Start.getInstance().jump();
				}
			}
			
			rs.close();
			stmt.close();
			connection.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
			}
		username.clear();
		password.clear();
	}
	
	public static String getUsername() {
		return currentUsername;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		// TODO Auto-generated method stub
		
	}

}
