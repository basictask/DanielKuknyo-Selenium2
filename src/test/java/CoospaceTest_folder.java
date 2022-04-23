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
          
public class CoospaceTest_folder {

    private WebDriver driver;
    private WebDriverWait wait;
	private XPT xp;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"); // Avoid being detected
		this.xp = new XPT(driver, wait);
	}

    @Test
    public void folderTest() throws InterruptedException {
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
		LogHandler logger = new LogHandler(driver, wait, pageURL);
		logger.loginValidUser();	
					         
		/////////////////////////////////// Create a folder --> Send a form /////////////////////////////////
		xp.clickXPath("/html/body/header/nav/ul/li[1]/a"); // Go back to startpage
		xp.clickXPath("//*[@id='scenetreecontainer']/section/ul[2]/li/div/span[1]/a");
		xp.clickXPath("//*[@id='widecontent']/section/section[1]/div[6]/div/h2/div[1]");			
		xp.typeName("Title", "Webteszt");
		xp.clickXPath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[3]");		
		xp.typeName("Tags[]", "TestingFolder");
		xp.clickXPath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[2]");
		System.out.println("Folder creation done");
		
		////////////////////////////////// Explicit wait and radio button ///////////////////////////////////
		xp.clickXPath("/html/body/div[4]/div/aside/div/section[1]/ul/li[19]/a");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement notificationPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/section"))); // Explicitly wait for the notification area to show up 
		
		// This part will click random radiobuttons for the notification settings
		Random r = new Random();
		int high = 4;
		int low = 2;
		int rndRange = high - low + 1;
		System.out.println(rndRange);
		for(int i=1; i<8; i++){ // Iterate over all the radiobuttons
			String radioButtonInd = String.valueOf(i);
			int whichButtonInd = r.nextInt(rndRange) + low; // Generate a random number between 2 and 4           
			String whichButtonToPress = String.valueOf(whichButtonInd); // Pick a button randomly out of the 3
			String xpathName = "/html/body/div[4]/div/section/div[2]/div[3]/table/tbody/tr[" + radioButtonInd + "]/td[" + whichButtonToPress + "]/span"; // Assemble name for xpath (radiobutton)
		    xp.clickXPath(xpathName);
		}
		
		xp.clickXPath("/html/body/div[4]/div/section/div[2]/div[3]/div/a[2]");
						
		////////////////////////////////////////// Logout ///////////////////////////////////////////////////
		logger.logout();
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








