package GenericFunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Reporting.Report;
import TestScriptRunner.Runner;

public class FeatureFile_RTC {

	Report objReport = new Report();
	FileWriter file = null;
	HashMap<String, String> allprojects;
	HttpContext httpContext;
	CloseableHttpClient httpClient;
	String acceptanceCriteria = "";

	public void  createFeatureFile(String strRTCStoryId) {
		try {
			String environment = Runner.properties.getProperty("environment");
			String strRTCProjName = Runner.properties.getProperty("RTCProjectName");
			RTCClient(environment);
			getAllProjects(environment);
			String projrctId = getProjectID(strRTCProjName);
			String query;
			if(environment.equalsIgnoreCase("dev")) {
				query = "https://dev2developer.aetna.com/ccm/oslc/contexts/"+projrctId+"/workitems?oslc_cm.query=dc:identifier=%22"+strRTCStoryId+"%22";
			} else {
				query = "https://developer.aetna.com/ccm/oslc/contexts/"+projrctId+"/workitems?oslc_cm.query=dc:identifier=%22"+strRTCStoryId+"%22";
			}
			String responseString = getJsonResponse(query);

			//Fetching the acceptance criteria from the output JSON file
			parseJsonString(responseString);
			
			//Remove quotes from start & end
			acceptanceCriteria = acceptanceCriteria.substring(1, acceptanceCriteria.length()-1);

			//Convert HTML string to normal string
			acceptanceCriteria = htmlParser(acceptanceCriteria);
			
			//Remove extra blank lines
			acceptanceCriteria = acceptanceCriteria.toString().replaceAll("(?m)^[ \t]*\r?\n", "");

			//Saving the acceptance criteria into Feature file
			File bddFeatureFile = new File(Runner.resdir+"\\"+Runner.strBDDFeatureFile);
			if(bddFeatureFile.exists()) {
				bddFeatureFile.delete();
			}
			
			file = new FileWriter(Runner.resdir+"\\"+Runner.strBDDFeatureFile);
			file.write(acceptanceCriteria);
			file.flush();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String htmlParser(String htmlString) {
		String normalString = htmlString;
		try {
			Map<String, String> htmlCharEntities = new HashMap<>();
			htmlCharEntities.put("&nbsp;", " ");
			htmlCharEntities.put("&lt;", "<");
			htmlCharEntities.put("&gt;", ">");
			htmlCharEntities.put("&amp;", "&");
			htmlCharEntities.put("&quot;", "\"");
			htmlCharEntities.put("&apos;", "'");
			htmlCharEntities.put("\\<.*?>", System.getProperty("line.separator"));
			
			for (Map.Entry<String, String> entry : htmlCharEntities.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    normalString = normalString.replaceAll(key, value);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return normalString;
	}

	public String getJsonResponse(String query) {
		StringBuilder responseString = new StringBuilder();
		try {
			HttpGet getAllprojects = new HttpGet(query);
			getAllprojects.addHeader("accept","text/json");
			HttpResponse responseGetNew = httpClient.execute(getAllprojects,httpContext);
			InputStream responseStream = responseGetNew.getEntity().getContent();

			//Converting the response into string
			BufferedReader br = null;
			String line;
			br = new BufferedReader(new InputStreamReader(responseStream));
			while ((line = br.readLine()) != null) {
				responseString.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception in getting API response from RTC: "+e.getMessage());
		}
		return responseString.toString();
	}

	public void RTCClient(String environment) {
		CookieStore cookieStore = new BasicCookieStore();
		httpContext = new BasicHttpContext();
		httpClient = HttpClientBuilder.create().build();
		String userName;
		String password;
		String authorizeUrl;
		if (environment.equalsIgnoreCase("dev")){
			userName = Runner.properties.getProperty("usernameDev");
			password = Runner.properties.getProperty("passwordDev");
			authorizeUrl = Runner.properties.getProperty("securityCheckUrlDev");
		} else {
			userName = Runner.properties.getProperty("usernameProd");
			password = Runner.properties.getProperty("passwordProd");
			authorizeUrl = Runner.properties.getProperty("securityCheckUrlProd");
		}

		try {
			httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			String jazzUserid = "j_username=".concat(userName);
			String jazzPassword = "j_password=".concat(password);
			String cred = jazzUserid+"&"+jazzPassword;

			HttpPost postReq = new HttpPost(authorizeUrl);
			StringEntity entity = new StringEntity(cred);
			postReq.setEntity(entity);
			postReq.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
			postReq.addHeader("User-Agent", "MSIE 8.0");
			httpClient.execute(postReq,httpContext); 
		} catch(Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception in connection with RTC: "+e.getMessage());
		}
	}

	public void getAllProjects(String environment) {
		try {
			HashMap<String,String> values = new HashMap<String,String>();
			HttpContext context = httpContext;
			String urlgetAllprojects;
			if (environment.equalsIgnoreCase("dev")){
				urlgetAllprojects = Runner.properties.getProperty("hostnameDev");
			} else {
				urlgetAllprojects = Runner.properties.getProperty("hostnameProd");
			}

			String getRTCoslcUrl = urlgetAllprojects.concat("/oslc/projectareas");
			HttpGet getAttributes = new HttpGet(getRTCoslcUrl);
			getAttributes.addHeader("accept","text/json");
			getAttributes.addHeader("Content-Type", "application/x-www-form-urlencoded"); 
			getAttributes.addHeader("User-Agent", "MSIE 8.0");

			HttpResponse responsePost = httpClient.execute(getAttributes,context);
			String response = httpResponseParse(responsePost);
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
			JsonArray projectAreaArray = getJsonArray(jsonObject, "oslc_cm:results");   		
			for (JsonElement elem : projectAreaArray) {
				JsonElement name = getJsonElement(elem.getAsJsonObject(), "dc:description");
				JsonElement value = getJsonElement(elem.getAsJsonObject(), "rdf:resource");
				String[] projectDetails = value.getAsString().split("\\/");
				String projectID = projectDetails[projectDetails.length-1];
				values.put(name.getAsString(),projectID);
			}
			allprojects = values;

		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception in getting all projects from RTC for "+environment+" environment.");
		} 
	}

	public String httpResponseParse(HttpResponse response){
		try {
			StringBuilder responseString = new StringBuilder();
			InputStream responseStream = response.getEntity().getContent();
			BufferedReader brNew = null;
			String line=null;
			brNew = new BufferedReader(new InputStreamReader(responseStream));
			while ((line = brNew.readLine()) != null) {
				responseString.append(line);
			}
			return responseString.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JsonArray getJsonArray(JsonObject jsonObject,String arrayKey){
		JsonArray jsonArray = jsonObject.getAsJsonArray(arrayKey);
		return jsonArray;

	}

	public JsonElement getJsonElement(JsonObject jsonObject,String element){
		JsonElement jsonElement;
		jsonElement = jsonObject.get(element);
		return jsonElement;
	}

	public String getProjectID (String projectName){
		String projectID = allprojects.get(projectName);
		if(projectID == null) {
			objReport.setValidationMessageInReport("FAIL", "Project name: "+projectName+" not fount in RTC.");
		}
		return projectID;
	}

	public void parseJsonString(String json) {
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(json.toString());
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			for(Map.Entry<String,JsonElement> field : entrySet){
				String key = field.getKey();
				JsonElement value = jsonObject.get(key);

				if ((value.isJsonNull() || value.isJsonPrimitive())) {
					if(key.equalsIgnoreCase("rtc_cm:com.ibm.team.apt.attribute.acceptance")){
						acceptanceCriteria = field.getValue().toString().trim();
					} 
				} else {
					checkJsonValueType(value);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void checkJsonValueType(JsonElement value) {
		if (value.isJsonObject()) {
			parseJsonString(value.toString());
		} else if (value.isJsonArray()) {
			JsonArray jsonArray = value.getAsJsonArray();
			for (JsonElement jsonArrayElement : jsonArray){				
				checkJsonValueType(jsonArrayElement);
			}
		}
	}
}