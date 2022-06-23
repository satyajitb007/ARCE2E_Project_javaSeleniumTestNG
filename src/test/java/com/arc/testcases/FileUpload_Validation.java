package com.arc.testcases;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.arc.pom.DashboardPage;
import com.arc.pom.Landing_Login_Page;
import com.arc.utils.ActionOperations;
import com.arc.utils.ReadJSON;
import com.arc.utils.WaitNew;
import com.arc.utils.initBrowser;

import commons.VerifyLinks;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import junit.framework.Assert;

public class FileUpload_Validation extends initBrowser {
	private static Logger logger = Logger.getLogger("Test Info:");

	@Parameters({ "jsonFile", "propertyFile", "log4jProp" })
	@BeforeSuite(description = "Launching the browser")
	@Owner("Satyajit")
	@Epic("EP001")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test case Description: verify launching of browser")
	@Story("Story name: To check CHROME browser has opened")
	@Feature("Validate WebBrowser has opened without any error ")
	public void tearUp(String jsonFile, String propertyFile, String log4jProp) throws IOException, ParseException {
		PropertyConfigurator.configure(log4jProp);
		logger.debug("Started");
		ReadJSON.readJSON(jsonFile);
		driver = FileUpload_Validation.startBrowser("Browser", propertyFile);
	}

	@BeforeTest
	@Parameters({ "propertyFile" })
	@Owner("Satyajit")
	@Epic("EP001")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test case Description: verify hitting the URL and it opened successfully")
	@Link("https://app.arcfacilities.com/")
	@Story("Story name: To check Website has opened without any error")
	@Feature("Validate Website Launched successfully")
	public void launchWebsite(String propertyFile) throws IOException {
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		p.load(fis);
		fis.close();
		try {

			if (VerifyLinks.verifyLinkActive(p.getProperty("base_url")) == 200) {
				driver.get(p.getProperty("base_url"));
				WaitNew.untilJqueryIsDone(driver, 200);
				logger.info("URL Hit");
				logReporter("URL Hit");
			}
		} catch (Exception e) {
			logger.debug("Ended with error  " + e.getMessage());
			logger.info("Ended with error  " + e.getMessage());
		} finally {

			System.out.println("Quite");
		}
	}

	@Step("{0}")
	public static String logReporter(String message) {
		return message;
	}

	@Test(priority = 0)
	@Parameters({ "propertyFile" })
	@Owner("Satyajit")
	@Epic("EP001")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test case Description: Login using given credential")
	@Link("app.arcfacilities.com")
	@Story("Story name: To check login functionality")
	@Feature("Validate arc login")
	public static void doLogin(String propertyFile) throws IOException {
		Landing_Login_Page llp = new Landing_Login_Page(driver);
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		p.load(fis);
		fis.close();
		String beforeSignInURL = driver.getCurrentUrl();
		Base64.Decoder decoder = Base64.getDecoder();
		String Decoded_username = new String(decoder.decode(p.getProperty("username_val")));
		String Decoded_Password = new String(decoder.decode(p.getProperty("password_val")));
		llp.getSignin().sendKeys(Decoded_username);
		llp.getPassword().sendKeys(Decoded_Password);
		llp.signInButtonClick().click();
		WaitNew.untilPageLoadComplete(driver, 500);
		WaitNew.explicitWaitPresenceofElement(driver, By.cssSelector(".download-app"));
		String actualWebsiteTitle = driver.getTitle();
		String afterSignInURL = driver.getCurrentUrl();
		Assert.assertEquals(p.getProperty("expectedWebsiteTitle"), actualWebsiteTitle);
		Assert.assertTrue(beforeSignInURL != afterSignInURL);

	}

