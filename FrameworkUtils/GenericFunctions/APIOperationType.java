package GenericFunctions;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Reporting.Report;
import TestScriptRunner.Runner;


public class APIOperationType {

	static String samlAccessToken = "";
	public static String strOperationTypeStatic = "";
	public static Boolean samlFlag = true;
	public void operationType(String strScriptName, String strIterationNum,String strServiceType , String strUserTokenRequired, String strOperationType)
	{
		strOperationTypeStatic = strOperationType;
		Report objReport=new Report();
		try
		{

			//eliminating . from Iteration number
			if(strIterationNum.contains("."))
			{
				int intdelLoc=strIterationNum.indexOf(".");
				strIterationNum=strIterationNum.substring(0, intdelLoc);
			}

			APICreateAndExecute webCreateAndExecute = new APICreateAndExecute();
			APIFunctions objAPIFunc = new APIFunctions();


			switch (strOperationType) 
			{
			case "ALL":

				switch (strServiceType) 
				{
				case "POST":						
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for POST Method
					objAPIFunc.validateResponsePost(strScriptName);

					break;

				case "POSTERROR":
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"YES");

					//Simple Validation Response for POST Method
					objAPIFunc.validateResponsePost(strScriptName);

					break;

				case "GET":
					//Create and Execute API for GET Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for GET Method
					objAPIFunc.validateResponsGet(strScriptName);

					break;

				case "GETERROR":

					//Create and Execute API for GET Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"YES");

					//Simple Validation Response for GET Method
					objAPIFunc.validateResponsGet(strScriptName);

					break;

				case "DELETE":
					//Create and Execute API for DELETE Method
					webCreateAndExecute.createAndExecuteDelete(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for GET Method
					objAPIFunc.validateResponsDelete(strScriptName);

					break;

				case "PUT":
					//Create and Execute API for PUT Method
					webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for PUT Method
					objAPIFunc.validateResponsePut(strScriptName);

					break;


				case "PUTERROR":
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"YES");

					//Simple Validation Response for POST Method
					objAPIFunc.validateResponsePut(strScriptName);

					break;
				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - ALL): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET , PUT)"); 	
					break;
				}

				break;
			case "DynamicValidationAPI":
				switch (strServiceType) 
				{
				case "POST":
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

					//Dynamic Validation Response for POST Method
					objAPIFunc.SemiDynamicValidateResponsepost(strScriptName);

					break;
				case "GET":						
					//Create and Execute API for get Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

					//Dynamic Validation Response for GET Method
					objAPIFunc.SemiDynamicValidateResponseGet(strScriptName);

					break;


				case "GETERROR":

					//Create and Execute API for get Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"YES");

					//Dynamic Validation Response for GET Method
					objAPIFunc.SemiDynamicValidateResponseGet(strScriptName);

					break;	

				case "PUT":
					//Create and Execute API for PUT Method
					webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"NO");

