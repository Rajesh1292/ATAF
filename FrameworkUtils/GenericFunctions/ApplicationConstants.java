package GenericFunctions;
@SuppressWarnings("static-access")
public class ApplicationConstants {
	//Application name (Name of the Application Folder)
	//static AppProperties ap=new AppProperties();
	
	public static String appName = AppProperties.getInstance().appName;
	
	//Platform for automation(Desktop/MobileBrowser/MobileApp)
	public static String platform = AppProperties.getInstance().platform;
	
	//Relative location of TestScript.xls
	public static String testScriptFile= AppProperties.getInstance().testScriptFile;
	
	//Relative location of Data.xls
	public static String testDataFile= AppProperties.getInstance().testDataFile;
		
	//Relative location of PageObject.xls
	public static String pomFile= AppProperties.getInstance().pomFile;
	
	
	//Application url for Desktop/ Mobile Web Applications
	public static String applicationURL = AppProperties.getInstance().applicationURL;
	
	//Perfecto host address
	public static String host = AppProperties.getInstance().host;
	
	//Perfecto UserName
	public static String hostUserName = AppProperties.getInstance().hostUserName;
	
	//Encrypted Perfecto password
	public static String hostPassword =  AppProperties.getInstance().hostPassword;
	
	//Mobile OS to be used(Android/IOS)
	public static String mobileOS = AppProperties.getInstance().mobileOS;
	
	//Desktop browser to be used
	public static String desktopBrowser = AppProperties.getInstance().desktopBrowser;
		
	//For Android App
	public static String appPackageName = AppProperties.getInstance().appPackageName;
	
	//For IOS App
	public static String appBundleId = AppProperties.getInstance().appBundleId;
	
	//Android App Location in Perfecto Repositry
	public static String appAndroidLocation = AppProperties.getInstance().appAndroidLocation;
	
	//IOS App Location in Perfecto Repositry
	public static String appIOSLocation = AppProperties.getInstance().appIOSLocation; 
	

}


