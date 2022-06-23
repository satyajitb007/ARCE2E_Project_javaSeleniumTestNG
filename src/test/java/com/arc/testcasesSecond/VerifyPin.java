package com.arc.testcasesSecond;

import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.PropertyConfigurator;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.arc.pom.DashboardPage;
import com.arc.pom.FIleAreaActionPerfom;
import com.arc.pom.Landing_Login_Page;
import com.arc.testcases.FileUpload_Validation;
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

public class VerifyPin extends initBrowser {
	@Parameters({ "jsonFile", "propertyFile", "log4jProp" })
	@Test(priority = 0, description = "Launching the browser")
	@Owner("Satyajit")
	@Epic("EP001")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test case Description: verify launching of browser")
	@Story("Story name: To check CHROME browser has opened")
	@Feature("Validate WebBrowser has opened without any error ")
	public void tearUp(String jsonFile, String propertyFile, String log4jProp) throws IOException, ParseException {
		PropertyConfigurator.configure(log4jProp);
		ReadJSON.readJSON(jsonFile);
		driver = FileUpload_Validation.startBrowser("Browser", propertyFile);
	}

	@Test(priority = 1)
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
				logReporter("URL Hit");
			}
		} catch (Exception e) {
			logReporter("Ended with error  " + e.getMessage());
		} finally {

			System.out.println("Quite");
		}
	}

	@Step("{0}")
	public static String logReporter(String message) {
		return message;
	}

	@Test(priority = 2)
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
	@Test(priority = 3)
	public void goToFolder()
			throws FileAlreadyExistsException, IOException, ParseException, AWTException, InterruptedException {
		FIleAreaActionPerfom fap = new FIleAreaActionPerfom(driver);
		ActionOperations ao = new ActionOperations(driver);
		driver.switchTo().frame(driver.findElement(By.cssSelector("iFrame[id=\"myFrameSPA\"]")));
		DashboardPage dp = new DashboardPage(driver);
		dp.showModulesClick().click();
		dp.fileandFoldersClick().click();
		List<WebElement> listOfFIlders;
		listOfFIlders = driver.findElements(By.cssSelector(".folder-name"));
		for (WebElement e : listOfFIlders) {
			if (e.getText().equalsIgnoreCase(ReadJSON.getData("FolderName"))) {
				ao.doubleClick(e);
				break;
			}
		}
		Thread.sleep(5000);
		WaitNew.explicitWaitPresenceofElement(driver, By.cssSelector(".document-name"));
		List<WebElement> elements = dp.listOfItems(By.cssSelector(".document-name"));
		for (WebElement element : elements) {
			System.out.println(element.getText());
			if (element.getText().equalsIgnoreCase(ReadJSON.getData("File_Name"))) {
				System.out.println("File found and the file name is: " + element.getText());
				logReporter("File found and the file name is: " + element.getText());
				ao.doubleClick(element);
				break;
			} else {
				System.out.println("File not present");
			}
		}

		System.out.println("debug");
		System.out.println(driver.getTitle());
		String parentWindow = driver.getWindowHandle();
//		Set<String> windows = driver.getWindowHandles();
//		Iterator<String> it = windows.iterator();
//		while (it.hasNext()) {
//			String window = it.next();
//			driver.switchTo().window(window);
//			System.out.println(driver.getTitle());
//		}
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		System.out.println(driver.getTitle());
		WebElement ele = driver.findElement(By.cssSelector("i.icon.icon-lg.icon-doc-tag"));
		WebElement ele2 = driver.findElement(By.cssSelector(".leaflet-tile.leaflet-tile-loaded"));
		ao.dragDrop(ele, ele2);
		WaitNew.explicitWaitVisibilityeofElement(driver, fap.createFilemodalText);
		String modalText = driver.findElement(fap.createFilemodalText).getText();
		if (modalText != "") {
			fap.changeofIcon().click();
			WaitNew.explicitWaitPresenceofElement(driver, By.cssSelector("ul.ps.ps--active-y>li"));
			List<WebElement> set = fap.listOfItems(By.cssSelector("ul.ps.ps--active-y>li"));
//			Collections.shuffle(set);
			for (WebElement tr : set.subList(0, Math.min(1, set.size()))) {
				String itemName = tr.getText();
				System.out.println(itemName);
				logReporter("Pin Name: "+itemName);
				tr.click();
				fap.enterFreeText().sendKeys(ReadJSON.getData("Enter_value"));
				fap.createPin().click();
				Thread.sleep(1000);
				fap.pinAreaClick().click();
				Thread.sleep(500);
				List<WebElement> listOfitems = fap.listOfItems(
						By.cssSelector("#ulTagList>li>div.list-tag>div:nth-child(2).tag-name-box>h4.tag-name"));
				for (WebElement e : listOfitems) {
					System.out.println(e.getText());
					System.out.println(itemName);
					Assert.assertEquals(e.getText(), itemName);
					Assert.assertTrue(e.getText().equalsIgnoreCase(itemName),"Pin Verified");
					if (e.getText().equalsIgnoreCase(itemName)) {
						System.out.println("Pin Verified");
						logReporter("Pin Verified from the list: "+e.getText());
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
