package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JS_Utils {

	private WebDriver driver;
	private JavascriptExecutor jse;

	public JS_Utils(WebDriver driver) {
		this.driver = driver;
		jse = (JavascriptExecutor) this.driver;
	}

	public String getTitleByJS() {
		return jse.executeScript("return document.title").toString();
	}

	public String getUrlByJS() {
		return jse.executeScript("return document.URL").toString();
	}

	public void generateAlertByJS(String msg) {
		jse.executeScript("return alert('" + msg + "')").toString();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.switchTo().alert().accept();
	}

	public void generateConfirmByJS(String msg) {
		jse.executeScript("return confirm('" + msg + "')").toString();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.switchTo().alert().accept();
	}

	public void generatePromptByJS(String msg, String promptValue) {
		jse.executeScript("return prompt('" + msg + "')").toString();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Alert alert = driver.switchTo().alert();
		alert.sendKeys(promptValue);
		alert.accept();

	}

	public void goBackwardByJS() {
		jse.executeScript("history.go(-1)");
	}

	public void refreshPageByJS() {
		jse.executeScript("history.go(0)");
	}

	public void goForwardByJS() {
		jse.executeScript("history.go(1)");
	}

	public String getPageInnerTextByJS() {
		return jse.executeScript("return document.documentElement.innerText;").toString();
	}

	public void scrollPageDownByJS() {
		jse.executeScript("return window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollPageDownInPixelsByJS(String height) {
		jse.executeScript("return window.scrollTo(0, " + height + ");");
	}

	public void scrollPageUpByJS() {
		jse.executeScript("return window.scrollTo(document.body.scrollHeight, 0);");
	}

	public void scrollPageUpInPixelsByJS(String height) {
		jse.executeScript("return window.scrollTo(" + height + ", 0);");
	}

	public void scrollMiddlePageUpByJS() {
		jse.executeScript("return window.scrollTo(0, document.body.scrollHeight/2);");
	}

	public void scrollToViewByJS(WebElement element) {
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Zoom ==> Chrome, Safari, Edge
	public void pageZoomInOutForChrome_Safari_Edge_ByJS(String zoomInOutPercentageValue) {
		jse.executeScript("document.body.style.zoom='" + zoomInOutPercentageValue + "%'");
	}

	// Mozilla zoom ==> document.body.style.MozTransform = 'scale(0.5)';
	public void pageZoomInOutFor_Firefox_ByJS(String zoomInOutPercentageValue) {
		jse.executeScript("document.body.style.MozTransform = 'scale(" + zoomInOutPercentageValue + ")'");
	}

	public void drawBorderByJS(WebElement element) {
		jse.executeScript("arguments[0].style.border='4px solid blue'", element);
	}

	public void drawBackgroundByJS(WebElement element) {
		jse.executeScript("arguments[0].style.background='yellow'", element);
	}
	
	public void drawBackgroundByJS(String color, WebElement element) {
		jse.executeScript("arguments[0].style.backgroundColor='" + color + "'", element);
	}

	public void flash(WebElement element) {

		String bgColor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			drawBackgroundByJS("rgb(0,200,0)", element);
			drawBackgroundByJS(bgColor, element);
		}

	}

}
