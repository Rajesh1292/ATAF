package GenericFunctions;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xerces.parsers.DOMParser;
import org.json.JSONException;
//import org.json.simple.JSONObject;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Reporting.Report;
import TestScriptRunner.Runner;


public class APIFunctions 
{
	String sCode = "";
	String repsonseStr="";
	public static String[] strVar = null;
	public static String[] tag = null;
	public static ArrayList<String> arrInputParm = null;
	public static Document doc = null;
	public static String strPathnew;
	public static int intOccur;
	public static String strTagWithOccurence;
	public static int intOccurUpd;
	public static int occr;
	public static String tagRemove;
	public static String strTagWithOccurenceUpd;
	public static Map<String, String> data= new HashMap<String,String>();
	static XSSFWorkbook wb=null;
	static XSSFSheet sSheet=null;
	public static boolean iSkip = true;
	public static boolean iSkipUpdate = true;
	public static ArrayList<String> urls = new ArrayList<String>();

	public static String cP = null;
	public static String nP = null;
	public static String grpId = "";
	public static String passwordType = "";
	public static int count1 = 1;
	static String jsonValue=null;
	static String key="access_token";

	private static String InputPath = null;
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	public static String TEST_XML_STRING = null;
	public static ArrayList<String> actual_values = new ArrayList<String>();

	String strTestCase = "";
	String strInputDataFolder = "";
	//String dataSheet=Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("testDataFile");
	String dataSheet=Runner.strResourceFldLoc+Runner.properties.getProperty("testDataFile");
	//String dataSheet="";
	Report objReport=new Report();



	//****************************** COMMON FUNCTIONS FOR WEB SERVICE CALL(POST)*****************************************************

