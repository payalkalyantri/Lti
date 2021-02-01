package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;

public class WriteTestResults {
	
	
  public void WriteResultsInExcel(String strExcelPath,String sheetName,LinkedHashMap<String, String>Results) throws IOException {
	//Create an object of File class to open xlsx file
      FileInputStream inputStream = new FileInputStream(strExcelPath);

      Workbook guru99Workbook = null;

      //Find the file extension by splitting  file name in substring and getting only extension name

      String fileExtensionName = strExcelPath.substring(strExcelPath.indexOf("."));

      //Check condition if the file is xlsx file

      if(fileExtensionName.equals(".xlsx")){

      //If it is xlsx file then create object of XSSFWorkbook class

      guru99Workbook = new XSSFWorkbook(inputStream);

      }

      //Check condition if the file is xls file

      else if(fileExtensionName.equals(".xls")){

          //If it is xls file then create object of XSSFWorkbook class

          guru99Workbook = new HSSFWorkbook(inputStream);

      }    

  //Read excel sheet by sheet name    

  Sheet sheet =  guru99Workbook.getSheet(sheetName);

  //Get the current count of rows in excel file

  int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

  //Get the first row from the sheet

  Row row = sheet.getRow(0);
  int column=row.getLastCellNum();
  for(int i=0;i<column;i++) {
	  if(row.getCell(column).getStringCellValue().equalsIgnoreCase("TestResult")) {
		  column=i;
		  break;
	  }

  //Create a new row and append it at last of sheet

 // Row newRow = sheet.createRow(rowCount+1);

  //Create a loop over the cell of newly created Row

  for(int j = 1; j < sheet.getLastRowNum(); j++){
	  for(Map.Entry<String, String>entry:Results.entrySet()) {
		  if(entry.getKey().equalsIgnoreCase(sheet.getRow(j).getCell(0).getStringCellValue())) {
			  sheet.getRow(j).getCell(column).setCellValue(entry.getValue().toString());
			  break;
		  }
	  }

      //Fill data in row

     

  }

  //Close input stream

  inputStream.close();

  //Create an object of FileOutputStream class to create write data in excel file

  FileOutputStream outputStream = new FileOutputStream(strExcelPath);

  //write data in the excel file

  guru99Workbook.write(outputStream);

  //close output stream

  outputStream.close();
	
  }

  }
}