	@Parameters({ "jsonFile", "propertyFile" })
	@Test(priority = 1)
	@Owner("Satyajit")
	@Epic("EP001")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test case Description: Create Folder and upload file under same folder")
	@Link("app.arcfacilities.com")
	@Story("Story name: To check File Upload Functionality")
	@Feature("Validate the uploaded file")
	public static void createFolder(String jsonFile, String propertyFile)
			throws IOException, ParseException, InterruptedException, AWTException {
		ReadJSON.readJSON(jsonFile);
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(propertyFile);
		p.load(fis);
		fis.close();
		ActionOperations ao = new ActionOperations(driver);
		System.out.println("debug");
		driver.switchTo().frame(driver.findElement(By.cssSelector("iFrame[id=\"myFrameSPA\"]")));
		DashboardPage dp = new DashboardPage(driver);
		dp.showModulesClick().click();
		dp.fileandFoldersClick().click();
		List<WebElement> listOfFIlders;
		boolean s = false;
		listOfFIlders = driver.findElements(By.cssSelector(".folder-name"));
		Iterator<WebElement> itr = listOfFIlders.iterator();
		while (itr.hasNext()) {
			String text = itr.next().getText();
			System.out.println(text);
			if (text.equals(ReadJSON.getData("FolderName"))) {
				s = true;
				System.out.println("Folder name " + ReadJSON.getData("FolderName") + " is already exist");
				throw new FileAlreadyExistsException("file exist"); 
			}
		}
		System.out.println("boolean value " + s);
		if (s == false) {
			WaitNew.explicitWaitElementToBeClickable(driver, dp.plusIcon);
			dp.createFolderClick().click();
			WaitNew.explicitWaitVisibilityeofElement(driver, dp.createFilemodalText);
			String modalText = driver.findElement(dp.createFilemodalText).getText();
			if (modalText != "") {
				System.out.println("Create folder");
				logReporter("Create folder");
				dp.enterFolderName().sendKeys(ReadJSON.getData("FolderName"));
				String foldername = dp.enterFolderName().getAttribute("value");
				dp.createTheFolder().click();
				System.out.println("Folder " + foldername + " has been created");
				logReporter("Folder " + foldername + " has been created");
			}
		}
		Thread.sleep(1000);
		listOfFIlders = driver.findElements(By.cssSelector(".folder-name"));
		for (WebElement e : listOfFIlders) {
			if (e.getText().equalsIgnoreCase(ReadJSON.getData("FolderName"))) {
				ao.doubleClick(e);
			}
		}
		Thread.sleep(1000);
		WaitNew.explicitWaitElementToBeClickable(driver, dp.plusIcon);
		WaitNew.explicitWaitVisibilityeofElement(driver, dp.fileContainer);
		for (WebElement ele : dp.uploadOptions()) {
			if (ele.getText().equalsIgnoreCase(ReadJSON.getData("upload_Option"))) {
				ele.click();
				break;
			}
		}
		String parentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		while (it.hasNext()) {
			String window = it.next();
			driver.switchTo().window(window);
			System.out.println(driver.getTitle());
			if (driver.getTitle().contains("Upload Campus")) {
				System.out.println("switching done");
				WebElement button = driver.findElement(By.id("btnSelectFiles"));
				ao.moveToElementPerform(button);
				ao.selectFile(p.getProperty("desired_file_path"));
				System.out.println("StartsHere");
				WebElement submitButton = driver.findElement(By.id("multipartUploadBtn"));
				ao.moveToElementPerform(submitButton);
				Thread.sleep(5000);
				driver.switchTo().window(parentWindow);
				System.out.println(driver.getCurrentUrl());
				driver.switchTo().frame(driver.findElement(By.cssSelector("iFrame[id=\"myFrameSPA\"]")));
				WaitNew.explicitWaitPresenceofElement(driver, By.cssSelector(".document-name"));
				List<WebElement> elements = dp.listOfItems(By.cssSelector(".document-name"));
				for (WebElement e : elements) {
					if (e.getText().equalsIgnoreCase(ReadJSON.getData("File_Name"))) {
						System.out.println("File has been uploaded and the file name is: "+e.getText());
						logReporter("File has been uploaded and the file name is: "+e.getText());
					} else {
						System.out.println("not uploaded");
					}
				}
			}
		}

	}

	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
