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
    	SetupHandler handler = new SetupHandler();
		this.driver = handler.getDriver();
		this.wait = handler.getWait();
		this.xp = handler.getNewNavigationToolbox();	
		handler.executeHideIdentity();
	}

    @Test
    public void folderAndFormManipulationTest() throws InterruptedException {
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
					         
		/////////////////////////////////// Create a folder --> Send a form /////////////////////////////////
		FolderManipulator folderCreator = new FolderManipulator(this.driver, this.wait, this.xp);
		String folderName = "Webteszt";
		String tags = "This is a test folder";
		folderCreator.CreateFolder(folderName, tags);

		////////////////////////////////// Explicit wait and radio button ///////////////////////////////////
		xp.clickXPath("/html/body/div[4]/div/aside/div/section[1]/ul/li[19]/a");
		WebDriverWait wait = new WebDriverWait(driver, 20); // We will wait until the radiobutton area is completely folded down
		WebElement notificationPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[4]/div/section"))); // Explicitly wait for the notification area to show up 
		
		// This part will click random radiobuttons for the notification settings
		Random r = new Random();
		
		for(int i=1; i<8; i++){ // Iterate over all the radiobuttons
			String radioButtonInd = String.valueOf(i);
			int whichButtonInd = r.nextInt(4 - 2 + 1) + 2; // Generate a random number between 2 and 4           
			String whichButtonToPress = String.valueOf(whichButtonInd); // Pick a button randomly out of the 3
			String xpathName = "/html/body/div[4]/div/section/div[2]/div[3]/table/tbody/tr[" + radioButtonInd + "]/td[" + whichButtonToPress + "]/span"; // Assemble name for xpath (radiobutton)
		    xp.clickXPath(xpathName);
		}
		
		xp.clickXPath("/html/body/div[4]/div/section/div[2]/div[3]/div/a[2]"); // Click save
						
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








