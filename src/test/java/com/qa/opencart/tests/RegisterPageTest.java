package com.qa.opencart.tests;

//import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void registerSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	public String getRandomEmailId() {
		return "testautomation" + System.currentTimeMillis() + "@opencart.com";
//		return "testautomation" + UUID.randomUUID() + "@opencart.com";

	}

	public String getRandomPassword() {
		return "automation@" + System.currentTimeMillis();
	}

//	@DataProvider
//	public Object[][] getUserRegisterData() {
//		return new Object[][] { 
//			{ "Kingsman", "sr3", "7531594560", "yes" },
//			{ "Kingsman", "K2", "7531594500", "yes" }, };
//	}

	@DataProvider
	public Object[][] getUserRegisterTestExcelData() {
		Object registerXLSheetData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_DATA_XLSHEET_NAME);
		return registerXLSheetData;
	}

	@Test(dataProvider = "getUserRegisterTestExcelData")
	public void userRegisterationTest(String firstName, String lastName, String telephone, String subscribe) {
		boolean isRegisterDone = registerPage.userRegisteration(firstName, lastName, getRandomEmailId(), telephone,
				getRandomPassword(), subscribe);
		Assert.assertTrue(isRegisterDone);

	}

}