					//Dynamic Validation Response for PUT Method
					objAPIFunc.SemiDynamicValidateResponsePut(strScriptName);
					break;
				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - DynamicValidationAPI): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET , PUT)"); 	
					break;
				}

				break;

				//Dynamic Database-API Response validation	
			case "DynamicDBAPIValidation":
				switch (strServiceType) 
				{
				case "POST":
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

					//DB Validation Response for POST Method
					objAPIFunc.dynamicValResponsPost(strScriptName);
					break;

				case "GET":
					//Create and Execute API for get Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

					//DB Validation Response for GET Method
					objAPIFunc.dynamicValResponsGET(strScriptName);

					break;
				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - DynamicDBAPIValidation): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET )"); 	
					break;
				}

				break;
				//Dynamic Database-API Response validation	
			case "DynamicDBReqAPIValidation":
				switch (strServiceType) 
				{
				case "POST":
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

					//DB-Request Validation for POST Method
					objAPIFunc.dynamicValRequestPost(strScriptName);

					break;
				case "PUT":
					//Create and Execute API for PUT Method
					webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"NO");

					objAPIFunc.dynamicValRequestPut(strScriptName);

					break;
				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - DynamicDBReqAPIValidation): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET , PUT )"); 	
					break;
				}

				break;
				//Create and execute API without Validation	
			case "CREATE&EXECUTE":
				switch (strServiceType) 
				{
				case "POST":
					//Create and Execute API for post Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");
					break;

				case "GET":
					//Create and Execute API for GET Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");						
					break;

				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - CREATE&EXECUTE): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET )"); 	
					break;
				}

				break;

				//SAMLValidation
			case "SAMLValidation":
				//SAML Token Generation
			{
				String samlURL = "";
				String username = "";
				String password = "";
				String baseUrl = "";
				String clientID = "";
				String clientSecret = "";
				String refreshToken = "";

				if(samlFlag==true){
					samlFlag = false;
					String dataSheet=Runner.strResourceFldLoc+Runner.properties.getProperty("testDataFile");
					FileInputStream io = new FileInputStream(dataSheet);	
					HSSFWorkbook wb=new HSSFWorkbook(io);
					HSSFSheet sheet=wb.getSheet("SAML");
					int rowNum = sheet.getLastRowNum()+1;
					for (int i = 0; i < rowNum; i++) {
						if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue().trim())){
							samlURL = sheet.getRow(i).getCell(1).getStringCellValue();
							username = sheet.getRow(i).getCell(2).getStringCellValue();
							password = sheet.getRow(i).getCell(3).getStringCellValue();
							baseUrl = sheet.getRow(i).getCell(4).getStringCellValue();
							clientID = sheet.getRow(i).getCell(5).getStringCellValue();
							clientSecret = sheet.getRow(i).getCell(6).getStringCellValue();
							if(!(sheet.getRow(i).getCell(7)==null || sheet.getRow(i).getCell(7).getStringCellValue().equalsIgnoreCase("")))
							{
								refreshToken = sheet.getRow(i).getCell(7).getStringCellValue();
							}
						}
					}
					APIFunctions objAPIFunctions = new APIFunctions();
					samlAccessToken = objAPIFunctions.generateSAMLAccessToken(samlURL,username,password,baseUrl,clientID,clientSecret,refreshToken);
