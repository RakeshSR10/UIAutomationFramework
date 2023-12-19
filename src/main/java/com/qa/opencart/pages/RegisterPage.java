package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirm_Password = By.id("input-confirm");

	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@type='radio' and @value='1']");
	private By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@type='radio' and @value='0']");

	private By privacyPoicyAgreeCheckBox = By
			.xpath("//div[@class='pull-right']//input[@type='checkbox' and @value='1']");
	private By continueBtn = By.xpath("//div[@class='pull-right']//input[@type='submit' and @value='Continue']");

	private By successMsg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public boolean userRegisteration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {
		elementUtil.waitForVisibilityOfElementLocated(this.firstName, AppConstants.MEDIUM_DEFAULT_WAIT)
				.sendKeys(firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirm_Password, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			elementUtil.doClick(subscribeYes);
		} else {
			elementUtil.doClick(subscribeNo);
		}

		elementUtil.doClick(privacyPoicyAgreeCheckBox);
		elementUtil.doClick(continueBtn);

		String successMsgText = elementUtil
				.waitForVisibilityOfElementLocated(successMsg, AppConstants.MEDIUM_DEFAULT_WAIT).getText();

		System.out.println(successMsgText);

		if (successMsgText.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}

	}
}
