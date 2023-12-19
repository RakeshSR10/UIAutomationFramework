package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By logoutLink = By.linkText("Logout");
	private By searchBox = By.xpath("//input[@name= 'search']");
	private By searchIcon = By.cssSelector("div#search button");
	private By accountHeadersLinks = By.cssSelector("div#content > h2");

	// page constructor:
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public String getAccountPageTitle() {
		String title = elementUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page title -> " + title);
		return title;
	}

	public String getAccountPageURL() {
		String actualURL = elementUtil.waitFor_URLContains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION,
				AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Account page URL -> " + actualURL);
		return actualURL;
	}

	// page actions
	public boolean isLogoutLinkExist() {
		return elementUtil.waitForVisibilityOfElementLocated(logoutLink, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();
	}

	public void logout() {
		if (isLogoutLinkExist()) {
			elementUtil.doClick(logoutLink);
		}
	}

	public boolean isSearchBoxExist() {
		return elementUtil.waitForVisibilityOfElementLocated(searchBox, AppConstants.MEDIUM_DEFAULT_WAIT).isDisplayed();
	}

	public List<String> getAccountLinksHeadersExist() {
		List<WebElement> headersList = elementUtil.waitForVisibilityOfElementsLocatedBy(accountHeadersLinks, 4);
		List<String> headersArrayList = new ArrayList<String>();

		for (WebElement text : headersList) {
			String headerText = text.getText();
			headersArrayList.add(headerText);
		}
		return headersArrayList;
	}

	public SearchResultsPage doSearch(String searchKey) {
		elementUtil.waitForVisibilityOfElementLocated(searchBox, AppConstants.MEDIUM_DEFAULT_WAIT).clear();
		elementUtil.waitForVisibilityOfElementLocated(searchBox, AppConstants.MEDIUM_DEFAULT_WAIT)
				.sendKeys(searchKey);
		elementUtil.doClick(searchIcon);
		return new SearchResultsPage(driver); // TDD (Test Driven Development) approach
	}
}
