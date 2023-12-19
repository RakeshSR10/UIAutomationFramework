package com.qa.opencart.pages;

//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SelectedProductInfoPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productThumbnailImgs = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

//	private Map<String, String> productMetaDataMap = new HashMap<String, String>(); // un-ordered Map

	private Map<String, String> productMetaDataMap = new LinkedHashMap<String, String>(); // ordered-map

//	private Map<String, String> productMetaDataMap = new TreeMap<String, String>(); // Alphabetical order map

	public SelectedProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public String getProductHeaderName() {
		String productHeaderName = elementUtil.dogetElementGetText(productHeader);
		System.out.println("Product Header Name => " + productHeaderName);
		return productHeaderName;
	}

	// TDD
	public int getProductThumbnailImgsCount() {
		int imgsCount = elementUtil
				.waitForVisibilityOfElementsLocatedBy(productThumbnailImgs, AppConstants.LONG_DEFAULT_WAIT).size();
		System.out.println("Procust - " + getProductHeaderName() + " images count " + imgsCount);
		return imgsCount;
	}

	private void getProductMedaData() {
		List<WebElement> metaDataList = elementUtil.waitForVisibilityOfElementsLocatedBy(productMetaData,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		for (WebElement ele : metaDataList) {
			String metaDataText = ele.getText();

			String metaDataKey = metaDataText.split(":")[0].trim();
			String metaDataValue = metaDataText.split(":")[1].trim();
			productMetaDataMap.put(metaDataKey, metaDataValue);
		}
	}

	private void getProductMedaPriceData() {
		List<WebElement> metaDataPriceList = elementUtil.waitForVisibilityOfElementsLocatedBy(productPriceData,
				AppConstants.MEDIUM_DEFAULT_WAIT);
		String productPrice = metaDataPriceList.get(0).getText();
		String productExTaxPrice = metaDataPriceList.get(1).getText().split(":")[1].trim();

		productMetaDataMap.put("price", productPrice);
		productMetaDataMap.put("ExTax Price", productExTaxPrice);
	}

	public Map<String, String> getCompleteProductDetails() {
		productMetaDataMap.put("ProductName", getProductHeaderName());
		getProductMedaData();
		getProductMedaPriceData();

		System.out.println(productMetaDataMap);
		return productMetaDataMap;
	}
}
