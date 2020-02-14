package function;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.connectDB;
import object.Grade;

public class SortGrade {

	public static void SortFinalGrade(int courseID) {
		
		Connection connection = connectDB.connectSqlite();
		
		List<Grade> gradeList = new ArrayList<>();
		
		String sql = "SELECT * FROM grade WHERE courseID = ? ORDER BY FinalGrade DESC;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, courseID);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				gradeList.add(new Grade(resultSet.getFloat(1), resultSet.getInt(3)));
			}
			
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		int size = gradeList.size();
		
		for (int i = 0; i < size; i++) {
			float persentage = i/(float)size;
			gradeList.get(i).setLetterGrade(getLetterGrade(persentage));
		}
		
		sql = "UPDATE grade SET LetterGrade = ? WHERE studentID = ?;";
		try {
			for (Grade gradeItem: gradeList) {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setString(1, gradeItem.getLetterGrade());
				preparedStatement.setInt(2, gradeItem.getStudentID());
				
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static String getLetterGrade(float persentage) {
		if (persentage <= 0.1 && persentage >= 0) {
			return "A";
		}
		else if (persentage <= 0.2 && persentage > 0.1) {
			return "A-";
		}
		else if (persentage <= 0.3 && persentage > 0.2) {
			return "B+";
		}
		else if (persentage <= 0.4 && persentage > 0.3) {
			return "B";
		}
		else if (persentage <= 0.5 && persentage > 0.4) {
			return "B-";
		}
		else if (persentage <= 0.6 && persentage > 0.5) {
			return "C+";
		}
		else if (persentage <= 0.7 && persentage > 0.6) {
			return "C";
		}
		else if (persentage <= 0.8 && persentage > 0.7) {
			return "C-";
		}
		else if (persentage <= 0.9 && persentage > 0.8) {
			return "D";
		}
		else {
			return "F";
		}
	}
	
}
