import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;

////////////////////////////////////// This is a class toolbox to aid page navigation ///////////////////////////////////
public class XPT {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public XPT(WebDriver driver, WebDriverWait wait){
		this.driver = driver;
		this.wait = wait;		
	}

    public WebElement waitVisibiiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }
	
	public void clickXPath(String xpathID){  // Clicks the WebElement with the given XPath
		waitVisibiiltyAndFindElement(By.xpath(xpathID)).click();		
	}

	public void typeXPath(String xpathID, String keysToSend){
		waitVisibiiltyAndFindElement(By.xpath(xpathID)).sendKeys(keysToSend); // Types a string into the WebElement with the given XPath	
	}

	public void typeName(String name, String keysToSend){ // Types a string into the WebElement with the given name
		waitVisibiiltyAndFindElement(By.name(name)).sendKeys(keysToSend);
	}

	public boolean checkPageLoaded(WebDriver drv, String pageURL) { // Check if the page has loaded based on the page URL
		JavascriptExecutor j = (JavascriptExecutor) drv;
      	j.executeScript("return document.readyState").toString().equals("complete");
		// get the current URL
		String s = drv.getCurrentUrl();
		// checking condition if the URL is loaded
		if (s.equals(pageURL)) {
			return true;
		} else {
			System.out.println("Page did not load");
			return false;
		}
	}		               	
} 
