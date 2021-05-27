package GenericFunctions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
//import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Reporter;
//import org.testng.SkipException;

//import com.perfectomobile.selenium.util.EclipseConnector;








//import Configuration.FrameworkConfig;
import Reporting.Report;
import TestScriptRunner.Runner;
//import atafsecurity.AES;
//import ch.qos.logback.classic.net.SyslogAppender;
import atafsecurity.AES;


@SuppressWarnings({"deprecation","rawtypes"})
//@SuppressWarnings("rawtypes")
public class WebMobileFunctions extends Report{

	/**
	 * Script Name   : <b>Selenium_CommonFunction</b>
	 * Generated     : <b>Mar 24, 2017 3:02:02 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2017/03/24
	 * @author n079597
	 */

	//CommonFunctions objCommonFunc= new CommonFunctions();
	//WebFunctions objWebFunc = new WebFunctions(); 
	Report objReport=new Report();


	public static WebDriver driver=null;
	public static String perfectoDecryptedPassword="";

	//public static ExtentReports extent=Report.extent;
	// public static ExtentTest logger=Report.logger;



	//******************



	/**
	 * @Name createElementLocatorObjectFromExcel   
	 * @Applicable Desktop,Mobile 
	 * @param strObjName Element name  specified in excel sheet
	 * @param strWorkSheet  Worksheet name containing the element(strobjName)
	 * @description Creates By class(Element locator) object by using the properties stored in Page Object Repository excel file
	 */

