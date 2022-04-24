/////////////////////////////////////////////////////////////////////////////////////////////////////
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.concurrent.TimeUnit;
import java.util.Random;

////////////////////////// This class will upload a file by drag and drop //////////////////////////          
public class CoospaceTest_file {

    private WebDriver driver;
    private WebDriverWait wait;
	private XPT xp;
	private String JS_DROP_FILE = "for(var b=arguments[0],k=arguments[1],l=arguments[2],c=b.ownerDocument,m=0;;){var e=b.getBoundingClientRect(),g=e.left+(k||e.width/2),h=e.top+(l||e.height/2),f=c.elementFromPoint(g,h);if(f&&b.contains(f))break;if(1<++m)throw b=Error('Element not interractable'),b.code=15,b;b.scrollIntoView({behavior:'instant',block:'center',inline:'center'})}var a=c.createElement('INPUT');a.setAttribute('type','file');a.setAttribute('style','position:fixed;z-index:2147483647;left:0;top:0;');a.onchange=function(){var b={effectAllowed:'all',dropEffect:'none',types:['Files'],files:this.files,setData:function(){},getData:function(){},clearData:function(){},setDragImage:function(){}};window.DataTransferItemList&&(b.items=Object.setPrototypeOf([Object.setPrototypeOf({kind:'file',type:this.files[0].type,file:this.files[0],getAsFile:function(){return this.file},getAsString:function(b){var a=new FileReader;a.onload=function(a){b(a.target.result)};a.readAsText(this.file)}},DataTransferItem.prototype)],DataTransferItemList.prototype));Object.setPrototypeOf(b,DataTransfer.prototype);['dragenter','dragover','drop'].forEach(function(a){var d=c.createEvent('DragEvent');d.initMouseEvent(a,!0,!0,c.defaultView,0,0,0,g,h,!1,!1,!1,!1,0,null);Object.setPrototypeOf(d,null);d.dataTransfer=b;Object.setPrototypeOf(d,DragEvent.prototype);f.dispatchEvent(d)});a.parentElement.removeChild(a)};c.documentElement.appendChild(a);a.getBoundingClientRect();return a;";

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"); // Avoid being detected
		this.xp = new XPT(driver, wait);
	}

	public void dropFile(String filePath, WebElement target) {
		JavascriptExecutor jse = (JavascriptExecutor) this.driver;

		String JS_DROP_FILE =
			"var target = arguments[0]," +
			"    offsetX = arguments[1]," +
			"    offsetY = arguments[2]," +
			"    document = target.ownerDocument || document," +
			"    window = document.defaultView || window;" +
			"" +
			"var input = document.createElement('INPUT');" +
			"input.type = 'file';" +
			"input.style.display = 'none';" +
			"input.onchange = function () {" +
			"  var rect = target.getBoundingClientRect()," +
			"      x = rect.left + (offsetX || (rect.width >> 1))," +
			"      y = rect.top + (offsetY || (rect.height >> 1))," +
			"      dataTransfer = { files: this.files };" +
			"" +
			"  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
			"    var evt = document.createEvent('MouseEvent');" +
			"    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
			"    evt.dataTransfer = dataTransfer;" +
			"    target.dispatchEvent(evt);" +
			"  });" +
			"" +
			"  setTimeout(function () { document.body.removeChild(input); }, 25);" +
			"};" +
			"document.body.appendChild(input);" +
			"return input;";

		WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, target, 0, 0);
		input.sendKeys(filePath);
	}

    @Test
    public void folderTest() throws InterruptedException {
        String pageURL = "https://coospace.uni-bge.hu/CooSpace";
		driver.get(pageURL); // Open Coospace

		/////////////////////////////////////////// Login ///////////////////////////////////////////////////
		LogHandler logger = new LogHandler(driver, wait, pageURL);
		logger.loginValidUser();	

		//////////////////////////////// Download some files from page //////////////////////////////////////
		xp.clickXPath("/html/body/header/nav/ul/li[2]/a"); // Go to my folder
		//xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[2]/div[1]/a"); // Download 4.pf
		//xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[4]/div[1]/a"); // Download another pdf
		//xp.clickXPath("/html/body/div[4]/div/section/div[4]/div/ul/li[5]/div[1]/a"); // Download another pdf
		                     	
		/////////////////////////////////// Navigate to folder //////////////////////////////////////////////		
		//driver.get(pageURL);
		//xp.clickXPath("/html/body/div[4]/div/section/div[3]/section/ul[2]/li/div/span[1]/a"); // Go to UEM folder
		//xp.clickXPath("/html/body/div[4]/div/section/section[1]/div[6]/ul/li[10]/a"); // Enter UploadTest folder	

		// File to up: C:\Users\Daniel Kuknyo\Downloads\1379361.1379371.pdf                                       
	    
		String filePath = "C:\\Users\\Daniel Kuknyo\\Downloads\\first.py";
   		WebElement target = driver.findElement(By.xpath("/html/body/div[4]/div/section/div[4]/form[1]/div/div/div[1]"));
		//WebElement target = driver.findElement(By.className("div.qq-upload-drop-area.qq-upload-drop-area-big.qq-upload-drop-area-active"));    // itt tartok
		dropFile(filePath, target);

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

 