//					samlAccessToken = "AAEkMWE4ZDAwYTgtNjJhZi00NGJlLWEyNzYtNWFlNzkyYjYwNTIz93aSQD4WTpVX9R3C0Tx7UBjUNihswWQT1kONoQXbmeQBq4dOFZdJWWCBdNsz3puKrohC0vjyMHn9vPP3XH3Zfj6JK0nUQ0m1ioeeTZSC1gEL9RAd-3dr3y1Zgf7h7cKl";
					System.out.println(samlAccessToken);
				}
			}

			switch (strServiceType) 
			{
			case "POST":
				//Create and Execute API for POST Method
				webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

				//Simple Validation Response for POST Method
				objAPIFunc.validateResponsePost(strScriptName);

				break;

			case "GET":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

				//Simple Validation Response for GET Method
				objAPIFunc.validateResponsGet(strScriptName);

				break;				

			case "GETERROR":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"YES");

				//Simple Validation Response for GET Method
				objAPIFunc.validateResponsGet(strScriptName);

				break;

			case "PUT":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"NO");

				//Simple Validation Response for GET Method
				objAPIFunc.validateResponsePut(strScriptName);

				break;

			case "DELETE":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecuteDelete(strScriptName,strUserTokenRequired,"NO");

				//Simple Validation Response for GET Method
				objAPIFunc.validateResponsDelete(strScriptName);

				break;

			default:
				objReport.setValidationMessageInReport("FAIL","Method operationType (case - SAMLValidation): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET )"); 	
				break;
			}

			break;


			//Switch Case for SAML flow without validation
			case "SAML_CREATE&EXECUTE":
			{
				String samlURL = "";
				String username = "";
				String password = "";
				String baseUrl = "";
				String clientID = "";
				String clientSecret = "";
				String refreshToken = "";

				if(samlFlag==true){
					samlFlag = false;
					String dataSheet=Runner.strResourceFldLoc+Runner.properties.getProperty("testDataFile");
					FileInputStream io = new FileInputStream(dataSheet);	
					HSSFWorkbook wb=new HSSFWorkbook(io);
					HSSFSheet sheet=wb.getSheet("SAML");
					int rowNum = sheet.getLastRowNum()+1;
					for (int i = 0; i < rowNum; i++) {
						if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(0).getStringCellValue().trim())){
							samlURL = sheet.getRow(i).getCell(1).getStringCellValue();
							username = sheet.getRow(i).getCell(2).getStringCellValue();
							password = sheet.getRow(i).getCell(3).getStringCellValue();
							baseUrl = sheet.getRow(i).getCell(4).getStringCellValue();
							clientID = sheet.getRow(i).getCell(5).getStringCellValue();
							clientSecret = sheet.getRow(i).getCell(6).getStringCellValue();
							if(!(sheet.getRow(i).getCell(7)==null || sheet.getRow(i).getCell(7).getStringCellValue().equalsIgnoreCase("")))
							{
								refreshToken = sheet.getRow(i).getCell(7).getStringCellValue();
							}
						}
					}
					APIFunctions objAPIFunctions = new APIFunctions();
					samlAccessToken = objAPIFunctions.generateSAMLAccessToken(samlURL,username,password,baseUrl,clientID,clientSecret,refreshToken);
					System.out.println(samlAccessToken);

				}
			}
			switch (strServiceType) 
			{
			case "POST":
				//Create and Execute API for post Method
				webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

				break;

			case "GET":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

				break;				

			case "GETERROR":
				//Create and Execute API for GET Method
				webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"YES");

				break;

			case "PUT":
				//Create and Execute API for PUT Method
				webCreateAndExecute.createAndExecutePut(strScriptName,strUserTokenRequired,"NO");

				break;

			case "DELETE":
				//Create and Execute API for PUT Method
				webCreateAndExecute.createAndExecuteDelete(strScriptName,strUserTokenRequired,"NO");

				break;

			default:
				objReport.setValidationMessageInReport("FAIL","Method operationType (case - SAMLValidation): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET )"); 	
				break;
			}

			break;


			case "VALIDATEXML":
				switch (strServiceType) 
				{
				case "POST":
					//Simple Validation Response for POST Method
					objAPIFunc.validateResponsePost(strScriptName);
					break;

				case "GET":
					//Simple Validation Response for GET Method
					objAPIFunc.validateResponsGet(strScriptName);
					break;

				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType (case - VALIDATEXML): Incorrect strServiceType argument '"+strServiceType+"' is passed. Please provide correct arguement value (POST , GET )"); 	
					break;
				}

				break;
				//Schema validation for JSON request/response
			case "SchemaValidationJSON":
				objAPIFunc.webserviceSchemaValjson(strScriptName);
				break;

				//Schema validation for XML request/response
			case "SchemaValidationXML":
				objAPIFunc.webserviceSchemaValxml(strScriptName);
				break;

				//Compare different tag names and tag values for diff XMLs
			case "COMPARETWOXMLs":
				objAPIFunc.webserviceCompareXMLs(strScriptName);
				break;
			default:
				objReport.setValidationMessageInReport("FAIL","Method operationType : Incorrect strOperationType argument '"+strServiceType+"' is passed. Please provide correct arguement value"); 	
				break;
			}

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "operationType");			
		}
	}
	
	
	public void soapOperationType(String strScriptName, String strIterationNum,String strServiceType , String strUserTokenRequired, String strOperationType)
	{
		strOperationTypeStatic = strOperationType;
		Report objReport=new Report();
		try
		{

			//eliminating . from Iteration number
			if(strIterationNum.contains("."))
			{
				int intdelLoc=strIterationNum.indexOf(".");
				strIterationNum=strIterationNum.substring(0, intdelLoc);
			}

			APICreateAndExecute webCreateAndExecute = new APICreateAndExecute();
			APIFunctions objAPIFunc = new APIFunctions();


			switch (strOperationType) 
			{
			case "ALL":

				switch (strServiceType) 
				{
				case "POST":						
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecutePost(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for POST Method
					objAPIFunc.validateSoapResponse(strScriptName,"POST");

					break;
					
				case "GET":						
					//Create and Execute API for POST Method
					webCreateAndExecute.createAndExecuteGet(strScriptName,strUserTokenRequired,"NO");

					//Simple Validation Response for POST Method
					objAPIFunc.validateSoapResponse(strScriptName, "GET");

					break;

				default:
					objReport.setValidationMessageInReport("FAIL","Method operationType : Incorrect strOperationType argument '"+strServiceType+"' is passed. Please provide correct arguement value"); 	
					break;
				}

			}
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "groovyOperationType");			
		}
	}

}


