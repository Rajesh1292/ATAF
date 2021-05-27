package GenericFunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Reporting.Report;
import TestScriptRunner.Runner;



public class APICreateAndExecute {

	/**
	 * Script Name   : <b>Webservice_CreateAndExecute</b>
	 * 
	 * Generated     : <b>Apr 3, 2017 10:33:50 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2017/04/03
	 * @author n077138
	 */

	Report objReport=new Report();

	//String dataSheet = ApplicationConstants.testDataFile;

	//String dataSheet=Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("testDataFile");
	String dataSheet=Runner.strResourceFldLoc+Runner.properties.getProperty("testDataFile");


	public HashMap<String,ArrayList<String>> TagidMap=new HashMap<String,ArrayList<String>>();
	static XSSFWorkbook wb2 = null;
	static XSSFSheet fSheet = null;
	public static int mutpAPICount = 0;
	public static int mutpGETCount = 0;
	public static int mutpPOSTCount = 0;
	public static int mutpPUTCount = 0;
	public static int mutpDELETECount = 0;

	public void createAndExecutePost(String strScriptName, String strUserTokenRequired,String strErrRspChkFlag) 
	{
		APIFunctions webserCommnFunc = new APIFunctions();
		String strInputDataSheetTemp = "";

		try{
			FileInputStream io = new FileInputStream(dataSheet);

			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("POST");

			int rowNum = sheet.getLastRowNum()+1;

			String strTestCase = null;
			String strWebserviceURL= null;
			String strInputDataSheet= null;
			String strInputDataFolder = null;
			String strOutputResponseFolder= null;
			String strRespnsXMLNodes= null;
			String strCertificate= "";
			String strCertPassword= "";
			String strBasexmlLocation= "";
			String strTestcaseName= null;
			String strWorksheetName= "";
			String strUserToken= "";
			String strClientId= "";
			String strClientSecret= "";
			String strAccessTokenEndpoint= "";
			String strCcope= "";
			String appAcceptUsed= "";
			String CDATAExists="";
			String JsonOut="";
			String isSAMLToken="";
			//String strEndPointUrl="";
//			String strClientID ="";
			String pIsJson="";
			boolean MultipleAPIFlagPost = false;
			String pJwtToken = "";
			String pUserNameToken= "";
			String pPasswordToken= "";
			String strAuthorizationEndpoint="";
			String strRedirectUrl="";
			String externalTokens="";

			for(int i=1;i<rowNum;i++)
			{
				if(MultipleAPIFlagPost==false)
				{

					if(mutpPOSTCount>0 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
						i=i+mutpPOSTCount;
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()))
					{
						mutpPOSTCount++;
						MultipleAPIFlagPost = true;
						//pScriptName Column value ( Test Script name)
						strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();

						//pWebService Column value (WebService Url)
						strWebserviceURL = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();

						if(strWebserviceURL.contains("#") && mutpAPICount>1){
							int startIndex = strWebserviceURL.indexOf("#");
							int lastIndex = strWebserviceURL.lastIndexOf("#");

							String tagDetails = strWebserviceURL.substring(startIndex+1, lastIndex);
							String ArrTagname[] = tagDetails.split(";");
							int outputIndex = Integer.parseInt(ArrTagname[0]);
							int index = Integer.parseInt(ArrTagname[2]);
							strWebserviceURL = strWebserviceURL.replace("#"+tagDetails+"#", webserCommnFunc.multipleConcatURL(ArrTagname[1], strTestCase, outputIndex, index));
						}

						System.out.println(strWebserviceURL);

						if (sheet.getRow(i).getCell(getcolumn("C"))!=null)
						{
							strInputDataSheet = Runner.strResourceFldLoc +sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim()+".xlsx";
						}


						//pInputXML Column Column value ()
						strInputDataFolder = Runner.properties.getProperty("InputXMLFolderPath") + sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();

						//pOutputResponseXML Column value ()
						strOutputResponseFolder = Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();

						//pInputXMLParentTag Column value ()
						if (sheet.getRow(i).getCell(getcolumn("F"))!=null)
						{
							strRespnsXMLNodes = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
						}

						//pCertificate Column value ()
						if (sheet.getRow(i).getCell(getcolumn("G"))!=null)
						{
							strCertificate = Runner.strResourceFldLoc +"Certificate\\"+sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();;
						}

						//pCertPassword Column value ()
						if (sheet.getRow(i).getCell(getcolumn("H"))!=null)
						{
							strCertPassword = sheet.getRow(i).getCell(getcolumn("H")).getStringCellValue().trim();
						}

						//pInputXMLPerm Column value ()
						if (sheet.getRow(i).getCell(getcolumn("I"))!=null)
						{
							if(!(sheet.getRow(i).getCell(getcolumn("I")).getStringCellValue().trim().equalsIgnoreCase(""))){
								strBasexmlLocation = Runner.strResourceFldLoc +"BaseFile\\"+sheet.getRow(i).getCell(getcolumn("I")).getStringCellValue().trim();
								System.out.println(strBasexmlLocation);
							}
						}

						//pTestCase Column value ()
						if (sheet.getRow(i).getCell(getcolumn("J"))!=null)
						{
							strTestcaseName = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
						}

						//pWorksheetName Column value ()
						if (sheet.getRow(i).getCell(getcolumn("K"))!=null)
						{
							strWorksheetName = sheet.getRow(i).getCell(getcolumn("K")).getStringCellValue().trim();
						}

						//pUserToken Column
						if (sheet.getRow(i).getCell(getcolumn("L"))!=null)
						{
							strUserToken = sheet.getRow(i).getCell(getcolumn("L")).getStringCellValue().trim();
						}

						//pClientId Column value ()
						if (sheet.getRow(i).getCell(getcolumn("M"))!=null)
						{
							strClientId = sheet.getRow(i).getCell(getcolumn("M")).getStringCellValue().trim();
						}

						//pClientSecret Column value ()
						if (sheet.getRow(i).getCell(getcolumn("N"))!=null)
						{
							strClientSecret = sheet.getRow(i).getCell(getcolumn("N")).getStringCellValue().trim();
						}

						//pAccessTokenEndpoint Column value ()
						if (sheet.getRow(i).getCell(getcolumn("O"))!=null)
						{
							strAccessTokenEndpoint = sheet.getRow(i).getCell(getcolumn("O")).getStringCellValue().trim();
						}

						//pCcope Column value ()
						if (sheet.getRow(i).getCell(getcolumn("P"))!=null)
						{
							strCcope = sheet.getRow(i).getCell(getcolumn("P")).getStringCellValue().trim();
						}

						//pAppAcceptUsed Column value ()
						if (sheet.getRow(i).getCell(getcolumn("Q"))!=null)
						{
							appAcceptUsed = sheet.getRow(i).getCell(getcolumn("Q")).getStringCellValue().trim();
						}

						//pJsonOut Column value ()
						if ((sheet.getRow(i).getCell(getcolumn("R")) != null))
						{
							JsonOut = sheet.getRow(i).getCell(getcolumn("R")).getStringCellValue().trim();
						}

						if ((sheet.getRow(i).getCell(getcolumn("S")) != null))
						{
							CDATAExists = sheet.getRow(i).getCell(getcolumn("S")).getStringCellValue().trim();
						}
						String strRespDSFolder = Runner.properties.getProperty("APIResponseDSFolderPath");

						strInputDataSheetTemp=strRespDSFolder+"\\"+strScriptName+"_Data_Sheet.xlsx";
						if(mutpAPICount>1){
							strInputDataSheetTemp=strRespDSFolder+"\\"+strScriptName+"_Data_Sheet"+(mutpAPICount-1)+".xlsx";
						}

						//For JSON Input
						if (sheet.getRow(i).getCell(getcolumn("T"))!=null)
						{
							pIsJson = sheet.getRow(i).getCell(getcolumn("T")).getStringCellValue().trim();
						}

						//SAML token  IsSAML--Yes/No 

						if (sheet.getRow(i).getCell(getcolumn("Z"))!=null){
							isSAMLToken= sheet.getRow(i).getCell(getcolumn("Z")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("U"))!=null)
						{
							pJwtToken = sheet.getRow(i).getCell(getcolumn("U")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("V"))!=null)
						{
							pUserNameToken = sheet.getRow(i).getCell(getcolumn("V")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("W"))!=null)
						{
							pPasswordToken = sheet.getRow(i).getCell(getcolumn("W")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("X"))!=null)
						{
							strAuthorizationEndpoint = sheet.getRow(i).getCell(getcolumn("X")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("Y"))!=null)
						{
							strRedirectUrl = sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("AA"))!=null)
						{
							externalTokens = sheet.getRow(i).getCell(getcolumn("AA")).getStringCellValue().trim();
						}

						//*****************

						File f3= new File(strInputDataSheetTemp);
						if (f3.exists()){
							f3.delete();
						}

						XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
						wb1.write(new FileOutputStream(strInputDataSheetTemp));

						String strinputXML;
						String XML_String="";

						if(!(strBasexmlLocation.equalsIgnoreCase(""))) {

							if (JsonOut.equalsIgnoreCase("YES")){
								strinputXML=strInputDataFolder+"\\\\"+strTestCase+ ".txt";
								System.out.println(strinputXML);
							}
							else{
								strinputXML= strInputDataFolder + "\\\\"+strTestCase+ ".xml" ;
								File f= new File(strinputXML);
								f.createNewFile();
							}

							if(externalTokens.equalsIgnoreCase("Yes")){
								strUserToken = webserCommnFunc.getExternalToken(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope, strCertificate, strCertPassword);

							}
							else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && strAuthorizationEndpoint.isEmpty() && !pJwtToken.equalsIgnoreCase("Yes")){
//								strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
								strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope, pUserNameToken, pPasswordToken);
							}
							else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && !(pUserNameToken.isEmpty()) && !pJwtToken.equalsIgnoreCase("Yes")){
								strUserToken = webserCommnFunc.getUserToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientId,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
							}

							Files.copy(Paths.get(strBasexmlLocation),new FileOutputStream(strinputXML));

							ArrayList<String> testList1 = webserCommnFunc.readXMLValues1(strTestcaseName, strInputDataSheetTemp, strWorksheetName);

							/*if(strBasexmlLocation.endsWith(".txt")||strBasexmlLocation.endsWith(".json")) {
								webserCommnFunc.updateJSONvalue(strinputXML,testList1,strTestCase,mutpAPICount);

							}
							else*/ if(JsonOut.isEmpty() || JsonOut.equalsIgnoreCase("") || JsonOut.equalsIgnoreCase("NO") || JsonOut==null){

								for (int i1=0; i1<testList1.size(); i1++){
									webserCommnFunc.xmlUpdateNew(strinputXML, testList1.get(i1).toString(), strBasexmlLocation, strScriptName);
								}

								//Convert the Input XML file content(DOM) to string which is going to be used for calling the Web service
								XML_String=(webserCommnFunc.convertDOMToString(strinputXML)).trim();
							} else {

								XML_String = webserCommnFunc.JSON_XML(strBasexmlLocation,strInputDataFolder,strScriptName).toString();
								webserCommnFunc.convertStringToDOM(XML_String,strInputDataFolder+"\\\\"+strScriptName+".xml");

								for (int i1=0; i1<testList1.size(); i1++){
									webserCommnFunc.xmlUpdateNew(strInputDataFolder+"\\\\"+strScriptName+".xml", testList1.get(i1).toString(), strBasexmlLocation, strScriptName);
								}

								XML_String = webserCommnFunc.convertToJson(strInputDataFolder,strInputDataFolder, strScriptName);
							}

							BufferedReader br = new BufferedReader(new FileReader(strinputXML));
							StringBuilder sb = new StringBuilder();
							String line = br.readLine();

							while (line != null) {
								sb.append(line);
								sb.append(System.lineSeparator());
								line = br.readLine();
							}
							XML_String = sb.toString();
							br.close();
						} else {
							strinputXML= strInputDataFolder + "\\\\"+strTestCase+ ".xml";
							XML_String=(webserCommnFunc.convertDOMToString(strinputXML)).trim();
						}

