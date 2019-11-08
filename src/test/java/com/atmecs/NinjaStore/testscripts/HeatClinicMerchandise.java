package com.atmecs.NinjaStore.testscripts;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.atmecs.NinjaStore.constants.ConstantFilePaths;
import com.atmecs.NinjaStore.constants.TimeOuts;
import com.atmecs.NinjaStore.pageactions.PageActionSendKeys;
import com.atmecs.NinjaStore.pageactions.PageActions;
import com.atmecs.NinjaStore.pageactions.PageActionsClick;
import com.atmecs.NinjaStore.pageactions.PageActionsFindElement;
import com.atmecs.NinjaStore.pageactions.PageActionsGetText;
import com.atmecs.NinjaStore.pageactions.PageActionsScrollDown;
import com.atmecs.NinjaStore.reports.LogReports;
import com.atmecs.NinjaStore.testbase.TestBase;
import com.atmecs.NinjaStore.utils.ExcelFileReader;
import com.atmecs.NinjaStore.utils.ReadLocatorsFile;
import com.atmecs.NinjaStore.utils.TestDataProvider;
import com.atmecs.NinjaStore.validatetest.ValidateResult;

public class HeatClinicMerchandise extends TestBase
{
	LogReports log = new LogReports();
	String browserUrl;
	Properties properties;
	WebElement element;
	
/**
 * launching browser url	
 * @throws IOException
 */
	@BeforeClass
	public void launchingUrl() throws IOException {
	browserUrl = ReadLocatorsFile.getData(ConstantFilePaths.CONFIG_FILE,"url1");
	driver.get(browserUrl);
	driver.manage().window().maximize();
	driver.manage().timeouts().pageLoadTimeout(TimeOuts.requiredPageLoadTime, TimeUnit.MINUTES);
	driver.manage().timeouts().implicitlyWait(TimeOuts.requiredImplicitWaitTime, TimeUnit.SECONDS);
	}
	
/**
 * 
 * providing test data from excel to data provider
 */
	
	@Test(dataProvider = "validationdata", dataProviderClass = TestDataProvider.class)
	public void testcase1(String expectedHeaderText,String expectedMercahntHeaderText,String expectedClearanceHeaderText,String expectedMenHeaderText,String expectedCartProductName,String expectedProductSize,String expectedPersonalizedName,
			String expectedColourOfProduct,String expectedTotalPriceOfCart) throws IOException, Exception
	{
		properties=ReadLocatorsFile.loadProperty(ConstantFilePaths.LOCATORS_FILE);
		ExcelFileReader readExcelData=new ExcelFileReader(ConstantFilePaths.TESTDATA_FILE1);
		
		   //Creating the JavascriptExecutor interface object by Type casting		
        JavascriptExecutor execute = (JavascriptExecutor)driver;		
        		
        //Fetching the Domain Name of the site.	
        String DomainName = execute.executeScript("return document.domain;").toString();			
        log.info("Domain name of the site = "+DomainName);					
          		
        //Fetching the URL of the site.		
        String url = execute.executeScript("return document.URL;").toString();	//Tostring() change object to name.		
        log.info("URL of the site = "+url);					
          		
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickHotSaucesMenu"));
       String actualHeaderText=PageActionsGetText.fetchAttributeText(driver,properties.getProperty("loc.getMenusText"));
       ValidateResult.validateData(actualHeaderText, expectedHeaderText, "validated HotSauces menu page");
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickMerchandiseMenu"));
       String actualMercahntHeaderText=PageActionsGetText.fetchAttributeText(driver,properties.getProperty("loc.getMenusText"));
       ValidateResult.validateData(actualMercahntHeaderText, expectedMercahntHeaderText, "validated Merchant menu page");
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickclearanceMenu"));
       String actualClearanceHeaderText=PageActionsGetText.fetchAttributeText(driver,properties.getProperty("loc.getMenusText"));
       ValidateResult.validateData(actualClearanceHeaderText, expectedClearanceHeaderText, "validated Clearance menu page");
       
       element= PageActionsFindElement.findWebElement(driver,  properties.getProperty("loc.clickMerchandiseMenu"));
       PageActions.mouseOverElement(driver, element);
       
       PageActionsClick.clickElement(driver,  properties.getProperty("loc.selectMenslink"));
       String actualMenHeaderText=PageActionsGetText.fetchAttributeText(driver,properties.getProperty("loc.getMenusText"));
       ValidateResult.validateData(actualMenHeaderText, expectedMenHeaderText, "validated Mens menu page");
       
       PageActionsScrollDown.PageScrollDown(driver);
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickBuyNow"));
       PageActionsClick.clickElement(driver, properties.getProperty("loc.selectRedColour"));
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.selectMediumSize"));
       
       PageActionSendKeys.sendKeys(driver, properties.getProperty("loc.selectMediumSize"),readExcelData.getData(0, 1, 0));
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickWindowBuyNow"));
       
       PageActionsClick.clickElement(driver, properties.getProperty("loc.clickcart"));
       
       String actualCartProductName=PageActionsGetText.fetchAttributeText(driver,properties.getProperty("loc.getCartProductName"));
       ValidateResult.validateData(actualCartProductName, expectedCartProductName, "validated product name in the cart");
       
       
       String actualProductSize=PageActionsGetText.getTextFrom(driver,properties.getProperty("loc.getCartProductSize"));
       ValidateResult.validateData(actualProductSize, expectedProductSize, "validated product size in the cart");
       
       
       String actualPersonalizedName=PageActionsGetText.getTextFrom(driver,properties.getProperty("loc.getPersonalizedName"));
       ValidateResult.validateData(actualPersonalizedName, expectedPersonalizedName, "validated PersonalizedName of product");
       
       String actualColourOfProduct=PageActionsGetText.getTextFrom(driver,properties.getProperty("loc.getProductColour"));
       ValidateResult.validateData(actualColourOfProduct, expectedColourOfProduct, "validated colour of the product in the cart");
     
       PageActionSendKeys.sendKeys(driver, properties.getProperty("loc.sendProductsTocart"),readExcelData.getData(0, 1, 1));
           
       PageActionsClick.clickElement(driver, properties.getProperty("loc.addProductsToCart"));
       
       String actualTotalPrice=PageActionsGetText.getTextFrom(driver,properties.getProperty("loc.getTotalPriceOftheCart"));
       ValidateResult.validateData(actualTotalPrice, expectedTotalPriceOfCart, "validated Total price of the products in the cart");
       
       log.info("validated all the product details");
       
}
}
