package Utility;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Xlsdataprovider {
	@DataProvider(name="Linkage")
	public static Object[][] Containssearchfetchdata(Method a) throws IOException
	{
		FetchExcelDataSet excelDataSet= new FetchExcelDataSet();
		Object[][] dataSet=excelDataSet.getDataSetAsObjectrray(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\TestData.xlsx","Linkage",a.getName());
		return dataSet;
		
	}
  
}
