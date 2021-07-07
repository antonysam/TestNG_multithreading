package library;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.JavascriptExecutor;

public class Golden_template {
	static WebDriver driver;
	static Actions act;

	// Opens the browser
	public static void openbrowser(String browsername) {

		switch (browsername) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\samdany\\Desktop\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		default:
			break;
		}
	}

	// Enters the URL
	public static void getURL(String URL, int PageLoad_Time_Seconds, int Implicit_Wait_Time_Seconds) {
		if (URL != " ") {
			driver.get(URL);
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(PageLoad_Time_Seconds, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(Implicit_Wait_Time_Seconds, TimeUnit.SECONDS);
		}
	}

	// Explicitly wait for webelement
	public static void explicitWait(By Locator, int Wait_time) {
		WebDriverWait wait = new WebDriverWait(driver, Wait_time);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
	}

	// Fluent wait
	public static Wait<WebDriver> fluentWait(int Wait_time, int Poll_time) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Wait_time, TimeUnit.SECONDS)
				.pollingEvery(Poll_time, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		return wait;
	}

	// Static wait
	public static void staticwait(int Static_Wait_Time) throws InterruptedException {
		Thread.sleep(Static_Wait_Time);
	}

	public static WebElement getElement(By locator) {
		return driver.findElement(locator);

	}

	// Validates the given Page title by expected title
	public static void ValidatePageTitle(String ExpectedTitle) {
		String ActualTitle = driver.getTitle();
		if (ActualTitle.equals(ExpectedTitle)) {
			System.out.println("The actual and expected titles are same. The title is: " + ActualTitle);
		} else {
			System.out.println("The actual and expected text are same. The text is: " + ActualTitle);
		}
	}
	
	// Returns the PageTitle
	public static String ValidatePageTitle() {
		String ActualTitle = driver.getTitle();
		return ActualTitle;
	}

	// Validates the given text by expected text
	public static void ValidateText(By locator, String ExpectedText) {
		WebElement element = Golden_template.getElement(locator);
		String ActualText = element.getText();
		if (ActualText.equals(ExpectedText)) {
			System.out.println("The actual and expected text are same. The text is: " + ActualText);
		} else {
			System.out.println("The actual and expected text are not same. The text is: " + ActualText);
		}
	}
	
	//Returns the Text
	public static String Validatetext(By locator) {
		WebElement element = Golden_template.getElement(locator);
		String ActualText = element.getText();
		return ActualText;
	}

	// Takes Screen Shot
	public static void takescrshot(String imgname, String dest) throws IOException {
		try {
			TakesScreenshot scrnshot = (TakesScreenshot) driver;
			File src = scrnshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(dest + imgname + ".png"));
			System.out.println("Screenshot taken");
		} catch (WebDriverException e) {

			System.out.println("Exception while taking screenshot" + e.getMessage());
		}

	}

	// Performs Java script enter text
	public static void Js_enter_text(By locator, String Text) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		WebElement element = getElement(locator);
		js.executeScript("arguments[0].value=arguments[1]", element, Text);

	}

	// Performs Java script click
	public static void Js_click(By locator) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		WebElement element = getElement(locator);
		js.executeScript("arguments[0].click()", element);

	}

	// Performs mouse events
	public static void mouseevent(By locator, String mouseoperation) {
		act = new Actions(driver);

		switch (mouseoperation) {
		case "contextclick":
			act.contextClick(getElement(locator)).build().perform();
			break;
		case "doubleclick":
			act.doubleClick(getElement(locator)).build().perform();
			break;
		case "moveToElement":
			act.moveToElement(getElement(locator)).build().perform();
			break;
		default:
			System.out.println("select a valid criteria for mouse action");
			break;
		}

	}

	// Performs keyboard events
	public static void keyboardevent(String keyboardoperation) {
		act = new Actions(driver);

		switch (keyboardoperation) {
		case "Scrolldown":
			act.sendKeys(Keys.PAGE_DOWN).build().perform();
			break;
		case "Scrollup":
			act.sendKeys(Keys.PAGE_UP).build().perform();
		default:
			System.out.println("select a valid criteria for Keyboard action");
			break;
		}
	}

	// Selects the Drop down present inside the select tag
	public static void selectDropDown(By locator, String value, String type) {
		Select select = new Select(getElement(locator));

		switch (type) {
		case "index":
			select.selectByIndex(Integer.parseInt(value));
			break;

		case "value":
			select.selectByValue(value);
			break;
		case "visibletext":
			select.selectByVisibleText(value);
			break;
		default:
			System.out.println("select a valid criteria for drop down");
			break;
		}
	}

	// Selects a Bootstrap drop down
	public static void selectBootstrapDropDown(By dropdown_locator, By dropdownlist_locator, String selection_text) {
		// List <WebElement>list = getBootstrapDropDown_Options(dropdown_locator,
		// dropdownlist_locator);
		driver.findElement(dropdown_locator).click();
		List<WebElement> list = Golden_template.driver.findElements(dropdownlist_locator);

		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i).getText());
			if (list.get(i).getText().contains(selection_text)) {
				list.get(i).click();
				break;
			}
		}

	}

	// Gets the options from the Drop Down
	public static List<WebElement> getBootstrapDropDown_Options(By dropdown_locator, By dropdownlist_locator) {
		driver.findElement(dropdown_locator).click();
		List<WebElement> list = Golden_template.driver.findElements(dropdownlist_locator);
		System.out.println("No of options in the Drop Down :" + list.size());

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getText());
		}
		return list;
	}

	// Gets the options from the Check box
	public static void getcheck_box_options(By Checkbox_Locator) {
		List<WebElement> checkbox_list = driver.findElements(Checkbox_Locator);

		for (int i = 0; i < checkbox_list.size(); i++) {
			System.out.println(checkbox_list.size());
			System.out.println(checkbox_list.get(i).getText());
		}
	}

}