	/**
	 * This method is to return the Web Service response value in String after successful GET operation on Web service Url
	 * @param response - HttpResponse object wchi contains API response value 
	 *@param strMethodName - It contains the Method Name
	 */
	public String getPOSTMethodRespVal(HttpResponse response , String strMethodName, String strErrRspChkFlag)  
	{
		String strResponsVal="";
		try 
		{

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();

			if (!(statusCode == 200 || statusCode == 201)) 
			{
				if (!(strErrRspChkFlag.equalsIgnoreCase("YES")))

				{
					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);	
					objReport.setValidationMessageInReport("FAIL","Method "+strMethodName+"(POST): Failed to generate correct API Response due to Error code "+strResponsVal);						
				} 

				else
				{
					//Get API response for status code other than 200
					objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(POST): API Error Response is generated with Error code "+statusCode);						

					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);					
				}
			}	
			else 
			{	
				//Get API response for status code 200
				objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(POST): Correct API Response is generated");					

				strResponsVal = new BasicResponseHandler().handleResponse(response);

			}

		}
		catch (Exception e) 
		{					
			objReport.writeStackTraceErrorInReport(e, "getPOSTMethodRespVal");
		}
		return strResponsVal;
	}


	/**
	 * @Name postWebserviceCall
	 * @param  strXMLInput XML input in the form of String value which will be used in the request body of the httppost object
		   @param  strWebService  Contains Web Service Url on Which POST operation will be performed
		   @param  strAccptHdrReqrdStatus contains the indicate Accept header is required in Request body of Httppost object or not( value - YES or NO)
		            YES - if Accept Header is required
		            NO -  if Accept Header is NOT required
	 */

	public String postWebserviceCall(String strXMLInput, String strWebService, String strAccptHdrReqrdStatus)
	{
		String strResponsVal="";
		try
		{
			//Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(strWebService);
			StringWriter writer = new StringWriter();

			//Add headers to the HttpPost object(postRequest)
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "text/xml");

			//Add Accept header to HttpPost object if strAccptHdrReqrdStatus value is YES
			if(strAccptHdrReqrdStatus.equalsIgnoreCase("YES"))
			{
				postRequest.addHeader("Accept", "application/xml");
			}

			// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
			writer.write(strXMLInput);
			StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
			postRequest.setEntity(userEntity);

			// Send the request; It will return the response in the form of HttpResponse object
			HttpResponse response = httpClient.execute(postRequest);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();

			//Get API response value
			HttpEntity entity = response.getEntity();
			strResponsVal = EntityUtils.toString(entity);	

			if (statusCode != 200) 
			{				
				objReport.setValidationMessageInReport("FAIL","Method postWebserviceCall(POST) : Failed to generate API Response due to Error "+strResponsVal);
			} 
			else 
			{			  			
				objReport.setValidationMessageInReport("PASS"," Method postWebserviceCall(POST) : API Response is generated successfully");							
			}

		}
		catch(Exception e) 
		{ 	
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCall(PUT)");			
		}
		return strResponsVal;
	}

	public String postWebserJWTjson(String strXMLInput,String strWebserviceURL,String strUserToken,String strClientId,String JwtToken, String certificate, String password, String strErrRspChkFlag,String jsonInput) {

		String strResponsVal="";
		try
		{
			String responseString = "";
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Define a postRequest request
			String url_Server = strWebserviceURL;
			HttpPost postRequest = new HttpPost(url_Server);
			StringWriter writer = new StringWriter();

			// HttpPost postRequest = new HttpPost (Web_Service);
			postRequest.addHeader("User-Agent"," Apache-HttpClient/4.1.1 (java 1.5)");
			
			if(jsonInput.equalsIgnoreCase("Yes")){
				postRequest.addHeader("Content-Type", "application/json");
			} else {
				postRequest.addHeader("Content-Type", "application/xml");
			}
			
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("ID_Token", JwtToken);
			postRequest.addHeader("Authorization", strUserToken);

			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size() > 0) {
//				System.out.println("The size of the keys is " + keystore.size());
//				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory = KeyManagerFactory	.getInstance("PKIX");
				keymgrfactory.init(keystore, password.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

//				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request post body
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in
				// HttpResponse object if any
				HttpResponse response = httpClient.execute(postRequest);


				// verify the valid error code first
				//int statusCode = response.getStatusLine().getStatusCode();
				//String testResponse = response.getStatusLine().getReasonPhrase();

				//System.out.println("statuscode :" + statusCode);
				//System.out.println("test: " +testResponse);

				strResponsVal=getPOSTMethodRespVal(response ,"postWebserviceCallSSLjson", strErrRspChkFlag); 

				/*						if (statusCode != 200) 
						{
							objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
						} 
						else 
						{
							//Get API response value
							strResponsVal = new BasicResponseHandler().handleResponse(response);				  			
							objReport.setValidationMessageInReport("PASS","postWebserviceCallSSLjson(POST) : API(SSL) Response is generated successfully");				
						}
				 */
			}

			else {
				objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}
		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallSSLjson(POST)");				
		}
		return strResponsVal;
	}

	/**
	 * @Name postSSLWebserviceCall
	 * @param   strXMLInput- XML input in the form of String value which will be used in the request body of the httppost object
			            strWebService - Contains Web Service Url on Which POST operation will be performed
			            StrCertificate - SSL certificate location path
			            strPassword   - Password for SSL connection
			            strAccptHdrReqrdStatus- contains the indicate Accept header is required in Request body of Httppost object or not( value - YES or NO)
		                YES - if Accept Header is required
		                NO -  if Accept Header is NOT required
	 * @description  - Perform POST activity on SSL Web service specified by'strWebService' using with/without Accept Header(based on strAccptHdrReqrdStatus value) to generate API Response in String value and also return the Response value.
	 */
	public String postSSLWebserviceCall(String strXMLInput, String strWebService,String StrCertificate, String strPassword,String strErrRspChkFlag)  
	{
		String strResponsVal="";
		try
		{
			//Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//HttpClient httpClient = HttpClientBuilder.create().build();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(strWebService);
			StringWriter writer = new StringWriter();

			//Add headers to the HttpPost object(postRequest)
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "text/xml");
			postRequest.addHeader("Accept", "application/xml");
			postRequest.addHeader("Accept-Encoding","gzip,deflate");

			// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(StrCertificate);
			keystore.load(fis, strPassword.toCharArray());
			fis.close();
			if (keystore.size() > 0) 
			{
				KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore, strPassword.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keyManagers, null, null);

				System.out.println("SSL Context established!!");

				SSLSocketFactory ssf = new SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will return the response in the form of HttpResponse object
				HttpResponse response = httpClient.execute(postRequest);

				// Verify the successful generation of API response ( 200 code indicates successful generation) and retrieve the response value						
				strResponsVal=getPOSTMethodRespVal(response ,"postSSLWebserviceCall", strErrRspChkFlag); 						

				/*		int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode != 200) 
						{
							HttpEntity entity = response.getEntity();
							strResponsVal = EntityUtils.toString(entity);
							objReport.setValidationMessageInReport("FAIL","POST Method : Failed to generate API(SSL) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
						} 
						else 
						{
							//Get API response value
							strResponsVal = new BasicResponseHandler().handleResponse(response);				  			
							objReport.setValidationMessageInReport("PASS","POST Method : API(SSL) Response is generated successfully");				
						}
				 */		

			}

			else 
			{
				objReport.setValidationMessageInReport("FAIL","POST Method : Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}		
		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCall(POST)");				
		}
		return strResponsVal;
	}

	public String postWebserviceCallSSLjson(String strXMLInput, String Web_Service,	String certificate, String password , String strErrRspChkFlag) {

		String strResponsVal="";
		try
		{
			String responseString = "";
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Define a postRequest request
			String url_Server = Web_Service;
			HttpPost postRequest = new HttpPost(url_Server);
			StringWriter writer = new StringWriter();

			// HttpPost postRequest = new HttpPost (Web_Service);
			postRequest.addHeader("User-Agent"," Apache-HttpClient/4.1.1 (java 1.5)");
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.addHeader("Accept", "application/json");

			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size() > 0) {
				System.out.println("The size of the keys is " + keystore.size());
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory = KeyManagerFactory	.getInstance("PKIX");
				keymgrfactory.init(keystore, password.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager"
						+ keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request post body
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in
				// HttpResponse object if any
				HttpResponse response = httpClient.execute(postRequest);


				// verify the valid error code first
				//int statusCode = response.getStatusLine().getStatusCode();
				//String testResponse = response.getStatusLine().getReasonPhrase();

				//System.out.println("statuscode :" + statusCode);
				//System.out.println("test: " +testResponse);

				strResponsVal=getPOSTMethodRespVal(response ,"postWebserviceCallSSLjson", strErrRspChkFlag); 

				/*						if (statusCode != 200) 
						{
							objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
						} 
						else 
						{
							//Get API response value
							strResponsVal = new BasicResponseHandler().handleResponse(response);				  			
							objReport.setValidationMessageInReport("PASS","postWebserviceCallSSLjson(POST) : API(SSL) Response is generated successfully");				
						}
				 */
			}

			else {
				objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}
		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallSSLjson(POST)");				
		}
		return strResponsVal;
	}

	public String getExternalCall(String strEndPointUrl,String strUserToken, String strClientID, String certificate , String certPassword, String jsonHeader) throws Exception 	
	{   
		String repsonseStr="";
		try 
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			//put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization",strUserToken);
			httpGet.setHeader("accept","application/json");
			httpGet.addHeader("X-IBM-Client-ID", strClientID);
			
			//To add extra headers from json file provided in data.xls
			if(!(jsonHeader.equalsIgnoreCase(""))){
				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonHeader.toString());
				Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
				Boolean getTagVal=false;
				for(Map.Entry<String,JsonElement> entry : entrySet){
					String key = entry.getKey();
					String value = jsonObject.get(key).toString();
					value = value.replaceAll("^\"|\"$", "");
					System.out.println("Key: " + key + "\tValue:" + value);
					httpGet.setHeader(key, value);
				}
			}
			
			StringWriter writer = new StringWriter();
			KeyStore keystore = KeyStore.getInstance("PKCS12");	
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, certPassword.toCharArray());
			fis.close();
			if (keystore.size()>0){
				System.out.println("The size of the keys is " + keystore.size());	
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory =  KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore,certPassword.toCharArray());
				KeyManager [] keyManagers =  keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch); 



				HttpResponse response = httpClient.execute(httpGet);

				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println(statusCode);

				//Read the response value line by line and store it in 'responseString' StringBuilder variable
				BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder responseString = new StringBuilder();
				String line = "";
				while ((line = breader.readLine()) != null) 
				{
					responseString.append(line);
				}
				//close the BufferedReader(breader) object
				breader.close();

				//Save the above created StringBuilder variable(responseString) data into string variable(strResponsVal)
				System.out.println(responseString.toString());
				return responseString.toString();
			}

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallJwt");
		}
		return null;
	}
	
	public String postWebserviceCallUserToken(String strXMLInput, String Web_Service, String strUserToken, String strClientID, String jsonInput)
	{
		String responseString="";
		try
		{

			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Define a postRequest request
			String url_Server = Web_Service;
			HttpPost postRequest = new HttpPost(url_Server);

			StringEntity contentType = new StringEntity(strXMLInput);
			if (jsonInput.equalsIgnoreCase("Yes")) {
				contentType.setContentType("application/json");
			} else {
				contentType.setContentType("application/xml");
			}
			
			postRequest.setEntity(contentType);
			//StringWriter writer = new StringWriter();

			postRequest.addHeader("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest
			.addHeader("Authorization", strUserToken);
			postRequest.addHeader("accept", "application/json");
			postRequest.addHeader("X-IBM-Client-ID", strClientID);

			// Send the request; It will immediately return the response in
			// HttpResponse object if any
			HttpResponse response = httpClient.execute(postRequest);

			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();

			//Get API response value
			responseString = new BasicResponseHandler().handleResponse(response);

			if (statusCode != 200) 
			{   
				objReport.setValidationMessageInReport("FAIL","postWebserviceCallUserToken method (POST): Failed to generate API Response due to Error code "+statusCode);
			} 
			else 
			{			  			
				objReport.setValidationMessageInReport("PASS","postWebserviceCallUserToken method (POST): API Response is generated successfully");				
			}
		}

		catch(Exception e) 
		{ 
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallUserToken");					
		}


		return responseString;
		// }
	}


	//postWebserviceCallUserToken
	/**
	 * @Name postWebserviceCallUserTokenJSON
	 * @param   strXMLInput- XML input in the form of String value which will be used in the request body of the httppost object
			            strWebService - Web Service Url
			            strUserToken - 
			            strClientID   - 
	 * @description  - Perform post activity using JSON input,UserToken, CleintID for Web service(Without SSL Connection) specified by 'strWebService' and return the response in String value.
	 */
	public String postWebserviceCallUserTokenJSON(String strXMLInput, String strWebService, String strUserToken, String strClientID)
	{
		String strResponsVal = "";
		try
		{
			// Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(strWebService);

			// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
			StringEntity jsonInput = new StringEntity(strXMLInput);
			jsonInput.setContentType("application/json");
			postRequest.setEntity(jsonInput);

			//Add headers to the HttpPost object(postRequest)
			//Add value (Specified by strUserToken) to Authorization header
			//Add value (application/json) to the accept header
			postRequest.addHeader("User-Agent",	"Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Authorization", strUserToken);
			postRequest.addHeader("accept", "application/json");
			postRequest.addHeader("X-IBM-Client-ID", strClientID);

			// Send the request; It will immediately return the response in the form HttpResponse object
			HttpResponse response = httpClient.execute(postRequest);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();

			//Get API response value
			strResponsVal = new BasicResponseHandler().handleResponse(response);

			if (statusCode != 200) 
			{   
				objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID): Failed to generate API Response due to Error code "+statusCode+"Response message is "+strResponsVal);
			} 
			else 
			{			  			
				objReport.setValidationMessageInReport("PASS","POST method (JSON input,User Token, Client ID): API Response is generated successfully");				
			}

		}
		catch(Exception e) 
		{ 
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallUserTokenJSON(POST - JSON input,User Token, Client ID)");					
		}
		return strResponsVal;
	}



	//Added by Harsit---postSSLWebserviceCallSAMLTokenJSON
	public String postSSLWebserviceCallSAMLTokenJSON(String strXMLInput, String strWebService,String StrCertificate, String strPassword,String samlAccessToken, String strClientId)
	{

		String strResponsVal = "";
		try
		{
			// Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(strWebService);

			//Add headers to the HttpPost object(postRequest)
			//Add value (Specified by strUserToken) to Authorization header
			//Add value (application/json) to the accept header
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "text/JSON");
			postRequest.addHeader("Authorization", samlAccessToken);
			postRequest.addHeader("X-IBM-Client-Id", strClientId);

			// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'		
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(StrCertificate);
			keystore.load(fis, strPassword.toCharArray());
			fis.close();
			if (keystore.size() > 0) 
			{
				KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore, strPassword.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
				StringWriter writer = new StringWriter();
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in the form HttpResponse object
				HttpResponse response = httpClient.execute(postRequest);
				System.out.println(response);
				// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
				//Get Status code
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200) 
				{   
					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);
					objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SS) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
				} 
				else 
				{	
					//Get API response value
					strResponsVal = new BasicResponseHandler().handleResponse(response);
					objReport.setValidationMessageInReport("PASS","POST method (JSON input,User Token, Client ID, Certificate, Password): API(SSL) Response is generated successfully");				
				}
			}

			else {
				objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}	

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCallUserTokenJSON(POST - JSON input,User Token, Client ID,Certificate, Password)");				
		}
		return strResponsVal;

	}

	public String putSSLWebserviceCallSAMLTokenJSON(String strXMLInput, String strWebService,String StrCertificate, String strPassword,String samlAccessToken, String strClientId)
	{

		String strResponsVal = "";
		try
		{
			// Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPut putRequest = new HttpPut(strWebService);

			//Add headers to the HttpPost object(postRequest)
			//Add value (Specified by strUserToken) to Authorization header
			//Add value (application/json) to the accept header
			putRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			putRequest.addHeader("Content-Type", "text/JSON");
			putRequest.addHeader("Authorization", samlAccessToken);
			putRequest.addHeader("X-IBM-Client-Id", strClientId);

			// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'		
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(StrCertificate);
			keystore.load(fis, strPassword.toCharArray());
			fis.close();
			if (keystore.size() > 0) 
			{
				KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore, strPassword.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
				StringWriter writer = new StringWriter();
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				putRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in the form HttpResponse object
				HttpResponse response = httpClient.execute(putRequest);
				System.out.println(response);
				// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
				//Get Status code
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200) 
				{   
					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);
					objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SS) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
				} 
				else 
				{	
					//Get API response value
					strResponsVal = new BasicResponseHandler().handleResponse(response);
					objReport.setValidationMessageInReport("PASS","POST method (JSON input,User Token, Client ID, Certificate, Password): API(SSL) Response is generated successfully");				
				}
			}

			else {
				objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}	

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCallUserTokenJSON(POST - JSON input,User Token, Client ID,Certificate, Password)");				
		}
		return strResponsVal;

	}

	public String putSSLWebserviceCall(String strXMLInput, String strWebService,String StrCertificate, String strPassword,String strErrRspChkFlag)  
	{
		String strResponsVal="";
		try
		{
			//Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();
			//HttpClient httpClient = HttpClientBuilder.create().build();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPut postRequest = new HttpPut(strWebService);
			StringWriter writer = new StringWriter();

			//Add headers to the HttpPost object(postRequest)
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");


			postRequest.addHeader("Content-Type", "text/xml");
			postRequest.addHeader("Accept", "application/xml");	

			postRequest.addHeader("Accept-Encoding","gzip,deflate");

			// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(StrCertificate);
			keystore.load(fis, strPassword.toCharArray());
			fis.close();
			if (keystore.size() > 0) 
			{
				KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore, strPassword.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keyManagers, null, null);

				System.out.println("SSL Context established!!");

				SSLSocketFactory ssf = new SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will return the response in the form of HttpResponse object
				HttpResponse response = httpClient.execute(postRequest);

				// Verify the successful generation of API response ( 200 code indicates successful generation) and retrieve the response value						
				strResponsVal=getPOSTMethodRespVal(response ,"postSSLWebserviceCall", strErrRspChkFlag); 						

			}

			else 
			{
				objReport.setValidationMessageInReport("FAIL","POST Method : Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}		
		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCall(POST)");				
		}
		return strResponsVal;
	}

	//----------------

	/**
	 * @Name postSSLWebserviceCallUserTokenJSON
	 * @param   strXMLInput- XML input in the form of String value which will be used in the request body of the httppost object
			            strWebService - Web Service Url
			           StrCertificate - SSL certificate location path
			            strPassword   - Password for SSL connection
			            strUserToken - 
			            strClientID   - 
	 * @description  - Perform post activity using JSON input,UserToken, CleintID for SSL Web service specified by 'strWebService' and return the response in String value.
	 */
	public String postSSLWebserviceCallUserTokenJSON(String strXMLInput, String strWebService,String StrCertificate, String strPassword,String strUserToken, String strClientId, String jsonInput)
	{
		String strResponsVal = "";
		try
		{
			// Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(strWebService);

			//Add headers to the HttpPost object(postRequest)
			//Add value (Specified by strUserToken) to Authorization header
			//Add value (application/json) to the accept header
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			
			if(jsonInput.equalsIgnoreCase("Yes")){
				postRequest.addHeader("Content-Type", "text/json");
			} else {
				postRequest.addHeader("Content-Type", "text/xml");
			}
			
			postRequest.addHeader("Authorization", strUserToken);
			postRequest.addHeader("X-IBM-Client-Id", strClientId);

			// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'		
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(StrCertificate);
			keystore.load(fis, strPassword.toCharArray());
			fis.close();
			if (keystore.size() > 0) 
			{
				KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore, strPassword.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
				StringWriter writer = new StringWriter();
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in the form HttpResponse object
				HttpResponse response = httpClient.execute(postRequest);

				// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
				//Get Status code
				int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200) 
				{   
					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);
					objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SS) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
				} 
				else 
				{	
					//Get API response value
					strResponsVal = new BasicResponseHandler().handleResponse(response);
					objReport.setValidationMessageInReport("PASS","POST method (JSON input,User Token, Client ID, Certificate, Password): API(SSL) Response is generated successfully");				
				}
			}

			else {
				objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}	

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCallUserTokenJSON(POST - JSON input,User Token, Client ID,Certificate, Password)");				
		}
		return strResponsVal;
	}

	public String postWebserviceHeaderCall(String strXMLInput,	String Web_Service) 
	{
		String strResponsVal = "";
		try
		{
			String responseString;
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Define a postRequest request
			String url_Server = Web_Service;
			HttpPost postRequest = new HttpPost(url_Server);
			StringWriter writer = new StringWriter();

			postRequest.addHeader("User-Agent",	"Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "text/xml");
			postRequest.addHeader("Accept", "application/xml");
			// Set the request post body
			writer.write(strXMLInput);
			StringEntity userEntity = new StringEntity(writer.getBuffer()
					.toString());
			postRequest.setEntity(userEntity);

			// Send the request; It will immediately return the response in
			// HttpResponse object if any
			HttpResponse response = httpClient.execute(postRequest);

			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();

			//Get API response value
			strResponsVal = new BasicResponseHandler().handleResponse(response);

			if (statusCode != 200) 
			{   
				objReport.setValidationMessageInReport("FAIL","postWebserviceHeaderCall (POST): Failed to generate API Response due to Error code "+statusCode);
			} 
			else 
			{			  			
				objReport.setValidationMessageInReport("PASS","postWebserviceHeaderCall (POST): API Response is generated successfully");				
			}

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "postWebserviceHeaderCall");				
		}

		return strResponsVal;
	}




	public String postWebserviceCallSSLwithCDATA(String strXMLInput,String Web_Service, String certificate, String password) throws Exception 	
	{   
		String ResponseString1="";
		String responseString="";
		try 
		{

			DefaultHttpClient httpClient = new DefaultHttpClient();

			//Define a postRequest request
			String url_Server=Web_Service;
			HttpPost postRequest = new HttpPost(url_Server);
			StringWriter writer = new StringWriter();

			//HttpPost postRequest = new HttpPost (Web_Service);
			postRequest.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "text/xml");

			KeyStore keystore = KeyStore.getInstance("PKCS12");	
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size()>0){
				System.out.println("The size of the keys is " + keystore.size());	
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory =  KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore,password.toCharArray());
				KeyManager [] keyManagers =  keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				//Set the request post body
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				//Send the request; It will immediately return the response in HttpResponse object if any
				//NodeList nodes = doc.getElementsByTagName("Q5:straightPullCMSContentsResponse");
				HttpResponse response = httpClient.execute(postRequest);
				NodeList nodes = doc.getElementsByTagName("CDATA");


				//verify the valid error code first
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("statuscode :"+statusCode);

				if (statusCode != 200) 
				{
					responseString = new BasicResponseHandler().handleResponse(response);
					//responseString=result.toString();
					System.out.println("Failed with HTTP error code : " + statusCode);
				}
				else
				{
					responseString = new BasicResponseHandler().handleResponse(response);
					//responseString=result.toString();
					//System.out.println("response string  :"+responseString);
					ResponseString1 = org.apache.commons.lang3.StringEscapeUtils.unescapeXml(responseString);
					System.out.println("response string with CDATA  :"+ResponseString1);
				}
			} 
			//org.apache.commons.lang3.
			else
			{
				System.out.println("No keys found. Please validate the certificate used");
			}

		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallSSLwithCDATA");
		}
		return ResponseString1;
	}

	//******************************************************************************************************************************

	// **************************************Methods Related GET Operation******************************************

	/**
	 * This method is to return the Web Service response value in String after successful GET operation on Web service Url
	 * @param httpGet - HttpGet class object containing the request body(Ex Authorization,aemultipath header information) which will be used to generate API response
	 *@param strMethodName - It contains the Method Name
	 */
	public String getMethodRespVal(HttpGet httpGet , String strMethodName, String strErrRspChkFlag)  
	{
		String strResponsVal = "";
		try 
		{
			//Create DefaultHttpClient object
			DefaultHttpClient Client = new DefaultHttpClient();
			// Send the request; It will immediately return the response in the form of HttpResponse object
			HttpResponse response = Client.execute(httpGet);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();


			if (!(statusCode == 200 || statusCode == 201))
			{
				if (!(strErrRspChkFlag.equalsIgnoreCase("YES")))
				{
					
					objReport.setValidationMessageInReport("FAIL","Method "+strMethodName+"(GET): Failed to generate correct API Response due to Error code "+statusCode);						
				} 

				else
				{
					objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(GET): API Error Response is generated with Error code "+statusCode);						

				}
			}	
			else 
			{	
				//Get API response	
				objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(GET): Correct API Response is generated");


			}

			//Read the response value line by line and store it in 'responseString' StringBuilder variable
			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = "";
			while ((line = breader.readLine()) != null) 
			{
				responseString.append(line);
			}
			//close the BufferedReader(breader) object
			breader.close();

			//Save the above created StringBuilder variable(responseString) data into string variable(strResponsVal)
			strResponsVal = responseString.toString();
		}
		catch (Exception e) 
		{					
			objReport.writeStackTraceErrorInReport(e, "getMethodRespVal");
		}
		return strResponsVal;
	}
	/**
	 * Perform GET activity in the Web service Url (specified by strEndPointUrl) using  Authorization(specified by strUserToken) and aemultipath(Specified by strAeMultiPath) header values
	 * @param strEndPointUrl - Contains WebService Url
			  @param strUserToken - Contains Authorization header value
			  @param strAeMultiPath - Contains aemultipath header value
	 */
	public String getWebserviceCall(String strEndPointUrl, String strUserToken,	String strAeMultiPath, String strErrRspChkFlag)  
	{
		String strResponsVal = "";
		try 
		{

			//Create HttpGet object for Webservice (Specified by strEndPointUrl)
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// Perform below activity if strAeMultiPath string value contains
			// the decimal point with trailing zeos (Ex: 1.0)
			if (strAeMultiPath.contains(".")) {
				int intdelLoc = strAeMultiPath.indexOf(".");
				strAeMultiPath = strAeMultiPath.substring(0, intdelLoc);
			}

			//Add headers to the HttpGet object(httpGet) using the values specified by strUserToken,strAeMultiPath
			httpGet.setHeader("Authorization", strUserToken);
			httpGet.setHeader("aemultipath", strAeMultiPath);
			httpGet.setHeader("Content-Type", "text/xml");
			httpGet.setHeader("Accept", "application/xml");

			// Send the request; It will immediately return the response in the form of HttpResponse object
			strResponsVal=getMethodRespVal(httpGet,"getWebserviceCall",strErrRspChkFlag );

		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCall");					
		}
		return strResponsVal;
	}

	/**
	 * This method is to Perform GET activity in the Web service Url (specified by strEndPointUrl) using Authorization(specified by strUserToken) and aemultipath(Specified by strAeMultiPath) header values
	 * @param strEndPointUrl - Contains WebService Url
			   @param strUserToken - Contains Authorization header value
			   @param strAeMultiPath - Contains aemultipath header value
	 */
	public String getWebserviceCallAuth(String strEndPointUrl,	String strUserToken, String strAeMultiPath , String strErrRspChkFlag) 
	{
		String strResponsVal="";
		try 
		{
			//DefaultHttpClient Client = new DefaultHttpClient();
			System.out.println(strEndPointUrl);

			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// Perform below activity if strAeMultiPath string value contains
			// the decimal point with trailing zeros (Ex: 1.0)
			if (strAeMultiPath.contains(".")) {
				int intdelLoc = strAeMultiPath.indexOf(".");
				strAeMultiPath = strAeMultiPath.substring(0, intdelLoc);
			}

			// put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization", strUserToken);
			httpGet.setHeader("Aemultipath", strAeMultiPath);

			// Get API response
			strResponsVal=getMethodRespVal(httpGet,"getWebserviceCallAuth" , strErrRspChkFlag );

		} 
		catch (Exception e) 					
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallAuth");
		}
		return strResponsVal;
	}

	/**
	 * This method is to create required Endpoint url using the values related to 'strTestCaseName' available in the worksheet(strWorksheet) of the Data Sheet(strDataSheet).
	 * @param strDataSheet - Input Data Sheet path
				   @param strTestCaseName - Contains Test Case name 
				   @param strWorksheet - Contains Worksheet name
	 */
	public String getWebserviceEndPointUrl(String strDataSheet,String strTestCaseName, String strWorksheet, int count)
	{
		// TODO Insert code here
		String strReqEndPntUrl="";
		try 
		{
			Boolean TestCaseFoundFag = false;

			//Create workbook object for excel file located in the path  specified by strDataSheet
			FileInputStream io = new FileInputStream(strDataSheet);
			XSSFWorkbook wb = new XSSFWorkbook(io);

			//Create work sheet object for the work sheet specified by 'strWorksheet'
			XSSFSheet sheet = wb.getSheet(strWorksheet);

			//Define string variable to store the endpoint urls and delimiter value
			String strInputUrl = "";
			String strDelimeter = "";

			//Get used row count
			int rowNum = sheet.getLastRowNum() + 1;

			//Iterate though all the rows till Test case match is found related to value specified by 'strTestCaseName'
			for (int i = 1; i < rowNum; i++) 
			{
				//Get the String value (Test case name) specified in the 1st column 
				String strTestCase = sheet.getRow(i).getCell(0).getStringCellValue().trim();

				//Compare the retrieved column value(strTestCase) with the value specified by 'strTestCaseName'. Perform the conditional statements if both value are same
				if (strTestCase.equalsIgnoreCase(strTestCaseName)) 
				{
					if (count>1){
						i=i+(count-1);
						strTestCase = sheet.getRow(i).getCell(0).getStringCellValue().trim();
					}
					//Get used column count for the selected row object
					int colNum = sheet.getRow(i).getLastCellNum();

					//Get the Url value mentioned in the 2nd column of the row containing the required test case
					strReqEndPntUrl = sheet.getRow(i).getCell(1).getStringCellValue().trim();

					boolean urlCreation = false;

					//Append values to base url by fetching it from previous response
					if(strReqEndPntUrl.contains("#") && APICreateAndExecute.mutpAPICount>1){
						int startIndex = strReqEndPntUrl.indexOf("#");
						int lastIndex = strReqEndPntUrl.lastIndexOf("#");

						String tagDetails = strReqEndPntUrl.substring(startIndex+1, lastIndex);
						String ArrTagname[] = tagDetails.split(";");
						int outputIndex = Integer.parseInt(ArrTagname[0]);
						int index = Integer.parseInt(ArrTagname[2]);
						strReqEndPntUrl = strReqEndPntUrl.replace("#"+tagDetails+"#", multipleConcatURL(ArrTagname[1], strTestCaseName, outputIndex, index));
						urlCreation = true;
					}

					//Append values to base url by fetching it from previous response
					if (colNum > 2 && APICreateAndExecute.mutpAPICount>1 && urlCreation==false) {
						int noOfCol = colNum - 2;
						String parameter[] = new String[noOfCol];

						for (int k = 0, j = 2; k < noOfCol && j <= colNum; k++, j++) {
							parameter[k] = sheet.getRow(i).getCell(j).getStringCellValue();

							//Checking if we need to fetch values from previous API or directly add the values to base url

							int colonCount = parameter[k].length() - parameter[k].replaceAll(";","").length();
							if(parameter[k].contains(";") && colonCount==2) {
								String tagDetails = parameter[k].split("=")[1];
								String tagName = tagDetails.split(";")[1];
								int outputIndex = Integer.parseInt(tagDetails.split(";")[0]);
								int index = Integer.parseInt(tagDetails.split(";")[2]);
								parameter[k] = parameter[k].replace(tagDetails, multipleConcatURL(tagName, strTestCaseName, outputIndex, index));	
							}

							System.out.println(parameter[k]);
						}

						for (int j = 0; j < noOfCol; j++) {	
							java.net.URI uri = new URIBuilder(strReqEndPntUrl).addParameter(parameter[j].split("=")[0]
									, parameter[j].split("=")[1]).build();
							strReqEndPntUrl = uri.toString();
						}
					}

					/*if (colNum > 2) {
						//Get the String delimiter value from the 3rd column if column cell type is Numeric
						if (sheet.getRow(i).getCell(2).getCellType() == 0) {
							strDelimeter = String.valueOf(sheet.getRow(i).getCell(2).getNumericCellValue());
						} else {
							//Get the String delimiter value from the 3rd column if column cell type is String
							strDelimeter = sheet.getRow(i).getCell(2).getStringCellValue().trim();
						}

						//Perform the below operation for column values available from 4th column to last used column of selected row object
						for (int j = 3; j < colNum; j++) {
							//Get the String column value from the column (j+1)if column cell type is Numeric
							if (sheet.getRow(i).getCell(j).getCellType() == 0) {
								strInputUrl = Integer.toString((int) sheet.getRow(i).getCell(j).getNumericCellValue());
							} else {
								//Get the String column value from the column (j+1)if column cell type is String
								strInputUrl = sheet.getRow(i).getCell(j).getStringCellValue().trim();
							}

							//Append the Column value(if selected column location is greater than 4) of the selected row to the Endpoint url value retrieved from the 2nd column(strReqEndPntUrl) and appended value is separated by delimiter value retrieved from 3rd column(strDelimeter) 
							if (j > 3) {
								strReqEndPntUrl = strReqEndPntUrl + strDelimeter+ strInputUrl;
							} else {
								//Append the Column value(if column location is less than 4) of the selected row to the Endpoint url value retrieved from the 2nd column(strReqEndPntUrl)
								strReqEndPntUrl = strReqEndPntUrl + "/" + strInputUrl;
							}
						}
					}*/

					TestCaseFoundFag = true;
					break;
				}
			}

			if (TestCaseFoundFag == false) {
				objReport.setValidationMessageInReport("FAIL","Method getWebserviceEndPointUrl: TestCase '"+strTestCaseName+"' is not found in worksheet '"+strWorksheet +"' of Input Data Sheet '"+strDataSheet+"'"); 			
			}
			else
			{
				objReport.setValidationMessageInReport("PASS","Method getWebserviceEndPointUrl: WebService Enpoint Url is created successfully for the Test Case '"+strTestCaseName+"'.Endpoint url is '"+strReqEndPntUrl+"'"); 			

			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceEndPointUrl");					
		}
		return strReqEndPntUrl;
	}



	public String multipleConcatURL(String tagName, String strTestCaseName, int outputIndex, int index) throws Exception {
		String strDataSheet=Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strTestCaseName+"_Data_Sheet.xlsx";
		if(outputIndex>1){
			strDataSheet=Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strTestCaseName+"_Data_Sheet"+(outputIndex-1)+".xlsx";
		}


		//Create workbook object for excel file located in the path  specified by strDataSheet
		FileInputStream io = new FileInputStream(strDataSheet);
		XSSFWorkbook wb = new XSSFWorkbook(io);

		//Create work sheet object for the work sheet specified by 'strWorksheet'
		XSSFSheet sheet = wb.getSheet("Output");

		int rowNum = sheet.getLastRowNum()+1;
		int flag=0;
		int fld_loc = 1;
		String tagValue="";
		for	(fld_loc=1;fld_loc<rowNum;fld_loc++)
		{
			if(sheet.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(tagName))
			{
				flag++;
				if(flag==index){
					tagValue = sheet.getRow(fld_loc).getCell(1).getStringCellValue();
					break;
				}
			}							
		}

		return tagValue;

	}

	/**
	 * This method is to perform GET activity in the Web service Url (specified by strEndPointUrl) without authorization and aemultipath header
	 * @param strEndPointUrl - Contains WebService Url
	 */
	public String getWebserviceCallWithoutTokenAemultipath(String strEndPointUrl , String strErrRspChkFlag) 
	{
		String strResponsVal="";
		try
		{
			//Create HttpGet object for Webservice (Specified by strEndPointUrl)
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			//Add headers to the HttpGet object(httpGet)
			httpGet.setHeader("Accept", "application/xml");

			// Get API response
			strResponsVal=getMethodRespVal(httpGet,"getWebserviceCallWithoutTokenAemultipath" , strErrRspChkFlag);

		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallWithoutTokenAemultipath");
		}
		return strResponsVal;
	}

	// getWebserviceCallWithoutUserToken
	/**
	 * This method is to perform GET activity in the Web service Url (specified by strEndPointUrl) using  aemultipath(Specified by strAeMultiPath) header value
	 * @param strEndPointUrl - Contains WebService Url
				   @param strAeMultiPath - Contains aemultipath header value
	 */
	public String getWebserviceCallWithoutUserToken(String strEndPointUrl,String strAeMultiPath , String strErrRspChkFlag)
	{
		String strResponsVal="";
		try 
		{
			//DefaultHttpClient Client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// Perform below activity if strAeMultiPath string value contains
			// the decimal point with trailing zeos (Ex: 1.0)
			if (strAeMultiPath.contains(".")) {
				int intdelLoc = strAeMultiPath.indexOf(".");
				strAeMultiPath = strAeMultiPath.substring(0, intdelLoc);
			}

			// put all of the header parameters in one string using setHeader
			httpGet.setHeader("Accept", "application/xml");

			//Get API response
			strResponsVal=getMethodRespVal(httpGet,"getWebserviceCallWithoutUserToken" ,strErrRspChkFlag );
		} 
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallWithoutUserToken");
		}
		return strResponsVal;
	}

	/**
	 * This method is to perform GET activity in the Web service Url (specified by strEndPointUrl) using  Authorization(specified by strUserToken), aemultipath(Specified by strAeMultiPath) header values and Client Id (specified by strClientID) values
	 * @param strEndPointUrl - Contains WebService Url
				   @param strUserToken - Contains Authorization header value
				   @param strAeMultiPath - Contains aemultipath header value
				   @param strClientID - Client id value
	 */
	public String getWebserviceCall(String strEndPointUrl, String strUserToken,	String strAeMultiPath, String strClientID,String strErrRspChkFlag) 
	{
		String strResponsVal="";
		try {

			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// Perform below activity if strAeMultiPath string value contains
			// the decimal point with trailing zeos (Ex: 1.0);
			if (strAeMultiPath.contains(".")) {
				int intdelLoc = strAeMultiPath.indexOf(".");
				strAeMultiPath = strAeMultiPath.substring(0, intdelLoc);
			}

			// put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization", strUserToken);
			httpGet.setHeader("Accept", "application/xml");
			httpGet.setHeader("X-IBM-Client-Id", strClientID);

			//Get API response
			strResponsVal=getMethodRespVal(httpGet,"getWebserviceCall",strErrRspChkFlag);
		} 
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCall");
		}
		return strResponsVal;
	}

	//Generating response using SAMLAccessToken and ClientID
	public String getWebserviceCallSAML(String strEndPointUrl, String samlAccessToken, String strClientID, String strErrRspChkFlag) {
		String repsonseStr="";
		String refreshToken="";
		try {

			//DefaultHttpClient Client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			if(samlAccessToken.contains("@;@")){
				String[] tokens = samlAccessToken.split("@;@");
				samlAccessToken = tokens[0];
				refreshToken = tokens[1];
				httpGet.setHeader("id_token",refreshToken);
			}
			
			//put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization",samlAccessToken);
			httpGet.setHeader("accept","application/json");
			httpGet.setHeader("X-IBM-Client-Id",strClientID);

			//Get API response
			repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallSAML" , strErrRspChkFlag);
			System.out.println(repsonseStr);
		} catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallSAML");
		}
		return repsonseStr;
	}

	//Generating response using Access and JWT
	/**
	 * this method performs GET activity in the Web service Url (specified by strEndPointUrl) using  Authorization(specified by strUserToken), Client Id (specified by Client ID) values and JWT Token(Specified by JwtToken) values
	 * @param strEndPointUrl - Contains WebService Url
				   @param strUserToken - Contains Authorization header value
				   @param strClientID - Contains aemultipath header value
				   @param JwtToken - Contains JWT Token
	 */
	public String getWebserviceCallJwt(String strEndPointUrl,String strUserToken, String strClientID, String JwtToken , String strErrRspChkFlag) throws Exception 	
	{   
		String repsonseStr="";
		try 
		{
			//DefaultHttpClient Client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			//put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization",strUserToken);
			httpGet.setHeader("accept","application/json");
			httpGet.setHeader("X-IBM-Client-Id",strClientID);
			httpGet.setHeader("ID_Token",JwtToken);

			//Get API response
			repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallJwt" , strErrRspChkFlag);						
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallJwt");
		}
		return repsonseStr;
	}


	public String getExternalCall(String strEndPointUrl,String strUserToken, String strClientID, String certificate , String certPassword) throws Exception 	
	{   
		String repsonseStr="";
		try 
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			//put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization",strUserToken);
			httpGet.setHeader("accept","application/json");
			StringWriter writer = new StringWriter();
			KeyStore keystore = KeyStore.getInstance("PKCS12");	
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, certPassword.toCharArray());
			fis.close();
			if (keystore.size()>0){
				System.out.println("The size of the keys is " + keystore.size());	
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory =  KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore,certPassword.toCharArray());
				KeyManager [] keyManagers =  keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch); 



				HttpResponse response = httpClient.execute(httpGet);

				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println(statusCode);

				//Read the response value line by line and store it in 'responseString' StringBuilder variable
				BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder responseString = new StringBuilder();
				String line = "";
				while ((line = breader.readLine()) != null) 
				{
					responseString.append(line);
				}
				//close the BufferedReader(breader) object
				breader.close();

				//Save the above created StringBuilder variable(responseString) data into string variable(strResponsVal)
				System.out.println(responseString.toString());
				return responseString.toString();
			}

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallJwt");
		}
		return null;
	}

	//special header
	/**
	 * This method is to perform GET activity in the Web service Url (specified by strEndPointUrl) using the below parameters
	 * @param strEndPointUrl - Contains WebService Url
				   @param strUserToken - Contains Token value
				   @param appId - Contains 
				   @param userContxt - Contains 
				   @param transId - 
				   @param Oid -
				   @param Version - 
				   @param action -  
	 */
	public String getWebserviceCallSplHeader(String strEndPointUrl, String strUserToken, String appId, String userContxt, String transId, String Oid, String Version, String action , String strErrRspChkFlag) 
	{
		String repsonseStr="";
		try {

			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization", strUserToken);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("eieheaderapplicationidentifier", appId);
			httpGet.setHeader("eieheaderusercontext", userContxt);
			httpGet.setHeader("eieheadertransactionid", transId);
			httpGet.setHeader("eieheaderorchestratingapplicationidentifier", Oid);
			httpGet.setHeader("eieheaderversion", Version);
			httpGet.setHeader("eieheaderaction", action);

			//Get API response					
			repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallSplHeader", strErrRspChkFlag);

		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallSplHeader");
		}
		return repsonseStr;
	}

	/**
	 * Perform GET activity in the Web service Url (specified by strEndPointUrl) using  User name(specified by pUserName)
	 * @param strEndPointUrl- Contains WebService Url
				   @param pUserName - Contains user name
	 */
	//Get Method without Aemultipath and token. Username is used inplace of Token
	public String getWebserviceCallWithoutMultipath(String strEndPointUrl, String pUserName , String strErrRspChkFlag) throws Exception   
	{   
		String repsonseStr="";

		try 
		{
			//DefaultHttpClient Client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			httpGet.setHeader("accept","application/xml");
			httpGet.setHeader("userName",pUserName);

			//Get API response
			repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallWithoutMultipath" , strErrRspChkFlag);						
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallWithoutMultipath");
		}

		return repsonseStr;
	}

	/**
	 * Perform GET activity in the Web service Url (specified by strEndPointUrl) using  Certificate (specified by certificate)and password (specified by password)
	 * @param strEndPointUrl - Contains WebService Url
				   @param certificate - Contains certificate
				   @param password - Contains password
	 */
	public String getWebserviceCallWithCertAndPassword(String strEndPointUrl, String certificate, String password , String strErrRspChkFlag) throws Exception   
	{   
		String repsonseStr = "";
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

//			httpGet.setHeader("Accept","application/xml");	

			KeyStore keystore = KeyStore.getInstance("PKCS12");	
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size()>0){
				System.out.println("The size of the keys is " + keystore.size());	
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory =  KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore,password.toCharArray());
				KeyManager [] keyManagers =  keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				SSLSocketFactory ssf = new SSLSocketFactory(sslContext, new StrictHostnameVerifier());
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				//Get API response
				repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallWithCertAndPassword" , strErrRspChkFlag);
			}
		}

		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallWithCertAndPassword");
		}
		return repsonseStr;

	}
	
	public String getwellBeingAPIToken(String urlAccessToken) throws InterruptedException{

		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver","C:\\Data\\GITRepositoryNew\\DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		options.addArguments("headless");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

		driver.get(urlAccessToken);
		
		
		String strURL = driver.getCurrentUrl();
		
		Thread.sleep(3000);
		String a[] = strURL.split("&expires_in");
		String wbAccessToken=a[0].substring(37, a[0].length());
	 	
		
		String b[] = strURL.split("&id_token=");
		String wbTokenId=b[1].replace("&state=connecticut", "");

		return wbAccessToken+"#fz"+wbTokenId;
		
	
	}
	
	public String getWellbeingAPI(String strEndPointUrl,String strUserToken, String JwtToken , String strErrRspChkFlag) throws Exception 	
	{   
		String repsonseStr="";
		try 
		{
			//DefaultHttpClient Client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			//put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization",strUserToken);
			httpGet.setHeader("accept","application/json");
			httpGet.setHeader("ID_Token",JwtToken);

			//Get API response
			repsonseStr=getMethodRespVal(httpGet,"getWebserviceCallJwt" , strErrRspChkFlag);						
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceCallJwt");
		}
		return repsonseStr;
	}
	
	public String putWebserviceCallToken(String strAccptHdr, String XML_String, String strWebserviceURL, String strUserToken , String strErrRspChkFlag)
	{
		int responseCode = -1;
		String output = "";
		String finalOutput = "";
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPut request = new HttpPut(strWebserviceURL);
			StringEntity params = new StringEntity(XML_String,"UTF-8");
			
			params.setContentType("application/x-www-form-urlencoded");
//			params.setContentEncoding("UTF-8");
			
			request.addHeader("Authorization", strUserToken);
			
			if(strAccptHdr.equalsIgnoreCase("xml"))
			{
				request.addHeader("Content-Type", "application/xml");
				request.addHeader("Accept", "application/xml");
			}
			else if(strAccptHdr.equalsIgnoreCase("json"))
			{
				request.addHeader("Content-Type", "application/json");
				request.addHeader("Accept", "application/json");
			}
			
			request.setEntity(params);
		
			// Send the request; It will immediately return the response in the form HttpResponse object
			HttpResponse response = httpClient.execute(request);
			System.out.println(response);
			
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();
			
			
	


			
			//HttpResponse response = httpClient.execute(request);
				finalOutput = getPUTMethodRespVal(response , "putWebserviceCallJSONToken",strErrRspChkFlag);

			// verify the valid error code first
				//		int statusCode = response.getStatusLine().getStatusCode();
//
						//Get API response value
				//		String responseString = new BasicResponseHandler().handleResponse(response);

				//		if (statusCode != 200) 
				//		{   
				//			objReport.setValidationMessageInReport("FAIL","postWebserviceCallUserToken method (POST): Failed to generate API Response due to Error code "+statusCode);
				//		} 
				//		else 
				//		{			  			
				//			objReport.setValidationMessageInReport("PASS","postWebserviceCallUserToken method (POST): API Response is generated successfully");				
				//		}

		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "putWebserviceCallJSONToken");
		} 					
		return finalOutput;
	}
	
	public String putWebserJWTjson(String strXMLInput,String strWebserviceURL,String strUserToken,String strClientId,String JwtToken, String certificate, String password, String strErrRspChkFlag) {

		String strResponsVal="";
		try
		{
			String responseString = "";
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Define a postRequest request
			String url_Server = strWebserviceURL;
			HttpPut postRequest = new HttpPut(url_Server);
			StringWriter writer = new StringWriter();

			// HttpPost postRequest = new HttpPost (Web_Service);
			postRequest.addHeader("User-Agent"," Apache-HttpClient/4.1.1 (java 1.5)");
			postRequest.addHeader("Content-Type", "application/json");
//			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("ID_Token", JwtToken);
			postRequest.addHeader("Authorization", strUserToken);

			KeyStore keystore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size() > 0) {
				System.out.println("The size of the keys is " + keystore.size());
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory = KeyManagerFactory	.getInstance("PKIX");
				keymgrfactory.init(keystore, password.toCharArray());
				KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager"
						+ keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch);

				// Set the request post body
				writer.write(strXMLInput);
				StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
				postRequest.setEntity(userEntity);

				// Send the request; It will immediately return the response in
				// HttpResponse object if any
				HttpResponse response = httpClient.execute(postRequest);


				// verify the valid error code first
				//int statusCode = response.getStatusLine().getStatusCode();
				//String testResponse = response.getStatusLine().getReasonPhrase();

				//System.out.println("statuscode :" + statusCode);
				//System.out.println("test: " +testResponse);

				strResponsVal=getPOSTMethodRespVal(response ,"postWebserviceCallSSLjson", strErrRspChkFlag); 

				/*						if (statusCode != 200) 
						{
							objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) Response due to Error code "+statusCode+"Response message is "+strResponsVal);
						} 
						else 
						{
							//Get API response value
							strResponsVal = new BasicResponseHandler().handleResponse(response);				  			
							objReport.setValidationMessageInReport("PASS","postWebserviceCallSSLjson(POST) : API(SSL) Response is generated successfully");				
						}
				 */
			}

			else {
				objReport.setValidationMessageInReport("FAIL","postWebserviceCallSSLjson(POST) : Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
			}
		}
		catch(Exception e)
		{		
			objReport.writeStackTraceErrorInReport(e, "postWebserviceCallSSLjson(POST)");				
		}
		return strResponsVal;
	}
	
	//For Post External calls
		public String postExternalcall(String strXMLInput, String strWebService,String StrCertificate, String strPassword, String strUserToken, String jsonInput, String strErrRspChkFlag)
		{

			String strResponsVal = "";
			try
			{
				// Create DefaultHttpClient object
				DefaultHttpClient httpClient = new DefaultHttpClient();

				// Create HttpPost object for the Web service specified by 'strWebService'
				HttpPost postRequest = new HttpPost(strWebService);

				//Add headers to the HttpPost object(postRequest)
				//Add value (Specified by strUserToken) to Authorization header
				//Add value (application/json) to the accept header
				postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				if(jsonInput.equalsIgnoreCase("Yes")){
					postRequest.addHeader("Content-Type", "application/json");
				} else {
					postRequest.addHeader("Content-Type", "application/xml");
				}
				postRequest.addHeader("Authorization", strUserToken);

				// Perform SSL connection using the Certificate located in the location mentioned in 'certificate' and password specified by 'password'		
				KeyStore keystore = KeyStore.getInstance("PKCS12");
				FileInputStream fis = new FileInputStream(StrCertificate);
				keystore.load(fis, strPassword.toCharArray());
				fis.close();
				if (keystore.size() > 0) 
				{
					KeyManagerFactory keymgrfactory = KeyManagerFactory.getInstance("PKIX");
					keymgrfactory.init(keystore, strPassword.toCharArray());
					KeyManager[] keyManagers = keymgrfactory.getKeyManagers();

					SSLContext sslContext = SSLContext.getInstance("TLS");

					sslContext.init(keyManagers, null, null);
					System.out.println("SSL Context established!!");
					SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					Scheme sch = new Scheme("https", 443, ssf);
					httpClient.getConnectionManager().getSchemeRegistry().register(sch);

					// Set the request body(value specified in 'strXMLInput') of the HttpPost object(postRequest)
					StringWriter writer = new StringWriter();
					writer.write(strXMLInput);
					StringEntity userEntity = new StringEntity(writer.getBuffer().toString());
					postRequest.setEntity(userEntity);

					// Send the request; It will immediately return the response in the form HttpResponse object
					HttpResponse response = httpClient.execute(postRequest);
					System.out.println(response);
					// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
					//Get Status code
					strResponsVal=getPOSTMethodRespVal(response ,"postSSLWebserviceCall", strErrRspChkFlag);
				}

				else {
					objReport.setValidationMessageInReport("FAIL","POST method (JSON input,User Token, Client ID,Certificate, Password): Failed to generate API(SSL) response as No keys found. Please validate the certificate used"); //LOG ERROR NEW			
				}	

			}
			catch(Exception e)
			{
				objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCallUserTokenJSON(POST - JSON input,User Token, Client ID,Certificate, Password)");				
			}
			return strResponsVal;

		}

	/**
	 * Perform GET activity in the Web service Url (specified by strEndPointUrl) using  Client ID (specified by strClientID) using AeMultiPath (specified by strAeMultiPath) and UserToken (specified by strUserToken)
	 * @param strEndPointUrl - Contains WebService Url
				   @param strClientID - Contains Client ID
				   @param strAeMultiPath - Contains multipath
				   @param strUserToken - Contains User Token
	 */
	public String getJsonWebserviceCall(String strEndPointUrl, String strClientID ,String strAeMultiPath, String strUserToken , String strErrRspChkFlag,String jsonHeader)
	{
		String strResponsVal="";
		try 
		{
			HttpGet httpGet = new HttpGet(strEndPointUrl);

			// Perform below activity if strAeMultiPath string value contains
			// the decimal point with trailing zeos (Ex: 1.0)
			if (strAeMultiPath.contains(".")) {
				int intdelLoc = strAeMultiPath.indexOf(".");
				strAeMultiPath = strAeMultiPath.substring(0, intdelLoc);
			}

			//To add extra headers from json file provided in data.xls
			if(!(jsonHeader.equalsIgnoreCase(""))){
				JsonParser jsonParser = new JsonParser();
				JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonHeader.toString());
				Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
				Boolean getTagVal=false;
				for(Map.Entry<String,JsonElement> entry : entrySet){
					String key = entry.getKey();
					String value = jsonObject.get(key).toString();
					value = value.replaceAll("^\"|\"$", "");
					System.out.println("Key: " + key + "\tValue:" + value);
					httpGet.setHeader(key, value);
				}
			}

			// put all of the header parameters in one string using setHeader
			httpGet.setHeader("Authorization", strUserToken);
			httpGet.setHeader("Accept", "application/json");
			httpGet.setHeader("X-IBM-Client-Id", strClientID);

			// Get API response
			strResponsVal=getMethodRespVal(httpGet,"getJsonWebserviceCall" , strErrRspChkFlag);

		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getJsonWebserviceCall");
		}
		return strResponsVal;
	}

	public String getJsonString(String jsonPath) {
		try{
			BufferedReader br = new BufferedReader(new FileReader(jsonPath));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String jsonString = sb.toString();
			br.close();
			return jsonString;
		}catch(Exception e){
			return null;
		}
	}


	/**
	 * This method is to get the position of the specified column in the Excel worksheet                           
	 * @param dataSheetLocation - Contains datasheet
	 * @param testCaseName - Test case
	 * @param dataSheet - Contains data
	 */
	public String getWebserviceEndPointUrlJSON(String dataSheetLocation,String testCaseName, String dataSheet) 
	{
		try
		{

			FileInputStream io = new FileInputStream(dataSheetLocation);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet(dataSheet);
			String strInputUrl = "";
			String strBaseUrl = "";
			// String strDelimeter = "";

			int rowNum = sheet.getLastRowNum() + 1;

			for (int i = 1; i < rowNum; i++) {
				int colNum = sheet.getRow(i).getLastCellNum();

				String strTestCase1 = sheet.getRow(i).getCell(0)
						.getStringCellValue().trim();

				if (testCaseName.equalsIgnoreCase(strTestCase1)) {

					// base Url
					strBaseUrl = sheet.getRow(i).getCell(1).getStringCellValue()
							.trim();
					int noOfCol = colNum - 2;
					String parameter[] = new String[noOfCol];

					for (int k = 0, j = 2; k < noOfCol && j <= colNum; k++, j++) {

						parameter[k] = sheet.getRow(i).getCell(j)
								.getStringCellValue();
						System.out.println(parameter[k]);

					}

					strInputUrl = strBaseUrl;
					for (int j = 0; j < noOfCol; j++) {
						java.net.URI uri = new URIBuilder(strInputUrl).addParameter(
								getKey(parameter[j]), getValue(parameter[j])).build();
						strInputUrl=uri.toString();


					}

				}
			}
			System.out.println(strInputUrl);
			return strInputUrl;
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getWebserviceEndPointUrlJSON");
			return null;
		}
	}	

	//*********************************************************************************************			
	//**********************************Methods related to PUT Operation*****************************

	/**
	 * This method is to return the Web Service response value in String after successful GET operation on Web service Url
	 * @param response - HttpResponse object wchi contains API response value 
	 *@param strMethodName - It contains the Method Name
	 */
	public String getPUTMethodRespVal(HttpResponse response , String strMethodName, String strErrRspChkFlag)  
	{
		String strResponsVal="";
		String output = "";	
		try 
		{
			int statusCode = response.getStatusLine().getStatusCode();

			if (!(statusCode == 200 || statusCode == 201))
			{
				if (!(strErrRspChkFlag.equalsIgnoreCase("YES")))
				{
					objReport.setValidationMessageInReport("FAIL","Method "+strMethodName+"(PUT): Failed to generate correct API Response due to Error code "+statusCode);						
				} 

				else
				{
					//Get API response for status code other than 200
					objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(PUT): API Error Response is generated with Error code "+statusCode);						

					HttpEntity entity = response.getEntity();
					strResponsVal = EntityUtils.toString(entity);					
				}
			}	
			else 
			{	
				//Get API response for status code 200
				objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(PUT): Correct API Response is generated");					

			}

			//Get API response value
			//Read the response value line by line and store it in 'strResponsVal' String variable					
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while ((output = br.readLine()) != null) 
			{
				strResponsVal = output;
			}					

			//close the BufferedReader(breader) object
			br.close();

		}
		catch (Exception e) 
		{					
			objReport.writeStackTraceErrorInReport(e, "getPUTMethodRespVal");
		}
		return strResponsVal;
	}


	/**
	 * This method performs PUT activity in the Web service Url (specified by strWebserviceURL) using Accept Header type (specified by strAccptHdr) and XML_String(Specified by XML_String) and return API response in String value.
	 * @param XML_String - Input XML
				   @param strWebserviceURL - Contains WebService Url on which PUT operation will be called
				   @param strAccptHdr - Contains value to determine the Accept Header type for Request body (Value - JSON,XML)
				                                 JSON - application/json
				                                 XML  - application/xml	
	 */			
	public String putWebserviceCall(String XML_String, String strWebserviceURL,String strAccptHdr, String strErrRspChkFlag)
	{
		String strResponsVal = "";
		//String output = "";	

		//Create DefaultHttpClient object
		HttpClient httpClient = new DefaultHttpClient();

		try {

			//Create HttpPut object for Webservice (Specified by strEndPointUrl)
			HttpPut request = new HttpPut(strWebserviceURL);

			//Add headers to the HttpPut object(request)					
			StringEntity params = new StringEntity(XML_String,"UTF-8");				
			params.setContentType("application/xml");
			request.addHeader("content-type", "application/xml");

			//Set the Accept Header based on value specified by strAccptHdr
			if(strAccptHdr.equalsIgnoreCase("xml"))
			{
				request.addHeader("Accept", "application/xml");
			}
			else if(strAccptHdr.equalsIgnoreCase("json"))
			{
				request.addHeader("Accept", "application/json");
			}
			else
			{
				objReport.setValidationMessageInReport("FAIL","Method putWebserviceCall : Incorrect strAccptHdr arguement '"+strAccptHdr+"' is passed");
			}
			request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			request.addHeader("Accept-Language", "en-US,en;q=0.8");
			request.setEntity(params);

			// Send the request; It will immediately return the response in the form of HttpResponse object
			HttpResponse response = httpClient.execute(request);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			strResponsVal=getPUTMethodRespVal(response , "putWebserviceCall",strErrRspChkFlag);


			//Get Status code
			/*		
						int responseCode = response.getStatusLine().getStatusCode();
						if (responseCode == 200) 
						{
							//Get API response value
							//Read the response value line by line and store it in 'strResponsVal' String variable					
							BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
							while ((output = br.readLine()) != null) 
							{
								strResponsVal = output;
							}					
							objReport.setValidationMessageInReport("PASS","Method putWebserviceCall(PUT) : Correct API Response is generated");

							//close the BufferedReader(breader) object
							br.close();
						}
						else
						{	
							//Incorrect API response
							objReport.setValidationMessageInReport("FAIL","Method putWebserviceCall(PUT) : Failed to generate correct API Response due to Error code "+response.getStatusLine().getStatusCode());
						}
			 */
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "putWebserviceCall");				
		}
		return strResponsVal;
	}

	/**
	 * This method performs PUT activity in the Web service Url (specified by strWebserviceURL) using only XML_String(Specified by XML_String) and return API response in String value.
	 * @param XML_String - Input XML
				   @param strWebserviceURL - Contains WebService Url on which PUT operation will be called 
	 */	
	public String putWebserviceCallJSON(String XML_String, String strWebserviceURL, String strErrRspChkFlag)
	{
		int responseCode = -1;
		String output = "";
		String finalOutput = "";
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPut request = new HttpPut(strWebserviceURL);
			StringEntity params = new StringEntity(XML_String,"UTF-8");
			params.setContentType("application/xml");
			// put all of the header parameters in one string using addHeader
			request.addHeader("content-type", "application/xml");
			request.addHeader("Accept", "application/json");
			request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			request.addHeader("Accept-Language", "en-US,en;q=0.8");
			request.setEntity(params);

			// Send the request; It will immediately return the response in
			// HttpResponse object if any
			HttpResponse response = httpClient.execute(request);
			finalOutput=getPUTMethodRespVal(response , "putWebserviceCallJSON",strErrRspChkFlag);

			/*	responseCode = response.getStatusLine().getStatusCode();
						if (response.getStatusLine().getStatusCode() == 200) 
						{							
							BufferedReader br = new BufferedReader(	new InputStreamReader((response.getEntity().getContent())));
							while ((output = br.readLine()) != null) 
							{
								finalOutput = output;
							}							
							objReport.setValidationMessageInReport("PASS","Method putWebserviceCallJSON(PUT) : Correct API Response is generated");							
						}
						else
						{
							//Incorrect API response
							objReport.setValidationMessageInReport("FAIL","Method putWebserviceCallJSON(PUT) : Failed to generate correct API Response due to Error code "+response.getStatusLine().getStatusCode());
						}
			 */
		}catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "putWebserviceCallJSON");
		} 				
		return finalOutput;
	}

	/**
	 * This method performs PUT activity in the Web service Url (specified by strWebserviceURL) using only XML_String(Specified by XML_String) and return API response in String value.
	 * @param XML_String - Input XML
				   @param strWebserviceURL - Contains WebService Url on which PUT operation will be called 
				   @param strUserToken - Contains User Token
	 */	
	public String putWebserviceCallJSONToken(String XML_String, String strWebserviceURL, String strUserToken , String strErrRspChkFlag)
	{
		int responseCode = -1;
		String output = "";
		String finalOutput = "";
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPut request = new HttpPut(strWebserviceURL);
			StringEntity params = new StringEntity(XML_String,"UTF-8");
			params.setContentType("application/xml");
			// put all of the header parameters in one string using addHeader
			request.addHeader("content-type", "application/xml");
			request.addHeader("Accept", "application/json");
			request.addHeader("Authorization", strUserToken);
			request.setEntity(params);
			// Send the request; It will immediately return the response in
			// HttpResponse object if any
			HttpResponse response = httpClient.execute(request);
			finalOutput = getPUTMethodRespVal(response , "putWebserviceCallJSONToken",strErrRspChkFlag);

			/*	responseCode = response.getStatusLine().getStatusCode();
						if (response.getStatusLine().getStatusCode() == 200) {
							BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
							while ((output = br.readLine()) != null) 
							{
								finalOutput = output;
							}							
							objReport.setValidationMessageInReport("PASS","Method putWebserviceCallJSONToken(PUT) : Correct API Response is generated");							
						}
						else
						{							
							//Incorrect API response
							objReport.setValidationMessageInReport("FAIL","Method putWebserviceCallJSONToken(PUT) : Failed to generate correct API Response due to Error code "+response.getStatusLine().getStatusCode());
						}
			 */
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "putWebserviceCallJSONToken");
		} 					
		return finalOutput;
	}

	//******************************************************************************************************************************
	// ******************************************Methods Related to User Token******************************************************

	// editing for diff Auth type
	/**
	 * This method is to generate User Token using the parameters like- Client Id, Client Secret, Access Token Endpoint, Scope, Username and password          
	 * @param strClientId - Contains Client ID value
	 * @param strClientSecret  - Contains Client Secret value
	 * @param strAccessTokenEndpoint - Contains Access Endpoint url
	 * @param strCcope - Contains Scope
	 * @param strUserName - Contains Username   
	 * @param strPassword - Contains password
	 */
	public String getUserTokenAuth(String strClientId, String strClientSecret,String strAccessTokenEndpoint, String strCcope, String strUserName,String strPassword)  
	{
		try
		{
			// TODO Auto-generated method stub
			if (strClientId == null
					|| strClientId.trim().equalsIgnoreCase("".trim())
					|| strClientSecret == null
					|| strClientSecret.equalsIgnoreCase("".trim())
					|| strAccessTokenEndpoint == null
					|| strAccessTokenEndpoint.equalsIgnoreCase("".trim())
					|| strCcope == null || strCcope.equalsIgnoreCase("".trim())) 
			{
				return "";
			} 
			else 
			{
				String responseString = "";

				DefaultHttpClient httpClient = new DefaultHttpClient();
				StringWriter writer = new StringWriter();
				// writer.write("grant_type=client_credentials&client_id="+
				// ClientId+"&client_secret="+ClientSecret+"&scope="+scope);
				// Define a postRequest request
				// String userTokenDetails =
				// "password&username=johndoe&password=A3ddj3w";

				String userTokenDetails="";

				if(!(strUserName.trim().equalsIgnoreCase("")))
				{
					userTokenDetails = "grant_type=password&username="+ strUserName + "&password=" + strPassword + "&client_id="+ strClientId + "&client_secret=" + strClientSecret	+ "&scope=" + strCcope;
				}
				else
				{
					userTokenDetails = "grant_type=client_credentials&client_id="+ strClientId+ "&client_secret="+ strClientSecret+ "&scope=" + strCcope;
				}
				writer.write(userTokenDetails);

				// writer.write("grant_type=client_credentials&client_id=c506db35-efb0-4c6d-8b00-2fdcd4fac968&client_secret=eT4vS5cH3kQ4uR4wJ6uB4bD8bL8mN6uA5aR0jJ5rJ8tY3xJ7fA&scope=Public%20NonPII");
				HttpPost postRequest = new HttpPost(strAccessTokenEndpoint);
				postRequest.addHeader("Content-Type","application/x-www-form-urlencoded");

				StringEntity userEntity = new StringEntity(userTokenDetails);
				postRequest.setEntity(userEntity);
				HttpResponse response = httpClient.execute(postRequest);

				// verify the valid error code first
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("statuscode :" + statusCode);

				if (statusCode != 200) {
					responseString = new BasicResponseHandler()
							.handleResponse(response);
					System.out.println("Failed with HTTP error code : "
							+ statusCode);
				}

				else {
					responseString = new BasicResponseHandler()
							.handleResponse(response);
					System.out.println("response string  :" + responseString);
				}


				String strTagNameArr[] = responseString.split(",");
				System.out.println(strTagNameArr.length);
				System.out.println(strTagNameArr[0]);
				String strToken[] = strTagNameArr[1].split(":");
				System.out.println(strToken[1]);

				//return "Bearer" + " "+ strToken[1].substring(1, strToken[1].length() - 1);
				return "Bearer"+" "+strToken[1].substring(1,strToken[1].length()-1);
			}

		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getUserTokenAuth");						
			return null;
		}
	}



	//changes for external api
	public String getExternalToken(String strClientId,String strClientSecret,String strAccessTokenEndpoint, String strCcope, String certificate, String password) throws Exception, IOException
	{
		try{
			String userTokenDetails="";
			String responseString="";

			DefaultHttpClient httpClient = new DefaultHttpClient();
			StringWriter writer = new StringWriter();
			// TODO Auto-generated method stub
			userTokenDetails = "grant_type=client_credentials&client_id="+ strClientId+ "&client_secret="+ strClientSecret+ "&scope=" + strCcope;

			KeyStore keystore = KeyStore.getInstance("PKCS12");	
			FileInputStream fis = new FileInputStream(certificate);
			keystore.load(fis, password.toCharArray());
			fis.close();
			if (keystore.size()>0){
				System.out.println("The size of the keys is " + keystore.size());	
				System.out.println("The key used is " + keystore.toString());

				KeyManagerFactory keymgrfactory =  KeyManagerFactory.getInstance("PKIX");
				keymgrfactory.init(keystore,password.toCharArray());
				KeyManager [] keyManagers =  keymgrfactory.getKeyManagers();

				// if (keyManagers.length>0){

				System.out.println("Success in Setting up KeyManager" + keyManagers.length);

				SSLContext sslContext = SSLContext.getInstance("TLS");

				sslContext.init(keyManagers, null, null);
				System.out.println("SSL Context established!!");
				//org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext, new StrictHostnameVerifier());

				SSLSocketFactory ssf= new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				Scheme sch = new Scheme("https", 443, ssf);
				httpClient.getConnectionManager().getSchemeRegistry().register(sch); 

				writer.write(userTokenDetails);

				// writer.write("grant_type=client_credentials&client_id=c506db35-efb0-4c6d-8b00-2fdcd4fac968&client_secret=eT4vS5cH3kQ4uR4wJ6uB4bD8bL8mN6uA5aR0jJ5rJ8tY3xJ7fA&scope=Public%20NonPII");
				HttpPost postRequest = new HttpPost(strAccessTokenEndpoint);
				postRequest.addHeader("Content-Type","application/x-www-form-urlencoded");

				StringEntity userEntity = new StringEntity(userTokenDetails);
				postRequest.setEntity(userEntity);
				HttpResponse response = httpClient.execute(postRequest);

				// verify the valid error code first
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println("statuscode :" + statusCode);

				if (statusCode != 200) {
					responseString = new BasicResponseHandler()
							.handleResponse(response);
					System.out.println("Failed with HTTP error code : "
							+ statusCode);
				}

				else {
					responseString = new BasicResponseHandler()
							.handleResponse(response);
					System.out.println("response string  :" + responseString);
				}

				String strTagNameArr[] = responseString.split(",");
				System.out.println(strTagNameArr.length);
				System.out.println(strTagNameArr[0]);
				String strToken[] = strTagNameArr[1].split(":");
				System.out.println(strToken[1]);

				//return "Bearer" + " "+ strToken[1].substring(1, strToken[1].length() - 1);
				System.out.println("Bearer"+" "+strToken[1].substring(1,strToken[1].length()-1));
				return "Bearer"+" "+strToken[1].substring(1,strToken[1].length()-1);
			}		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;	
	}
	/**
	 * This method is to generate Access Token using the parameters like- Client Id, Client Secret, Authorization Endpoint, Access Token Endpoint and Redirect URL,  Scope, Username and password          
	 * @param strClientId - Contains Client ID value
	 * @param strClientSecret  - Contains Client Secret value
	 * @param strAuthorizationEndpoint - Contains Authorization End point URL
	 * @param strAccessTokenEndpoint - Contains Access Endpoint url
	 * @param strRedirectUrl - Contains Redirect URL
	 * @param strCcope - Contains Scope
	 * @param pUserNameToken - Contains Username   
	 * @param pPasswordToken - Contains password
	 */
	public String generateAccessToken(String strClientId,String strClientSecret,String strAuthorizationEndpoint,String strAccessTokenEndpoint,String strRedirectUrl, String strCcope, String pUserNameToken, String pPasswordToken) throws Exception, IOException
	{ 	String accessToken="";

	try
	{

		System.setProperty("webdriver.gecko.driver","C:\\Data\\Browsers\\geckodriver.exe");
		System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Firefox\\firefox.exe");

		//Create a firefox profile with installing the Rest Client Plugin
		ProfilesIni profile = new ProfilesIni(); 
		FirefoxProfile myprofile = profile.getProfile("restclient");
		myprofile.setPreference("network.proxy.type", 0);

		//Setting Capabilities
		DesiredCapabilities ffCapabilities = DesiredCapabilities.firefox();
		ffCapabilities.setCapability("firefoxProfile", myprofile);
		ffCapabilities.setCapability(FirefoxDriver.PROFILE, myprofile);
		WebDriver driver = new FirefoxDriver(ffCapabilities);

		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("chrome://restclient/content/restclient.html");
		Thread.sleep(3);

		//click Authentication dropdown list
		driver.findElement(By.xpath("//*[@class='dropdown-toggle' and contains(text(),'Authentication')]")).click();
		Thread.sleep(2);
		driver.findElement(By.xpath("//a[text()='OAuth2']")).click();

		Thread.sleep(2);

		String  mainWin= driver.getWindowHandle();
		//Send the values
		driver.findElement(By.name("client_id")).sendKeys(strClientId);
		driver.findElement(By.name("client_secret")).sendKeys(strClientSecret);
		driver.findElement(By.name("authorization_endpoint")).sendKeys(strAuthorizationEndpoint);
		driver.findElement(By.name("token_endpoint")).sendKeys(strAccessTokenEndpoint);
		//POST,GET
		driver.findElement(By.name("redirection_endpoint")).sendKeys(strRedirectUrl);
		driver.findElement(By.name("scope")).sendKeys(strCcope);

		WebElement webElement=driver.findElement(By.name("scope"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",webElement);

		Thread.sleep(2);

		driver.findElement(By.xpath("//*[@class='btn btn-success btnAuthorize']")).click();
		Thread.sleep(2);

		driver.findElement(By.xpath("//*[text()='Yes, please']")).click();
		Thread.sleep(5);

		//Switch to another window
		Set<String> winhandles= driver.getWindowHandles();

		for (String handle : winhandles) 
		{

			if(!(handle.equalsIgnoreCase(mainWin)))
			{ 
				driver.switchTo().window(handle);

				driver.findElement((By.id("username"))).sendKeys(pUserNameToken);
				driver.findElement((By.id("password"))).sendKeys(pPasswordToken);		
				Thread.sleep(5);
				WebElement loginBtn= driver.findElement((By.id("loginButton")));

				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", loginBtn);
				break;
			}
		}
		Thread.sleep(10);
		driver.switchTo().window(mainWin);

		String value = (String)(((JavascriptExecutor)driver).executeScript("return arguments[0].value",driver.findElement(By.name("access_token"))));
		System.out.println("aa--"+value); 
		Thread.sleep(2);


		driver.findElement(By.xpath("//*[@class='form-horizontal']/div[2]/div/input[@name='access_token']")).sendKeys(Keys.chord(Keys.CONTROL,"a"));
		driver.findElement(By.xpath("//*[@class='form-horizontal']/div[2]/div/input[@name='access_token']")).sendKeys(Keys.chord(Keys.CONTROL,"c"));

		accessToken = (String) (Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));

		System.out.println(accessToken);
	}
	catch (Exception e)
	{
		objReport.writeStackTraceErrorInReport(e, "generateAccessToken");
		return null;
	}
	//Append the token with Bearer
	return "Bearer " + accessToken;
	}

	/**
	 * This method is to generate JWT Token Token using the below parameters 
	 * @param tokenUrl - Contains Client ID value
	 * @param authUrl  - Contains Client Secret value
	 * @param clientId - Contains Access Endpoint url
	 * @param clientSecret - Contains Scope
	 * @param scope - Contains Username  
	 * @param redirectUrl - Contains Redirect URL
	 * @param userName - Contains user name 
	 * @param password - Contains password
	 */
	public String getJwtToken(String tokenUrl,String authUrl,String clientId,String clientSecret,String scope,String redirectUrl,String userName,String password) throws Exception
	{
		String jwtToken="";
		String finalStr ="";
		try
		{


			List<String> tokens = new ArrayList<String>();
			DefaultHttpClient httpclient = new DefaultHttpClient();
			//getUserTokenAuth(clientId,clientSecret,authUrl,scope,userName,password);
			String authCode = getAuthCode(authUrl, userName, password, scope, clientId, redirectUrl);

			HttpPost post = new HttpPost(tokenUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id", clientId));
			params.add(new BasicNameValuePair("client_secret", clientSecret));
			params.add(new BasicNameValuePair("code", authCode));
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));

			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse responsejwt = httpclient.execute(post);
			JsonParser parser = new JsonParser();	
			JsonObject data = (JsonObject) parser.parse(new InputStreamReader(responsejwt.getEntity().getContent()));
			String token = data.get("access_token").toString();

			String jwt= "";
			if(data.get("jwt_token") != null)
			{
				jwt= data.get("jwt_token").toString();
			}
			else
			{
				jwt= data.get("id_token").toString();
			}

			String refresh = data.get("refresh_token").toString();
			String accessToken = token.substring(1, token.length()-1);

			jwtToken = jwt.substring(1, jwt.length()-1);
			String refreshToken = refresh.substring(1, refresh.length()-1);					
			System.out.println("jwt token:"+jwtToken);

			finalStr = accessToken+"#fz"+jwtToken;
			System.out.println("jwt token:"+jwtToken);

		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getJwtToken");
			return null;
		}
		return finalStr;
	}


	//Changes for Membership API(05/20/2018)
	public String getUserToken(String tokenUrl,String authUrl,String clientId,String clientSecret,String scope,String redirectUrl,String userName,String password) throws Exception
	{

		String accessToken ="";
		try
		{					

			List<String> tokens = new ArrayList<String>();
			DefaultHttpClient httpclient = new DefaultHttpClient();
			//getUserTokenAuth(clientId,clientSecret,authUrl,scope,userName,password);
			String authCode = getAuthCode(authUrl, userName, password, scope, clientId, redirectUrl);

			HttpPost post = new HttpPost(tokenUrl);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id", clientId));
			params.add(new BasicNameValuePair("client_secret", clientSecret));
			params.add(new BasicNameValuePair("code", authCode));
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));

			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse responsejwt = httpclient.execute(post);
			JsonParser parser = new JsonParser();	
			JsonObject data = (JsonObject) parser.parse(new InputStreamReader(responsejwt.getEntity().getContent()));
			String token = data.get("access_token").toString();
			//String jwt = data.get("jwt_token").toString();
			//String refresh = data.get("refresh_token").toString();
			accessToken = "Bearer "+token.substring(1, token.length()-1);

		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getJwtToken");
			return null;
		}
		return accessToken;
	}


	/**
	 * This method is to generate Authorization Code sed to generate JWT Token using below parameters 
	 * @param authUrl  - Contains Client Secret value
	 * @param userName - Contains user name 
	 * @param password - Contains password
	 * @param scope - Contains Access End point Url
	 * @param clientId - Contains Client Id
	 * @param redirectUrl - Contains redirect Url 
	 */
	public static String getAuthCode(String authUrl, String userName, String password, String scope, String clientId, String redirectUrl) 
	{
		String authCode="";
		Report objReport = new Report();
		try
		{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("scope", scope));
			params.add(new BasicNameValuePair("response_type", "code"));
			params.add(new BasicNameValuePair("client_id", clientId));
			params.add(new BasicNameValuePair("redirect_uri", redirectUrl));

			java.net.URI uri = new URIBuilder(authUrl).addParameters(params).build();
			HttpGet post = new HttpGet(uri);
			HttpClientContext context = HttpClientContext.create();

			HttpResponse response = httpclient.execute(post, context);
			java.net.URI finalUrl = post.getURI();
			List<java.net.URI> locations = context.getRedirectLocations();
			if (locations != null) {
				finalUrl = locations.get(locations.size() - 1);
			}
			//System.out.println(finalUrl);
			EntityUtils.consume(response.getEntity());
			String userid = "username=".concat(userName);
			String userPassword = "Password=".concat(password);
			String cred = userid+"&"+userPassword;
			HttpPost postReq = new HttpPost(finalUrl);
			StringEntity entity = new StringEntity(cred);
			postReq.setEntity(entity);
			postReq.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
			postReq.addHeader("User-Agent", "MSIE 8.0");

			HttpResponse responsePost = httpclient.execute(postReq,context);
			List<org.apache.http.Header> location = Arrays.asList(responsePost.getHeaders("Location"));
			String locationUrl = location.get(0).getValue().toString();
			String[] locationArray = locationUrl.split("=");
			authCode = locationArray[1].trim().toString();
			//System.out.println(authCode);

			//EntityUtils.consume(arg0);
			EntityUtils.consume(responsePost.getEntity());
			System.out.println("responsePost: "+responsePost);
			System.out.println("authcode- " +authCode);
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getAuthCode");
			return null;
		}

		return authCode;

	}

	// ***************************Methods related to API response validation*****************
	/**
	 * This method is to perform validation of web services responses by comparing with expected responses 
	 * @param strInputDataSheet - Contains input data details
	 * @param strExcelWebSrvcFildValdPath - Location of datasheet containing validation details
	 * @param strWorkSheet - Sheet in datasheet
	 * @param strTestCase - Test Case name
	 * @param mutpPUTCount 
	 */
	public void validateWebSrvcResponseFieldValue(String strInputDataSheet,	String strExcelWebSrvcFildValdPath, String strWorkSheet,String strTestCase, int count2)  
	{
		Boolean valRespStatusFlag= false;
		try
		{
			//Creating sheet for storing the response from web service
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");

			FileInputStream fileInputStream_Webservc= new FileInputStream(strExcelWebSrvcFildValdPath);
			XSSFWorkbook wb1=new XSSFWorkbook(fileInputStream_Webservc);
			XSSFSheet webSerRspnFldValsheet=wb1.getSheet(strWorkSheet);

			int rowNum = webSerRspnFldValsheet.getLastRowNum()+1;

			String strArr[];
			String strTagArr[];
			String strTagValArr[];

			String expected_Field_Val="";
			String actual_Field_Val="";
			int fld_loc=0;
			int cnt=1;
			int intcnt;
			int OutputSheetrowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int m;

			//creating loop to search the required Test case and split the tag name from value
			for(m=1;m<rowNum;m++){

				if (webSerRspnFldValsheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase.trim()) ){
					if(count2>1){
						m=m+(count2-1);
					}
					int colNum = webSerRspnFldValsheet.getRow(m).getLastCellNum();
					for(int k=1;k<colNum;k++){
						if (webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim().length()>0)
						{
							strArr=(webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim()).split("@");

							if(strArr.length>2)
							{
								if(strArr[0].matches("[0-9]+"))
								{
									intcnt=Integer.parseInt(strArr[0]);
									strTagArr=strArr[1].split(";");
									strTagValArr=strArr[2].split(";");
								}
								else
								{
									intcnt=1;
									strTagArr=strArr[0].split(";");	
									strTagValArr=(strArr[1]+"@"+strArr[2]).split(";");
								}
							}
							else
							{
								intcnt=1;
								strTagArr=strArr[0].split(";");	
								strTagValArr=strArr[1].split(";");
							}

							for(int i=0;i<strTagArr.length;i++)
							{   cnt=1;
							expected_Field_Val="";
							actual_Field_Val="";


							for	(fld_loc=1;fld_loc<OutputSheetrowNum;fld_loc++)
							{
								if(excelSheet_DS_Output.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTagArr[i]))
								{
									if(cnt==intcnt)
										break;
									else
										cnt=cnt+1;
								}							
							}

							if(fld_loc==OutputSheetrowNum)
							{
								objReport.setValidationMessageInReport("FAIL"," API Response Validation : XML tag '"+strTagArr[i] +"' is not available in API response XML"); 										
							}	

							// Expected Input Field
							expected_Field_Val=strTagValArr[i];

							//Response from webservice
							actual_Field_Val=excelSheet_DS_Output.getRow(fld_loc).getCell(1).getStringCellValue().trim();// Response Field Value in Output Worksheet					

							if(!expected_Field_Val.equalsIgnoreCase(actual_Field_Val))
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("FAIL","'"+strTagArr[i] +"'  field Validation------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		
							}
							else
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("PASS","'"+strTagArr[i] +"'  field Validation------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		
							}
							}
						}
					}
				}

			}
			if(valRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateWebSrvcResponseFieldValue : Please check Test Case '"+strTestCase+"' is available under Test Case column of 'Webservice_Validation' Worksheet" ); 							
			}
		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateWebSrvcResponseFieldValue");
		}

	} 


	// ***************************Methods related to API response validation*****************
	/**
	 * This method is to perform validation of web services responses by comparing with expected responses 
	 * @param strInputDataSheet - Contains input data details
	 * @param strExcelWebSrvcFildValdPath - Location of datasheet containing validation details
	 * @param strWorkSheet - Sheet in datasheet
	 * @param strTestCase - Test Case name
	 */
	public void validateWebSrvcResponseGETFieldValue(String strInputDataSheet,	String strExcelWebSrvcFildValdPath, String strWorkSheet,String strTestCase, int count2)  
	{
		Boolean valRespStatusFlag= false;
		try
		{
			//Creating sheet for storing the response from web service
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");

			FileInputStream fileInputStream_Webservc= new FileInputStream(strExcelWebSrvcFildValdPath);
			XSSFWorkbook wb1=new XSSFWorkbook(fileInputStream_Webservc);
			XSSFSheet webSerRspnFldValsheet=wb1.getSheet(strWorkSheet);

			int rowNum = webSerRspnFldValsheet.getLastRowNum()+1;

			String strArr[];
			String strTagArr[];
			String strTagValArr[];

			String expected_Field_Val="";
			String actual_Field_Val="";
			int fld_loc=0;
			int cnt=1;
			int intcnt;
			int OutputSheetrowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int m;

			//creating loop to search the required Test case and split the tag name from value
			for(m=1;m<rowNum;m++){

				if (webSerRspnFldValsheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase.trim()) ){

					if(count2>1){
						m=m+(count2-1);
					}
					int colNum = webSerRspnFldValsheet.getRow(m).getLastCellNum();
					for(int k=1;k<colNum;k++){
						if (webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim().length()>0)
						{
							strArr=(webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim()).split("@");

							if(strArr.length>2)
							{
								if(strArr[0].matches("[0-9]+"))
								{
									intcnt=Integer.parseInt(strArr[0]);
									strTagArr=strArr[1].split(";");
									strTagValArr=strArr[2].split(";");
								}
								else
								{
									intcnt=1;
									strTagArr=strArr[0].split(";");	
									strTagValArr=(strArr[1]+"@"+strArr[2]).split(";");
								}
							}
							else
							{
								intcnt=1;
								strTagArr=strArr[0].split(";");	
								strTagValArr=strArr[1].split(";");
							}

							for(int i=0;i<strTagArr.length;i++)
							{   cnt=1;
							expected_Field_Val="";
							actual_Field_Val="";


							for	(fld_loc=1;fld_loc<OutputSheetrowNum;fld_loc++)
							{
								if(excelSheet_DS_Output.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTagArr[i]))
								{
									if(cnt==intcnt)
										break;
									else
										cnt=cnt+1;
								}							
							}

							if(fld_loc==OutputSheetrowNum)
							{
								objReport.setValidationMessageInReport("FAIL"," API Response Validation : XML tag '"+strTagArr[i] +"' is not available in API response XML"); 										
							}	

							// Expected Input Field
							expected_Field_Val=strTagValArr[i];

							//Response from webservice
							actual_Field_Val=excelSheet_DS_Output.getRow(fld_loc).getCell(1).getStringCellValue().trim();// Response Field Value in Output Worksheet					

							if(!expected_Field_Val.equalsIgnoreCase(actual_Field_Val))
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("FAIL","'"+strTagArr[i] +"'  field Validation------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		
							}
							else
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("PASS","'"+strTagArr[i] +"'  field Validation------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		
							}
							}
						}
					}
					break;
				}

			}
			if(valRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateWebSrvcResponseFieldValue : Please check Test Case '"+strTestCase+"' is available under Test Case column of 'Webservice_Validation' Worksheet" ); 							
			}
		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateWebSrvcResponseFieldValue");
		}

	} 


	/**
	 * This method is to validate dynamic web service responses with expected responses                   
	 * @param strInputDataSheet - Contains input data details             
	 */
	public void validateDynamicOutput(String strInputDataSheet) 
	{
		try
		{
			FileInputStream io = new FileInputStream("C:\\Users\\n116535\\Desktop\\Feature code details.xlsx");
			FileInputStream io1 = new FileInputStream(strInputDataSheet);
			XSSFWorkbook wb=new XSSFWorkbook(io);
			XSSFWorkbook wb1=new XSSFWorkbook(io1);
			XSSFSheet sheet=wb.getSheetAt(0);
			XSSFSheet sheet2=wb1.getSheet("output_1");

			XSSFRow row;
			XSSFRow row2;
			XSSFCell cell;
			HashMap<String, Object> map = new HashMap<String, Object>();
			HashMap<String, Object> map2 = new HashMap<String, Object>();
			int rows; // No of rows
			int rows2;
			rows = sheet.getPhysicalNumberOfRows()-1;
			rows2 = sheet2.getPhysicalNumberOfRows()-1;

			int cols = 0; // No of columns
			int tmp = 0;
			int Slnum = 0;
			int j = 0;
			int k =0;
			String temp = "";
			String memId = "183593319+10+1+20150101+740699+A+1";

			//for number of columns
			for(int i = 1; i <= rows; i++) {
				row = sheet.getRow(i);
				if(row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if(tmp > cols) cols = tmp;
				}
			}

			//for getting the 1st index of the row which one is required 
			for(int r = 1; r <= rows; r++) {
				row = sheet.getRow(r);
				if(row != null) {
					for(int c = 1; c < cols; c++) {
						cell = row.getCell(c);

						if(cell != null && cell.toString().contains("+") && cell.toString().equalsIgnoreCase(memId)) 
						{
							j=cell.getRowIndex();
						}
					}
				}
			}

			//for getting total number of memberId and last index of row  
			for(int r = 1; r <= rows; r++) {
				row = sheet.getRow(r);
				if(row != null) {
					for(int c = 1; c < cols; c++) {
						cell = row.getCell(c);

						if(cell != null && cell.toString().contains("+")) 
						{
							Slnum=Slnum+1;
							k = rows/Slnum;
						}
					}
				}
			}

			for(int r = j; r <= j+k; r++) {
				row = sheet.getRow(r);
				if(row != null) {
					for(int c = 1; c < cols; c++) {
						cell = row.getCell(c);

						if(c>=2){
							while(!cell.toString().isEmpty())
							{
								if(!cell.toString().equalsIgnoreCase("true") && !cell.toString().equalsIgnoreCase("false")){
									temp = cell.toString();
								}
								//count=count+1;
								if(cell.toString().equalsIgnoreCase("true") || cell.toString().equalsIgnoreCase("false")){
									map.put(temp.toString(), cell.toString());
								}
								break;
							}
						}
					}
				}
			}
			//2nd map
			for(int i = 1; i <= rows2; i++) {
				row2 = sheet2.getRow(i);
				if(row2 != null) {
					tmp = sheet2.getRow(i).getPhysicalNumberOfCells();
					if(tmp > cols) cols = tmp;
				}
			}

			for(int r = 0; r <= rows2; r++) {
				row2 = sheet2.getRow(r);
				if(row2 != null) {
					for(int c = 0; c < cols; c++) {
						cell = row2.getCell(c);
						//System.out.println(cell.toString());
						if(cell != null){
							while(!cell.toString().isEmpty())
							{
								if(!cell.toString().equalsIgnoreCase("true") && !cell.toString().equalsIgnoreCase("false")){
									temp = cell.toString();
								}
								//count=count+1;
								if(cell.toString().equalsIgnoreCase("true") || cell.toString().equalsIgnoreCase("false")){
									map2.put(temp.toString(), cell.toString());
								}
								break;
							}
						}
					}

				}
			}


			Set keys = map.keySet();
			//Print 1st map
			System.out.println("Provided Datasheet values : ");
			//System.out.println(keys);
			for (Iterator i = keys.iterator(); i.hasNext(); ) {
				String key = (String) i.next();
				String value = (String) map.get(key);
				System.out.println(key + " = " + value);
			}
			System.out.println("\n");
			System.out.println("\n");
			//Print 2nd map
			System.out.println("Our Output values : ");
			Set keys2 = map2.keySet();
			for (Iterator i = keys2.iterator(); i.hasNext(); ) {
				String key = (String) i.next();
				String value = (String) map2.get(key);
				System.out.println(key + " = " + value);
			}
			System.out.println("\n");

			//calling compare function
			mapsAreEqual(map, map2);
			System.out.println("Comparision results : " +mapsAreEqual(map, map2));

		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateDynamicOutput");
		}
	}

	//for comparing 2 Hashmaps
	//validateDynamicWebSrvcResponseFieldValue
	/**
	 * This method is to call the write to Excel and validation of response methods                 
	 * @param strInputDataSheet  - Contains input data details         
	 */
	public void validateDynamicWebSrvcResponseFieldValue(String strInputDataSheet) 
	{
		try 
		{
			writeExcelOutput(strInputDataSheet);
			validateDynamicOutput(strInputDataSheet);
		} 
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "validateDynamicWebSrvcResponseFieldValue");
		}
	}

	/**
	 * This method is to verify the correct API response is generated after performing Post activity on the Webservice
	 * @param strScriptName It contains Test script name                                    
	 */
	public void validateResponsePost(String strScriptName) 

	{
		//int countPost1 = 0;
		//CommonFunctions commonFunc= new CommonFunctions();
		Boolean valPostRespStatusFlag= false;
		try 
		{
			APIFunctions webserCommnFunc = new APIFunctions();

			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to POST worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("POST");

			//Get total used row count of 'POST' worksheet
			int rowNum = sheet.getLastRowNum()+1;
			boolean MultipleAPIPost=false;

			//Iterate through 1st column of all the rows of 'POST' worksheet till a match is found same as Test Script name specified by 'strScriptName' 
			for(int i=1;i<rowNum;i++)
			{
				if (sheet.getRow(i).getCell(getcolumn("A"))!=null)
				{
					if(APICreateAndExecute.mutpPOSTCount>1 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()) && MultipleAPIPost==false){
						i=i+(APICreateAndExecute.mutpPOSTCount-1);
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()) && MultipleAPIPost==false)
					{
						MultipleAPIPost = true;
						valPostRespStatusFlag= true;

						//Get Test case name
						strTestCase = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();

						//Get system path location where output response excel will be saved
						//strInputDataFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath");
						strInputDataFolder = Runner.properties.getProperty("APIResponseDSFolderPath");

						//Output response excel sheet
						String strInputDataSheet=strInputDataFolder+"\\"+strScriptName+"_Data_Sheet.xlsx";
						if(APICreateAndExecute.mutpAPICount>1){
							strInputDataSheet=strInputDataFolder+"\\"+strScriptName+"_Data_Sheet"+(APICreateAndExecute.mutpAPICount-1)+".xlsx";
						}
						//Validate Web service response data
						webserCommnFunc.validateWebSrvcResponseFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase,APICreateAndExecute.mutpPOSTCount);
					}
				}
			}

			if(valPostRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateResponsePost : Please check Test script '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "validateResponsePost (POST)");	
		}
	}

	/**
	 * This method is toVerify the correct API dynamic response is generated after performing Post activity on the Webservice
	 * @param strScriptName It contains Test script name                                    
	 */
	public void validateDynamicResponsePost(String strScriptName)  
	{
		Boolean valPostRespStatusFlag= false;
		try 
		{
			APIFunctions webserCommnFunc = new APIFunctions();

			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to POST worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("POST");

			//Get total used row count of 'POST' worksheet
			int rowNum = sheet.getLastRowNum()+1;

			//Iterate through 1st column of all the rows of 'POST' worksheet till a match is found same as Test Script name specified by 'strScriptName' 			
			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					valPostRespStatusFlag= true;

					//Get Test case name
					strTestCase = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();

					//Get system path location where output response excel will be saved
					strInputDataFolder = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();

					//Output response excel sheet
					String strInputDataSheet=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";

					//Validate Web service response data
					webserCommnFunc.validateDynamicWebSrvcResponseFieldValue(strInputDataSheet);
				}
			}

			if(valPostRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateDynamicResponsePost : Please check Test script '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "validateDynamicResponsePost (POST)");		
		}
	}

	/**
	 * This method is to Verify the correct API response is generated after performing GET activity on the Webservice 
	 * @param strScriptName It contains Test script name                                    
	 */
	public void validateResponsGet(String strScriptName)  
	{
		Boolean valGetRespStatusFlag= false;
		Integer intTestCaseRowNum=0;
		APIFunctions webserCommnFunc = new APIFunctions();

		try 
		{
			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to GET worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb1=new HSSFWorkbook(io);		
			HSSFSheet sheet=wb1.getSheet("GET");

			//Get total used row count of 'GET' worksheet
			int rowNum = sheet.getLastRowNum()+1;

			boolean MultipleAPIValidateFlag=false;

			//Iterate through 1st column of all the rows of 'GET' worksheet till a match is found same as Test Script name specified by 'strScriptName' 						
			for(int i=1;i<rowNum;i++)
			{
				if (sheet.getRow(i).getCell(getcolumn("A"))!=null && (MultipleAPIValidateFlag==false))
				{
					if(APICreateAndExecute.mutpGETCount>1 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()) ){
						i=i+(APICreateAndExecute.mutpGETCount-1);
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
						MultipleAPIValidateFlag = true;
						valGetRespStatusFlag= true;

						//Get Test case name
						strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name

						//String strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
						String strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).toString().trim();

						strInputDataFolder = Runner.properties.getProperty("APIResponseDSFolderPath");
						String strInputDataSheet=strInputDataFolder+strScriptName+"_Data_Sheet.xlsx";

						if(APICreateAndExecute.mutpAPICount>1){

							strInputDataSheet=strInputDataFolder+strScriptName+"_Data_Sheet"+(APICreateAndExecute.mutpAPICount-1)+".xlsx";
						}

						//Validate Web service response data
						webserCommnFunc.validateWebSrvcResponseGETFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase, APICreateAndExecute.mutpGETCount);

						intTestCaseRowNum=webserCommnFunc.getTestCaseRowNumber(dataSheet, strTestCase, strIterationNumber);
						//webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "PASS");
					}
				}
			}
			if(valGetRespStatusFlag==false)
			{
				//	webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "FAIL");
				objReport.setValidationMessageInReport("FAIL","Method validateResponsGet : Please check Test script '"+strScriptName+"' is available under Test script column of 'GET' Worksheet" ); 							

			}
		}
		catch (Exception e) {	
			//webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "FAIL");
			objReport.writeStackTraceErrorInReport(e, "validateResponsGet (GET)");	
		}
	}

	/**
	 * This method is to Verify the correct dynamic API response is generated after performing GET activity on the Webservice 
	 * @param strScriptName It contains Test script name                                    
	 */
	public void validateDynamicResponsGet(String strScriptName)  
	{
		Boolean valGetRespStatusFlag=false;

		try {
			APIFunctions webserCommnFunc = new APIFunctions();

			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to GET worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb1=new HSSFWorkbook(io);
			HSSFSheet sheet=wb1.getSheet("GET");

			//Get total used row count of 'GET' worksheet
			int rowNum = sheet.getLastRowNum()+1;

			//Iterate through 1st column of all the rows of 'GET' worksheet till a match is found same as Test Script name specified by 'strScriptName' 									
			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					valGetRespStatusFlag=true;

					//Get Test case name
					strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name

					//Get system path location where output response excel will be saved
					strInputDataFolder = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();

					//Output response excel sheet
					String strInputDataSheet=strInputDataFolder+"\\"+strTestCase+"_Data_Sheet.xlsx";

					//Validate Web service response data
					webserCommnFunc.validateDynamicWebSrvcResponseFieldValue(strInputDataSheet);
				}
			}

			if(valGetRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateDynamicResponsGet : Please check Test script '"+strScriptName+"' is available under Test script column of 'GET' Worksheet" ); 							
			}
		}
		catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "validateDynamicResponsGet (GET))");	
		}

	}

	/**
	 * This method is to Verify the correct dynamic API response is generated after performing PUT activity on the Webservice 
	 * @param strScriptName It contains Test script name                                    
	 */
	public void validateResponsePut(String strScriptName) 
	{
		Boolean valPutRespStatusFlag=false;

		try 
		{
			APIFunctions webserCommnFunc = new APIFunctions();

			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to PUT worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("PUT");

			//Get total used row count of 'GET' worksheet
			int rowNum = sheet.getLastRowNum()+1;
			boolean MultipleAPIValidateFlag=false;

			//Iterate through 1st column of all the rows of 'PUT' worksheet till a match is found same as Test Script name specified by 'strScriptName' 											
			for(int i=1;i<rowNum;i++)
			{
				if (sheet.getRow(i).getCell(getcolumn("A"))!=null && (MultipleAPIValidateFlag==false))
				{
					if(APICreateAndExecute.mutpPUTCount>1 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()) ){
						i=i+(APICreateAndExecute.mutpPUTCount-1);
					}

					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

						MultipleAPIValidateFlag = true;
						valPutRespStatusFlag=true;

						//Get Test case name
						strTestCase = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();

						//Get system path location where output response excel will be saved
						//strInputDataFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath");
						strInputDataFolder = Runner.properties.getProperty("APIResponseDSFolderPath");

						//Output response excel sheet
						String strInputDataSheet=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";
						if(APICreateAndExecute.mutpAPICount>1){

							strInputDataSheet=strInputDataFolder+strScriptName+"_Data_Sheet"+(APICreateAndExecute.mutpAPICount-1)+".xlsx";
						}

						//Validate Web service response data
						webserCommnFunc.validateWebSrvcResponseFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase,APICreateAndExecute.mutpPUTCount);
					}
				}
			}

			if(valPutRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateResponsePut : Please check Test script '"+strScriptName+"' is available under Test script column of 'PUT' Worksheet" ); 							
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "validateResponsePut (PUT))");	
		}
	}

	///******************************XML File Operation related Methods****************************************

	/**
	 * This method is to convert the String value to DOM using the input Xml and the location of XML                           
	 * @param strInput - Input XML
	 * @param strXMLFile - Location of XML         
	 */
	public void convertStringToDOM(String strInput, String strXMLFile)
	{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Transforming the XML Source to a Result
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(builder.parse(new InputSource(new StringReader(strInput))));
			StreamResult result = new StreamResult(new File(strXMLFile));
			transformer.transform(source, result);
		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "convertStringToDOM");				
		}

	}

	/**
	 * This method is to convert the DOM value to String value and store it in Excel sheet    
	 * @param strExcelFile - Location of the Excel sheet
	 * @param strDOMFile - XMl file
	 * @param strXMLTagName - XMl Tag name                                     
	 */
	public void covertDOMToExcel(String strExcelFile, String strDOMFile,String[] strXMLTagName)  
	{
		try {
			FileInputStream io = new FileInputStream(strExcelFile);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet("Output");
			if (sheet != null) {
				int index = wb.getSheetIndex(sheet);
				wb.removeSheetAt(index);
			}

			// Creating Excel worksheet
			sheet = wb.createSheet("Output");

			// create a DOMParser
			DOMParser parser = new DOMParser();
			parser.parse(strDOMFile);

			// get the DOM Document object
			Document doc = parser.getDocument();

			// Storing the XML Node name and its value in Output worksheet
			for (int i = 0; i < strXMLTagName.length; i++) {
				NodeList nodeList = doc.getElementsByTagName(strXMLTagName[i]);
				checkIndvNodes(nodeList, nodeList.getLength(), sheet);
			}

			// Saving the values in Excel
			FileOutputStream outFile = new FileOutputStream(strExcelFile);
			wb.write(outFile);
			outFile.close();
		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "covertDOMToExcel");					
		}
	}

	/**This method is to store the output response in the Excel by creating worksheet Output                                     
	 * @param strExcelFile - Location of the Excel sheet  
	 * @param strDOMFile - XMl file
	 * @param strXMLTagName - XMl Tag name
	 * @param strTestcase - Test case name 
	 * @param length - Length of Tag
	 */
	public void covertDOMToExcelpSeq(String strExcelFile, String strDOMFile,String[] strXMLTagName, String strTestcase, int length)
	{
		try 
		{
			XSSFWorkbook wb = null;
			try {
				FileInputStream io = new FileInputStream(strExcelFile);
				wb = new XSSFWorkbook(io);
			} catch (Exception e) {
				wb = new XSSFWorkbook();
			}
			XSSFSheet sheet = wb.getSheet("Output");
			if (sheet == null) {
				sheet = wb.createSheet("Output");
			}

			// Creating Excel worksheet
			XSSFRow row;
			if (sheet.getLastRowNum() > 0) {
				row = sheet.createRow(sheet.getLastRowNum() + 1);// sheet.getRow(rownum);
			} else {
				row = sheet.createRow(sheet.getLastRowNum());// sheet.getRow(rownum);
			}

			row.createCell(0).setCellValue("API tagname : " + strTestcase);
			row.createCell(1).setCellValue("API value : " + strTestcase);

			// create a DOMParser
			DOMParser parser = new DOMParser();
			parser.parse(strDOMFile);

			// get the DOM Document object
			Document doc = parser.getDocument();
			System.out.println(doc);

			// Storing the XML Node name and its value in Output worksheet
			for (int i = 0; i < strXMLTagName.length; i++) 
			{
				NodeList nodeList = doc.getElementsByTagName(strXMLTagName[i]);
				sheet = checkIndvNodesPseqNum(nodeList, nodeList.getLength(),sheet, strTestcase);
			}
			// Saving the values in Excel
			FileOutputStream outFile = new FileOutputStream(strExcelFile);
			wb.write(outFile);
			outFile.close();
		} 
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "covertDOMToExcelpSeq");
		}
	}

	// COMMON FUNCTIONS TO CHECK INDIVIDUAL NODES
	/**
	 *This method is to traverse through each nodes of the XMl and store it in the sheet using the below parameters                        
	 * @param nodelist - Node List
	 * @param iNodeListlength - length of the Nodes 
	 * @param sheet - Work sheet      
	 */
	public void checkIndvNodes(NodeList nodelist, int iNodeListlength, XSSFSheet sheet)
	{
		try {
			XSSFRow row;
			Node childNode;
			NodeList childNodeList;

			// Traversing through XML Nodes
			for (int j = 0; j < nodelist.getLength(); j++) {
				childNode = nodelist.item(j);
				childNodeList = childNode.getChildNodes();

				// Checking XML node having child nodes or not
				if (childNodeList.getLength() > 1) {
					checkIndvNodes(childNodeList, childNodeList.getLength(),
							sheet);
				} else {
					// Storing the node name and its value in the Worksheet
					row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(
							childNode.getNodeName().trim());
					row.createCell(1).setCellValue(
							childNode.getTextContent().trim());
				}
			}
		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "checkIndvNodes");
		}
	}

	/**
	 * This method is to traverse through each nodes of the XMl and store it in the sheet using the below parameters                                       
	 * @param nodelist - Node List
	 * @param iNodeListlength - length of the Nodes
	 * @param sheet - Work sheet
	 * @param strTestcase  -Test case name                      
	 */
	public XSSFSheet checkIndvNodesPseqNum(NodeList nodelist, int iNodeListlength,XSSFSheet sheet, String strTestcase)  
	{
		try {
			XSSFRow row;
			Node childNode;
			NodeList childNodeList;
			int rownum = 0;
			int colnum = 0;

			// Traversing through XML Nodes
			for (int j = 1; j < nodelist.getLength() + 1; j++) {
				childNode = nodelist.item(j - 1);
				childNodeList = childNode.getChildNodes();

				// Checking XML node having child nodes or not
				if (childNodeList.getLength() > 1) 
				{
					sheet = checkIndvNodesPseqNum(childNodeList,childNodeList.getLength(), sheet, strTestcase);
				} else {
					// Storing the node name and its value in the Worksheet
					row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(	childNode.getNodeName().trim() + "+" + strTestcase);
					row.createCell(1).setCellValue(	childNode.getTextContent().trim());							
				}
			}

		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "checkIndvNodesPseqNum");					
		}
		return sheet;
	}

	// Convert XML to String
	/**
	 * This method is to convert DOM to String value 
	 * @param strXMLFile - input XML file  
	 */
	public String convertDOMToString(String strXMLFile)  
	{
		String xmlAsString = "";
		try {

			File xmlFile = new File(strXMLFile);
			Reader fileReader = new FileReader(xmlFile);
			BufferedReader bufReader = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = bufReader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = bufReader.readLine();
			}
			xmlAsString = sb.toString();						
		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "convertDOMToString");					
		}
		return xmlAsString;
	}

	/**
	 * This method is to remove the specified tag from XML and get response  
	 * @param strBasexml - Permanent XML
	 * @param strPath - Location of the sheet
	 * @param occr - Occurrence
	 * @param rmvtg  - Tag to be deleted                                  
	 */

	public static void deleteChildTag(String strBasexml, String strPath, int occr,String rmvtg)  
	{
		Report objReport=new Report();
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(strBasexml));

			int j = 1;
			int p = 1;
			Element table = doc.getDocumentElement();
			Node row3 = table.getElementsByTagName(strTagWithOccurenceUpd).item(0);
			Node rowCom = row3;
			NodeList childList = null;
			if(intOccurUpd==0){
				childList = row3.getChildNodes();
			}
			else{

				while(j<intOccurUpd)
				{
					if(row3.getNodeName().equals(rowCom.getNextSibling().getNodeName()))
					{
						j++;
						row3=rowCom.getNextSibling();
					}
					else
						rowCom=rowCom.getNextSibling();
				}
				childList = row3.getChildNodes();
			}

			// Looking through all children nodes
			for (int x = 0; x < childList.getLength(); x++) {
				if(!childList.item(x).getNodeName().equalsIgnoreCase(rmvtg))
					continue;
				Node child = childList.item(x);
				Node childCom = child;

				// To search only "identifier_1:idValue" children
				//if (occr==0 && child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equalsIgnoreCase(arrInputParm.get(2)) && child.getTextContent().equalsIgnoreCase(arrInputParm.get(3))) 
				if (occr==0 && child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equalsIgnoreCase(arrInputParm.get(2))) 				
				{
					row3=row3.removeChild(child);
					break;
				}
				else{
					while(p<occr)
					{
						if(child.getNodeName().equalsIgnoreCase(childCom.getNextSibling().getNodeName()))
						{
							p++;
							child=childCom.getNextSibling();
						}
						else
							childCom=childCom.getNextSibling();
					}
					row3=row3.removeChild(child);
					break;
				}
			}

			// collection of the nodes to delete child
			List<Node> delete = new ArrayList<Node>();

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(strPath));
		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "postSSLWebserviceCall(POST)");				
		}
	}

	/**
	 * This method is to remove Parent tag from XML and get response   
	 * @param strBasexml - Permanent XML
	 * @param strPath - Location of the sheet                                   
	 */

	public static void deleteParentTag(String strBasexml, String strPath) 
	{
		Report objReport=new Report();
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Document document1 = dbf.newDocumentBuilder().parse(new File(strBasexml));
			int i=1;

			// -------------- finding the right node and removing it -----------------
			Element table = document1.getDocumentElement();
			Node row3 = table.getElementsByTagName(strTagWithOccurenceUpd).item(0);
			Node rowCom = row3;
			if(intOccurUpd==0)
			{
				table.removeChild(row3);
			}
			else
			{
				while(i<intOccurUpd)
				{
					if(row3.getNodeName().equals(rowCom.getNextSibling().getNodeName()))
					{
						i++;
						row3=rowCom.getNextSibling();
					}
					else
						rowCom=rowCom.getNextSibling();
				}
				table.removeChild(row3);
			}

			// -------------- printing the resulting tree to the console -------------
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.transform(new DOMSource(document1), new StreamResult(strPath));
		}

		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "deleteParentTag");
		}
	}

	/**
	 *  This method is to modify the required tag values by using the below parameters
	 * @param strBasexmlLocation   - Permanent XML   
	 * @param strInputDataSheet - Input data details
	 * @param strinputXML - Input XML
	 * @param strWorksheetName - Worksheet name
	 * @param strTestcaseName - Test case name
	 */
	public void modifyMultipleTagValue(String strBasexmlLocation,String strInputDataSheet,String strinputXML, String strWorksheetName,String strTestcaseName)
	{
		try
		{
			String str3 = null;
			String str4 =null;
			String str5 = null;
			FileInputStream io = new FileInputStream(strInputDataSheet);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet(strWorksheetName);

			int lastRowNum = sheet.getLastRowNum();

			int i2;
			for (i2 = 1; i2 <= lastRowNum; i2++) {
				int colmncnt = sheet.getRow(i2).getLastCellNum();
				// Getting the Test Case Name from the 1st Column cells of Input
				// Data Sheet
				String TestCaseId = sheet.getRow(i2).getCell(0).getStringCellValue()
						.trim();
				if (TestCaseId.equalsIgnoreCase(strTestcaseName)) {
					str3 = sheet.getRow(i2).getCell(3).getStringCellValue().trim();
					str5 = sheet.getRow(i2).getCell(4).getStringCellValue().trim();
					str4 = sheet.getRow(i2).getCell(5).getStringCellValue().trim();
				}
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(strBasexmlLocation);
			//NodeList nodes1 = doc.getElementsByTagName("*");

			//Get the staff element by tag name directly "H:\\Data\\Multiple_PPID_Request.xml"
			NodeList nodeP2List = doc.getElementsByTagName("*");
			//loop the staff child node 
			System.out.println(nodeP2List.getLength());

			for (int i = 0; i != nodeP2List.getLength(); ++i)
			{
				NodeList nodeP3List = doc.getElementsByTagName(str3);

				for (int j = 0; j != nodeP2List.getLength(); ++j){

					org.w3c.dom.Node child = nodeP3List.item(j);
					System.out.println(j);
					int str4int = Integer.parseInt(str4);
					if (j==str4int) {
						System.out.println(child.getTextContent());
						child.setTextContent(str5);

						// Transforming the XML Source to a Result
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(strinputXML);
						transformer.transform(source, result);
						System.out.println("tag val modified successfully");
					}
				}
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "modifyMultipleTagValue");
		}
	}

	/**
	 *Coping content from permanent XML(inputXMLPerm) to temporary XML(inputXML)   
	 * @param inputXMLPerm - Permanent XML
		           @param inputXML - Temporary XML
	 */
	public void copyPermXMLToTempXML(String inputXMLPerm, String inputXML) throws Exception
	{
		FileInputStream instream = null;
		FileOutputStream outstream = null;

		try {
			File infile = new File(inputXMLPerm);
			File outfile = new File(inputXML);

			instream = new FileInputStream(infile);
			outstream = new FileOutputStream(outfile);

			byte[] buffer = new byte[1024];

			int length;
			/*
			 * copying the contents from input stream to output stream using
			 * read and write methods
			 */
			while ((length = instream.read(buffer)) > 0)
			{
				outstream.write(buffer, 0, length);
			}

			// Closing the input/output file streams
			instream.close();
			outstream.close();

			System.out.println("File copied successfully!!");

		} 
		catch (Exception e)
		{						
			objReport.writeStackTraceErrorInReport(e, "copyPermXMLToTempXML");				
		}

	}

	/**
	 * This method is to update the XML tags and values according to scenario and the inputs provided in the datasheet
	 * @param strPath - Path of the xml 
	 * @param xmpUpdateParms - operation that needs to be carried out 
	 * @param strBasexmlLocation - Base XML location     
	 */

	public static void xmlUpdateNew(String strPath, String xmpUpdateParms,  String strBasexmlLocation, String strScriptName)  
	{
		// TODO Auto-generated method stub
		Report objReport=new Report();

		try {
			File file = new File(strPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder document = docFactory.newDocumentBuilder();
			doc = document.parse(file);
			strPathnew = strPath;
			StringBuilder sb = new StringBuilder(xmpUpdateParms);
			String[] sbArr;
			/*
			 * Takes the input string and splits it based on the forward slash
			 * Converts the string array into a list and then checks whether the
			 * first item is having the option to addParent, addChild,
			 * UpdtChild.
			 */
			sbArr = sb.toString().split("/");
			arrInputParm = new ArrayList<String>(Arrays.asList(sbArr));

			//For adding Parent node
			if (arrInputParm.get(0).contains("addParent")) 
			{
				/*
				 * Splits the second item in the list that has the node path
				 * separated by semi-colon Converts the String array created
				 * into an array list
				 */
				String[] tagNames = arrInputParm.get(1).split(";");
				ArrayList<String> tagNamesParm = new ArrayList<String>(Arrays.asList(tagNames));


				if (tagNamesParm.get(tagNamesParm.size() - 1).contains(","))
				{
					String[] strLastTag = tagNamesParm.get(tagNamesParm.size() - 1).split(",");
					strTagWithOccurence = strLastTag[0];
					intOccur = (int) Integer.parseInt(strLastTag[1]);
				} 
				else 
				{
					intOccur = 0;
					strTagWithOccurence= tagNamesParm.get(tagNamesParm.size() - 1);
				}
				/* Takes all the nodes in the document into a list */
				NodeList nl = doc.getChildNodes();

				/*
				 * For each node does a check to match the first tagName from
				 * the tagName arraylist For all the nodes that match the first
				 * tagName, it makes a call to the Recursive Search Parent with
				 * the node, the tagNames array list and integer 0 which will
				 * get incremented in subsequent recursive calls of the function
				 * Recursive Search Parent.
				 */
				for (int i = 0; i < nl.getLength(); i++)
				{
					if (nl.item(i).getNodeName().equals(tagNamesParm.get(0))) 
					{
						RecursiveSearchParent(nl.item(i), tagNamesParm, strTagWithOccurence, 0, intOccur );
					}
				}

			} 

			//For adding Child node
			else if (arrInputParm.get(0).contains("addChild")) 
			{
				String[] tagNames = arrInputParm.get(1).split(";");
				ArrayList<String> tagNamesParm = new ArrayList<String>(Arrays.asList(tagNames));
				/*
				 * In this section, we are trying to check whether the node path
				 * has any occurence mentioned for the last child present in
				 * tagNamesParm list separated by comma. The comma separated Tag
				 * Name and Occurence to be picked up for the node is added into
				 * strTagWithOccurence and intOccur respectively. If last item
				 * in the list is not comma separated, then we pass 0 to
				 * intOccur and the last item in the list to
				 * strTagWithOccurence.
				 */
				if (tagNamesParm.get(tagNamesParm.size() - 1).contains(",")) 
				{
					String[] strLastTag = tagNamesParm.get(tagNamesParm.size() - 1).split(",");
					strTagWithOccurence = strLastTag[0];
					intOccur = (int) Integer.parseInt(strLastTag[1]);
					System.out.println("inrOccur " +intOccur);
					// System.out.println("Tag with Occurence number "+strTagWithOccurence);
				} 
				else 
				{
					intOccur = 0;
					strTagWithOccurence = tagNamesParm.get(tagNamesParm.size() - 1);
				}

				NodeList nl = doc.getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) 
				{
					if (nl.item(i).getNodeName().equals(tagNamesParm.get(0))) 
					{
						System.out.println("The node is n "+nl.item(i).getNodeName());
						RecursiveSearchAddChild(nl.item(i), tagNamesParm,strTagWithOccurence, 0, intOccur);
					}
				}
			} 

			//For updating child node
			else if (arrInputParm.get(0).contains("updtChild")) 
			{
				String[] tagNames = arrInputParm.get(1).split(";");
				if(tagNames.length>1)
				{
					String passwordType = tagNames[1];
				}
				ArrayList<String> tagNamesParm = new ArrayList<String>(Arrays.asList(tagNames));
				String childValue = "";
				if(arrInputParm.size()<4 || arrInputParm.get(arrInputParm.size()-1).equalsIgnoreCase("null"))
				{
					arrInputParm.add(arrInputParm.size(), "Blank value");
				}
				else
				{
					childValue = arrInputParm.get(arrInputParm.size()-1);

					if(childValue.contains("{")){
						int startIndex = childValue.indexOf("{")+1;
						int lastIndex = childValue.lastIndexOf("}");

						String tagDetails = childValue.substring(startIndex, lastIndex);
						String ArrTagname[] = tagDetails.split(";");
						int outputIndex = Integer.parseInt(ArrTagname[0]);
						int index = Integer.parseInt(ArrTagname[2]);
						childValue = childValue.replace("{"+tagDetails+"}", multipleConcatURLPost(ArrTagname[1], outputIndex, index, strScriptName));
						System.out.println(childValue);
					}
					if(arrInputParm.get(3).contains("#"))
					{
						//childValue=arrInputParm.get(tagNamesParm.size()-1).replace("#", "/");
						childValue=arrInputParm.get(3).replace("#", "/");
						arrInputParm.remove(tagNamesParm.size()-1);
						arrInputParm.add(tagNamesParm.size()-1, childValue);
						System.out.println(arrInputParm);
					}
				}
				if (tagNamesParm.get(tagNamesParm.size() - 1).contains(",")) 
				{
					String[] strLastTag = tagNamesParm.get(tagNamesParm.size() - 1).split(",");
					strTagWithOccurenceUpd = strLastTag[0];
					intOccurUpd = (int) Integer.parseInt(strLastTag[1]);
				} 
				else 
				{
					intOccurUpd = 0;
					strTagWithOccurenceUpd = tagNamesParm.get(tagNamesParm.size() - 1);
				}
				NodeList nl = doc.getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) 
				{
					if (nl.item(i).getNodeName().equals(tagNamesParm.get(0)))
					{
						System.out.println("The node is n "+nl.item(i).getNodeName());
						RecursiveSearchAddChildUpd(nl.item(i), tagNamesParm,strTagWithOccurenceUpd, 0, intOccurUpd, passwordType, childValue);
					}

				}
			}

			//For deleting Parent node
			else if(arrInputParm.get(0).contains("deleteParent")) 
			{
				String [] tagNames = arrInputParm.get(1).split(";");
				ArrayList<String> tagNamesParm =new ArrayList<String>(Arrays.asList(tagNames));
				if(tagNamesParm.get(tagNamesParm.size()-1).contains(","))
				{
					String [] strLastTag = tagNamesParm.get(tagNamesParm.size()-1).split(",");
					strTagWithOccurenceUpd =strLastTag[0]; 
					intOccurUpd = (int) Integer.parseInt(strLastTag[1]);
				}
				else
				{
					intOccurUpd =0;
					strTagWithOccurenceUpd = tagNamesParm.get(tagNamesParm.size()-1);
				}	 
				deleteParentTag(strBasexmlLocation,strPath);
			}
			else if(arrInputParm.get(0).contains("deleteChild")) 
			{
				String [] tagNames = arrInputParm.get(1).split(";");
				String [] removeTag = arrInputParm.get(2).split(";");

				ArrayList<String> tagNamesParm =new ArrayList<String>(Arrays.asList(tagNames));
				ArrayList<String> tagNamesParmRemove =new ArrayList<String>(Arrays.asList(removeTag));

				if(tagNamesParmRemove.get(tagNamesParmRemove.size()-1).contains(","))
				{
					String [] strTagOccr = tagNamesParmRemove.get(tagNamesParmRemove.size()-1).split(",");
					tagRemove = strTagOccr[0];
					occr = (int) Integer.parseInt(strTagOccr[1]);
				}

				else{
					tagRemove = tagNamesParmRemove.get(tagNamesParmRemove.size()-1);
					occr = 0;
				}
				if(tagNamesParm.get(tagNamesParm.size()-1).contains(","))
				{
					String [] strLastTag = tagNamesParm.get(tagNamesParm.size()-1).split(",");
					strTagWithOccurenceUpd =strLastTag[0]; 
					intOccurUpd = (int) Integer.parseInt(strLastTag[1]);
				}
				else
				{
					intOccurUpd =0;
					strTagWithOccurenceUpd = tagNamesParm.get(tagNamesParm.size()-1);
				}	 
				deleteChildTag(strBasexmlLocation,strPath ,occr,tagRemove);
			}

		} 
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "xmlUpdateNew");
		}
	}



	public static String multipleConcatURLPost(String tagName, int outputIndex, int index, String strScriptName) throws Exception {

		String strDataSheet=Runner.properties.getProperty("APIResponseDSFolderPath")+strScriptName+"_Data_Sheet.xlsx";
		if(outputIndex>1){
			strDataSheet=Runner.properties.getProperty("APIResponseDSFolderPath")+strScriptName+"_Data_Sheet"+(outputIndex-1)+".xlsx";
		}

		//Create workbook object for excel file located in the path  specified by strDataSheet
		FileInputStream io = new FileInputStream(strDataSheet);
		XSSFWorkbook wb = new XSSFWorkbook(io);

		//Create work sheet object for the work sheet specified by 'strWorksheet'
		XSSFSheet sheet = wb.getSheet("Output");

		int rowNum = sheet.getLastRowNum()+1;
		int flag=0;
		int fld_loc = 1;
		String tagValue="";
		for	(fld_loc=1;fld_loc<rowNum;fld_loc++)
		{
			if(sheet.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(tagName))
			{
				flag++;
				if(flag==index){
					tagValue = sheet.getRow(fld_loc).getCell(1).getStringCellValue();
					break;
				}
			}							
		}

		return tagValue;
	}

	/**
	 * This method is used to search the desired tag name using the below parameters    
	 * @param nl - Node
	 * @param str - Contains an array of Tags used to traverse 
	 * @param strLastTag - Last tag name
	 * @param iCounter - No of Count
	 * @param iOccur -  Count of the Tags if it is present multiple times                             
	 */

	public static void RecursiveSearchParent(Node nl, ArrayList<String> str, String strLastTag,int iCounter,int iOccur) throws TransformerException 
	{
		Report objReport=new Report();
		try{

			NodeList childNodes = nl.getChildNodes();
			if (iCounter < str.size() - 1)
			{
				iCounter++;
			}
			for (int i = 0; i < childNodes.getLength(); i++)
			{
				if (childNodes.item(i).getNodeName().contains(str.get(iCounter)))
				{
					RecursiveSearchParent(childNodes.item(i), str, strTagWithOccurence, iCounter, iOccur);
				}
			}
			if (iCounter == str.size() - 1)
			{
				for (int k = 0; (k < childNodes.getLength()); k++)
				{
					if ((childNodes.item(k).getNodeType() == 1)&& childNodes.item(k).getNodeName().equals(strTagWithOccurence))
					{
						// Function to append child
						xmlAppendTag(childNodes.item(k),arrInputParm.get(arrInputParm.size() - 1),intOccur);

						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(strPathnew);
						transformer.transform(source, result);
						arrInputParm.clear();
						break;
					}
				}
			}
		}

		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "RecursiveSearchParent");
		}
	}

	/**
	 * This method is to append the XMl Tag as per position   
	 * @param nl - Node 
	 * @param str - Tag to append
	 * @param iOccur -  Count of the Tags if it is present multiple times                                   
	 */

	public static void xmlAppendTag(Node nl, String str, int iOccur) 
	{
		Report objReport=new Report();
		try
		{
			Node newNode = doc.createElement(str);
			//nl.appendChild(newNode);
			NodeList childNodes = doc.getElementsByTagName(nl.getNodeName());
			if(iOccur==0){
				childNodes.item(childNodes.getLength() - 1).appendChild(newNode);
			}
			else{
				childNodes.item(iOccur - 1).appendChild(newNode);		
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "xmlAppendTag");
		}
	}

	/**
	 *This method is to search for the desired child tag to perform actions such as add child tag, delete child tag or update the child tag                                        
	 * @param nl - Node
	 * @param str - Contains an array of Tags used to traverse 
	 * @param strLastTag - Last tag name 
	 * @param iCounterChild - No of child tags
	 * @param iCounter - No of Count
	 * @param iOccur -  Count of the Tags if it is present multiple times  
	 */

	public static void RecursiveSearchAddChild(Node nl, ArrayList<String> str,String strLastTag, int iCounterChild, int iOccur)throws TransformerException
	{
		Report objReport=new Report();
		try
		{
			NodeList childNodes = nl.getChildNodes();
			//iSkip=true;
			if (iCounterChild < str.size() - 1)
			{
				iCounterChild++;
				iSkip=false;
			}


			for (int i = 0; i < childNodes.getLength()&&!iSkip; i++)
			{
				if(str.get(iCounterChild).contains(","))
				{
					//String str1 = str.get(iCounterChild).split(",")[0];
					if (childNodes.item(i).getNodeName().equalsIgnoreCase(str.get(iCounterChild).substring(0, str.get(iCounterChild).length()-2)))
					{
						System.out.println("The node is "+childNodes.item(i).getNodeName());
						RecursiveSearchAddChild(childNodes.item(i), str,strTagWithOccurence, iCounterChild, iOccur);
					}
				}
				else if (childNodes.item(i).getNodeName().equalsIgnoreCase(str.get(iCounterChild)))
				{
					System.out.println("The node is "+childNodes.item(i).getNodeName());
					RecursiveSearchAddChild(childNodes.item(i), str,strTagWithOccurence, iCounterChild, iOccur);
				}
			}

			for (int k = 0; (k < childNodes.getLength()) &&!iSkip; k++)
			{
				if ((childNodes.item(k).getNodeType() == 1)&& childNodes.item(k).getNodeName().equals(strTagWithOccurence))
				{
					// Function to append child
					xmlAppendChildTagValue(childNodes.item(k),arrInputParm.get(arrInputParm.size() - 2),arrInputParm.get(arrInputParm.size() - 1), intOccur);

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(strPathnew);
					transformer.transform(source, result);
					arrInputParm.clear();
					break;
				}

			}

		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "RecursiveSearchAddChild");
		}
	}

	/**
	 * This method is to fetch the tag value of a child tag 
	 * @param nl - Node
	 * @param str - List of Tags 
	 * @param strVal - tags to be stretched 
	 * @param iOccur - Count of the Tags if it is present multiple times                                 
	 */
	public static void xmlAppendChildTagValue(Node nl, String str,String strVal, int iOccur)
	{
		Report objReport=new Report();
		try
		{
			Node newNode = doc.createElement(str);
			NodeList childNodes = doc.getElementsByTagName(nl.getNodeName());
			childNodes.item(childNodes.getLength() - 1).appendChild(newNode).setTextContent(strVal);
			iSkip = true;
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "xmlAppendChildTagValue");
		}
	}

	/**
	 * This method is to search for the desired child tag to perform updation of the child tag     
	 * @param nl - Node
	 * @param str - Contains an array of Tags used to traverse 
	 * @param strLastTag - Last tag name 
	 * @param iCounterChild - No of child tags
	 * @param iCounter - No of Count
	 * @param iOccur -  Occurrences 
	 * @param passwordType  - Contains Password                                                                         
	 */
	public static void RecursiveSearchAddChildUpd(Node nl,ArrayList<String> str, String strLastTag, int iCounter, int iOccur, String passwordType, String childValue) throws TransformerException
	{
		Report objReport=new Report();
		try
		{
			NodeList childNodes = nl.getChildNodes();
			Boolean chKParentTagOccrFlag=true;

			if (iCounter < str.size() - 1)
			{
				iCounter++;

				for (int i = 0; i < childNodes.getLength(); i++)
				{
					//System.out.println("The test node is "+childNodes.item(i).getNodeName());
					if (str.get(iCounter).contains(childNodes.item(i).getNodeName()))
					{
						//System.out.println("The node is "+childNodes.item(i).getNodeName());
						RecursiveSearchAddChildUpd(childNodes.item(i), str,strTagWithOccurenceUpd, iCounter, iOccur, passwordType, childValue);
					}
				}

			}
			else
			{
				chKParentTagOccrFlag=false;
			}

			int occCnt=0;
			for (int k = 0; (k < childNodes.getLength()); k++) {
				//System.out.println("The node value is " +childNodes.item(k).getNodeName());

				if(childNodes.item(k).getNodeName().equals(strTagWithOccurenceUpd))
				{					
					if(iOccur >0 && k>1)
					{
						occCnt=occCnt+1;
					}				
				}

				if (chKParentTagOccrFlag==true)
				{	
					if ((childNodes.item(k).getNodeType() == 1) && childNodes.item(k).getNodeName().equals(strTagWithOccurenceUpd) && occCnt==iOccur)
					{
						// Function to Update child
						if(childNodes.item(k).getNodeName().equalsIgnoreCase(arrInputParm.get(arrInputParm.size() - 2)))
						{
							xmlUpdateChildTagValue(nl,arrInputParm.get(arrInputParm.size() - 2),childValue, intOccurUpd,passwordType);
						}
						else
						{
							xmlUpdateChildTagValue(childNodes.item(k),arrInputParm.get(arrInputParm.size() - 2),childValue, intOccurUpd,passwordType);
						}

						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource(doc);
						StreamResult result = new StreamResult(strPathnew);
						transformer.transform(source, result);
						arrInputParm.clear();
						break;
					}
				}
			}

		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "RecursiveSearchAddChildUpd");
		}
	}

	/**
	 * This method is to validate the password and update teh child tag accordingly using the below parameters 
	 * @param nl - Node
	 * @param strTag - Contains a tag name
	 * @param strTagVal - Tag value
	 * @param iOccur -  Occurrences 
	 * @param passwordType  - Contains Password                                  
	 */
	public static void xmlUpdateChildTagValue(Node nl, String strTag,String strTagVal, int iOccur, String passwordType) 
	{
		Report objReport=new Report();
		try
		{
			if(passwordType.equalsIgnoreCase("currentPassword"))
			{
				cP = strTagVal;
			}
			else if(passwordType.equalsIgnoreCase("newPassword"))
			{
				nP = strTagVal;
			}

			if(cP!=null && nP!=null){
				if ((nP.length() <= 8) || (nP.length() > 20) && !(nP.matches(".*[a-zA-Z]+.*")) || !(nP.matches(".*[0-9]+.*")) )
				{
					System.out.println("New password constraints not fulfilled: must be of length 8 to 20, with 1 alphabet & 1 number.");
					objReport.setValidationMessageInReport("PASS","Getting expected Webservice API Response value  for the Password validation  like 'New password constraints not fulfilled: must be of length 8 to 20, with 1 alphabet & 1 number.'"); //LOG ERROR NEW			
				}
				if(cP.equals(nP))
				{ 
					System.out.println("old & new password cannot be same");
					//	objReport.write_params_for_Keyword_in_Report(strStartTime, strEndTime, keyword)
					//objReport.setValidationMessageInReport("'"+"Password validation " +"'  field Validation " + "Expected value: " +"Current password & new password should not be same","Webservice API Response value : "+"New password and current password are same.", "PASS"); //LOG ERROR NEW			
					objReport.setValidationMessageInReport("FAIL","Webservice API Response value : New password :"+nP+" and current password : "+cP+" are same."); //LOG ERROR NEW			

					//return;
				}

			}

			if(strTag.equalsIgnoreCase("groupId")){
				if(strTagVal.equalsIgnoreCase("Nav") || strTagVal.equalsIgnoreCase("ngx") || strTagVal.equalsIgnoreCase("01")){
					grpId = strTagVal;

					//return;
				}
				else{
					///								System.out.println("Invalid Group Id in Datasheet");
					//Report objReport=new Report();  // LOG ERROR NEW
					//objReport.setValidationMessageInReport("'"+"Password validation " +"'  field Validation " + "Expected value: " +"Group ID Entered should be Valid.","Webservice API Response value : "+"Invalid Group Id in Datasheet.", "FAIL"); //LOG ERROR NEW			
					//objReport.setValidationMessageInReport("FAIL","Invalid Group Id in Datasheet."); //LOG ERROR NEW			


				}
			}

			if(strTag.equalsIgnoreCase("questionId")){
				System.out.println(strTagVal);
				int s=Integer.parseInt(strTagVal);
				if(grpId.equalsIgnoreCase("Nav") || grpId.equalsIgnoreCase("Ngx")){
					if(s<1008 || s>1014){
						System.out.println("Invalid Question Id in Datasheet");
						objReport.setValidationMessageInReport("FAIL","Invalid Question Id in Datasheet"); 		
					}
				}
				else if(grpId.equalsIgnoreCase("01")){
					//return;
					if(s<2001 || s>2015){
						System.out.println("Invalid Question Id in Datasheet");
						objReport.setValidationMessageInReport("FAIL","Invalid Question Id in Datasheet"); 			
					}
				}
			}

			NodeList nChildNodes = nl.getChildNodes();

			for (int iCounter = 0; iCounter < nChildNodes.getLength(); iCounter++) {
				if (nChildNodes.item(iCounter).getNodeName().equals(strTag)) {
					// System.out.println("Match Found");
					if (strTagVal.equalsIgnoreCase("null") || strTagVal.equalsIgnoreCase("Blank value")) {
						strTagVal = "";
					}
					nChildNodes.item(iCounter).setTextContent(strTagVal);
					iSkipUpdate=true;
					break;
				}
			}
		}

		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "xmlUpdateChildTagValue");
		}
	}

	/**
	 * This method is to convert the input XML tag values to JSOn input values  
	 * @param BaseJsonLoc - Node
	 * @param InputFolderPath - Contains a tag name
	 * @param strScriptName - Test case name
	 */
	public StringBuilder JSON_XML(String BaseJsonLoc, String InputFolderPath, String strScriptName) throws FileNotFoundException, IOException, JSONException
	{
		// TODO Insert code here

		StringBuilder sb=null;

		//Read JSON File
		try{
			InputPath=BaseJsonLoc;
			String json = readFile(BaseJsonLoc);//Read File


			//Convert JSON to XML
			String xml = convert(json, "root");//State name of root element tag

			//String replaceValue ="John";   
			//String toReplace = "xyz";

			//String xml = replaceToReplace(jsonStringValue,replaceValue,toReplace);
			/*System.out.println(xml);*/
			//Write XML File
			writeFile(InputFolderPath, xml, strScriptName ,"xml");
			/*int first = jsonStringValue.indexOf(tagName)+tagName.length()+4;
		    System.out.println(first);
		    String outputValue = jsonStringValue.repla
			 */


			BufferedReader br = new BufferedReader(new FileReader(new File(InputFolderPath+"\\\\"+strScriptName+"_xml_.xml")));
			String line;
			sb = new StringBuilder();

			while((line=br.readLine())!= null){
				sb.append(line.trim());
			}						
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "JSON_XML");
		}
		return sb;
	}

	/**
	 * This method is to convert the duplicate tag values to unique one  
	 * @param json - Node
	 * @param root - Contains a tag name
	 */

	public String convert(String json, String root) throws JSONException
	{
		//int q=1;
		/*json= json.substring(2, json.length() - 2);
			int i=1; 


			Map<String, List<String>> map = new HashMap<String, List<String>>();
			String[] values = json.split("},");
			System.out.println(values.length);
			for (;i<=(values.length-1);i++){
				System.out.println(values[i]);
				if(values[i].toString().contains(": {"))
				{
					String[] keyValue = values[i].split(":\\s[{]");
					StringBuilder key = new StringBuilder(keyValue[0]);
					StringBuilder value = new StringBuilder(keyValue[1]);
					if(value.toString().contains(": [{")){
						String[] keyValue1 = value.toString().split(":\\s\\[[{]");
						key = new StringBuilder(keyValue1[0]);
						value = new StringBuilder(keyValue1[1]);	
						if (!map.containsKey(key)) {
							map.put(key.toString(), new ArrayList<String>());
						}
				    		map.get(key.toString()).add(value.toString());
					}
					if(value.toString().contains(":")){
						String[] keyValue1 = value.toString().split(":");
						key = new StringBuilder(keyValue1[0]);
						value = new StringBuilder(keyValue1[1]);
						if (!map.containsKey(key)) {
							map.put(key.toString(), new ArrayList<String>());
						}
				    		map.get(key.toString()).add(value.toString());

					}*/

		/*if (!map.containsKey(key)) {
						map.put(key.toString(), new ArrayList<String>());
					}
			    		map.get(key.toString()).add(value.toString());
		}*/



		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n<"+root+">";
		org.json.JSONObject jsonFileObject =null;
		try{
			jsonFileObject=new org.json.JSONObject(json);
		}
		catch(Exception e){
			if (e.getMessage().contains("Duplicate key")){



				jsonFileObject=(JSONObject) convertduplicatetounique(json,e.getMessage());
			}
		}
		xml=xml+org.json.XML.toString(jsonFileObject)+ "</"+root+">";



		//String jsonString1=map.toString();
		/*JSONObject jsonObjt = new JSONObject(json);

		    String xml = XML.toString(jsonObjt);*/



		System.out.println(actual_values.size());
		int j=0;
		for(int i=0;i<actual_values.size();i++){
			if(i>=2 && !actual_values.get(i).equals(actual_values.get(i-1))){
				j=0;
			}
			System.out.println(actual_values.get(i)+"_"+j);
			System.out.println(actual_values.get(i));
			xml=xml.replaceAll(actual_values.get(i)+"_"+j, actual_values.get(i));
			j++;
		}
		System.out.println(xml);

		return xml;

	}   

	/**
	 * @param strInputDataSheetTemp - temporary datasheet
	 * @param strTagResponseData - 
	 * @param pConstraintDataCapture - 
	 * @param strTestcase  - Test case name                              
	 */
	public ArrayList<String> readpConvertStringData(String strInputDataSheetTemp, String strTagResponseData,String pConstraintDataCapture, String strTestcase) 
	{
		try
		{
			FileInputStream io = new FileInputStream(strInputDataSheetTemp);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet("Output");

			ArrayList<String> tempData = new ArrayList<String>();
			String tempData1 = "";
			String constrainDatFrmXLS = "";
			int Colnum = 0;// (Integer.parseInt(strTestcase)-1)*2;
			Boolean selectFlag = false;
			String Constraintagname = "";
			String ConstraintTagVAl = "";
			if (pConstraintDataCapture == null
					|| pConstraintDataCapture.trim().equalsIgnoreCase("".trim())) {
				selectFlag = true;
			} else {
				Constraintagname = pConstraintDataCapture.split("@")[0] + "+"
						+ (strTestcase);
				ConstraintTagVAl = pConstraintDataCapture.split("@")[1];
			}
			int rowNum = sheet.getLastRowNum() + 1;

			for (int i = 1; i < rowNum; i++) {
				if ((selectFlag == false)
						&& sheet.getRow(i).getCell(Colnum).toString().trim()
						.equalsIgnoreCase(Constraintagname.trim())) {
					constrainDatFrmXLS = sheet.getRow(i).getCell(Colnum + 1)
							.toString().trim();
					if (sheet.getRow(i).getCell(Colnum + 1).toString().trim()
							.equalsIgnoreCase(ConstraintTagVAl.trim())) {
						selectFlag = true;
					}
				}
				if (((selectFlag) || constrainDatFrmXLS.trim().equalsIgnoreCase(
						"".trim()))
						&& sheet.getRow(i)
						.getCell(Colnum)
						.toString()
						.trim()
						.equalsIgnoreCase(
								strTagResponseData.trim() + "+"
										+ strTestcase)) {

					tempData.add(sheet.getRow(i).getCell(Colnum + 1).toString()
							.trim());
					selectFlag = false;
					constrainDatFrmXLS = "";

				}

			}
			return tempData;
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "readpConvertStringData");
			return null;
		}
	}

	/**
	 * This method is to     
	 * @param pApLink
	 * @param pUserdata                               
	 */
	public String strlinkpathWithuserdataUpd(String pApLink, String pUserdata) 
	{
		try
		{
			String UserdataArr[] = pUserdata.split(",");

			for (int i = 0; i < UserdataArr.length; i++) {
				while (pApLink.trim().contains(
						("{" + UserdataArr[i].split("@")[0].trim() + "}"))) {
					pApLink = pApLink.replace(
							("{" + UserdataArr[i].split("@")[0].trim() + "}"),
							UserdataArr[i].split("@")[1].trim());
				}
			}
			return pApLink;
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "strlinkpathWithuserdataUpd");
			return null;
		}
	}

	/**
	 * This method is to fetch the XML input values that includes tag name, tag value , operation to be performed on the tags and the path to reach the required tag
	 * @param strTestCase - Test case name
	 * @param strExcelPath - Location of datasheet
	 * @param strWorksheet - Datasheet                       
	 */
	public ArrayList<String> readXMLValues1(String strTestCase,	String strExcelPath, String strWorksheet) 
	{
		ArrayList<String> testList = new ArrayList<String>();
		try {

			String strText = "";
			String strText1 = "";
			String TestCaseId = "";
			int count1 = 0; 

			FileInputStream io = new FileInputStream(strExcelPath);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet(strWorksheet);
			XSSFSheet inputXMLDataSheet = null;
			// Creating 'InputXMLdata' worksheet to store the required data
			// which is used to create Input Xml file

			if (wb.getSheet("InputXMLdata") == null) {
				inputXMLDataSheet = wb.createSheet("InputXMLdata");
			} else {
				inputXMLDataSheet = wb.getSheet("InputXMLdata");
			}

			// Saving the data in the columns of 1st row of 'InputXMLdata'
			// worksheet
			XSSFRow row = inputXMLDataSheet.createRow(0);

			// Saving the Test Case Name in the 1st column of the First row
			row.createCell(0).setCellValue(strTestCase);
			XSSFCell cell;

			// Navigating to Input Data Sheet
			// int cnt=0;
			int cnt1 = 1;

			// Getting the total row count of the Input Data Sheet
			int cnt_Member_ID = sheet.getLastRowNum();

			// Getting the total Column count of the Input Data Sheet
			// int colmncnt=sheet.getRow(2).getLastCellNum();

			int i;
			for (i = 1; i <= cnt_Member_ID; i++) {
				int colmncnt = sheet.getRow(i).getLastCellNum();
				// Getting the Test Case Name from the 1st Column cells of Input
				// Data Sheet
				TestCaseId = sheet.getRow(i).getCell(0).getStringCellValue()
						.trim();

				// If required Test case name match is found , then get the cell
				// value of the 'Operation', 'Path','Tag','Tag value' columns
				// and save it in variable
				if (TestCaseId.equalsIgnoreCase(strTestCase)) {
					for (int i1 = 1; i1 < colmncnt; i1++) {
						if (sheet.getRow(i).getCell(i1).getCellType() == 0) {
							strText1 = String.valueOf(sheet.getRow(i)
									.getCell(i1).getNumericCellValue());
						} else {
							strText1 = sheet.getRow(i).getCell(i1)
									.getStringCellValue().trim();
						}

						if (i1 == 2) {
							// changing after debugging 17th April,2017

							if (strText1.contains("/")) {
								strText1 = strText1.replace("/", ";");
							}
						}

						if(i1==4)
						{
							if(sheet.getRow(i).getCell(4).getStringCellValue().contains("/")){
								strText1=sheet.getRow(i).getCell(4).getStringCellValue().trim();
								strText1=strText1.replace("/", "#");

							}
						}

						if (i1 > 1) {
							strText = strText + "/" + strText1;
						}

						else {
							strText = strText1;
						}
					}

					// Save the 'strText' variable value in the Column
					// cell(Column number is stored in Cnt1 variable) of 1st Row
					// of 'InputXMLdata' worksheet
					cell = row.createCell(cnt1);
					cell.setCellValue(strText);

					// Increment the column number of the 'InputXMLdata'
					// worksheet
					cnt1 = cnt1 + 1;
				}
			}

			// Save the the values in 'InputXMLdata' worksheet and close it
			FileOutputStream outFile = new FileOutputStream(strExcelPath);
			wb.write(outFile);
			outFile.close();

			io = null;
			wb = null;
			sheet = null;

			io = new FileInputStream(strExcelPath);
			wb = new XSSFWorkbook(io);
			sheet = wb.getSheet("InputXMLdata");

			// int rowNum = sheet.getLastRowNum()+1;
			// int colNum = sheet.getRow(0).getLastCellNum();

			String strcellVal = "";
			// for(int m=1;m<rowNum;m++){

			// if
			// (sheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase)){
			int colNum = sheet.getRow(0).getLastCellNum();
			for (int k = 1; k < colNum; k++) {
				strcellVal = sheet.getRow(0).getCell(k).getStringCellValue()
						.trim();
				if (!(strcellVal.equals(""))) {
					testList.add(sheet.getRow(0).getCell(k)
							.getStringCellValue().trim());
				}
			}

		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "readXMLValues1");
		}
		return testList;
	}

	//**********************************************************************************************************************		

	//**********************************************Methods Related to JSON*************************************************		
	/**
	 * This method is to parse the JSON value to String    
	 * @param responseString - JSON Response  
	 * @param sheet - Excel sheet                      
	 */
	public XSSFSheet parseJsonString(String responseString, XSSFSheet sheet) 
	{

		try
		{
			sSheet=sheet;	
			sSheet=APICreateAndExecute.fSheet;
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(responseString.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> field : entrySet){
				String key = field.getKey();
				JsonElement value = jsonObject.get(key);

				if ((value.isJsonNull() || value.isJsonPrimitive())) {

					System.out.println("Key: " + field.getKey() + "\tValue:" + (value.getAsString()).replace("\"", ""));
					//data.put(field.getKey(), field.getValue().toString().replace("\"", ""));
					//System.out.println("map is " +data);
					XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(
							field.getKey());
					row.createCell(1).setCellValue(
							(value.getAsString()).replace("\"", ""));

				}

				else {
					checkJsonValueType1(value);

				}
			}}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "parseJsonString");
		}
		return sSheet;
	}

	/**
	 * This method is to check the type of values containing in a JSON File   
	 * @param value - JSON Node                     
	 */
	public void checkJsonValueType1(JsonElement value) 
	{
		try

		{
			if (value.isJsonObject()) {
				// System.out.println(value.toString());
				// Calling parseJsonString recursively to get
				// <String,String> Key Value pairs.
				parseJsonString(value.toString(),sSheet);

			} else if (value.isJsonArray()) {

				JsonArray jsonArray = value.getAsJsonArray();
				for (JsonElement jsonArrayElement : jsonArray){				
					checkJsonValueType1(jsonArrayElement);
				}
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "checkJsonValueType1");
		}
	}

	/**
	 * This method is to convert the duplicate JSON file object to unique one   
	 * @param json - JSON input
	 * @param e_getMessage  - Message                        
	 */
	public Object convertduplicatetounique(String json, String e_getMessage) {
		org.json.JSONObject jsonFileObject= null;
		try
		{

			String errorMessage=e_getMessage.split("\"")[1];
			int i=0;
			int j=0;
			String json_mod="";
			while (json.indexOf(errorMessage, i)>0)
			{

				System.out.println(json.indexOf(errorMessage));
				i=json.indexOf(errorMessage, i)+errorMessage.length();
				json_mod=json.substring(0,i)+ "_"+j+json.substring(i);
				//modified_values.add(json_mod);
				json=json_mod;
				j++;
				actual_values.add(errorMessage);

			}


			jsonFileObject=new org.json.JSONObject(json);


		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "convertduplicatetounique");
		}

		return jsonFileObject;
	}

	/**
	 * This method is to retrieve all object value from JSON output   
	 * @param jString - JSON response                        
	 */
	public String getValue(String jString)  
	{
		String value = null;

		try
		{

			JsonParser parser = new JsonParser();
			JsonObject object = (JsonObject) parser.parse(jString); 
			// Now json is a JsonObject

			// Get Key:Value pair from JsonObject
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(jString.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> entry : entrySet){
				String key = entry.getKey();
				JsonElement value1 = jsonObject.get(key);

				if (value1.isJsonPrimitive()){
					value=value1.getAsString();
				} else {
					value=value1.toString();
				}
			}
			return value;
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getValue");						
			return null;
		}
	}	

	/**
	 * This method is to retrieve key values from JSON output   
	 * @param json - JSON response                        
	 */
	public String getKey(String json) 
	{

		try{

			String key = null;
			JsonParser parser = new JsonParser();
			JsonObject object = (JsonObject) parser.parse(json);

			// Get Key:Value pair from JsonObject
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(json.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> entry : entrySet){
				key = entry.getKey();
				System.out.println(key);
			}
			return key.toString();
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getKey");
			return null;
		}
	}	

	/**
	 * This method is to parse the JSON output response    
	 * @param responseString - JSON response                  
	 */
	public  Map<String, String> parseJsonString(String responseString)  
	{
		try
		{
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(responseString.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> field : entrySet){
				String key = field.getKey();
				JsonElement value = jsonObject.get(key);

				if ((value.isJsonNull() || value.isJsonPrimitive())) {

					System.out.println("Key: " + field.getKey() + "\tValue:" + value.getAsString());
					data.put(field.getKey(), value.getAsString());
					//System.out.println("map is " +data);
				}

				else {
					checkJsonValueType(value);
				}
			}
			return data; 

		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "parseJsonString");
			return null;
		}
	}	

	/**
	 * This method is to check the type of values containing in a JSON File   
	 * @param value - JSON Node                                     
	 */

	public void checkJsonValueType(JsonElement value)  
	{
		try
		{
			if (value.isJsonObject()) {
				parseJsonString(value.toString());
			} else if (value.isJsonArray()) {
				JsonArray jsonArray = value.getAsJsonArray();
				for (JsonElement jsonArrayElement : jsonArray){				
					checkJsonValueType(jsonArrayElement);
				}
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "checkJsonValueType");
		}
	}

	/**
	 * This method is to store the JSON response into a file location
	 * @param strOutputResponseFolder - Output Location      
	 * @param strTestCase - Test case name
	 * @param responseString  - JSON response       
	 */
	public void storeJsonResponse(String strOutputResponseFolder,String strTestCase, String responseString, int count) 
	{					
		try {
			String outputResponseFile="";
			if(count>1){
				outputResponseFile=strOutputResponseFolder+"\\"+strTestCase+(count-1)+"output.txt";
			}
			else{
				outputResponseFile=strOutputResponseFolder+"\\"+strTestCase+"output.txt";
			}

			File f= new File(outputResponseFile);
			f.createNewFile();

			BufferedWriter out = new BufferedWriter(new FileWriter(outputResponseFile));
			out.write(responseString);  //Replace with the string 
			//you are trying to write  
			out.close();
		}
		catch (IOException e)
		{
			objReport.writeStackTraceErrorInReport(e, "storeJsonResponse");

		}
	}

	/**
	 * This method is to converts XML String to JSON  
	 * @param OutputFolderPath - Output Location     
	 * @param InputXMLPath - Input XML folder location
	 * @param strScriptName  - Test case name      
	 */				
	public String convertToJson(String OutputFolderPath, String InputXMLPath , String strScriptName) 
	{		
		String jsonPrettyPrintString="";
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(InputXMLPath+"\\\\"+strScriptName+"_xml_.xml")));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
				sb=sb.append(line.trim());
			}
			TEST_XML_STRING = sb.toString();
			jsonPrettyPrintString ="";
			JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
			jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			jsonPrettyPrintString = jsonPrettyPrintString.substring(9,jsonPrettyPrintString.length()-1);
			writeFile(OutputFolderPath, jsonPrettyPrintString , strScriptName ,"txt");
		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "convertToJson");
		}
		return jsonPrettyPrintString;
	}

	//*************************************************************************************************************************	
	/**
	 * This method is to splits the input values and regex values
	 * @param strInput - Input value
	 * @param strRegex - Regex value                          
	 */
	public String[] stringsplitter(String strInput, String strRegex) 
	{
		try
		{
			String[] strVar = null;
			if (strInput.contains(strRegex)) {
				strVar = strInput.split(strRegex);
				return strVar;
			} else
				return strVar;
		}
		catch (Exception e) 
		{		
			objReport.writeStackTraceErrorInReport(e, "stringsplitter");
			return null;
		}
	}

	/**
	 * This method is to compare he mapping objects
	 * @param mapA
	 * @param mapB                               
	 */
	public boolean mapsAreEqual(Map<String, Object> mapA, Map<String, Object> mapB) {
		try{
			for (String k : mapB.keySet())
			{
				if (!mapA.get(k).toString().equals(mapB.get(k).toString())) {
					return false;
				}
			} 
			for (String y : mapA.keySet())
			{
				if (!mapB.containsKey(y)) {
					return false;
				}
			} 
		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "mapsAreEqual");
			return false;
		}
		return true;
	}

	/**
	 * This method save the content of file located in the path specified by 'strFilepath' in String varible and return it.
	 * @param strFilepath - Contains File name alongwith path
	 */

	public String readFile(String strFilepath) throws FileNotFoundException, IOException
	{
		String strFileContent="";
		try
		{
			//Create String builder object for storing text value('strFilepath' File contentent value )
			StringBuilder sb = new StringBuilder();

			//Create InputStream for the File specified by strFilepath
			InputStream in = new FileInputStream(InputPath);

			//Create Reader object for reading the File contents line by line
			Charset encoding = Charset.defaultCharset();
			Reader reader = new InputStreamReader(in, encoding);

			//Start reading the file content line by line and save it in stringbuilder object(sb)
			int r = 0;
			while ((r = reader.read()) != -1)//Note! use read() rather than readLine()
				//Can process much larger files with read()
			{
				char ch = (char) r;
				sb.append(ch);
			}

			//Close the InputStream for the File specified by strFilepath
			in.close();

			//close the Reader object
			reader.close();

			//Convert the Stringbuilder object(sb) into String value
			strFileContent=sb.toString();
		}

		catch (Exception e)
		{					
			objReport.writeStackTraceErrorInReport(e, "readFile");
		}
		return strFileContent;
	}

	/**
	 * This method is to write String into a file
	 * @param filepath - file location
					   @param output - Output location
					   @param strScriptName - Test case name
					   @param format - format 
	 */
	public void writeFile(String filepath, String output,String strScriptName , String format) throws FileNotFoundException, IOException
	{
		System.out.println(filepath+"\\\\"+strScriptName+"_"+format+"_."+format);
		FileWriter ofstream = new FileWriter(filepath+"/"+strScriptName+"_"+format+"_."+format);
		try (BufferedWriter out = new BufferedWriter(ofstream)) {
			out.write(output);
		}
	}

	/**
	 * This method is to take input  parameters and map them into an excel sheet
	 * @param hm     List of tag names and values
	 * @param strInputDataSheetTemp - Input datasheet location
	 */
	public void exportMapSSToExcel(Map<String, String> hm, String strInputDataSheetTemp) 
	{

		try
		{
			FileInputStream io = new FileInputStream(strInputDataSheetTemp);
			XSSFWorkbook wb=null;
			wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.createSheet("Output");
			sheet = wb.getSheet("Output");

			// Creating Excel worksheet
			if(sheet ==null){
				sheet = wb.createSheet("Output");
			}

			Set <String> keySet=hm.keySet();

			for (String k: keySet){
				XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
				row.createCell(0).setCellValue(k);
				row.createCell(1).setCellValue(hm.get(k));
			}

			FileOutputStream outFile = new FileOutputStream(strInputDataSheetTemp);
			wb.write(outFile);
			outFile.close();
		}
		catch(Exception e)
		{						
			objReport.writeStackTraceErrorInReport(e, "exportMapSSToExcel");
		}
	}

	/**
	 * This method is to write the response into the excel sheet with sheet name Output
	 * @param strInputDataSheet  - Location of the input datasheet      
	 */

	public void writeExcelOutput(String strInputDataSheet)
	{
		try {

			FileInputStream io1 = new FileInputStream(strInputDataSheet);
			XSSFWorkbook wb1=new XSSFWorkbook(io1);
			XSSFSheet sheet2=wb1.getSheet("output");

			FileInputStream io = new FileInputStream(strInputDataSheet);
			XSSFWorkbook wb=new XSSFWorkbook(io);
			XSSFSheet sheet3=wb.createSheet("output_1");

			XSSFRow row;

			int lastRow = sheet2.getLastRowNum()+1;
			int j=0;
			Boolean rowCreate=true;
			row = sheet3.createRow(j);
			for (int i=0; i<lastRow; i++){
				if (rowCreate){
					row = sheet3.createRow(j);
				}
				if (sheet2.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim() !=null && sheet2.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim().equalsIgnoreCase("Code")){
					String str1 = sheet2.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();

					XSSFCell cellfirst=row.createCell(0);
					cellfirst.setCellValue(str1.trim());
					rowCreate= false;
				}
				else if (sheet2.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim() !=null && sheet2.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim().equalsIgnoreCase("enabled")){
					String str2 = sheet2.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
					XSSFCell cellsec=row.createCell(1);
					cellsec.setCellValue(str2.trim());
					j++;
					rowCreate=true;
				}
			}
			// Saving the values in Excel
			FileOutputStream outFile = new FileOutputStream(strInputDataSheet);
			wb.write(outFile);
			outFile.close();

		}
		catch(Exception e){
			objReport.writeStackTraceErrorInReport(e, "writeExcelOutput");
		}
	}	

	/**
	 * This method is to retrieve all the rows of an excel sheet containing values
	 * @param strExcelFile - Excel sheet file location
	 * @param strTestCase  - Test case name
	 * @param strIterationNum  - Iteration Number                         
	 */
	public int getTestCaseRowNumber(String strExcelFile, String strTestCase,String strIterationNum) 
	{
		int getTestCaseRowNumber = 0;
		try {

			Boolean testCaseFlag = false;
			int intdelLoc = 0;

			FileInputStream io = new FileInputStream(strExcelFile);
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheet("GET");

			// Removing Decimal(.0) from 'strIterationNum' String variable
			String strIterationNum1;
			if (strIterationNum.contains(".")) {
				intdelLoc = strIterationNum.indexOf(".");
				strIterationNum1 = strIterationNum.substring(0, intdelLoc);
			} else {
				strIterationNum1 = strIterationNum;
			}

			// Getting 'pIterationNo' column position in the Excel Worksheet
			int pItrtn_fld_loc = -1;
			do {
				pItrtn_fld_loc++;
			} while (!sheet.getRow(0).getCell(pItrtn_fld_loc)
					.getStringCellValue().trim()
					.equalsIgnoreCase("pIterationNo"));

			// Getting 'TestCase' column position in the Excel Worksheet
			int pTestCase_fld_loc = -1;
			do {
				pTestCase_fld_loc++;
			} while (!sheet.getRow(0).getCell(pTestCase_fld_loc)
					.getStringCellValue().trim()
					.equalsIgnoreCase("pScriptName"));

			int intTotalRowCnt = sheet.getLastRowNum() + 1;

			for (int i = 1; i < intTotalRowCnt; i++) {

				String strItertnNumDataSheet1 = null;
				String strItertnNumDataSheet = null;
				String strTestCaseName = null;

				if (sheet.getRow(i).getCell(pItrtn_fld_loc).getCellType() == 0) {
					strItertnNumDataSheet1 = String.valueOf(sheet.getRow(i)
							.getCell(pItrtn_fld_loc).getNumericCellValue());
					if (strItertnNumDataSheet1.contains(".")) {
						intdelLoc = strItertnNumDataSheet1.indexOf(".");
						strItertnNumDataSheet = strItertnNumDataSheet1
								.substring(0, intdelLoc);
					} else {
						strItertnNumDataSheet = strItertnNumDataSheet1;
					}
				}

				else {
					strItertnNumDataSheet = sheet.getRow(i)
							.getCell(pItrtn_fld_loc).getStringCellValue()
							.trim();
				}

				if (sheet.getRow(i).getCell(pTestCase_fld_loc).getCellType() == 0) {
					strTestCaseName = String.valueOf(sheet.getRow(i)
							.getCell(pTestCase_fld_loc).getNumericCellValue());
				}

				else {
					strTestCaseName = sheet.getRow(i)
							.getCell(pTestCase_fld_loc).getStringCellValue()
							.trim();
				}

				if ((strItertnNumDataSheet.equalsIgnoreCase(strIterationNum1))
						&& (strTestCaseName.equalsIgnoreCase(strTestCase))) {
					testCaseFlag = true;
					getTestCaseRowNumber = i;
					break;
				}
			}

			if (testCaseFlag == false) {
				objReport.setValidationMessageInReport("FAIL","Testcase '" + strTestCase + "' with "+ strIterationNum+ " is not availabe in the Data Sheet"); 
			}

		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getTestCaseRowNumber");
		}

		return getTestCaseRowNumber;
	}

	/**
	 * This method is to update the exception status in datasheet as PASS or FAIL for POST Method in a specified column 
	 * @param strExcelFile - Excel sheet file location
	 * @param rowNum - Row Number  
	 * @param strStatus - Status as Pass or Fail                        
	 */
	public void updateExecStatusExcelPOST(String strExcelFile, int rowNum,	String strStatus) 
	{
		try {

			FileInputStream io = new FileInputStream(strExcelFile);
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheet("POST");

			int pExecStatus_fld_loc = 0;
			do {
				pExecStatus_fld_loc++;
			} while (!sheet.getRow(0).getCell(pExecStatus_fld_loc)
					.getStringCellValue().trim()
					.equalsIgnoreCase("Execution Status"));

			if (rowNum > 0) {
				sheet.getRow(rowNum).createCell(pExecStatus_fld_loc)
				.setCellValue(strStatus);
			}

			FileOutputStream outFile = new FileOutputStream(strExcelFile);
			wb.write(outFile);
			outFile.close();
		}

		catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "updateExecStatusExcelPOST");
		}

	}

	/**
	 * This method is to update the exception status in datasheet as PASS or FAIL for GET Method in a specified column 
	 * @param strExcelFile - Excel sheet file location
	 * @param rowNum - Row Number  
	 * @param strStatus - Status as Pass or Fail                          
	 */
	public void updateExecStatusExcelGET(String strExcelFile, int rowNum,String strStatus) 
	{
		try {

			FileInputStream io = new FileInputStream(strExcelFile);
			HSSFWorkbook wb = new HSSFWorkbook(io);
			HSSFSheet sheet = wb.getSheet("GET");

			int pExecStatus_fld_loc = 0;
			do {
				pExecStatus_fld_loc++;
			} while (!sheet.getRow(0).getCell(pExecStatus_fld_loc)
					.getStringCellValue().trim()
					.equalsIgnoreCase("Execution Status"));

			if (rowNum > 0) {
				sheet.getRow(rowNum).createCell(pExecStatus_fld_loc)
				.setCellValue(strStatus);
			}

			FileOutputStream outFile = new FileOutputStream(strExcelFile);
			wb.write(outFile);
			outFile.close();
		}

		catch (Exception e) {
			//objReport.writeStackTraceErrorInReport(e, "updateExecStatusExcelGET");
		}

	}
	/**
	 * This method is to get the position of the specified column in the Excel worksheet    
	 * @param Colname - Column name                                            
	 */
	public static int getcolumn(String Colname) 
	{
		Report objReport=new Report();
		int colIdx=0;
		try
		{
			String newstr = Colname.replaceAll("[^A-Za-z]+", "");
			colIdx = CellReference.convertColStringToIndex(newstr);
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "getcolumn");						
		}
		return colIdx;
	}

	/**
	 * This method is to get     
	 * @param sCode                                           
	 */	
	public String getStatusCode(String sCode)  {
		try {
			return sCode;
		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getStatusCode");						
			return null;
		}

	}

	/**
	 * This method is to compare the actual status code with the expected ones 
	 * @param strstatusCode - Status Code                                        
	 */
	public String compareStatusCode(String strstatusCode) 
	{
		try
		{
			getStatusCode(sCode);
			if (strstatusCode.equalsIgnoreCase(getStatusCode(sCode))) {

				return strstatusCode;
			} else {
				return strstatusCode;
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "compareStatusCode");
		}
		return strstatusCode;
	}

	// enhancement of code for multiple APIs
	/**
	 * This method is to  make dynamic endpoint URL for MultiAPIs 
	 * @param strInputDataSheetTemp - Temporary Input datasheet location
	 * @param strTagResponseData -List of datas
	 * @param pApLink - end point URL
	 * @param currAPI - current API
	 * @param tagidMap - List of Tags  
	 * @param strTestcase - test case name                                 
	 */
	public String strInputDetails(String strInputDataSheetTemp,	String[] strTagResponseData, String pApLink, int currAPI,HashMap<String, ArrayList<String>> tagidMap, String strTestcase) 
	{
		try {
			Matcher m = Pattern.compile("\\<(.*?)\\>").matcher(pApLink);
			String reg_expMatch = "";
			String exp_replace = "";

			int findval = 1;
			String Tagvalconcat = "";
			String tagSep = "";
			while (m.find() && findval <= m.groupCount()) {
				if (m.group(findval).toString().trim().contains("n,")) {
					String tempval = m.group(findval).toString().split("n,")[1];
					Matcher m1 = Pattern.compile("\\((.*)\\)").matcher(tempval);
					while (m1.find()) {
						tagSep = m1.group(1).toString().trim();
					}
					Tagvalconcat = "";
					String tagAPINm = tempval.split("(" + tagSep + ")")[1]
							.split("\\)")[1].trim();
					for (int tagvalcnt = 0; tagvalcnt < tagidMap.get(tagAPINm)
							.size(); tagvalcnt++) {
						Tagvalconcat = Tagvalconcat
								+ tagidMap.get(tagAPINm).get(tagvalcnt)
								+ tagSep;
					}
					// to remove the last tagseperator
					Tagvalconcat = Tagvalconcat.substring(0,
							Tagvalconcat.length() - 1);
					while (pApLink.contains("<" + m.group(findval) + ">")) {
						pApLink = pApLink.replace("<" + m.group(findval) + ">",
								Tagvalconcat);
					}
					Tagvalconcat = "";
				} else {
					for (int f = 0; f < strTagResponseData.length; f++) {
						reg_expMatch = "<" + strTagResponseData[f].trim() + ">";
						exp_replace = tagidMap
								.get(strTagResponseData[f].trim()).get(currAPI);
						while (pApLink.trim().contains(
								("<" + strTagResponseData[f].trim() + ">"))) {
							pApLink = pApLink.replace(("<"
									+ strTagResponseData[f].trim() + ">"),
									exp_replace);
						}

					}
				}
			}
		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "strInputDetails");						
			return ("");
		}
		pApLink = pApLink.trim();
		return pApLink;
	}

	/**
	 * This method is to replace a string value with another
	 * @param str - String value
	 * @param strval - Value needs to replaced
	 * @param strrep - Value to replace                           
	 */
	public String replace(String str, String strval, String strrep) 
	{
		try
		{
			String[] words = str.split(strval);
			StringBuilder sentence = new StringBuilder(words[0]);

			for (int i = 1; i < words.length; ++i) {
				sentence.append(strrep);
				sentence.append(words[i]);
			}

			return sentence.toString();
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "replace");
			return ("");
		}
	}


	//*********************************

	public void dynamicValResponsPost(String strScriptName)  
	{
		Boolean valPostRespStatusFlag= false;
		//CommonFunctions commonFunc= new CommonFunctions();
		try{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("POST");

			String strTestcaseName = "";
			String DynamicDatabaseVal = "";
			String strInputDataFolder = "";
			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					if (sheet.getRow(i).getCell(getcolumn("D"))!=null){
						//strInputDataFolder = Runner.strWorkSpcPath +Runner.properties.getProperty("InputXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
						strInputDataFolder = Runner.properties.getProperty("InputXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();

					}
					if (sheet.getRow(i).getCell(getcolumn("J"))!=null){
						strTestcaseName = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("Y"))!=null){
						//DynamicDatabaseVal = sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim();
						DynamicDatabaseVal = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+"\\Resources\\"+sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim()+".xlsx";

					}	

					valPostRespStatusFlag= true;

					//Validate Web service response data
					//String strInputDataSheetTemp=Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strScriptName+"_Data_Sheet.xlsx";
					String strInputDataSheetTemp=Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strScriptName+"_Data_Sheet.xlsx";

					String strInputCSVFile = strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.txt";

					//Connection and fetching Data
					validateDBDynamicResponse(DynamicDatabaseVal , strTestcaseName, strInputDataSheetTemp,strInputCSVFile);

					//Validation of Data Fetched..
					CompareGenerateResultPOST(strInputDataSheetTemp);
				}

			}
			if(valPostRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method dynamicValResponsPost : Please check Test script '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}
		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "dynamicValResponsPost");	
		}
	}


	public void validateDBDynamicResponse(String strInputDataSheet, String strTestCase, String strInputDataSheetTemp, String strInputCSVFile)  
	{
		Boolean valDBDynRespStatusFlag= false;					
		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet sheet = excelWorkBook_DS.getSheet("DBDynamic");

			int rowNum = sheet.getLastRowNum()+1;
			int i;

			String ServerName = "";
			String Port = "";
			String DBName = "";
			String Username = "";
			String Password = "";
			String Query = "";

			for(i=1;i<rowNum;i++)
			{

				if (sheet.getRow(i).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase)){

					if (sheet.getRow(i).getCell(getcolumn("B"))!=null){
						ServerName = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						Port = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("D"))!=null){
						DBName = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("E"))!=null){
						Username = sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("F"))!=null){
						Password = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("G"))!=null){
						Query = sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();
					}

					if(Port.contains("."))
					{
						int intPort=Port.indexOf(".");
						Port=Port.substring(0, intPort);
					}

					valDBDynRespStatusFlag=true;

					String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
					String url="jdbc:db2://"+ServerName+":"+Port+"/"+DBName;

					Connection conn = null;

					//Load class into memory
					Class.forName(jdbcClassName);
					//Establish connection
					conn = DriverManager.getConnection(url, Username, Password);
					System.out.println(conn);
					Statement stmt = conn.createStatement() ;
					String query = Query;
					ResultSet rs = stmt.executeQuery(query) ;

					String Output = "";

					FileWriter ofstream = new FileWriter(strInputCSVFile);
					BufferedWriter out = new BufferedWriter(ofstream);

					while (rs.next())
					{
						String FEATURE_CD = rs.getString("FEATURECD");
						String FEATURE_NAME = rs.getString("FEATURE_NAME");				
						String FEATURE_DESC = rs.getString("FEATURE_DESC");
						String CATEGORY_NAME = rs.getString("CATEGORY_NAME");
						String CATEGORY_DESC = rs.getString("CATEGRY_DESC");
						String EnableStatus = rs.getString("EnableStatus");
						String GLBLFEAT_STATUS = rs.getString("Final_Status");
						String GLBLFEAT_MESSAGE = rs.getString("Final_Msg");
						String GLBLFEAT_EFF_ST_DTS = rs.getString("Final_EffDate");
						String GLBLFEAT_EXP_ST_DTS = rs.getString("FINAL_ENDDate");

						// print the results
						System.out.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s\n", FEATURE_CD, FEATURE_NAME, FEATURE_DESC, CATEGORY_NAME, CATEGORY_DESC, EnableStatus, GLBLFEAT_STATUS,GLBLFEAT_MESSAGE,GLBLFEAT_EFF_ST_DTS,GLBLFEAT_EXP_ST_DTS);

						Output = FEATURE_CD+"#"+FEATURE_NAME+"#"+FEATURE_DESC+"#"+CATEGORY_NAME+"#"+CATEGORY_DESC+"#"+EnableStatus+"#"+GLBLFEAT_STATUS+"#"+GLBLFEAT_MESSAGE+"#"+GLBLFEAT_EFF_ST_DTS+"#"+GLBLFEAT_EXP_ST_DTS+"\n";

						out.write(Output);										
					}
					out.close();

					csvToExcelConverterPOST(strInputCSVFile,strInputDataSheetTemp);

					if(conn!=null)
					{
						conn.close();
					}	

				} 
			}


			if(valDBDynRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateDBDynamicResponse : Please check Test Case '"+strTestCase+"' is available under Test script column of 'DBDynamic' Worksheet" ); 							
			}

			//excelWorkBook_DS.close();
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateDBDynamicResponse");	
		}
	}

	public void csvToExcelConverterPOST(String ResponseCSVFile, String ResponseExcelFile) 
	{
		try 
		{
			FileInputStream io = new FileInputStream(ResponseExcelFile);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet("DB_Data");
			if (sheet != null) {
				int index = wb.getSheetIndex(sheet);
				wb.removeSheetAt(index);
			}

			// Creating Excel work sheet
			sheet = wb.createSheet("DB_Data");

			String currentLine=null;
			int RowNum=0;
			XSSFRow headerRow =sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Q2:idValue");
			headerRow.createCell(1).setCellValue("Q2:name");
			headerRow.createCell(2).setCellValue("Q2:description");
			headerRow.createCell(3).setCellValue("Q2:categoryName");
			headerRow.createCell(4).setCellValue("Q2:categoryDescription");
			headerRow.createCell(5).setCellValue("Q2:enabled");	
			headerRow.createCell(6).setCellValue("Q2:featureStatus");
			headerRow.createCell(7).setCellValue("Q2:featureStatusMessage");		
			headerRow.createCell(8).setCellValue("Q2:datetimeBegin");
			headerRow.createCell(9).setCellValue("Q2:datetimeEnd");

			BufferedReader br = new BufferedReader(new FileReader(ResponseCSVFile));
			while ((currentLine = br.readLine()) != null) {
				String str[] = currentLine.split("#");
				RowNum++;
				XSSFRow currentRow=sheet.createRow(RowNum);
				for(int i=0;i<str.length;i++){
					currentRow.createCell(i).setCellValue(str[i]);
				}
			}

			// Saving the values in Excel
			FileOutputStream outFile = new FileOutputStream(ResponseExcelFile);
			wb.write(outFile);
			outFile.close();
			//br.close();
			//wb.close();
			//System.out.println("Done");
		} 
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "csvToExcelConverterPOST");	
		}
	}

	//CompareGenerateResult
	public void CompareGenerateResultPOST(String strInputDataSheetTemp) 
	{

		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheetTemp);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");
			XSSFSheet excelSheet_DB_Output = excelWorkBook_DS.getSheet("DB_Data");

			int lastOutputRowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int lastDBRowNum = excelSheet_DB_Output.getLastRowNum()+1;
			int colNum = excelSheet_DB_Output.getRow(0).getLastCellNum();

			String DB_Field_Tag="";
			String API_Field_Tag="";
			String DB_Field_Val="";
			String API_Field_Val="";
			String API_Field_Val_idValue = "";
			String DB_Field_valueCompare = "";
			String API_Field_TagMulti = "";
			String DB_Field_TagMulti = "";
			String API_Field_ValMulti = "";
			String DB_Field_ValMulti = "";
			int m;
			int n;
			int f;
			int o = 2;
			if(lastDBRowNum>2){
				for(m=(o-1);m<lastOutputRowNum;m++){
					API_Field_Tag = excelSheet_DS_Output.getRow(m).getCell(0).getStringCellValue().trim();

					if(API_Field_Tag.equalsIgnoreCase("q2:enabled")){
						Boolean flag = true;
						int count = m;//3
						API_Field_Val_idValue = excelSheet_DS_Output.getRow(m+2).getCell(1).getStringCellValue().trim();//HeaOppAssessmentVoyager

						System.out.println(m);


						for(f=1;f<lastDBRowNum;f++){
							if(flag == true){
								DB_Field_valueCompare = excelSheet_DB_Output.getRow(f).getCell(0).getStringCellValue().trim();

								if(DB_Field_valueCompare.equalsIgnoreCase(API_Field_Val_idValue)){							
									System.out.println(f);

									int flagCount = 0;
									for(int j=count;j<lastOutputRowNum;j++){
										if(flag==true){
											for(int a=0;a<colNum;a++){
												o = j;

												API_Field_TagMulti = excelSheet_DS_Output.getRow(j).getCell(0).getStringCellValue().trim();
												DB_Field_TagMulti = excelSheet_DB_Output.getRow(0).getCell(a).getStringCellValue().trim();


												if (DB_Field_TagMulti.equalsIgnoreCase(API_Field_TagMulti)){
													if (API_Field_TagMulti.equalsIgnoreCase("q2:enabled")){
														flagCount++;												
													}

													if(flagCount==2){
														flag=false;
														break;
													}
													API_Field_ValMulti= excelSheet_DS_Output.getRow(j).getCell(1).getStringCellValue().trim();
													DB_Field_ValMulti =excelSheet_DB_Output.getRow(f).getCell(a).getStringCellValue().trim();

													if(DB_Field_TagMulti.equalsIgnoreCase("q2:datetimeBegin") || DB_Field_TagMulti.equalsIgnoreCase("q2:datetimeEnd")){

														DB_Field_ValMulti = DB_Field_ValMulti.concat(".0");

													}
													if (DB_Field_ValMulti.equalsIgnoreCase(API_Field_ValMulti)){
														objReport.setValidationMessageInReport("PASS","Method CompareGenerateResultPOST : Field Validation  Expected DB Response value: " +DB_Field_ValMulti+", Webservice API Response value : "+API_Field_ValMulti ); 																								
													}
													else
													{
														objReport.setValidationMessageInReport("FAIL","Method CompareGenerateResultPOST : Field Validation  Expected DB Response value: " +DB_Field_ValMulti+", Webservice API Response value : "+API_Field_ValMulti ); 																								
													}
												}
											}
										}	
									}
								}
							}
						}
					}
				}
			}
			else{
				for(m=1;m<lastOutputRowNum;m++){
					for(n=0;n<colNum;n++){
						API_Field_Tag = excelSheet_DS_Output.getRow(m).getCell(0).getStringCellValue().trim();
						DB_Field_Tag = excelSheet_DB_Output.getRow(0).getCell(n).getStringCellValue().trim();

						if (DB_Field_Tag.equalsIgnoreCase(API_Field_Tag)){
							API_Field_Val= excelSheet_DS_Output.getRow(m).getCell(1).getStringCellValue().trim();
							DB_Field_Val=excelSheet_DB_Output.getRow(1).getCell(n).getStringCellValue().trim();

							if(DB_Field_Tag.equalsIgnoreCase("q2:datetimeBegin") || DB_Field_Tag.equalsIgnoreCase("q2:datetimeEnd")){

								DB_Field_ValMulti = DB_Field_ValMulti.concat(".0");

							}
							if (DB_Field_Val.equalsIgnoreCase(API_Field_Val))
							{
								objReport.setValidationMessageInReport("PASS","Method CompareGenerateResultPOST : Field Validation Expected DB Response value: " +DB_Field_Val+", Webservice API Response value : "+API_Field_Val ); 																								
							}
							else{
								objReport.setValidationMessageInReport("FAIL","Method CompareGenerateResultPOST : Field Validation Expected DB Response value: " +DB_Field_Val+", Webservice API Response value : "+API_Field_Val ); 																								
							}
						}
					}
				}
			}
			//excelWorkBook_DS.close();
		}
		catch(Exception e){
			objReport.writeStackTraceErrorInReport(e, "CompareGenerateResultPOST");
		}
	}


	public void dynamicValResponsGET(String strScriptName)  
	{

		Boolean valDynGETRespStatusFlag=false;
		try
		{
			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("GET");

			String strTestcaseName = "";
			String DynamicDatabaseVal = "";
			String strInputDataFolder = "";
			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						strInputDataFolder = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("A"))!=null){
						strTestcaseName = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("AH"))!=null){
						DynamicDatabaseVal = sheet.getRow(i).getCell(getcolumn("AH")).getStringCellValue().trim();
					}	

					valDynGETRespStatusFlag=true;

					//Validate Web service response data
					String strInputDataSheetTemp=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";
					String strInputCSVFile = strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.txt";

					//Connection and fetching Data
					validateDBDynamicResponseGET(DynamicDatabaseVal , strTestcaseName, strInputDataSheetTemp,strInputCSVFile);

					//Validation of Data Fetched..
					CompareGenerateResultGET(strInputDataSheetTemp);
				}

			}

			if(valDynGETRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method dynamicValResponsGET : Please check Test script '"+strScriptName+"' is available under Test script column of 'GET' Worksheet" ); 							
			}
		}

		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "dynamicValResponsPost");	
		}
	}

	//CompareGenerateResult
	public void CompareGenerateResultGET(String strInputDataSheetTemp) throws Exception {

		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheetTemp);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");
			XSSFSheet excelSheet_DB_Output = excelWorkBook_DS.getSheet("DB_Data");

			int lastOutputRowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int lastDBRowNum = excelSheet_DB_Output.getLastRowNum()+1;
			int colNum = excelSheet_DB_Output.getRow(0).getLastCellNum();

			String DB_Field_Tag="";
			String API_Field_Tag="";
			String DB_Field_Val="";
			String API_Field_Val="";
			String API_Field_Val_idValue = "";
			String DB_Field_valueCompare = "";
			String API_Field_TagMulti = "";
			String DB_Field_TagMulti = "";
			String API_Field_ValMulti = "";
			String DB_Field_ValMulti = "";
			int m;
			int n;
			int f;
			int o = 2;
			if(lastDBRowNum>2){
				for(m=(o-1);m<lastOutputRowNum;m++){
					API_Field_Tag = excelSheet_DS_Output.getRow(m).getCell(0).getStringCellValue().trim();

					if(API_Field_Tag.equalsIgnoreCase("idValue")){
						Boolean flag = true;
						int count = m;//3
						API_Field_Val_idValue = excelSheet_DS_Output.getRow(m).getCell(1).getStringCellValue().trim();//HeaOppAssessmentVoyager

						System.out.println(m);

						for(f=1;f<lastDBRowNum;f++){
							if(flag == true){
								DB_Field_valueCompare = excelSheet_DB_Output.getRow(f).getCell(0).getStringCellValue().trim();

								if(DB_Field_valueCompare.equalsIgnoreCase(API_Field_Val_idValue)){							
									System.out.println(f);

									int flagCount = 0;
									for(int j=count;j<lastOutputRowNum;j++){
										if(flag==true){
											for(int a=0;a<colNum;a++){
												o = j;

												API_Field_TagMulti = excelSheet_DS_Output.getRow(j).getCell(0).getStringCellValue().trim();
												DB_Field_TagMulti = excelSheet_DB_Output.getRow(0).getCell(a).getStringCellValue().trim();


												if (DB_Field_TagMulti.equalsIgnoreCase(API_Field_TagMulti)){
													if (API_Field_TagMulti.equalsIgnoreCase("idValue")){
														flagCount++;												
													}

													if(flagCount==2){
														flag=false;
														break;
													}
													API_Field_ValMulti= excelSheet_DS_Output.getRow(j).getCell(1).getStringCellValue().trim();
													DB_Field_ValMulti =excelSheet_DB_Output.getRow(f).getCell(a).getStringCellValue().trim();

													if(DB_Field_TagMulti.equalsIgnoreCase("datetimeBegin") || DB_Field_TagMulti.equalsIgnoreCase("datetimeEnd")){

														DB_Field_ValMulti = DB_Field_ValMulti.concat(".0");

													}
													if (DB_Field_ValMulti.equalsIgnoreCase(API_Field_ValMulti)){
														objReport.setValidationMessageInReport("PASS","Method CompareGenerateResultGET : field Validation Expected DB Response value: " +DB_Field_ValMulti+", Webservice API Response value : "+API_Field_ValMulti ); 							
													}
													else{
														objReport.setValidationMessageInReport("FAIL","Method CompareGenerateResultGET : field Validation Expected DB Response value: " +DB_Field_ValMulti+", Webservice API Response value : "+API_Field_ValMulti ); 							
													}
												}
											}
										}	
									}
								}
							}
						}
					}
				}
			}
			else{
				for(m=1;m<lastOutputRowNum;m++){
					for(n=0;n<colNum;n++){
						API_Field_Tag = excelSheet_DS_Output.getRow(m).getCell(0).getStringCellValue().trim();
						DB_Field_Tag = excelSheet_DB_Output.getRow(0).getCell(n).getStringCellValue().trim();

						if (DB_Field_Tag.equalsIgnoreCase(API_Field_Tag)){
							API_Field_Val= excelSheet_DS_Output.getRow(m).getCell(1).getStringCellValue().trim();
							DB_Field_Val=excelSheet_DB_Output.getRow(1).getCell(n).getStringCellValue().trim();

							if(DB_Field_Tag.equalsIgnoreCase("datetimeBegin") || DB_Field_Tag.equalsIgnoreCase("datetimeEnd")){

								DB_Field_Val = DB_Field_Val.concat(".0");

							}
							if (DB_Field_Val.equalsIgnoreCase(API_Field_Val)){
								objReport.setValidationMessageInReport("PASS","Method CompareGenerateResultGET : Field Validation Expected DB Response value: " +DB_Field_Val+", Webservice API Response value : "+API_Field_Val ); 																			
							}
							else{
								objReport.setValidationMessageInReport("FAIL","Method CompareGenerateResultGET : Field Validation Expected DB Response value: " +DB_Field_Val+", Webservice API Response value : "+API_Field_Val ); 							
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			objReport.writeStackTraceErrorInReport(e, "CompareGenerateResultGET");	
		}
	}

	public void validateDBDynamicResponseGET(String strInputDataSheet, String strTestCase, String strInputDataSheetTemp, String strInputCSVFile) throws Exception {

		Boolean valDBDynGETRespStatusFlag=false;
		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet sheet = excelWorkBook_DS.getSheet("DBDynamic");

			int rowNum = sheet.getLastRowNum()+1;
			int i;

			String ServerName = "";
			String Port = "";
			String DBName = "";
			String Username = "";
			String Password = "";
			String Query = "";

			for(i=1;i<rowNum;i++){

				if (sheet.getRow(i).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase)){

					if (sheet.getRow(i).getCell(getcolumn("B"))!=null){
						ServerName = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						Port = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("D"))!=null){
						DBName = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("E"))!=null){
						Username = sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("F"))!=null){
						Password = sheet.getRow(i).getCell(getcolumn("F")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("G"))!=null){
						Query = sheet.getRow(i).getCell(getcolumn("G")).getStringCellValue().trim();
					}

					if(Port.contains("."))
					{
						int intPort=Port.indexOf(".");
						Port=Port.substring(0, intPort);
					}

					valDBDynGETRespStatusFlag=true;
					String jdbcClassName="com.ibm.db2.jcc.DB2Driver";
					String url="jdbc:db2://"+ServerName+":"+Port+"/"+DBName;

					Connection conn = null;

					//Load class into memory
					Class.forName(jdbcClassName);
					//Establish connection
					conn = DriverManager.getConnection(url, Username, Password);
					System.out.println(conn);
					Statement stmt = conn.createStatement() ;
					String query = Query;
					ResultSet rs = stmt.executeQuery(query) ;

					String Output = "";

					FileWriter ofstream = new FileWriter(strInputCSVFile);
					BufferedWriter out = new BufferedWriter(ofstream);

					while (rs.next())
					{
						String FEATURE_CD = rs.getString("FEATURECD");
						String FEATURE_NAME = rs.getString("FEATURE_NAME");				
						String FEATURE_DESC = rs.getString("FEATURE_DESC");
						String CATEGORY_NAME = rs.getString("CATEGORY_NAME");
						String CATEGORY_DESC = rs.getString("CATEGRY_DESC");
						String EnableStatus = rs.getString("EnableStatus");
						String GLBLFEAT_STATUS = rs.getString("Final_Status");
						String GLBLFEAT_MESSAGE = rs.getString("Final_Msg");
						String GLBLFEAT_EFF_ST_DTS = rs.getString("Final_EffDate");
						String GLBLFEAT_EXP_ST_DTS = rs.getString("FINAL_ENDDate");

						// print the results
						//System.out.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s\n", FEATURE_CD, FEATURE_NAME, FEATURE_DESC, CATEGORY_NAME, CATEGORY_DESC, EnableStatus, GLBLFEAT_STATUS,GLBLFEAT_MESSAGE,GLBLFEAT_EFF_ST_DTS,GLBLFEAT_EXP_ST_DTS);
						Output = FEATURE_CD+"#"+FEATURE_NAME+"#"+FEATURE_DESC+"#"+CATEGORY_NAME+"#"+CATEGORY_DESC+"#"+EnableStatus+"#"+GLBLFEAT_STATUS+"#"+GLBLFEAT_MESSAGE+"#"+GLBLFEAT_EFF_ST_DTS+"#"+GLBLFEAT_EXP_ST_DTS+"\n";
						out.write(Output);

					}
					out.close();

					csvToExcelConverterGET(strInputCSVFile,strInputDataSheetTemp);

					if(conn!=null)
					{
						conn.close();
					}	

				} 
			}

			if(valDBDynGETRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateDBDynamicResponse : Please check Test Case '"+strTestCase+"' is available under Test script column of 'DBDynamic' Worksheet" ); 							
			}

			//excelWorkBook_DS.close();
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateDBDynamicResponseGET");	
		}
	}

	public void csvToExcelConverterGET(String ResponseCSVFile, String ResponseExcelFile) throws Exception {
		try {
			FileInputStream io = new FileInputStream(ResponseExcelFile);
			XSSFWorkbook wb = new XSSFWorkbook(io);
			XSSFSheet sheet = wb.getSheet("DB_Data");
			if (sheet != null) {
				int index = wb.getSheetIndex(sheet);
				wb.removeSheetAt(index);
			}

			// Creating Excel work sheet
			sheet = wb.createSheet("DB_Data");

			String currentLine=null;
			int RowNum=0;
			XSSFRow headerRow =sheet.createRow(0);
			headerRow.createCell(0).setCellValue("idValue");
			headerRow.createCell(1).setCellValue("name");
			headerRow.createCell(2).setCellValue("description");
			headerRow.createCell(3).setCellValue("categoryName");
			headerRow.createCell(4).setCellValue("categoryDescription");
			headerRow.createCell(5).setCellValue("enabled");	
			headerRow.createCell(6).setCellValue("status");
			headerRow.createCell(7).setCellValue("statusMessage");		
			headerRow.createCell(8).setCellValue("datetimeBegin");
			headerRow.createCell(9).setCellValue("datetimeEnd");

			BufferedReader br = new BufferedReader(new FileReader(ResponseCSVFile));
			while ((currentLine = br.readLine()) != null) {
				String str[] = currentLine.split("#");
				RowNum++;
				XSSFRow currentRow=sheet.createRow(RowNum);
				for(int i=0;i<str.length;i++){
					currentRow.createCell(i).setCellValue(str[i]);
				}
			}

			// Saving the values in Excel
			FileOutputStream outFile = new FileOutputStream(ResponseExcelFile);
			wb.write(outFile);
			outFile.close();
			//System.out.println("Done");
		} 
		catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "csvToExcelConverterGET");	
		}
	}

	//*************************

	public void DynamicValidateWebSrvcResponseFieldValue(String strInputDataSheet,String strExcelWebSrvcFildValdPath,String strWorkSheet,String strTestCase) throws Exception
	{
		String Script_Status="Pass";

		Boolean valDynValRespStatusFlag=false;

		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");

			FileInputStream fileInputStream_Webservc= new FileInputStream(strExcelWebSrvcFildValdPath);
			XSSFWorkbook wb1=new XSSFWorkbook(fileInputStream_Webservc);
			XSSFSheet webSerRspnFldValsheet=wb1.getSheet(strWorkSheet);

			int rowNum = webSerRspnFldValsheet.getLastRowNum()+1;

			String strArr[];
			String strTagArr[];
			String strTagValArr[];

			String expected_Field_Val="";
			String actual_Field_Val="";
			int fld_loc=0;
			int cnt=1;
			int intcnt;
			int OutputSheetrowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int m;

			for(m=1;m<rowNum;m++)
			{

				if (webSerRspnFldValsheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase)){
					int colNum = webSerRspnFldValsheet.getRow(m).getLastCellNum();
					for(int k=1;k<colNum;k++){
						if (webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim().length()>0)
						{
							strArr=(webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim()).split("@");

							if(strArr.length>2)
							{
								intcnt=Integer.parseInt(strArr[0]);
								strTagArr=strArr[1].split(";");
								strTagValArr=strArr[2].split(";");
							}
							else
							{
								intcnt=1;
								strTagArr=strArr[0].split(";");	
								strTagValArr=strArr[1].split(";");
							}

							for(int i=0;i<strTagArr.length;i++)
							{   cnt=1;
							expected_Field_Val="";
							actual_Field_Val="";

							for	(fld_loc=1;fld_loc<OutputSheetrowNum;fld_loc++)
							{
								if(excelSheet_DS_Output.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTagArr[i]))
								{
									if(cnt==intcnt)
										break;
									else
										cnt=cnt+1;
								}							
							}

							expected_Field_Val=strTagValArr[i];// Expected Input Field
							actual_Field_Val=excelSheet_DS_Output.getRow(fld_loc).getCell(1).getStringCellValue().trim();// Response Field Value in Output Worksheet					

							//System.out.println("check");
							if(!actual_Field_Val.contains(expected_Field_Val))
							{
								valDynValRespStatusFlag=true;
								objReport.setValidationMessageInReport("FAIL","Method DynamicValidateWebSrvcResponseFieldValue : '"+strTagArr[i] +"' field Validation Expected value: " +expected_Field_Val+ "Webservice API Response value : "+actual_Field_Val ); 																			
							}
							else
							{												
								valDynValRespStatusFlag=true;
								objReport.setValidationMessageInReport("PASS","Method DynamicValidateWebSrvcResponseFieldValue : '"+strTagArr[i] +"' field Validation Expected value: " +expected_Field_Val+ "Webservice API Response value : "+actual_Field_Val ); 																			
							}
							}

						}
					}
				}
			}

			if(valDynValRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method DynamicValidateWebSrvcResponseFieldValue : Please check Test Case '"+strTestCase+"' is available under Test Case column of 'WebService_Validation' Worksheet" ); 							
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "DynamicValidateWebSrvcResponseFieldValue");
		}

	}


	public void SemiDynamicValidateResponsepost(String strScriptName) throws IOException
	{

		Boolean valSemiDynValRespPOSTStatusFlag =false;						
		try{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);					   
			HSSFSheet sheet=wb.getSheet("POST");
			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()))
				{

					valSemiDynValRespPOSTStatusFlag=true;

					strTestCase = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();//Script Name
					strInputDataFolder = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();

					// Dynamic creation of Temporary Data sheet
					String strInputDataSheet=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";

					//Validate Web service response data
					DynamicValidateWebSrvcResponseFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase);
				}

			}

			//wb.close();

			if(valSemiDynValRespPOSTStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method SemiDynamicValidateResponsepost : Please check Test Case '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "SemiDynamicValidateResponsepost");
		}
	}

	// Dynamic validation Response for PUT method
	public void SemiDynamicValidateResponsePut(String strScriptName) throws IOException
	{
		Boolean valSemiDynValRespPUTStatusFlag =false;	
		try{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("PUT");

			//count total row number of sheet
			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					valSemiDynValRespPUTStatusFlag=true;

					strTestCase = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();//Script Name
					strInputDataFolder = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();

					// Dynamic creation of Temporary Data sheet
					String strInputDataSheet=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";


					//Validate Web service response data
					DynamicValidateWebSrvcResponseFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase);
				}
			}
			if(valSemiDynValRespPUTStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method SemiDynamicValidateResponsePut : Please check Test Case '"+strScriptName+"' is available under Test script column of 'PUT' Worksheet" ); 							
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "SemiDynamicValidateResponsePut");
		}
	}

	//Dynamic validation for GET Method
	public void SemiDynamicValidateResponseGet(String strScriptName) throws IOException
	{
		Boolean valSemiDynValRespGETStatusFlag =false;

		try
		{
			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("GET");

			//count row number
			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					valSemiDynValRespGETStatusFlag=true;

					strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name
					strInputDataFolder = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();

					// Dynamic creation of Temporary Data sheet
					//String strInputDataSheet=strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.xlsx";
					//String strInputDataSheet=Runner.strWorkSpcPath +Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strScriptName+"_Data_Sheet.xlsx";
					String strInputDataSheet=Runner.properties.getProperty("APIResponseDSFolderPath")+"\\"+strScriptName+"_Data_Sheet.xlsx";

					//Validate Web service response data
					DynamicValidateWebSrvcResponseFieldValue(strInputDataSheet, strInputDataSheet, "Webservice_Validation", strTestCase);
				}
			}
			if(valSemiDynValRespGETStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method SemiDynamicValidateResponseGet : Please check Test Case '"+strScriptName+"' is available under Test script column of 'GET' Worksheet" ); 							
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "SemiDynamicValidateResponseGet");
		}
	}


	//******************

	public void dynamicValRequestPost(String strScriptName) throws Exception 

	{
		Boolean valDynRespPOSTStatusFlag=false;

		try{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("POST");

			String strTestcaseName = "";
			String DynamicDatabaseVal = "";
			String strInputDataFolder = "";
			String strReqXMLNodes = "";
			String strInputDataSheet="";

			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						strInputDataSheet = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("D"))!=null){
						strInputDataFolder = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
					}

					if (sheet.getRow(i).getCell(getcolumn("J"))!=null){
						strTestcaseName = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("Y"))!=null){
						//DynamicDatabaseVal = sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim();
						DynamicDatabaseVal = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+"\\Resources\\"+sheet.getRow(i).getCell(getcolumn("Y")).getStringCellValue().trim()+".xlsx";
					}	
					if (sheet.getRow(i).getCell(getcolumn("Z"))!=null){
						strReqXMLNodes = sheet.getRow(i).getCell(getcolumn("Z")).getStringCellValue().trim();
					}

					//Validate Web service response data
					String strDOMFile = strInputDataFolder+"\\\\"+strScriptName+".xml";
					String ReqInputDataSheetTemp=strInputDataFolder+"\\\\"+strScriptName+"Request_Data_Sheet.xlsx";
					String strInputCSVFile = strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.txt";

					File f3= new File(ReqInputDataSheetTemp);
					if (f3.exists()){
						f3.delete();
					}

					XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
					wb1.write(new FileOutputStream(ReqInputDataSheetTemp));

					//Request will be stored
					String strTagNameReq[]=strReqXMLNodes.split("@");
					covertDOMToExcel(ReqInputDataSheetTemp, strDOMFile, strTagNameReq);

					//Connection and fetching Data
					validateDBDynamicResponse(DynamicDatabaseVal , strTestcaseName, ReqInputDataSheetTemp,strInputCSVFile);

					//Validation of Data Fetched..
					CompareGenerateResult(ReqInputDataSheetTemp);
					//wb1.close();
				}

			}

			if(valDynRespPOSTStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method dynamicValRequestPost : Please check Test Case '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}

		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "dynamicValRequestPost");
		}
	}


	public void dynamicValRequestPut(String strScriptName)  

	{
		Boolean valDynRespPUTStatusFlag=false;

		try
		{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("PUT");

			String strTestcaseName = "";
			String DynamicDatabaseVal = "";
			String strInputDataFolder = "";
			String strReqXMLNodes = "";
			String strInputDataSheet="";
			String pIsJSON="";

			int rowNum = sheet.getLastRowNum()+1;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						strInputDataSheet = sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("D"))!=null){
						strInputDataFolder = sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim();
					}

					if (sheet.getRow(i).getCell(getcolumn("J"))!=null){
						strTestcaseName = sheet.getRow(i).getCell(getcolumn("J")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("T"))!=null){
						DynamicDatabaseVal = sheet.getRow(i).getCell(getcolumn("T")).getStringCellValue().trim();
					}	
					if (sheet.getRow(i).getCell(getcolumn("U"))!=null){
						strReqXMLNodes = sheet.getRow(i).getCell(getcolumn("U")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("S"))!=null){
						pIsJSON = sheet.getRow(i).getCell(getcolumn("S")).getStringCellValue().trim();
					}

					valDynRespPUTStatusFlag=true;

					//Validate Web service response data
					String strDOMFile = strInputDataFolder+"\\\\"+strScriptName+".xml";
					String ReqInputDataSheetTemp=strInputDataFolder+"\\\\"+strScriptName+"Request_Data_Sheet.xlsx";
					String strInputCSVFile = strInputDataFolder+"\\\\"+strScriptName+"_Data_Sheet.txt";

					File f3= new File(ReqInputDataSheetTemp);
					if (f3.exists()){
						f3.delete();
					}

					XSSFWorkbook wb1=new XSSFWorkbook(new FileInputStream(new File(strInputDataSheet)));
					wb1.write(new FileOutputStream(ReqInputDataSheetTemp));

					//Request will be stored
					if(pIsJSON.equalsIgnoreCase("Yes")){

						String XML_String = convertToJson(strInputDataFolder,strInputDataFolder, strScriptName);

						FileInputStream fis = new FileInputStream(ReqInputDataSheetTemp);

						XSSFWorkbook wb2 = new XSSFWorkbook(fis);
						XSSFSheet fSheet = wb2.createSheet("Output");
						fSheet = wb2.getSheet("Output");
						fSheet= parseJsonStringReq(XML_String, fSheet);

						FileOutputStream outFile = new FileOutputStream(ReqInputDataSheetTemp);
						wb2.write(outFile);
						outFile.close();

					}
					else{
						String strTagNameReq[]=strReqXMLNodes.split("@");
						covertDOMToExcel(ReqInputDataSheetTemp, strDOMFile, strTagNameReq);
					}

					//Connection and fetching Data
					validateDBDynamicResponse(DynamicDatabaseVal , strTestcaseName, ReqInputDataSheetTemp,strInputCSVFile);

					//Validation of Data Fetched..
					CompareGenerateResult(ReqInputDataSheetTemp);
					//wb1.close();

				}
			}
			if(valDynRespPUTStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method dynamicValRequestPost : Please check Test Case '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}

		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "dynamicValRequestPost");
		}
	}

	public void CompareGenerateResult(String strInputDataSheetTemp) throws Exception {

		try
		{
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheetTemp);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");
			XSSFSheet excelSheet_DB_Output = excelWorkBook_DS.getSheet("DB_Data");

			int lastOutputRowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int colNum = excelSheet_DB_Output.getRow(0).getLastCellNum();

			String DB_Field_Tag="";
			String API_Field_Tag="";
			String DB_Field_Val="";
			String API_Field_Val="";

			int m;
			int n;

			for(m=1;m<lastOutputRowNum;m++){
				for(n=0;n<colNum;n++){
					API_Field_Tag = excelSheet_DS_Output.getRow(m).getCell(0).getStringCellValue().trim();
					DB_Field_Tag = excelSheet_DB_Output.getRow(0).getCell(n).getStringCellValue().trim();

					if (DB_Field_Tag.equalsIgnoreCase(API_Field_Tag)){
						API_Field_Val= excelSheet_DS_Output.getRow(m).getCell(1).getStringCellValue().trim();
						DB_Field_Val=excelSheet_DB_Output.getRow(1).getCell(n).getStringCellValue().trim();

						if (DB_Field_Val.equalsIgnoreCase(API_Field_Val))
						{
							objReport.setValidationMessageInReport("PASS","Method CompareGenerateResult : field Validation DB Response value: " +DB_Field_Val+" , Webservice API Request value : "+API_Field_Val ); 																															
						}
						else{
							objReport.setValidationMessageInReport("FAIL","Method CompareGenerateResult : field Validation DB Response value: " +DB_Field_Val+" , Webservice API Request value : "+API_Field_Val ); 																			
						}
					}
				}
			}
			//excelWorkBook_DS.close();
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "CompareGenerateResult");
		}
	}

	public XSSFSheet parseJsonStringReq(String responseString, XSSFSheet sheet) 
	{

		try
		{
			sSheet=sheet; 
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(responseString.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> field : entrySet){
				String key = field.getKey();
				JsonElement value = jsonObject.get(key);

				if ((value.isJsonNull() || value.isJsonPrimitive())) {

					System.out.println("Key: " + field.getKey() + "\tValue:" + value.getAsString());

					XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
					row.createCell(0).setCellValue(
							field.getKey());
					row.createCell(1).setCellValue(value.getAsString());
				}
				else {
					checkJsonValueTypeReq(value);
				}
			}
		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "parseJsonStringReq");
		}

		return sSheet;
	}

	public void checkJsonValueTypeReq(JsonElement value) 
	{
		try
		{
			if (value.isJsonObject()) {
				// Calling parseJsonString recursively to get
				// <String,String> Key Value pairs.
				parseJsonStringReq(value.toString(),sSheet);
			} else if (value.isJsonArray()) {
				JsonArray jsonArray = value.getAsJsonArray();
				for (JsonElement jsonArrayElement : jsonArray){				
					checkJsonValueTypeReq(jsonArrayElement);
				}
			}}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "checkJsonValueTypeReq");
		}
	}

	/**************************SCHEMA VALIDATION****************************************/				       
	/**
	 * This method is use to validate JSON with schema and generate a report
	 * 
	 * @param strScriptName
	 *            - Name of the Script
	 */

	public void webserviceSchemaValjson(String strScriptName) {

		String Script_Status="";
		try{

			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("SchemaValidation");

			int rowNum = sheet.getLastRowNum()+1;
			String strSchemaFile = null;
			String strJsonFile = null;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
					if (sheet.getRow(i).getCell(getcolumn("B"))!=null){
						strSchemaFile = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("BaseXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						strJsonFile = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("BaseXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}

					File schemaFile = new File(strSchemaFile);
					File jsonFile = new File(strJsonFile);

					if (SchemaValidationUtils.isJsonValid(schemaFile, jsonFile))
					{
						System.out.println("Valid!");
						System.out.println(SchemaValidationUtils.reportGeneration(schemaFile, jsonFile));

						if(SchemaValidationUtils.reportGeneration(schemaFile, jsonFile).contains("error")){
							String[] data = SchemaValidationUtils.reportGeneration(schemaFile, jsonFile).split("error");
							System.out.println(data[1]);
						}

						objReport.setValidationMessageInReport("PASS","webserviceSchemaValjson: XML and Schema Validation : All Match Found");}
					else
					{
						String modifiedstr = "";
						String strurl = SchemaValidationUtils.reportGeneration(schemaFile, jsonFile);
						if(strurl.contains("error")){
							modifiedstr = strurl.substring(strurl.indexOf("error"), strurl.lastIndexOf("]")+1);
							System.out.println(modifiedstr);
						}

						objReport.setValidationMessageInReport("FAIL","Schema Validating: XML and Schema Validation Failed: "+modifiedstr);}}
			}
		}
		catch(Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "webserviceSchemaValjson");
		}
	}

	/*** This method is use to validate XML with schema and generate a report
	 * 
	 * @param strScriptName
	 *            - Name of the Script

	 */

	public void webserviceSchemaValxml(String strScriptName) {
		String Script_Status="";
		try{
			FileInputStream io = new FileInputStream(dataSheet);
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet("SchemaValidation");

			int rowNum = sheet.getLastRowNum()+1;
			String strSchemaFile = null;
			String strXMLFile = null;

			for(int i=1;i<rowNum;i++)
			{
				if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){
					if (sheet.getRow(i).getCell(getcolumn("B"))!=null){
						strSchemaFile = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("BaseXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
					}
					if (sheet.getRow(i).getCell(getcolumn("C"))!=null){
						strXMLFile = Runner.strWorkSpcPath +Runner.properties.getProperty("appName")+Runner.properties.getProperty("BaseXMLFolderPath")+sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim();
					}

					final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";
					SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
					Schema schema = factory.newSchema(new File(strSchemaFile));
					Validator validator = schema.newValidator();
					validator.validate(new StreamSource(new File(strXMLFile)));
				} 
			}
			System.out.println("Valid!");
			objReport.setValidationMessageInReport("PASS","Schema Validating: XML and Schema Validation : All Match Found");
		}
		catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "webserviceSchemaValxml");
		}
	}

	//************************************************
	/**
	 * This method is to compare two XMLS i.e. Output XML from Mark Logic with Expected XML
	 * 
	 * @param strScriptName
	 *            - Name of the Script
	 */
	public void webserviceCompareXMLs(String strScriptName) 
	{
		// TODO Insert code here
		try{
			FileInputStream io1 = new FileInputStream(dataSheet);

			HSSFWorkbook wb1 = new HSSFWorkbook(io1);
			HSSFSheet sheet1 = wb1.getSheet("COMPAREXMLs");

			String strCompareMappingSheet = null; //Comparing sheet containing all the data related to XMLs
			String strlocationXML1 = null; // Location of the XML (Output/Expected)
			String strlocationXML2 = null; // Location of the XML (Output/Expected)
			String strOutputLocation = null;// Location where output needs to be stored
			String strMaxRecord = null; // Maximum Limit of the characters that can be compared
			String strstartXML1 = null; // Starting point of XML1 
			String strstartXML2 = null; // Starting point of XML2

			int rowNum1 = sheet1.getLastRowNum() + 1;
			for (int i1 = 1; i1 < rowNum1; i1++) {

				if (strScriptName.equalsIgnoreCase(sheet1.getRow(i1).getCell(getcolumn("A")).getStringCellValue().trim())) {
					if (sheet1.getRow(i1).getCell(getcolumn("B"))!=null)
					{
						strCompareMappingSheet = sheet1.getRow(i1).getCell(getcolumn("B")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("C"))!=null)
					{
						strlocationXML1 = sheet1.getRow(i1).getCell(getcolumn("C")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("D"))!=null)
					{
						strlocationXML2 = sheet1.getRow(i1).getCell(getcolumn("D")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("E"))!=null)
					{
						strOutputLocation = sheet1.getRow(i1).getCell(getcolumn("E")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("F"))!=null)
					{
						strMaxRecord = sheet1.getRow(i1).getCell(getcolumn("F")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("G"))!=null)
					{
						strstartXML1 = sheet1.getRow(i1).getCell(getcolumn("G")).getStringCellValue().trim();
					}
					if (sheet1.getRow(i1).getCell(getcolumn("H"))!=null)
					{
						strstartXML2 = sheet1.getRow(i1).getCell(getcolumn("H")).getStringCellValue().trim();
					}
					System.gc();
					long startTime = System.currentTimeMillis();

					int MaxRecNunber = 0;

					// Considering the number after decimal point 
					if (strMaxRecord.contains(".")) {
						int intdelLoc = strMaxRecord.indexOf(".");
						strMaxRecord = strMaxRecord.substring(0, intdelLoc);
					}

					if (strMaxRecord.matches("-?\\d+(\\.\\d+)?")) {
						MaxRecNunber = Integer.parseInt(strMaxRecord);
						String d_path;
						// Putting the location of compare map excel location
						d_path = strCompareMappingSheet;
						FileInputStream io;

						try {
							io = new FileInputStream(d_path);
							// Opening compare map xls for comparing v1 and v2 headers
							XSSFWorkbook wb = new XSSFWorkbook(io);
							XSSFSheet sheet = wb.getSheetAt(0);
							int rowNum = sheet.getLastRowNum() + 1;
							// Finding the location as where to write the output data report
							String FILENAME = strOutputLocation;
							String newLine = System.getProperty("line.separator");

							ArrayList<String> CompHeadertags = new ArrayList<String>();
							ArrayList<String> ParentTags = new ArrayList<String>();
							ArrayList<String> ParentTags_excel = new ArrayList<String>();
							CompHeadertags.add("iidValue");
							for (int i = 1; i < rowNum; i++) {
								if (sheet.getRow(i).getCell(0) != null) {
									CompHeadertags.add(sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim());
									// Checking for parent tags
									if ((sheet.getRow(i).getCell(getcolumn("D")) != null)
											&& (sheet.getRow(i).getCell(getcolumn("E")) != null)) {
										ParentTags.add(sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim()
												+ ","
												+ sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim());
										ParentTags_excel.add(sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue()
												.trim() + ","
												+ sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim());
									} else {
										ParentTags_excel.add(" , ");
									}

								}
							}
							io.close();
							// Removing duplicate parent tag elements
							Set<String> hs = new HashSet<>();
							hs.addAll(ParentTags);
							ParentTags.clear();
							ParentTags.addAll(hs);

							HashMap<String, ArrayList<String>> xml_v2 = new HashMap<String, ArrayList<String>>();
							HashMap<String, ArrayList<String>> xml_v1 = new HashMap<String, ArrayList<String>>();
							HashMap<String, ArrayList<String>> Output_rep = new HashMap<String, ArrayList<String>>();

							// Passing xml data location, main comp header and which one to choose ( v1-->1 && v2--> 0)
							System.out.println(
									"passing xml data loaction, main comp header and which one to choose ( v1-->1 && v2--> 0)");

							xml_v1 = getxmldata(strlocationXML1, ParentTags, CompHeadertags, 1, ParentTags_excel,
									strstartXML1);
							xml_v2 = getxmldata(strlocationXML2, ParentTags, CompHeadertags, 0, ParentTags_excel,
									strstartXML2);
							System.out.println("Map  written for Xml_v1 ::" + xml_v1.size() + "VAL:: " + xml_v1);
							System.out.println("Map  written for Xml_v2 ::" + xml_v2.size() + "VAL:: " + xml_v2);

							ArrayList<String> Errorlog = new ArrayList<String>();
							System.gc();
							Boolean status;
							System.out.println(" Starting comparison betwen Xml");
							//Comparing the key values of both XMLs
							for (String Xml_val_v2 : xml_v2.keySet()) {

								status = true;
								Errorlog.add(xml_v2.get(Xml_val_v2).get(0));
								for (String Xml_val_v1 : xml_v1.keySet()) {

									if (Xml_val_v1.equalsIgnoreCase(Xml_val_v2)) {

										for (int k = 1; k < CompHeadertags.size(); k++) {
											if (xml_v2.get(Xml_val_v2).get(k - 1)
													.equalsIgnoreCase(xml_v1.get(Xml_val_v2).get(k - 1))) {
												Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1) + "(v2) :::: "
														+ xml_v1.get(Xml_val_v2).get(k - 1) + " (v1)");
											} else {
												if ((xml_v2.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("True")
														&& (xml_v1.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("y")))) {
													Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1)
															+ "(v2) :::: " + newLine + xml_v1.get(Xml_val_v2).get(k - 1)
															+ " (v1)");
												} else if ((xml_v2.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("False")
														&& (xml_v1.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("N")))) {
													Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1)
															+ "(v2) :::: " + newLine + xml_v1.get(Xml_val_v2).get(k - 1)
															+ " (v1)");
												} else {
													Errorlog.add("Mismatch  in " + xml_v2.get(Xml_val_v2).get(k - 1)
															+ "(v2) :::: " + newLine + xml_v1.get(Xml_val_v2).get(k - 1)
															+ " (v1)");
													status = false;
												}

											}
										}

										break;
									}

								}
								if (Errorlog.size() == 1) {
									Errorlog.add(" Mismatch in Element as no elements found in the V1 for the IID value "
											+ Xml_val_v2);
									status = false;
								}
								if (status == false) {
									Output_rep = Getmapvalue(Errorlog, Output_rep);
								}
								Errorlog.clear();
							}
							System.out.println("Writing data into xls");

							if (Output_rep.size() > 0) {
								Writereport(Output_rep, CompHeadertags, FILENAME, MaxRecNunber);
								String Script_Status = "FAIL";
								objReport.setValidationMessageInReport("FAIL","Method webserviceCompareXMLs : Validating - Proper Matching: Mismatch Found"); 

								//logInfo(" Script status : " + Script_Status);

								for (String Xml_val_v2 : xml_v2.keySet()) {

									status = true;
									Errorlog.add(xml_v2.get(Xml_val_v2).get(0));
									for (String Xml_val_v1 : xml_v1.keySet()) {

										if (Xml_val_v1.equalsIgnoreCase(Xml_val_v2)) {

											for (int k = 1; k < CompHeadertags.size(); k++) {
												if (xml_v2.get(Xml_val_v2).get(k - 1)
														.equalsIgnoreCase(xml_v1.get(Xml_val_v2).get(k - 1))) {
													Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1)
															+ "(v2) :::: " + xml_v1.get(Xml_val_v2).get(k - 1) + " (v1)");
												} else {
													if ((xml_v2.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("True")
															&& (xml_v1.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("y")))) {
														Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1)
																+ "(v2) :::: " + newLine + xml_v1.get(Xml_val_v2).get(k - 1)
																+ " (v1)");
													} else if ((xml_v2.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("False")
															&& (xml_v1.get(Xml_val_v2).get(k - 1).equalsIgnoreCase("N")))) {
														Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k - 1)
																+ "(v2) :::: " + newLine + xml_v1.get(Xml_val_v2).get(k - 1)
																+ " (v1)");
													} else {
														objReport.setValidationMessageInReport("FAIL", "Method webserviceCompareXMLs : Output xml2 - xml_v2.get(Xml_val_v2).get(k - 1)"
																+ "Output xml1  + xml_v1.get(Xml_val_v2).get(k - 1)");

													}
												}
											}
										}
									}
								}
							} else {
								String Script_Status = "PASS";
								objReport.setValidationMessageInReport("PASS",
										"Method webserviceCompareXMLs : Validating - Proper Matching : All Match Found");
								//logInfo(" Script status : " + Script_Status);
								objReport.setValidationMessageInReport("PASS","Method webserviceCompareXMLs :Output xml2-  All Match Found :Output xml1: All Match Found "); 
							}

							System.out.println(" Completed Report :: " + FILENAME);

						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							objReport.writeStackTraceErrorInReport(e, "webserviceCompareXMLs : File Not Found");

						} catch (IOException e) {
							// TODO Auto-generated catch block
							objReport.writeStackTraceErrorInReport(e, "webserviceCompareXMLs");
						}
					} else {
						System.out.println(" max rec count is not numeric");
						String newLine = System.getProperty("line.separator");

					}
					long stopTime = System.currentTimeMillis();
					long elapsedTime = stopTime - startTime;

					String newLine = System.getProperty("line.separator");
					/*						System.out.println(newLine + "Total execution time: "
											+ String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toHours(elapsedTime),
													TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
													- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))));
									System.out.println("Total execution time: " + String.format("%d min, %d sec",
											TimeUnit.MILLISECONDS.toHours(elapsedTime), TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
											- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))));
					 */
				}
			}
		}

		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "webserviceCompareXMLs");
		}
	}
	/**
	 * This method is to 
	 * @param XMLPath
	 *            - Name of the Script
	 * @param parentTags
	 * 			  - 
	 * @param compHeadertags
	 * 			  -
	 * @param i
	 * 			  -
	 * @param  parentTags_excel
	 * 			  -
	 * @param XML_block_start
	 * 			  -
	 */
	private static HashMap<String, ArrayList<String>> getxmldata(String XMLPath, ArrayList<String> parentTags,
			ArrayList<String> compHeadertags, int i, ArrayList<String> parentTags_excel, String XML_block_start) {
		// TODO Auto-generated method stub
		Report objReport = new Report();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		MyHandler handler = new MyHandler();

		handler.InputdataXLS = new ArrayList<>();
		// Here the starting element is 1 as first is reserved for Map index
		// value. Hence the passing 1 at last value
		handler.InputdataXLS = getinputTag(compHeadertags, i, 1, ",");
		// Here the starting element is 0 to capture all the parent tags. Hence
		// the passing 0 at last value,1);
		handler.ParenTagName = getinputTag(parentTags, i, 0, ",");
		handler.ParenTagName_Xls = getinputTag(parentTags_excel, i, 0, ",");
		handler.count = 0.0;
		handler.Xml_block_Start = XML_block_start;

		try {

			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new File(XMLPath), handler);



			return (HashMap<String, ArrayList<String>>) handler.getEmpList();
		} catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "getxmldata");
			return null;
		}

	}

	/**
	 * This method is to 
	 * 
	 * @param strScriptName
	 *            - Name of the Script
	 */
	private static ArrayList<String> getinputTag(ArrayList<String> compHeadertags, int Choice, int startEelement,
			String SplitValue) {
		// TODO Auto-generated method stub
		Report objReport = new Report();
		ArrayList<String> XMLTagVal = new ArrayList<String>();
		try {

			for (int i = startEelement; i < compHeadertags.size(); i++) {

				XMLTagVal.add(compHeadertags.get(i).split("[//" + SplitValue + "]")[Choice]);
			}

			return XMLTagVal;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			objReport.writeStackTraceErrorInReport(e, "getinputTag");
		}
		return null;
	}
	/**
	 * This method is to 
	 * 
	 * @param strScriptName
	 *            - Name of the Script
	 */
	private static HashMap<String, ArrayList<String>> Getmapvalue(ArrayList<String> InputdataXLS,
			HashMap<String, ArrayList<String>> xmllistMAP) {
		// TODO Auto-generated method stub
		String tempo = null;
		ArrayList<String> temp = new ArrayList<String>();
		for (int InpTag = 0; InpTag < InputdataXLS.size(); InpTag++) {
			tempo = InputdataXLS.get(InpTag);
			temp.add(tempo);
		}

		xmllistMAP.put(temp.get(0).toString(), temp);
		return (xmllistMAP);

	}
	/**
	 * This method is to compare two XMLS i.e. Output XML with Expected XML
	 * 
	 * @param strScriptName
	 *            - Name of the Script
	 */
	private static void Writereport(HashMap<String, ArrayList<String>> output_rep, ArrayList<String> header,
			String fILENAME, int maxRecNunber) throws IOException {
		// TODO Auto-generated method stub
		String d_path = fILENAME;
		int rowCount = 0;
		int columnCount = 0;
		try {

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFRow row;
			XSSFCell cell;
			XSSFSheet sheet1 = wb.createSheet("Report");
			row = sheet1.createRow(rowCount);
			XSSFCellStyle style = wb.createCellStyle();
			XSSFCellStyle wrapText = wb.createCellStyle();
			wrapText.setWrapText(true);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setWrapText(true);
			// Creating header tags
			String Header_v1 = "";
			String Header_v2 = "";
			for (int i = 0; i < header.size(); i++) {
				if (header.get(i).split("[//,]").length > 1) {
					Header_v1 = header.get(i).split("[//,]")[0].split("[//+]")[0];
					Header_v2 = header.get(i).split("[//,]")[1].split("[//+]")[0];
				} else {
					if (header.get(i).split("[//,]").length == 1) {
						Header_v1 = header.get(i);
						Header_v2 = "";
					} else {
						Header_v2 = "";
						Header_v1 = Header_v2;

					}
				}
				cell = row.createCell(columnCount++);
				// cell.setCellValue(header.get(i));
				cell.setCellValue(Header_v1 + "," + Header_v2);
			}

			// Writing the data
			columnCount = 0;
			List<String> sortedKeys = new ArrayList<String>(output_rep.keySet());
			Collections.sort(sortedKeys);
			for (String repCont : sortedKeys) {
				if (rowCount >= (int) maxRecNunber) {
					break;
				}
				columnCount = 0;
				row = sheet1.createRow(++rowCount);
				for (int i = 0; i < output_rep.get(repCont).size(); i++) {
					cell = row.createCell(columnCount++);
					if (output_rep.get(repCont).get(i).contains("Mismatch")) {
						cell.setCellValue(output_rep.get(repCont).get(i));
						cell.setCellStyle(style);
					} else {
						cell.setCellValue(output_rep.get(repCont).get(i));
						cell.setCellStyle(wrapText);
					}

				}
			}
			sheet1.autoSizeColumn(rowCount - 1);
			FileOutputStream outFile = new FileOutputStream(d_path);

			wb.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static String generateSAMLAccessToken(String samlURL,String username,String password,String baseUrl,String clientID,String clientSecret,String refreshToken){
		String token = "";
		try{
			String strResponsVal = "";
			String samlCode = generateSAMLCode(samlURL, username, password);
			//Create DefaultHttpClient object
			DefaultHttpClient httpClient = new DefaultHttpClient();

			// Create HttpPost object for the Web service specified by 'strWebService'
			HttpPost postRequest = new HttpPost(baseUrl);

			//Add headers to the HttpPost object(postRequest)
			postRequest.addHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");

			//Post Query String to Endpoint url
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(3);
			parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
			parameters.add(new BasicNameValuePair("client_id", clientID));
			parameters.add(new BasicNameValuePair("client_secret", clientSecret));
			parameters.add(new BasicNameValuePair("code", samlCode));
			postRequest.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

			// Send the request; It will return the response in the form of HttpResponse object
			HttpResponse response = httpClient.execute(postRequest);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();

			//Get API response value
			HttpEntity entity = response.getEntity();
			strResponsVal = EntityUtils.toString(entity);	
			System.out.println(strResponsVal);
			/*			
			JsonParser parser = new JsonParser();	

			JsonObject data = (JsonObject) parser.parse((Reader) response.getEntity());
			accessToken = data.get("access_token").toString();*/

			String jsonString = strResponsVal.toString();

			if(refreshToken.equalsIgnoreCase("Y")||refreshToken.equalsIgnoreCase("Yes")){
				key = key+"@id_token";
			}

			String value=parseAccessToken(jsonString,key);
			System.out.println(value);

			if(refreshToken.equalsIgnoreCase("Y")||refreshToken.equalsIgnoreCase("Yes")){
				token = value.substring(1, value.length()-1);
			}
			token = value;
			
			if (statusCode != 200) {	
				System.out.println(statusCode);
				//	objReport.setValidationMessageInReport("FAIL","Method postWebserviceCall(POST) : Failed to generate API Response due to Error "+strResponsVal);
			} 
			else {			  	
				System.out.println(statusCode);	
				//	objReport.setValidationMessageInReport("PASS"," Method postWebserviceCall(POST) : API Response is generated successfully");							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}


	static String parseAccessToken(String json, String key) throws IOException {
		// Get Key:Value pair from JsonObject

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(json.toString());
		Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
		for(Map.Entry<String,JsonElement> field : entrySet){
			String key1 = field.getKey();
			JsonElement value = jsonObject.get(key1);
			String[] keys = key.split("@");
			if(field.getKey().equals(keys[0].toString())){
				//System.out.println(field.getKey()+" : "+ field.getValue());
				jsonValue=(field.getValue().toString()).substring(1, (field.getValue().toString()).length()-1);
				
				//return jsonValue;

			}
			else if(key.contains("@") && field.getKey().equals(keys[1].toString())){
				//System.out.println(field.getKey()+" : "+ field.getValue());
				jsonValue=jsonValue +"@;@"+ (field.getValue().toString()).substring(1, (field.getValue().toString()).length()-1);
				//return jsonValue;

			}
			else{

				if (!(value.isJsonNull() || value.isJsonPrimitive())) {
					checkJsonAccessToken(value);
				}

			}
		}
		// System.out.println("Checking if Json String has a field--false means
		// it does not have a field :" +fieldsIterator.hasNext());
		return jsonValue;

	}

	private static void checkJsonAccessToken(JsonElement value) throws IOException {

		if (value.isJsonObject()) {
			parseAccessToken(value.toString(),key);

		} else if (value.isJsonArray()) {
			JsonArray jsonArray = value.getAsJsonArray();
			for (JsonElement jsonArrayElement : jsonArray){				
				checkJsonAccessToken(jsonArrayElement);
			}
		}

	}


	public static String generateSAMLCode(String url, String username, String password) {
		String samlCode = "";
		try{
			String chromeDriver = "";
			if (Runner.localRunFlag==true)
			{
				chromeDriver=Runner.strWorkSpcPath+"DigitalAssuranceCTScripts\\ATAFramework\\FrameworkUtils\\Resources\\chromedriver.exe";
			}
			else
			{
				chromeDriver=Runner.strResourceFldLoc+"\\Framework\\chromedriver.exe";
			}

			System.setProperty("webdriver.chrome.driver",chromeDriver);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();

			sendTab(3);
			copyToClipboard(url);
			Thread.sleep(1000);
			ctrlV();
			enterKey();
			Thread.sleep(5000);
			copyToClipboard(username);
			ctrlV();
			Thread.sleep(3000);
			sendTab(1);
			copyToClipboard(password);
			ctrlV();
			enterKey();
			Thread.sleep(2000);
			String currentUrl = driver.getCurrentUrl();
			driver.close();
			String[] splitString = currentUrl.split("code=");
			samlCode = splitString[1];
		} catch (Exception e) {
			e.printStackTrace();

		}
		return samlCode;
	}

	public static void copyToClipboard(String data) {
		try {
			StringSelection selection = new StringSelection(data);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTab(int num) {
		try {
			for(int i=1;i<=num;i++) {
				Robot rbt = new Robot();
				rbt.keyPress(KeyEvent.VK_TAB);
				rbt.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(200);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void ctrlV() {
		try {
			Robot rbt = new Robot();
			rbt.keyPress(KeyEvent.VK_CONTROL);
			rbt.keyPress(KeyEvent.VK_V);
			rbt.keyRelease(KeyEvent.VK_CONTROL);
			rbt.keyRelease(KeyEvent.VK_V);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void enterKey() {
		try {
			Robot rbt = new Robot();
			rbt.keyPress(KeyEvent.VK_ENTER);
			rbt.keyRelease(KeyEvent.VK_ENTER);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//************************************


	/*public void updateJSONvalue(String strinputXML, ArrayList<String> testList1, String strTestCaseName, int mutpAPICount) {
		// TODO Auto-generated method stub
		FileWriter file=null;

		try{

			JSONParser parser = new JSONParser();
			FileReader fr = new FileReader(strinputXML);
			Object obj = parser.parse(fr);
			org.json.simple.JSONObject originalJsonObject = (org.json.simple.JSONObject) parser.parse(new FileReader(strinputXML));
			//JSONObject originalJsonObject =  (JSONObject) parser.parse(new FileReader(strinputXML));

			for (int i1=0; i1<testList1.size(); i1++){
				StringBuilder sb = new StringBuilder(testList1.get(i1).toString());
				String[] sbArr;
				sbArr = sb.toString().split("/");
				String tag = sbArr[2];
				String value = sbArr[3];
				String parentKey = sbArr[1]+";"+tag;
				parentKey = parentKey.replace("root;", "");

				//Checking if we need to fetch values from previous API
				int colonCount = value.length() - value.replaceAll(";","").length();
				if(value.contains(";") && colonCount==2 && mutpAPICount>1) {
					String tagName = value.split(";")[1];
					int outputIndex = Integer.parseInt(value.split(";")[0]);
					int index = Integer.parseInt(value.split(";")[2]);
					value = value.replace(value, multipleConcatURL(tagName, strTestCaseName, outputIndex, index));	
				}
				ArrayList<String> jsonElementList = new ArrayList<>();
				jsonElementList.add(originalJsonObject.toString());
				String[] originalKey=parentKey.split(";");

				for (int i = 0; i < originalKey.length; i++) {
					org.json.simple.JSONObject tempJSON = (org.json.simple.JSONObject) parser.parse(jsonElementList.get(i));
					if(i== originalKey.length-1){
						jsonElementList.add(value);
					} else {
						jsonElementList.add(tempJSON.get(originalKey[i]).toString());
					}
				}

				for (int i = jsonElementList.size()-2,j=originalKey.length-1; i>=0; i--,j--) {

					org.json.simple.JSONObject tempJSON = (org.json.simple.JSONObject) parser.parse(jsonElementList.get(i));
					System.out.println("Object to be update: "+tempJSON);
					System.out.println("Key: "+originalKey[j]);
					System.out.println("Value: "+jsonElementList.get(i+1));

					JsonParser jp = new JsonParser();
					JsonElement je = jp.parse(jsonElementList.get(i+1));
					tempJSON.put(originalKey[j], je);
					jsonElementList.set(i, tempJSON.toString());
				}
				originalJsonObject = (org.json.simple.JSONObject) parser.parse(jsonElementList.get(0));
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(originalJsonObject.toJSONString());
			String prettyJsonString = gson.toJson(je);
			file = new FileWriter(strinputXML);
			file.write(prettyJsonString);

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	public String getJsonWebserviceCallDelete(String strEndPointUrl, String strClientID, String strUserToken , String strErrRspChkFlag){

		String strResponsVal="";
		try 
		{
			HttpDelete httpdel = new HttpDelete(strEndPointUrl);

			// put all of the header parameters in one string using setHeader
			httpdel.setHeader("Accept", "application/json");
			httpdel.setHeader("X-IBM-Client-Id",strClientID);
			httpdel.setHeader("Authorization", strUserToken);		

			// Get API response
			strResponsVal=deleteMethodRespVal(httpdel,"getJsonWebserviceCallDelete" , strErrRspChkFlag);

		} catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "getJsonWebserviceCallDelete");
		}
		return strResponsVal;


	}

	public String deleteMethodRespVal(HttpDelete httpDel , String strMethodName, String strErrRspChkFlag)  
	{
		String strResponsVal = "";
		try 
		{
			//Create DefaultHttpClient object
			DefaultHttpClient Client = new DefaultHttpClient();
			// Send the request; It will immediately return the response in the form of HttpResponse object
			HttpResponse response = Client.execute(httpDel);

			// Verify the successful generation of API response through Status Code ( 200 code indicates successful generation) and retrieve the response value
			//Get Status code
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != 200) 
			{
				if (!(strErrRspChkFlag.equalsIgnoreCase("YES")))
				{
					objReport.setValidationMessageInReport("FAIL","Method "+strMethodName+"(DELETE): Failed to generate correct API Response due to Error code "+statusCode);						
				} 

				else
				{
					objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(DELETE): API Error Response is generated with Error code "+statusCode);						

				}
			}	
			else 
			{	
				//Get API response	
				objReport.setValidationMessageInReport("PASS","Method "+strMethodName+"(DELETE): Correct API Response is generated");


			}

			//Read the response value line by line and store it in 'responseString' StringBuilder variable
			BufferedReader breader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder responseString = new StringBuilder();
			String line = "";
			while ((line = breader.readLine()) != null) 
			{
				responseString.append(line);
			}
			//close the BufferedReader(breader) object
			breader.close();

			//Save the above created StringBuilder variable(responseString) data into string variable(strResponsVal)
			strResponsVal = responseString.toString();
		}
		catch (Exception e) 
		{					
			objReport.writeStackTraceErrorInReport(e, "getMethodRespVal");
		}
		return strResponsVal;
	}

	public void validateResponsDelete(String strScriptName)  
	{
		Boolean valGetRespStatusFlag= false;
		Integer intTestCaseRowNum=0;
		APIFunctions webserCommnFunc = new APIFunctions();
		try 
		{
			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to GET worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb1=new HSSFWorkbook(io);		
			HSSFSheet sheet=wb1.getSheet("Delete");

			//Get total used row count of 'GET' worksheet
			int rowNum = sheet.getLastRowNum()+1;

			boolean MultipleAPIValidateFlag=false;

			//Iterate through 1st column of all the rows of 'GET' worksheet till a match is found same as Test Script name specified by 'strScriptName' 						
			for(int i=1;i<rowNum;i++)
			{
				if (sheet.getRow(i).getCell(getcolumn("A"))!=null && (MultipleAPIValidateFlag==false))
				{
					if(APICreateAndExecute.mutpDELETECount>1 && strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()) ){
						i=i+(APICreateAndExecute.mutpDELETECount-1);
					}
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim())){

						MultipleAPIValidateFlag = true;
						valGetRespStatusFlag= true;

						//Get Test case name
						strTestCase = sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim();//Script Name

						//String strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).getStringCellValue().trim();
						String strIterationNumber = sheet.getRow(i).getCell(getcolumn("B")).toString().trim();

						strInputDataFolder = Runner.properties.getProperty("APIResponseDSFolderPath");
						String strInputDataSheet=strInputDataFolder+strScriptName+"_Data_Sheet.xlsx";

						if(APICreateAndExecute.mutpAPICount>1){

							strInputDataSheet=strInputDataFolder+strScriptName+"_Data_Sheet"+(APICreateAndExecute.mutpAPICount-1)+".xlsx";
						}

						//Validate Web service response data
						webserCommnFunc.validateWebSrvcResponseDELETEFieldValue(strInputDataSheet, strInputDataSheet, "Delete_Validation", strTestCase, APICreateAndExecute.mutpDELETECount);

						intTestCaseRowNum=webserCommnFunc.getTestCaseRowNumber(dataSheet, strTestCase, strIterationNumber);
						//webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "PASS");
					}
				}
			}
			if(valGetRespStatusFlag==false)
			{
				//	webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "FAIL");
				objReport.setValidationMessageInReport("FAIL","Method validateResponsGet : Please check Test script '"+strScriptName+"' is available under Test script column of 'GET' Worksheet" ); 							

			}
		}
		catch (Exception e) {	
			//webserCommnFunc.updateExecStatusExcelGET(dataSheet, intTestCaseRowNum, "FAIL");
			objReport.writeStackTraceErrorInReport(e, "validateResponsGet (GET)");	
		}
	}

	public void validateWebSrvcResponseDELETEFieldValue(String strInputDataSheet,	String strExcelWebSrvcFildValdPath, String strWorkSheet,String strTestCase, int count2)  
	{
		Boolean valRespStatusFlag= false;
		try
		{
			//Creating sheet for storing the response from web service
			FileInputStream fileInputStream_DS = new FileInputStream(strInputDataSheet);
			XSSFWorkbook excelWorkBook_DS = new XSSFWorkbook(fileInputStream_DS);
			XSSFSheet excelSheet_DS_Output = excelWorkBook_DS.getSheet("Output");

			FileInputStream fileInputStream_Webservc= new FileInputStream(strExcelWebSrvcFildValdPath);
			XSSFWorkbook wb1=new XSSFWorkbook(fileInputStream_Webservc);
			XSSFSheet webSerRspnFldValsheet=wb1.getSheet(strWorkSheet);

			int rowNum = webSerRspnFldValsheet.getLastRowNum()+1;

			String strArr[];
			String strTagArr[];
			String strTagValArr[];

			String expected_Field_Val="";
			String actual_Field_Val="";
			int fld_loc=0;
			int cnt=1;
			int intcnt;
			int OutputSheetrowNum = excelSheet_DS_Output.getLastRowNum()+1;
			int m;

			//creating loop to search the required Test case and split the tag name from value
			for(m=1;m<rowNum;m++){

				if (webSerRspnFldValsheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase.trim()) ){

					if(count2>1){
						m=m+(count2-1);
					}
					int colNum = webSerRspnFldValsheet.getRow(m).getLastCellNum();
					for(int k=1;k<colNum;k++){
						if (webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim().length()>0)
						{
							strArr=(webSerRspnFldValsheet.getRow(m).getCell(k).getStringCellValue().trim()).split("@");

							/*//Added for Multiple depedent APIs'(Validation will be picked from previous API's response)
							for (int i = 0; i < strArr.length; i++) {
								if(strArr[i].contains("#") && APICreateAndExecute.mutpAPICount>1){
									int startIndex = strArr[i].indexOf("#");
									int lastIndex = strArr[i].lastIndexOf("#");

									String tagDetails = strArr[i].substring(startIndex+1, lastIndex);
									String ArrTagname[] = tagDetails.split(";");
									int outputIndex = Integer.parseInt(ArrTagname[0]);
									int index = Integer.parseInt(ArrTagname[2]);
									strArr[i] = strArr[i].replace("#"+tagDetails+"#", multipleConcatURL(ArrTagname[1], strTestCase, outputIndex, index));
								}
							}*/


							if(strArr.length>2)
							{
								if(strArr[0].matches("[0-9]+"))
								{
									intcnt=Integer.parseInt(strArr[0]);
									strTagArr=strArr[1].split(";");
									strTagValArr=strArr[2].split(";");
								}
								else
								{
									intcnt=1;
									strTagArr=strArr[0].split(";");	
									strTagValArr=(strArr[1]+"@"+strArr[2]).split(";");
								}
							}
							else
							{
								intcnt=1;
								strTagArr=strArr[0].split(";");	
								strTagValArr=strArr[1].split(";");
							}

							for(int i=0;i<strTagArr.length;i++)
							{   cnt=1;
							expected_Field_Val="";
							actual_Field_Val="";


							for	(fld_loc=1;fld_loc<OutputSheetrowNum;fld_loc++)
							{
								if(excelSheet_DS_Output.getRow(fld_loc).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTagArr[i]))
								{
									if(cnt==intcnt)
										break;
									else
										cnt=cnt+1;
								}							
							}

							if(fld_loc==OutputSheetrowNum)
							{
								objReport.setValidationMessageInReport("FAIL"," API Response Validation : XML tag '"+strTagArr[i] +"' is not available in API response XML"); 										
							}	

							// Expected Input Field
							expected_Field_Val=strTagValArr[i];

							//Response from webservice
							actual_Field_Val=excelSheet_DS_Output.getRow(fld_loc).getCell(1).getStringCellValue().trim();// Response Field Value in Output Worksheet					

							if(!expected_Field_Val.equalsIgnoreCase(actual_Field_Val))
							{

								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("FAIL","'"+strTagArr[i] +"'  field Validation Deletion Failed------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		

							}
							else
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("PASS","'"+strTagArr[i] +"'  field Validation Deleted Successfully------" + "Expected value: " +expected_Field_Val + " ; Webservice API Response value : "+actual_Field_Val); 		
							}
							}
						}
					}
					break;
				}

			}
			if(valRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateWebSrvcResponseFieldValue : Please check Test Case '"+strTestCase+"' is available under Test Case column of 'Webservice_Validation' Worksheet" ); 							
			}
		}

		catch (Exception e) {
			objReport.writeStackTraceErrorInReport(e, "validateWebSrvcResponseFieldValue");
		}
	}

	public void validateXMLTagVal(String strXMLTagFldValdExcel, String strWorkSheet,String strTestCase,String xmlDocument) {
		Boolean valRespStatusFlag= false;
		try {

			String strArr[]=null;
			FileInputStream fisXMLTagValdExcel= new FileInputStream(strXMLTagFldValdExcel);
			XSSFWorkbook wb1=new XSSFWorkbook(fisXMLTagValdExcel);
			XSSFSheet XMLTagValdSheet=wb1.getSheet(strWorkSheet);

			int rowNum = XMLTagValdSheet.getLastRowNum()+1;

			String expXMLTagVal="";
			String actXMLTagVal="";
			String xmlTagPath="";
			String xmlTagName="";
			String xmlTagNameArr[]=null;

			ArrayList<String> actTagValLst= new ArrayList<String>();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(xmlDocument));

			//XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = XPathFactory.newInstance().newXPath();
			xpath.setNamespaceContext(new NamespaceResolver(document));

			//creating loop to search the required Test case and split the tag name from value
			for(int m=1;m<rowNum;m++)
			{
				if (XMLTagValdSheet.getRow(m).getCell(0).getStringCellValue().trim().equalsIgnoreCase(strTestCase.trim()) )
				{

					int colNum = XMLTagValdSheet.getRow(m).getLastCellNum();
					for(int k=1;k<colNum;k++)
					{
						if (XMLTagValdSheet.getRow(m).getCell(k).getStringCellValue().trim().length()>0)
						{
							actTagValLst= new ArrayList<String>();
							valRespStatusFlag=true;

							strArr=(XMLTagValdSheet.getRow(m).getCell(k).getStringCellValue().trim()).split("@");
							xmlTagPath=strArr[0].trim();
							expXMLTagVal=strArr[1].trim();
							xmlTagNameArr=xmlTagPath.split("/");
							xmlTagName=xmlTagNameArr[xmlTagNameArr.length-1];

							XPathExpression expr = xpath.compile(xmlTagPath+"/text()");

							//Search XPath expression
							Object result = expr.evaluate(document, XPathConstants.NODESET);

							//Iterate over results and fetch book names
							NodeList nodes = (NodeList) result;
							for (int i = 0; i < nodes.getLength(); i++) 
							{
								actTagValLst.add(nodes.item(i).getNodeValue());
							}

							//Response from webservice
							actXMLTagVal=actTagValLst.get(0).toString().trim();

							if(!(expXMLTagVal.equalsIgnoreCase(actXMLTagVal)))
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("FAIL","'"+xmlTagName +"'  field Validation------" + "Expected value: " +expXMLTagVal + " ; Webservice API Response value : "+actXMLTagVal); 		
							}
							else
							{
								valRespStatusFlag=true;
								objReport.setValidationMessageInReport("PASS","'"+xmlTagName +"'  field Validation------" + "Expected value: " +expXMLTagVal + " ; Webservice API Response value : "+actXMLTagVal); 		
							}
						}
					}
				}
			}

			if(valRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateXMLTagVal : Please check Test Case '"+strTestCase+"' is available under Test Case column of 'Webservice_Validation' Worksheet" ); 							
			}

		}
		catch (Exception e)
		{
			objReport.writeStackTraceErrorInReport(e, "validateXMLTagVal");
		}

	} 


	public void validateSoapResponse(String strScriptName, String worksheetName) 

	{
		//int countPost1 = 0;
		//CommonFunctions commonFunc= new CommonFunctions();
		Boolean valPostRespStatusFlag= false;
		try 
		{
			APIFunctions webserCommnFunc = new APIFunctions();

			//Create File object for the 'dataSheet' Excel file
			FileInputStream io = new FileInputStream(dataSheet);

			//Navigate to POST worksheet of the 'dataSheet' Excel file
			HSSFWorkbook wb=new HSSFWorkbook(io);
			HSSFSheet sheet=wb.getSheet(worksheetName);

			//Get total used row count of 'POST' worksheet
			int rowNum = sheet.getLastRowNum()+1;
			boolean MultipleAPIPost=false;
			String strOutputResponseFolder="";
			String xmlDocument="";

			//Iterate through 1st column of all the rows of 'POST' worksheet till a match is found same as Test Script name specified by 'strScriptName' 
			for(int i=1;i<rowNum;i++)
			{
				if (sheet.getRow(i).getCell(getcolumn("A"))!=null)
				{					
					if(strScriptName.equalsIgnoreCase(sheet.getRow(i).getCell(getcolumn("A")).getStringCellValue().trim()))
					{						
						valPostRespStatusFlag= true;
						String strInputDataSheet = Runner.strResourceFldLoc +sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim()+".xlsx";
						String responseFolderColumn = "";
						if(worksheetName.equalsIgnoreCase("GET")){
							responseFolderColumn = "N";
						}else if(worksheetName.equalsIgnoreCase("POST")){
							responseFolderColumn = "E";
						}
						
						if (sheet.getRow(i).getCell(getcolumn(responseFolderColumn))!=null)
						{
							//pOutputResponseXML Column value ()
							strOutputResponseFolder = Runner.properties.getProperty("APIResponseXMLFolderPath")+ sheet.getRow(i).getCell(getcolumn(responseFolderColumn)).getStringCellValue().trim();
						}
						else
						{
							strOutputResponseFolder = Runner.properties.getProperty("APIResponseXMLFolderPath").trim();						
						}

						xmlDocument=strOutputResponseFolder+"/"+strScriptName+".xml";
						//Validate Web service response data
						webserCommnFunc.validateXMLTagVal(strInputDataSheet,"Webservice_Validation", strScriptName,xmlDocument);
					}
				}
			}

			if(valPostRespStatusFlag==false)
			{
				objReport.setValidationMessageInReport("FAIL","Method validateSoapResponse : Please check Test script '"+strScriptName+"' is available under Test script column of 'POST' Worksheet" ); 							
			}
		}
		catch (Exception e) 
		{
			objReport.writeStackTraceErrorInReport(e, "validateSoapResponse("+worksheetName+")");	
		}
	}


}


class NamespaceResolver implements NamespaceContext
{
	//Store the source document to search the namespaces
	private Document sourceDocument;

	public NamespaceResolver(Document document) {
		sourceDocument = document;
	}

	//The lookup for the namespace uris is delegated to the stored document.
	public String getNamespaceURI(String prefix) {
		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
			return sourceDocument.lookupNamespaceURI(null);
		} else {
			return sourceDocument.lookupNamespaceURI(prefix);
		}
	}

	public String getPrefix(String namespaceURI) {
		return sourceDocument.lookupPrefix(namespaceURI);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String namespaceURI) {
		return null;
	}
}
