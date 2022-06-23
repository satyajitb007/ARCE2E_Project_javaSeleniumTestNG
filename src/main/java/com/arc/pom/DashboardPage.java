package com.arc.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage {
	public WebDriver driver;
	By userLogo = By.xpath("//a[@id='user-info']");
	By showModuledownArrowKey = By.xpath("//span[contains(text(),'Show other modules')]");
	String element = "//span[contains(text(),'Show other modules')]";
	By folderFilesLocator = By.xpath("//h4[contains(text(),' Folders & files ')]");
	public By plusIcon = By.cssSelector(".icon-plus.material-icons");
	public By createFilemodalText = By.cssSelector("h2#mat-dialog-title-0");
	By folderNameArea = By.cssSelector(".mat-input-element.mat-form-field-autofill-control");
	By createNewFolderbutton = By.xpath("//span[contains(text(),'Create new folder')]") ;
	By createFinalButton = By.xpath("//span[contains(text(),'Create') and @class='mat-button-wrapper']");
	By FileUpload_optionsContainer = By.cssSelector(".btn-text");
	public By fileContainer = By.cssSelector(".fixed-action-btn");
	public By documentName = By.cssSelector(".document-name");
	
	public DashboardPage(WebDriver driver2) {
		this.driver = driver2;
	}
	
	public WebElement dashbaordPage_afterloggedin() {
		return driver.findElement(userLogo);
	}
	
	public WebElement showModulesClick() {
		return driver.findElement(showModuledownArrowKey);
	}
	
	public WebElement fileandFoldersClick() {
		return driver.findElement(folderFilesLocator);
		
	}
	
	public WebElement createFolderClick() {
		return driver.findElement(createNewFolderbutton);
		
	}
	
	public void javaScriptClick() {
		WebElement ele = driver.findElement(By.xpath(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
	}
	
	public WebElement enterFolderName() {
		return driver.findElement(folderNameArea);
		
	}
	
	public WebElement createTheFolder() {
		return driver.findElement(createFinalButton);
		
	}
	
	public List<WebElement> uploadOptions(){
		return driver.findElements(FileUpload_optionsContainer);
		
	}
	public List<WebElement> listOfItems(By ele){
		return driver.findElements(ele);
		
	}
}
