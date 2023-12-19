package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	// By locator
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value ='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By appLogo = By.xpath("//img[@alt= 'naveenopencart']");

	private By registerPageLink = By.linkText("Register");

	// page constructor:
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	// "Encapsulation"
	// page actions/methods:
	@Step("getting login page title")
	public String getLoginPageTitle() {
		String title = elementUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page title -> " + title);
		return title;
	}

	@Step("getting login page URL")
	public String getLoginPageURL() {
		String actualURL = elementUtil.waitFor_URLContains(AppConstants.LOGIN_PAGE_URL_FRACTION,
				AppConstants.SHORT_DEFAULT_WAIT);
		System.out.println("Login page URL -> " + actualURL);
		return actualURL;
	}

	@Step("checking forgot-password link exist")
	public boolean isForgotPwdLinkExist() {
		return elementUtil.waitForVisibilityOfElementLocated(forgotPwdLink, AppConstants.SHORT_DEFAULT_WAIT)
				.isDisplayed();
	}

	@Step("checking app logo exist")
	public boolean isLogoExist() {
		return elementUtil.waitForVisibilityOfElementLocated(appLogo, AppConstants.SHORT_DEFAULT_WAIT).isDisplayed();

	}

	@Step("username is: {0} and password is: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("Credentials are: <" + username + "> : <" + pwd + ">");
		elementUtil.waitForVisibilityOfElementLocated(userName, AppConstants.MEDIUM_DEFAULT_WAIT).sendKeys(username);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginBtn);

		return new AccountsPage(driver);
	}

	@Step("navigate to register page")
	public RegisterPage navigateToRegisterPage() {
		elementUtil.waitForVisibilityOfElementLocated(registerPageLink, AppConstants.MEDIUM_DEFAULT_WAIT).click();
		return new RegisterPage(driver);
	}
}
