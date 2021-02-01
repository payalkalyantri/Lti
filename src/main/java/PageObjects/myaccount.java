package PageObjects;
import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.swing.text.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.net.Urls;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import Utility.FetchExcelDataSet;
import Utility.Read_XLSX;
import Utility.SeleniumUtils;
import Utility.SuiteBase;
import Utility.TestResultStatus;
import Utility.WriteTestResults;
import net.bytebuddy.implementation.bind.annotation.Super;

public class myaccount extends SuiteBase implements WebElements  {

	public void TC1(WebDriver driver, LinkedHashMap<String, String> data) throws InterruptedException {
		String Value=getData(data,"SearchValue");
		try {
			SeleniumUtils.WaitForVisibility(driver, SearchBox, "SearchBox", 20);
			SeleniumUtils.EnterText(driver, SearchBox, Value, "SearchBox");
			SeleniumUtils.WaitForVisibility(driver, SearchIcon, "SearchIcon", 20);
			SeleniumUtils.Click(driver, SearchIcon,  "SearchIcon");
			SeleniumUtils.WaitForVisibility(driver, SortBy, "SortBy", 20);
			SeleniumUtils.Click(driver, SortBy,  "SortBy");
			SeleniumUtils.WaitForVisibility(driver, HighPrice, "HighPrice", 20);
			SeleniumUtils.Click(driver, HighPrice,  "SortBy HighPrice");
			SeleniumUtils.WaitForVisibility(driver, SecondElement, "SecondElement", 20);
			SeleniumUtils.Click(driver, SecondElement,  "SecondElement");
			SeleniumUtils.WaitForVisibility(driver, ModelName, "ModelName", 20);
			String Model=driver.findElement(ModelName).getText();
			System.out.println(Model);
			Assert.assertEquals(Model, "Nikon D850","Expected model name is not present");
		} catch (Exception e) {
			TestResultStatus.TestFail=true;
			Assert.fail();
		}
		
	}
	public void TC2() throws InterruptedException {
		try {
			Thread.sleep(10000);
			//SuiteBase sb=new SuiteBase();
			//sb.getDriver().findElement(By.xpath("//div[@id='care']")).click();;
		} catch (Exception e) {
			TestResultStatus.TestFail=true;
			Assert.fail();
		}
		
	}
}




