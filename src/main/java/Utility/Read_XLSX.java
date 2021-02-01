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
import org.apache.poi.ss.formula.SheetNameFormatter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.common.collect.Table.Cell;

public class Read_XLSX {
	public String filelocation=null;
	public FileInputStream ipstr=null;
	public XSSFWorkbook wb=null;
	public XSSFSheet ws=null;
	public FileOutputStream opstr=null;

	public Read_XLSX(String filelocation) throws IOException {
		this.filelocation=filelocation;
		 try {
			ipstr = new FileInputStream(filelocation);
			wb=new XSSFWorkbook(ipstr);
			ws=wb.getSheetAt(0);
			ipstr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String> getEnvUrl(String Wsname) {
		
		try {
			int rowNum=retrieveNoofRows(Wsname);
			HashMap<String, String>UrlList=new HashMap<String, String>();
			for(int i=0;i<rowNum;i++) {
				XSSFRow row=ws.getRow(i);
				if(row.getCell(2).getStringCellValue().equalsIgnoreCase("Y")) {
					UrlList.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
				}
			}
			return UrlList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public int retrieveNoofRows(String wsname) {
		int SheetIndex=wb.getSheetIndex(wsname);
		if(SheetIndex==-1) {
			return 0;
		}
		else {
			ws=wb.getSheetAt(SheetIndex);
			int rowcount=ws.getLastRowNum()+1;
			return rowcount;
		}
		
	}

	public boolean WriteResultTC(String sheetname, String columnname, String testcase, String result) throws FileNotFoundException {
		try {
			int SheetIndex=wb.getSheetIndex(sheetname);
			if(SheetIndex==-1)
			return false;
			int ColNum=RetrieveNoOFColumns(sheetname);
			int ColumnNum=-1;
			int Rowno=retrieveNoofRows(sheetname);
			int RowNum=-1;
			for(int i=0;i<Rowno;i++) {
				XSSFRow row=ws.getRow(i);
				if(row.getCell(0).getStringCellValue().equalsIgnoreCase(testcase.trim())) {
					RowNum=i;
				}
			}
			XSSFRow SuiteRow=ws.getRow(0);
			for(int j=0;j<ColNum;j++) {
				if(SuiteRow.getCell(j).getStringCellValue().equalsIgnoreCase(columnname.trim())) {
					ColumnNum=j;
				}
			}
			if(ColNum==-1) {
				return false;
			}
			XSSFRow Row=ws.getRow(RowNum);
			XSSFCell cell=Row.getCell(ColumnNum);
			if(cell==null) {
				Row.createCell(ColumnNum);
			}
			cell.setCellValue(result);
			if(result.equalsIgnoreCase("Pass")) {
				XSSFCellStyle style=wb.createCellStyle();
				style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style);
				XSSFFont font=wb.createFont();
				font.setColor(IndexedColors.BLACK.getIndex());
				style.setFont(font);
				cell.setCellStyle(style);
			}
			else if(result.equalsIgnoreCase("Fail")) {
				XSSFCellStyle style=wb.createCellStyle();
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style);
				XSSFFont font=wb.createFont();
				font.setColor(IndexedColors.BLACK.getIndex());
				style.setFont(font);
				cell.setCellStyle(style);
			}
			else  {
				XSSFCellStyle style=wb.createCellStyle();
				style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cell.setCellStyle(style);
				XSSFFont font=wb.createFont();
				font.setColor(IndexedColors.BLACK.getIndex());
				style.setFont(font);
				cell.setCellStyle(style);
			}
			opstr=new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int RetrieveNoOFColumns(String Sheetname) {
		int SheetIndex=wb.getSheetIndex(Sheetname);
		if(SheetIndex==-1) {
		return 0;
		}
		else {
			ws=wb.getSheetAt(SheetIndex);
			return ws.getRow(0).getLastCellNum();
		}
	}
}
