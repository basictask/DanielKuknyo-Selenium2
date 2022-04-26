/////////////////////////////////////////////////////////////////////////////////////////////////////
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.*;
import java.util.ArrayList;

////////////////////////// This class will download some files to the PC ///////////////////////////         
public class CoospaceTest_file {

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
    public void fileTest() throws InterruptedException {
        String pageURL = "https://coospace.uni-bge.hu/CooSpace";
		driver.get(pageURL); // Open Coospace

		/////////////////////////////////////////// Login ///////////////////////////////////////////////////
		LogHandler logger = new LogHandler(driver, wait, pageURL);
		logger.loginValidUser();	

		//////////////////////////////// Download some files from page //////////////////////////////////////
		xp.clickXPath("/html/body/header/nav/ul/li[2]/a"); // Go to my folder
		xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[2]/div[1]/a"); // Download 4.pf
		//xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[4]/div[1]/a"); // Download another pdf
		//xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[5]/div[1]/a"); // Download another pdf

		///////////////////////////// Multitest from extendable arraylist ///////////////////////////////////
        ArrayList<String> runTests = new ArrayList<>(Arrays.asList("document.readyState", "document.cookie", "document.fullscreenEnabled", "document.inputEncoding", "document.title", "document.baseURI")); // Define an array to test
		ArrayList<String> testResults = xp.checkPageMultiFromArray(driver, runTests); // Call the function that runs these texts with a JavaScriptExecutor

		for(int i=0; i<testResults.size(); i++){
			System.out.println(testResults.get(i));
 		}
		                            	
		////////////////////////////////////////// Logout ///////////////////////////////////////////////////
		logger.logout();
    	System.out.println("Logout done");
	}

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}

 
