import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.TimeUnit;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    private WebElement waitVisibiiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    @Test
    public void multiplicationTest() throws InterruptedException {
        this.driver.get("https://alternativeto.net/"); // Open AlternativeTo

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
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}