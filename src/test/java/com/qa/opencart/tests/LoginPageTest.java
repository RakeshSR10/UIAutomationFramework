package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design OpenCart Login page")
@Story("US 101: Login page feature")
@Feature("F50: Feature Login page")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {

	@Description("login page title test....")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("login page URL test....")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void getLoginPageURLTest() {
		String actualURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));
	}

	@Description("login page forgotPassword link test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Description("verifying App login page logo test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4)
	public void logoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}

	@Description("User login account with username and password test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 5)
	public void doLoginTest() {
		accountsPage = loginPage.doLogin(properties.getProperty("LOGIN_PAGE_USERNAME"), properties.getProperty("LOGIN_PAGE_PASSWORD"));
		accountsPage.isLogoutLinkExist();
//		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}

}
