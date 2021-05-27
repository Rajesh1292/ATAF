package Reporting;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
//import java.text.NumberFormat;
import java.text.SimpleDateFormat;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import GenericFunctions.CommonFunctions;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.parser.JSONParser;


import java.util.ArrayList;
import java.util.Base64;

//import org.testng.IReporter;


import TestScriptRunner.Runner;

public class ResultsReport implements IReporter
{
	//private static Object parent=null;
	//This is the customize emailabel report template file path.
	//private static final String emailableReportTemplateFile = Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\BaseReportFormat.html";

	String strResrcFldrLocPath1="";

	//private static final String emailableReportTemplateFile = Runner.strResourceFldLoc+"\\BaseReportFormat.html";

	public static int counter = 0;

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// Adding Code to update Execution Result map to add Failed Keyword and error message


		try
		{
			addfiledkeywordnMessage(suites);
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
			Date timeAfrExectn = new Date();
			Runner.strEndTime = sdf1.format(timeAfrExectn);
			//System.out.println();

			long timediff = timeAfrExectn.getTime() - Runner.timeBfrExectn.getTime(); 

			long diffSeconds = timediff / 1000 % 60;
			long diffMinutes = timediff / (60 * 1000) % 60;
			long diffHours = timediff / (60 * 60 * 1000) % 24;

			Runner.strTotalTimeTaken=Long.toString(diffHours)+"h :"+Long.toString(diffMinutes)+"m :"+Long.toString(diffSeconds)+"s"; 

			if(Runner.testExecutionStatus==false)
			{							
				Runner.strOverallExectnStatus="PASS";
			}
			else
			{
				Runner.strOverallExectnStatus="FAIL";
			}


			// Get content data in TestNG report template file.
			String customReportTemplateStr = this.readEmailabelReportTemplate();

			// Create custom report title.
			String customReportTitle = this.getCustomReportTitle("Automation Test Script Execution Report");

			// Create test suite summary data.
			String customSuiteSummary = this.getTestSuiteSummary(suites);

			// Create test methods summary data.
			String customTestMethodSummary = this.getTestMehodSummary(suites);

			// Replace report title place holder with custom title.
			customReportTemplateStr = customReportTemplateStr.replace("TestNG_Custom_Report_Title", customReportTitle);

			// Replace test suite place holder with custom test suite summary.
			customReportTemplateStr = customReportTemplateStr.replace("Test_Case_Summary", customSuiteSummary);

			// Replace test methods place holder with custom test method summary.
			customReportTemplateStr = customReportTemplateStr.replace("Test_Case_Detail", customTestMethodSummary);

			// Write replaced test report content to custom-emailable-report.html.
			File targetFile = new File(outputDirectory + "/ResultsReport.html");
			FileWriter fw = new FileWriter(targetFile);
			fw.write(customReportTemplateStr);
			fw.flush();
			fw.close();

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void addfiledkeywordnMessage(List<ISuite> suites) {
		// TODO Auto-generated method stub
		Boolean firsterrorfound=false;
		for(ISuite tempSuite: suites)
		{
			Map<String, ISuiteResult> testResults = tempSuite.getResults();
			for (ISuiteResult result : testResults.values()) {
				ITestContext testObj = result.getTestContext();
				//String keyword = testObj.getName();
				IResultMap testFailedResult = testObj.getFailedTests();
				if (testFailedResult.size() > 0)
				{
					// one or more Keywords have failed. Get the first one to add in Json
					Set<ITestResult> testResultSet = testFailedResult.getAllResults();
					for(ITestResult testResult : testResultSet)
					{
						String testClassName = testResult.getTestClass().getName(); //Package and Class name of Failed Method
						String testMethodName = testResult.getMethod().getMethodName(); // Method name of Failed method
						Object paramObjArr[] = testResult.getParameters();
						String paramStr=""; // parse parameters that had been used in the method
						for(Object paramObj : paramObjArr)
						{
							paramStr += (String)paramObj;
							paramStr += " ";
						}
						//Get reporter message list.
						List<String> repoterMessageList = Reporter.getOutput(testResult);
						String reporterMessage="";
						for(String tmpMsg : repoterMessageList)				
						{
							reporterMessage += tmpMsg;
							reporterMessage += " ";
						}
						//First Errored Keyword will be written and exited
						if(Runner.strJSONResVal.trim().isEmpty())
						{							

							Runner.strJSONResVal=testClassName+"-"+testMethodName+"-"+paramStr+";"+reporterMessage;
							firsterrorfound=true;
							break;

						}

					}
				}

				if(firsterrorfound){
					break;
				}

				if(Runner.failedKeywordStatus==true)
				{
					IResultMap testPassedResult = testObj.getPassedTests();
					if (testPassedResult.size() > 0)
					{						
						// one or more Keywords have failed. Get the first one to add in Json
						Set<ITestResult> testResultSet = testPassedResult.getAllResults();
						for(ITestResult testResult : testResultSet)
						{
							//Get reporter message list.
							List<String> repoterMessageList = Reporter.getOutput(testResult);
							String reporterMessage="";

							for(String tmpMsg : repoterMessageList)				
							{
								reporterMessage += tmpMsg;
								reporterMessage += " ";
							}

							String testClassName="";
							String testMethodName="";
							String paramStr="";

							if (reporterMessage.trim().toLowerCase().contains("####fail"))
							{
								testClassName = testResult.getTestClass().getName(); //Package and Class name of Failed Method
								testMethodName = testResult.getMethod().getMethodName(); // Method name of Failed method
								Object paramObjArr[] = testResult.getParameters();
								paramStr=""; // parse parameters that had been used in the method
								for(Object paramObj : paramObjArr)
								{
									paramStr += (String)paramObj;
									paramStr += " ";
								}

								//First Errored Keyword will be written and exited
								if(Runner.strJSONResVal.trim().isEmpty()) {							
									Runner.strJSONResVal=testClassName+"-"+testMethodName+"-"+paramStr+";"+reporterMessage;
									firsterrorfound=true;
									break;
								}
							}
							if(firsterrorfound){
								break;
							}
						}
					}
				}



			}
			if(firsterrorfound){
				break;
			}
		}
	}

	/* Read template content. */
	private String readEmailabelReportTemplate() {
		StringBuffer retBuf = new StringBuffer();

		try {

			String strResrcFldrLocPath=Runner.strResourceFldLoc+"\\Framework\\";
			/*if (Runner.localRunFlag==true)
			{
				strResrcFldrLocPath=Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources";;

				//strResrcFldrLocPath=Runner.strResourceFldLoc;
			}
			else
			{
				strResrcFldrLocPath=Runner.strResourceFldLoc+"\\Framework\\";
				//tring emailableReportTemplateFile = Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\BaseReportFormat.html";			
			}*/

			String emailableReportTemplateFile = strResrcFldrLocPath+"\\BaseReportFormat.html";

			File file = new File(emailableReportTemplateFile);

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}
			br.close();
			return retBuf.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return retBuf.toString();
		}
		finally
		{
			//return retBuf.toString();

		}
	}

	/* Build custom report title. */
	private String getCustomReportTitle(String title)
	{		
		//retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
		StringBuffer retBuf = new StringBuffer();
		return retBuf.toString();	
	}

