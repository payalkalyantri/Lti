package Test;
import java.beans.FeatureDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.text.Utilities;

import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.net.Urls;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageObjects.myaccount;
import Utility.FetchExcelDataSet;
import Utility.Read_XLSX;
import Utility.SuiteBase;
import Utility.SuiteUtility;
import Utility.TestResultStatus;
import Utility.TimeLoadUtility;
import Utility.WriteTestResults;
import net.bytebuddy.implementation.bind.annotation.Super;
@Listeners(Utility.ScreenShotUtility.class)
public class myaccountTC extends SuiteBase {
	public Read_XLSX TestCaseListExcelsearchContains=null;
	public Read_XLSX MasterRuleSheetData=null;
	//public Read_XLSX MasterRuleSheetData_Allocations=null;
	public Properties Config=null;
	public int DataSet=-1;
	Read_XLSX FilePath=null;
	LinkedHashMap<String, String>Result=new LinkedHashMap<String, String>();
	public HashMap<String, String>URLs=null;
	public boolean TestSkip=false;
	public double start,end,total;
	private DecimalFormat df=new DecimalFormat("#.##");
	public boolean TestFail=false;;
	@BeforeClass(alwaysRun=true)
	public void SetUp() {
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FilePath=MasterRuleSheetData_Allocations;
		URLs=MasterRuleSheetData_Allocations.getEnvUrl("Environment");
		//listofRules=MasterRuleSheetData_Allocations.get
	}
	//WriteTestResults wr=new WriteTestResults();
	
  @Test(dataProvider="Linkage",dataProviderClass=Utility.Xlsdataprovider.class)
  public void Linkage_Test1(LinkedHashMap<String, String>data) throws InterruptedException {
	  DataSet++;
	 String name=getData(data,"Name");
	 String age=getData(data, "Age");
	 String CaseToRun=getData(data, "CaseToRun");
	 String TestCase=getData(data, "TestCaseName");
	 
	 if(CaseToRun.equalsIgnoreCase("N")) {
		 System.out.println(TestCase+" is N so skipped");
		 TestSkip=true;
		 throw new SkipException(TestCase+"is N so skipped");
	 }
	 else {
		 Reporter.log("Error logged for test case id:tc1");
		 for(String Key:URLs.keySet()) {
			 Key=Key.replaceAll("[()]","");
			 System.out.println(URLs.get(Key));
		 LoadWebBrowser();
		 getDriver().get(URLs.get(Key));
		 start=System.currentTimeMillis();
		 myaccount obj=new myaccount();
		 obj.TC1(getDriver(),data);
		 end=System.currentTimeMillis();
		 System.out.println("Took"+(end-start)/1000000+"ms");
		 total=(end-start)/1000;
		 String totaltime=df.format(total);
		 if(URLs.size()>0) {
			 getDriver().manage().deleteAllCookies();
			 getDriver().navigate().refresh(); 
		 }
	 }
	 }
  }
  @Test(dataProvider="Linkage",dataProviderClass=Utility.Xlsdataprovider.class)
  public void Linkage_Test2(LinkedHashMap<String, String>data) throws InterruptedException {
	  DataSet++;
	 String name=getData(data,"Name");
	 String age=getData(data, "Age");
	 String CaseToRun=getData(data, "CaseToRun");
	 String TestCase=getData(data, "TestCaseName");
	 
	 if(CaseToRun.equalsIgnoreCase("N")) {
		 System.out.println(TestCase+" is N so skipped");
		 TestSkip=true;
		 throw new SkipException(TestCase+"is N so skipped");
	 }
	 else {
		 Reporter.log("Error logged for test case id:tc1");
		 for(String Key:URLs.keySet()) {
			 Key=Key.replaceAll("[()]","");
			 System.out.println(URLs.get(Key));
		 LoadWebBrowser();
		 getDriver().get(URLs.get(Key));
		 start=System.currentTimeMillis();
		 myaccount obj=new myaccount();
		 obj.TC2();
		 end=System.currentTimeMillis();
		 System.out.println("Took"+(end-start)/1000000+"ms");
		 total=(end-start)/1000;
		 String totaltime=df.format(total);
		 if(URLs.size()>0) {
			 getDriver().manage().deleteAllCookies();
			 getDriver().navigate().refresh(); 
		 }
	 }
	 }
  }
  @AfterMethod(alwaysRun = true)
  public void ReporterdataResults(ITestResult Result) {
	  TestFail=TestResultStatus.TestFail;
	 Add_Log.info("Resule.StartEndTime"+Result.getStartMillis()+ "End Time"+ Result.getEndMillis());
	 Add_Log.info("Took Method:"+(Result.getEndMillis()-Result.getStartMillis())/1000000+"ms");
	 total=((Result.getEndMillis()-Result.getStartMillis())/1000);
	 String totaltime=df.format(total);
	 Add_Log.info("Total time after method"+totaltime);
	 if(Result.getStatus()==ITestResult.SKIP){
		 TimeLoadUtility.result.put(Result.getName(), "SKIP");
		 TimeLoadUtility.timeload.put(Result.getName(), totaltime);
		 Reporter.log(Result.getName()+"is skip");
		 Add_Log.info(Result.getName()+"is skip");
	 }
	 else if(Result.getStatus()==ITestResult.FAILURE){
		 TimeLoadUtility.result.put(Result.getName(), "Fail");
		 TimeLoadUtility.timeload.put(Result.getName(), totaltime);
		 Reporter.log(Result.getName()+"is Fail");
		 Add_Log.info(Result.getName()+"is Fail");
		 if(!(getDriver()==null)) {
			 CloseBrowser();
		 }
	 }
	 else {
		 TimeLoadUtility.result.put(Result.getName(), "Pass");
		 TimeLoadUtility.timeload.put(Result.getName(), totaltime);
		 Reporter.log(Result.getName()+"is Pass");
		 Add_Log.info(Result.getName()+"is Pass");
		 if(!(getDriver()==null)) {
			 CloseBrowser();
		 }
	 }
  }
 
	
	  
	
	@AfterSuite(alwaysRun = true)
	  
	  
public void aftersuite() throws FileNotFoundException {
		FetchExcelDataSet data=new FetchExcelDataSet();
		for(Map.Entry m:TimeLoadUtility.result.entrySet()) {
			String Sheetname="Linkage";
			if(m.getKey().toString().contains("Linkage")) {
				Sheetname="Linkage";
			}
			Add_Log.info(m.getKey().toString()+":Reporting test data line"+m.getKey().toString()+"as"+m.getValue().toString());
			SuiteUtility.WriteUtility(FilePath, Sheetname, "TimeLoad", m.getKey().toString(), m.getValue().toString());
		}
		data.ReportLog("TestData","reportLog","xlsx");
	}
	 
}




