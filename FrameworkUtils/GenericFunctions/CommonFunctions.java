package GenericFunctions;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
//import javax.imageio.ImageWriteParam;
//import javax.imageio.ImageWriter;
//import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Reporting.Report;
import TestScriptRunner.Runner;

public class CommonFunctions 
{

	public static String originalimgpath="", path="";

	/**
	 * @Name LoadProperty   
	 * @param String strFile- Contains the properties file location
	 * @description -Loads the Properties file available in the location specified by 'strFile'
	 */

	Report objReport=new Report();

	//public static Properties LoadProperty(File strFile)
	public static Properties LoadProperty(String  strFileLocation)
	{
		Properties properties = null ;
		try
		{
			File file = new File(strFileLocation);
			FileInputStream fileInput = new FileInputStream(file);
			properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
		}
		catch(Exception e)
		{
			Runner.loadPropFileFlag=true;
			//System.out.println("Fail Loading Property File");
		}

		return properties;
	}


	//@SuppressWarnings("unchecked")
	public static void captureFailScreenshot() 
	{
		try {
			String strStartTime = new SimpleDateFormat("MMddyy_HHmmss").format(Calendar.getInstance().getTime()).replace(":", ".").replace("-", ".");		
			String fileName = Runner.driverMap.get("TCResultFolderLocation").toString()+"\\"+Runner.driverMap.get("TestScriptName").toString()+strStartTime+".png";
			File scrFile = ((TakesScreenshot)WebMobileFunctions.driver).getScreenshotAs(OutputType.FILE);
			//if(!new File(Runner.driverMap.get("TCResultFolderLocation").toString()).exists())
			//new File(Runner.driverMap.get("TCResultFolderLocation").toString()).mkdirs();
			FileUtils.copyFile(scrFile, new File(fileName));
			compressScreenshot(fileName);
			originalimgpath=originalimgpath+fileName+"####";
			Runner.driverMap.put("Screenshot_Link", originalimgpath);
		}
		catch(Exception e) 
		{
			//e.printStackTrace();
			Runner.driverMap.put("Screenshot_Link", "");
		}
	}

