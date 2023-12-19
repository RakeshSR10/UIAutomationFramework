package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public SelectedProductInfoPage selectProduct(String productName) {
		// run-time by locator
		elementUtil.waitForVisibilityOfElementLocated(By.linkText(productName), AppConstants.MEDIUM_DEFAULT_WAIT)
				.click();
		return new SelectedProductInfoPage(driver); // TDD (Test Driven Development) approach
	}

}
