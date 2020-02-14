package function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import object.Grade;

public class ExportAnalysis {

	public static void ExportAnalysisToXlsx(List<Grade> gradeList, File outputFile) {
		
		String[] head = {"Student ID", "Final Grade", "Letter Grade"};
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet workSheet = workbook.createSheet("Sheet-1");
		
		//create the head
		XSSFRow rowHead = workSheet.createRow(0);
		for (int i = 0; i < head.length; i++) {
			XSSFCell cellHead = rowHead.createCell(i);
			cellHead.setCellValue(head[i]);
		}
		
		//add data
		for (int i = 1; i < gradeList.size() + 1; i++) {
			XSSFRow row = workSheet.createRow(i);
			
			XSSFCell cellID = row.createCell(0);
			XSSFCell cellFinal = row.createCell(1);
			XSSFCell cellLetter = row.createCell(2);
			
			cellID.setCellValue(Integer.toString(gradeList.get(i - 1).getStudentID()));
			cellFinal.setCellValue(Float.toString(gradeList.get(i - 1).getFinalGrade()));
			cellLetter.setCellValue(gradeList.get(i - 1).getLetterGrade());
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			workbook.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
