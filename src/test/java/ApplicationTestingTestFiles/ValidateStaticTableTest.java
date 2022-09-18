package ApplicationTestingTestFiles;

import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellValue;
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

public class ValidateStaticTableTest {
	
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
		Reporter.log("<h1>TEST6</h1>");
		Reporter.log("<h2>Test EMail Login page. </h2>");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.scrollDown(1500);
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String pageScrollDown=abstractBasePage.captureScreenShot("pageScrollDown.png");
		Reporter.log("<img src=\"" + pageScrollDown+"\"/>");
		
		Reporter.log("Write a method that allows you to find the value of any cell on the grid. "
				+ "Use the method to find the value of the cell at coordinates 2, 2 (staring at 0 in the top left corner)");
		String cellValue = webTableValues(2, 2);
		String expectedCellValue = testDataFile.readData(testDataFileName, testDataSheetName, "CellValue", "Test6");
		Reporter.log("Assert that the value of the cell is \"Ventosanzap\". ");
		Assert.assertTrue(cellValue.equalsIgnoreCase(expectedCellValue), "validated static webtable with values. ");
		/*
		 * To capture screenshot and attach to the extend report.
		 */
		String cellValueSS=abstractBasePage.captureScreenShot("cellValueSS.png");
		Reporter.log("<img src=\"" + cellValueSS+"\"/>");
		
		Reporter.log("Close the browser. ");
	}
	
	public String webTableValues(int row, int col) {
		String cellValue="";
		if(row!=0) {
		row=row+1;
		col=col+1;
		WebElement cell = driver.findElement(By.xpath("//*[@class='table table-bordered table-dark']/tbody/tr["+row +"]/td["+col+"]"));
		cellValue=cell.getText();
		}
		else {
			WebElement cell = driver.findElement(By.xpath("//*[@class='table table-bordered table-dark']/tbody/tr["+row +"]/td["+col+"]"));
			cellValue=cell.getText();
		}
		return cellValue;
	}
	
	@AfterTest()
	public void closeApplication() {
		Reporter.log("Close all the tabs and Chrome WebDriver. ");
		AbstractBasePage abstractBasePage = new AbstractBasePage(driver);
		abstractBasePage.closeApp();
	}

}