						if (XML_String.contains("&amp;")){
							XML_String = XML_String.split("&amp;")[0] + "&" +XML_String.split("&amp;")[1];
						}


						//jwt Token

						String JwtToken="",finalStr="";
						if(pJwtToken.equalsIgnoreCase("Yes")){
							finalStr = webserCommnFunc.getJwtToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientId,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
							String[] finalStr1 = finalStr.split("#fz");
							strUserToken = "Bearer " +finalStr1[0].toString();
							JwtToken = finalStr1[1].toString();

						}

						//Calling Web service
						String responseString;

						String samlAccessToken = APIOperationType.samlAccessToken;
						if(isSAMLToken.equalsIgnoreCase("yes")){
							samlAccessToken = "Bearer "+samlAccessToken;
							responseString=webserCommnFunc.postSSLWebserviceCallSAMLTokenJSON(XML_String,strWebserviceURL,strCertificate,strCertPassword,samlAccessToken,strClientId);
							//responseString = webserCommnFunc.getWebserviceCallSAML(strEndPointUrl, samlAccessToken, strClientID, strErrRspChkFlag);
						}
						else if(externalTokens.equalsIgnoreCase("Yes")){
							responseString = webserCommnFunc.postExternalcall(XML_String,strWebserviceURL,strCertificate,strCertPassword,strUserToken,pIsJson,strErrRspChkFlag);

						}
						else if(pJwtToken.equalsIgnoreCase("YES")){
							responseString = webserCommnFunc.postWebserJWTjson(XML_String,strWebserviceURL, strUserToken, strClientId, JwtToken, strCertificate, strCertPassword,strErrRspChkFlag,pIsJson);
						}
						else if(strUserToken.isEmpty() && (strCertificate.isEmpty()||strCertPassword.isEmpty())){
							responseString=webserCommnFunc.postWebserviceCall(XML_String, strWebserviceURL,"NO");
						}
						else if (CDATAExists.equalsIgnoreCase("Yes") && (strUserToken.isEmpty() && (!strCertificate.isEmpty()))){
							responseString=webserCommnFunc.postWebserviceCallSSLwithCDATA(XML_String,strWebserviceURL,strCertificate,strCertPassword);
						}
						else if (!strUserToken.isEmpty() && (strCertificate.isEmpty()||strCertPassword.isEmpty())){
							responseString=webserCommnFunc.postWebserviceCallUserToken(XML_String, strWebserviceURL,strUserToken,strClientId,pIsJson);
						}
						else if (!strUserToken.isEmpty() && (strCertificate.isEmpty()) && (!JsonOut.equalsIgnoreCase("YES"))){
							responseString=webserCommnFunc.postSSLWebserviceCall(XML_String, strWebserviceURL,strUserToken,strClientId,strErrRspChkFlag);
						}
						else if (strUserToken.isEmpty() && (!strCertificate.isEmpty()) && (JsonOut.equalsIgnoreCase("YES"))){
							responseString=webserCommnFunc.postWebserviceCallSSLjson(XML_String,strWebserviceURL,strCertificate,strCertPassword,strErrRspChkFlag);
							webserCommnFunc.storeJsonResponse(strOutputResponseFolder,strTestCase,responseString,mutpAPICount);
						}
						else if (!strUserToken.isEmpty() && (!strCertificate.isEmpty()) && (JsonOut.equalsIgnoreCase("YES"))){
							responseString=webserCommnFunc.postSSLWebserviceCallUserTokenJSON(XML_String,strWebserviceURL,strCertificate,strCertPassword,strUserToken,strClientId,pIsJson);
							webserCommnFunc.storeJsonResponse(strOutputResponseFolder,strTestCase,responseString,mutpAPICount);
						}
						else if (strUserToken.isEmpty() && (!strCertificate.isEmpty()) ){
							responseString=webserCommnFunc.postSSLWebserviceCall(XML_String,strWebserviceURL,strCertificate,strCertPassword,strErrRspChkFlag);
						}
						else if (strUserToken.isEmpty() && (strCertificate.isEmpty()||strCertPassword.isEmpty()) && appAcceptUsed.equalsIgnoreCase("YES")){
							responseString=webserCommnFunc.postWebserviceHeaderCall(XML_String, strWebserviceURL);
						}
						else{
							responseString=webserCommnFunc.postSSLWebserviceCallUserTokenJSON(XML_String,strWebserviceURL,strCertificate,strCertPassword,strUserToken,strClientId,pIsJson);
						}

