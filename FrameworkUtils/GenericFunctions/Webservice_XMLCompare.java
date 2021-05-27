package GenericFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import Reporting.Report;

public class Webservice_XMLCompare {

	/**
	 * Script Name   : <b>Webservice_XMLCompare</b>
	 * Generated     : <b>Apr 7, 2017 10:25:54 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2017/04/07
	 * @author n077138
	 */

	String strCompareMappingSheet=null;
	String strlocationXML1 =null;
	String strlocationXML2 		=null;
	String strOutputLocation =null;
	String strMaxRecord =null;
	String strstartXML1=null;
	String strstartXML2 =null;
	
	public void webserviceCompareXMLs(String strScriptName, String dataSheet) throws Exception {
		// TODO Insert code here
		Report objCall = new Report();
		FileInputStream io1 = new FileInputStream(dataSheet);

		Workbook wb1 = WorkbookFactory.create(io1);
		Sheet sheet1=wb1.getSheet("COMPAREXMLs");

		int rowNum1 = sheet1.getLastRowNum()+1;
		for(int i1=1;i1<rowNum1;i1++)
		{
			if(strScriptName.equalsIgnoreCase(sheet1.getRow(i1).getCell(getcolumn("A")).getStringCellValue().trim())){

				strCompareMappingSheet = sheet1.getRow(i1).getCell(getcolumn("B")).getStringCellValue().trim();//Script Name
				strlocationXML1 = sheet1.getRow(i1).getCell(getcolumn("C")).getStringCellValue().trim();
				strlocationXML2 = sheet1.getRow(i1).getCell(getcolumn("D")).getStringCellValue().trim();
				strOutputLocation = sheet1.getRow(i1).getCell(getcolumn("E")).getStringCellValue().trim();
				strMaxRecord = sheet1.getRow(i1).getCell(getcolumn("F")).getStringCellValue().trim();
				strstartXML1 = sheet1.getRow(i1).getCell(getcolumn("G")).getStringCellValue().trim();
				strstartXML2 = sheet1.getRow(i1).getCell(getcolumn("H")).getStringCellValue().trim();

				System.gc();
				long startTime = System.currentTimeMillis();

				int MaxRecNunber= 0;

				if(strMaxRecord.contains("."))
				{
					int intdelLoc=strMaxRecord.indexOf(".");
					strMaxRecord=strMaxRecord.substring(0, intdelLoc);
				}

				if (strMaxRecord.matches("-?\\d+(\\.\\d+)?"))
				{
					MaxRecNunber=Integer.parseInt(strMaxRecord);
					String d_path;
					// putting the location of compare map excel location
					d_path=strCompareMappingSheet;
					//String d_path= "C://Data//Test//Compare Outputs//Compare.xlsx";
					FileInputStream io;
					//ArrayList<String> XMLTagVal= new ArrayList<String>();
					try {
						io = new FileInputStream(d_path);
						// opening compare map xls for comparing v1 and v2 headers
						Workbook wb = WorkbookFactory.create(io);
						Sheet sheet=wb.getSheetAt(0);
						int rowNum = sheet.getLastRowNum()+1;
						// finding the location as where to write the output data report
						String FILENAME=strOutputLocation;
						//BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
						String newLine = System.getProperty("line.separator");

						ArrayList<String> CompHeadertags= new ArrayList<String>();
						ArrayList<String> ParentTags= new ArrayList<String>();
						ArrayList<String> ParentTags_excel= new ArrayList<String>();
						CompHeadertags.add("iidValue");
						for(int i=1;i<rowNum;i++)
						{
							if (sheet.getRow(i).getCell(0) != null)
							{
								CompHeadertags.add( sheet.getRow(i).getCell(getcolumn("C")).getStringCellValue().trim());
								// Checking for parent tags
								if ((sheet.getRow(i).getCell(getcolumn("D")) != null) && (sheet.getRow(i).getCell(getcolumn("E")) != null))
								{
									ParentTags.add( sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim()+ ","+ sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim());
									ParentTags_excel.add( sheet.getRow(i).getCell(getcolumn("E")).getStringCellValue().trim()+ ","+ sheet.getRow(i).getCell(getcolumn("D")).getStringCellValue().trim());
								}
								else
								{
									ParentTags_excel.add(" , ");	
								}

							}
						}	
						io.close();
						// removing duplicate parent tag elements 

						Set<String> hs = new HashSet<>();
						hs.addAll(ParentTags);
						ParentTags.clear();
						ParentTags.addAll(hs);

						HashMap <String, ArrayList<String>> xml_v2 = new HashMap  <String, ArrayList<String>>();
						HashMap <String, ArrayList<String>> xml_v1 = new HashMap  <String, ArrayList<String>>();
						HashMap <String, ArrayList<String>> Output_rep = new HashMap  <String, ArrayList<String>>();

						// passing xml data loaction, main comp header and which one to choose ( v1-->1 && v2--> 0)
						System.out.println("passing xml data loaction, main comp header and which one to choose ( v1-->1 && v2--> 0)");

						//demo.showEventDemo(newLine+"Passing xml data loaction, main comp header and which one to choose ( v1-->1 && v2--> 0)");	
						xml_v1=getxmldata(strlocationXML1,ParentTags,CompHeadertags,1,ParentTags_excel,strstartXML1);
						xml_v2= getxmldata(strlocationXML2,ParentTags,CompHeadertags,0,ParentTags_excel,strstartXML2);
						System.out.println("Map  written for Xml_v1 ::" + xml_v1.size()+ "VAL:: "+ xml_v1);
						System.out.println("Map  written for Xml_v2 ::" + xml_v2.size()+ "VAL:: "+ xml_v2);

						//demo.showEventDemo(newLine+	"Map  written for Xml_v1 ::" + xml_v1.size() +newLine+"Map  written for Xml_v2 ::" + xml_v2.size());
						ArrayList<String> Errorlog= new ArrayList<String>();
						System.gc();
						Boolean status;
						System.out.println(" Starting comparison betwen Xml");
						//demo.showEventDemo(newLine +	" Starting comparison betwen Xml");
						for (String Xml_val_v2:xml_v2.keySet())
						{

							status= true;
							Errorlog.add(xml_v2.get(Xml_val_v2).get(0));
							for ( String Xml_val_v1:xml_v1.keySet()) {

								if (Xml_val_v1.equalsIgnoreCase(Xml_val_v2))
								{
									//if (xml_v2.get(Xml_val_cmp).size()== xml_v1.get(Xml_val_cmp).size())	{
									for ( int k=1; k<CompHeadertags.size();k++)
									{
										if (xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase(xml_v1.get(Xml_val_v2).get(k-1)))
										{
											Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
										}
										else 
										{
											if ((xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase("True") && (xml_v1.get(Xml_val_v2).get(k-1).equalsIgnoreCase("y"))))
											{
												Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ newLine+xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
											}
											else
												if ((xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase("False") && (xml_v1.get(Xml_val_v2).get(k-1).equalsIgnoreCase("N"))))
												{
													Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ newLine+xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
												}
												else
												{
													Errorlog.add("Mismatch  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ newLine+xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
													status= false;
												}

										}
									}

									break;
								}

							}
							if (Errorlog.size()==1)
							{
								Errorlog.add(" Mismatch in Element as no elements found in the V1 for the IID value " + Xml_val_v2);
								status= false;
							}
							if( status== false){
								Output_rep=Getmapvalue(Errorlog,Output_rep);
							}
							Errorlog.clear();
						}
						System.out.println("Writing data into xls" );

						if (Output_rep.size()>0){
							Writereport(Output_rep,CompHeadertags,FILENAME,MaxRecNunber);
							//String Script_Status="FAIL";
						//	objCall.set_validation_message_in_Report("Validating: Proper Matching ","Mismatch Found", "FAIL"); //LOG ERROR NEW			
							objCall.setValidationMessageInReport("FAIL","Validating: Proper Matching : Mismatch Found"); 		
							
							//HelperFile obj=new HelperFile();  // LOG ERROR NEW

							for (String Xml_val_v2:xml_v2.keySet())
							{
								status= true;
								Errorlog.add(xml_v2.get(Xml_val_v2).get(0));
								for ( String Xml_val_v1:xml_v1.keySet()) {

									if (Xml_val_v1.equalsIgnoreCase(Xml_val_v2))
									{
										for ( int k=1; k<CompHeadertags.size();k++)
										{
											if (xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase(xml_v1.get(Xml_val_v2).get(k-1)))
											{
												Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
											}
											else 
											{
												if ((xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase("True") && (xml_v1.get(Xml_val_v2).get(k-1).equalsIgnoreCase("y"))))
												{
													Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ newLine+xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
												}
												else
													if ((xml_v2.get(Xml_val_v2).get(k-1).equalsIgnoreCase("False") && (xml_v1.get(Xml_val_v2).get(k-1).equalsIgnoreCase("N"))))
													{
														Errorlog.add("Match  in " + xml_v2.get(Xml_val_v2).get(k-1) + "(v2) :::: "+ newLine+xml_v1.get(Xml_val_v2).get(k-1) + " (v1)");
													}
													else
													{
														objCall.setValidationMessageInReport("FAIL", "Output xml2 "+xml_v2.get(Xml_val_v2).get(k-1) + " ; Output xml1 "+xml_v1.get(Xml_val_v2).get(k-1) ); //LOG ERROR NEW			
													}
											}
										}
									}
								}
							}
						}
						else{
							objCall.setValidationMessageInReport("PASS", "Validating: Proper Matching : All Match Found"); //LOG ERROR NEW			

							objCall.setValidationMessageInReport("PASS", "Output xml2: All Match Found ; Output xml1: All Match Found " ); //LOG ERROR NEW			

						}

						System.out.println(" Completed Report :: "+ FILENAME);

					}catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					System.out.println(" max rec count is not numeric");
					//String newLine = System.getProperty("line.separator");

				}
				long stopTime = System.currentTimeMillis();
				long elapsedTime = stopTime - startTime;

				String newLine = System.getProperty("line.separator");
				System.out.println(newLine+	"Total execution time: " +
						String.format("%d min, %d sec",
								TimeUnit.MILLISECONDS.toHours(elapsedTime),
								TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
								TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))));
				System.out.println("Total execution time: " +
						String.format("%d min, %d sec",
								TimeUnit.MILLISECONDS.toHours(elapsedTime),
								TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
								TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))));

			}
		}
	}
	
	
	private static HashMap<String, ArrayList<String>> getxmldata(String XMLPath, ArrayList<String> parentTags,
			ArrayList<String> compHeadertags, int i, ArrayList<String> parentTags_excel, String XML_block_start) {
		// TODO Auto-generated method stub
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

		MyHandler handler = new MyHandler();

		handler.InputdataXLS=new ArrayList<>();	
		// Here the starting element is 1 as first is reserved for Map index value. Hence the passing 1 at last value
		handler.InputdataXLS= getinputTag( compHeadertags, i,1,",");
		//Here the starting element is 0 to capture all the parent tags. Hence the passing 0 at last value,1);
		handler.ParenTagName=getinputTag( parentTags, i,0,",");
		handler.ParenTagName_Xls=getinputTag(parentTags_excel, i,0,",");;
		handler.count=0.0;
		handler.Xml_block_Start=XML_block_start;

		try {

			SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(new File(XMLPath), handler);

			return (HashMap<String, ArrayList<String>>) handler.getEmpList();
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}


	}
	private static ArrayList<String> getinputTag(ArrayList<String> compHeadertags, int Choice,int startEelement,String SplitValue) {
		// TODO Auto-generated method stub

		ArrayList<String> XMLTagVal= new ArrayList<String>();
		try {


			for(int i=startEelement;i<compHeadertags.size();i++)
			{

				XMLTagVal.add(compHeadertags.get(i).split("[//"+SplitValue+"]")[Choice] );
			}							

			return XMLTagVal;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	private static HashMap<String, ArrayList<String>> Getmapvalue(ArrayList<String> InputdataXLS, HashMap<String, ArrayList<String>> xmllistMAP) {
		// TODO Auto-generated method stub
		String tempo = null;
		ArrayList <String> temp= new ArrayList<String>();
		for ( int InpTag=0;InpTag<InputdataXLS.size();InpTag++)
		{
			tempo = InputdataXLS.get(InpTag);
			temp.add(tempo);
		}

		xmllistMAP.put(temp.get(0).toString(),temp);
		return (xmllistMAP);

	}
	private static void Writereport(HashMap<String, ArrayList<String>> output_rep, ArrayList<String> header, String fILENAME,int maxRecNunber) throws IOException {
		// TODO Auto-generated method stub
		String d_path= fILENAME;
		int rowCount = 0;
		int columnCount = 0;
		try {


			XSSFWorkbook wb= new XSSFWorkbook();
			Row row;
			Cell cell;
			Sheet sheet1=wb.createSheet("Report");
			row= sheet1.createRow(rowCount);
			CellStyle style = wb.createCellStyle();
			CellStyle wrapText = wb.createCellStyle();
			wrapText.setWrapText(true);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setWrapText(true);
			// Creating header tags
			String Header_v1="";
			String Header_v2="";
			for ( int i=0;i<header.size();i++)
			{	
				if (header.get(i).split("[//,]").length >1)
				{
					Header_v1= header.get(i).split("[//,]")[0].split("[//+]")[0];
					Header_v2= header.get(i).split("[//,]")[1].split("[//+]")[0];
				}
				else 
				{
					if (header.get(i).split("[//,]").length ==1)
					{
						Header_v1= header.get(i);
						Header_v2= "";
					}
					else
					{
						Header_v2="";	
						Header_v1=Header_v2;

					}
				}
				cell= row.createCell(columnCount++);
				//cell.setCellValue(header.get(i));
				cell.setCellValue(Header_v1 + "," + Header_v2);
			}

			// Writing the data
			columnCount=0;
			List<String> sortedKeys=new ArrayList<String>(output_rep.keySet());
			Collections.sort(sortedKeys);
			for (String repCont:sortedKeys)
			{
				if (rowCount>=(int) maxRecNunber)
				{
					break;
				}
				columnCount=0;					
				row= sheet1.createRow(++rowCount);
				for (int i=0;i<output_rep.get(repCont).size();i++)
				{
					cell= row.createCell(columnCount++);
					if (output_rep.get(repCont).get(i).contains("Mismatch"))
					{
						cell.setCellValue(output_rep.get(repCont).get(i));
						cell.setCellStyle(style);
					}
					else
					{
						cell.setCellValue(output_rep.get(repCont).get(i));
						cell.setCellStyle(wrapText);
					}

				}
			}
			sheet1.autoSizeColumn(rowCount-1);
			FileOutputStream  outFile =new FileOutputStream(d_path);

			wb.write(outFile);
			outFile.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	public static int getcolumn(String Colname)
	{
		String newstr = Colname.replaceAll("[^A-Za-z]+", "");
		int colIdx = CellReference.convertColStringToIndex(newstr);

		return colIdx;
	}

}
