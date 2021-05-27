package Reporting;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import TestScriptRunner.Runner;

public class TestScriptExectnStatusListener implements IInvokedMethodListener 
{
	private static volatile boolean failing;

	public TestScriptExectnStatusListener() {
		failing = false;
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) 
	{
		if (failing) {

			if(Runner.stopTestSuiteRunFlag==true)
			{
				
				//throw new RuntimeException("Test skipped due to a detected failure in the overall suite.");
				throw new SkipException("Method execution is skipped due to the Fail status of previous method");
			}
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult)
	{
		if ((! testResult.isSuccess())) 
		{
			failing = true;			
		}

		//Update the test Result with "Skipped".  
		//Alternatively, you could use omit this code.
		//The RuntimeException thrown above will mark the test with "Failed" by default.
		if ((failing) && 
				(testResult.getThrowable().getMessage().contains("Test skipped"))) {
			testResult.setStatus(ITestResult.SKIP);
		}
	}
}