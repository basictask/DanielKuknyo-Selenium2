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
          
public class LogHandler {

    private WebDriver driver;
	private WebDriverWait wait;
	private String pageURL;
	private XPT xp;
	private String usernameField = "//*[@id='username']";
	private String passwordField = "//*[@id='password']";	
	private String loginButton = "//*[@id='loginleft']/div[1]/div[1]/input[3]";
	private String username = "KUKNYOD";
	private String pass = "Apsara383%%";
	private String profileButton = "/html/body/div[1]/div/div[1]/div[3]";
	private String logoutButton = "//*[@id='header1r']/div[3]/div/ul/li[3]/a";

	public LogHandler(WebDriver driver, WebDriverWait wait, String pageURL) {
		this.driver = driver;
		this.wait = wait;
		this.pageURL = pageURL;	
		this.xp = new XPT(driver, wait);
	}

    public void loginValidUser() throws InterruptedException { // Logs in to Coospace with my user
		xp.typeXPath(usernameField, username);
		xp.typeXPath(passwordField, pass);
		xp.clickXPath(loginButton);
		String title = driver.getTitle();	
		System.out.println("Title of page: " + title);
		System.out.println("Login done");		
	}

	public void logout(){
		this.driver.get(this.pageURL); // Go back to startpage
		xp.clickXPath(profileButton); // Click profile button
		xp.clickXPath(logoutButton); // Click logout		
	}
}