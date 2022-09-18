package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class ValidationButtonEnableTest {
	
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
		Reporter.log("<h1>TEST4</h1>");
		Reporter.log("<h2>Validate the WebElement Button. </h2>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.scrollDown(650);
		Reporter.log("validate the button enable status: Enable");
		boolean button1 = abstractBasePage.verifyElementEnable(AbstractBasePage.Button1);
		boolean button2 = abstractBasePage.verifyElementEnable(AbstractBasePage.Button2);
		Assert.assertTrue(button1, "Validated the button1: Enable");
		Assert.assertTrue(!button2, "Validated the button1: disable");
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String ButtonEnable=abstractBasePage.captureScreenShot("ButtonEnable.png");
		Reporter.log("<img src=\"" + ButtonEnable+"\"/>");
		Reporter.log("Close the browser. ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