	// Creating TestObjects using Object properties stored in Excel file
	public By createElementLocatorObjectFromExcel(String strElementName,String strWorkSheet) throws Exception
	{
		By objElement=null;
		try
		{

			boolean elelocatorStatusFlag=false;

			
				//Create excel object for page object repository excel file
			//String excelObjRepositry =Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+ Runner.properties.getProperty("pomFile");
			String excelObjRepositry =Runner.resdir+"\\" + Runner.properties.getProperty("pomFile");
			
			//System.out.println(excelObjRepositry);
			//String 	excelObjRepositry ="C:\\Data\\Applications\\MyAetnaTest1\\MyAetna\\src\\Resources\\PageObject.xls";
			



			//String excelObjRepositry =MyAetnaCrossBrowser_Config1.strPOMFile;
			FileInputStream fis_PageObjectExcel=null;
			try{
			//Create object for the worksheet specified in 'worksheetObjRepositry'
			fis_PageObjectExcel = new FileInputStream(excelObjRepositry);
			}
			catch(Exception e){
				objReport.setValidationMessageInReport("FAIL", "Please check pomFile property value is available in AppConfig.properties file");					
			}
			Workbook workBookPageObject = WorkbookFactory.create(fis_PageObjectExcel);
			Sheet sheetPageObject = workBookPageObject.getSheet(strWorkSheet);

			//Navigate to required element(specified in strobjName) in the Worksheet(Specified in worksheetObjRepositry)
			int totalRowCount=sheetPageObject.getLastRowNum();
			String objName;
			String objLocator;
			String objLocatorValue;

			int j=1;
			for (j=1;j<=totalRowCount;j++)
			{
				objName=sheetPageObject.getRow(j).getCell(0).getStringCellValue();
				if(objName.equalsIgnoreCase(strElementName))
				{
					//Retrieve the properties(element locator(like class,id etc ) and locator value) of the  element for which element locator object(By class )need to be created
					objLocator=sheetPageObject.getRow(j).getCell(1).getStringCellValue().toLowerCase();
					objLocatorValue=sheetPageObject.getRow(j).getCell(2).getStringCellValue();

					switch(objLocator) 
					{
					case "id" :
						objElement= By.id(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					case "name" :
						objElement=By.name(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					case "classname" :
						objElement=By.className(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "tagname" :
						objElement=By.tagName(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "linktext" :
						objElement=By.linkText(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "partiallinktext" :
						objElement=By.partialLinkText(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "cssselector" :
						objElement=By.cssSelector(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "xpath" :
						objElement=By.xpath(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					default : 			
						objReport.setValidationMessageInReport("FAIL", "Incorrect Locator type is available for the Element '"+strElementName+"in '"+strWorkSheet+ "' worksheet of Page object excel repository");					
					}

				}

				if(elelocatorStatusFlag==true)
				{
					break;
				}
			}

			//Verifying Element info is available in the Worksheet of Page Object Excel file
			if(j==totalRowCount+1)
			{
				//objReport.setValidationMessageInReport("Verify locator object for the '"+strObjName +"' Webelement is created","", "Incorrect webelemnet name '"+strObjName+"' is passed as arguement in the method", "FAIL");		
				objReport.setValidationMessageInReport("FAIL", "Element name '"+strElementName+"' passed as arguement is not available in '"+strWorkSheet+ "' worksheet of Page object excel repository");					
			}
			fis_PageObjectExcel.close();
			workBookPageObject=null;
			sheetPageObject=null;
		}
		catch(Exception e){
			//objReport.setValidationMessageInReport("Verify locator object for the '"+strObjName +"' Webelement is created" ,"","Failed to create locator object for '"+strObjName +"' WebElement available in the '"+strWorkSheet+ "' worksheet of Page object excel repository", "FAIL");
			objReport.setValidationMessageInReport("FAIL", "Failed to create locator object for '"+strElementName +"' WebElement available in the '"+strWorkSheet+ "' worksheet of Page object excel repository due to Exception '"+e+"'");					
		}

		return objElement;

	}
	/**
	 * This method generates the list of parameters specified in the TestScript.xls for each keyword. 
	 * The index of parameters in the list returned
	 * corresponds to the position of parameter in TestScript.xls
	 * @param strParams The keyword parameters string generated by xml 
	 * @returns List<String>
	 * @applicableTo  Desktop, Mobile                                        
	 */

	public List<String> getListOfKeywordParameters(String strParams) throws Exception
	{
		List<String> strParamsList = new ArrayList<String>();

		try{
			String [] strParamsArr=strParams.split("#@");
			int noOfParameters=strParamsArr.length;

			for(int i=0; i<noOfParameters;i++){
				strParamsList.add(strParamsArr[i].trim());
			}
		}
		
		catch (Exception e){

			objReport.setValidationMessageInReport("FAIL", "Exception occured in getListOfKeywordParameters");					
		}
		return strParamsList;

	}

	/**
	 * This method launches the application url in the browser and it is Specific to Desktop browser
	 * @name launchUrl
	 * @param strBrowser The browser to be opened(Possible Values - Chrome,FF,IE)
	 * @param strBrowserDriver Contains Browser driver file name along with location of it in the System
	 * @param strUrl  The Application URL to be launched
	 * @applicableTo  Desktop                                         
	 */

	
	public void launchUrl(String strBrowser,String strUrl) throws Exception
	{
		try{
			
			
			String strBrowserDriver= "";
			// Launch Chrome browser
			
			String strResrcFldrLocPath=Runner.resdir+"\\Framework";
			/*if (Runner.localRunFlag==true)
			{
				strResrcFldrLocPath=Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources";
			}
			else
			{
				strResrcFldrLocPath=Runner.strResourceFldLoc+"\\Framework\\";
			}*/
			
			if (strBrowser.equalsIgnoreCase("Chrome"))
			{	
				//strBrowserDriver=Runner.strWorkSpcPath+ "DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\chromedriver.exe";
				
				//strBrowserDriver=Runner.strResourceFldLoc + "\\chromedriver.exe";
				strBrowserDriver=strResrcFldrLocPath + "\\chromedriver.exe";
				
				System.out.println(strBrowserDriver);
				System.setProperty("webdriver.chrome.driver", strBrowserDriver);
				
				System.setProperty("webdriver.chrome.verboseLogging", "false");
				
				//System.out.println("aa1");
				ChromeOptions options = new ChromeOptions();
				//System.out.println("aa2");
				// Maximize the browser
				options.addArguments("--start-maximized");
				//System.out.println("aa3");
				options.addArguments("enable-automation");
				driver = new ChromeDriver(options);	
				System.out.println("Chrome driver launched");
			}
			if (strBrowser.equalsIgnoreCase("Edge"))
			{	
				 //System.setProperty("webdriver.edge.driver","C:\\Data\\Applications\\AccountManagerV3Test\\ATAFramework\\FrameworkUtils\\Resources\\MicrosoftWebDriver.exe");
         		
				strBrowserDriver = strResrcFldrLocPath + "\\MicrosoftWebDriver.exe";
				
				System.out.println(strBrowserDriver);
				System.setProperty("webdriver.edge.driver", strBrowserDriver);	
				 driver = new EdgeDriver();
         		 driver.manage().window().maximize();
			}

			// Launch Firefox browser
			if(strBrowser.equalsIgnoreCase("FF"))
			{		
				//strBrowserDriver=Runner.strWorkSpcPath+ "DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\geckodriver.exe";
				strBrowserDriver=strResrcFldrLocPath + "\\geckodriver.exe";
				
				System.setProperty("webdriver.gecko.driver",strBrowserDriver);
				System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Firefox\\Firefox.exe");				
				FirefoxProfile ffprofile= new FirefoxProfile();
				DesiredCapabilities ffCapabilities = DesiredCapabilities.firefox();
				ffCapabilities.setCapability("firefoxProfile", ffprofile);
				ffCapabilities.setCapability(FirefoxDriver.PROFILE, ffprofile);
				driver = new FirefoxDriver(ffCapabilities);

				// Maximize the browser
				driver.manage().window().maximize();
			}

			// Launch Internet Explorer browser
			if(strBrowser.equalsIgnoreCase("IE"))
			{
				//strBrowserDriver=Runner.strWorkSpcPath+ "DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\IEDriverServer.exe";
				
				strBrowserDriver=strResrcFldrLocPath + "\\IEDriverServer.exe";
				//System.out.println(strBrowserDriver);
				
				System.setProperty("webdriver.ie.driver", strBrowserDriver);
			/*	DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, strUrl);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);                                                                
				ieCapabilities.setCapability("ensureCleanSession", true);
				*/
				//System.out.println("launch IE");
				
				//System.out.println("IE LAUNCHED");
				
				
				InternetExplorerOptions ieOptions= new InternetExplorerOptions();
				ieOptions.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, strUrl);
				ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);                                                                
				ieOptions.setCapability("ensureCleanSession", true);
				ieOptions.setCapability("silent", true);
				
				driver = new InternetExplorerDriver(ieOptions);

				// Maximize the browser
				driver.manage().window().maximize();
			}

			// Delete all cookies
			if (!(strBrowser.equalsIgnoreCase("Edge"))){
			driver.manage().deleteAllCookies();
			}

			//Open the application in browser
			driver.get(strUrl);

			//Reporter.log("Application is launched successfully");
			objReport.setValidationMessageInReport("PASS", "Application is launched successfully");					
		}
		catch(Exception e){

			objReport.setValidationMessageInReport("FAIL", "Failed to launch Application due to exception : "+e);

		}		
	}


	/**
	 * This method searches the element in the application by using the By class locators and returns the 1st instance of the element on successful search
	 * @Name getElement
	 * @param objLocator The By class locator of the element to be searched  
	 * @param objName The Element Name to be searched
	 * @applicableTo  Desktop,Mobile
	 * @throws Exception 
	 * @description It returns the 1st instance of the WebElement available in the Web page
	 * @return WebElement                                        
	 */

	public WebElement getElement(By objLocator, String objName) throws Exception
	{
		WebElement element=null;
		try 
		{
			element=driver.findElement(objLocator);
		}
		//catch (NoSuchElementException e)
		catch (Exception e)
		{	
			//String s= e.getMessage();
			//String s1= objLocator.toString();
			//objReport.setValidationMessageInReport("FAIL", "Method getElement : Failed to locate '"+objName+"' element due to exception : "+e);
			objReport.setValidationMessageInReport("FAIL", "Method getElement : Failed to locate element '"+objName+"' using  selenium locator: "+objLocator.toString());
		}

		return element;
	}
	/**
	 * This method searches the element in the application by using the By class locators and returns the list object containing all instance of the element available in application page  on successful search
	 * @Name getListElements
	 * @param objLocator The By class object of the element to be searched  
	 * @param objName The Element Name to be searched
	 * @applicableTo  Desktop,Mobile
	 * @throws Exception 
	 * @description This method searches the element in the application by using the By class locators and returns the list object containing all instance of the element available in application page  on successful search
	 * @return List<WebElement>                                        
	 */
	public List<WebElement> getListElements(By objLocator, String objName) throws Exception
	{
		List<WebElement> lstElements=new ArrayList<>();
		try 
		{
			lstElements=driver.findElements(objLocator);
		}				
		catch (Exception e)
		{			
			objReport.setValidationMessageInReport("FAIL", "Method getListElements : Failed to locate '"+objName+"' element due to exception : "+e);
		}
		//System.out.println(lstElements.size());
		return lstElements;
	}

	/**
	 * Clicks the element(Button,Link,Image)
	 * @Name click
	 * @param objLocator The By class object of the element to be clicked  
	 * @param objName The Element Name to be clicked
	 * @return boolean     
	 * @applicableTo  Desktop,Mobile   
	 * @description Clicks the element(Button,Link,Image)                              
	 */
	public void click(By objLocator, String objName) throws Exception
	{
		try {
			WebElement btnElement=getElement(objLocator,objName);
			btnElement.click();
			objReport.setValidationMessageInReport("PASS", "'"+objName + "' button is clicked");
		}
		catch(Exception e) 
		{
			objReport.setValidationMessageInReport("FAIL", "Failed to click '"+objName + "' button due to exception : "+e.toString());
		}
	}

	/**
	 * Enters the text in the text box
	 * @Name enterText
	 * @param objLocator The By class object of the text box element where value to be entered 
	 * @param strText The text to be entered in the text box
	 * @param objName The text box Element Name where value to be entered
	 * @return boolean
	 * @applicableTo  Desktop,Mobile 
	 * @description This method clears the text box and then enters the value (strText) provided by user                                       
	 */
	public boolean enterText(By objLocator,String strText,String objName) throws Exception
	{
		try {
			boolean result = true;
			WebElement txtBoxElement=getElement(objLocator,objName);
			txtBoxElement.clear();
			
			txtBoxElement.sendKeys(strText);
			objReport.setValidationMessageInReport("PASS", strText + " text is entered in the '"+ objName + "' textbox ");			
			return result;
		}
		catch(Exception e) 
		{
			objReport.setValidationMessageInReport("FAIL", "Failed to enter text in '"+ objName + "' textbox");
			return false;
		}
	}

	/**
	 * This method clears the text in the specified text box object
	 * @Name clearTextbox     
	 * @param obj The By class object of the text box element
	 * @param objName Name of the Text box WebElement
	 * @Applicable Desktop,Mobile 
	 * @description This method clears the text in the specified object
	 */

	public void clearTextbox(By obj, String objName) throws Exception
	{
		try{
			WebElement txtBoxElement=getElement(obj,objName);
			txtBoxElement.clear();
			objReport.setValidationMessageInReport("PASS", "Value is removed from '"+ objName + "' textbox");						
		}
		catch(Exception e) 
		{
			objReport.setValidationMessageInReport("FAIL", "Failed to remove value from '"+ objName + "' textbox due to Exception : "+e);	
		}

	}

	/**
	 * Hover over the WebElement specified in objName
	 * @Name hoverElement         
	 * @param obj -  The By class object of the web element
	 * @param objName - Name of the WebElement
	 * @Applicable Desktop
	 * @description This method hovers the mouse pointer on WebElement specified                                                  
	 */
	public void hoverElement(By obj,String objName) throws Exception
	{
		try {
			Actions action= new Actions(driver);
			WebElement hoverElement=getElement(obj,objName);
			action.moveToElement(hoverElement).build().perform();		
			//objReport.setValidationMessageInReport("Hover over the "+objName,"", "", "PASS");
			objReport.setValidationMessageInReport("PASS", "Hover over the '"+objName+ "' element");	
		}
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Failed to Hover over the '"+objName+ "' element due to Exception : "+e );
		}
	}

	/**
	 * It returns the selected text value of drop-down element specified by objName
	 * @Name getDropdown         
	 * @param obj -  The By class object of the dropdown
	 * @param objName - Name of the drop-down WebElement
	 * @description It returns the selected text value of drop-down element specified by objName
	 * @Applicable Desktop,Mobile 
	 * @return String                                                           
	 */
	public String getDrpdwnSeltdValue(By obj,String objName) throws Exception
	{
		String strText=null;
		try{                       
			Select selectListBox = new Select(getElement(obj,objName));
			strText = selectListBox.getFirstSelectedOption().getText();
		}
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method getDrpdwnSeltdValue : Failed to retrieve the selected value of the '"+objName+ "' drop-down due to Exception : "+e);	
		}
		return strText;
	}

	/**
	 * This method selects the value(strValueToBeSelected) in the drop-down specified by objName
	 * @Name selectDropdown         
	 * @param obj -  The By class object of the dropdown
	 * @param strValueToBeSelected - Value to be selected in the drop-down
	 * @param objName - Name of the drop-down WebElement
	 * @description This method selects the value(strValueToBeSelected) in the drop-down specified by objName
	 * @Applicable Desktop,Mobile                                                              
	 */

	public void selectDropdown(By obj, String strValueToBeSelected, String objName) throws Exception
	{
		try
		{
			Select selectListBox = new Select(getElement(obj,objName));			
			selectListBox.selectByVisibleText(strValueToBeSelected);
			objReport.setValidationMessageInReport("PASS", "'"+strValueToBeSelected+"' value is selected in '"+objName+"' drop-down");	
		}		
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method selectDropdown : Failed to select the '"+strValueToBeSelected+"' value of the '"+objName+ "' drop-down due to Exception : "+e);	
		}

	}

	/**
	 * This method gets all the value of a drop-down specified by 'objName'
	 * @Name getAllDopdownValues         
	 * @param obj -  The By class object of the dropdown
	 * @param objName - Name of the drop-down WebElement
	 * @return List<WebElement>
	 * @Applicable Desktop,Mobile
	 * @description This method gets all the value of a drop-down specified by 'objName'                                                           
	 */

	public List<WebElement> getAllDopdownValues(By obj , String objName) throws Exception
	{
		List<WebElement> allOptions=null;
		try
		{
			WebElement obj_Select=driver.findElement(obj);			
			Select selectListBox = new Select(obj_Select);			
			allOptions=selectListBox.getOptions();			
		}
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method getAllDopdownValues : Failed to reteive all  drop-down values of the of the '"+objName+ "' drop-down due to Exception : "+e);	
		}
		return allOptions;
	}

	/**
	 * Script execution will wait till condition specified in 'strConditionMode' is completed 
	 * @Name explicitWait         
	 * @Applicable Desktop,Mobile
	 * @param obj - By element locator of web element
	 * @param maxTimeOut - Maximum waiting time (Seconds)
	 * @param strConditionMode - Waiting condition( Value : VISIBILITY , INVISIBILITY, PRESENCE , FRAME, CLICKABLE)
	 * @description Script execution will wait(Maximum time specified in maxTimeOut) till condition specified in 'strConditionMode' is completed                                                            
	 */

	public void explicitWait(By obj,int maxTimeOut , String strConditionMode) throws Exception
	{
		try{

			String mode = strConditionMode.toUpperCase();                    
			switch (mode) {
			case "VISIBILITY":
				(new WebDriverWait(driver, maxTimeOut))
				.until(ExpectedConditions.visibilityOfElementLocated(obj));       
				break;

			case "INVISIBILITY":
				(new WebDriverWait(driver, maxTimeOut))
				.until(ExpectedConditions.invisibilityOfElementLocated(obj));
				break;

			case "PRESENCE":
				(new WebDriverWait(driver, maxTimeOut))
				.until(ExpectedConditions.presenceOfElementLocated(obj));
				break;   

			case "FRAME":
				(new WebDriverWait(driver, maxTimeOut))
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(obj));
				break; 

			case "CLICKABLE":
				(new WebDriverWait(driver, maxTimeOut))
				.until(ExpectedConditions.elementToBeClickable(obj));        
				break;
			default:
				objReport.setValidationMessageInReport("FAIL", "Method explicitWait: Incorrect checking condition mode is '"+strConditionMode+"' provided. Please provide the correct condition mode");	

			}				
		}
		catch(Exception e){
			objReport.setValidationMessageInReport("FAIL", "Method explicitWait: Exception '"+e+"' encountered");	
		}

	}

	/**
	 * This method returns the value of the html attribute(strAttribute)
	 * @Name getAttributeValue         
	 * @Applicable Desktop,Mobile
	 * @param obj -  By element locator of web element
	 * @param strAttribute - Name of the html Attribute
	 * @param objName - Name of the drop-down WebElement
	 * @return String
	 * @description This method returns the value of the html attribute(strAttribute) for the WebElement specified by obj/objName                                                         
	 */
	public String  getAttributeValue(By obj, String strAttribute,String objName) throws Exception
	{
		try
		{
			return getElement(obj,objName).getAttribute(strAttribute).trim();
		}
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method getAttributeValue : Failed to reteive '"+strAttribute+"' attribute value of the '"+objName+ "' element due to Exception : "+e);	
			return null;
		}
	}


