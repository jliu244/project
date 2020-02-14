package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import object.Student;

public class ReadStudentList {
	
	public static List<Student> readStudentFromXlsx(File inputFile) {
		List<Student> studentList = new ArrayList<>();
		
		FileInputStream fileInputStream = null;
		
		try { 
			fileInputStream = new FileInputStream(inputFile);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		XSSFWorkbook workbook = null;
		
		try {
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (workbook == null) {
			System.out.println("xlsx file loading fail, please check");
			return null;
		}
		
		XSSFSheet workSheet = workbook.getSheetAt(0);
		int rowNum = workSheet.getLastRowNum();
		
		for (int i = 0; i < rowNum; i++) {
			XSSFRow row = workSheet.getRow(i);
			
			XSSFCell studentNameCell = row.getCell(1);
			XSSFCell studentIDCell = row.getCell(0);
			
			String studentNameTemp = studentNameCell.getStringCellValue();
			String studenIDTemp = Double.toString(studentIDCell.getNumericCellValue());
			
			Student studentTemp = new Student(studenIDTemp, studentNameTemp);
			studentList.add(studentTemp);
		}
		
		
		return studentList;
	}

}
