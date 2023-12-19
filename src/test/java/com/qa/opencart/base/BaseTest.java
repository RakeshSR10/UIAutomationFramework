package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;
import com.qa.opencart.pages.SelectedProductInfoPage;

public class BaseTest {

	protected WebDriver driver;
	protected Properties properties;
	DriverFactory driverFactory;
	protected LoginPage loginPage;
	protected RegisterPage registerPage;
	protected AccountsPage accountsPage;
	protected SearchResultsPage searchResultsPage;
	protected SelectedProductInfoPage selectedProductInfoPage;

	// When an assertion fails, don't throw an exception but record the failure.
	// Calling assertAll() will cause an exception to be thrown if at least one
	// assertion failed.
	protected SoftAssert softAssert;

	@Parameters({ "browser" })
	@BeforeTest
	public void setup(String browserName) {
		driverFactory = new DriverFactory();
		properties = driverFactory.initConfigProperty();

		if (browserName != null) {
			properties.setProperty("browser", browserName);
		}

		driver = driverFactory.initDriver(properties);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
