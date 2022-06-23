package com.arc.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionOperations {

	WebDriver driver;
	Actions actions;
	Robot rb;

	public ActionOperations(WebDriver driver) throws AWTException {
		this.driver = driver;
		actions = new Actions(driver);
		rb = new Robot();
	}

	public void doubleClick(WebElement ele) {
		actions.doubleClick(ele).perform();
	}

	public void moveToElementPerform(WebElement ele) {
		actions.moveToElement(ele).click().perform();
	}

	// copy file to clip board
	public void selectFile(String filePath) {
		rb.delay(3000);
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void dragDrop(WebElement ele, WebElement ele2) {
		actions.click(ele).moveToElement(ele2)
		.click()
		.build()
		.perform();
	}
}
