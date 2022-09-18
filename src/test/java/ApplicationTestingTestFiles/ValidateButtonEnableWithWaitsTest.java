package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ApplicationTestingProcessFiles.NumadicCarrersProcess;
import abstractBase.AbstractBasePage;
import abstractBase.TestDataFile;

public class ValidateButtonEnableWithWaitsTest {
	
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
		Reporter.log("<h1>TEST5</h1>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		Reporter.log("<h2>Validate the button enable after a random time. </h2>");
		Reporter.log("Navigate to the HomePage");
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String HomePage=abstractBasePage.captureScreenShot("HomePage.png");
		Reporter.log("<img src=\"" + HomePage+"\"/>");
		
		Reporter.log("ScrollDown to Test 5");
		abstractBasePage.scrollDown(850);
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String test5=abstractBasePage.captureScreenShot("test5.png");
		Reporter.log("<img src=\"" + test5+"\"/>");
		
		WebDriverWait wait = new WebDriverWait(driver,300);
		Reporter.log("wait till the element is visible and enable. ");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("test5-button")));
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String enable=abstractBasePage.captureScreenShot("enable.png");
		Reporter.log("<img src=\"" + enable+"\"/>");
		
		/*
		 * In Html souce code at div 5, Code is scripted as button is disabled instead of enable. So I have validated with isEnable() method 
		 * and asserting as not enabled (vice versa).
		 */
		Reporter.log("Validate alert message after clicking the button. ");
		abstractBasePage.clickOnElement(AbstractBasePage.Button3);
		boolean button3 = abstractBasePage.verifyElementEnable(AbstractBasePage.Button3);
		Assert.assertTrue(!button3, "Validated the button at test 5 division: Enable");
		String alert = abstractBasePage.getWebElementText(AbstractBasePage.alertMessage);
		String expectedAlertMessage = testDataFile.readData(testDataFileName, testDataSheetName, "AlertMessage", "Test5");
		Assert.assertTrue(expectedAlertMessage.equalsIgnoreCase(alert), "validated alert message displayed in div 5: " + alert);
		Reporter.log("Alert Message: " + alert);
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String alertMsg=abstractBasePage.captureScreenShot("alertMsg.png");
		Reporter.log("<img src=\"" + alertMsg+"\"/>");
		
		
		Reporter.log("Close the browser. ");
	}
	
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