	/**
	 * This method checks both String values specified by strValue1,strValue2 are same or not.
	 * @Name compareText         
	 * @param strValue1 -  String variable
	 * @param StrValue2 -  String Variable
	 * @return boolean
	 * @throws Exception 
	 * @description This method checks both String values specified by strValue1,strValue2 are same or not.
	 *               Returns boolean true if both String values are same 
	 *               Returns boolean false if both String values are different                                                                    
	 */
	public Boolean compareText(String strValue1,String StrValue2) throws Exception
	{		
		if((strValue1.equalsIgnoreCase(StrValue2)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	/**
	 * This method returns the text value of the WebElement specified by objName/obj
	 * @Name getElementText         
	 * @param obj -   By element locator of web element
	 * @param objName - Name of the WebElement
	 * @return String 
	 * @throws Exception
	 * @applicableTo Desktop, Mobile
	 * @description This method returns the text value of the WebElement specified by objName/obj                                                    
	 */		
	public String getElementText(By obj , String objName) throws Exception
	{
		try
		{			
			WebElement element=getElement(obj,objName);
			return element.getText().trim();		
		}
		catch(Exception e) {
			//objReport.setValidationMessageInReport("Exception in keyword get_element_text using object "+objName,"", "", "FAIL");
			objReport.setValidationMessageInReport("FAIL", "Method 'getElementText' : Failed to get the text of '"+objName + "' due to Exception : "+e);
			return null;
		}
	}



	/**
	 * This method checks whether the expected label is displayed for the element
	 * @Name verifyLabel
	 * @param obj -By element locator of web element
	 * @param strExpElementText - Expected value of text for the webElement
	 * @param objName - Name of the WebElement
	 * @applicableTo  Desktop,Mobile
	 * @description This method checks the correct label displays for the element
	 * 				-Returns true if the expected text value matches with retrieved text value.
	 * 				-Returns true if the expected text value does not match with retrieved text value.
	 * @throws Exception
	 * @return  Boolean                                               
	 */
	public boolean verifyLabel(By obj ,String strExpElementText, String objName) throws Exception
	{
		try
		{			
			WebElement element=getElement(obj,objName);

			//Getting the Actual text of the element
			String strActElementText=element.getText().trim();

			if(strActElementText.equalsIgnoreCase(strExpElementText))
			{
				objReport.setValidationMessageInReport("PASS", "Correct '"+strExpElementText+"' label  is displayed for '"+objName+"' element");			
				return true;
			}	
			else
			{
				objReport.setValidationMessageInReport("FAIL", "Incorrect '"+strExpElementText+"' label  is displayed for '"+objName+"' element");			
				return false;
			}	

		}
		catch(Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method 'verifyLabel' : Failed to verify the labbel of '"+objName + "' element due to Exception '"+e+"'");			
			return false;
		}
	}


	/**
	 * This method checks element visibility in the Web page or Mobile App  
	 * @Name verifyElementVisibility
	 * @param obj - WebElement locator class object 
	 * @param objName - Name of the WebElement
	 * @applicableTo  Desktop,Mobile
	 * @throws Exception
	 * @description This method checks element visibility in the Web page or Mobile App
	 * @return  Boolean true or false                                               
	 */
	public boolean verifyElementVisibility(By obj,String objName) throws Exception
	{
		try
		{			
			Boolean elementDisplayStatus=getElement(obj,objName).isDisplayed();

			if(elementDisplayStatus==true)
			{
				objReport.setValidationMessageInReport("PASS", "'"+objName+"' is displayed");			
				return true;
			}		
			else
			{
				objReport.setValidationMessageInReport("FAIL", "'"+objName+"' is not displayed");
				return false;
			}

		}
		catch(Exception e){
			objReport.setValidationMessageInReport("FAIL", "Method 'verifyElementVisibility' : Failed to verify visibility of '"+objName + "' element due to Exception '"+e+"'");			
			return false;
		}
	}


	/**
	 * This method checks element visibility in the Web page or Mobile App  
	 * @Name getElementCount
	 * @param obj - WebElement locator class object 
	 * @param objName - Name of the WebElement
	 * @applicableTo  Desktop,Mobile
	 * @throws Exception
	 * @description This method checks the size of the Web Element list and returns its count
	 * @return  Integer                                               
	 */
	public int getElementCount(By obj,String objName) throws Exception
	{


		try{
			List<WebElement> elementList = getListElements(obj,objName);
			int elementListSize= elementList.size();
			return elementListSize;
		}

		catch(NoSuchElementException e){
			objReport.setValidationMessageInReport("FAIL", "Method 'getElementCount' : Failed to get count of '"+objName + "' element due to Exception '"+e+"'");
			return 0;
		}
	}

	/**
	 * This method checks element display status in the Web page or Mobile App  
	 * @Name getElementDisplayStatus
	 * @param obj - WebElement locator class object 
	 * @param objName - Name of the WebElement
	 * @applicableTo  Desktop,Mobile
	 * @throws Exception
	 * @description This method checks the size of the Web Element list and returns its count
	 * @return  Boolean                                               
	 */
	public boolean getElementDisplayStatus(By obj,String objName) throws Exception
	{


		try{
			int elementListSize=getElementCount(obj, objName);
			if(elementListSize!=0){
				return true;
			}
			else{
				return false;
			}
		}

		catch(NoSuchElementException e){
			objReport.setValidationMessageInReport("FAIL", "Method 'getElementDisplayStatus' : Failed to get count of '"+objName + "' element due to Exception '"+e+"'");
			return false;
		}



	}



	/**
	 * This method checks if a web element is selected or not selected. 
	 * @Name verifySelectedOrDeselected         
	 * @param obj -  WebElement locator class object
	 * @param strSelection - Element state to be verified(Value : selected/deselected)
	 * @param objName - Name of the WebElement
	 * @applicableTo Desktop,Mobile
	 * @throws Exception
	 * @description This method checks if a web element is selected or not selected. 
	 *              - Returns boolean true if strSelection and web element status match
	 *              - Returns boolean false if strSelection and web element status do not match                                                
	 */


	public void verifySelectedOrDeselected(By obj,String strSelection, String objName) throws Exception
	{
		try
		{
			WebElement element = null;	

			//Element Selection Validation
			if(strSelection.equalsIgnoreCase("selected"))
			{
				element=driver.findElement(obj);
				if(element.isSelected())
				{
					objReport.setValidationMessageInReport("PASS", "'"+objName+"' is selected");	
				}
				else
				{
					objReport.setValidationMessageInReport("FAIL", "'"+objName+"' is not selected");	
				}
			}

			//Element not selected Validation
			else if(strSelection.equalsIgnoreCase("deselected"))
			{
				element=driver.findElement(obj);

				if(element.isSelected())
				{
					objReport.setValidationMessageInReport("FAIL", "'"+objName+"' is selected");	
				}
				else
				{
					objReport.setValidationMessageInReport("PASS", "'"+objName+"' is not selected");	
				}
			}

			else
			{
				objReport.setValidationMessageInReport("FAIL", "Incorrect '"+strSelection+"' parameter is provied. Please provide correct parameter (selected or deselected)");	
			}
		}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL", "Method 'verifySelectedOrDeselected' : Failed to verify '"+objName + "' element is selected or descelected due to Exception '"+e+"'");			
		}

	}


	/**
	 * This method checks web element(link/button/dropdown etc) is enabled or disabled
	 * @Name verifyEnabledOrDisabled         
	 * @param obj -  WebElement locator class object
	 * @param strSelection - state to be validated (VALUE : enabled, disabled)
	 * @param objName - Name of the WebElement
	 * @applicableTo Desktop, Mobile
	 * @throws Exception
	 * @description This method checks web element(link/button/dropdown etc) is enabled or disabled
	 *                                                       
	 */

	public void VerifyEnabledOrDisabled(By obj,String strSelection, String objName) throws Exception{

		try
		{
			WebElement element = null;	

			//Enabled Validation
			if(strSelection.equalsIgnoreCase("enabled"))
			{
				element=driver.findElement(obj);
				if(element.isEnabled())
				{
					objReport.setValidationMessageInReport("PASS", "'"+objName+"' is enabled");
				}
				else
				{
					objReport.setValidationMessageInReport("FAIL", "'"+objName+"' is disabled");
				}
			}

			//Disabled Validation
			else if(strSelection.equalsIgnoreCase("disabled"))
			{
				element=driver.findElement(obj);
				if(element.isEnabled())
				{
					objReport.setValidationMessageInReport("FAIL", "'"+objName+"' is enabled");
				}
				else
				{
					objReport.setValidationMessageInReport("PASS", "'"+objName+"' is disabled");
				}
			}
			else
			{
				objReport.setValidationMessageInReport("FAIL", "Incorrect '"+strSelection+"' parameter is provied. Please provide correct parameter (enabled or disabled)");	
			}
		}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL", "Method 'VerifyEnabledOrDisabled' : Failed to '"+objName + "' element is enabled or disabled due to Exception '"+e+"'");			
		}

	}

	/**
	 * This method switches the frame based on the frame id/ index provided (strFrameid)
	 * @Name switchFrame        
	 * @param strFrameid - Id/Index of the frame
	 * @param strFrameType - Type of frame identifier (VALUE : INT, STRING, WEBELEMENT, DEFAULT)
	 * @applicableTo Desktop, Mobile Browser
	 * @throws Exception
	 * @description This method switches the frame based on the frame id/ index provided (strFrameid)                                      
	 */

	public void switchFrame(String strFrameid,String strFrameType) throws Exception{


		try{
			String srtUpperCase =String.valueOf(strFrameType.toUpperCase());
			switch (srtUpperCase) {
			case "INT": //To handle if entered frame Id is number
				int  typeInt=Integer.parseInt(strFrameid);
				driver.switchTo().frame(typeInt);
				break;
			case "STRING": //To handle if entered frame Id is string
				driver.switchTo().frame(strFrameid);
				break;
			case "WEBELEMENT": //To handle if entered frame Id is webelement
				driver.switchTo().frame(strFrameid);
				break;
			case "DEFAULT": //To switch default frame
				driver.switchTo().defaultContent();
				break;
			}

		}
		catch (Exception e)
		{
			objReport.setValidationMessageInReport("FAIL", "Method 'switchFrame' : Failed to switch Frame disabled due to Exception '"+e+"'");			  
		}

	}

	/**
	 * This method scrolls in the Web Application page by given units in x/y axis  
	 * @Name scrollPage        
	 * @param scrollX - units scrolled in x- axis
	 * @param scrollY - units scrolled in y- axis
	 * @applicableTo Desktop, Mobile Browser
	 * @description This method scrolls in the Web Application page by given units in x/y axis                                               
	 */
	public void scrollPage(int scrollX,int scrollY) throws Exception{
		try
		{
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy("+scrollX+","+scrollY+")");
		}
		catch(Exception e){	
			objReport.setValidationMessageInReport("FAIL", "Method 'scrollPage' : Failed to scroll page due to Exception '"+e+"'");			  
		}
	}              

	/**
	 * This method will refresh the web application 
	 * @Name pageReload
	 * @applicableTo  Desktop,Mobile
	 * @description  This method will reload the webpage                                           
	 */
	public void pageReload() throws Exception
	{
		try {
			driver.switchTo().defaultContent();
			((JavascriptExecutor) driver).executeScript("location.reload();");

		}catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL", "Method 'pageReload' : Failed to relaod the web page due to Exception '"+e+"'");			  
		}

	}

