package com.arc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.qameta.allure.Step;

public class initBrowser {
	public static WebDriver driver;
	public static WebDriver startBrowser(String browserName, String propertyFile) throws IOException, ParseException {
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		p.load(fis);
		fis.close();

		if (ReadJSON.getData(browserName).equalsIgnoreCase("mozilla")) {
			System.setProperty("webdriver.gecko.driver", p.getProperty("mozilla_driver_path"));
			driver = new FirefoxDriver();
		} else if (ReadJSON.getData(browserName).equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", p.getProperty("chrome_driver_path"));
			ChromeDriverService driverService = ChromeDriverService.createDefaultService();
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(false);
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-extension");
			options.addArguments("user-data-dir=" + p.getProperty("chrome_profile"));
			driver = new ChromeDriver(driverService, options);

		} else if (ReadJSON.getData(browserName).equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", p.getProperty("ie_driver_path"));
			driver = new InternetExplorerDriver();
		}
		
		WaitNew.waitForPageLoading(driver);
		driver.manage().window().maximize();
		logReporter("Browxer is up and running");
		System.out.println("Browxer is up and running");
		return driver;

	}

	@Step("{0}")
	public static String logReporter(String message) {
		return message;
	}
}