	/* Build test suite summary data. */
	private String getTestSuiteSummary(List<ISuite> suites)
	{
		StringBuffer retBuf = new StringBuffer();

		try
		{

			int totalTestPassed = 0;
			int totalTestFailed = 0;
			int totalTestSkipped = 0;
			@SuppressWarnings("unused")
			int totalTestCount=0;

			for(ISuite tempSuite: suites)
			{
				//retBuf.append("<tr><td colspan=11><center><b>" + tempSuite.getName() + "</b></center></td></tr>");			
				Map<String, ISuiteResult> testResults = tempSuite.getResults();

				int passCnt=0;
				int failCnt=0;
				int skipCnt=0;

				for (ISuiteResult result : testResults.values()) 
				{									
					ITestContext testObj = result.getTestContext();

					totalTestPassed = testObj.getPassedTests().getAllMethods().size();
					totalTestSkipped = testObj.getSkippedTests().getAllMethods().size();
					totalTestFailed = testObj.getFailedTests().getAllMethods().size();

					totalTestCount = totalTestPassed + totalTestSkipped + totalTestFailed;

					if (Runner.failedKeywordStatus==false)
					{
						if (totalTestPassed >0)
						{
							passCnt=passCnt+1;
						}

						if (totalTestFailed >0)
						{
							failCnt=failCnt+1;
						}

						if (totalTestSkipped >0)
						{
							skipCnt=skipCnt+1;
						}	
					}


					if (Runner.failedKeywordStatus==true)
					{

						Boolean chkFailedKeyworExist=false;

						String testName = testObj.getName();
						for(String s:Runner.failedKeywrdmap.keySet())
						{
							if (s.trim().equalsIgnoreCase(testName))
							{
								chkFailedKeyworExist=true;
								break;
							}
						}

						if (chkFailedKeyworExist==false)
						{							
							passCnt=passCnt+1;							
						}

						else
						{
							failCnt=failCnt+1;
						}

					}
				}

				int TotalKeywrdCnt= passCnt+failCnt+skipCnt;

				retBuf.append("<table id='header'  width=\"100%\" style=\"table-layout:fixed;word-break:break-all;\"><thead> <tr class='heading'> <th colspan='4' style='font-family:Copperplate Gothic; font-size:1.4em;'>Automation Execution Report - "+Runner.strTestScriptName +"</th>  </tr>");
				retBuf.append(" <tr class='subheading'> <th>Start Time</th> <th> End Time</th> <th>Total Time Taken</th> <th> Overall Execution Status</th> </tr> ");
				retBuf.append(" <tr class='subheading'> <th>"+Runner.strStartTime+"</th> <th>"+Runner.strEndTime+"</th> <th>"+Runner.strTotalTimeTaken+"</th> <th> "+Runner.strOverallExectnStatus+"</th> </tr> ");
				retBuf.append(" <tr class='subheading'> <th> Passed </th> <th>Failed</th> <th>Skipped</th> <th> Total Keywords</th> </tr> ");
				retBuf.append(" <tr class='subheading'> <th>"+Integer.toString(passCnt)+"</th> <th>"+Integer.toString(failCnt)+"</th> <th>"+Integer.toString(skipCnt)+"</th> <th> "+Integer.toString(TotalKeywrdCnt)+"</th> </tr> ");
				retBuf.append("</thead></table>");	
			}
			return retBuf.toString();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return retBuf.toString();
		}finally
		{
			//return retBuf.toString();
		}
	}

	/* Get date string format value. */
	private String getDateInStringFormat(Date date)
	{
		StringBuffer retBuf = new StringBuffer();
		if(date==null)
		{
			date = new Date();
		}
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		retBuf.append(df.format(date));
		return retBuf.toString();
	}

	/* Convert long type deltaTime to format hh:mm:ss:mi. */
	private String convertDeltaTimeToString(long deltaTime)
	{
		StringBuffer retBuf = new StringBuffer();

		//long milli = deltaTime;

		long seconds = deltaTime / 1000;

		if(seconds==0)
			seconds=1;	

		long minutes = seconds / 60;

		long hours = minutes / 60;

		//retBuf.append(hours + "h :" + minutes + "m :" + seconds + ":" + milli);
		retBuf.append(hours + "h :" + minutes + "m :" + seconds + "s");

		return retBuf.toString();
	}

