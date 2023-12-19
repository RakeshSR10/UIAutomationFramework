package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exception.FrameworkException;
import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JS_Utils js_Utils;

	static WebDriverWait driverWait;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		js_Utils = new JS_Utils(driver);
	}
	
	private void isHighlight(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			js_Utils.flash(element);
		}
	}

	public By getBy(String locatorType, String locatorValue) {

		By by = null;

		switch (locatorType.toLowerCase().trim()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "classname":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "cssselector":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			by = By.tagName(locatorValue);
			break;

		default:
			throw new FrameworkException("WRONG_LOCATOR_EXCEPTION");
		}
		return by;
	}

	public void doSendKeys(String locatorType, String locatorValue, String value) {
//		getElement(getBy(locatorType, locatorValue)).sendKeys(value);
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	@Step("entering value {1} to element : {0}")
	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	@Step("clicking on element : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}

	public String dogetElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String dogetElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);

		isHighlight(element);
		return element;
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}

	public String dogetElementAttribute(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	// Capture the text of all the page links and return List<String>
	public List<String> getElememtsTextList(By locator) {
		List<WebElement> elementList = getElements(locator);
		List<String> elementTextList = new ArrayList<String>();

		for (WebElement e : elementList) {
			String text = e.getText();

			if (text.length() != 0) {
				elementTextList.add(text);
			}

		}
		return elementTextList;
	}

	// Capture the specific attribute from the list
	public List<String> getElementsAttributeLists(By locator, String attrName) {
		List<WebElement> elementsList = getElements(locator);
		List<String> elementAttributeList = new ArrayList<String>();

		for (WebElement e : elementsList) {
			String attributeValue = e.getAttribute(attrName);
			elementAttributeList.add(attributeValue);
		}

		return elementAttributeList;
	}

	public int totalElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// Important for Interview ****************************
	public void Search(By searchField, By suggestions, String searchKey, String suggName) throws InterruptedException {

		doSendKeys(searchField, searchKey);

		Thread.sleep(4000);

		List<WebElement> autoSuggestionList = getElements(suggestions);
		System.out.println(autoSuggestionList.size());

		for (WebElement e : autoSuggestionList) {
			String autoText = e.getText();
			System.out.println(autoText);

			if (autoText.contains(suggName)) {
				Thread.sleep(2000);
				e.click();
				break;
			}
		}
	}

	// Important Interview **************************************
	public void clickOnElement(By locator, String linkText) {
		List<WebElement> elementsLists = getElements(locator);

		System.out.println(elementsLists.size());

		int i = 1;
		for (WebElement e : elementsLists) {
			String text = e.getText();
			System.out.println(i + "-> " + text);
			i++;

			if (text.contains(linkText)) {
				e.click();
				break;
			}
		}
	}

	// Important Interview********************************
	public boolean checkSingleElementPresent(By locator) {
		return driver.findElements(locator).size() == 1 ? true : false;
	}

	public boolean checkElementsPresent(By locator) {
		return driver.findElements(locator).size() >= 1 ? true : false;
	}

	public boolean checkTotalElementPresent(By locator, int totalElements) {
		return driver.findElements(locator).size() == totalElements ? true : false;
	}

	// *****Select Drop-Down Menu*********************

	public Select createSelect(By locator) {
		Select select = new Select(getElement(locator));
		return select;
	}

	public void doSelectDropDownElementByIndex(By locator, int indexNum) {
		createSelect(locator).selectByIndex(indexNum);
	}

	public void doSelectDropDownElementByVisibleText(By locator, String visibleText) {
		createSelect(locator).selectByVisibleText(visibleText);
	}

	public void doSelectDropDownElementByValue(By locator, String value) {
		createSelect(locator).selectByValue(value);
	}

	public int getTotalDropDownOptions(By locator) {
		return createSelect(locator).getOptions().size();
	}

	public List<String> doSelectAllDropDownOptions(By locator) {
		List<WebElement> countryOptionsList = createSelect(locator).getOptions();
		List<String> countryList = new ArrayList<String>();

		for (WebElement e : countryOptionsList) {
			String text = e.getText();
			countryList.add(text);
		}

		return countryList;
	}

	public void doSelectDropDownGenericMethod(By locator, String dropDownValue) {
		List<WebElement> countryOptionsList = createSelect(locator).getOptions();

		for (WebElement e : countryOptionsList) {
			String text = e.getText();
			System.out.println(text);

			if (text.contains(dropDownValue)) {
				e.click();
				break;
			}
		}

	}

	public void selectDropDownWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		for (WebElement e : optionsList) {
			String text = e.getText();

			if (text.equalsIgnoreCase(value)) {
				e.click();
				break;
			}
		}
	}

	// ***********Multiple dropDown select*************

	public boolean isDropDownMultiple(By locator) {
		return createSelect(locator).isMultiple() ? true : false;
	}

	/**
	 * 1. Single selection 2. Multiple selection 3. All selection: please pass "all"
	 * in the parameter to select all values
	 * 
	 * @param locator
	 * @param values
	 */

	public void selectDropDownMultipleValues(By locator, By optionLocator, String... values) {

		if (isDropDownMultiple(locator)) {

			if (values[0].equalsIgnoreCase("all")) {
				List<WebElement> optionList = getElements(optionLocator);

				for (WebElement e : optionList) {
					e.click();
				}

			} else {
				for (String value : values) {
					createSelect(locator).selectByVisibleText(value);
				}
			}

		}
	}

	// ***********Drop down Parent Child Action classes***************

	public void twoLevelMenuHandle(By parentLocator, By childLocator) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentLocator)).build().perform();
		Thread.sleep(2000);
		doClick(childLocator);
	}

	// **********Multiple Menu Drop Down Handle*********************

	public void fourLevelMenuHandle(By perentShopBy_MenuLocator, By childOne_MenuLocator, By childTwo_MenuLocator,
			By childThree_MenuLocator) throws InterruptedException {

		doClick(perentShopBy_MenuLocator);

		Actions act = new Actions(driver);
		Thread.sleep(2000);

		act.moveToElement(getElement(childOne_MenuLocator)).build().perform();
		Thread.sleep(2000);

		act.moveToElement(getElement(childTwo_MenuLocator)).build().perform();
		Thread.sleep(2000);

		doClick(childThree_MenuLocator);

	}

	// ************** ActionClass_SendKeys *****************

	public void doActionSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).perform();

	}

	public void doActionClick(By locator) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator)).perform();
	}

	// *************** ActionClass_SendKeys with Pause***************

	public void doActionsSendKeysWithPause(By locator, String value) {
		Actions act = new Actions(driver);
		char[] val = value.toCharArray();
		for (char v : val) {
			act.sendKeys(getElement(locator), String.valueOf(v)).pause(1000).build().perform();
		}
	}

	// *********** Explicit wait *************

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForPresenceOfElementLocated(By locator, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible
	 * 
	 * @param locator
	 * @param timeOut
	 * @param sleepingTimeOut
	 * @return
	 */
	public WebElement waitForPresenceOfElementLocated(By locator, int timeOut, int sleepingTimeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(sleepingTimeOut));
		WebElement element = driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForPresenceAllOfElementsLocatedBy(By locator, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("waiting for element : {0} and timeout : {1}")
	public WebElement waitForVisibilityOfElementLocated(By locator, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @param sleepingTimeOut
	 * @return
	 */
	public WebElement waitForVisibilityOfElementLocated(By locator, int timeOut, int sleepingTimeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(sleepingTimeOut));
		WebElement element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
		isHighlight(element);
		return element;
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForVisibilityOfElementsLocatedBy(By locator, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public void doClickByWait(By locator, int timeOut) {
		waitForVisibilityOfElementLocated(locator, timeOut).click();
	}

	public void doSendKeysByWait(By locator, int timeOut, String keyValue) {
		waitForVisibilityOfElementLocated(locator, timeOut).sendKeys(keyValue);
	}

	// *********** Wait for Title URL ***********

	public String waitForTitleContains(String titleFraction, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (driverWait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(titleFraction + " Title not present......!");
			e.printStackTrace();
		}
		return null;
	}

	public String waitForTitleIs(String title, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (driverWait.until(ExpectedConditions.titleIs(title))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println(title + " Title not present......!");
			e.printStackTrace();
		}
		return null;
	}

	// *********** Wait for URL ***********

	@Step("waiting for url : {0} and timeout : {1}")
	public String waitFor_URLContains(String URLFraction, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (driverWait.until(ExpectedConditions.urlContains(URLFraction))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println(URLFraction + " URL not present......!");
			e.printStackTrace();
		}
		return null;
	}

	public String waitFor_URLToBe(String URL, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (driverWait.until(ExpectedConditions.urlToBe(URL))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println(URL + " URL not present......!");
			e.printStackTrace();
		}
		return null;
	}

	// *********** Wait for JavaScript Alert ***********

	public Alert waitFor_JSAlertPresent(int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return driverWait.until(ExpectedConditions.alertIsPresent());
	}

	public void accept_JSAlert(int timeOut) {
		waitFor_JSAlertPresent(timeOut).accept();
	}

	public void dismiss_JSAlert(int timeOut) {
		waitFor_JSAlertPresent(timeOut).dismiss();
	}

	public String getJSAlertText(int timeOut) {
		return waitFor_JSAlertPresent(timeOut).getText();
	}

	public void doEnterValueOn_JSAlert(int timeOut, String jsValue) {
		waitFor_JSAlertPresent(timeOut).sendKeys(jsValue);
	}

	// ********** IFrame wait *************

	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameByID_or_Name(String frameIdORName, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdORName));
	}

	public void waitForFrameByWebElement(WebElement frameElement, int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		driverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// ************** Wait For Browser Window Handle **********

	public boolean checkNewWindowExist(int timeOut, int expectedNumberOfWindows) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

		try {
			if (driverWait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
				return true;
			}

		} catch (TimeoutException e) {
			System.out.println("Number of windows were not matched....!");
		}
		return false;
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		try {
			driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			driverWait.until(ExpectedConditions.elementToBeClickable(locator));

		} catch (TimeoutException e) {
			System.out.println("Element is not clickable or enabled....!");
		}

	}

	// ************* Wait for Fluent Wait **************

	public WebElement waitForElementWithFluentWait(By locator, int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("--timeout is done....still not found element---").ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class);

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		isHighlight(element);
		return element;
	}

	// ************** FluentWait for Frame wait *********

	public void waitForFrameWithFluentWait(String frameIdORName, int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("--timeout is done....still not found any frame---").ignoring(NoSuchFrameException.class);

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdORName));

	}

	// ************** FluentWait for Alert *********

	public Alert waitForAlertWithFluentWait(int timeout, int intervalTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(intervalTime))
				.withMessage("--timeout is done....still not alert appear---").ignoring(NoAlertPresentException.class);

		return wait.until(ExpectedConditions.alertIsPresent());

	}

	// **************** Custom Wait ************
	public WebElement retryElement(By locator, int timeOut) {

		WebElement element = null;
		int attempts = 1;

		while (attempts <= timeOut) {
			try {
				element = getElement(locator);
				System.out.println("Element found at " + locator + " in attempts " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found..... " + locator + " in attempts " + attempts);
				try {
					Thread.sleep(500); // default polling time = 500 ms
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null) {
			System.out.println("Element not found.... tried for " + timeOut + " times with the interval of " + 500
					+ " milliSeconds. ");
			throw new FrameworkException("No Such Element......!");
		}

		isHighlight(element);
		return element;

	}

	public WebElement retryElement(By locator, int timeOut, int timeInterval) {

		WebElement element = null;
		int attempts = 1;

		while (attempts <= timeOut) {
			try {
				element = getElement(locator);
				System.out.println("Element found at " + locator + " in attempts " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("Element is not found..... " + locator + " in attempts " + attempts);
				try {
					Thread.sleep(timeInterval); // custom polling time = 500 ms
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}

		if (element == null) {
			System.out.println("Element not found.... tried for " + timeOut + " times with the interval of " + 500
					+ " milliSeconds. ");
			throw new FrameworkException("No Such Element......!");
		}

		isHighlight(element);
		return element;

	}

	// ************** PageLoading Wait **************
	// States of Page loading --> loading, interactive, complete

	public boolean isPageLoading(int timeOut) {
		driverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = driverWait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete' "))
				.toString();
		return Boolean.parseBoolean(flag);
	}

}
