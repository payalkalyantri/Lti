package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.net.ssl.SSLEngineResult.Status;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.blip.JPEG;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.common.collect.Table.Cell;

public class ScreenShotUtility implements ITestListener {
	
	public String ScreenshotOnFail="yes";
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestFailure(ITestResult tr) {
		if(ScreenshotOnFail.equalsIgnoreCase("Yes")) {
			capturescreenshot(tr,"fail");
		}
		
	}
	public void capturescreenshot(ITestResult tr, String status) {
		String Destdir="";
		String passfailmethod=tr.getMethod().getRealClass().getSimpleName()+"."+tr.getMethod().getMethodName();
		File srcfile=((TakesScreenshot)((SuiteBase)tr.getInstance()).getDriver()).getScreenshotAs(OutputType.FILE);
		DateFormat df=new SimpleDateFormat("dd-MMM-yyyy__hh:mm:ssaa");
		if(status.equalsIgnoreCase("Fail")) {
			Destdir=System.getProperty("user.dir")+"\\Screenshots";
		}
		new File(Destdir).mkdirs();
		String destFile=df.format(new Date())+"Facility Id"+".jpeg";
		try {
			FileUtils.copyFile(srcfile, new File(Destdir+"/"+destFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStart(ITestContext tr) {
		System.out.println("Start");
		DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now=LocalDateTime.now();
		ExecStartTime=df.format(now);
		System.out.println(ExecStartTime);

	}
	@Override
	public void onFinish(ITestContext tr) {
		System.out.println("Execution Completed");
		DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now=LocalDateTime.now();
		ExecEndTime=df.format(now);
		System.out.println("Start time"+ExecStartTime+"End Time"+ExecEndTime );
		
	}
	public String ExecStartTime;
	public String ExecEndTime;

	

	
}