	/* Get test method summary info. */
	private String getTestMehodSummary(List<ISuite> suites)
	{
		StringBuffer retBuf = new StringBuffer();

		try
		{
			for(ISuite tempSuite: suites)
			{
				//retBuf.append("<tr><td colspan=7><center><b>" + tempSuite.getName() + "</b></center></td></tr>");

				Map<String, ISuiteResult> testResults = tempSuite.getResults();

				for (ISuiteResult result : testResults.values()) {

					ITestContext testObj = result.getTestContext();

					String testName = testObj.getName();

					/* Get failed test method related data. */
					IResultMap testFailedResult = testObj.getFailedTests();
					if (testFailedResult.size() > 0)
					{
						//retBuf.append("<table>");
						retBuf.append("<table style=\"table-layout:fixed;word-break:break-all;\"><tbody><tr class='section'>");

						String failedTestMethodInfo = this.getTestMethodReport(testName, testFailedResult, false, false);
						retBuf.append(failedTestMethodInfo);
						retBuf.append("</table>");
					}

					/* Get skipped test method related data. */
					IResultMap testSkippedResult = testObj.getSkippedTests();
					if (testSkippedResult.size() > 0)
					{
						//retBuf.append("<table>");
						retBuf.append("<table style=\"table-layout:fixed;word-break:break-all;\"><tbody><tr class='section'>");

						String skippedTestMethodInfo = this.getTestMethodReport(testName, testSkippedResult, false, true);
						retBuf.append(skippedTestMethodInfo);
						retBuf.append("</table>");
					}

					/* Get passed test method related data. */
					IResultMap testPassedResult = testObj.getPassedTests();
					if (testPassedResult.size() > 0)
					{
						//retBuf.append("<table>");
						retBuf.append("<table style=\"table-layout:fixed;word-break:break-all;\"><tbody><tr class='section'>");

						if (Runner.failedKeywordStatus==false)
						{
							String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);				
							retBuf.append(passedTestMethodInfo);
							retBuf.append("</table>");
						}

						if (Runner.failedKeywordStatus==true)
						{
							Boolean chkFailedKeyworExist=false;

							for(String s:Runner.failedKeywrdmap.keySet())
							{
								if (s.trim().equalsIgnoreCase(testName))
								{
									chkFailedKeyworExist=true;
									break;
								}
							}

							if(chkFailedKeyworExist== false)
							{
								String passedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, true, false);				
								retBuf.append(passedTestMethodInfo);
								retBuf.append("</table>");
							}

							if(chkFailedKeyworExist== true)
							{
								String failedTestMethodInfo = this.getTestMethodReport(testName, testPassedResult, false, false);
								retBuf.append(failedTestMethodInfo);
								retBuf.append("</table>");
							}
						}


					}
				}
			}
			return retBuf.toString();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return retBuf.toString();
		}finally
		{
			//return retBuf.toString();
		}
	}

	/* Get failed, passed or skipped test methods report. */
	private String getTestMethodReport(String testName, IResultMap testResultMap, boolean passedReault, boolean skippedResult)
	{
		StringBuffer retStrBuf = new StringBuffer();

		String resultTitle = testName;

		String color = "green";

		if(skippedResult)
		{
			resultTitle += " - Skipped ";
			color = "yellow";
		}else
		{
			if(!passedReault)
			{
				resultTitle += " - Failed ";
				color = "red";
			}else
			{
				resultTitle += " - Passed ";
				color = "green";
			}
		}

		System.out.println(resultTitle);
		//retStrBuf.append("<tr bgcolor=" + color + "><td width=\"90%\" colspan=7><center><b>" + resultTitle + "</b></center></td></tr>");
		retStrBuf.append("<tr bgcolor=" + color + "><td width=\"100%\" colspan=6  onclick=\"toggleMenu('"+testName+"')\"><center><b>" + resultTitle + "</b></center></td></tr>");

		retStrBuf.append("<tbody id='"+testName+"' style='display:table-row-group'>");

		//if(!(resultTitle.contains("Skipped")))
		//{

		Set<ITestResult> testResultSet = testResultMap.getAllResults();

		String path="";
		String[] patharr=null;
		String actualimgpath="";
		String[] imgarr=null;
		if(resultTitle.contains("Failed"))
		{
			if(!(Runner.strTechnology.equalsIgnoreCase("API")||Runner.strTechnology.equalsIgnoreCase("Backend")))
			{				
				if(!(Runner.driverMap.get("Screenshot_Link").toString().equals("")))
				{
					path = Runner.driverMap.get("Compressed_Screenshot_Link").toString();
					patharr = path.split("####");
					actualimgpath = Runner.driverMap.get("Screenshot_Link").toString();
					//String actualimgpath1=actualimgpath.replaceAll("\\\\", "\\\\\\");
					imgarr = actualimgpath.split("####");
				}
			}
		}		


		for(ITestResult testResult : testResultSet)
		{
			String testClassName = "";
			@SuppressWarnings("unused")
			String testMethodName = "";
			String startDateStr = "";
			String executeTimeStr = "";
			@SuppressWarnings("unused")
			String paramStr = "";
			String reporterMessage = "";
			String exceptionMessage = "";

			//Get testClassName
			testClassName = testResult.getTestClass().getName();

			//Get testMethodName
			testMethodName = testResult.getMethod().getMethodName();

			//Get startDateStr
			long startTimeMillis = testResult.getStartMillis();
			startDateStr = this.getDateInStringFormat(new Date(startTimeMillis));

			//Get Execute time.
			long deltaMillis = testResult.getEndMillis() - testResult.getStartMillis();
			executeTimeStr = this.convertDeltaTimeToString(deltaMillis);

			//Get parameter list.
			Object paramObjArr[] = testResult.getParameters();
			for(Object paramObj : paramObjArr)
			{
				paramStr += (String)paramObj;
				paramStr += " ";
			}

			//Get reporter message list.
			List<String> repoterMessageList = Reporter.getOutput(testResult);
			for(String tmpMsg : repoterMessageList)				
			{
				reporterMessage += tmpMsg;
				reporterMessage += " ";
			}

			//Get exception message.
			String strSkipMsgArr1[]=null;
			String strSkipMsgArr2[]=null;
			String strSkipMsg="";
			Throwable exception = testResult.getThrowable();
			if(exception!=null)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				exception.printStackTrace(pw);

				exceptionMessage = sw.toString();
				if(resultTitle.contains("Skipped"))
				{
					strSkipMsgArr1=exceptionMessage.split("SkipException:");
					strSkipMsgArr2=strSkipMsgArr1[1].split("\r");
					strSkipMsg=strSkipMsgArr2[0].trim();
				}

			}

			retStrBuf.append("<tr>");

			String strReprtMsgArr[]=null;
			int reprtMsgCnt=0;

			if(!(resultTitle.contains("Skipped")))
			{
				strReprtMsgArr=reporterMessage.split("####");
				reprtMsgCnt= strReprtMsgArr.length/2;
			}

			retStrBuf.append("<td align=\"center\" width=\"12.5%\" rowspan=\""+reprtMsgCnt+1+"\">");
			//retStrBuf.append("<td width=\"100\" rowspan=\""+reprtMsgCnt+1+"\">");
			retStrBuf.append(testClassName);
			retStrBuf.append("</td>");

			retStrBuf.append("<td width=\"7.5%\" rowspan=\""+reprtMsgCnt+1+"\">");
			retStrBuf.append(startDateStr);
			retStrBuf.append("</td>");

			retStrBuf.append("<td align=\"center\" width=\"10%\" rowspan=\""+reprtMsgCnt+1+"\">");
			retStrBuf.append(executeTimeStr);
			retStrBuf.append("</td>");

			if(!(resultTitle.contains("Skipped")))
			{

				retStrBuf.append("<td width=\"40%\">");				
				retStrBuf.append(strReprtMsgArr[0]);				
				retStrBuf.append("</td>");	

				retStrBuf.append("<td align=\"center\" width=\"10%\">");					
				retStrBuf.append(strReprtMsgArr[1]);				
				retStrBuf.append("</td>");	

				retStrBuf.append("<td width=\"20%\">");	
				if(strReprtMsgArr[1].contains("FAIL"))
				{
					Runner.strFailedStepMsg=strReprtMsgArr[0];
					String strResTitleArr[]=resultTitle.split("-");
					//String strClassArr[]=testClassName.split(".");
					Runner.strFailedKeywrd= testClassName.trim() + " - "+ strResTitleArr[0].trim();

					if(!(Runner.strTechnology.equalsIgnoreCase("API") ||Runner.strTechnology.equalsIgnoreCase("Backend")))
					{			
						if(!(Runner.driverMap.get("Screenshot_Link").toString().equals("")))
						{
							retStrBuf.append(base64image(imgarr[counter],patharr[counter]));
						}
					}

				}

				retStrBuf.append("</td>");
			}
			else
			{
				retStrBuf.append("<td width=\"40%\">");				
				retStrBuf.append(strSkipMsg);				
				retStrBuf.append("</td>");	

				retStrBuf.append("<td align=\"center\" width=\"10%\">");					
				retStrBuf.append("SKIP");				
				retStrBuf.append("</td>");

				retStrBuf.append("<td width=\"20%\">");	
				retStrBuf.append("</td>");

			}

			/*		retStrBuf.append("<td width=\"20%\">");	
			if(strReprtMsgArr[1].contains("FAIL")){
				retStrBuf.append(base64image(imgarr[counter],patharr[counter]));
			}

			retStrBuf.append("</td>");
			 */
			retStrBuf.append("</tr>");

			//int p=1;

			if(!(resultTitle.contains("Skipped")))
			{

				for (int i=2; i <strReprtMsgArr.length-1;i++)
				{
					//System.out.println(p);
					retStrBuf.append("<tr>");
					retStrBuf.append("<td width=\"40%\">");				
					retStrBuf.append(strReprtMsgArr[i]);				
					retStrBuf.append("</td>");	
					i=i+1;
					retStrBuf.append("<td align=\"center\">");					
					retStrBuf.append(strReprtMsgArr[i]);				
					retStrBuf.append("</td  width=\"10%\">");					


					retStrBuf.append("<td width=\"20%\">");		
					if(strReprtMsgArr[i].contains("FAIL")){

						Runner.strFailedStepMsg=strReprtMsgArr[i-1];
						String strResTitleArr[]=resultTitle.split("-");
						//String strClassArr[]=testClassName.split(".");
						Runner.strFailedKeywrd= testClassName.trim() + " - "+ strResTitleArr[0].trim();

						if(!(Runner.strTechnology.equalsIgnoreCase("API")||Runner.strTechnology.equalsIgnoreCase("Backend"))){

							if(!(Runner.driverMap.get("Screenshot_Link").toString().equals("")))
							{
								retStrBuf.append(base64image(imgarr[counter],patharr[counter]));
							}
						}
					}
					retStrBuf.append("</td>");

					retStrBuf.append("</tr>"); 
				}}

			if((resultTitle.contains("Failed")))
				counter++;						
		}			

		retStrBuf.append("</tbody>");
		return retStrBuf.toString();
	}

	/* Convert a string array elements to a string. */
	@SuppressWarnings("unused")
	private String stringArrayToString(String strArr[])
	{
		StringBuffer retStrBuf = new StringBuffer();
		if(strArr!=null)
		{
			for(String str : strArr)
			{
				retStrBuf.append(str);
				retStrBuf.append(" ");
			}
		}
		return retStrBuf.toString();
	}


	public String base64image(String actualimage, String path){
		FileInputStream fileIn;
		@SuppressWarnings("unused")
		byte[] imgbytes,imgbytes1 = null;
		String imageString = "";
		//String imageString1=null;
		try {

			//actualimage.replaceAll("\\", "/");
			fileIn = new FileInputStream(path);

			FileInputStream fileIn1 = new FileInputStream(actualimage);			
			imgbytes = IOUtils.toByteArray(fileIn);
			String base64String = Base64.getEncoder().encodeToString(imgbytes);			
			imgbytes1 = IOUtils.toByteArray(fileIn1);
			//String base64String1 = Base64.getEncoder().encodeToString(imgbytes1);

			//System.out.println(base64String);
			String actualimage1=actualimage.replace("\\", "\\\\");
			imageString = "<a href=\""+actualimage1+"\"><img src=\"data:image/png;base64,"+base64String+"\" alt=\"Base64 encoded image\"/></a>";
			//imageString = "<a href=\"data:image/png;base64,"+base64String1+"\"><img src=\"data:image/png;base64,"+base64String+"\" alt=\"Base64 encoded image\"/></a>";

		} catch ( IOException e) {
			imageString = "";
			System.out.println(e.getMessage()); 
		}
		return imageString;

	}

	//****************Test Suite Summary Report functions******************

	/* Read template content. */
	/*		private String readEmailabelTSReportTemplate()
		{
			StringBuffer retBuf = new StringBuffer();

			try {

				String strResrcFldrLocPath=Runner.resdir+"\\Framework\\TSE_SummaryBaseReportFormat.html";
				if (Runner.localRunFlag==true)
				{
					strResrcFldrLocPath=Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources";
				}
				else
				{
					strResrcFldrLocPath=Runner.strResourceFldLoc+"\\Framework\\";

				}

				String emailableReportTemplateFile = strResrcFldrLocPath+"\\TSE_SummaryBaseReportFormat.html";

				File file = new File(emailableReportTemplateFile);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				String line = br.readLine();
				while(line!=null)
				{
					retBuf.append(line);
					line = br.readLine();
				}

				br.close();
				retBuf.toString();
			}
			 catch (Exception ex) {
				ex.printStackTrace();
				retBuf.toString();
				//br.close();
			}
			finally
			{
				//return retBuf.toString();
			}
			return retBuf.toString();
		}

		 Build custom report title. 
		@SuppressWarnings("unused")
		private String getCustomReportTitle1(String title)
		{		
			StringBuffer retBuf = new StringBuffer();
			return retBuf.toString();	
		}*/

	public static String readFile (String fileName){
		String output;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			output = sb.toString();
			br.close();
			return output;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null; 
	}

	public static ArrayList<String> readJsonFile(String jsonFileLocation){
		ArrayList <String> jsonOutputs=null;
		try{

			String jsonOutput;
			jsonOutputs = new ArrayList<String>();
			//jsonOutput = readFile("C:\\Data\\RESULTS" + "\\" + jsonFileLocation );
			jsonOutput = readFile(jsonFileLocation );		
			jsonOutputs.add(jsonOutput);

		}    
		catch (Exception e){
			e.printStackTrace();
		}
		return jsonOutputs; 
	}


	/*public String getSuiteSumamry()
		{
			StringBuffer retBuf = new StringBuffer();

			try {
				JsonObject jsummdet=new JsonObject();
				List<String> jsonread=ResultsReport.readJsonFile(Runner.strTSEJsonFile);
				JsonParser jsonParser = new JsonParser();
				for (String s:jsonread){
					JsonObject jo = (JsonObject) jsonParser.parse(s);
					jsummdet=(JsonObject) jo.get("Summary");
				}
				String passedCnt=jsummdet.get("Passed").toString();
                String failedCnt=jsummdet.get("Failed").toString();
                String TotalCnt=jsummdet.get("Total Scripts").toString();
                String igncnt=jsummdet.get("Ignored").toString();
                String strStartTime=jsummdet.get("Start Time").toString().substring(1, jsummdet.get("Start Time").toString().length()-1);
                String strEndTime=jsummdet.get("End Time").toString().substring(1, jsummdet.get("End Time").toString().length()-1);
                String strTotalTimeTaken=jsummdet.get("Total Time Taken").toString().substring(1, jsummdet.get("Total Time Taken").toString().length()-1);
					//TotalCnt=passedCnt+failedCnt;

					retBuf.append("<table id='header'  width=\"100%\" style=\"table-layout:fixed;word-break:break-all;\"><thead> <tr class='heading'> <th colspan='7' style='font-family:Copperplate Gothic; font-size:1.4em;'>Automation Suite Execution Summary Report</th>  </tr>");
					retBuf.append(" <tr class='subheading'> <th>Start Time</th> <th> End Time</th> <th>Total Time Taken</th><th>Passed</th> <th> Failed</th> <th> Ignored</th> <th> Total Scripts</th></tr> ");
					retBuf.append(" <tr class='subheading'> <th>"+strStartTime+"</th> <th>"+strEndTime+"</th> <th>"+strTotalTimeTaken+"</th> <th>"+passedCnt+"</th> <th> "+failedCnt+"</th><th> "+igncnt+"</th> <th> "+TotalCnt+"</th></tr> ");
					retBuf.append("</thead></table>");	
				}

			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return retBuf.toString();

		}

		 Get test method summary info. 
		//@SuppressWarnings("unused")
		private String getTestMehodSummary()
		{
			StringBuffer retBuf = new StringBuffer();

			try
			{
				//HashMap <String, String> testCases = new HashMap<String, String>();
				//JsonObject jsonObjectRequestFinal= new JsonObject();

				ArrayList<String> jsonResults = readJsonFile(Runner.strTSEJsonFile);

				JsonParser jsonParser = new JsonParser();
				Set<Map.Entry<String, JsonElement>> entries=null;
				//HashMap <String, String> testresultMap = new HashMap<String, String>();

				//int passedCnt=0;
				//int failedCnt=0;
				//boolean passFlag=false;
				//boolean failFlag=false;

				for (String jsonOutput : jsonResults)
				{
					JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonOutput);
					JsonArray jsonObjectArray = jsonObject.getAsJsonArray("TestCaseList");

					String testCaseName="";
					String []testCaseNameArr=null;
					String testCaseName1="";
					String date="";
					String []dateArr=null;
					String date1="";
					String verdict="";
					String []verdictArr=null;
					String verdict1="";
					String testcaseNumber="";
					String resultAttachment="";
					String resultAttachment1="";
					String []resAttArr=null;
					String resultAttachment2="";
					String comments="";

					String strItrNum="";
					String []strItrNumArr=null;
					String strItrNum1="";



					int cnt=0;
					String strCnt= "";
					Boolean ignoredscript=false;
					for (JsonElement value : jsonObjectArray)
					{
						entries =value.getAsJsonObject().entrySet();
						Iterator <Map.Entry<String, JsonElement>> iterator = entries.iterator();
						while(iterator.hasNext()){

							testcaseNumber =iterator.next().getKey();
							verdict = value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("ExeStatus").toString();
						    verdictArr=verdict.split("\"");
							verdict1=verdictArr[1].toString().trim();

							testCaseName = value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("TestCase").toString();
							testCaseNameArr=testCaseName.split("\"");
							testCaseName1=testCaseNameArr[1].toString().trim();
							date = value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("TSExectnTime").toString();
							dateArr=date.split("\"");
							date1=dateArr[1].toString().trim();
							resultAttachment = value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("ResAttachment").toString();
							if(!(resultAttachment.contains("\"\""))){
								resultAttachment1=resultAttachment.replace("\\\\\\", "\\");
								resAttArr=resultAttachment1.split("\"");
								resultAttachment2=resAttArr[1].toString().trim();
								ignoredscript=false;
							}
							else{
								comments= value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("Comments").toString();
								resultAttachment2=comments;
								ignoredscript=true;
							}

							strItrNum = value.getAsJsonObject().get(testcaseNumber).getAsJsonObject().get("ItrNo").toString();
							strItrNumArr=strItrNum.split("\"");
							strItrNum1=strItrNumArr[1].toString().trim();


						}

						cnt=cnt+1;
						strCnt=Integer.toString(cnt);
						retBuf.append("<tr>");	

						retBuf.append("<td align=\"center\" width=\"5%\">");
						retBuf.append(strCnt);
						retBuf.append("</td>");

						retBuf.append("<td align=\"center\" width=\"15%\">");
						retBuf.append(testCaseName1);
						retBuf.append("</td>");

						retBuf.append("<td align=\"center\" width=\"5%\">");
						retBuf.append(strItrNum1);
						retBuf.append("</td>");

						retBuf.append("<td align=\"center\" width=\"15%\">");
						retBuf.append(date1);
						retBuf.append("</td>");

						retBuf.append("<td align=\"center\" width=\"10%\">");
						retBuf.append(verdict1);
						retBuf.append("</td>");
						if(!ignoredscript){
							retBuf.append("<td align=\"left\" width=\"50%\">");
							retBuf.append("<a href=");
							retBuf.append("\""+resultAttachment2+"\">");
							retBuf.append(resultAttachment2);
							retBuf.append("</a>");
							retBuf.append("</td>");
						}
						else{
							retBuf.append("<td align=\"left\" width=\"10%\">");
							retBuf.append(resultAttachment2);
							retBuf.append("</td>");
						}

						retBuf.append("</tr>");
					}
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
				return retBuf.toString();
			}finally
			{
				//return retBuf.toString();
			}
			return retBuf.toString();
		}

public void generateTestSuiteExectnReport() 
{
	// TODO Auto-generated method stub
	try{	

		// Get content data in TestNG report template file.
		String customReportTemplateStr = this.readEmailabelTSReportTemplate();

		// Create custom report title.
		String customReportTitle = this.getCustomReportTitle("Automation Test Script Execution Report");

		// Create test suite summary data.
		String customSuiteSummary = this.getSuiteSumamry();

		// Create test methods summary data.
		String customTestMethodSummary = this.getTestMehodSummary();

		// Replace report title place holder with custom title.
		customReportTemplateStr = customReportTemplateStr.replaceAll("\\$TestNG_Custom_Report_Title\\$", customReportTitle);

		// Replace test suite place holder with custom test suite summary.
		customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Summary\\$", customSuiteSummary);

		customReportTemplateStr = customReportTemplateStr.replaceAll("\\$Test_Case_Detail\\$", customTestMethodSummary);

		// Write replaced test report content to custom-emailable-report.html.
		//SimpleDateFormat format = new SimpleDateFormat("MMddyyyyhhmmss");
		//String DateToStr = format.format(new Date());
		String TargetFolder=Runner.workingdir +"\\Result";
		if(!new File(TargetFolder).exists())
			new File(TargetFolder).mkdirs();
		File targetFile = new File(TargetFolder+"\\TSE_SummaryReport_"+Runner.strReportTimestamp+".html");
			FileWriter fw = new FileWriter(targetFile);
			fw.write(customReportTemplateStr);
			fw.flush();
			fw.close();

		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

}*/



	public static ArrayList<String>  readBDDJsonFile() {

		ArrayList<String> strResultArray =null;
		try {
			//Reading & Converting the JSON File to String
			strResultArray = new ArrayList<>();

			//String bddReportTemplateFile = Runner.strResultFldLoc+"\\Cucumber.json";
			BufferedReader br = new BufferedReader(new FileReader(Runner.cucjsonfilename));

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String jsonString = sb.toString();
			br.close();
			JSONArray jArray = new JSONArray(jsonString);
			JSONObject rootObject = new JSONObject(jArray.get(0).toString());
			JSONArray rows = rootObject.getJSONArray("elements");

			int p=0;
			int m=0;
			int scnrioCnt=0;
			for(int i=0; i < rows.length(); i++) {
				m=0;
				JSONObject row = rows.getJSONObject(i);
				JSONArray elements = row.getJSONArray("steps");

				//Getting the Scenario/TC Name
				String strScenario = row.getString("name").trim();


				// 08/20/2018--- Scenario type
				scnrioCnt=scnrioCnt+1;
				//String strScenarioType = row.getString("keyword").trim();

				boolean scenarioStatusFlag = true;
				String strStep1="";


				for(int j=0; j < elements.length(); j++) {

					// 08/20/2018 - Modified the strTempResult variable to add Scenario count
					String strTempResult = scnrioCnt+";"+strScenario;
					JSONObject element =  elements.getJSONObject(j);

					//Getting the Keyword & Step Name
					String keyword = element.getString("keyword").trim();
					String step = element.getString("name").trim();
					String stepsWithValue = step;
					strTempResult = strTempResult+";"+"@"+keyword+" "+step;			
					JSONObject match = element.getJSONObject("match");

					//Checking if the Step running has any Arguments or not
					//08/20/2018---	Modified the code to handle the arguments for Scenario Outline type
					/*
			if (strScenarioType.equalsIgnoreCase("Scenario Outline"))			
			{
				strTempResult = strTempResult+";"+"ScenarioOutline";
			}			
			else
			{
					 */			if(match.toString().contains("arguments")) {
						 //String strTempArgs = "";
						 JSONArray arguments = match.getJSONArray("arguments");
						 for (int k = 0; k < arguments.length(); k++) {
							 JSONObject argument = (JSONObject) arguments.get(k);
							 String strParams = argument.getString("val").trim();
							 if(strParams.contains("\"")){
								 String [] splitstr=strParams.split("\"");
								 strParams=splitstr[splitstr.length-1];
							 }
							 String strValue = CommonFunctions.getBDDArgs(strScenario, strParams, Runner.strItrNum);
							 stepsWithValue = stepsWithValue.replace(strParams, strValue);
							 strTempResult = strTempResult.replace(strParams, strValue);
						 }}	
					 /*		
						if(k==arguments.length()-1) {
							strTempArgs = strTempArgs+strParams+" - "+strValue;
						} else {
							strTempArgs = strTempArgs+strParams+" - "+strValue+", ";
						}
					  */	
					 /*				}


					strTempResult = strTempResult+";"+strTempArgs;
				} 

				else 				
				{
					strTempResult = strTempResult+";"+"NoValue";
				}
			}
					  */

					 JSONObject result = element.getJSONObject("result");

					 //For Running Status Pass/Fail, Getting the Duration
					 if(!((result.getString("status").equalsIgnoreCase("skipped")) || (result.getString("status").equalsIgnoreCase("undefined")))){
						 Long time = result.getLong("duration");
						 long nanoSec = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
						 Date date = new Date(nanoSec);
						 DateFormat formatter = new SimpleDateFormat("ss.SSS");
						 String dateFormatted = formatter.format(date);
						 strTempResult = strTempResult+";"+dateFormatted;
					 } else {
						 strTempResult = strTempResult+";"+"NoValue";
					 }

					 //Getting the TC Running Status
					 String status = result.getString("status");
					 if(status.equalsIgnoreCase("passed")) {
						 status = "PASS";
					 } else if(status.equalsIgnoreCase("failed")) {
						 status = "FAIL";
					 } else if(status.equalsIgnoreCase("skipped")) {
						 status = "SKIP";
					 } else if(status.equalsIgnoreCase("undefined")) {
						 status = "FAIL";
						 scenarioStatusFlag = false;
					 }
					 //					 strTempResult = strTempResult+";"+status;

					 //Getting TC Running Status in case of (OnFailure=Continue)
					 String strErrMsg = "";
					 String key = strScenario+"@"+(j+1)+"@"+keyword+" "+step;
					 if(Runner.strBDDFailMsgMap.containsKey(key)){
						 status = "FAIL";
						 scenarioStatusFlag = false;
						 strErrMsg = Runner.strBDDFailMsgMap.get(strScenario+"@"+(j+1)+"@"+keyword+" "+step);
						 String strScreenshot = Runner.strBDDScreenshotMap.get(strScenario+"@"+(j+1)+"@"+keyword+" "+step);
						 strTempResult = strTempResult+";"+"FAIL"+";"+strErrMsg+";"+strScreenshot;
					 }

					 /*//If Status is Fail, Getting the ErrorMsg from BDDFailMsgMap by passing the TC/Scenario Name
					 if(result.getString("status").equalsIgnoreCase("failed")){
						 scenarioStatusFlag = false;
						 String strErrMsg = Runner.strBDDFailMsgMap.get(strScenario);
						 String strScreenshot = Runner.strBDDScreenshotMap.get(strScenario);
						 strTempResult = strTempResult+";"+strErrMsg+";"+strScreenshot;
					 }*/ 
					 else if(result.getString("status").equalsIgnoreCase("undefined")) {
						 strTempResult = strTempResult+";"+status+";Method related to this step is not available in the Java files available under StepDefinition Package;NoValue";
					 }

					 else {
						 strTempResult = strTempResult+";"+status+";NoValue;NoValue";
					 }
					 strTempResult = strTempResult+";Passed";

					 if(j==0)
					 {
						 m=p;
						 strStep1=strTempResult;
					 }
					 strResultArray.add(strTempResult);

					 //Added by N117876
					 if(!status.equalsIgnoreCase("SKIP")){
						 if(!strErrMsg.equalsIgnoreCase("")){
							 Runner.strBDDResJson.get(strScenario).add(stepsWithValue+"@@@@"+strErrMsg+"####"+status+"####");
						 } else {
							 Runner.strBDDResJson.get(strScenario).add(stepsWithValue+"####"+status+"####");
						 }
					 }
					 p=p+1;
				}

				String strUpdatedStep1="";
				String strStep1Arr[]=strStep1.split(";");

				strUpdatedStep1=strStep1Arr[0].trim();


				for(int i1=1;i1<strStep1Arr.length;i1++)                                                 
				{
					if(i1==strStep1Arr.length-1)
					{
						if(scenarioStatusFlag==false)
						{
							strUpdatedStep1=strUpdatedStep1+";Failed";

						}
						else
						{
							strUpdatedStep1=strUpdatedStep1+";Passed";
						}
					}

					else
					{
						strUpdatedStep1=strUpdatedStep1+";"+strStep1Arr[i1].trim();
					}                                                                                                                                                              
				}

				strResultArray.set(m, strUpdatedStep1);             

			}
			//            System.out.println(strResultArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResultArray;
	}

	public void generateBDDReport() 
	{

		try
		{

			ArrayList<String> scenarioExectnSummary=readBDDJsonFile();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy HH.mm.ss");
			Date timeAfrExectn = new Date();
			Runner.strEndTime = sdf1.format(timeAfrExectn);
			long timediff = timeAfrExectn.getTime() - Runner.timeBfrExectn.getTime(); 

			long diffSeconds = timediff / 1000 % 60;
			long diffMinutes = timediff / (60 * 1000) % 60;
			long diffHours = timediff / (60 * 60 * 1000) % 24;

			Runner.strTotalTimeTaken=Long.toString(diffHours)+"h :"+Long.toString(diffMinutes)+"m :"+Long.toString(diffSeconds)+"s"; 


			// Get content data in TestNG report template file.
			String customReportTemplateStr = readBDDReportTemplate();

			// Create custom report title.
			String customReportTitle = getBDDCustomReportTitle("Automation Test Script Execution Report");

			// Create test suite summary data.
			String customSuiteSummary = addBDDSuiteSummaryInReport(scenarioExectnSummary);

			String customTestMethodSummary=addBDDScenarioExectnSummaryInReport(scenarioExectnSummary) ;

			// Replace report title place holder with custom title.
			customReportTemplateStr = customReportTemplateStr.replace("TestNG_Custom_Report_Title", customReportTitle);

			// Replace test suite place holder with custom test suite summary.
			customReportTemplateStr = customReportTemplateStr.replace("Test_Case_Summary", customSuiteSummary);

			// Replace test methods place holder with custom test method summary.
			customReportTemplateStr = customReportTemplateStr.replace("Test_Case_Detail", customTestMethodSummary);

			// Write replaced test report content to custom-emailable-report.html.

			// Write replaced test report content to custom-emailable-report.html.
			File targetFile1 = new File(Runner.strResultFldLocfortest + "/ResultsReport.html");
			FileWriter fw1 = new FileWriter(targetFile1);
			fw1.write(customReportTemplateStr);
			fw1.flush();
			fw1.close();

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}


	/* Build test suite summary data. */
	@SuppressWarnings("finally")
	private static String addBDDSuiteSummaryInReport(ArrayList<String> scenarioExectnSummary)
	{
		StringBuffer retBuf = new StringBuffer();

		try
		{

			int bddPassScnCnt=0;
			int bddFailScnCnt=0;
			//int bddTotalCnt=0;
			// boolean overallStatusFlag=false;
			String featuresummarystatus="";							   
			String stepExectnSummary="";
			stepExectnSummary=scenarioExectnSummary.get(0).toString().trim();
			String stepExectnSummaryArr[]=stepExectnSummary.split(";");
			String strScenario1=stepExectnSummaryArr[0];
			String strScenarioExctnStatus=stepExectnSummaryArr[stepExectnSummaryArr.length-1];

			if(strScenarioExctnStatus.toLowerCase().contains("pass"))
			{
				bddPassScnCnt=bddPassScnCnt+1;
			}
			else
			{
				bddFailScnCnt=bddFailScnCnt+1;
				//overallStatusFlag=false;
			}

			String strScenario2="";

			//boolean scenariochkStatusflag=false;
			for( int i=0;i<scenarioExectnSummary.size();i++)
			{
				stepExectnSummary=scenarioExectnSummary.get(i).toString().trim();
				stepExectnSummaryArr=stepExectnSummary.split(";");
				strScenario2=stepExectnSummaryArr[0];
				strScenarioExctnStatus=stepExectnSummaryArr[stepExectnSummaryArr.length-1];

				System.out.println("Scenario:"+stepExectnSummaryArr[0]+" -- "+"Step:"+(i+1)+" -- "+stepExectnSummaryArr[4]);

				if(!(strScenario1.equalsIgnoreCase(strScenario2)))                                
				{
					if(strScenarioExctnStatus.toLowerCase().contains("pass"))
					{
						bddPassScnCnt=bddPassScnCnt+1;
						strScenario1=strScenario2;
					}
					else
					{
						bddFailScnCnt=bddFailScnCnt+1;
						strScenario1=strScenario2;
					}
				}
			}


			if(bddFailScnCnt >0)
			{
				featuresummarystatus="FAIL";
			}
			else
			{
				featuresummarystatus="PASS";
			}




			String strBDdScnPassedCnt=Integer.toString(bddPassScnCnt);
			String BDdScnFailedCnt=Integer.toString(bddFailScnCnt);
			String strBddTotalscenarios=Integer.toString(bddPassScnCnt+bddFailScnCnt);


			retBuf.append("<table id='header'  width=\"100%\" style=\"table-layout:fixed;word-break:break-all;\"><thead> <tr class='heading'> <th colspan='4' style='font-family:Lato Bold; font-size:22px;'>Automation Execution Report - "+Runner.strTestScriptName+"</th>  </tr>");
			retBuf.append(" <tr class='subheading'> <th>Start Time</th> <th> End Time</th> <th>Total Time Taken</th> <th> Overall Execution Status</th> </tr> ");
			retBuf.append(" <tr class='subheading'> <th>"+Runner.strStartTime+"</th> <th>"+Runner.strEndTime+"</th> <th>"+Runner.strTotalTimeTaken+"</th> <th> "+featuresummarystatus+"</th> </tr> ");
			retBuf.append(" <tr class='subheading'> <th> Feature </th> <th>Passed</th> <th>Failed</th> <th> Total Scenarios</th> </tr> ");
			retBuf.append(" <tr class='subheading'> <th>"+Runner.strBDDFeatureFile+"</th> <th>"+strBDdScnPassedCnt+"</th> <th>"+BDdScnFailedCnt+"</th> <th> "+strBddTotalscenarios+"</th> </tr> ");
			retBuf.append("</thead></table>"); 

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally{
			return retBuf.toString();
		}
	}


	/* Read template content. */
	@SuppressWarnings("finally")
	private  static String  readBDDReportTemplate()
	{
		StringBuffer retBuf = new StringBuffer();

		try {

			String strResrcFldrLocPath=Runner.resdir+"\\Framework";


			String bddReportTemplateFile = strResrcFldrLocPath+"\\BaseBDDReportFormat.html";


			File file = new File(bddReportTemplateFile);

			FileReader fr = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}finally
		{
			return retBuf.toString();
		}
	}


	@SuppressWarnings("finally")
	private  String addBDDStepExectnSummaryInReport( String strStepExectnSummaryArr[])
	{
		StringBuffer retStrBuf = new StringBuffer();

		try
		{
			retStrBuf.append("<tr>");

			retStrBuf.append("<td align=\"left\" width=\"45%\">");
			//retStrBuf.append("<td width=\"100\" rowspan=\""+reprtMsgCnt+1+"\">");
			retStrBuf.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+strStepExectnSummaryArr[2]);
			retStrBuf.append("</td>");
			/*
				if ((strStepExectnSummaryArr[3].trim().equalsIgnoreCase("ScenarioOutline")))
				{
					retStrBuf.append("<td align=\"center\" width=\"15%\">");
					retStrBuf.append("");
					retStrBuf.append("</td>");
				}

				else if(!(strStepExectnSummaryArr[3].trim().equalsIgnoreCase("NoValue")))
				{
					retStrBuf.append("<td align=\"center\" width=\"15%\">");
					retStrBuf.append(strStepExectnSummaryArr[3]);
					retStrBuf.append("</td>");
				}
				else
				{
					retStrBuf.append("<td align=\"center\" width=\"15%\">");
					retStrBuf.append("NA");
					retStrBuf.append("</td>");
				}
			 */

			String color;
			if(strStepExectnSummaryArr[4].equalsIgnoreCase("PASS"))
				color="lightgreen";
			else
				color="red";
			retStrBuf.append("<td align=\"center\" bgcolor="+color+" width=\"7%\">");
			retStrBuf.append(strStepExectnSummaryArr[4]);
			retStrBuf.append("</td>");


			if(!(strStepExectnSummaryArr[3].trim().equalsIgnoreCase("NoValue")))
			{
				retStrBuf.append("<td align=\"center\" width=\"8%\">");
				retStrBuf.append(strStepExectnSummaryArr[3]);
				retStrBuf.append("</td>");
			}
			else
			{
				retStrBuf.append("<td align=\"center\" width=\"8%\">");
				retStrBuf.append("</td>");
			}
			if(!(strStepExectnSummaryArr[5].trim().equalsIgnoreCase("NoValue")))

			{
				retStrBuf.append("<td align=\"center\" width=\"20%\">");
				retStrBuf.append(strStepExectnSummaryArr[5]);
				retStrBuf.append("</td>");
			}
			else
			{
				retStrBuf.append("<td align=\"center\" width=\"20%\">");
				retStrBuf.append("</td>");
			}

			if(!(strStepExectnSummaryArr[6].trim().equalsIgnoreCase("NoValue")))

			{
				String path="";
				String[] patharr=null;
				String actualimgpath="";
				String[] imgarr=null;

				if(!(Runner.strTechnology.equalsIgnoreCase("API")||Runner.strTechnology.equalsIgnoreCase("Backend")))
				{					
					if(!(Runner.driverMap.get("Screenshot_Link").toString().equals("")))
					{
						path = Runner.driverMap.get("Compressed_Screenshot_Link").toString();
						patharr = path.split("####");
						actualimgpath = Runner.driverMap.get("Screenshot_Link").toString();
						//String actualimgpath1=actualimgpath.replaceAll("\\\\", "\\\\\\");
						imgarr = actualimgpath.split("####");
					}
				}


				retStrBuf.append("<td align=\"center\" width=\"20%\">");
				// retStrBuf.append(strStepExectnSummaryArr[6]);
				if(!(Runner.driverMap.get("Screenshot_Link").toString().equals("")))
				{
				retStrBuf.append(base64image(imgarr[counter],patharr[counter]));
				}
				retStrBuf.append("</td>");
			}
			else
			{
				retStrBuf.append("<td align=\"center\" width=\"20%\">");
				retStrBuf.append("</td>");
			}

			retStrBuf.append("</tr>");

		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			return retStrBuf.toString();
		}
	}

	/* Get test method summary info. */
	@SuppressWarnings("finally")
	private String addBDDScenarioExectnSummaryInReport(ArrayList<String> scenarioExectnSummary)
	{
		StringBuffer retBuf = new StringBuffer();             
		try
		{
			String strStepExeInfo="";
			//String strColor="";
			String strScenarioExctnStatus="";
			String stepExectnSummary=scenarioExectnSummary.get(0).toString().trim();
			//String stepExectnSummary2="";
			String strScenario1="";
			String strScenario2="";

			// 08/20/2018 - Code Added
			String strScnCnt1="";
			String strScnCnt2="";

			retBuf.append("<table width=\"100%\" style=\"table-layout:fixed;word-break:break-all;\"><tbody>"); 
			//retBuf.append("<col width=\"30%\"/> <col width=\"15%\"/> <col width=\"8%\"/> <col width=\"7%\"/> <col width=\"20%\"/> <col width=\"20%\"/>");     
			retBuf.append("<col width=\"45%\"/> <col width=\"7%\"/> <col width=\"8%\"/> <col width=\"20%\"/> <col width=\"20%\"/>");     

			String stepExectnSummaryArr[]=stepExectnSummary.split(";");

			// 08/20/2018 - Code Modified
			strScnCnt1=stepExectnSummaryArr[0];
			strScenario1=stepExectnSummaryArr[1];

			strScenarioExctnStatus=stepExectnSummaryArr[stepExectnSummaryArr.length-1];

			String resultTitle = "";

			String color = "green";

			if(strScenarioExctnStatus.toLowerCase().contains("pass"))
			{
				resultTitle += "@Scenario-> "+strScenario1;
				color = "green";
				String Status="PASS";
				retBuf.append("<tr bgcolor=white><td width=\"100%\" colspan=1 Style=\"color:blue;font-size:1.1em\" onclick=\"toggleMenu('"+strScenario1+"')\"><b>" + resultTitle + "</b></td><td bgcolor=white Style=\"color:"+color+";font-size:1.1em\"  align=Left colspan=4>"+Status+"</td></tr>");
				retBuf.append("<tbody id='"+strScenario1+"' style='display:table-row-group'>");

			}else
			{
				resultTitle += "@Scenario-> "+strScenario1;
				color = "red";
				String Status="FAIL";
				retBuf.append("<tr bgcolor=white><td width=\"100%\" colspan=1 Style=\"color:blue;font-size:1.1em\" onclick=\"toggleMenu('"+strScenario1+"')\"><b>" + resultTitle + "</b></td><td bgcolor=white Style=\"color:"+color+";font-size:1.1em\"  align=Left colspan=4>"+Status+"</td></tr>");
				retBuf.append("<tbody id='"+strScenario1+"' style='display:table-row-group'>");
			}

			strStepExeInfo = addBDDStepExectnSummaryInReport(stepExectnSummaryArr);
			retBuf.append(strStepExeInfo);

			for( int i=1;i<scenarioExectnSummary.size();i++)
			{
				stepExectnSummary=scenarioExectnSummary.get(i).toString().trim();
				stepExectnSummaryArr=stepExectnSummary.split(";");

				// 08/20/2018 - Code Modified	
				strScenario2=stepExectnSummaryArr[1];
				strScnCnt2=stepExectnSummaryArr[0];;

				//if(strScenario1.equalsIgnoreCase(strScenario2))
				if(strScnCnt1.equalsIgnoreCase(strScnCnt2))
				{
					strStepExeInfo = addBDDStepExectnSummaryInReport(stepExectnSummaryArr);
					retBuf.append(strStepExeInfo);
					//strScenario1=strScenario2;
				}
				else
				{
					retBuf.append("</tbody>");
					retBuf.append("</table>");
					retBuf.append("<table width=\"100%\" style=\"table-layout:fixed;word-break:break-all;\"><tbody>");     
					// retBuf.append("<col width=\"30%\"/> <col width=\"15%\"/> <col width=\"8%\"/> <col width=\"7%\"/> <col width=\"20%\"/> <col width=\"20%\"/>");               
					retBuf.append("<col width=\"45%\"/> <col width=\"7%\"/> <col width=\"8%\"/> <col width=\"20%\"/> <col width=\"20%\"/>");               

					strScenarioExctnStatus=stepExectnSummaryArr[stepExectnSummaryArr.length-1];

					if(strScenarioExctnStatus.toLowerCase().contains("pass"))
					{
						resultTitle="";
						resultTitle += "@Scenario-> " +strScenario2;
						color = "green";
						String Status="PASS";
						retBuf.append("<tr bgcolor=white><td width=\"100%\" colspan=3 Style=\"color:blue;font-size:1.1em\" onclick=\"toggleMenu('"+strScenario2+"')\"><b>" + resultTitle + "</b></td><td bgcolor=white Style=\"color:"+color+";font-size:1.1em\"  align=Left colspan=3>"+Status+"</td></tr>");
						retBuf.append("<tbody id='"+strScenario1+"' style='display:table-row-group'>");
					}
					else
					{
						resultTitle="";
						resultTitle += "@Scenario-> "+strScenario2;
						color = "red";
						String Status="FAIL";
						retBuf.append("<tr bgcolor=white><td width=\"100%\" colspan=3 Style=\"color:blue;font-size:1.1em\" onclick=\"toggleMenu('"+strScenario2+"')\"><b>" + resultTitle + "</b></td><td bgcolor=white Style=\"color:"+color+";font-size:1.1em\"  align=Left colspan=3>"+Status+"</td></tr>");
						retBuf.append("<tbody id='"+strScenario1+"' style='display:table-row-group'>");

					}
					strStepExeInfo = addBDDStepExectnSummaryInReport(stepExectnSummaryArr);
					retBuf.append(strStepExeInfo);
					//strScenario1=strScenario2;
					strScnCnt1=strScnCnt2;
				}

			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			return retBuf.toString();
		}
	}

	/* Build custom report title. */
	private static String getBDDCustomReportTitle(String title)
	{                              
		//retBuf.append(title + " " + this.getDateInStringFormat(new Date()));
		StringBuffer retBuf = new StringBuffer();
		return retBuf.toString();               
	}
	//Create New Test Suite Summary
	public void generateTestSuiteExectnReport() {
		// TODO Auto-generated method stub
		String consjson=Runner.strTSEJsonFile;

		String reportString = "";
		List<JSONObject> tcdetails=readTCdetailsfromJSON(consjson);
		JSONObject sumdetails=readSumdetailsfromJSON(consjson);
		String exectempreppath=Runner.resdir+"\\Framework\\Test_exec_summary_Template.html";
		reportString=readCTrepTemplate(exectempreppath);
		reportString=buildrepSuitedetails(reportString, sumdetails);	
		reportString=buildreptestdetails(reportString, tcdetails);
		String TargetFolder=Runner.workingdir +"\\Result";
		if(!new File(TargetFolder).exists())
			new File(TargetFolder).mkdirs();
		File targetFile = new File(TargetFolder+"\\TSE_SummaryReport_"+Runner.strReportTimestamp+".html");
		//File targetFile = new File(dirPath +"\\"+repfilename);
		FileWriter fw;
		try {
			fw = new FileWriter(targetFile);
			fw.write(reportString);
			fw.flush();
			fw.close();
			System.out.println("Consolidated Result Output HTML created at -"+TargetFolder+"\\TSE_SummaryReport_"+Runner.strReportTimestamp+".html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//lg.writeToFile(Level.INFO,"Consolidated Result Output HTML created at -"+dirPath+"\\"+repfilename);

	}

	private String buildreptestdetails(String reportString, List<JSONObject> tcdetails) {
		// TODO Auto-generated method stub
		StringBuffer resbuf=new StringBuffer();
		String testelem1="<tr><td class=\"column1\">";
		String testelem2="<td class=\"column2\">";
		String testelem3="<td class=\"column3\"><span class=\"";
		String testelem4="<td class=\"column4\">";
		String testelem5="<td class=\"column5\">";
		String elemend="</td>";
		String rowend="</tr>";
		Set<String> entries=null;
		for (JSONObject value : tcdetails){
			entries =value.keySet();
			for(String key: entries)
			{
				String singleresult="";
				JSONObject ja =  value.getJSONObject(key);
				//Object tcname="TestCase";
				String tcnameval=ja.get("TestCase").toString();
				String itr=ja.get("ItrNo").toString();
				String status=ja.get("ExeStatus").toString();
				String colr="";
				String tcrespath=ja.get("ResAttachment").toString();
				String defectid="";
				//String failedkeyword="";
				String defHREF="";
				String defcom="";
				String deftype="";
				String comm="";
				if(status.equalsIgnoreCase("Fail"))
					colr="failstats";
				else if(status.equalsIgnoreCase("Ignored")){
					colr="ignstats";
					comm="Test Ignored since no valid iteration found in Data sheet. Please check";
				}
				else
					colr="passstats";
				if(ja.has("DefectID")){
					defectid=ja.get("DefectID").toString();
					//failedkeyword=ja.get("Failed Keyword").toString();
					defHREF=ja.get("DefectHREF").toString();
					defcom=ja.get("DefectFound").toString();
					deftype=ja.get("DefectType").toString();
					if(defcom.equalsIgnoreCase("NEW"))
						comm="New "+deftype+" Logged for This TC";
					else
						comm=deftype+" Already Exists, Comments have been updated for this run";
				}
				if(tcrespath.isEmpty()){
					singleresult=testelem1+tcnameval+elemend+testelem2+itr+elemend+testelem3+colr+"\">"+status+"</span>"+elemend;
				}
				else{
					singleresult=testelem1+"<a href=\""+tcrespath+"\">"+tcnameval+"</a>"+elemend+testelem2+itr+elemend+testelem3+colr+"\">"+status+"</span>"+elemend;
				}
				if(defHREF.isEmpty())
					singleresult=singleresult+testelem4+defectid+elemend+testelem5+comm+elemend+rowend;
				else
					singleresult=singleresult+testelem4+"<a href=\""+defHREF+"\">"+defectid+"</a>"+elemend+testelem5+comm+elemend+rowend;
				resbuf.append(singleresult);
			}
		}





		return reportString.replaceFirst("\\$tcdetbody", resbuf.toString());
	}

	private String buildrepSuitedetails(String reportString,JSONObject jsummdet) {
		// TODO Auto-generated method stub
		String passedCnt=jsummdet.get("Passed").toString();
		String failedCnt=jsummdet.get("Failed").toString();
		String TotalCnt=jsummdet.get("Total Scripts").toString();
		String igncnt=jsummdet.get("Ignored").toString();
		String strStartTime=removequotesfromJSONval(jsummdet.get("Start Time").toString());
		String strEndTime=removequotesfromJSONval(jsummdet.get("End Time").toString());
		String strTotalTimeTaken=removequotesfromJSONval(jsummdet.get("Total Time Taken").toString());
		String DMtool=removequotesfromJSONval(jsummdet.get("DMtool").toString());
		//String DMprojectarea=removequotesfromJSONval(jsummdet.get("DMprojectarea").toString());
		String DMTeam=removequotesfromJSONval(jsummdet.get("DM_RTCteamName").toString());
		String TMtool=removequotesfromJSONval(jsummdet.get("TMtool").toString());
		String TMprojArea=removequotesfromJSONval(jsummdet.get("TM_RQMprojectArea").toString());
		String TMSuiteID=removequotesfromJSONval(jsummdet.get("TM_RQMsuiteID").toString());
		String TMdomname=removequotesfromJSONval(jsummdet.get("TM_RQMdomainName").toString());
		String TMappname=removequotesfromJSONval(jsummdet.get("TM_RQMappName").toString());
		String env=removequotesfromJSONval(jsummdet.get("TM_RQMenv").toString());
		//put read details in read HTML template
		reportString=reportString.replaceAll("\\$appname", TMappname);
		reportString=reportString.replaceAll("\\$domname", TMdomname);
		reportString=reportString.replaceAll("\\$env", env);
		reportString=reportString.replaceAll("\\$startime", strStartTime);
		reportString=reportString.replaceAll("\\$endtime", strEndTime);
		reportString=reportString.replaceAll("\\$duration",strTotalTimeTaken);
		reportString=reportString.replaceAll("\\$tmtool", TMtool);
		reportString=reportString.replaceAll("\\$tmprojarea", TMprojArea);
		reportString=reportString.replaceAll("\\$dmtoolteam", DMtool+"-"+DMTeam);
		//Build Container detials
		String tshref="";
		//	        if(jsummdet.containsKey("TMsuiteHREF"))
		//	        	tshref=removequotesfromJSONval(jsummdet.get("TMsuiteHREF").toString()); //Build this when integrating RQM integration
		//	        	
		//String tsid=""; //Build this when integrating RQM integration
		//double passper=0.00;
		double passper=((double)(Integer.parseInt(passedCnt))/((double)Integer.parseInt(TotalCnt)))*100;
		reportString=reportString.replaceAll("\\$tsidhref", tshref);
		reportString=reportString.replaceAll("\\$tsid", TMSuiteID);
		reportString=reportString.replaceAll("\\$passper", ""+Math.round(passper));
		reportString=reportString.replaceAll("\\$passcnt", passedCnt);
		reportString=reportString.replaceAll("\\$failcnt", failedCnt);
		reportString=reportString.replaceAll("\\$igncnt", igncnt);
		reportString=reportString.replaceAll("\\$totcnt", TotalCnt);
		reportString=reportString.replaceAll("\\$defcnt", "NA");
		return reportString;
	}

	private String readCTrepTemplate(String reptemppath){
		StringBuffer retBuf = new StringBuffer();
		try{
			File file = new File(reptemppath);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}

			br.close();
			retBuf.toString();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			retBuf.toString();
			//br.close();
		}
		finally
		{
			//return retBuf.toString();
		}
		return retBuf.toString();
	}
	private JSONObject readSumdetailsfromJSON(String consjson) {
		// TODO Auto-generated method stub
		JSONObject sumdetails=new JSONObject();
		try{

			//FileReader fr = new FileReader(consjson);
			JSONObject jo = new JSONObject();
			//JSONParser jp = new JSONParser();
			//jo = (JSONObject) jp.parse(fr);
			jo=readJsonfile(consjson);
			sumdetails=(JSONObject) jo.get("Summary");
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

		return sumdetails;
	}
	private JSONObject readJsonfile(String filename){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String jsonString = sb.toString();
			br.close();
			JSONObject rootObject=new JSONObject (jsonString);
			//JSONArray jArray = new JSONArray(jsonString);
			//JSONObject rootObject = new JSONObject(jArray.get(0).toString());
			return rootObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}


	}
	private List<JSONObject> readTCdetailsfromJSON(String consjson){
		// TODO Auto-generated method stub
		List<JSONObject> tcdetails=new ArrayList<>();
		try{
			//FileReader fr = new FileReader(consjson);
			JSONObject jo = new JSONObject();
			//JSONParser jp = new JSONParser();
			//jo = (JSONObject) jp.parse(fr);
			jo=readJsonfile(consjson);
			JSONArray ja = (JSONArray) jo.get("TestCaseList");
			int counter=0,length = ja.length();
			while(length>0){
				tcdetails.add((JSONObject) ja.get(counter));
				counter++;length--;
			}
		} catch (Exception e){
			return null;
		}

		return tcdetails;
	}
	public String removequotesfromJSONval(String val){
		try{
			if(val.isEmpty())
				return "";
			if (val.contains("\"")){
				val=val.substring(1, val.length()-1);
			}

		}
		catch(Exception e){
			return "";
		}
		return val;
	}

}
