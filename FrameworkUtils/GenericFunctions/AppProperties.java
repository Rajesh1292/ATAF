package GenericFunctions;

//import java.io.FileInputStream;
//import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import TestScriptRunner.Runner;
public final class AppProperties {
	
	private static AppProperties appProperties;
	
	public static String appName= "";
	public static String platform = "";
	public static String testScriptFile="";
	public static String testDataFile="";
	public static String pomFile="";
	public static String host = "";
	public static String hostUserName = "";
	public static String hostPassword = "";
	public static String mobileOS="";
	public static String appPackageName = "";
	public static String appBundleId = "";
	public static String appAndroidLocation = "";
	public static String appIOSLocation = "";
	public static String desktopBrowser="";
	public static String applicationURL ="";
	
	
	private AppProperties() {
		getPropertyValues();
	}

	private void getPropertyValues() {
		try {
			//Properties properties = new Properties();
			Properties properties=Runner.properties;
			//appName = properties.getProperty("appName").trim();
			
			testScriptFile=properties.getProperty("testScriptFile").trim();
			if(!StringUtils.isEmpty(properties.getProperty("platform")))
			{
			platform = properties.getProperty("platform").trim();
			}
			//testDataFile=properties.getProperty("testDataFile").trim();
			if(!StringUtils.isEmpty(properties.getProperty("pomFile"))){
				pomFile=properties.getProperty("pomFile").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("host"))){
				host = properties.getProperty("host").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("hostUserName"))){
				hostUserName = properties.getProperty("hostUserName").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("hostPassword"))){
				hostPassword = properties.getProperty("hostPassword").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("appPackageName"))){
				appPackageName = properties.getProperty("appPackageName").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("appBundleId"))){
				appBundleId = properties.getProperty("appBundleId").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("appAndroidLocation"))){
				appAndroidLocation = properties.getProperty("appAndroidLocation").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("appIOSLocation"))){
				appIOSLocation = properties.getProperty("appIOSLocation").trim();
			}
			if(!StringUtils.isEmpty(properties.getProperty("applicationURL"))){
				applicationURL = properties.getProperty("applicationURL").trim();
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Unable to read configuration file --> " + e.getMessage());
			stopAppExecution();
		}
	}

	public static AppProperties getInstance() {
		if (appProperties == null)
			appProperties = new AppProperties();
		return appProperties;
	}
	
	public static void stopAppExecution() {
		stopAppExecutionWithStatus(1, "Application execution terminated..");
	}

	public static void stopAppExecutionWithStatus(int errorcode, String message) {
		System.out.println("Error Message -> " + message);
		System.out.println("Error Code -> " + errorcode);
		System.exit(errorcode);
	}
	
}