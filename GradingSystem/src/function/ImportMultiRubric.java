package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import connectDB.connectDB;
import object.RubricItem;

public class ImportMultiRubric {

	public static void ImportMulitRubric(File importFile, String courseID) {
		List<RubricItem> rubricList = new ArrayList<>();
		
		FileInputStream fileInputStream = null;
		XSSFWorkbook workbook = null;
		
		try {
			fileInputStream = new FileInputStream(importFile);
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		int sheetNum = workbook.getNumberOfSheets();
		String sql = null;
		Connection connection = connectDB.connectSqlite();
		
		for (int i = 0; i < sheetNum; i++) {
			XSSFSheet sheetHead = workbook.getSheetAt(i);
			XSSFRow rowHead = sheetHead.getRow(0);
			XSSFCell titleCell = rowHead.getCell(0);
			String rubricTitle = titleCell.getStringCellValue();
			int rubricID = 0;
			
			
			try {
				sql = "INSERT INTO rubric (ID, title, courseID) VALUES (null, ?, ?);";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, rubricTitle);
				preparedStatement.setInt(2, Integer.parseInt(courseID));
				
				preparedStatement.executeUpdate();
				
				sql = "SELECT * FROM rubric WHERE title = ?;";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, rubricTitle);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					rubricID = resultSet.getInt(1);
				}
				
				resultSet.close();
				preparedStatement.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			for (int j = 2; j <= sheetHead.getLastRowNum(); j++) {
				XSSFRow row = sheetHead.getRow(j);
				XSSFCell cellName = row.getCell(0);
				XSSFCell cellPercentage = row.getCell(1);
				
				rubricList.add(new RubricItem(cellName.getStringCellValue(), (float)cellPercentage.getNumericCellValue()));
			}
			
			
			sql = "INSERT INTO item (ID, title, percentage, rubricID) VALUES (NULL, ?, ?, ?);";
			try {
				for (RubricItem rubric: rubricList) {
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, rubric.getItemName());
					preparedStatement.setFloat(2, rubric.getItemPer());
					preparedStatement.setInt(3, rubricID);
					
					preparedStatement.executeUpdate();
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO: handle exception
			}
			
			rubricList.clear();
			
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
