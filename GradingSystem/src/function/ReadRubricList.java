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
import object.RubricItem;

public class ReadRubricList {
	
	public static List<RubricItem> ReadRubricFromXlsx(File inputFile) {
		List<RubricItem> rubricList = new ArrayList<>();
		
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
			// TODO: handle exception
			e.printStackTrace();
		}
		
		if (workbook == null) {
			System.out.println("xlsx file loading fail, please check");
			return null;
		}
		
		XSSFSheet workSheet = workbook.getSheetAt(0);
		int rowNum = workSheet.getLastRowNum() + 1;
		
		for (int i = 0; i < rowNum; i++) {
			XSSFRow row = workSheet.getRow(i);
			
			XSSFCell rubricName = row.getCell(0);
			XSSFCell rubricPersentage = row.getCell(1);
			
			String rubricNameTemp = rubricName.getStringCellValue();
			float rubricPerTemp = (float)rubricPersentage.getNumericCellValue();
			
			RubricItem itemTemp = new RubricItem(rubricNameTemp, rubricPerTemp);
			rubricList.add(itemTemp);
			
		}
		
		
		return rubricList;
	}
	
}