	// @SuppressWarnings("unchecked")
	public static void compressScreenshot(String imgpath){

		try{

			//imgpath.replaceAll("\\\\", "/");

			BufferedImage image = ImageIO.read(new File(imgpath));

			File compressedImageFile = new File(imgpath.substring(0, imgpath.lastIndexOf(".png"))+"_compressed.png");
			String compressedImageFilePath = compressedImageFile.getAbsolutePath();
			//setting the scaled image dimension
			int scaledWidth = (int) (image.getWidth() * 0.17);
			int scaledHeight = (int) (image.getHeight() * 0.2);
			Image tmp = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			// scales the input image to the output image
			BufferedImage outputImage = new BufferedImage(scaledWidth,scaledHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = outputImage.createGraphics();
			g.drawImage(tmp, 0, 0, java.awt.Color.WHITE, null);
			ImageIO.write(outputImage, "png" , compressedImageFile);
//			System.out.println("Resized screenshot placed at- "+ compressedImageFile.getAbsolutePath());
			path=path+compressedImageFilePath+"####";
			Runner.driverMap.put("Compressed_Screenshot_Link",path );

			if(Runner.strBDDIndicator.equalsIgnoreCase("Y")||Runner.strBDDIndicator.equalsIgnoreCase("Yes")){
//				Runner.strBDDScreenshotMap.put(Runner.strBDDScenario.getName(), compressedImageFilePath);
				Runner.strBDDScreenshotMap.put(Runner.strBDDScenario.getName()+"@"+Runner.bddStpCnt+"@"+Runner.bddCurrentStep, compressedImageFilePath);
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public  String getBDDArgsWithValue(String testcasename,String columnname, String strItrNo) {
		FileInputStream fis;
		String strCellValue = "";
		boolean getParamValFlag=false;
		Report objReport= new Report();
		try {
			fis = new FileInputStream(Runner.strtTestDataFile);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet ws = wb.getSheet(Runner.strDataSheetName);
			int rows = ws.getPhysicalNumberOfRows();
			for(int i=0; i<rows; i++){
				if(ws.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(testcasename)){
					int cols = ws.getRow(0).getPhysicalNumberOfCells();
					for (int j = 0; j < cols; j++) {
						if(ws.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase(strItrNo)) {
							if(ws.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase(columnname)) {
								strCellValue = ws.getRow(i).getCell(j).getStringCellValue().trim();
								getParamValFlag=true;
								break;
							}
						}
					}
				}
			} 

			if(getParamValFlag==false)
			{                                                              
				objReport.setValidationMessageInReport("FAIL", "Failed to retrieve the value of '"+columnname+"' for Iteration No. '"+strItrNo+"' of Scenario '"+testcasename+"' from Data agruement sheet.");
			}
		} catch (Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Failed to retrieve the value of '"+columnname+"' for Iteration No. '"+strItrNo+"' of Scenario '"+testcasename+"' from Data agruement sheet");
			e.printStackTrace();
		} 
		return strCellValue;
	}


	public static  String getBDDArgs(String testcasename,String columnname, String strItrNo) {
		FileInputStream fis;
		String strCellValue = "";
		Boolean colvalfound=false;
		try {
			fis = new FileInputStream(Runner.strtTestDataFile);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet ws = wb.getSheet(Runner.strDataSheetName);
			int rows = ws.getPhysicalNumberOfRows();
			for(int i=0; i<rows; i++){
				if(ws.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(testcasename)){
					int cols = ws.getRow(0).getPhysicalNumberOfCells();
					for (int j = 0; j < cols; j++) {
						if(ws.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase(strItrNo)) {
							if(ws.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase(columnname)) {
								strCellValue = ws.getRow(i).getCell(j).getStringCellValue().trim();
								colvalfound=true;
								break;
							}

						}
					}
				}
				if(colvalfound)
					break;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return strCellValue;
	}

	/**
	 * This method verifies the specific text content of MS word document
	 * @param strDocPath MS word document path in the system
	 * @returns strContentToBeChecked Expected text content to be validated
	 * @applicableTo  Desktop                                       
	 */
	/*public void validateContentInMSWordDocument( String strDocPath , String strContentToBeChecked)
	{
		try{
			String strDocArr[]=strDocPath.split("\\\\");
			BodyContentHandler handler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			FileInputStream inputstream = new FileInputStream(new File(strDocPath));
			ParseContext pcontext = new ParseContext();

			//OOXml parser
			OOXMLParser  msofficeparser = new OOXMLParser (); 
			msofficeparser.parse(inputstream, handler, metadata,pcontext);

			String strDocumentContent=handler.toString().toLowerCase();

			if(strDocumentContent.contains(strContentToBeChecked.toLowerCase()))
			{
				//System.out.println(strContentToBeChecked + " is available in the '"+strDocArr[strDocArr.length-1]+"'");			
				objReport.setValidationMessageInReport("PASS", "'"+strContentToBeChecked + "' is available in the '"+strDocArr[strDocArr.length-1]+"'");					
			}
			else
			{
				//System.out.println(strContentToBeChecked + " is not available in the '"+strDocArr[strDocArr.length-1]+"'");
				objReport.setValidationMessageInReport("FAIL", "'"+strContentToBeChecked + "' is not available in the '"+strDocArr[strDocArr.length-1]+"'");									
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "'validateContentInMSWordDocument' method: Failed due to Exception :"+e.toString());									
		}
	}*/

	/**
	 * This method verifies the specific text content of PDF document
	 * @param strPDFDocPath PDF document path in the system
	 * @returns strContentToBeChecked Expected text content to be validated
	 * @applicableTo  Desktop                                       
	 */
	/*public  void validateContentInPDFDocument(String strPDFDocPath , String strContentToBeChecked)
	{
		try{
			String strDocArr[]=strPDFDocPath.split("\\\\");
			BodyContentHandler handler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			FileInputStream inputstream = new FileInputStream(new File(strPDFDocPath));
			ParseContext pcontext = new ParseContext();

			//parsing the document using PDF parser
			PDFParser pdfparser = new PDFParser(); 
			pdfparser.parse(inputstream, handler, metadata,pcontext);

			String strDocumentContent=handler.toString().toLowerCase();

			if(strDocumentContent.contains(strContentToBeChecked.toLowerCase()))
			{
				System.out.println(strContentToBeChecked + " is available in the '"+strDocArr[strDocArr.length-1]+"'");
				objReport.setValidationMessageInReport("PASS", "'"+strContentToBeChecked + "' is available in the '"+strDocArr[strDocArr.length-1]+"'");					
			}
			else
			{
				System.out.println(strContentToBeChecked + " is not available in the '"+strDocArr[strDocArr.length-1]+"'");
				objReport.setValidationMessageInReport("FAIL", "'"+strContentToBeChecked + "' is not available in the '"+strDocArr[strDocArr.length-1]+"'");									
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "'validateContentInPDFDocument' method: Failed due to Exception :"+e.toString());									
		}
	}*/

	public static String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCellValue = cell.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				Double value = cell.getNumericCellValue();
				Long longValue = value.longValue();
				strCellValue = new String(longValue.toString());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCellValue = "";
				break;
			}
		}
		return strCellValue;
	}
	
	public static Map<String,Map<Integer,String>> readFeatureFile(String bddFeatureFile) {
		Map<String,Map<Integer,String>> featureMap = new HashMap<>();
		try {
			
			Map<Integer,String> stepMap = new HashMap<>();
			int i=1;
			String scenarioName = "";
			BufferedReader br = new BufferedReader(new FileReader(bddFeatureFile));
			String line = br.readLine();

			while (line != null) {
				line=line.trim();
				if(line.startsWith("Scenario:")){
					scenarioName = line;
				} else if(!(line.startsWith("Feature:")||line.startsWith("Scenario:")||line.startsWith("@")||line.trim().equalsIgnoreCase(""))){
					stepMap.put(i, line);
					i++;
				}

				line = br.readLine();

				if(line != null){
					line=line.trim();
					if(line.startsWith("Scenario:") && !stepMap.isEmpty()){
						featureMap.put(scenarioName.split(":")[1].trim(), stepMap);
						scenarioName = line;
						stepMap = new HashMap<>();
						i=1;
					}
				} else if(line==null){
					featureMap.put(scenarioName.split(":")[1].trim(), stepMap);
				}
			}

			br.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
		return featureMap;
	}
	
	public static String readJSONFile (String fileName){
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
}
