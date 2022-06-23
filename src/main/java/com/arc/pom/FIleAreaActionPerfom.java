package com.arc.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FIleAreaActionPerfom {
	public WebDriver driver;

	public FIleAreaActionPerfom(WebDriver driver) {
		this.driver = driver;
	}
	
	By chaneIcon = By.id("tagIconSearchInput");
	public By createFilemodalText = By.xpath("//h4[contains(text(),'Create pin')]");
	public By freeTextBox = By.xpath("//textarea[@placeholder='Description']");
	By createPinButton = By.xpath("//button[contains(text(),'Create')]");
	By pindropdownArea = By.xpath("//span[contains(text(),'Pin') and @id='spnTagCount']");
	
	public WebElement changeofIcon() {
		return driver.findElement(chaneIcon);
	}
	public List<WebElement> listOfItems(By ele){
		return driver.findElements(ele);
		
	}
	public WebElement enterFreeText() {
		return driver.findElement(freeTextBox);
	}
	public WebElement createPin() {
		return driver.findElement(createPinButton);
	}
	
	public WebElement pinAreaClick() {
		return driver.findElement(pindropdownArea);
	}
}
