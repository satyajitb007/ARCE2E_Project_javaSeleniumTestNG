package com.arc.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Landing_Login_Page {

	public WebDriver driver;

	By userName = By.xpath("//input[@id='UserID']");
	By passWord = By.xpath("//input[@id='Password']");
	By signInButton = By.xpath("//button[contains(text(),'Sign in')]");

	public Landing_Login_Page(WebDriver driver2) {
		this.driver = driver2;
	}

	public WebElement getSignin() {
		return driver.findElement(userName);
	}

	public WebElement getPassword() {
		return driver.findElement(passWord);
	}

	public WebElement signInButtonClick() {
		return driver.findElement(signInButton);
	}

}
