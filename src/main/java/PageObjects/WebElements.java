package PageObjects;

import org.openqa.selenium.By;

public interface WebElements {
	By SearchBox=By.xpath("//input[@id='twotabsearchtextbox']");
	By SearchIcon=By.xpath("//input[@id='nav-search-submit-button']");
	By SortBy=By.xpath("//span[text()='Featured']");
	By HighPrice=By.xpath("//a[text()='Price: High to Low']");
	By SecondElement=By.xpath("(//span[@class='a-size-medium a-color-base a-text-normal'])[2]");
	By ModelName=By.xpath("(//div[@data-feature-name='productOverview']//td[@class='a-span9']/span)[2]");

}