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
          
public class CoospaceTest_user {

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
    public void multiplicationTest() throws InterruptedException {
        String pageURL = "https://coospace.uni-bge.hu/CooSpace";
		driver.get(pageURL); // Open Coospace

		///////////////////////////////Check if the page is loaded //////////////////////////////////////////
		PageTester pt = new PageTester();
		boolean loaded = pt.checkPageLoaded(driver, pageURL);
		if(!loaded) {
			driver.quit();
		} 

		/////////////////////////////////////////// Login ///////////////////////////////////////////////////
		LogHandler logger = new LogHandler(driver, wait, pageURL);
		logger.loginValidUser();	
			
        ////////////////////////////////// Change user settings by sending a form ///////////////////////////
		xp.clickXPath("//*[@id='header1r']/div[3]/a"); // Click profile button
		xp.clickXPath("//*[@id='header1r']/div[3]/div/ul/li[1]/a");
		xp.clickXPath("//*[@id='btn_modify']");
		xp.typeXPath("//*[@id='profile_data_skype_edit']", "DanielKuknyoTestMessageNotMyRealSkypeName"); // Type in my super secret skype name
		
		// Select dropdown and type in a value
		WebElement skypeDropDown = driver.findElement(By.xpath("/html/body/div[4]/div/section/div[2]/div/div[3]/table/tbody/tr[1]/td[3]/select")); // Select skype name dropdown  
    	Select dropdown = new Select(skypeDropDown);  		
        dropdown.selectByIndex(1);  // Set the DD value
		xp.clickXPath("//*[@id='profile_data_savebutton']");
		System.out.println("User account change done");
		
		////////////////////////////////////////// Logout ///////////////////////////////////////////////////
		logger.logout();
	}

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}








