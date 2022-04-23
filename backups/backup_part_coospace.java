/////////////////////////////////////////////////////////////////////////////////////////////////////
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import java.util.Random;
          
public class CoospaceTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"); // Avoid being detected
	}

    private WebElement waitVisibiiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }
	
	private void clickXPath(String xpathID){
		waitVisibiiltyAndFindElement(By.xpath(xpathID)).click();		
	}

	private void typeXPath(String xpathID, String keysToSend){
		waitVisibiiltyAndFindElement(By.xpath(xpathID)).sendKeys(keysToSend); // Input username						
	}

    @Test
    public void multiplicationTest() throws InterruptedException {
        String pageURL = "https://coospace.uni-bge.hu/CooSpace";
		driver.get(pageURL); // Open Coospace

		///////////////////////////////Check if the page is loaded //////////////////////////////////////////
		JavascriptExecutor j = (JavascriptExecutor) driver;
      	j.executeScript("return document.readyState").toString().equals("complete");
		// get the current URL
		String s = driver.getCurrentUrl();
		// checking condition if the URL is loaded
		if (s.equals(pageURL)) {
			System.out.println("Page Loaded");
			System.out.println("Current Url: " + s);
		} else {
			System.out.println("Page did not load");
			driver.quit();
		}			

		/////////////////////////////////////////// Login ///////////////////////////////////////////////////
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='username']")).sendKeys("KUKNYOD"); // Input username
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='password']")).sendKeys("Apsara383%%"); // Input username
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='loginleft']/div[1]/div[1]/input[3]")).click();		
		String title = driver.getTitle();	
		System.out.println("Title of page: " + title);
		System.out.println("Login done");		
			
        ////////////////////////////////// Change user settings by sending a form ///////////////////////////
		clickXPath("//*[@id='header1r']/div[3]/a"); // Click profile button
		clickXPath("//*[@id='header1r']/div[3]/div/ul/li[1]/a");
		clickXPath("//*[@id='btn_modify']");
		typeXPath("//*[@id='profile_data_skype_edit']", "DanielKuknyoTestMessageNotMyRealSkypeName"); // Type in my super secret skype name
		
		WebElement skypeDropDown = driver.findElement(By.xpath("/html/body/div[4]/div/section/div[2]/div/div[3]/table/tbody/tr[1]/td[3]/select")); // Select skype name dropdown  
    	Select dropdown = new Select(skypeDropDown);  		
        dropdown.selectByIndex(1);  // Set the DD value
		clickXPath("//*[@id='profile_data_savebutton']");
		System.out.println("User account change done");
		
		/////////////////////////////////// Create a folder --> Send a form /////////////////////////////////
		clickXPath("/html/body/header/nav/ul/li[1]/a"); // Go back to startpage
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='scenetreecontainer']/section/ul[2]/li/div/span[1]/a")).click();
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='widecontent']/section/section[1]/div[6]/div/h2/div[1]")).click();		        							
		waitVisibiiltyAndFindElement(By.name("Title")).sendKeys("Webteszt"); // Input folder name
		waitVisibiiltyAndFindElement(By.xpath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[3]")).click(); // Click next
		waitVisibiiltyAndFindElement(By.name("Tags[]")).sendKeys("TestingFolder"); // Input folder name		
		waitVisibiiltyAndFindElement(By.xpath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[2]")).click(); // Click Done
		System.out.println("Folder creation done");
		
		////////////////////////////////// Explicit wait and radio button ///////////////////////////////////
		clickXPath("/html/body/div[4]/div/aside/div/section[1]/ul/li[19]/a");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement notificationPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/section"))); // Explicitly wait for the notification area to show up 
		
		// This part will click random radiobuttons for the notification settings
		Random rnd = new Random();
		int high = 2;
		int low = 4;
		for(int i=1; i<8; i++){
			String radioButtonInd = String.valueOf(i);
			int whichButtonInd = rnd.nextInt(high-low)+low;
			String whichButtonToPress = String.valueOf(whichButtonInd);			
			String xpathName = "/html/body/div[4]/div/section/div[2]/div[3]/table/tbody/tr[" + radioButtonInd + "]/td[" + whichButtonToPress + "]/span";
		    clickXPath(xpathName);
		}
		
		clickXPath("/html/body/div[4]/div/section/div[2]/div[3]/div/a[2]");
						
		////////////////////////////////////////// Logout ///////////////////////////////////////////////////
		driver.get(pageURL); // Go back to startpage
		clickXPath("/html/body/div[1]/div/div[1]/div[3]"); // Click profile button
		clickXPath("//*[@id='header1r']/div[3]/div/ul/li[3]/a"); // Click logout		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Wait a little bit
    	System.out.println("Logout done");
	}

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}








