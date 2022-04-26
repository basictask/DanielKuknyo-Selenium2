import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;

////////////////////////////////////// This is a class toolbox to aid page navigation ///////////////////////////////////
// This is the BasePage object class for the tester program. It handles clicking, navigation and typing into locator objs
public class XPT {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	public XPT(WebDriver driver, WebDriverWait wait){
		this.driver = driver;
		this.wait = wait;		
	}

    public WebElement waitVisibiiltyAndFindElement(By locator){
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    public void waitForElementToDisappear(By locator){ // Wait for an element to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
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
} 
	







