package com.atmecs.NinjaStore.helperpages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.atmecs.NinjaStore.constants.ConstantFilePaths;
import com.atmecs.NinjaStore.pageactions.PageActionsClick;
import com.atmecs.NinjaStore.pageactions.PageActionsGetText;
import com.atmecs.NinjaStore.reports.LogReports;
import com.atmecs.NinjaStore.utils.ReadLocatorsFile;
import com.atmecs.NinjaStore.validatetest.ValidateResult;



public class ScenarioHelper {
	public WebDriver driver;
	Properties properties;
	LogReports log = new LogReports();
	String locatorProperty;
	String actualTextTitle;

	public ScenarioHelper(WebDriver driver) {
		this.driver = driver;
	}

	public void validatingProductDetails(String Price,String tax,String description,String total,String removaltotal) throws IOException {
		properties=ReadLocatorsFile.loadProperty(ConstantFilePaths.LOCATORS_FILE);
		locatorProperty=properties.getProperty("loc.SearchDropDown");
		
		String productPrice=PageActionsGetText.fetchAttributeText(driver, properties.getProperty("loc.checkProductPrice"));
		ValidateResult.validateData(productPrice, Price, "validated product price");
		
		String productTax=PageActionsGetText.fetchAttributeText(driver, properties.getProperty("loc.checkProductExTax"));
		ValidateResult.validateData(productTax, tax, "validated product tax");
		
		
		String productDescription=PageActionsGetText.fetchAttributeText(driver, properties.getProperty("loc.checkProductDescription"));
		ValidateResult.validateData(productDescription, description, "validated product description");
		
		String cartTotal=PageActionsGetText.fetchAttributeText(driver, properties.getProperty("loc.checkCartTotal"));
		ValidateResult.validateData(cartTotal, total, "validated product cartTotal");
		
		log.info("YES ALL THE 5 ADDED PRODUCTS THERE IN THE CART"+" "+total);
		log.info("GRAND TOTAL OF THE CART VALIDATED"+" "+total);
		
		PageActionsClick.clickElement(driver, properties.getProperty("loc.clickOnCartTotal"));
		log.info("clicking  cart total");
		
		PageActionsClick.clickElement(driver, properties.getProperty("loc.removeFromCart"));
		log.info("after removing on product from   cart total");
		
		String cartTotalAfterDeleting=PageActionsGetText.fetchAttributeText(driver, properties.getProperty("loc.checkCartTotal"));
		ValidateResult.validateData(cartTotalAfterDeleting, removaltotal, "validated product cartTotal");
		
		
		
	}
}
