//////////////////////////// Made by Daniel Kuknyo //////////////////////////////////////////////////
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;

////////////////////////// This the test suite for Coospace testing ////////////////////////////////         
public class CoospaceTest{

    private WebDriver driver;
    private WebDriverWait wait;
	private XPT xp;
	private PageNavigator navi;

    @Before
    public void setup() {
    	SetupHandler handler = new SetupHandler();
		this.driver = handler.getDriver();
		this.wait = handler.getWait();
		this.xp = handler.getNewNavigationToolbox();	
		this.navi = handler.getPageNavigator();
		handler.executeHideIdentity();
	}

    @Test
    public void fileOperationsDownloadTest() throws InterruptedException {
        String pageURL = "https://coospace.uni-bge.hu/CooSpace";
		driver.get(pageURL); // Open Coospace

		/////////////////////////////////////////// Login ///////////////////////////////////////////////////
		LogHandler logger = new LogHandler(driver, wait, pageURL);
		logger.loginValidUser();	

		//////////////////////////////// Download some files from page //////////////////////////////////////
		this.navi.navigateToFolder(); // Go to my folder
		xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[2]/div[1]/a"); // Download 4.pf
		xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[4]/div[1]/a"); // Download another pdf
		xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[5]/div[1]/a"); // Download another pdf

		///////////////////////////// Multitest from extendable arraylist ///////////////////////////////////
        PageTester pt = new PageTester();
		ArrayList<String> runTests = new ArrayList<>(Arrays.asList("document.readyState", "document.cookie", "document.fullscreenEnabled", "document.inputEncoding", "document.title", "document.baseURI")); // Define an array to test
		ArrayList<String> testResults = pt.checkPageMultiFromArray(driver, runTests); // Call the function that runs these texts with a JavaScriptExecutor

		for(int i=0; i<testResults.size(); i++){
			System.out.println(testResults.get(i));
 		}
		                            	
		////////////////////////////////////////// Logout ///////////////////////////////////////////////////
		this.navi.navigateHome();
		logger.logout();
    	System.out.println("Logout done");
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

    @Test
    public void changeUserPreferencesAfterLogin() throws InterruptedException {
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

 
