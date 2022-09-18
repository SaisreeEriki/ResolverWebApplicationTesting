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

public class ValidateDropDownValueTest {
	
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
		Reporter.log("Navigate to Numadic Careers Page.");
		abstractBasePage.launchApplication(ApplicationURL);
	}
	
	
	@Test
	public void test() throws Exception {
		Reporter.log("<h1>TEST3</h1>");
		Reporter.log("<h2>Validate Drop Down Element. </h2>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.scrollDown(500);
		Thread.sleep(5000);
		String defaultOption = abstractBasePage.getWebElementText(AbstractBasePage.Option);
		String expectedOption = testDataFile.readData(testDataFileName, testDataSheetName, "DropdownDefaultOption", "Test3");
		Assert.assertTrue(defaultOption.equalsIgnoreCase(expectedOption), "Validated Default option of the Drop down. ");
		
		/*
		 * Select Class will not work here as there is no Select Tag in DOM file. 
		 * Using Dynamic xpaths we can select the option.
		 */
		
		Reporter.log("Select Option 3 from the Select list. ");
		abstractBasePage.clickOnElement(AbstractBasePage.Option);
		Thread.sleep(4000);
		abstractBasePage.clickOnElement(AbstractBasePage.Option3);
		Thread.sleep(4000);
		String selectedOption = abstractBasePage.getWebElementText(AbstractBasePage.Option);
		String expectedOpt = testDataFile.readData(testDataFileName, testDataSheetName, "DropdownDefaultOption", "Test3_1");
		System.out.println(selectedOption);
		Assert.assertTrue(selectedOption.equalsIgnoreCase(expectedOpt), "Validated selected option as Option3. ");
		
		Reporter.log("Close the browser. ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
