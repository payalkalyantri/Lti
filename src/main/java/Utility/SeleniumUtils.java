package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.google.common.collect.Table.Cell;

public class SeleniumUtils {
	public static org.apache.log4j.Logger Add_Log=org.apache.log4j.Logger.getLogger("rootLogger");
	public static void cleanDirectory(String directoryName) {
		File srcFile= new File(directoryName);
		try {
			FileUtils.cleanDirectory(srcFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void WaitForVisibility(WebDriver driver,By Element,String eledesc,int time) {
		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(Element));
		} catch (Exception e) {

			e.printStackTrace();
			Add_Log.info(eledesc+" took more time than expected");
			Reporter.log(eledesc+" took more time than expected");
			TestResultStatus.TestFail=true;
			Assert.fail();
		}
	}
	public static void EnterText(WebDriver driver,By locator,String text,String eledesc) {
		if(text!=null) {
			By Loader=By.xpath("//div[contains(@class,'x-mask-msg-text')]");

			try {
				WaitForInvisibilityOfLoader(driver, Loader, 60);
				WaitForVisibility(driver, locator, eledesc, 10);
				driver.findElement(locator).sendKeys(text);
				Add_Log.info(eledesc+" is entered ");
				Reporter.log(eledesc+" is entered ");
				} catch (Exception e) {

				e.printStackTrace();
				Add_Log.info(" Failed to enter "+eledesc);
				Reporter.log(" Failed to enter "+eledesc);
				TestResultStatus.TestFail=true;
				Assert.fail();
			}
		}
	}
	
	public static void Click(WebDriver driver,By locator,String eledesc) {
		
			By Loader=By.xpath("//div[contains(@class,'x-mask-msg-text')]");

			try {
				WaitForInvisibilityOfLoader(driver, Loader, 60);
				WaitForVisibility(driver, locator, eledesc, 10);
				driver.findElement(locator).click();
				Add_Log.info(eledesc+" is clicked ");
				Reporter.log(eledesc+" is clicked ");
				} catch (Exception e) {

				e.printStackTrace();
				Add_Log.info(eledesc+" is clicked ");
				Reporter.log(eledesc+" is clicked ");
				TestResultStatus.TestFail=true;
				Assert.fail();
			}
		
	}

	public static void WaitForInvisibilityOfLoader(WebDriver driver,By locator,int time) {
		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
