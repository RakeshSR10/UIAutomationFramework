package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties properties;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();

	public static String highlight = null;
	
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("BROWSER");
//		String browserName = System.getProperty("browser");

		System.out.println("Browser name is => " + browserName);
		
		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(properties);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			threadLocalDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			threadLocalDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			break;
		case "safari":
//			driver = new SafariDriver();
			threadLocalDriver.set(new SafariDriver());
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			threadLocalDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please enter valid browser: " + browserName + " ......!");
			throw new FrameworkException("No matching browser found....!");
		}

		getDriver().manage().deleteAllCookies();// external, cache data
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		getDriver().get(properties.getProperty("URL"));

		return getDriver();
	}

	public static WebDriver getDriver() {
		return threadLocalDriver.get();
	}

	public Properties initConfigProperty() {

		// mvn clean install -Denv="qa"
		FileInputStream fileInputStream = null;
		properties = new Properties();

		String envName = System.getProperty("env");
		System.out.println("envName => " + envName);

		try {
			if (envName == null) {
				System.out.println("Your env is null........ By default running test cases on QA env....");
				fileInputStream = new FileInputStream("./src/test/resources/config/config.qa.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					fileInputStream = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;

				case "dev":
					fileInputStream = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;

				case "uat":
					fileInputStream = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;

				case "stage":
					fileInputStream = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;

				case "prod":
					fileInputStream = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Please select right Environment.... and 'Not' -> " + envName);
					throw new FrameworkException(envName);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot(String methodName) {
		
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir") + "/screenshots/" + methodName + "_" + System.currentTimeMillis()+".png";
				
		File destination = new File(path);
		
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
