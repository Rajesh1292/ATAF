package TestScriptRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.testng.TestNG;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.DefectLogger.defectLoggerFactory;
import com.DefectLogger.defectLoggerFactory.dmTools;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import GenericFunctions.CommonFunctions;
import GenericFunctions.ExcelUtils;
import GenericFunctions.WebMobileFunctions;
import Reporting.ResultsReport;
import TestResultPostUtil.ResultPostfactory;
import TestResultPostUtil.ResultPostfactory.TMtools;
import atafsecurity.AES;
import cucumber.api.Scenario;
import cucumber.api.cli.Main;

//@SuppressWarnings({"rawtypes","unchecked"})
public class Runner {
	public static String strRerxid=new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	public static boolean testScriptRunErrorStatus = false;
	public static boolean testExecutionStatus = false;
	public static boolean stopTestSuiteRunFlag = false;
	public static String strOnFailureStatus="";
	public static String strTechnology="";
	public static Properties properties;
	public static Map<String,String> driverMap = new HashMap<String,String>();
	public static final Logger logger=Logger.getRootLogger();
	public static String strResourceFldLoc="";
	public static String failedStepScreenshot="";
	public static String strWorkSpcPath="";
	public static boolean loadPropFileFlag = false;
	public static boolean localRunFlag = false;
	public static String StrAppName="";
	public static String strTestScriptName="";
	public static String strStartTime="";
	public static String strEndTime="";
	public static String strTotalTimeTaken="";
	public static String strOverallExectnStatus="";
	public static Date timeBfrExectn=null;
	public static String strCreateSuitePath ="";
	public static String strTSEJsonFile ="";
	public static String strFailedStepMsg ="";
	public static String strFailedKeywrd ="";
	public static String strItrNum = "";
	public static Map<String, ArrayList<String>> strBDDResJson = new HashMap<String, ArrayList<String>>();
	public static Map<String, String> strBDDFailMsgMap = new HashMap<String,String>();
	public static Map<String, String> strBDDScreenshotMap = new HashMap<String,String>();
	public static Scenario strBDDScenario;
	public static String strtTestDataFile="";
	public static String strDataSheetName="";
	public static boolean methodExectnPassStatus=true;
	public static String strBDDFeatureName="";
	public static String strResultFldLoc="";
	public static String strBDDIndicator="";
	public static String strBDDFeatureFile="";
	public static String strTSEStartTime="";
	public static String strTSEEndTime="";
	public static String workingdir="";
	public static String configfile="";
	public static String resdir="";
	public static String strTestScriptFile="";
	public static String strTestScriptFiletabname="Test_Script";
	public static Map<String, String> execResultmap = new HashMap<String,String>();
	public static List<Sheet> wbdatasheet=new ArrayList<>();
	public static List<Row> rowdata=new ArrayList<>();
	public static  List<Row> tcrows=new ArrayList<>();
	public static String strResultFldLocfortest="";
	public static Sheet TSsheetname;
	public static Date suitestarttime;
	public static Date suiteendtime;
	public static String strReportTimestamp="";
	public static String strTMresfileloc="";
	public static Boolean localrunflag=true;
	public static String cucjsonfilename="";
	public static String strJSONResVal="";
	public static String TMtool="";
	public static String TM_RQMenv="";
	public static String DM_RallyWorkspaceName="";
	public static String DM_RTCprojectArea="";
	public static String TM_RQMsuiteID="";
	public static String TM_RQMprojectID="";
	public static String TM_RQMtestType="";
	public static String TM_RQMscriptID="";
	public static String TM_RallyTestType="";
	public static String DM_RallyProjectName="";
	public static String DM_RTCteamName="";
	public static String TM_RallyProjectName="";
	public static String TM_RQMdomainName="";
	public static String TM_RQMreleaseDate="";
	public static String TM_RQMprojectArea="";
	public static String TM_RQMappName="";
	public static String DMtool="";
	public static String DM_RTCenv="";
	public static String DM_RTCdefectType="";
	public static String TM_RallyWorkspaceName="";
	public static String DM_RallyEnvironment="";
	public static String DM_RallyPriority="";
	public static String DM_RallySeverity="";
	public static String DM_RallyBusinessCategory="";
	public static String DM_RallyDefectSource="";
	public static String DM_RallyFoundinAssureCareRelease="";
	public static String DM_RallyTestingType="";
	public static boolean StoryOrTestIdFlag = false;
	public static String StoryOrTestId="";

	public static Boolean failedKeywordStatus  = false;
	public static HashMap<String,Boolean> failedKeywrdmap = new HashMap<String,Boolean>();
	public static String execMethodName = "";
	public static int bddStpCnt = 0;
	public static Map<String,Map<Integer,String>> bddFeatureMap = new HashMap<>();
	public static String bddCurrentStep = "";


