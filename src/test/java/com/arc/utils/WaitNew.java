package com.arc.utils;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class WaitNew {
	static WebDriverWait wait;
	public static void untilJqueryIsDone(WebDriver driver, Integer timeoutInSeconds) {
		until(driver, (d) -> {
			Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
			if (!isJqueryCallDone)
				System.out.println("JQuery call is in Progress");
			return isJqueryCallDone;
		}, timeoutInSeconds);
	}


	public static void untilPageLoadComplete(WebDriver driver, Integer timeoutInSeconds) {
		until(driver, (d) -> {
			Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState")
					.equals("complete");
			if (!isPageLoaded)
				System.out.println("Document is loading");
			return isPageLoaded;
		}, timeoutInSeconds);
	}


	@SuppressWarnings("deprecation")
	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Integer timeoutInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
		try {
			webDriverWait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void waitForObject(WebDriver driver)

	{
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	}

	public static void waitForPageLoading(WebDriver driver)

	{
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

	}

	public static void waitForAlert(WebDriver driver) throws InterruptedException

	{
		int i = 0;
		while (i++ < 5)
			try {
				Alert alert = driver.switchTo().alert();
				System.out.println(alert.getText());
				Reporter.log(alert.getText());
				alert.accept();
				WaitNew.untilJqueryIsDone(driver, 20);
				System.out.println("Accepted the alert successfully.");
				break;
			} catch (NoAlertPresentException e)

			{
				System.err.println("Error came while waiting for the alert popup. " + e.getMessage());
				Thread.sleep(1000);
				continue;
			}
	}
	
	public static void explicitWaitPresenceofElement(WebDriver drive, By ele) {
		wait = new WebDriverWait(drive, 500);
		wait.until(ExpectedConditions.presenceOfElementLocated(ele));
	}
	public static void explicitWaitVisibilityeofElement(WebDriver drive, By ele) {
		wait = new WebDriverWait(drive, 500);
		wait.until(ExpectedConditions.presenceOfElementLocated(ele));
	}
	public static void explicitWaitElementToBeClickable(WebDriver drive, By ele) {
		wait = new WebDriverWait(drive, 100);
		wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
	}
}