	/**
	 * This method is used to select the check box
	 * @Name  checkBoxSelect
	 * @param objChkBox By element locator of Check box  web element
	 * @param objChkBoxName  Check box Element Name
	 * @applicableTo  Desktop,Mobile 
	 * @description This method validates if a check box element is unchecked and then selects it.                                            
	 */

	public void checkBoxSelect(By objChkBox, String strChkOrUnchk, String objChkBoxName) throws Exception
	{
		try{
			WebElement chkBoxElement=getElement(objChkBox,objChkBoxName);
			boolean strFlag =chkBoxElement.isSelected();                  
			if ((strFlag == false) && (strChkOrUnchk.equalsIgnoreCase("ON")))
			{
				chkBoxElement.click();    
				objReport.setValidationMessageInReport("PASS", "'"+objChkBox+"' is checked");
			} 

			else if ((strFlag == true) && (strChkOrUnchk.equalsIgnoreCase("OFF")))
			{
				chkBoxElement.click(); 
				objReport.setValidationMessageInReport("PASS", "'"+objChkBox+"' is not checked");
			}

			else 
			{
				objReport.setValidationMessageInReport("FAIL", "Incorrect '"+strChkOrUnchk+"' check or uncheck selection parameter is provied. Please provide correct parameter (ON or OFF)");					
			}
		}
		catch (Exception e)
		{                    
			objReport.setValidationMessageInReport("FAIL", "Method 'checkBoxSelect' : Failed to check or uncheck the '"+objChkBox+"' checkbox due to Exception '"+e+"'");			  
		}
	}

	/**
	 * This method is used to create By locator of elements of Native Mobile App
	 * @Name createElementLocatorObjectFromExcelForMobileApp   
	 * @Applicable Mobile App
	 * @param strElementName-  The name of by locator identifier given in excel repository
	          worksheetObjRepositry-  The name of worksheet which contains the identifier in excel repository
	          deviceOS - OS of device being used. (VALUES: IOS, Android)
	 * @description Creates By class(Element locator) object by using the properties stored in Page Object Repository excel file
	 * @return By element locator
	 */