	//@SuppressWarnings({ "unused", "static-access" })
	public static void main(String[] args)throws Exception {
		// ATAF-1.1 - New Runner Module
		setStarttimeforExecution();
		System.out.println("Execution Started at - "+strTSEStartTime);
		System.out.println("# of Args are - "+args.length);
		workingdir=System.getProperty("user.dir");
		configfile=workingdir+"\\Config\\AppConfig.properties";
		resdir=workingdir+"\\Resources";
		//Check Prerequisites and decide whether to proceed on execution
		Boolean prereqchecked=validaterunnerPrereq();
		if(!prereqchecked) {
			System.out.println("Prerequisite Validation Failed, Exiting process");
			System.exit(1);
		}

		Boolean successfulflag=true;
		if(args.length==1 || args.length >3){
			System.out.println("Invalid Number of Arguments - "+args.length);
			System.exit(1);
		}
		if(args.length==0)
		{
			//Flow for Local Run - Run all That are marked as Execute and pick Iterations as per the Iteration Column

			// Parse Test script XLS and identify scripts to be run
			List<String> tclist=gettobeExecTests("",0);
			if(tclist.size()==0){
				System.out.println("No Tests to execute. Exiting Process");
				System.exit(1);
			}
			int tccntr=0;
			for (String tc:tclist){
				System.out.println("Executing Test & iteration - "+tc);
				testExecutionStatus=false;

				failedKeywordStatus = false;
				failedKeywrdmap = new HashMap<String,Boolean>();

				//
				successfulflag=triggerRun(tccntr,tc);
				System.out.println("Execution Complete for Test & iteration - "+tc+" -Test successfulflag-"+successfulflag);
				tccntr++;
			}

		}
		else if (args.length>=2)
		{
			localrunflag=false;
			//Flow for Run With script name and Iteration as Argument
			List<String> tclist=gettobeExecTests(args[0],Integer.parseInt(args[1]));
			if(tclist.size()==0){
				System.out.println("No Tests found with name- "+args[0]);
				System.exit(1);
			}
			System.out.println("Executing Test & iteration - "+tclist.get(0));
			testExecutionStatus=false;
			successfulflag=triggerRun(0,tclist.get(0));
			System.out.println("Execution Complete for Test & iteration - "+tclist.get(0)+" -Test successfulflag-"+successfulflag);
		}
		//Create Summary Result JSON
		String tcargs="";
		if (!localrunflag){
			tcargs=args[0]+";"+args[1];
		}
		setEndtimeforExecution();
		CreateexecSummJson(tcargs);

		//Check if TM/DM Tool execution required
		String postResults = getpropertyifavailable("postResults");
		String createDefect = getpropertyifavailable("createDefect");
		if(postResults.equalsIgnoreCase("Y") || createDefect.equalsIgnoreCase("Y")) {

			//Read Summary JSON
			String jsonContent = CommonFunctions.readJSONFile(strTSEJsonFile);
			JsonParser jsonParser = new JsonParser();
			JsonObject tcSummary = new JsonObject();
			JsonObject jo = (JsonObject) jsonParser.parse(jsonContent);
			tcSummary = (JsonObject) jo.get("Summary");

			//Execute TM Utility
			if(postResults.equalsIgnoreCase("Y") && checkUtilSanity(tcSummary, "TM")) {
				String toolToPost = tcSummary.get("TMtool").getAsString();
				String env = tcSummary.get("TM_RQMenv").getAsString();
				String TMuserName = getpropertyifavailable("TMuserName");
				String TMpassword = AES.decrypt(getpropertyifavailable("TMpassword"));
				for(TMtools t:TMtools.values()){
					if(t.name().equalsIgnoreCase(toolToPost)){
						ResultPostfactory resultpost = new ResultPostfactory();
						resultpost.resultPost(strTSEJsonFile, t, env, "NA", TMuserName, TMpassword);
					}
				}
			}

			//Execute DM Utility
			if(createDefect.equalsIgnoreCase("Y") && checkUtilSanity(tcSummary, "DM")) {
				String toolForDefect = tcSummary.get("DMtool").getAsString();
				String env = tcSummary.get("DM_RTCenv").getAsString();
				String defectType = tcSummary.get("DM_RTCdefectType").getAsString();
				String DMuserName = getpropertyifavailable("DMuserName");
				String DMpassword = AES.decrypt(getpropertyifavailable("DMpassword"));
				for(dmTools t : dmTools.values()){
					if(t.name().equalsIgnoreCase(toolForDefect))
						defectLoggerFactory.defectLogger(strTSEJsonFile, t, env, defectType, DMuserName, DMpassword);
				}
			}
		}

		if(args.length==0) // Create COnsolidated Summary report only during Local & RQMrun with Multiple TCs
			createTestSuiteSummaryRep();
		// If run from test management tool, Then exit with success or Failure return code for the test that has been run. This is to update result in TM tool
		if(args.length==2) {
			//Create Attachment file for RQM
			File file = new File("C:\\temp\\temp.txt");
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			FileWriter f1 = new FileWriter ("C:\\temp\\temp.txt");
			f1.write("\\\\"+strTMresfileloc);
			f1.close();
			if(!successfulflag)
				System.exit(1);
		}
	}

