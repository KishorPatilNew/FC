package tests.fakeBar;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Program to detect fake gold bar using minimum number of weighings for any possible fake bar position with given nine fake bars
 * 
 * Algorithm:
 * The best possible algorithm is divide 9 bars in 3 groups. this gives maximum and minimum 2 weighings to find any possible position of fake bar.
 * Step 1: Weigh first two groups, if they weigh same, then the fake bar exists in third group.
 * 		  If first group weighs more than second, then the fake bar exists in second group.
 *        If first group weighs less than second, then the fake bar exists in first group.
 * Step 2: Step 1 gives the Group which weighs less. 
 * 			Now from this group; weigh first two numbers in this group, if they weigh same, then the fake bar is third number.
 *          if first number is greater than second, then the fake bar is second number.
 *          if first number is less than second, then the fake bar is first number.
 * Step 3: Step 2 confirms which number is the fake bar.
 * This way, It needs minimum and maximum two weighings to find the fake gold bar at any position.
 * 
 * 
 * UI automation:
 * Application is automated using Selenium Webdriver and Java as scripting language.
 * 
 * @author Kishori Patil
 *
 */
public class DetectFakeBar {
	private static String applicationUrl = "http://sdetchallenge.fetch.com/";
	private static WebDriver driver = null;
	private static String chromeDriverFilePath = "\\src\\main\\resources\\chromedriver.exe";
	
	/**
	 * Weighings challenge program
	 * @param args
	 */
	public static void main(String[] args) {
		String result = null;
		int index = -1;
		
		//launch application
		openApplication();
		
		//Gold bars are divided in three groups
		//Group1 is indices 0,1,2
		//Group2 is indices 3,4,5
		//Group3 is indices 6,7,8
		
		// First Weigh Group1 and Group2 bars
		enterValueIntoCell(0, "left", 0);
		enterValueIntoCell(1, "left", 1);
		enterValueIntoCell(2, "left", 2);
		enterValueIntoCell(0, "right", 3);
		enterValueIntoCell(1, "right", 4);
		enterValueIntoCell(2, "right", 5);
		//click Weigh button and read Result 
		clickWeighButton();
		result = readResult();
		
		//Reset bowls for next weighing
		clickResetButton();
		
		if(result.equals("=")) { //if Group1 and Group2 are equal then the fake bar exists in Group3
			//Group3
			index = weighGroupElements(6, 7, 8);
		} else if(result.equals(">")) { //if Group1 is greater than Group2 then the fake bar exists in Group2
			//Group2
			index = weighGroupElements(3, 4, 5);
		} else { //fake bar exists in Group1
			//Group1 
			index = weighGroupElements(0, 1, 2);
		}
		
		//Confirm the fake bar
		System.out.println("***************** Program Output ******************************");
		System.out.println("Fake Gold Bar is Number :"+index);
		clickFakeGoldBarButton(index);
		
		//Read alert and output it
		readAlertMessage();
		
		// Read the list of weighings and output them
		readWeighings();
		
		// close driver session
		driver.quit();
		System.out.println("**** Program Ends!");
	}
	
	/**
	 * Read Weighings
	 */
	private static void readWeighings() {
		List<WebElement> weighingsElements = driver.findElements(By.xpath("//div[text()='Weighings']/parent::div//li"));
		System.out.println("Weighings:");
		for(WebElement e : weighingsElements) {
			System.out.println(e.getText());
		}
	}
	
	/**
	 * Read Alert message
	 */
	private static void readAlertMessage() {
		Alert a = driver.switchTo().alert();
		String alertMessage = a.getText();
		a.dismiss();
		System.out.println("Alert message: " +alertMessage);
	}
	
	/**
	 * Click fake gold bar button with given number
	 * @param number : The index of the bar
	 */
	private static void clickFakeGoldBarButton(int number) {
		driver.findElement(By.xpath("//div[@class='coins']//button[text()='"+number+"']")).click();
	}
	
	/**
	 * Weigh the two elements out of three elements using weighing scale to find the index of the fake bar which weigh less
	 * @param first :Index of first bar in group of three
	 * @param second :Index of second bar in group of three
	 * @param third :Index of third bar in group of three
	 * @return :Index of the fake bar which weigh less
	 */
	private static int weighGroupElements(int first, int second, int third) {
		int finalIndex = -1;
		//weigh first two elements
		enterValueIntoCell(0, "left", first);
		enterValueIntoCell(0, "right", second);
		//click Weigh button and read Result 
		clickWeighButton();
		String result = readResult();
		if(result.equals("=")) { //if first two elements are equal, then third element is fake bar
			finalIndex = third;
		} else if (result.equals(">")) { //if first is greater than second, then second element is fake bar
			finalIndex = second;
		} else { //else first element is fake bar
			finalIndex = first;
		}
		return finalIndex;
	}
	
	/**
	 * Enters given value into the bowl identified by it's given side and the position
	 * @param position :Position of the cell in bowl
	 * @param side : Bowl side, left or right
	 * @param value : Gold bar Number to be entered in the cell/bowl position 
	 */
	private static void enterValueIntoCell(int position, String side, int value) {
		String cellLocator = "//input[@data-index='"+position+"' and @data-side='"+side+"']";
		driver.findElement(By.xpath(cellLocator)).sendKeys(value+"");
	}
	
	/**
	 * Clicks Weigh button and waits until weighing is done
	 */
	private static void clickWeighButton() {
		//read Weighings size before click on Weigh button.
		int count = driver.findElements(By.xpath("//div[text()='Weighings']/parent::div//li")).size();
		
		driver.findElement(By.id("weigh")).click();
		//Wait until Weighing is done
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[text()='Weighings']/parent::div//li"), count+1));
	}
	
	/**
	 * Reads the Result of weighing
	 * @return
	 */
	private static String readResult() {
		String resultButtonLocator = "//div[@class='result']//button[@id='reset']";
		return driver.findElement(By.xpath(resultButtonLocator)).getText();
	}
	
	/**
	 * Clicks Reset button
	 */
	private static void clickResetButton() {
		driver.findElement(By.xpath("//button[@id='reset' and text()='Reset']")).click();
	}
	
	
	private static void openApplication() {
		String dir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", dir+chromeDriverFilePath);
		driver = new ChromeDriver();
		
		//System.setProperty("webdriver.gecko.driver", "C:\\Kishori_Jan2024\\Workspace\\FC\\src\\main\\resources\\geckodriver.exe");
		//driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(applicationUrl);
	}

}
