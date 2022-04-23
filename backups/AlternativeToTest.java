import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.TimeUnit;

public class AlternativeToTest {

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

    @Test
    public void multiplicationTest() throws InterruptedException {
        driver.get("https://alternativeto.net/"); // Open AlternativeTo

		////////////////////////////////////// Logging in //////////////////////////////////////
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // Wait for the cookie consent to appear
		driver.findElement(By.xpath("//*[@id='main-body']/div[3]/div[2]/div[1]/div[2]/div[2]/button[1]")).click(); // Click on okay I consent 
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	 // Wait for the cookie banner to go away
		driver.findElement(By.xpath("//a[@href='/account/login']")).click(); // Click on the login page
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	 // Wait for the login screen						
		WebElement usernameElement = waitVisibiiltyAndFindElement(By.xpath("//*[@id='username']"));
        usernameElement.sendKeys("daniel.kuknyo@mailbox.org"); // Input username		

		WebElement passwordElement = waitVisibiiltyAndFindElement(By.xpath("//*[@id='password']")); // Input password
        passwordElement.sendKeys("TestPassword1");

		driver.findElement(By.xpath("/html/body/main/section/div/div/div/form/div[2]/button")).click(); // Click on the Continue login button
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		

		////////////////////////////////////// Sending a form as a logged in user ////////////////////////////////////////
		//WebElement profileButton = waitVisibiiltyAndFindElement(findPassword);
        driver.get("https://alternativeto.net/"); // Open AlternativeTo
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='__next']/div/header/div[1]/div/div/span/span[3]/button/div/svg/path")).click(); // Profile button (drop-down)
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='main-body']/div[5]/div/span/ul/li[1]/a")).click(); // Profile menu
		waitVisibiiltyAndFindElement(By.xpath("//*[@id='mainContent']/div[2]/div/div/div[3]/div/a")).click(); // Edit profile button		
		
		//  Delete all content from bio textarea and input some text
		WebElement bioInputBox = waitVisibiiltyAndFindElement(By.xpath("//*[@id='bio']")); // Input password
		bioInputBox.clear();
        bioInputBox.sendKeys("Hi, my name's Daniel Kuknyo and I have chosen this site as my Software Testing project.");				
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}