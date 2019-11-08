package com.atmecs.NinjaStore.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.atmecs.NinjaStore.reports.LogReports;

/**
 * interacting with excel file and reads the entire cell data from it.
 * 
 *
 */
public class ReadExcelFile {
	 FileInputStream fileReader;
	 Workbook book;
	 Sheet sheet;
	 LogReports log=new LogReports();
	

	public  Object[][] ReadWriteExcel(String sheetName, String path)     // sheet name to fetch data from particular sheet
	{
		//FileInputStream file = null;
	try {
	fileReader = new FileInputStream(path);                     // set test data excel file as testData_path in  constants class
	} catch (FileNotFoundException e) {
	log.info("File Not Found");
	}
	try {
	book = WorkbookFactory.create(fileReader);
	} catch (InvalidFormatException e) {
	e.printStackTrace();
	} catch (IOException e) {
	e.printStackTrace();
	}
	sheet = book.getSheet(sheetName);                                  // Read Every rows,columns and pass value to data provider
	Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	for (int i = 0; i < sheet.getLastRowNum(); i++) {
	for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) { 
	data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
	}
	}
	return data;
	}
}
/*
 * @DataProvider public Object[][] possibleValues() throws Exception { 
 * 
 * Object data[][] = ExcelUtils.ReadExcel("positive search item",ConstantFilePaths.TESTDATA_FILE); 
 * return data; 
 * }
 * 
 * @Test(dataProvider = "possibleValues", priority = 1)
 */