package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class SearchProductResultPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {
		accountsPage = loginPage.doLogin(properties.getProperty("LOGIN_PAGE_USERNAME"),
				properties.getProperty("LOGIN_PAGE_PASSWORD"));
	}

//	@DataProvider
//	public Object[][] getSearchData() {
//		
//		// Data Testing Approach
//		return new Object[][] { 
//			{ "MacBook", "MacBook Pro", 4 },
//			{ "MacBook", "MacBook Air", 4 },
//			{ "iMac", "iMac", 3 },
//			{ "Samsung", "Samsung SyncMaster 941BW", 1 }
//		};
//	}

	@DataProvider
	public Object[][] getSearchExcelSheetData() {
		return ExcelUtil.getTestData(AppConstants.PRODUCT_DATA_XLSHEET_NAME);

	}

	@Test(dataProvider = "getSearchExcelSheetData")
	public void productImagesTest(String searchKey, String productName, String prodImgCount) {
		searchResultsPage = accountsPage.doSearch(searchKey);
		selectedProductInfoPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(String.valueOf(selectedProductInfoPage.getProductThumbnailImgsCount()), prodImgCount);
	}

	@Test
	public void getCompleteProductDetailsTest() {
		searchResultsPage = accountsPage.doSearch("MacBook");
		selectedProductInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDetailsMap = selectedProductInfoPage.getCompleteProductDetails();

		softAssert.assertEquals(productDetailsMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDetailsMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productDetailsMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDetailsMap.get("Availability"), "In Stock");

		softAssert.assertEquals(productDetailsMap.get("price"), "$2,000.00");
		softAssert.assertEquals(productDetailsMap.get("ExTax Price"), "$2,000.00");
		softAssert.assertAll();
	}
}
