package ApplicationTestingTestFiles;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ApplicationTestingProcessFiles.NumadicCarrersProcess;
import abstractBase.AbstractBasePage;
import abstractBase.TestDataFile;

public class VeryListOfItemsTest {
	
	WebDriver driver;
	
	public String ApplicationURL = null;
	
	public static final String excelDataFileName = "LaunchBrowserTestData.xlsx";
	public static final String excelDataSheetName = "AppBasicData";
	
	public static final String testDataFileName = "TestData.xlsx";
	public static final String testDataSheetName = "TestData";
	
	public static final String columnByText = "Data";
	
	TestDataFile testDataFile = new TestDataFile(driver);
	
	@BeforeTest
	public void brwsrLaunch() throws Exception {
		String webDriver = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriver");
		String webDriverPath = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "webDriverPath");
		ApplicationURL = testDataFile.readData(excelDataFileName, excelDataSheetName, columnByText, "Url");
		
		Reporter.log("Launch Chrome WebDriver");
		System.setProperty(webDriver, webDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.launchApplication(ApplicationURL);
	}
	
	
	@Test
	public void test() throws Exception {
		Reporter.log("<h1>TEST2</h1>");
		Reporter.log("<h2>Verify and Validate the List. </h2>");
		Reporter.log("Navigate to the Home Page");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		
		Reporter.log("<h3>In the test 2 div, assert that there are 3 values in the list group. </h3>");
		List<WebElement> elements = driver.findElements(By.xpath("//ul[@class='list-group']/li"));
		int numberOfItems = elements.size();
		Reporter.log("The Number of Items in the list. " + numberOfItems);
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String listOfItems=abstractBasePage.captureScreenShot("listOfItems.png");
		Reporter.log("<img src=\"" + listOfItems+"\"/>");
		
		Reporter.log("<h3>Assert that the second list items value is set to \"List Item 2\". </h3>");
		String ItemValue = testDataFile.readData(testDataFileName, testDataSheetName, "ItemValue", "Test2");
		int itemNum=0;
		String actualValue = "";
		for(int i=0;i<elements.size();i++) {
			itemNum=i+1;
			actualValue = elements.get(i).getText();
			if(itemNum==2) {
				break;
			}
		}
		Assert.assertTrue(actualValue.contains(ItemValue), "validated the value of Item 2.");
		
		String expectedBadgeValue = testDataFile.readData(testDataFileName, testDataSheetName, "BadgeValue", "Test2");
		String actualBadgeValue = driver.findElement(By.xpath("//ul[@class='list-group']/li["+itemNum+"]/span")).getText();
		Assert.assertTrue(actualBadgeValue.equalsIgnoreCase(expectedBadgeValue), "Validated the badge value of item 2. ");
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String valueOfItem2=abstractBasePage.captureScreenShot("valueOfItem2.png");
		Reporter.log("<img src=\"" + valueOfItem2+"\"/>");
		
		Reporter.log("Close the browser. ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
