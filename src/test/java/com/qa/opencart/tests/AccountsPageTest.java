package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetUp() {
		accountsPage = loginPage.doLogin(properties.getProperty("LOGIN_PAGE_USERNAME"),
				properties.getProperty("LOGIN_PAGE_PASSWORD"));
	}

	@Test
	public void getAccountPageTitleTest() {
		Assert.assertEquals(accountsPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void getAccountPageURLTest() {
		Assert.assertTrue(accountsPage.getAccountPageURL().contains(AppConstants.ACCOUNTS_PAGE_URL_FRACTION));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}

	@Test
	public void isSearchBoxExistTest() {
		Assert.assertTrue(accountsPage.isSearchBoxExist());
	}

	@Test
	public void getAccountLinksHeadersCountTest() {
		List<String> accountPageHeadersList = accountsPage.getAccountLinksHeadersExist();
		System.out.println(accountPageHeadersList);
		Assert.assertEquals(accountPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}

	public void getAccountLinksHeadersExistTest() {
		List<String> accountPageHeadersList = accountsPage.getAccountLinksHeadersExist();
		System.out.println(accountPageHeadersList);
		Assert.assertEquals(accountPageHeadersList, AppConstants.ACCOUNTS_PAGE_HEADERS_LIST);
	}

	@Test
	public void doSearchTest() {
		searchResultsPage = accountsPage.doSearch("MacBook");
		selectedProductInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		String actualProductHeaderValue = selectedProductInfoPage.getProductHeaderName();
		Assert.assertEquals(actualProductHeaderValue, "MacBook Pro");
	}
}