	private static void createTestSuiteSummaryRep(){
		//Generate TestSuite Summary Report			
		ResultsReport objResRprt= new ResultsReport();
		objResRprt.generateTestSuiteExectnReport(); 
	}
	private static Boolean CreateexecSummJson(String tcargs) {
		// TODO Auto-generated method stub

		if(execResultmap.keySet().size()==0)
			return false;
		JSONObject objTC_final = new JSONObject();
		List<JSONObject> listOfTCResVal = new ArrayList<JSONObject>();
		int runcnt=1;
		int passcnt=0,failcnt=0,ignorecnt=0,totcnt=0;
		String[] strResValArr=null;
		for(String s:execResultmap.keySet()){
			JSONObject objTC = new JSONObject();
			JSONObject objTC1 = new JSONObject();
			String[] tcitr=s.split(";");
			String tc=tcitr[0];
			String itr=tcitr[1];
			strResValArr=execResultmap.get(s).toString().split(";");
			objTC.put("TestCase", tc);
			objTC.put("BDDIndicator", strBDDIndicator);
			objTC.put("ExeStatus", strResValArr[0]);
			objTC.put("ResAttachment", strResValArr[1]);
			objTC.put("TimeStamp", strResValArr[2]);
			objTC.put("ItrNo", itr);
			objTC.put("TSExectnTime", strResValArr[3]);

			if (StoryOrTestIdFlag == false) {
				if(strResValArr.length==5)
					objTC.put("Comments", strResValArr[4]);
				if(strResValArr.length==6){
					objTC.put("Failed Keyword", strResValArr[4]);
					objTC.put("Failed Step", strResValArr[5]);
				}			
			} else {
				if(strResValArr.length==5) {    
					try {
						if(StoryOrTestIdFlag==true)
							objTC.put("StoryId/TestId", strResValArr[4]);
					} catch (Exception e) {
						objTC.put("Comments", strResValArr[4]);
					}
				}
				if(strResValArr.length==7){
					objTC.put("Failed Keyword", strResValArr[4]);
					objTC.put("Failed Step", strResValArr[5]);
					if(StoryOrTestIdFlag==true)
						objTC.put("StoryId/TestId", strResValArr[6]);
				}
			}

			objTC1.put("TC_"+runcnt, objTC);
			listOfTCResVal.add(objTC1);
			runcnt++;
			if(strResValArr[0].equalsIgnoreCase("PASS")){
				passcnt++;
			}
			else if(strResValArr[0].equalsIgnoreCase("FAIL")){
				failcnt++;
			}
			else if(strResValArr[0].equalsIgnoreCase("IGNORED")){
				ignorecnt++;
			}

		}
		totcnt=passcnt+failcnt+ignorecnt;
		JSONObject objfin_summary = new JSONObject();
		objfin_summary.put("Passed", passcnt);
		objfin_summary.put("Failed", failcnt);
		objfin_summary.put("Ignored", ignorecnt);
		objfin_summary.put("Total Scripts", totcnt);
		objfin_summary.put("Start Time", strTSEStartTime);
		objfin_summary.put("End Time", strTSEEndTime);
		objfin_summary.put("Total Time Taken", getexecutiontime());
		objfin_summary.put("TMtool", TMtool);
		objfin_summary.put("TM_RQMenv", TM_RQMenv);
		objfin_summary.put("TM_RQMprojectArea", TM_RQMprojectArea);
		objfin_summary.put("TM_RQMdomainName", TM_RQMdomainName);
		objfin_summary.put("TM_RQMappName", TM_RQMappName);
		objfin_summary.put("TM_RQMprojectID", TM_RQMprojectID);
		objfin_summary.put("TM_RQMreleaseDate", TM_RQMreleaseDate);
		objfin_summary.put("TM_RQMtestType", TM_RQMtestType);
		objfin_summary.put("TM_RQMsuiteID", TM_RQMsuiteID);
		objfin_summary.put("TM_RQMscriptID", TM_RQMscriptID);
		objfin_summary.put("TM_RallyProjectName", TM_RallyProjectName);
		objfin_summary.put("TM_RallyWorkspaceName", TM_RallyWorkspaceName);
		objfin_summary.put("TM_RallyTestType", TM_RallyTestType);
		objfin_summary.put("DMtool", DMtool);
		objfin_summary.put("DM_RTCenv", DM_RTCenv);
		objfin_summary.put("DM_RTCdefectType", DM_RTCdefectType);
		objfin_summary.put("DM_RTCprojectArea", DM_RTCprojectArea);
		objfin_summary.put("DM_RTCteamName", DM_RTCteamName);
		objfin_summary.put("DM_RallyProjectName", DM_RallyProjectName);
		objfin_summary.put("DM_RallyWorkspaceName", DM_RallyWorkspaceName);
		objfin_summary.put("DM_RallyEnvironment", DM_RallyEnvironment);
		objfin_summary.put("DM_RallyPriority", DM_RallyPriority);
		objfin_summary.put("DM_RallySeverity", DM_RallySeverity);
		objfin_summary.put("DM_RallyBusinessCategory", DM_RallyBusinessCategory);
		objfin_summary.put("DM_RallyDefectSource", DM_RallyDefectSource);
		objfin_summary.put("DM_RallyFoundinAssureCareRelease", DM_RallyFoundinAssureCareRelease);
		objfin_summary.put("DM_RallyTestingType", DM_RallyTestingType);
		objTC_final.put("TestCaseList", listOfTCResVal);
		objTC_final.put("Summary", objfin_summary);
		//SimpleDateFormat format = new SimpleDateFormat("MMddyyyyhhmmss");
		String TargetFolder=Runner.workingdir +"\\Result";
		if(!new File(TargetFolder).exists())
			new File(TargetFolder).mkdirs();
		if(!localrunflag){
			strTSEJsonFile=TargetFolder+"\\TestExecutionResult_"+tcargs+"_"+strReportTimestamp+".json";
		}
		else
			strTSEJsonFile=TargetFolder+"\\TestExecutionResult_MultipleTCs_"+strReportTimestamp+".json";
		try (FileWriter file = new FileWriter(strTSEJsonFile)) 
		{
			//System.out.println(strBasePath+"\\TestScriptExecutionResult_"+DateToStr+".json");
			file.write(objTC_final.toString(6));
			file.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	private static void closebrowsers(){
		//close all browsers if running web tc
		if(strTechnology.equalsIgnoreCase("Web"))									
		{
			try
			{
				if(!(WebMobileFunctions.driver.equals(null)))
				{

					try{
						WebMobileFunctions.driver.close();
						WebMobileFunctions.driver.quit();
						Thread.sleep(2000);
					}
					catch (Exception e)
					{
					}
				}
			}
			catch (Exception e)
			{
			}

		}
	}
	@SuppressWarnings("static-access")
	private static Boolean triggerRun(int tccntr,String TCnItr) throws Exception{
		//clean Driver map for new test case run
		driverMap.clear();
		strJSONResVal="";
		stopTestSuiteRunFlag=false;
		ExcelUtils excelFunc= new ExcelUtils();
		String starttime=getcurrentInstant();
		Boolean succesfullycompleted=true;
		timeBfrExectn=gettimestamp();
		Row rw=tcrows.get(tccntr);
		String[] TCANDITR=TCnItr.split(";");
		strTestScriptName=TCANDITR[0];
		String strItrnum=TCANDITR[1];

		strItrNum=strItrnum;

		//Get Testscript related ata from XL
		strBDDIndicator = rw.getCell(excelFunc.findCol(TSsheetname, "BDD_Ind")).toString().trim();
		strTechnology = rw.getCell(excelFunc.findCol(TSsheetname, "Technology")).toString().trim();
		strOnFailureStatus = rw.getCell(excelFunc.findCol(TSsheetname, "OnFailure")).toString().trim();


		if(strBDDIndicator.equalsIgnoreCase("Y")||strBDDIndicator.equalsIgnoreCase("Yes")) {
			strBDDFeatureFile = rw.getCell(excelFunc.findCol(TSsheetname, "FeatureFile_BDD")).toString().trim();
		}

		//Get RTC/Rally/HPALM story related data
		if(rw.getSheet().getRow(0).getCell(11).getStringCellValue().equalsIgnoreCase("StoryId/TestId")) {
			StoryOrTestIdFlag = true;
			if(!(rw.getCell(11)==null||rw.getCell(excelFunc.findCol(TSsheetname, "StoryId/TestId")).toString().trim().equalsIgnoreCase(""))){
				StoryOrTestId = rw.getCell(excelFunc.findCol(TSsheetname, "StoryId/TestId")).toString().trim();
				/*FeatureFile_RTC featureFile = new FeatureFile_RTC();
				featureFile.createFeatureFile(StoryOrTestId);*/
			}
		}

		driverMap.put("StepFlag", "true");
		driverMap.put("TestScriptName", strTestScriptName);
		System.out.println("Test Type - BDD Indicator-"+strBDDIndicator);
		String strResultfile="";
		// SETTING THE RESULTS FOLDER LOCATION for Test being run
		String strTCFolderName = strTestScriptName+"_"+strItrnum+"_"+setFoldercreationtimestamp();
		strResultFldLocfortest=strResultFldLoc+strTCFolderName;
		File fResultDir = new File(strResultFldLocfortest);
		fResultDir.mkdirs();
		driverMap.put("TCResultFolderLocation",strResultFldLocfortest);
		setTestscriptstartTime();
		if(strBDDIndicator.equalsIgnoreCase("Y")||strBDDIndicator.equalsIgnoreCase("Yes")){
			if(strBDDFeatureFile.isEmpty() && !localrunflag){
				System.out.println("No BDD feature file specified, Exiting");
				System.exit(1);
			}

			strtTestDataFile=resdir+"\\"+rw.getCell(excelFunc.findCol(TSsheetname, "TestData_Path")).toString().trim().split(";")[0];
			strDataSheetName=rw.getCell(excelFunc.findCol(TSsheetname, "TestData_Path")).toString().trim().split(";")[1];
			String strBDDKeywords =getBDDDetails(rw);
			bddFeatureMap = CommonFunctions.readFeatureFile(resdir+"/"+strBDDFeatureFile);
			BDDrunner(strBDDFeatureFile, strBDDKeywords);
			ResultsReport rr = new ResultsReport();
			rr.generateBDDReport();
			strResultfile = strResultFldLocfortest+"/ResultsReport.html";
		}
		else{

			String strRowDataNum=TCANDITR[2];
			String xldatadetails=rw.getCell(excelFunc.findCol(TSsheetname, "TestData_Path")).toString().trim();
			String strDataFileName=xldatadetails.split(";")[0].trim();
			String strDataSheetName=xldatadetails.split(";")[1].trim();
			File f= new File (resdir+"\\"+strDataFileName);
			FileInputStream fi= new FileInputStream(f);
			Workbook objWrkBookData = WorkbookFactory.create(fi);				
			Sheet datasheet = objWrkBookData.getSheet(strDataSheetName);
			Integer inRowDataNum = Integer.parseInt(strRowDataNum);

			//Sheet datasheet=wbdatasheet.get(tccntr);
			Row testdatarow = datasheet.getRow(inRowDataNum);

			runTestScriptKeywords(rw,testdatarow, datasheet);
			strResultfile = strResultFldLocfortest+"/"+"TestNG-Output/ResultsReport.html";

			/*//Print execution status in Data.xls
			try {
				if(testExecutionStatus){
					datasheet.getRow(inRowDataNum).createCell(5).setCellValue("FAIL");
					datasheet.getRow(inRowDataNum).createCell(6).setCellValue(strFailedStepMsg);
				} else {
					datasheet.getRow(inRowDataNum).createCell(5).setCellValue("PASS");
					datasheet.getRow(inRowDataNum).createCell(6).setCellValue("");
				}

				FileOutputStream fileOut = new FileOutputStream(f);
				objWrkBookData.write(fileOut);
				fileOut.close();
			} catch (Exception e) {
				System.out.println("----------Data sheet opened------------------");
			}*/

		}
		closebrowsers();
		String endttime=getcurrentInstant();
		String strResultLocationforJSON= strResultfile.replace("\\","\\\\");
		String res="";
		if(testExecutionStatus){
			res="Fail;";
			succesfullycompleted=false;
		}

		else {
			res = "Pass;"	;
			succesfullycompleted=true;
		}
		strTMresfileloc=strResultLocationforJSON;
		if(strBDDIndicator.equalsIgnoreCase("Y")||strBDDIndicator.equalsIgnoreCase("Yes"))
		{
			if(strBDDFailMsgMap.isEmpty())
				strJSONResVal=res+strResultLocationforJSON+";"+starttime+";"+ endttime;//Nothing Failed
			else{
				String allfailed="";
				String allmsg="";
				/*for(String scn:strBDDFailMsgMap.keySet()){
					String msg=strBDDFailMsgMap.get(scn);
					allfailed="@@@"+scn+"_"+allfailed;
					allmsg=scn+"---->"+msg+"---------"+allmsg;
				}*/

				//Modified by N117876
				for(String scn : strBDDResJson.keySet()) {
					ArrayList<String> stpList = strBDDResJson.get(scn);
					for(String stp : stpList) {
						allfailed = scn;
						allmsg = allmsg+" "+stp;
					}
				}

				strJSONResVal=res+strResultLocationforJSON+";"+starttime+";"+ endttime+";"+allfailed+";"+allmsg;
			}
		}
		else{
			if(!strJSONResVal.trim().isEmpty())
				strJSONResVal=res+strResultLocationforJSON+";"+starttime+";"+ endttime+";"+strJSONResVal;
			else
				strJSONResVal=res+strResultLocationforJSON+";"+starttime+";"+ endttime;
		}

		if (StoryOrTestIdFlag == true)
		{
			strJSONResVal=strJSONResVal+";"+StoryOrTestId;
		}

		execResultmap.put(TCnItr, strJSONResVal);
		return succesfullycompleted;
	}
	private static String getBDDDetails(Row rw){
		String strBDDKeywords="";

		//Checking if RTC_StoryId column is present in TestScript.xls
		int keywordColStart = 11;
		if(StoryOrTestIdFlag == true) {
			keywordColStart = 12;
		}

		for (int j = keywordColStart; j < rw.getPhysicalNumberOfCells(); j++) {
			if(!(rw.getCell(j)==null||rw.getCell(j).getStringCellValue().trim().equalsIgnoreCase(""))){
				if(j==11){
					strBDDKeywords = rw.getCell(j).getStringCellValue();
				} else {
					strBDDKeywords = strBDDKeywords+";"+rw.getCell(j).getStringCellValue();
				}
			}
			//return strBDDKeywords;
		}
		return strBDDKeywords;
	}
	private static Boolean validaterunnerPrereq() {
		// TODO Auto-generated method stub
		Boolean overallres=true;
		String strTestScriptFilename="";
		File f=new File(configfile);
		if(!f.exists()){
			//App Config Doesnt Exist
			System.out.println("Config File Doesnt Exist at"+configfile);
			overallres=overallres && false;
		}
		else {
			//Read Config
			properties=CommonFunctions.LoadProperty(configfile);
			strTestScriptFilename = properties.getProperty("testScriptFile").trim();
			strResultFldLoc=properties.getProperty("resultFolderLoc").trim();
			if(strTestScriptFilename.isEmpty()){
				System.out.println("Config File Doesnt contain value for Test script XLS: strTestScriptFile-"+strTestScriptFile);
				overallres=overallres && false;
			}
			if(strResultFldLoc.isEmpty()){
				System.out.println("Config File Doesnt contain value for Result Location: strResultFldLoc-"+strResultFldLoc);
				overallres=overallres && false;
			}
		}
		f=new File(resdir);
		if(!f.exists()){
			//Resources Folder Doesnt Exist
			System.out.println("resources Dir Doesnt Exist at-"+resdir);
			overallres=overallres && false;
		}
		else {
			strTestScriptFile=resdir+strTestScriptFilename;
			f=new File(strTestScriptFile);
			if(!f.exists()){
				//Resources Folder Doesnt Exist
				System.out.println("Test Script XLS named in  Config: "+strTestScriptFile+" Doesnt Exist at- "+resdir+"\\"+strTestScriptFile);
				overallres=overallres && false;
			}
		}
		//strResourceFldLoc
		f=new File(resdir+"\\Framework");
		FWassetcopy fcopy=new FWassetcopy();
		if(!f.exists()){
			//Resources Folder Doesnt Exist
			System.out.println("Framework resources Dir Doesnt Exist at-"+resdir+" copying resources from Framework Jar");
			f.mkdirs();
			Boolean assetcopied=fcopy.addmissingfiles();
			overallres=overallres && assetcopied;
		}
		else {
			Boolean assetcopied=fcopy.addmissingfiles();
			overallres=overallres && assetcopied;
		}
		f=new File(strResultFldLoc);
		if(!f.exists()){
			//Resources Folder Doesnt Exist
			System.out.println("Result Dir Doesnt Exist at-"+strResultFldLoc);
			overallres=overallres && false;
		}
		// Optional Arguments - Will not stop Test execution

		//Gathere Test Management tool details
		TMtool=getpropertyifavailable("TMtool");
		TM_RQMenv=getpropertyifavailable("TM_RQMenv");
		TM_RQMprojectArea=getpropertyifavailable("TM_RQMprojectArea");
		TM_RQMdomainName=getpropertyifavailable("TM_RQMdomainName");
		TM_RQMappName=getpropertyifavailable("TM_RQMappName");
		TM_RQMprojectID=getpropertyifavailable("TM_RQMprojectID");
		TM_RQMreleaseDate=getpropertyifavailable("TM_RQMreleaseDate");
		TM_RQMtestType=getpropertyifavailable("TM_RQMtestType");
		TM_RQMsuiteID=getpropertyifavailable("TM_RQMsuiteID");
		TM_RallyProjectName=getpropertyifavailable("TM_RallyProjectName");
		TM_RallyWorkspaceName=getpropertyifavailable("TM_RallyWorkspaceName");
		TM_RallyTestType=getpropertyifavailable("TM_RallyTestType");
		TM_RQMscriptID=getpropertyifavailable("TM_RQMscriptID");

		//Gathere Defect Management tool details
		DMtool=getpropertyifavailable("DMtool");
		DM_RTCenv=getpropertyifavailable("DM_RTCenv");
		DM_RTCdefectType=getpropertyifavailable("DM_RTCdefectType");
		DM_RTCprojectArea=getpropertyifavailable("DM_RTCprojectArea");
		DM_RTCteamName=getpropertyifavailable("DM_RTCteamName");
		DM_RallyProjectName=getpropertyifavailable("DM_RallyProjectName");
		DM_RallyWorkspaceName=getpropertyifavailable("DM_RallyWorkspaceName");
		DM_RallyEnvironment=getpropertyifavailable("DM_RallyEnvironment");
		DM_RallyPriority=getpropertyifavailable("DM_RallyPriority");
		DM_RallySeverity=getpropertyifavailable("DM_RallySeverity");
		DM_RallyBusinessCategory=getpropertyifavailable("DM_RallyBusinessCategory");
		DM_RallyDefectSource=getpropertyifavailable("DM_RallyDefectSource");
		DM_RallyFoundinAssureCareRelease=getpropertyifavailable("DM_RallyFoundinAssureCareRelease");
		DM_RallyTestingType=getpropertyifavailable("DM_RallyTestingType");

		return overallres;
	}

	public static String getpropertyifavailable(String propname){
		Object obj=propname;
		String retval="";
		if (properties.containsKey(obj)){
			retval=properties.getProperty(propname).trim();
		}
		return retval;
	}
	public static void setStarttimeforExecution(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		suitestarttime = new Date();
		strTSEStartTime = sdf1.format(suitestarttime);
	}
	public static void setTestscriptstartTime(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		//suitestarttime = new Date();
		strStartTime = sdf1.format( new Date());
	}
	public static void setEndtimeforExecution(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		suiteendtime = new Date();
		strTSEEndTime = sdf1.format(suiteendtime);
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyyhhmmss");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		strReportTimestamp=format.format(suiteendtime);
	}
	public static String setFoldercreationtimestamp(){
		//SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		//suiteendtime = new Date();
		//strTSEEndTime = sdf1.format(suiteendtime);
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyyhhmmss");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		return format.format(new Date());
	}
	public static String getexecutiontime(){
		long timediff = suiteendtime.getTime() - suitestarttime.getTime(); 
		long diffSeconds = timediff / 1000 % 60;
		long diffMinutes = timediff / (60 * 1000) % 60;
		long diffHours = timediff / (60 * 60 * 1000) % 24;

		String strTSETotalTimeTaken=Long.toString(diffHours)+"h :"+Long.toString(diffMinutes)+"m :"+Long.toString(diffSeconds)+"s";
		return strTSETotalTimeTaken;
	}

	@SuppressWarnings("static-access")
	public static  List<String> gettobeExecTests(String tcname, int itrnum)  {
		Boolean Localrunflag=true;
		String xlcolName="";
		String xlcolvaltocheck="";
		if(!(tcname.isEmpty() && itrnum==0)){
			Localrunflag=false;
		}
		if(Localrunflag){
			xlcolName = "Execute";
			xlcolvaltocheck="yes";
		}
		else{
			xlcolName = "TestScript_Name";
			xlcolvaltocheck=tcname;
		}
		ExcelUtils excelFunc= new ExcelUtils();
		List<String> tobeExcuted = new ArrayList<String>();
		String xlTCsheetname=strTestScriptFile;
		String TCSheetname="Test_Script";
		int r=0, intNoOfRowsTC;
		Row rwScriptRowNumInTS=null;
		//List<Integer> testCases = new ArrayList<Integer>();
		File f = new File(xlTCsheetname);
		String basepath=f.getParent();
		FileInputStream objTSExcel;
		try {
			objTSExcel = new FileInputStream(f);
			Workbook objTSWrkBookData = WorkbookFactory.create(objTSExcel);
			Sheet objWrkSheetData = objTSWrkBookData.getSheet(TCSheetname);
			TSsheetname=objWrkSheetData;
			//Row rowData=null;
			Row rw=null;
			//Row rw_header=null;
			intNoOfRowsTC= objWrkSheetData.getLastRowNum()+1;
			//			 intNoOfRowsTC= objWrkSheetData.getLastRowNum();

			String TesttobeExcuted;
			int startIternum;
			int endIternum ;
			String itrRunTypefortest ;

			for(r=1;r<intNoOfRowsTC;r++)
			{	
				rw = objWrkSheetData.getRow(r);

				String xldatadetails=rw.getCell(excelFunc.findCol(objWrkSheetData, "TestData_Path")).toString().trim();
				String strDataFileName=xldatadetails.split(";")[0].trim();
				String strDataSheetName=xldatadetails.split(";")[1].trim();
				strBDDIndicator = rw.getCell(excelFunc.findCol(TSsheetname, "BDD_Ind")).toString().trim();
				File datafile= new File(basepath+"\\"+strDataFileName);
				if(rw.getCell(excelFunc.findCol(objWrkSheetData, xlcolName)).toString().trim().equalsIgnoreCase(xlcolvaltocheck)){
					if (rw.getCell(excelFunc.findCol(objWrkSheetData, "TestScript_Name")).toString().trim() != null || rw.getCell(excelFunc.findCol(objWrkSheetData, "TestScript_Name")).toString().trim()==""){
						rwScriptRowNumInTS=objWrkSheetData.getRow(r);
						TesttobeExcuted=rw.getCell(excelFunc.findCol(objWrkSheetData, "TestScript_Name")).toString().trim();
						itrRunTypefortest=rw.getCell(excelFunc.findCol(objWrkSheetData, "IterationMode")).toString().trim();

						startIternum=Integer.parseInt(CommonFunctions.getCellValueAsString(rw.getCell(excelFunc.findCol(objWrkSheetData, "StartIteration"))));
						endIternum=Integer.parseInt(CommonFunctions.getCellValueAsString(rw.getCell(excelFunc.findCol(objWrkSheetData, "EndIteration"))));
						if (strBDDIndicator.equalsIgnoreCase("Y")){

							if(itrRunTypefortest.equalsIgnoreCase("RunOneIterationOnly"))
							{
								tobeExcuted.add(TesttobeExcuted+";"+1);
								tcrows.add(rw); 
							}

							else if(itrRunTypefortest.equalsIgnoreCase("RunRangeOfIterations"))
							{
								for (int i=startIternum; i<= endIternum ; i++ )
								{
									tobeExcuted.add(TesttobeExcuted+";"+i);
									tcrows.add(rw);
								}
							}

						}
						else{
							if (Localrunflag)
								tobeExcuted.addAll(gettestswithiterations(rwScriptRowNumInTS,datafile,strDataSheetName,TesttobeExcuted,itrRunTypefortest,startIternum,endIternum));
							else{
								tobeExcuted.addAll(gettestswithiterations(rwScriptRowNumInTS,datafile,strDataSheetName,TesttobeExcuted,"RunOneIterationOnly",itrnum,itrnum));
								objTSExcel.close();
								System.out.println("# of Test to Execute - "+tobeExcuted.size());
								return tobeExcuted;
							}
						}


					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return tobeExcuted;	  
	}




	@SuppressWarnings("static-access")
	private static List<String> gettestswithiterations(Row rw,File strtTestDataFile, String strDataSheetName, String strTestScriptName, String strdrIterationMode,int intdsStart,int intdsEnd) 
	{			
		ArrayList<String> tobeExcutedwithitr = new ArrayList<String>();
		// TODO Auto-generated method stub
		ExcelUtils excelFunc= new ExcelUtils();		
		FileInputStream objDataExcel;
		try {
			objDataExcel = new FileInputStream(strtTestDataFile);
			Workbook objWrkBookData = WorkbookFactory.create(objDataExcel);

			Sheet objWrkSheetData = objWrkBookData.getSheet(strDataSheetName);
			Row rowData=null;
			//Get the total number of row in the worksheet of Data.xls excel containing the arguments information
			int intNoOfRowsTestdata= objWrkSheetData.getLastRowNum()+1;

			for(int dtstcnt=1;dtstcnt<intNoOfRowsTestdata;dtstcnt++){
				rowData = objWrkSheetData.getRow(dtstcnt);
				String dstcname = rowData.getCell(excelFunc.findCol(objWrkSheetData, "pScriptName")).toString().trim();
				int dsitrnum= Integer.parseInt(CommonFunctions.getCellValueAsString(rowData.getCell(excelFunc.findCol(objWrkSheetData, "pIterationNo"))));
				if(dstcname.equalsIgnoreCase(strTestScriptName) && strdrIterationMode.equalsIgnoreCase("RunOneIterationOnly") && dsitrnum == intdsStart)
				{
					//tobeExcutedwithitr.add(strTestScriptName+";"+intdsStart);
					//rowdata.add(rowData);
					//wbdatasheet.add(objWrkSheetData);	

					tobeExcutedwithitr.add(strTestScriptName+";"+intdsStart+";"+Integer.toString(dtstcnt)+";"+strDataSheetName);
					tcrows.add(rw);										
					objDataExcel.close();
					return tobeExcutedwithitr;
				}
				if(dstcname.equalsIgnoreCase(strTestScriptName) && strdrIterationMode.equalsIgnoreCase("RunAllIterations"))
				{
					//tobeExcutedwithitr.add(strTestScriptName+";"+dsitrnum);					
					//rowdata.add(rowData);
					//wbdatasheet.add(objWrkSheetData);

					tobeExcutedwithitr.add(strTestScriptName+";"+dsitrnum+";"+Integer.toString(dtstcnt)+";"+strDataSheetName);
					tcrows.add(rw);				
				}
				else if(dstcname.equalsIgnoreCase(strTestScriptName) && strdrIterationMode.equalsIgnoreCase("RunRangeOfIterations") && dsitrnum >= intdsStart && dsitrnum <= intdsEnd)
				{
					//tobeExcutedwithitr.add(strTestScriptName+";"+dsitrnum);					
					//rowdata.add(rowData);
					//wbdatasheet.add(objWrkSheetData);

					tobeExcutedwithitr.add(strTestScriptName+";"+dsitrnum+";"+Integer.toString(dtstcnt)+";"+strDataSheetName);
					tcrows.add(rw);

				}
			}
			if(tobeExcutedwithitr.size()==0 && (strdrIterationMode.equalsIgnoreCase("RunAllIterations") || strdrIterationMode.equalsIgnoreCase("RunOneIterationOnly"))){
				//No Match found for the TC and ITR
				String curtimestamp=getcurrentInstant();
				String strResVal="Ignored;;"+curtimestamp+";"+ curtimestamp+";Iteration Number specified in Testscript_XLS Not Found in Data_XLS";
				execResultmap.put(strTestScriptName+";"+intdsStart, strResVal);
			}
			else if (strdrIterationMode.equalsIgnoreCase("RunRangeOfIterations") && tobeExcutedwithitr.size()<intdsEnd-intdsStart+1){
				//# of ITRs expected is less than range specified
				List<String> itrs=new ArrayList<>();
				for (String TC:tobeExcutedwithitr){
					String [] tcargs=TC.split(";");
					String itrnum=tcargs[1];
					itrs.add(itrnum);
				}
				for(int cnt= intdsStart; cnt<=intdsEnd;cnt++){
					if(!itrs.contains(""+cnt)){
						//Itr not found, add to result map
						String curtimestamp=getcurrentInstant();
						String strResVal="Ignored;;"+curtimestamp+";"+ curtimestamp+";Iteration Number specified in Testscript_XLS Not Found in Data_XLS";
						execResultmap.put(strTestScriptName+";"+cnt, strResVal);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//objDataExcel.close();
		return tobeExcutedwithitr;
	}

	public static String getcurrentInstant(){
		Date current_date = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss");
		return date.format(current_date);
	}
	public static Date gettimestamp(){
		//SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
		Date time = new Date();
		return time;
	}
	///////////Runner Methods for Test NG run of Keywords & BDD runner for BDD feature files	
	/**
	 * @Name runTestScriptKeywords   
	 * @description - Execute the keywords of the Test scripts
	 */
	@SuppressWarnings("static-access")
	public static void runTestScriptKeywords(Row rwScriptRowNumInTS,Row rowData, Sheet objWrkSheetData) throws Exception
	{		
		File f=null;
		try
		{
			ExcelUtils excelFunc= new ExcelUtils();

			String strTSName=rowData.getCell(0).toString().trim();		
			int intNoOfCols = rwScriptRowNumInTS.getLastCellNum()-1;
			String strkeywordName = null;
			String strvariableNames =null;
			String [] arrvariableNamArr = null;

			String strTestNGOutputFld=strResultFldLocfortest+"\\"+"TestNG-Output";
			File fTestNGOutputFld = new File(strTestNGOutputFld);
			fTestNGOutputFld.mkdir();

			DocumentBuilderFactory dbFactory =DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// root element
			Element suite = doc.createElement("suite");
			doc.appendChild(suite);
			Attr attrSuite = doc.createAttribute("name");
			attrSuite.setValue("suite1");
			suite.setAttributeNode(attrSuite);

			Element listnrs = doc.createElement("listeners");
			suite.appendChild(listnrs);


			Element listnr1 = doc.createElement("listener");
			listnrs.appendChild(listnr1);
			Attr attrlistnr1 = doc.createAttribute("class-name");
			attrlistnr1.setValue("Reporting.TestScriptExectnStatusListener");
			listnr1.setAttributeNode(attrlistnr1);

			Element listnr = doc.createElement("listener");
			listnrs.appendChild(listnr);
			Attr attrlistnr = doc.createAttribute("class-name");
			attrlistnr.setValue("Reporting.ResultsReport");
			listnr.setAttributeNode(attrlistnr);

			//********

			int testCnt=1;

			//Checking if RTC_StoryId column is present in TestScript.xls
			int keywordColStart = 11;
			if(StoryOrTestIdFlag == true) {
				keywordColStart = 12;
			}
			for (int k = keywordColStart;k<=intNoOfCols;k++)
			{		
				testCnt= testCnt+1;
				String val="";
				try{
					val = rwScriptRowNumInTS.getCell(k).toString().trim();
				}
				catch (Exception e)
				{

				}

				if (!val.equalsIgnoreCase(""))
				{
					if(val.contains(","))
					{
						strkeywordName = val.split(",")[0];
						strvariableNames = val.split(",")[1];
						arrvariableNamArr = strvariableNames.split("-");							
						String strMethod= arrvariableNamArr[1].replace("\n", ""); 

						Element test2 = doc.createElement("test");
						suite.appendChild(test2);
						Attr attrTest2 = doc.createAttribute("name");
						attrTest2.setValue(strMethod);
						test2.setAttributeNode(attrTest2);

						Element classes2 = doc.createElement("classes");
						test2.appendChild(classes2);

						Element cls2 = doc.createElement("class");
						classes2.appendChild(cls2);
						Attr attrClass2 = doc.createAttribute("name");
						attrClass2.setValue("Test."+strkeywordName);
						cls2.setAttributeNode(attrClass2);

						Element methods2 = doc.createElement("methods");
						cls2.appendChild(methods2);

						Element include2 = doc.createElement("include");
						methods2.appendChild(include2);
						Attr attrInclude2 = doc.createAttribute("name");
						attrInclude2.setValue(arrvariableNamArr[1].replace("\n", ""));
						include2.setAttributeNode(attrInclude2);

						if(arrvariableNamArr.length > 2)
						{
							String[] strDataArgs = new String[arrvariableNamArr.length-2];

							for(int intvariableNamArr = 2;intvariableNamArr<arrvariableNamArr.length;intvariableNamArr++ )
							{
								arrvariableNamArr[intvariableNamArr] = arrvariableNamArr[intvariableNamArr].replace("\n", ""); 
								int ColArg=excelFunc.findCol(objWrkSheetData, arrvariableNamArr[intvariableNamArr]);

								String argValue="";
								try{
									argValue=rowData.getCell(ColArg).toString().trim();
								}
								catch (Exception e)
								{
									argValue="";
								}

								strDataArgs[intvariableNamArr-2]=argValue;
							}

							String strParams="";
							strParams=strDataArgs[0];
							for (int m=1;m<strDataArgs.length;m++)
							{
								strParams=strParams+" #@ "+strDataArgs[m];															
							}
							Element param2 = doc.createElement("parameter");
							methods2.appendChild(param2);

							Attr attrParam3 = doc.createAttribute("name");
							attrParam3.setValue("strParams");
							param2.setAttributeNode(attrParam3);

							Attr attrParam4 = doc.createAttribute("value");
							attrParam4.setValue(strParams);
							param2.setAttributeNode(attrParam4);
						}
					}
					else
					{
						break;
					}
				}
			}

			strResourceFldLoc=resdir+"\\";
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(strResourceFldLoc+strTSName+".xml"));

			transformer.transform(source, result);

			f = new File(strResourceFldLoc+strTSName+".xml");

			// Create object of TestNG Class
			TestNG runner=new TestNG();
			runner.setOutputDirectory(strTestNGOutputFld);

			// Create a list of String 
			List<String> suitefiles=new ArrayList<String>();

			// Add xml file which you have to execute
			//System.out.println(strResourceFldLoc+strTSName+".xml");

			suitefiles.add(strResourceFldLoc+strTSName+".xml");
			//suitefiles.add("C://Data//Applications//MyAetnaCrossBrowser//Resources//01_Vrfy_PlanDetails_Navigation.xml");
			// now set xml file for execution
			runner.setTestSuites(suitefiles);
			//	runner.

			// finally execute the runner using run method
			//	System.out.println("Start ruynner");
			runner.run();
			//System.out.println("End Runner");
		}

		catch (Exception e)
		{
			System.out.println("XML Exception");
			e.printStackTrace();
			//System.exit(1);
		}
		finally
		{
			f.delete();
		}
	}

	public static void BDDrunner(String  strBDDFeatureFile, String strBDDKeywords) {
		try {
			String featureFileScenario = "";
			File cucjsonfile=new File (System.getProperty("user.dir")+"\\Cucumber.json");
			cucjsonfilename=cucjsonfile.getAbsolutePath();
			if (cucjsonfile.exists())
				cucjsonfile.delete();
			if(strBDDKeywords.contains(";")) {
				String featureScenarioDtls[] = strBDDKeywords.split(";");
				//File bddfeaturefile=new File("resdir\\"+strBDDFeatureFile);
				//while(bddfeaturefile.)
				for (int i = 0; i < featureScenarioDtls.length; i++) {
					//Check if Scenario name exists in BDD feature
					//if()
					if(i==0){
						featureFileScenario = "@"+featureScenarioDtls[i];
					} else {
						featureFileScenario = featureFileScenario+",@"+featureScenarioDtls[i];
					}
				}
			} else if(!(strBDDKeywords.trim().equalsIgnoreCase(""))){
				featureFileScenario = "@"+strBDDKeywords;
			}

			//strBDDFeatureFile=strBDDFeatureFile;
			if(!featureFileScenario.equalsIgnoreCase("")) {
				String[] options ={"-g", "stepDefinition","-t", featureFileScenario,"-p","html:Report","-p","json:"+cucjsonfilename,  "Resources\\"+strBDDFeatureFile};
				//				String[] options ={"-g", "stepDefinition","-t", "@Scenario1,@Scenario2","-p","html:Report","-p","json:"+Runner.strResultFldLoc+"/Cucumber.json",  "Resources\\"+strBDDFeatureFile};
				Main.run(options, Thread.currentThread().getContextClassLoader());
			} else {
				String[] options ={"-g", "stepDefinition","-p","html:Report","-p","json:"+cucjsonfilename,  "Resources\\"+strBDDFeatureFile};
				Main.run(options, Thread.currentThread().getContextClassLoader());
			}  
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static boolean checkUtilSanity(JsonObject tcSummary, String tool) {
		// TODO Auto-generated method stub
		switch(tool) {
		case "TM":
			String toolToPost = tcSummary.get("TMtool").getAsString();
			switch(toolToPost) {
			case "RQM":
				String env = tcSummary.get("TM_RQMenv").getAsString();
				String projectArea = tcSummary.get("TM_RQMprojectArea").getAsString();
				String domainName = tcSummary.get("TM_RQMdomainName").getAsString();
				String appName = tcSummary.get("TM_RQMappName").getAsString();
				String projectID = tcSummary.get("TM_RQMprojectID").getAsString();
				String testType = tcSummary.get("TM_RQMtestType").getAsString();
				String releaseDate = tcSummary.get("TM_RQMreleaseDate").getAsString();
				String suiteID = tcSummary.get("TM_RQMsuiteID").getAsString();
				String scriptID = tcSummary.get("TM_RQMscriptID").getAsString();

				if(!(env.isEmpty()||projectArea.isEmpty()||domainName.isEmpty()||appName.isEmpty()
						||projectID.isEmpty()||testType.isEmpty()||releaseDate.isEmpty()||suiteID.isEmpty()
						||scriptID.isEmpty()))
					return true;
				break;

			case "Rally":
				String projectName = tcSummary.get("TM_RallyProjectName").getAsString();
				String workspaceName = tcSummary.get("TM_RallyWorkspaceName").getAsString();
				String rallyTestType = tcSummary.get("TM_RallyTestType").getAsString();

				if(!(projectName.isEmpty()||workspaceName.isEmpty()||rallyTestType.isEmpty()))
					return true;
				break;

			default:
				return false;
			}

		case "DM":
			String toolForDefect = tcSummary.get("DMtool").getAsString();
			switch(toolForDefect) {
			case "RTC":
				String env = tcSummary.get("DM_RTCenv").getAsString();
				String defectType = tcSummary.get("DM_RTCdefectType").getAsString();
				String projectArea = tcSummary.get("DM_RTCprojectArea").getAsString();
				String teamName = tcSummary.get("DM_RTCteamName").getAsString();

				if(!(env.isEmpty()||defectType.isEmpty()||projectArea.isEmpty()||teamName.isEmpty()))
					return true;
				break;

			case "Rally":
				String projectName = tcSummary.get("DM_RallyProjectName").getAsString();
				String workspaceName = tcSummary.get("DM_RallyWorkspaceName").getAsString();

				if(!(projectName.isEmpty()||workspaceName.isEmpty()))
					return true;
				break;

			default:
				return false;
			}
		}
		return false;
	}
}
