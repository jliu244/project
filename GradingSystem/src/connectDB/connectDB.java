package connectDB;

import java.sql.Connection;
import java.sql.*;

public class connectDB {
	
	public static Connection connectSqlite() {
		
		Connection connection = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:system.db");
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Connect database fail, please check the database existing");
			e.printStackTrace();
		}
			
		return connection;
	}
	
}