						System.out.println(responseString);
						if (JsonOut.equalsIgnoreCase("YES")){

							FileInputStream fis = new FileInputStream(strInputDataSheetTemp);

							wb2 = new XSSFWorkbook(fis);
							fSheet = wb2.createSheet("Output");
							fSheet = wb2.getSheet("Output");
							fSheet= webserCommnFunc.parseJsonString(responseString, fSheet);

							FileOutputStream outFile = new FileOutputStream(strInputDataSheetTemp);
							wb2.write(outFile);
							outFile.close();
							//wb1.close();
						}
						else {
							//Convert Web service Response String to DOM (Storing Webservice Response Data in XML file)
							String strWebserviceResponseXML= strOutputResponseFolder + "\\"+strTestCase+".xml" ;

							if(CDATAExists.equalsIgnoreCase("Yes"))
							{
								if(responseString.contains("CDATA"))
								{
									objReport.setValidationMessageInReport("PASS"," CDATA Validation (POST Method) : Webservice API Response value Contains CDATA value");	
								}
								else{
									objReport.setValidationMessageInReport("FAIL"," CDATA Validation (POST Method) : Webservice API Response did not contain CDATA value");	
								}
							}
							//webserCommnFunc.parseCData(responseString, strWebserviceResponseXML);
							else
							{
								webserCommnFunc.convertStringToDOM(responseString,strWebserviceResponseXML);

								//Storing the XML (Output Response XML)Nodes Attribute information in Excel file
								String strTagNameArr[]=strRespnsXMLNodes.split("@");
								webserCommnFunc.covertDOMToExcel(strInputDataSheetTemp,strWebserviceResponseXML,strTagNameArr);
							}

							//**************

						}

					}
				}}}
		catch(Exception e)
		{			
			objReport.writeStackTraceErrorInReport(e, "createAndExecutePost");
		}
	}

	public static int getcolumn(String Colname)
	{
		String newstr = Colname.replaceAll("[^A-Za-z]+", "");
		int colIdx = CellReference.convertColStringToIndex(newstr);

		return colIdx;
	}



	public void createAndExecuteGet(String strScriptName, String strUserTokenRequired , String strErrRspChkFlag)  
	{

		APIFunctions webserCommnFunc = new APIFunctions();
		String strDatasheet=dataSheet;
		int intTestCaseRowNum=0;	
		try
		{
			FileInputStream io = new FileInputStream(dataSheet);	
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("GET");

			int rowNum = sheet.getLastRowNum()+1;

			String strTestCase = "";//Script Name
			String strIterationNumber= "";
			//String strInputDataSheetFolder= null;//Temporary Input Datasheet
			String strInputDataSheet = "";//Input Data sheet
			String strInputDataWorksheet = "";//Input Data worksheet
			String strUserToken = "";// UserToken
			String strAeMultiPath = "";//AeMultiPath
			String strstatusCode = "";
			String strClientId= "";
			String strClientSecret= "";
			String strAccessTokenEndpoint= "";
			String strCcope= "";
			String strClientID= "";
			String strResponseXMLParentTag= "";//Response XML Parent Tag
			String strOutputResponseXMLFolder= "";//Output Response XML Folder
			//strDatasheet= null;
			String strUserName= "";
			String strCert="";
			String strCertPassword="";
			String pIsJson="";
			String pUserNameToken= "";
			String pPasswordToken= "";
			String JsonOut="";
			String pJwtToken="";
			String strAuthorizationEndpoint="";
			String strRedirectUrl="";
			String isSAMLToken = "";
			String isExternal = "";
			String jsonHeader = "";
			String wellBeingAPI = "";

			boolean MultipleAPIFlag=false;

			Boolean GETMethodRunStatudFlag=false;
			for(int i=1;i<rowNum;i++)
			{

				if(MultipleAPIFlag==false){

					if(mutpGETCount>0 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
						i=i+mutpGETCount;
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()))
					{
						mutpGETCount++;
						MultipleAPIFlag = true;
						GETMethodRunStatudFlag= true;
						strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name
						strIterationNumber = CommonFunctions.getCellValueAsString(sheet.getRow(i).getCell(getcolumn("B")));

						if (sheet.getRow(i).getCell(getcolumn("C"))!=null)
						{
							strInputDataSheet = Runner.strResourceFldLoc +sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim()+".xlsx";
						}

						if (sheet.getRow(i).getCell(getcolumn("D"))!=null)
						{
							strInputDataWorksheet = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("E"))!=null)
						{
							strUserToken = sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("F"))!=null)
						{
							strAeMultiPath = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("G"))!=null)
						{
							strstatusCode = sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("H"))!=null)
						{
							strClientId = sheet.getRow(i).getCell(getcolumn("H")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("I"))!=null)
						{
							strClientSecret = sheet.getRow(i).getCell(getcolumn("I")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("J"))!=null)
						{
							strAccessTokenEndpoint = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("K"))!=null)
						{
							strCcope = sheet.getRow(i).getCell(getcolumn("K")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("L"))!=null)
						{
							strClientID = sheet.getRow(i).getCell(getcolumn("L")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("M"))!=null)
						{
							strResponseXMLParentTag = sheet.getRow(i).getCell(getcolumn("M")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("N"))!=null)
						{
							strOutputResponseXMLFolder = Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i).getCell(getcolumn("N")).getStringCellValue().trim();							
						}
						if (sheet.getRow(i).getCell(getcolumn("O"))!=null){
							strUserName = sheet.getRow(i).getCell(getcolumn("O")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("P"))!=null)
						{
							strCert = Runner.strResourceFldLoc +"Certificate\\"+sheet.getRow(i).getCell(getcolumn("P")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("Q"))!=null)
						{
							strCertPassword = sheet.getRow(i).getCell(getcolumn("Q")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("R"))!=null)
						{
							pIsJson = sheet.getRow(i).getCell(getcolumn("R")).getStringCellValue().trim();
						}



						if (sheet.getRow(i).getCell(getcolumn("S"))!=null)
						{
							JsonOut=sheet.getRow(i).getCell(getcolumn("S")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("T"))!=null)
						{
							pUserNameToken= sheet.getRow(i).getCell(getcolumn("T")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("U"))!=null)
						{
							pPasswordToken= sheet.getRow(i).getCell(getcolumn("U")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("V"))!=null)
						{
							strRedirectUrl = sheet.getRow(i).getCell(getcolumn("V")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("W"))!=null){
							strAuthorizationEndpoint = sheet.getRow(i).getCell(getcolumn("W")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("X"))!=null){
							pJwtToken= sheet.getRow(i).getCell(getcolumn("X")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("Y"))!=null){
							isSAMLToken= sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("Z"))!=null){
							isExternal= sheet.getRow(i).getCell(getcolumn("Z")).getStringCellValue().trim();
						}
						if (!(sheet.getRow(i).getCell(getcolumn("AA"))==null ||sheet.getRow(i).getCell(getcolumn("AA")).getStringCellValue().trim().equalsIgnoreCase("")||sheet.getRow(i).getCell(getcolumn("AA")).getStringCellValue().trim().equalsIgnoreCase("No"))){
							jsonHeader=Runner.strResourceFldLoc +"Header\\"+sheet.getRow(i).getCell(getcolumn("AA")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("AB"))!=null){
							wellBeingAPI= sheet.getRow(i).getCell(getcolumn("AB")).getStringCellValue().trim();
						}

						//String strInputDataSheetTemp= strInputDataSheetFolder+"\\\\"+strTestCase+"_Data_Sheet.xlsx";

						//String strRespDSFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath");						
						String strRespDSFolder = Runner.properties.getProperty("APIResponseDSFolderPath");
						String strInputDataSheetTemp=strRespDSFolder+strScriptName+"_Data_Sheet.xlsx";

						if(mutpAPICount>1){
							strInputDataSheetTemp=strRespDSFolder+strScriptName+"_Data_Sheet"+(mutpAPICount-1)+".xlsx";
						}
						intTestCaseRowNum=webserCommnFunc.getTestCaseRowNumber(strDatasheet, strTestCase, strIterationNumber);

						//	String strDate= new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());
						XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
						wb1.write(new FileOutputStream(strInputDataSheetTemp));

						//Removing Decimal(.0) from 'strIterationNum' String variable 
						//String strIterationNum = null;
						if(strIterationNumber.contains(".0"))
						{
							int intdelLoc=strIterationNumber.indexOf(".");
							strIterationNumber=strIterationNumber.substring(0, intdelLoc);
						}

						String strWebserviceResponseXML="";
						if(!JsonOut.equalsIgnoreCase("yes")){
							if(mutpAPICount>1){
								strWebserviceResponseXML= strOutputResponseXMLFolder+"\\"+strTestCase+"_"+mutpAPICount+".xml" ;							
							}
							else{
								strWebserviceResponseXML= strOutputResponseXMLFolder+"\\"+strTestCase+".xml" ;	
							}
						}
						String strEndPointUrl="";

						if(pIsJson.equalsIgnoreCase("yes")){
							strEndPointUrl = webserCommnFunc.getWebserviceEndPointUrlJSON(strInputDataSheet, strTestCase, strInputDataWorksheet);
						}
						else{
							strEndPointUrl=webserCommnFunc.getWebserviceEndPointUrl(strInputDataSheetTemp, strTestCase, strInputDataWorksheet,mutpAPICount);
							System.out.println("strEndPointUrl  : "+strEndPointUrl);

						}
						System.out.println("strEndPointUrl  : "+strEndPointUrl);

						if(isExternal.equalsIgnoreCase("Yes")){
							strUserToken = webserCommnFunc.getExternalToken(strClientId,strClientSecret,strAccessTokenEndpoint,strCcope,strCert, strCertPassword);

						}
						else if (wellBeingAPI.equalsIgnoreCase("Yes")) {
							strUserToken = webserCommnFunc.getwellBeingAPIToken(strAccessTokenEndpoint);

						}
						else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && strAuthorizationEndpoint.isEmpty() && !pJwtToken.equalsIgnoreCase("Yes")){
//							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId , strClientSecret, strAccessTokenEndpoint, strCcope, pUserNameToken, pPasswordToken);
						}
						else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && !(pUserNameToken.isEmpty()) && !pJwtToken.equalsIgnoreCase("Yes")){
							strUserToken = webserCommnFunc.getUserToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientID,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
						}
						System.out.println(strstatusCode);

						String JwtToken="",finalStr="";
						if(pJwtToken.equalsIgnoreCase("Yes") && !wellBeingAPI.equalsIgnoreCase("Yes")){
							finalStr = webserCommnFunc.getJwtToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientID,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
							String[] finalStr1 = finalStr.split("#fz");
							strUserToken = "Bearer " +finalStr1[0].toString();
							JwtToken = finalStr1[1].toString();
						}

						if (wellBeingAPI.equalsIgnoreCase("Yes")) {
							String[] strWellbeingAPI = strUserToken.split("#fz");
							strUserToken = "Bearer " + strWellbeingAPI[0];
							JwtToken = strWellbeingAPI[1];

						}

						String responseString ;
						String samlAccessToken = APIOperationType.samlAccessToken;

						if (wellBeingAPI.equalsIgnoreCase("Yes")) {
							responseString = webserCommnFunc.getWellbeingAPI(strEndPointUrl, strUserToken, JwtToken, strErrRspChkFlag)	;						
						}
						else if(isExternal.equalsIgnoreCase("yes")) {
							responseString = webserCommnFunc.getExternalCall(strEndPointUrl, strUserToken, strClientID, strCert, strCertPassword, jsonHeader);
						}
						else if(isSAMLToken.equalsIgnoreCase("yes")){
							samlAccessToken = "Bearer "+samlAccessToken;
							responseString = webserCommnFunc.getWebserviceCallSAML(strEndPointUrl, samlAccessToken, strClientID, strErrRspChkFlag);
						}
						else if(pJwtToken.equalsIgnoreCase("YES")){
							responseString = webserCommnFunc.getWebserviceCallJwt(strEndPointUrl, strUserToken, strClientID, JwtToken , strErrRspChkFlag);
						}
						else if((!strUserToken.isEmpty() || pUserNameToken.isEmpty() || !strClientID.isEmpty()) && JsonOut.equalsIgnoreCase("YES")){

							responseString = webserCommnFunc.getJsonWebserviceCall(strEndPointUrl, strClientID, strAeMultiPath,strUserToken , strErrRspChkFlag,jsonHeader);						

						}
						else if(!strUserToken.isEmpty() && strClientID.isEmpty() && strCert.isEmpty()){
							responseString = webserCommnFunc.getWebserviceCall(strEndPointUrl,strUserToken, strAeMultiPath, strErrRspChkFlag);
						}
						else if(!strUserToken.isEmpty() && strCert.isEmpty()){
							responseString = webserCommnFunc.getWebserviceCall(strEndPointUrl, strUserToken, strAeMultiPath, strClientID);		
						}

						else if(!strUserToken.isEmpty() && pUserNameToken.isEmpty()){
							responseString = webserCommnFunc.getWebserviceCall(strEndPointUrl, strUserToken, strAeMultiPath, strClientID);		
						}

						else if(!strUserName.isEmpty() && strUserToken.isEmpty() && strCert.isEmpty()){

							responseString= webserCommnFunc.getWebserviceCallWithoutMultipath(strEndPointUrl, strUserName, strErrRspChkFlag);
						}
						else if(strCert.isEmpty()){

							responseString = webserCommnFunc.getWebserviceCallWithoutUserToken(strEndPointUrl,strAeMultiPath, strErrRspChkFlag);
						}else{
							responseString = webserCommnFunc.getWebserviceCallWithCertAndPassword(strEndPointUrl,strCert,strCertPassword,strErrRspChkFlag);
						}
						System.out.println(responseString);  

						webserCommnFunc.storeJsonResponse(strOutputResponseXMLFolder,strTestCase,responseString,mutpAPICount);

						String sCode = "";
						String ResponseExpectedStatusCode = webserCommnFunc.getStatusCode(sCode);
						System.out.println(ResponseExpectedStatusCode);
						String strsCode=webserCommnFunc.compareStatusCode(strstatusCode);

						if (JsonOut.equalsIgnoreCase("YES") && strstatusCode.isEmpty())
						{
							FileInputStream fis = new FileInputStream(strInputDataSheetTemp);

							wb2 = new XSSFWorkbook(fis);
							fSheet = wb2.createSheet("Output");
							fSheet = wb2.getSheet("Output");

							fSheet= webserCommnFunc.parseJsonString(responseString, fSheet);
							FileOutputStream outFile = new FileOutputStream(strInputDataSheetTemp);
							wb2.write(outFile);
							outFile.close();

						}

						else if (strstatusCode.isEmpty())
						{
							webserCommnFunc.convertStringToDOM(responseString,strWebserviceResponseXML);

							//Storing the XML (Output Response XML)Nodes Attribute information in Excel file
							String strTagNameArr[]=strResponseXMLParentTag.split("@");
							webserCommnFunc.covertDOMToExcel(strInputDataSheetTemp,strWebserviceResponseXML,strTagNameArr);

							//webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");

						}
						else{
							if(strsCode.equalsIgnoreCase(strstatusCode))
							{
								//webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");
								objReport.setValidationMessageInReport("PASS","Method createAndExecuteGet : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 						
							}
							else
							{
								webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");
								objReport.setValidationMessageInReport("FAIL","Method createAndExecuteGet : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 							
							}
						}

						break;
					}}
			}


			if(GETMethodRunStatudFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method createAndExecuteGet : please check test script '"+strScriptName+"' is available in the GET Worksheet" ); 							

			}	
		}
		catch (Exception e) 
		{
			//Updating the execution status column of DataSheet with status as FAIL					
			webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");					
			objReport.writeStackTraceErrorInReport(e, "createAndExecuteGet");
		}
	}



	public void createAndExecuteGetDB(String strScriptName, String strUserTokenRequired, String strErrRspChkFlag) throws Exception {

		int count =0;
		APIFunctions webserCommnFunc = new APIFunctions();

		FileInputStream io = new FileInputStream(dataSheet);	
		HSSFWorkbook wb=new HSSFWorkbook(io);
		HSSFSheet sheet=wb.getSheet("GET");

		int rowNum = sheet.getLastRowNum()+1;

		String strTestCase = null;//Script Name
		String strIterationNumber= null;
		String strInputDataSheetFolder= null;//Temporary Input Datasheet
		String strInputDataSheet = null;//Input Data sheet
		String strInputDataWorksheet = null;//Input Data worksheet
		String strUserToken = null;// UserToken
		String strAeMultiPath = null;//AeMultiPath
		String strstatusCode = null;
		String strClientId= null;
		String strClientSecret= null;
		String strAccessTokenEndpoint= null;
		String strCcope= null;
		String strClientID= null;
		String strResponseXMLParentTag= null;//Response XML Parent Tag
		String strOutputResponseXMLFolder= null;//Output Response XML Folder
		String strDatasheet= null;
		String strUserName= null;
		String strCert=null;
		String strCertPassword=null;

		for(int i=1;i<rowNum;i++)
		{
			if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

				strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name
				strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
				strInputDataSheetFolder = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
				strInputDataSheet = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
				strInputDataWorksheet = sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();
				strUserToken = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
				strAeMultiPath = sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();
				strstatusCode = sheet.getRow(i).getCell(getcolumn("H")).getStringCellValue().trim();
				strClientId = sheet.getRow(i).getCell(getcolumn("I")).getStringCellValue().trim();
				strClientSecret = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
				strAccessTokenEndpoint = sheet.getRow(i).getCell(getcolumn("K")).getStringCellValue().trim();
				strCcope = sheet.getRow(i).getCell(getcolumn("L")).getStringCellValue().trim();
				strClientID = sheet.getRow(i).getCell(getcolumn("M")).getStringCellValue().trim();
				strResponseXMLParentTag = sheet.getRow(i).getCell(getcolumn("N")).getStringCellValue().trim();
				strOutputResponseXMLFolder = sheet.getRow(i).getCell(getcolumn("O")).getStringCellValue().trim();
				strDatasheet = sheet.getRow(i).getCell(getcolumn("P")).getStringCellValue().trim();
				strUserName = sheet.getRow(i).getCell(getcolumn("Q")).getStringCellValue().trim();
				strCert = sheet.getRow(i).getCell(getcolumn("R")).getStringCellValue().trim();
				strCertPassword = sheet.getRow(i).getCell(getcolumn("S")).getStringCellValue().trim();

				String strInputDataSheetTemp= strInputDataSheetFolder+"\\"+strTestCase+"_Data_Sheet.xlsx";
				int intTestCaseRowNum=webserCommnFunc.getTestCaseRowNumber(strDatasheet, strTestCase, strIterationNumber);

				try
				{
					//	String strDate= new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());

					XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
					wb1.write(new FileOutputStream(strInputDataSheetTemp));

					//Removing Decimal(.0) from 'strIterationNum' String variable 
					//String strIterationNum = null;
					if(strIterationNumber.contains(".0"))
					{
						int intdelLoc=strIterationNumber.indexOf(".");
						strIterationNumber=strIterationNumber.substring(0, intdelLoc);
					}

					String strWebserviceResponseXML= strOutputResponseXMLFolder+"\\"+strTestCase+"_"+strIterationNumber+"_"+".xml" ;
					String strEndPointUrl=webserCommnFunc.getWebserviceEndPointUrl(strInputDataSheetTemp, strTestCase, strInputDataWorksheet,count);
					System.out.println("strEndPointUrl  : "+strEndPointUrl);

					if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES")){
						strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
					}
					System.out.println(strstatusCode);

					String responseString;
					if(!strUserToken.isEmpty() && strClientID.isEmpty() && strCert.isEmpty()){
						responseString = webserCommnFunc.getWebserviceCall(strEndPointUrl,strUserToken, strAeMultiPath,strErrRspChkFlag);
					}
					else if(!strUserToken.isEmpty() && strCert.isEmpty()){
						responseString = webserCommnFunc.getWebserviceCall(strEndPointUrl, strUserToken, strAeMultiPath, strClientID);		
					}

					else if(!strUserName.isEmpty() && strUserToken.isEmpty() && strCert.isEmpty()){

						responseString= webserCommnFunc.getWebserviceCallWithoutMultipath(strEndPointUrl, strUserName,strErrRspChkFlag);
					}
					else if(strCert.isEmpty()){

						responseString = webserCommnFunc.getWebserviceCallWithoutUserToken(strEndPointUrl,strAeMultiPath, strErrRspChkFlag);
					}else{
						responseString = webserCommnFunc.getWebserviceCallWithCertAndPassword(strEndPointUrl,strCert,strCertPassword ,strErrRspChkFlag);
					}


					String sCode = "";
					String ResponseExpectedStatusCode = webserCommnFunc.getStatusCode(sCode);
					System.out.println(ResponseExpectedStatusCode);
					String strsCode=webserCommnFunc.compareStatusCode(strstatusCode);

					if (strstatusCode.isEmpty()){
						webserCommnFunc.convertStringToDOM(responseString,strWebserviceResponseXML);

						//Storing the XML (Output Response XML)Nodes Attribute information in Excel file
						String strTagNameArr[]=strResponseXMLParentTag.split("@");
						webserCommnFunc.covertDOMToExcel(strInputDataSheetTemp,strWebserviceResponseXML,strTagNameArr);


						webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");

						//Deleting the temporary created Input Data Worksheet
						/*File f3= new File(strInputDataSheetTemp);
						f3.delete();*/
					}
					else{
						if(strsCode.equalsIgnoreCase(strstatusCode)){
							objReport.setValidationMessageInReport("PASS","Method createAndExecuteGetDB : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 													
							webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");
						}
						else{
							objReport.setValidationMessageInReport("FAIL","Method createAndExecuteGetDB : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 											
							webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");
						}
					}

				}
				catch (Exception e) {
					//Updating the execution status column of DataSheet with status as FAIL
					webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");
					objReport.writeStackTraceErrorInReport(e, "createAndExecuteGetDB");
				}
			}
		}

	}


	public void createAndExecutePut(String strScriptName, String strUserTokenRequired,  String strErrRspChkFlag) {

		APIFunctions webserCommnFunc = new APIFunctions();
		String strInputDataSheetTemp = "";

		try{
			FileInputStream io = new FileInputStream(dataSheet);

			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("PUT");

			int rowNum = sheet.getLastRowNum()+1;

			String strTestCase = null;
			String strWebserviceURL= null;
			String strInputDataSheet= null;
			String strInputDataFolder = null;
			String strOutputResponseFolder= null;
			String strRespnsXMLNodes= null;
			String strCertificate= null;
			String strCertPassword= null;
			String strBasexmlLocation= "";
			String strTestcaseName= null;
			String strWorksheetName= null;
			String strUserToken= "";
			String strClientId= null;
			String strClientSecret= null;
			String strAccessTokenEndpoint= null;
			String strCcope= null;
			String pIsJSON=null;
			String JsonOut="";
			String CDATAExists="";
			String isSAMLToken = "";
			String pUserNameToken= "";
			String pPasswordToken= "";
			String strAuthorizationEndpoint="";
			String strRedirectUrl="";
			String isExternal = "";
			String isjwt = "";
			boolean MultipleAPIFlagPut = false;


			for(int i1=1;i1<rowNum;i1++)
			{
				if(MultipleAPIFlagPut == false)
				{
					if(mutpPUTCount>0 && strScriptName.equalsIgnoreCase(sheet.getRow(i1).getCell(getcolumn("A")).getStringCellValue().trim())){
						i1=i1+mutpPUTCount;
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i1).getCell(getcolumn("A")).getStringCellValue().trim()))
					{
						mutpPUTCount++;
						MultipleAPIFlagPut = true;
						strTestCase = sheet.getRow(i1).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name

						if (sheet.getRow(i1).getCell(getcolumn("B"))!=null){
							strWebserviceURL = sheet.getRow(i1).getCell(getcolumn("B")).getStringCellValue().trim();
							if(strWebserviceURL.contains("#") && mutpAPICount>1){
								int startIndex = strWebserviceURL.indexOf("#");
								int lastIndex = strWebserviceURL.lastIndexOf("#");

								String tagDetails = strWebserviceURL.substring(startIndex+1, lastIndex);
								String ArrTagname[] = tagDetails.split(";");
								int outputIndex = Integer.parseInt(ArrTagname[0]);
								int index = Integer.parseInt(ArrTagname[2]);
								strWebserviceURL = strWebserviceURL.replace("#"+tagDetails+"#", webserCommnFunc.multipleConcatURL(ArrTagname[1], strTestCase, outputIndex, index));
							}
						}
						System.out.println("WebServiceURL: "+strWebserviceURL);
						if (sheet.getRow(i1).getCell(getcolumn("C"))!=null)
						{
							strInputDataSheet = Runner.strResourceFldLoc +sheet.getRow(i1).getCell(getcolumn("C")).getStringCellValue().trim()+".xlsx";
						}						
						if (sheet.getRow(i1).getCell(getcolumn("D"))!=null){
							//strInputDataFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("InputXMLFolderPath") + sheet.getRow(i1).getCell(getcolumn("D")).getStringCellValue().trim();
							strInputDataFolder = Runner.properties.getProperty("InputXMLFolderPath") + sheet.getRow(i1).getCell(getcolumn("D")).getStringCellValue().trim();						
						}
						if (sheet.getRow(i1).getCell(getcolumn("E"))!=null){
							//strOutputResponseFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i1).getCell(getcolumn("E")).getStringCellValue().trim();
							strOutputResponseFolder = Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i1).getCell(getcolumn("E")).getStringCellValue().trim();						
						}
						if (sheet.getRow(i1).getCell(getcolumn("F"))!=null){
							strRespnsXMLNodes = sheet.getRow(i1).getCell(getcolumn("F")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("G"))!=null){
							strCertificate = Runner.strResourceFldLoc +"Certificate\\"+ sheet.getRow(i1).getCell(getcolumn("G")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("H"))!=null){
							strCertPassword = sheet.getRow(i1).getCell(getcolumn("H")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("I"))!=null){
							if(!(sheet.getRow(i1).getCell(getcolumn("I")).getStringCellValue().trim().equalsIgnoreCase(""))){
								strBasexmlLocation = Runner.strResourceFldLoc+"BaseFile\\"+sheet.getRow(i1).getCell(getcolumn("I")).getStringCellValue().trim();

							}

						}
						if (sheet.getRow(i1).getCell(getcolumn("J"))!=null){
							strTestcaseName = sheet.getRow(i1).getCell(getcolumn("J")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("K"))!=null){
							strWorksheetName = sheet.getRow(i1).getCell(getcolumn("K")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("L"))!=null){
							strUserToken = sheet.getRow(i1).getCell(getcolumn("L")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("M"))!=null){
							strClientId = sheet.getRow(i1).getCell(getcolumn("M")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("N"))!=null){
							strClientSecret = sheet.getRow(i1).getCell(getcolumn("N")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("O"))!=null){
							strAccessTokenEndpoint = sheet.getRow(i1).getCell(getcolumn("O")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("P"))!=null){
							strCcope = sheet.getRow(i1).getCell(getcolumn("P")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("Q"))!=null){
							JsonOut = sheet.getRow(i1).getCell(getcolumn("Q")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("R"))!=null){
							CDATAExists = sheet.getRow(i1).getCell(getcolumn("R")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("S"))!=null){
							pIsJSON = sheet.getRow(i1).getCell(getcolumn("S")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("T"))!=null){
							isSAMLToken = sheet.getRow(i1).getCell(getcolumn("T")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("U"))!=null)
						{
							pUserNameToken = sheet.getRow(i1).getCell(getcolumn("U")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("V"))!=null)
						{
							pPasswordToken = sheet.getRow(i1).getCell(getcolumn("V")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("W"))!=null)
						{
							strAuthorizationEndpoint = sheet.getRow(i1).getCell(getcolumn("W")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("X"))!=null)
						{
							strRedirectUrl = sheet.getRow(i1).getCell(getcolumn("X")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("Y"))!=null)
						{
							isExternal = sheet.getRow(i1).getCell(getcolumn("Y")).getStringCellValue().trim();
						}
						if (sheet.getRow(i1).getCell(getcolumn("Z"))!=null)
						{
							isjwt = sheet.getRow(i1).getCell(getcolumn("Z")).getStringCellValue().trim();
						}


						String strRespDSFolder = Runner.properties.getProperty("APIResponseDSFolderPath");

						strInputDataSheetTemp=strRespDSFolder+"\\"+strScriptName+"_Data_Sheet.xlsx";
						if(mutpAPICount>1){
							strInputDataSheetTemp=strRespDSFolder+"\\"+strScriptName+"_Data_Sheet"+(mutpAPICount-1)+".xlsx";
						}

						File f3= new File(strInputDataSheetTemp);
						if (f3.exists()){
							f3.delete();
						}

						XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
						wb1.write(new FileOutputStream(strInputDataSheetTemp));

						String strinputXML;
						if (JsonOut.equalsIgnoreCase("YES")){
							strinputXML=strInputDataFolder+"\\"+strTestCase+ ".txt";
							System.out.println(strinputXML);
						}
						else{
							strinputXML= strInputDataFolder + "\\"+strTestCase+ ".xml" ;
							File f= new File(strinputXML);
							f.createNewFile();
						}


						/*if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES")){
							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
						}*/
						String jwtToken = "" ,finalStr = "";
						if(isjwt.equalsIgnoreCase("Yes")){
							finalStr = webserCommnFunc.getJwtToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientId,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
							String[] finalStr1 = finalStr.split("#fz");
							strUserToken = "Bearer " +finalStr1[0].toString();
							jwtToken = finalStr1[1].toString();

						}
						else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && strAuthorizationEndpoint.isEmpty()){
//							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope, pUserNameToken, pPasswordToken);
						}
						else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && !(pUserNameToken.isEmpty())){
							strUserToken = webserCommnFunc.getUserToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientId,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
						}else if(isExternal.equalsIgnoreCase("Yes")){
							strUserToken = webserCommnFunc.getExternalToken(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope, strCertificate, strCertPassword);

						}
						
						String XML_String="";

						if(!(strBasexmlLocation.equalsIgnoreCase(""))) {
							Files.copy(Paths.get(strBasexmlLocation),new FileOutputStream(strinputXML));
							if (JsonOut.equalsIgnoreCase("YES")){
								strinputXML=strInputDataFolder+"\\\\"+strTestCase+ ".txt";
								System.out.println(strinputXML);
							}
							else{
								strinputXML= strInputDataFolder + "\\\\"+strTestCase+ ".xml" ;
								File f= new File(strinputXML);
								f.createNewFile();
							}

							if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES")){
								strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
							}

							ArrayList<String> testList1 = webserCommnFunc.readXMLValues1(strTestcaseName, strInputDataSheetTemp, strWorksheetName);

							/*if(strBasexmlLocation.endsWith(".txt")||strBasexmlLocation.endsWith(".json")) {
								webserCommnFunc.updateJSONvalue(strinputXML,testList1,strTestCase,mutpAPICount);

							}
							else*/ if(JsonOut.isEmpty() || JsonOut.equalsIgnoreCase("") || JsonOut.equalsIgnoreCase("NO") || JsonOut==null){

								for (int i2=0; i1<testList1.size(); i2++){
									webserCommnFunc.xmlUpdateNew(strinputXML, testList1.get(i2).toString(), strBasexmlLocation, strScriptName);
								}

								//Convert the Input XML file content(DOM) to string which is going to be used for calling the Web service
								XML_String=(webserCommnFunc.convertDOMToString(strinputXML)).trim();
							} else {

								XML_String = webserCommnFunc.JSON_XML(strBasexmlLocation,strInputDataFolder,strScriptName).toString();
								webserCommnFunc.convertStringToDOM(XML_String,strInputDataFolder+"\\\\"+strScriptName+".xml");

								for (int i2=0; i1<testList1.size(); i2++){
									webserCommnFunc.xmlUpdateNew(strInputDataFolder+"\\\\"+strScriptName+".xml", testList1.get(i2).toString(), strBasexmlLocation, strScriptName);
								}

								XML_String = webserCommnFunc.convertToJson(strInputDataFolder,strInputDataFolder, strScriptName);
							}

							BufferedReader br = new BufferedReader(new FileReader(strinputXML));
							StringBuilder sb = new StringBuilder();
							String line = br.readLine();

							while (line != null) {
								sb.append(line);
								sb.append(System.lineSeparator());
								line = br.readLine();
							}
							XML_String = sb.toString();
							br.close();
						}
						//------------------------------------------


						if (XML_String.contains("&amp;")){
							XML_String=XML_String.split("&amp;")[0] + "&" +XML_String.split("&amp;")[1];
						}
						if (XML_String.contains("root")){
							XML_String=XML_String.substring(44,XML_String.length()-7);
							System.out.println(1);
						}

						//Calling Web service
						String responseString = "";
						if(pIsJSON==null || pIsJSON.equalsIgnoreCase(""))
						{
							pIsJSON="NO";
						}

						String samlAccessToken = APIOperationType.samlAccessToken;
						if(isSAMLToken.equalsIgnoreCase("yes")){
							samlAccessToken = "Bearer "+samlAccessToken;
							//responseString = webserCommnFunc.postSSLWebserviceCallSAMLTokenJSON(XML_String,strWebserviceURL,strCertificate,strCertPassword,samlAccessToken,strClientId);
							responseString = webserCommnFunc.putSSLWebserviceCallSAMLTokenJSON(XML_String,strWebserviceURL,strCertificate,strCertPassword,samlAccessToken,strClientId);
						}else if(isExternal.equalsIgnoreCase("Yes")){
							//postExternalcall
							responseString = webserCommnFunc.postExternalcall(XML_String,strWebserviceURL,strCertificate,strCertPassword,strUserToken,pIsJSON,strErrRspChkFlag);

						}
						else if(isjwt.equalsIgnoreCase("YES")){
							responseString = webserCommnFunc.putWebserJWTjson(XML_String,strWebserviceURL, strUserToken, strClientId, jwtToken, strCertificate, strCertPassword,strErrRspChkFlag);
						}
						else if (strUserToken.isEmpty() && (!strCertificate.isEmpty()) ){
							responseString=webserCommnFunc.putSSLWebserviceCall(XML_String,strWebserviceURL,strCertificate,strCertPassword,strErrRspChkFlag);
						}
						else if(pIsJSON.equalsIgnoreCase("YES") && !strUserTokenRequired.equalsIgnoreCase("YES")){
							responseString=webserCommnFunc.putWebserviceCallJSON(XML_String,strWebserviceURL,strErrRspChkFlag);
							webserCommnFunc.storeJsonResponse(strOutputResponseFolder,strTestCase,responseString,mutpAPICount);
						}
						else if(pIsJSON.equalsIgnoreCase("YES") && strUserTokenRequired.equalsIgnoreCase("YES")){
							responseString=webserCommnFunc.putWebserviceCallToken("json",XML_String,strWebserviceURL,strUserToken,strErrRspChkFlag);
							webserCommnFunc.storeJsonResponse(strOutputResponseFolder,strTestCase,responseString,mutpAPICount);
						}
						else if (strUserTokenRequired.equalsIgnoreCase("YES")&&(pIsJSON.equalsIgnoreCase("No"))){
							responseString=webserCommnFunc.putWebserviceCallToken("xml",XML_String,strWebserviceURL,strUserToken,strErrRspChkFlag);
						}
						else if(strUserToken.isEmpty() && (strCertificate.isEmpty()||strCertPassword.isEmpty()) && pIsJSON.equalsIgnoreCase("NO") && JsonOut.isEmpty()){
							responseString=webserCommnFunc.putWebserviceCall(XML_String, strWebserviceURL,"xml",strErrRspChkFlag);
						}

						System.out.println(responseString);

						if (JsonOut.equalsIgnoreCase("YES"))
						{

							FileInputStream fis = new FileInputStream(strInputDataSheetTemp);

							wb2 = new XSSFWorkbook(fis);
							fSheet = wb2.createSheet("Output");
							fSheet = wb2.getSheet("Output");
							fSheet= webserCommnFunc.parseJsonString(responseString, fSheet);

							FileOutputStream outFile = new FileOutputStream(strInputDataSheetTemp);
							wb2.write(outFile);
							outFile.close();
							//wb1.close();
						}
						else {
							//Convert Web service Response String to DOM (Storing Webservice Response Data in XML file)
							String strWebserviceResponseXML= strOutputResponseFolder + "\\"+strTestCase+".xml" ;

							if(CDATAExists.equalsIgnoreCase("Yes")){
								if(responseString.contains("CDATA"))
								{
									objReport.setValidationMessageInReport("PASS"," CDATA Validation (PUT Method) : Webservice API Response value Contains CDATA value");	
								}
								else
								{
									objReport.setValidationMessageInReport("FAIL"," CDATA Validation (PUT Method) : Webservice API Response did not contain CDATA value");	
								}
							}

							if(!CDATAExists.equalsIgnoreCase("Yes") && !APIOperationType.strOperationTypeStatic.contains("CREATE&EXECUTE")){
								webserCommnFunc.convertStringToDOM(responseString,strWebserviceResponseXML);

								//Storing the XML (Output Response XML)Nodes Attribute information in Excel file
								String strTagNameArr[]=strRespnsXMLNodes.split("@");
								webserCommnFunc.covertDOMToExcel(strInputDataSheetTemp,strWebserviceResponseXML,strTagNameArr);
								break;
							}
						}
					}
				}}}

		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "createAndExecutePut");
		}
	}

	public void createAndExecuteDelete(String strScriptName, String strUserTokenRequired , String strErrRspChkFlag){


		APIFunctions webserCommnFunc = new APIFunctions();
		String strDatasheet=dataSheet;
		int intTestCaseRowNum=0;	
		try
		{
			FileInputStream io = new FileInputStream(dataSheet);	
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("DELETE");

			int rowNum = sheet.getLastRowNum()+1;

			String strTestCase = "";//Script Name
			String strIterationNumber= "";
			//String strInputDataSheetFolder= null;//Temporary Input Datasheet
			String strInputDataSheet = "";//Input Data sheet
			String strInputDataWorksheet = "";//Input Data worksheet
			String strUserToken = "";// UserToken
			String strAeMultiPath = "";//AeMultiPath
			String strstatusCode = "";
			String strClientId= "";
			String strClientSecret= "";
			String strAccessTokenEndpoint= "";
			String strCcope= "";
			String strClientID= "";
			String strResponseXMLParentTag= "";//Response XML Parent Tag
			String strOutputResponseXMLFolder= "";//Output Response XML Folder
			//strDatasheet= null;
			String strUserName= "";
			String strCert="";
			String strCertPassword="";
			String pIsJson="";
			String pUserNameToken= "";
			String pPasswordToken= "";
			String JsonOut="";
			String pJwtToken="";
			String strAuthorizationEndpoint="";
			String strRedirectUrl="";
			String isSAMLToken = "";

			boolean MultipleAPIFlag=false;

			Boolean GETMethodRunStatudFlag=false;
			for(int i=1;i<rowNum;i++)
			{

				if(MultipleAPIFlag==false){

					if(mutpDELETECount>0 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
						i=i+mutpDELETECount;
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()))
					{
						mutpDELETECount++;
						MultipleAPIFlag = true;
						GETMethodRunStatudFlag= true;
						strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name
						strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).toString().trim();

						if (sheet.getRow(i).getCell(getcolumn("C"))!=null)
						{
							strInputDataSheet = Runner.strResourceFldLoc +sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim()+".xlsx";
						}

						if (sheet.getRow(i).getCell(getcolumn("D"))!=null)
						{
							strInputDataWorksheet = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("E"))!=null)
						{
							strUserToken = sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("F"))!=null)
						{
							strAeMultiPath = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("G"))!=null)
						{
							strstatusCode = sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("H"))!=null)
						{
							strClientId = sheet.getRow(i).getCell(getcolumn("H")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("I"))!=null)
						{
							strClientSecret = sheet.getRow(i).getCell(getcolumn("I")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("J"))!=null)
						{
							strAccessTokenEndpoint = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("K"))!=null)
						{
							strCcope = sheet.getRow(i).getCell(getcolumn("K")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("L"))!=null)
						{
							strClientID = sheet.getRow(i).getCell(getcolumn("L")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("M"))!=null)
						{
							strResponseXMLParentTag = sheet.getRow(i).getCell(getcolumn("M")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("N"))!=null)
						{
							strOutputResponseXMLFolder = Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i).getCell(getcolumn("N")).getStringCellValue().trim();							
						}
						if (sheet.getRow(i).getCell(getcolumn("O"))!=null){
							strUserName = sheet.getRow(i).getCell(getcolumn("O")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("P"))!=null)
						{
							strCert = sheet.getRow(i).getCell(getcolumn("P")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("Q"))!=null)
						{
							strCertPassword = sheet.getRow(i).getCell(getcolumn("Q")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("R"))!=null)
						{
							pIsJson = sheet.getRow(i).getCell(getcolumn("R")).getStringCellValue().trim();
						}



						if (sheet.getRow(i).getCell(getcolumn("S"))!=null)
						{
							JsonOut=sheet.getRow(i).getCell(getcolumn("S")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("T"))!=null)
						{
							pUserNameToken= sheet.getRow(i).getCell(getcolumn("T")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("U"))!=null)
						{
							pPasswordToken= sheet.getRow(i).getCell(getcolumn("U")).getStringCellValue().trim();
						}

						if (sheet.getRow(i).getCell(getcolumn("V"))!=null)
						{
							strRedirectUrl = sheet.getRow(i).getCell(getcolumn("V")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("W"))!=null){
							strAuthorizationEndpoint = sheet.getRow(i).getCell(getcolumn("W")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("X"))!=null){
							pJwtToken= sheet.getRow(i).getCell(getcolumn("X")).getStringCellValue().trim();
						}
						if (sheet.getRow(i).getCell(getcolumn("Y"))!=null){
							isSAMLToken= sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim();
						}

						//String strInputDataSheetTemp= strInputDataSheetFolder+"\\\\"+strTestCase+"_Data_Sheet.xlsx";

						//String strRespDSFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath");						
						String strRespDSFolder = Runner.properties.getProperty("APIResponseDSFolderPath");
						String strInputDataSheetTemp=strRespDSFolder+strScriptName+"_Data_Sheet.xlsx";

						if(mutpAPICount>1){
							strInputDataSheetTemp=strRespDSFolder+strScriptName+"_Data_Sheet"+(mutpAPICount-1)+".xlsx";
						}
						intTestCaseRowNum=webserCommnFunc.getTestCaseRowNumber(strDatasheet, strTestCase, strIterationNumber);

						//	String strDate= new SimpleDateFormat("MMddyyyyhhmmss").format(new Date());

						XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
						wb1.write(new FileOutputStream(strInputDataSheetTemp));

						//Removing Decimal(.0) from 'strIterationNum' String variable 
						//String strIterationNum = null;
						if(strIterationNumber.contains(".0"))
						{
							int intdelLoc=strIterationNumber.indexOf(".");
							strIterationNumber=strIterationNumber.substring(0, intdelLoc);
						}

						String strWebserviceResponseXML="";
						if(!JsonOut.equalsIgnoreCase("yes")){
							if(mutpAPICount>1){
								strWebserviceResponseXML= strOutputResponseXMLFolder+"\\"+strTestCase+"_"+strIterationNumber+"_"+mutpAPICount+".xml" ;							
							}
							else{
								strWebserviceResponseXML= strOutputResponseXMLFolder+"\\"+strTestCase+"_"+strIterationNumber+"_"+".xml" ;	
							}
						}
						String strEndPointUrl="";

						if(pIsJson.equalsIgnoreCase("yes")){
							strEndPointUrl = webserCommnFunc.getWebserviceEndPointUrlJSON(strInputDataSheet, strTestCase, strInputDataWorksheet);
						}
						else{
							strEndPointUrl=webserCommnFunc.getWebserviceEndPointUrl(strInputDataSheetTemp, strTestCase, strInputDataWorksheet,mutpDELETECount);
							System.out.println("strEndPointUrl  : "+strEndPointUrl);

						}
						System.out.println("strEndPointUrl  : "+strEndPointUrl);

						if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && pUserNameToken.isEmpty() && !pJwtToken.equalsIgnoreCase("Yes")){
							strUserToken = webserCommnFunc.getUserTokenAuth(strClientId, strClientSecret, strAccessTokenEndpoint, strCcope,"","");
						}
						else if(strUserToken.isEmpty() && strUserTokenRequired.equalsIgnoreCase("YES") && !(pUserNameToken.isEmpty()) && !pJwtToken.equalsIgnoreCase("Yes")){
							strUserToken = webserCommnFunc.getUserTokenAuth(strClientID, strClientSecret, strAccessTokenEndpoint, strCcope, pUserNameToken, pPasswordToken);
							//strUserToken = webserCommnFunc.getUserToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientID,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
						}
						System.out.println(strstatusCode);

						//jwt Token

						String JwtToken="",finalStr="";
						if(pJwtToken.equalsIgnoreCase("Yes")){
							finalStr = webserCommnFunc.getJwtToken(strAccessTokenEndpoint,strAuthorizationEndpoint,strClientID,strClientSecret,strCcope,strRedirectUrl,pUserNameToken,pPasswordToken);
							String[] finalStr1 = finalStr.split("#fz");
							strUserToken = "Bearer" +finalStr1[0].toString();
							JwtToken = finalStr1[1].toString();
						}

						String responseString = "";

						if(isSAMLToken.equalsIgnoreCase("Yes")||isSAMLToken.equalsIgnoreCase("Y")){
							String samlAccessToken = "Bearer "+APIOperationType.samlAccessToken;
							responseString=webserCommnFunc.getJsonWebserviceCallDelete(strEndPointUrl, strClientID, samlAccessToken , strErrRspChkFlag);
						} else {
							responseString=webserCommnFunc.getJsonWebserviceCallDelete(strEndPointUrl, strClientID, strUserToken , strErrRspChkFlag);
						}						

						System.out.println(responseString);  

						webserCommnFunc.storeJsonResponse(strOutputResponseXMLFolder,strTestCase,responseString,mutpAPICount);

						String sCode = "";
						String ResponseExpectedStatusCode = webserCommnFunc.getStatusCode(sCode);
						System.out.println(ResponseExpectedStatusCode);
						String strsCode=webserCommnFunc.compareStatusCode(strstatusCode);

						if (JsonOut.equalsIgnoreCase("YES") && strstatusCode.isEmpty())
						{
							FileInputStream fis = new FileInputStream(strInputDataSheetTemp);

							wb2 = new XSSFWorkbook(fis);
							fSheet = wb2.createSheet("Output");
							fSheet = wb2.getSheet("Output");

							fSheet= webserCommnFunc.parseJsonString(responseString, fSheet);
							FileOutputStream outFile = new FileOutputStream(strInputDataSheetTemp);
							wb2.write(outFile);
							outFile.close();

						}

						else if (strstatusCode.isEmpty())
						{
							webserCommnFunc.convertStringToDOM(responseString,strWebserviceResponseXML);

							//Storing the XML (Output Response XML)Nodes Attribute information in Excel file
							String strTagNameArr[]=strResponseXMLParentTag.split("@");
							webserCommnFunc.covertDOMToExcel(strInputDataSheetTemp,strWebserviceResponseXML,strTagNameArr);

							//webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");

						}
						else{
							if(strsCode.equalsIgnoreCase(strstatusCode))
							{
								//webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "PASS");
								objReport.setValidationMessageInReport("PASS","Method createAndExecuteDelete : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 						
							}
							else
							{
								webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");
								objReport.setValidationMessageInReport("FAIL","Method createAndExecuteDelete : Expected output "+strstatusCode+ " ,Response Received "+strsCode ); 							
							}
						}

						break;
					}}
			}


			if(GETMethodRunStatudFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method createAndExecuteGet : please check test script '"+strScriptName+"' is available in the GET Worksheet" ); 							

			}	
		}
		catch (Exception e) 
		{
			//Updating the execution status column of DataSheet with status as FAIL					
			webserCommnFunc.updateExecStatusExcelGET(strDatasheet, intTestCaseRowNum, "FAIL");					
			objReport.writeStackTraceErrorInReport(e, "createAndExecuteGet");
		}
	}
}
