package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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

public class FetchExcelDataSet {
	
	HashMap<Integer, LinkedHashMap<String, String>> hashDataSet=new HashMap<Integer, LinkedHashMap<String,String>>();
	public Object[][] getDataSetAsObjectrray(String strExcelPath,String SheetName,String TestCasename) throws IOException {
		HashMap<Integer, LinkedHashMap<String, String>>hashDataSet=makeTestData(strExcelPath,SheetName,TestCasename);
		Object[][] objArray=new Object[hashDataSet.size()][1];
		for(int i=0;i<hashDataSet.size();i++) {
			objArray[i][0]=getData(hashDataSet,i);
		}
		return objArray;
	}
	public String getData(LinkedHashMap<String, String> data, String key) {
		if(data.get(key)!=null && data.get(key).length()>0) {
			return data.get(key);
		}
		else {
		return null;
		}
	}
	public HashMap<Integer, LinkedHashMap<String, String>> makeTestData(String strExcelPath, String sheetName,String testCasename) throws IOException {
		XSSFSheet excelSheet=null;
		try {
			FileInputStream excelFileStream=new FileInputStream(strExcelPath);
			@SuppressWarnings("resource")
			XSSFWorkbook excelWorkbook= new XSSFWorkbook(excelFileStream);
			 excelSheet = excelWorkbook.getSheet(sheetName);
			int numRows=excelSheet.getLastRowNum();
			int columnIndex=-1;
			for(int count=0;count<excelSheet.getRow(0).getLastCellNum();count++) {
				if(excelSheet.getRow(count).getCell(0).getStringCellValue().equalsIgnoreCase("TestCaseName")) {
					columnIndex=count;
					break;
				}
			}
			for(int rowcount=1,validrows=1;rowcount<=numRows;rowcount++) {
				if(excelSheet.getRow(rowcount).getCell(columnIndex).getStringCellValue().equalsIgnoreCase(testCasename.trim())) {
						
						hashDataSet.put(validrows-1,getRowData(excelSheet,rowcount));
						validrows++;
					}
					
				}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashDataSet;
	}

	private LinkedHashMap<String, String> getRowData(XSSFSheet excelSheet, int rowCount) {
		LinkedHashMap<String, String>hashRowdata=new LinkedHashMap<String, String>();
		XSSFRow HeaderRow=excelSheet.getRow(0);
		XSSFRow row=excelSheet.getRow(rowCount);
		int TotalInputValues=row.getLastCellNum();
		for(int cellcount=0;cellcount<TotalInputValues;cellcount++) {
			XSSFCell headerCell=HeaderRow.getCell(cellcount);
			XSSFCell cell=row.getCell(cellcount, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			String cellValue=cell.getStringCellValue();
			hashRowdata.put(headerCell.getStringCellValue(), cellValue);
		}
		return hashRowdata;
	}

	private LinkedHashMap<String, String> getData(HashMap<Integer, LinkedHashMap<String, String>> hashMap, int rowNumber) {
		LinkedHashMap<String, String>hashData=null;
		hashData=hashMap.get(rowNumber);
		return hashData;
	}
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
public void ReportLog(String srcfilename, String Reportname, String extension) {
	File src=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\"+srcfilename+"."+extension);
	String Destdir=System.getProperty("user.dir")+"ReportLog";
	DateFormat datef=new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	String destfile=Reportname+"-"+datef.format(new Date())+"."+extension;
	try {
		FileUtils.copyFile(src, new File(Destdir+"/"+destfile));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
