package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;

public class LoginTest extends BaseTest {
	
	@DataProvider(name="LoginData")
	public Object[][] getLoginData() throws IOException{
		
		String filePath = System.getProperty("user.dir")+"/testdata/TestData.xlsx";
		ExcelUtils.loadExcel(filePath, "Sheet1");
		int rowCount = ExcelUtils.getRowCount();
		Object[][] data = new Object[rowCount-1][2];
		
		for(int i=1; i<rowCount; i++) {
			
			data[i-1][0] = ExcelUtils.getCellData(i, 0);	// Username
			data[i-1][1] = ExcelUtils.getCellData(i, 1);	// Password
		}
		ExcelUtils.closeExcel();
		return data;
	}
//Get data from Dataprovider - creating new object as LoginData2 apart from excel LoginData
	@DataProvider(name="LoginData2")
	public Object[][] getData(){
		
		return new Object[][] {
			{"user1","pass1"},
			{"user2","pass2"},
			{"admin@yourstore.com","admin"}
		};
	}
	
	/* @Test(dataProvider = "LoginData2") */
	
	//Getting data from testng.xml
	@Test
	@Parameters({"username", "password"})
	public void testVolidLogin(String username, String password) {
		
		Log.info("Starting login test...");
		test = ExtentReportManager.createTest("Login Test - "+username);
		
		test.info("Navigating to URL");
		LoginPage loginpage = new LoginPage(driver);
		
		Log.info("Adding credentials");
		test.info("Adding Credentails");

		/*
		 * loginpage.enterUsername("admin@yourstore.com");
		 * loginpage.enterPassword("admin");
		 */
		
		loginpage.enterUsername(username);
		loginpage.enterPassword(password);
		
		test.info("Clicking on Login button");
		loginpage.clickLogin();
		
		System.out.println("Titel of the page is: "+driver.getTitle());
		
		Log.info("Verifying the Page Title...");
		test.info("Verifying page title");
		
		Assert.assertEquals(driver.getTitle(), "Dashboard / nopCommerce administration");
		//Dashboard / nopCommerce administration
		Log.info("Login test completed...");
		test.pass("Login Successful");
	}

}
