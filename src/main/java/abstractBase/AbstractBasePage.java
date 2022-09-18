package abstractBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AbstractBasePage {
	
	WebDriver driver;
	
	public AbstractBasePage(WebDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }
	
	 public static @FindBy (id = "inputEmail")
		WebElement emailId;
	 
	 public static @FindBy (id = "inputPassword")
		WebElement password;
	 
	 public static @FindBy (xpath = "//*[@class='btn btn-lg btn-primary btn-block']")
		WebElement signIn;
	 
	 public static @FindBy (id = "dropdownMenuButton")
		WebElement Option;
	 
	 public static @FindBy (xpath = "//*[@class='dropdown-menu show']/a[contains(text(),'Option 3')]")
		WebElement Option3;
	 
	 public static @FindBy (xpath = "//*[@id='test-4-div']/button[@class='btn btn-lg btn-primary']")
		WebElement Button1;
	 
	 public static @FindBy (xpath = "//button[@class='btn btn-lg btn-secondary']")
		WebElement Button2;
	 
	 public static @FindBy (id = "test5-button")
		WebElement Button3;
	 
	 public static @FindBy (id = "test5-alert")
		WebElement alertMessage;
	 
	 
	 public void launchApplication(String url) {
			driver.manage().window().maximize();
			driver.get(url);
		 }
	 
	 public String getWebElementText(WebElement element) {
		String text = element.getText();
		return text;
	 }
	 
	 public void clickOnElement(WebElement element) {
		 element.click();
	 }
	 
	 public void setText(WebElement element, String text) {
		 element.sendKeys(text);
	 }
	 
	 public void selectText(WebElement element, String text) {
		 Select select = new Select(element);
		 select.selectByVisibleText(text);
	 }
	 
	 public boolean verifyElementPresent(WebElement element) {
		 boolean iseElementPresent = element.isDisplayed();
		 return iseElementPresent;
		 
	 }
	 
	 public String getCurrentPageUrl() {
		String currentUrl = driver.getCurrentUrl();
		 return currentUrl;
	 }
	 
	 public void hoveringOnElement(WebElement srcElement, WebElement targetElement) throws IOException {
		Actions action = new Actions(driver);
		action.moveToElement(srcElement);
		captureScreenShot("ApplyButton.png");
		action.moveToElement(targetElement)
			.build()
			.perform();
	 }

	public String captureScreenShot(String filePath) throws IOException {
		String srcPath = "D:\\New Project\\ResolverUITest\\Screenshots\\";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(src,new File(srcPath + timestamp() + " " + filePath));
			} catch (IOException e)

			{
				System.out.println(e.getMessage());
			}
		String fileName = srcPath + timestamp() + " " + filePath;
		return fileName;
			
			
		}

		public static String timestamp() {
			return new SimpleDateFormat("yyyy-Mm-dd HH-mm-ss").format(new Date());
		}
		
		public String getAttributeValues(WebElement element, String attribute) {
			String value = element.getAttribute(attribute);
			return value;
		}
		
		public boolean verifyElementEnable(WebElement element) {
			 boolean iseElementEnable = element.isEnabled();
			 return iseElementEnable;
		 }
		 
		 public boolean verifyElementSelected(WebElement element) {
			 boolean iseElementselected = element.isSelected();
			 return iseElementselected;
		 }

		 public boolean isEmpty(WebElement element) {
			 boolean isempty = element.getText().equalsIgnoreCase("");
			 return isempty;
		 }
		 
		 public String validateBlankTextErrorMessage(WebElement element, WebElement Message) {
			 String Msg = "";
			 if(isEmpty(element)) {
				 verifyElementPresent(Message);
				 Msg = Message.getText();
			 }
			 return Msg;
		 }
		
		 public void scrollDown(int moveOffSet) {
			 JavascriptExecutor Scrool = (JavascriptExecutor) driver;
			 Scrool.executeScript("window.scrollBy(0,"+moveOffSet +")", "");
			 driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 }
		 
	 public void closeApp() {
		 driver.quit();
	 }
}
