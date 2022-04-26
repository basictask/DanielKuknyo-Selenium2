import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;

public class pageTester{
	
	public pageTester(WebDriver driver, WebDriverWait wait){                      	
	
	}

	public boolean checkPageLoaded(WebDriver drv, String pageURL){ // Check if the page has loaded based on the page URL
		JavascriptExecutor j = (JavascriptExecutor) drv;
      	j.executeScript("return document.readyState").toString().equals("complete");
		String s = drv.getCurrentUrl(); // Current URL
		
		// Checking if the loaded URL equals the given one
		if (s.equals(pageURL)){
			return true;
		}else{
			System.out.println("Page did not load");
			return false;
		}
	}

	public ArrayList<String> checkPageMultiFromArray(WebDriver drv, ArrayList<String> commandsToCheck){ // Multiple static page tests from array
		JavascriptExecutor j = (JavascriptExecutor) drv;
		ArrayList<String> result = new ArrayList<String>();
		
		int n = commandsToCheck.size();
		for(int i=0; i<n; i++){
			String commandToExec = "return " + commandsToCheck.get(i); 
			String resultCommand = j.executeScript(commandToExec).toString(); // Execute the command reveived through the parameters
			
			if(!resultCommand.isEmpty() && !resultCommand.equals("")){
				result.add(commandsToCheck.get(i) + " --> " + resultCommand);
			}else{
				result.add(commandsToCheck.get(i) + " --> Undefined");
			}
		}

		return result;
	}	
} 
