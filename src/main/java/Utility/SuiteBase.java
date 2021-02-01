package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

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
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.common.collect.Table.Cell;

public class SuiteBase {
	public static org.apache.log4j.Logger Add_Log=null;
	public Read_XLSX TestCaseListExcelsearchContains=null;
	public Read_XLSX MasterRuleSheetData=null;
	public Read_XLSX MasterRuleSheetData_Allocations=null;
	public Properties Config=null;
	Read_XLSX FilePath=null;
	public HashMap<String, String>URLs=null;
	
	public void init() throws IOException {
		
		Add_Log=org.apache.log4j.Logger.getLogger("rootLogger");
		 TestCaseListExcelsearchContains = new Read_XLSX(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	MasterRuleSheetData= new Read_XLSX(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	MasterRuleSheetData_Allocations= new Read_XLSX(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx");
	Add_Log.info("All excel files initialized successfully");
	Config=new Properties();
	FileInputStream fip=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\config\\config.properties");
	Config.load(fip);
	Add_Log.info("Config.properties file loaded successfully");
	SeleniumUtils.cleanDirectory(System.getProperty("user.dir")+"\\Download");
	FilePath=MasterRuleSheetData_Allocations;
	URLs=MasterRuleSheetData_Allocations.getEnvUrl("Environment");
	for(String Key:URLs.keySet()) {
		System.out.println(URLs.keySet());
		String url=URLs.get(Key);
			/*
			 * String [] urlArr=url.split("sm_user="); if(null != urlArr[1]) { String
			 * []tempArr=urlArr[1].split("&"); if(null!=tempArr[0].replace("&", "")); }
			 */
	}
	}
	
	
	HashMap<Integer, LinkedHashMap<String, String>> hashDataSet=new HashMap<Integer, LinkedHashMap<String,String>>();
	public WebDriver ExistingChromeBrowser;
	InheritableThreadLocal<WebDriver> driver= new InheritableThreadLocal<WebDriver>();
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
	public void CloseBrowser() {
		getDriver().close();
		getDriver().quit();
	}
	public void LoadWebBrowser() {
		if(Config.getProperty("testBrowser").equalsIgnoreCase("chrome")&& ExistingChromeBrowser==null) {
			driver.set(ExistingChromeBrowser);
		}
		if(Config.getProperty("testBrowser").equalsIgnoreCase("chrome")&& ExistingChromeBrowser==null) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--headless");
			DesiredCapabilities cap=new DesiredCapabilities().chrome();
			options.addArguments("--no-sandbox");  // Bypass OS security model
			options.addArguments("--disable-gpu"); // applicable to windows os onlyWebDriver driver = new ChromeDriver();
			options.addArguments("--start-maximized");
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WINDOWS);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver.set(new ChromeDriver(options));
			Add_Log.info("Chrome driver is loaded");
		}
		
	}
	public WebDriver getDriver() {
		return driver.get();
	}
	public HashMap<Integer, LinkedHashMap<String, String>> makeTestData(String strExcelPath, String sheetName,String testCasename) throws IOException {
		
		try {
			FileInputStream excelFileStream=new FileInputStream(strExcelPath);
			XSSFWorkbook excelWorkbook= new XSSFWorkbook(excelFileStream);
			XSSFSheet excelSheet = excelWorkbook.getSheet(sheetName);
			int numRows=excelSheet.getLastRowNum();
			int columnIndex=-1;
			for(int count=0;count<numRows;count++) {
				if(excelSheet.getRow(count).getCell(0).getStringCellValue().equalsIgnoreCase(testCasename.trim())) {
					columnIndex=count;
					int maxColumn=excelSheet.getRow(columnIndex).getLastCellNum();
					for(int i=0;i<1;i++) {
						
						hashDataSet.put(i,getRowData(excelSheet,columnIndex));
					}
					System.out.println(hashDataSet.get(1));
					break;
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
}
