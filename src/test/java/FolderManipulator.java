import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

public class FolderManipulator{
	private WebDriver driver;
	private WebDriverWait wait;
	private XPT xp;

	public FolderManipulator(WebDriver driver, WebDriverWait wait, XPT xp){
		this.driver = driver;
		this.wait = wait;
		this.xp = xp;
	}

	public void CreateFolder(String title, String tags){
		xp.clickXPath("/html/body/header/nav/ul/li[1]/a"); // Go back to startpage
		xp.clickXPath("//*[@id='scenetreecontainer']/section/ul[2]/li/div/span[1]/a");
		xp.clickXPath("//*[@id='widecontent']/section/section[1]/div[6]/div/h2/div[1]");			
		xp.typeName("Title", title);
		xp.clickXPath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[3]");		
		xp.typeName("Tags[]", tags);
		xp.clickXPath("/html/body/div[5]/div[2]/div/div/div/div[2]/a[2]");
		System.out.println("Folder creation done");					
	}		
} 