	// Creating TestObjects using Object properties stored in Excel file for Mobile Application based on their OS.
	public By createElementLocatorObjectFromExcelForMobileApp(String strElementName,String worksheetObjRepositry, String deviceOS) throws Exception
	{
		By objElement=null;
		try
		{

			boolean elelocatorStatusFlag=false;
			String excelObjRepositry =Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+ Runner.properties.getProperty("pomFile");

			FileInputStream fis_PageObjectExcel = new FileInputStream(excelObjRepositry);
			Workbook workBookPageObject = WorkbookFactory.create(fis_PageObjectExcel);
			Sheet sheetPageObject = workBookPageObject.getSheet(worksheetObjRepositry);

			//Navigating to required element in the Worksheet
			int totalRowCount=sheetPageObject.getLastRowNum();
			String objName;
			String objLocator;
			String objLocatorValue="";

			int j=1;
			for (j=1;j<=totalRowCount;j++)
			{
				objName=sheetPageObject.getRow(j).getCell(0).getStringCellValue();
				if(objName.equalsIgnoreCase(strElementName))
				{
					//Retrieving the properties(element locator(like class,id etc ) and locator value) of the  element for which element locator object(By class )need to be created
					objLocator=sheetPageObject.getRow(j).getCell(1).getStringCellValue().toLowerCase();
					if(deviceOS.equalsIgnoreCase("Android")){
						objLocatorValue=sheetPageObject.getRow(j).getCell(2).getStringCellValue();
					}
					else if(deviceOS.equalsIgnoreCase("IOS")){
						objLocatorValue=sheetPageObject.getRow(j).getCell(3).getStringCellValue();
					}

					switch(objLocator) 
					{
					case "id" :
						objElement= By.id(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					case "name" :
						objElement=By.name(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					case "classname" :
						objElement=By.className(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "tagname" :
						objElement=By.tagName(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "linktext" :
						objElement=By.linkText(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "partiallinktext" :
						objElement=By.partialLinkText(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "cssselector" :
						objElement=By.cssSelector(objLocatorValue);
						elelocatorStatusFlag=true;
						break;

					case "xpath" :
						objElement=By.xpath(objLocatorValue);
						elelocatorStatusFlag=true; 
						break;

					default : 
						objReport.setValidationMessageInReport("FAIL", "Incorrect Locator type is available for the Element '"+strElementName+"in '"+worksheetObjRepositry+ "' worksheet of Page object excel repository");					
					}

				}

				if(elelocatorStatusFlag==true)
				{
					break;
				}
			}

			//Verifying Element info is available in the Worksheet of Page Object Excel file
			if(j==totalRowCount+1)
			{
				objReport.setValidationMessageInReport("FAIL", "Element name '"+strElementName+"' passed as arguement is not available in '"+worksheetObjRepositry+ "' worksheet of Page object excel repository");					

			}
			fis_PageObjectExcel.close();
			workBookPageObject=null;
			sheetPageObject=null;
		}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL", "Failed to create locator object for '"+strElementName +"' WebElement available in the '"+worksheetObjRepositry+ "' worksheet of Page object excel repository");					

		}

		return objElement;

	}










	/**
	 * Initializes AppiumDriver instance with specified capabilities. 
	 * 			Validates if the application specified in AppConfig.properties file 
	 * 			is installed and if not then installs it on device opened. 
	 * @Name connectMobileDeviceApp   
	 * @Applicable Mobile App
	 * @param deviceOS- The OS of device to be selected(VALUES: Android, Ios)  
		          OSVersion- The version of OS of the device to be selected(Empty string in case no specific version is required.) 
		          deviceModel- The model of device to be selected(Empty string in case no specific model is required.)
		          deviceId - The model id in case a specific device is to be selected. (Empty string in case a specific device is not required.)
	 * @description Creates Initializes AppiumDriver instance with specified capabilities. 
	 * 				Validates if the application specified in AppConfig.properties file is installed and if not then installs it on device opened. 
	 * @return AppiumDriver
	 */


	
	public AppiumDriver connectMobileDeviceApp(String deviceOS, String OSVersion, String deviceModel, String deviceId) throws Exception
	{   
		String strPerfectoUserName= ApplicationConstants.hostUserName;  

		try{
			//declare the Map for script parameters
			Map<String, Object> params = new HashMap<>();


			//Initialization of variables
			Boolean appInstallationStatus =false;

			String applicationName = ApplicationConstants.appName;
			String hostName=ApplicationConstants.host;


			DesiredCapabilities capabilities = new DesiredCapabilities();
			System.out.println("Run Started");
			capabilities.setCapability("user", ApplicationConstants.hostUserName);
			capabilities.setCapability("password", getDecryptedPassword());
			capabilities.setCapability("platformName", deviceOS);
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("scriptName", "NativeApp");
			capabilities.setCapability("openDeviceTimeout", 2);
			if(!OSVersion.equals("")){
				capabilities.setCapability("platformVersion", OSVersion);
			}
			if(!deviceModel.equals("")){
				capabilities.setCapability("model", deviceModel);
			}
			
			if(deviceOS.equalsIgnoreCase("Android")){
				capabilities.setCapability("model","^((?!Nexus).)*$");
				capabilities.setCapability("model","^((?!(Galaxy Tab|Nexus)).)*$");
				capabilities.setCapability("fullReset",true);
				capabilities.setCapability("app", ApplicationConstants.appAndroidLocation);
				capabilities.setCapability("appPackage", ApplicationConstants.appPackageName);
			}
			else if(deviceOS.equalsIgnoreCase("Ios")){
				capabilities.setCapability("model", "iPhone.*");
				//capabilities.setCapability("app", ApplicationConstants.appIOSLocation);
				capabilities.setCapability("bundleId", ApplicationConstants.appBundleId);
			}
			
			if(!deviceId.equals("")){
				capabilities.setCapability("deviceName", deviceId);
			}
			

			System.out.println(capabilities);

			if (deviceOS.equalsIgnoreCase("Android")){
				driver = new AndroidDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				System.out.println("AndroidDriver");
				appInstallationStatus=checkAppInstallationStatus(driver, deviceOS);
				if(!appInstallationStatus){
					appInstallationStatus=installApp(driver, deviceOS);
					((AppiumDriver)driver).executeScript("mobile:application:open", params);
				}

				else if(appInstallationStatus){
					params.put("identifier", ApplicationConstants.appPackageName);
					objReport.setValidationMessageInReport( "PASS", applicationName+" application is installed and opened in mobile device");
				}
				else{
					objReport.setValidationMessageInReport("FAIL", applicationName+" application is not installed and opened in mobile device" );
				}

			}

			else if (deviceOS.equalsIgnoreCase("iOS")){
				driver = new IOSDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				System.out.println("iOSDriver");
				appInstallationStatus=checkAppInstallationStatus(driver, deviceOS);
				if(!appInstallationStatus){
					appInstallationStatus=installApp(driver, deviceOS);
					((AppiumDriver)driver).executeScript("mobile:application:open", params);
				}               
				else if(appInstallationStatus){
					objReport.setValidationMessageInReport("PASS", applicationName+" application is installed and opened in mobile device");

				}
				else{
					objReport.setValidationMessageInReport("FAIL",applicationName+" application is not installed and opened in mobile device");
				}
			}

			else{

				driver = new RemoteWebDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				System.out.println("remote");
			}

			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

			objReport.setValidationMessageInReport("PASS","User "+ strPerfectoUserName +" connected to mobile device successfully" );                                                        
			//Thread.sleep(2);   
			System.out.println(driver);


		}

		catch(Exception e){
			//do nothing - no driver is active
			//e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL","Unable to connect user "+ strPerfectoUserName +" to mobile device. Please check if the device is being used by another user");
		}

		return (AppiumDriver) driver;

	}

	/**
	 * This method is used to checks whether the Application specified in AppConfig.properties 
	 * is installed in the device opened.
	 * @Name  checkAppInstallationStatus
	 * @param driver Driver Instance
	 * @param deviceOS  Device OS
	 * @applicableTo  Mobile 
	 * @description This method is used to checks whether the Application specified in AppConfig.properties 
	 * is installed in the device opened.
	 *  @return boolean
	 * */

	
	public static boolean checkAppInstallationStatus(WebDriver driver, String deviceOS){
		Boolean appInstallationStatus=false;
		try{
			if(deviceOS.equalsIgnoreCase("Ios")){
				appInstallationStatus=  ((AppiumDriver)driver).isAppInstalled(ApplicationConstants.appBundleId);
			}
			else if(deviceOS.equalsIgnoreCase("Android")){
				appInstallationStatus= ((AppiumDriver)driver).isAppInstalled(ApplicationConstants.appPackageName);
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return appInstallationStatus;

	}

	/**
	 * This method installs the application present at the location specified in 
	 * AppConfig.properties file in the device opened.
	 * @Name  installApp
	 * @param driver Driver Instance
	 * @param deviceOS  Device OS
	 * @applicableTo  Mobile 
	 * @description This method installs the application present at the location specified in 
	 * AppConfig.properties file in the device opened.
	 * Returns true if application installed successfully.
	 * Returns false if application cannot be installed.
	 * @return boolean
	 * */

	
	public static boolean installApp(WebDriver driver, String deviceOS){
		//declare the Map for script parameters
		Boolean installationFlag=false;
		Map<String, Object> params = new HashMap<>();
		try{
			if(deviceOS.equalsIgnoreCase("Ios")){
				params.put("file", ApplicationConstants.appIOSLocation);
				((AppiumDriver)driver).executeScript("mobile:application:install", params);
				installationFlag = ((AppiumDriver)driver).isAppInstalled(ApplicationConstants.appBundleId);
			}
			else if(deviceOS.equalsIgnoreCase("Android")){
				params.put("file", ApplicationConstants.appAndroidLocation);
				((AppiumDriver)driver).executeScript("mobile:application:install", params);
				installationFlag = ((AppiumDriver)driver).isAppInstalled(ApplicationConstants.appBundleId);
			}

		}
		catch (Exception e){

		}
		return installationFlag;

	}

	/**
	 * This method returns the decrypted perfecto password from AppConfig.properties
	 * @Name  getDecryptedPassword
	 * @applicableTo  Mobile 
	 * @description This method returns the decrypted perfecto password from AppConfig.properties
	 * @returnS String
	 * */
	public static String getDecryptedPassword() {
		if (perfectoDecryptedPassword == null || perfectoDecryptedPassword.length() == 0) {
			try {
				perfectoDecryptedPassword = AES.decrypt(ApplicationConstants.hostPassword);
				//perfectoDecryptedPassword = "";
			} catch (Exception e) {
				perfectoDecryptedPassword = null;
				e.printStackTrace();
			}
		}
		return perfectoDecryptedPassword;
	}


	/**
	 * Initializes WebDriver instance with specified capabilities and launches the 
	 * URL provided in AppConfig.properties in the default Mobile Browser			
	 * @Name connectMobileBrowser   
	 * @Applicable Mobile Browser
	 * @param deviceOS- The OS of device to be selected(VALUES: Android, Ios)  
		          OSVersion- The version of OS of the device to be selected(Empty string in case no specific version is required.) 
		          deviceModel- The model of device to be selected(Empty string in case no specific model is required.)
		          deviceId - The model id in case a specific device is to be selected. (Empty string in case a specific device is not required.)
	 * @description Initializes WebDriver instance with specified capabilities.	 
	 * launches the URL provided in AppConfig.properties in the default Mobile Browser				
	 * @return WebDriver
	 */

	
	public  WebDriver connectMobileBrowser(String deviceOS, String OSVersion, String deviceModel, String deviceId) throws Exception{

		String strPerfectoUserName= ApplicationConstants.hostUserName; 
		String browserName="mobileOS";
		String applicationURL = ApplicationConstants.applicationURL;

		try{

			String hostName=ApplicationConstants.host;

			DesiredCapabilities capabilities = new DesiredCapabilities();
			System.out.println("Run Started");
			capabilities.setCapability("user", ApplicationConstants.hostUserName);
			capabilities.setCapability("password", getDecryptedPassword());
			capabilities.setCapability("platformName", deviceOS);
			capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
			capabilities.setCapability("autoWebview", true);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability("openDeviceTimeout", 2);
			if(!OSVersion.equals("")){
				capabilities.setCapability("platformVersion", OSVersion);
			}
			if(!deviceModel.equals("")){
				capabilities.setCapability("model", deviceModel);
			}
			else{
				if(deviceOS.equalsIgnoreCase("Android")){
					capabilities.setCapability("model","^((?!Nexus).)*$");
					capabilities.setCapability("model","^((?!(Galaxy Tab|Nexus)).)*$");
				}
				else if(deviceOS.equalsIgnoreCase("Ios")){
					capabilities.setCapability("model", "iPhone.*");
				}
			}
			if(!deviceId.equals("")){
				capabilities.setCapability("deviceName", deviceId);
			}

			System.out.println(capabilities);

			if (deviceOS.equalsIgnoreCase("Android")){
				driver = new AndroidDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				System.out.println("AndroidDriver");
				driver.navigate().to(applicationURL);
				objReport.setValidationMessageInReport("PASS","User "+ strPerfectoUserName +" connected to mobile device successfully and navigated to application URL" );

			}

			else if (deviceOS.equalsIgnoreCase("iOS")){
				driver = new IOSDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				driver.navigate().to(applicationURL);
				objReport.setValidationMessageInReport("PASS","User "+ strPerfectoUserName +" connected to mobile device successfully and navigated to application URL");
			}
			else{

				driver = new RemoteWebDriver(new URL("https://" + hostName + "/nexperience/perfectomobile/wd/hub"), capabilities);
				System.out.println("remote");
				driver.navigate().to(applicationURL);
				objReport.setValidationMessageInReport("PASS","User "+ strPerfectoUserName +" connected to mobile device successfully and navigated to application URL");
			}

			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);


			//Thread.sleep(2);   
			System.out.println(driver);


		}

		catch(Exception e){
			//do nothing - no driver is active
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL","Unable to connect user "+ strPerfectoUserName +" to mobile device. Please check if the device is being used by another user");
		}

		return  driver;

	}

	/**
	 * Terminates the driver instance			
	 * @Name closeAppandDevice   
	 * @Applicable Mobile Applications
	 * @description Terminates the app and driver instance				
	 * @return void
	 */

	public void closeAppandDevice()
	{
		try{


			driver=(AppiumDriver)WebMobileFunctions.driver;
			((AppiumDriver)driver).closeApp();
			driver.close();
			driver.quit();
			objReport.setValidationMessageInReport("PASS","Device successfully closed.");
		}catch(Exception e){

			objReport.setValidationMessageInReport("FAIL","Device could not be successfully closed due to exception: " + e.getMessage() );
		}
	}




	/**
	 * Terminates the driver instance			
	 * @Name closeDeviceConnection   
	 * @Applicable Mobile 
	 * @description ITerminates the driver instance				
	 * @return void
	 */


	public void closeDeviceConnection(){
		try{

			driver.close();
			driver.quit();
			objReport.setValidationMessageInReport("PASS","Device successfully closed.");
		}catch(Exception e){

			objReport.setValidationMessageInReport("FAIL","Device NOT closed successfully.");
		}
	}
	/**
	 * Captures screenshot		
	 * @Name captureScreenshot   
	 * @Applicable Mobile, Desktop
	 * @description It takes screenshot of the page and puts it in the Test Script result folder				
	 * @return void
	 */

	//@SuppressWarnings("unchecked")
	public void captureScreenshot() 
	{
		try {
			String strStartTime = new SimpleDateFormat("MMddyy_HHmmss").format(Calendar.getInstance().getTime()).replace(":", ".").replace("-", ".");		
			String fileName = Runner.driverMap.get("TCResultFolderLocation").toString()+"\\"+Runner.driverMap.get("TestScriptName").toString()+strStartTime+".jpg";
			Runner.driverMap.put("Screenshot_Link", fileName);

			File scrFile = ((TakesScreenshot)WebMobileFunctions.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(fileName));		
		}
		catch(Exception e) 
		{
		}
	}

	/**
	 * Returns full name of the month based on integer value of month		
	 * @Name getMonthName   
	 * @Applicable Mobile, Desktop
	 * @description Returns full name of the month based on integer value of month				
	 * @return String
	 */
	public String getMonthName(int month) {
		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		return monthNames[month-1];
	}

	/**
	 * Selects Date from IOS Picker wheel	
	 * @Name selectDateIos   
	 * @Applicable Mobile, Desktop
	 * @description Selects Date from IOS Picker wheel					
	 * @return void
	 */

	public void selectDateIos(String strDate, String strMonth, String strYear) throws Exception{
		try{



			List<WebElement>  datePickerList= getListElements(By.className("UIAPickerWheel"), "Date Picker Wheel");

			//HashMap<String, String> deviceProperties = new HashMap<String, String>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("property", "ALL");
			String properties = (String)((AppiumDriver) driver).executeScript("mobile:handset:info", params);
			System.out.println(properties);

			String[] arrProperties = properties.split(",");
			String deviceName = arrProperties[1];




			String strMonthPicker= datePickerList.get(0).getAttribute("value");
			//Point pMonth= datePickerList.get(0).getLocation();
			//Dimension dMonth= datePickerList.get(0).getSize();
			for(int i=0; i<31; i++){
				if(strMonthPicker.equalsIgnoreCase(strMonth) || strMonthPicker.equalsIgnoreCase(strDate)){
					break;
				}
				else{
					if(deviceName.contains("Plus")){
//						((AppiumDriver) driver).tap(1, pMonth.getX()+ dMonth.getWidth(), pMonth.getY() + 550, 100);
					}
					else{
//						((AppiumDriver) driver).tap(1, pMonth.getX()+ dMonth.getWidth(), pMonth.getY() + 200, 100);
					}

					strMonthPicker= datePickerList.get(0).getAttribute("value");
				}	
			}

			String strDatePicker= datePickerList.get(1).getAttribute("value");

			//Point pDate= datePickerList.get(1).getLocation();
			//Dimension dDate= datePickerList.get(1).getSize();
			for(int i=0; i<31; i++){
				if(strDatePicker.equalsIgnoreCase(strDate) || strDatePicker.equalsIgnoreCase(strMonth)){
					break;
				}
				else{
					if(deviceName.contains("Plus")){
//						((AppiumDriver) driver).tap(1, pDate.getX()+ dDate.getWidth(), pDate.getY() + 550 , 100);
					}
					else{
//						((AppiumDriver) driver).tap(1, pDate.getX()+ dDate.getWidth(), pDate.getY() + 200 , 100);
					}

					strDatePicker= datePickerList.get(1).getAttribute("value");
				}
			}

			String strYearPicker= datePickerList.get(2).getAttribute("value");
			//Point pYear= datePickerList.get(2).getLocation();
			//Dimension dYear= datePickerList.get(2).getSize();




			//Creating eleement for Clear Button
			//By Buttonclear = createElementLocatorObjectFromExcelForMobileApp("button_Clear", "AetnaHealthLogin", "iOS");
			//WebElement clearButton = getElement(By.xpath("//UIAButton[@name='Clear']"), "Clear Button");



			//Point pClear=clearButton.getLocation();
			//Dimension dClear= clearButton.getSize();
			for(int i=0; i<100; i++){
				if(strYearPicker.equalsIgnoreCase(strYear)){
					break;
				}
				else{
					if(deviceName.contains("Plus")){
//						((AppiumDriver) driver).tap(1, pYear.getX()+ dYear.getWidth(), (pClear.getY()+dClear.getHeight()+200) , 100);
					}
					else{
//						((AppiumDriver) driver).tap(1, pYear.getX()+ dYear.getWidth(), (pYear.getY()+200) , 100);	
					}

					strYearPicker= datePickerList.get(2).getAttribute("value");
				}
			}


			//Creating element for Done Button and clicking it

			click(By.xpath("//UIAButton[@name='Done']"), "Done Button");
		}
		catch (Exception e)
		{
			System.out.println(e);
			objReport.setValidationMessageInReport( "FAIL", "Exception in selectDateIos keyword: " +e.getMessage());
		}

	}

	/**
	 * Returns integer value of month based on full name	
	 * @Name monthIntValue   
	 * @Applicable Mobile, Desktop
	 * @description Returns integer value of month based on full name			
	 * @return int
	 */

	public int monthIntValue(String strMonthVal) {
		// TODO Auto-generated method stub
		switch(strMonthVal)
		{
		case "January":
			return(01);
		case "February":
			return(02);
		case "March":
			return  (03); 
		case "April":
			return  (04);
		case "May":
			return (05);
		case "June":
			return(06);
		case "July":
			return(07);
		case "August":
			return(8);
		case "September":
			return(9);
		case "October":
			return(10);
		case "November":
			return(11);
		case "December":
			return(12);
		default:
			return(01);
		}

	}

	/**
	 * Selects Date from Android Picker wheel	
	 * @Name selectDateAndroid   
	 * @Applicable Mobile
	 * @description Selects Date from Android Picker wheel					
	 * @return void
	 */

	public void selectDateAndroid(String DOB_Correct) throws Exception{
		try{

			Boolean booMonth_choose= false;
			// Year pick
			String Yearval= DOB_Correct.split("-")[0];
			int iYearval=Integer.parseInt(Yearval);
			By displayYearText=By.xpath("//*[@resource-id='displayYear']");

			click(displayYearText, "Year field");

			Thread.sleep(2);
			//By displayYearValue=By.xpath("//*[@id='65']");

			//String sYearval1="//*[@text='" + iYearval +"']";


			By displayYearValue=By.xpath("//*[@resource-id='" + iYearval +"']");
			click(displayYearValue, "Year field");




			// Month pick
			By lblMonthName=By.xpath("//*[@resource-id='displayMonth']");
			String strMonthVal=getElementText(lblMonthName, "Date Picker > Month Name");
			System.out.println(strMonthVal);
			System.out.println(DOB_Correct);
			while(!booMonth_choose)
			{
				strMonthVal=getElementText(lblMonthName, "Date Picker > Month Name");

				if (monthIntValue(strMonthVal)<Integer.parseInt(DOB_Correct.split("-")[1]))
				{

					By btnNextMonth=By.xpath("//*[@content-desc='Next> Next']");
					click(btnNextMonth, "Next Month Button");
					Thread.sleep(2);
				}
				else if (monthIntValue(strMonthVal)>Integer.parseInt(DOB_Correct.split("-")[1]))

				{

					By btnPrevMonth=By.xpath("//*[@content-desc='Prev< Prev']");
					click(btnPrevMonth, "Previous Month Button");
					Thread.sleep(2);
				}
				else
				{
					booMonth_choose= true;
				}
			}

			// daypic
			By dateValue=By.xpath("//*[@class='android.widget.GridView']/view[*]/view[*]/view[1]");
			List <WebElement> datelist= getListElements(dateValue, "Date field");
			//objCommonFunc.click_button(displayYearValue, "Year field");

			datelist.get(Integer.parseInt(DOB_Correct.split("-")[2])-1).click();


			By btnOK=By.xpath("//*[@contentDesc='OK']");
			click(btnOK, "Date Picker > OK button");



		}	
		catch (Exception e)
		{
			objReport.setValidationMessageInReport( "FAIL", "Exception in selectDateAndroid keyword: " +e.getMessage());
		}

	}



	/**
	 * This method is specific to MOBILE and used to select a date from calendar widget
	 * @param calFieldpropValue
	 * @param Date
	 * @applicableTo  Mobile
	 * @driversBase Appium
	 */
	public void selectDateFromCalendarGrid(By calFieldpropValue, String Date, String deviceOS)
	{
		By dropIOSCalProperties = By.xpath("//UIALink/UIAStaticText[@label = '"+Date+"']" +"|"+"//UIAButton[@label = '"+Date+"']");
		By dropAndroidCalProperties =By.xpath("//android.widget.GridView//android.view.View[contains(@content-desc, '"+Date+"')]"+"|"+"//android.widget.Button[@text='"+Date+"']"); 


		@SuppressWarnings("unused")
		boolean blFlag = true;
		try
		{
			if(deviceOS.equalsIgnoreCase("Android"))
			{

				WebElement ObjElement = getElement(calFieldpropValue, "Android Calculator Element" );
				if(ObjElement.isDisplayed())
				{
					blFlag = true;
					ObjElement.click();

					WebElement objFindDate = getElement(dropAndroidCalProperties,  "Android Calculator Properties");
					objFindDate.click();
				}
				else
				{
					blFlag = false;
					objReport.setValidationMessageInReport("FAIL", "The date in Android was not selected due to exception");
				}
			}

			else if(deviceOS.equalsIgnoreCase("Ios"))
			{

				WebElement ObjElement = getElement(calFieldpropValue, "IOS Calender Element");
				if(ObjElement.isDisplayed())
				{
					if(ObjElement.isEnabled())
					{
						blFlag = true;
						ObjElement.click();
						WebElement objFindDate = getElement(dropIOSCalProperties, "IOS Calender Properties");
						objFindDate.click();
					}
					else
					{
						blFlag = false;
						objReport.setValidationMessageInReport("FAIL", "The date in IOS was not selected.");
					}
				}

			}

		}
		catch (Exception e){

			objReport.setValidationMessageInReport("FAIL", "The date was not selected due to exception: " +e.getMessage());
		}
	}



	
	/**
	 * This method is specific to MOBILE and to swipe Up
	 * @param deviceOS
	 * @applicableTo  Mobile
	 * @driversBase Appium
	 * @return void
	 */
	public void swipeUp(String deviceOS){

		try
		{
			if(deviceOS.equalsIgnoreCase("android"))
			{
				AndroidDriver driverA = (AndroidDriver)driver;

				Dimension pageSize = driverA.manage().window().getSize();
				
				System.out.println(pageSize);

				int starty = (int) (pageSize.height* 0.8);

				int endy = (int) (pageSize.height * 0.4); 
				int startx = (int) (pageSize.width *0.2);


				TouchAction swipeDown = new TouchAction(driverA);
//				swipeDown.press(startx, starty).moveTo(startx,endy).release();
				PointOption point1 = PointOption.point(startx, starty);
				PointOption point2 = PointOption.point(startx,endy);
				swipeDown.press(point1).moveTo(point2).release();
				(driverA).performTouchAction(swipeDown);

				//Reporter.log("Swipe Down is success");
			}
			else if(deviceOS.equalsIgnoreCase("iOS"))
			{

				IOSDriver driverI= (IOSDriver)driver;
				Dimension pageSize = driverI.manage().window().getSize();
				System.out.println(pageSize);

				int starty = (int) (pageSize.height* 0.6);

				int endy = (int) (pageSize.height * 0.4); 
				int startx = (int) (pageSize.width *0.2);


				TouchAction swipeDown = new TouchAction(driverI);
//				swipeDown.press(startx, starty).moveTo(startx,endy).release();
				PointOption point1 = PointOption.point(startx, starty);
				PointOption point2 = PointOption.point(startx,endy);
				swipeDown.press(point1).moveTo(point2).release();

				(driverI).performTouchAction(swipeDown);

			}
			objReport.setValidationMessageInReport("PASS", "Swipe action performed ");
		}
		catch (Exception e){
			objReport.setValidationMessageInReport("FAIL", "Swipe action failed due to exception: " +e.getMessage());
		}

	}

	/**
	 * This method is specific to MOBILE and to swipe Right
	 * @param deviceOS
	 * @applicableTo  Mobile
	 * @driversBase Appium
	 * @return void
	 */
	public void swipeRight(String deviceOS){

		try
		{


			if(deviceOS.equalsIgnoreCase("android"))
			{
				AndroidDriver driverA = (AndroidDriver)driver;
				driverA.context("NATIVE_APP");

				Dimension pageSize = driverA.manage().window().getSize();

				System.out.println(pageSize);

				int startx = (int) (pageSize.width * 0.7);
				int endx = (int) (pageSize.width * 0.3); 
				int starty = pageSize.height / 2;


				TouchAction swipeRight = new TouchAction(driverA);
//				swipeRight.press(startx, starty).moveTo(endx,starty).release();
				PointOption point1 = PointOption.point(startx, starty);
				PointOption point2 = PointOption.point(endx,starty);
				swipeRight.press(point1).moveTo(point2).release();
				(driverA).performTouchAction(swipeRight);


			}
			else if(deviceOS.equalsIgnoreCase("iOS"))
			{
				IOSDriver driverI= (IOSDriver)driver;

				driverI.context("NATIVE_APP");

				Dimension pageSize = driverI.manage().window().getSize();

				System.out.println(pageSize);

				int startx = (int) (pageSize.width * 0.8);
				int endx = (int) (pageSize.width * 0.2); 
				int starty = pageSize.height / 2;


				TouchAction swipeRight = new TouchAction(driverI);
//				swipeRight.press(startx, starty).moveTo(endx,starty).release();
				PointOption point1 = PointOption.point(startx, starty);
				PointOption point2 = PointOption.point(endx,starty);
				swipeRight.press(point1).moveTo(point2).release();
				driverI.performTouchAction(swipeRight);


			}
			objReport.setValidationMessageInReport("PASS", "Swipe action performed ");
		}

		catch (Exception e){
			objReport.setValidationMessageInReport("FAIL", "Swipe right action failed due to exception: " +e.getMessage());
		}

	}


	/**
	 * This method is specific to MOBILE and it is used to hide keyboard
	 * @applicableTo  Mobile
	 * @driversBase Appium 
	 * @return void
	 */
	@SuppressWarnings("unused")
	public void hidKeyboard(String deviceOS) {

		try{

			if(deviceOS.equalsIgnoreCase("android"))
			{
				AndroidDriver driverA = (AndroidDriver)driver;
				driverA.hideKeyboard();
			}

			else if(deviceOS.equalsIgnoreCase("iOS"))
			{
				IOSDriver driverI= (IOSDriver)driver;
				By cancelDoneButton = By.xpath("//*[@name = 'Cancel']"+"|" + "//*[@name = 'Done']" + "|" + "//UIAButton[@name = 'Cancel']"+"|" + "//UIAButton[@name = 'Done']");
				click(cancelDoneButton, "Cancel or Done button on keyboard");

			}
		}
		catch (Exception e){
			objReport.setValidationMessageInReport("FAIL", "Swipe right action failed due to exception: " +e.getMessage());

		}
	}
	
	/**
	 * This method is specific to MOBILE and it is used to check whether a text is displayed on the mobile screen after switching to VISUAL context in Perfecto
	 * @applicableTo  Mobile
	 * @param String text- Value to be checked
	 * 		  int threshold-The acceptable match level percentage, between 20 and 100.
	 * 		  int timeout- in seconds
	 * @driversBase Appium 
	 * @throws Exception
	 * @return String true or false
	 * @description It can be used in situations where element locators are not able to identify a certain element like pop up messages or button.
	 */
	
	public String ocrTextCheck( String text, int threshold, int timeout) {
		// Verify that arrived at the correct page, look for the Header Text
		Map<String, Object> params = new HashMap<>();
		params.put("content", text);
		params.put("timeout", Integer.toString(timeout));
		params.put("measurement", "accurate");
//		The source for retrieving the screen content.//		Camera - Screenshot taken from the video stream
	    params.put("source", "camera");
//	    Automatic - OCR distinguishes between text and images, filtering out images
		params.put("analysis", "automatic");
		if (threshold>0)
			params.put("threshold", Integer.toString(threshold));
		return (String) ((AppiumDriver)driver).executeScript("mobile:checkpoint:text", params);

	}

	/**
	 * This method is specific to MOBILE and it is used to click a text displayed on the mobile screen after switching to VISUAL context in Perfecto
	 * @applicableTo  Mobile
	 * @param String text- Value to be checked
	 * 		  int threshold-The acceptable match level percentage, between 20 and 100.
	 * 		  int timeout- in seconds
	 * @driversBase Appium 
	 * @throws Exception
	 * @description It can be used in situations where element locators are not able to identify a certain element like pop up messages or button.
	 */
	
	public String ocrTextClick(String text, int threshold, int timeout) {
		Map<String, Object> params = new HashMap<>();
		params.put("content", text);
		params.put("timeout", Integer.toString(timeout));
		
		if (threshold>0)
			params.put("threshold", Integer.toString(threshold));
		return (String) ((AppiumDriver)driver).executeScript("mobile:text:select", params);
	}
	
	
	
	/**
	 * This method is specific to MOBILE and it is used to get all the contexts currently open in a device
	 * @applicableTo  Mobile
	 * @driversBase Appium 
	 * @throws Exception
	 * @return Set<String>
	 */
	
	@SuppressWarnings("unchecked")
	public Set<String> getContexts() {
		Set<String> currentContexts=null;
		try{
			currentContexts = ((AppiumDriver)driver).getContextHandles();
			System.out.println(currentContexts);
			return currentContexts;
		}
		catch(Exception e){
			objReport.setValidationMessageInReport("FAIL", "Exception in  getContexts" +e.getMessage());
			return currentContexts;
		}
		
	}
	
	
	/**
	 * This method is specific to MOBILE and it is used to get all the contexts currently open in a device
	 * @applicableTo  Mobile
	 * @params String contextName - Name of the context you want to switch to.
	 * 				  Possible Values- NATIVE_APP, WEB_VIEW, VISUAL etc 
	 * @driversBase Appium 
	 * @throws Exception
	 * @return void
	 */
	
	//@SuppressWarnings( "rawtypes" )
	public void switchToContext(String contextName) {
		Set<String> currentContexts=getContexts();
		try{
			if(currentContexts.contains(contextName)){
				((AppiumDriver)driver).context(contextName);
				objReport.setValidationMessageInReport("PASS", "Context switched to" + contextName);
			}
			else{
				objReport.setValidationMessageInReport("FAIL", "The context: " + contextName + "does not exist");
			}
			
		}
		catch(Exception e){
			objReport.setValidationMessageInReport("FAIL", "Exception in  switchToContext" +e.getMessage());
			
		}
		
	}
	
	/**
	 * This method is used to obtain the page source of the current page displayed and prints it on the console as well.
	 * @applicableTo  Mobile, desktop
	 * @return String
	 */
	
	public String getAppPageSource(){
		String pageSource="";
		pageSource=driver.getPageSource();
		System.out.println(pageSource);
		return pageSource;
	}
	
//******************New Functions*********************
	/**
	 * Double clicks the element(Button,Link,Image)
	 * @Name doubleclick
	 * @param objLocator The By class object of the element to be clicked  
	 * @param objName The Element Name to be clicked    
	 * @applicableTo  Desktop,Mobile   
	 * @description dounle Clicks the element(Button,Link,Image)                              
	 */
	public void doubleclick(By elementLocator,String elementName) 
	{
		try {
			Actions action= new Actions(driver);
			WebElement hoverElement=getElement(elementLocator,elementName);
			action.moveToElement(hoverElement).doubleClick().perform();
			objReport.setValidationMessageInReport("PASS", elementName + " is clicked");

		}
		catch(Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Method doubleclick : Failed due to Exception : "+e);
		}

	}
	
	/**
	 * Closes the browser which is used for running the automation script
	 * @Name closeBrowser  
	 * @applicableTo  Desktop,Mobile   
	 * @description Closes the browser which is used for running the automation script                            
	 */
	public void closeBrowser()
	{		
		try {
			
			driver.close();
			driver.quit();
			objReport.setValidationMessageInReport("PASS" , "Browser is closed");	
		}

		catch (Exception e) 
		{				
			closeAllBrowser();  			
		}
	}
	
	/**
	 * Closes all the existing opened browsers in the system
	 * @Name closeAllBrowser  
	 * @applicableTo  Desktop,Mobile   
	 * @description Closes all the existing opened browsers in the system                    
	 */
	public void closeAllBrowser()
	{
		try
		{	
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM geckodriver.exe");
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM firefox.exe");
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM IEDriverServer.exe");
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM iexplore.exe");
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("cmd /c taskkill /F /IM chrome.exe");					
		}

		catch(Exception e)
		{

		}
	}

	/**
	 * Validates the portion of the label value of the element
	 * @Name verifyElementContainText  
	 *  @param objLocator The By class locator of the element to be searched  
	 * @param objName The Element Name to be searched
	 * @param strExpVal Expected text value
	 * @applicableTo  Desktop,Mobile   
	 * @description Validates the portion of the label value of the element                     
	 */
	public String verifyElementContainText(By objLocator, String objName ,String strExpVal)
	{
		String strValue = "";
		try {
			String strElementText = getElementText(objLocator, objName);
			System.out.println(strValue);

			if (strElementText.toLowerCase().contains(strExpVal.toLowerCase())) 
			{							
				objReport.setValidationMessageInReport("PASS", "'"+objName + "' element label value contains the '"+strExpVal+"' text");		
			}
			else
			{
				objReport.setValidationMessageInReport("FAIL", "'"+strExpVal + "' text Value is not displayed under label value of the '" + objName + "' element. Incorrect value '"+strElementText+"' is displayed" );	
			}
		}
		catch (Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Method verifyElementContainText : Failed due to exception : "+e);		
		}
		return strValue;

	}
	
	/**
	 * Validates the text available in the textbox element
	 * @Name verifyTextboxValue  
	 * @param objLocator The By class locator of the element to be searched  
	 * @param objName The Element Name to be searched
	 * @param strExpVal Expected textbox value
	 * @applicableTo  Desktop,Mobile   
	 * @description Validates the text available in the textbox element                    
	 */
	public void verifyTextboxValue(By objLocator, String objName ,String strExpVal)
	{
		try 
		{
			getElement(objLocator, objName).sendKeys(Keys.CONTROL + "a");

			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_C);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_C);

			Thread.sleep(3000);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();

			String actTextBoxVal = (String) clipboard.getData(DataFlavor.stringFlavor);

			if (actTextBoxVal.equalsIgnoreCase(strExpVal)) 
			{			
				objReport.setValidationMessageInReport("PASS", strExpVal+ " value is available in the '"+objName +"' textbox");								
			}
			else 
			{
				objReport.setValidationMessageInReport("FAIL", strExpVal+ " value is not available in the '"+objName +"' textbox . Incorrect "+actTextBoxVal+ "value is available");					
			}

		} catch (Exception e) 
		{
			objReport.setValidationMessageInReport("FAIL", "Method verifyTextboxValue : Failed due to exception : "+e);
		}

	}

}
