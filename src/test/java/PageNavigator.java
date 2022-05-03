import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PageNavigator{
	private String homeAddress = "/html/body/header/nav/ul/li[1]/a";
	private String folderAddress = "/html/body/header/nav/ul/li[2]/a";
	private String calendarAddress = "/html/body/header/nav/ul/li[3]/a";
	private String notesAddress = "/html/body/header/nav/ul/li[6]/a";
	private String glossaryAddress = "/html/body/header/nav/ul/li[8]/a";
	private String trainerAddress = "/html/body/header/nav/ul/li[9]/a";
	private WebDriver driver;
	private WebDriverWait wait;
	private XPT xp; 
	
	public PageNavigator(WebDriver driver, WebDriverWait wait, XPT xp){
		this.driver = driver;
		this.wait = wait;
		this.xp = xp;		
	}

	private void navigateToAddress(String address){
		if(address.equals("home")){
			xp.clickXPath(homeAddress);
		}else if(address.equals("folder")){
			xp.clickXPath(folderAddress);
		}else if(address.equals("calendar")){
			xp.clickXPath(calendarAddress);
		}else if(address.equals("notes")){
			xp.clickXPath(notesAddress);
		}else if(address.equals("glossary")){
			xp.clickXPath(glossaryAddress);
		}else if(address.equals("trainer")){
			xp.clickXPath(trainerAddress);
		}
	}

	public void navigateHome(){
		navigateToAddress("home");
	}

	public void navigateToFolder(){
		navigateToAddress("folder");
	}

	public void navigateToCalendar(){
		navigateToAddress("calendar");
	}

	public void navigateToNotes(){
		navigateToAddress("notes");
	}

	public void navigateToGlossary(){
		navigateToAddress("glossary");
	}

	public void navigateToTrainer(){
		navigateToAddress("trainer");
	}
} 
