package Reporting;



import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import GenericFunctions.CommonFunctions;
import GenericFunctions.WebMobileFunctions;
import TestScriptRunner.Runner;

public class Report 
{
	public static SoftAssert softAssert = new SoftAssert();


	//@SuppressWarnings("unchecked")
	public void setValidationMessageInReport(String strStatus, String strMessage) 
	{
		try{
			if(strStatus.equalsIgnoreCase("PASS"))
			{
				/*if(Runner.strBDDIndicator.equalsIgnoreCase("Y")||Runner.strBDDIndicator.equalsIgnoreCase("Yes")) {
					Runner.strBDDFailMsgMap.put(Runner.strBDDScenario.getName(), strMessage);
    			}*/
				//logger.log(LogStatus.PASS, strMessage);
				Reporter.log(strMessage+"####PASS####");    	
				//Reporter.log("<br />" ); 
				
			}
			else if(strStatus.equalsIgnoreCase("FAIL"))
			{
				Runner.testExecutionStatus = true;
				Runner.driverMap.put("StepFlag", "false");

				// Capture application Screenshot related to failed step
				CommonFunctions.captureFailScreenshot();
				System.out.println(Runner.failedStepScreenshot);
				Reporter.log(strMessage+"####FAIL####");

				if(Runner.strBDDIndicator.equalsIgnoreCase("Y")||Runner.strBDDIndicator.equalsIgnoreCase("Yes")){
//					Runner.strBDDFailMsgMap.put(Runner.strBDDScenario.getName(), strMessage);
					Runner.strBDDFailMsgMap.put(Runner.strBDDScenario.getName()+"@"+Runner.bddStpCnt+"@"+Runner.bddCurrentStep, strMessage);
					
					/*WebMobileFunctions objWebMobFunc= new WebMobileFunctions();
					if(Runner.strTechnology.equalsIgnoreCase("Web"))
					{
						objWebMobFunc.closeBrowser();
						Thread.sleep(2000);
					}
					Assert.fail();*/
				}

				//Setting the condition for not proceeding with the further script execution. 
				//This setting up condition based on the value available in On Failure column of the currently running test script

				if(Runner.strOnFailureStatus.equalsIgnoreCase("Exit")) {   
					Runner.stopTestSuiteRunFlag=true;
					Assert.fail();
				}

				Runner.methodExectnPassStatus=false;

				StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				String execMethodName = "";

				for (int i=0;i<stackTrace.length;i++) {
					execMethodName=stackTrace[i].getMethodName().trim();
					if (execMethodName.substring(0, 6).equals("invoke")) {
						Runner.execMethodName=stackTrace[i-1].getMethodName().trim();
						break;
					}
				}

				Runner.failedKeywordStatus=true;
				Runner.failedKeywrdmap.put(Runner.execMethodName, true);

			} else if(strStatus.equalsIgnoreCase("SKIP")) {
				Reporter.log(strMessage+"####SKIP####"); 
			} else {
				Reporter.log("Please provide correct strStatus arguement value (PASS or FAIL)"+"####FAIL####"); 
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	public void writeStackTraceErrorInReport(Exception e , String strMethodName) 
	{
		//	StringWriter errors = new StringWriter();
		//	e.printStackTrace(new PrintWriter(errors));		   
		//	String stackTraceError=errors.toString();
		setValidationMessageInReport("FAIL","Method '"+strMethodName+"' : Failed due to exception "+ e   ); 			
	}

	public void setMethodExecutnStatusInReport() 
	{
		if(Runner.methodExectnPassStatus==false)
		{
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertTrue(false);
			softAssert.assertAll();
		}
	}

	public void checkMethodSkipStatus() 
	{
		if(Runner.stopTestSuiteRunFlag == true)
		{
			setValidationMessageInReport("SKIP", "Method execution is skipped due to the Fail status of previous method");
			throw new SkipException("");
		}
	}
}
