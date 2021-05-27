package GenericFunctions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;


import org.xml.sax.helpers.DefaultHandler;

import Reporting.Report;
/**
 * Description   : Functional Test Script
 * @author n628204
 */
public class MyHandler extends DefaultHandler
{
	/**
	 * Script Name   : <b>MyHandler</b>
	 * Generated     : <b>Apr 7, 2017 10:33:00 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 6.1  Build 7601 (S)
	 * 
	 * @since  2017/04/07
	 * @author n077138
	 */

	// TODO Insert code here
	Report objReport= new Report();
	public Map <String, ArrayList<String>> xmllistMAP = null;
	public ArrayList<String> xmllist = null;
	public  ArrayList <String> InputdataXLS= null;
	public  ArrayList <String> ParenTagName= null;
	public  ArrayList <String> ParenTagName_Xls= null;
	double count=0;
	String Xml_block_Start= null;
	//public index

	//getter method for employee list
	public  Map <String, ArrayList<String>>getEmpList() {
		return xmllistMAP;
	}

	/*boolean bAge = false;
	    boolean bName = false;
	    boolean bGender = false;
	    boolean bRole = false;
	 */

	boolean tagnamefnd= false;
	boolean Proxyidfnd= false;
	boolean parenTagname= false;
	String parenTagNM="";
	String Tagname="";

	// String colNum = "p:individualIdToken";


	public String getName(String colNum){
		return colNum;
	}

	
	public void startElement(String uri, String localName, String qName, Attributes attributes)
	{
		tagnamefnd= false;
		// check for preferredind is set to Y or not
		try
		{
			if (qName.equalsIgnoreCase("p:IIDproxyValue")){

				if (xmllist.get(0).contains("0000000001125693"))
				{
					System.out.println(" need to figure the issue");
				}
				if (attributes.getValue(0)!=null)
				{
					//System.out.println("preferredind tag fnd:: "+ attributes.getValue(0));
					if (attributes.getValue(0).trim().equalsIgnoreCase("y")){
						tagnamefnd=true;
						if (parenTagname)
						{
							Tagname=qName + "+" + parenTagNM;
						}
						else {
							Tagname=qName;
						}
						xmllist.add(InputdataXLS.get(3).toString()+","+attributes.getValue(0));  
					}

				}



				/*if (qName.equalsIgnoreCase("ipp:communicationPreference"))
		  {
			  System.out.println(" cOMM works");
		  }*/
				if(parenTagname==false && (!(qName.equalsIgnoreCase("p:IIDproxyValue"))))
				{
					for (Object P_tags:ParenTagName)
					{
						if (qName.equalsIgnoreCase(P_tags.toString()) && parenTagname==false)
						{
							parenTagname= true;
							parenTagNM=qName;
							break;
						}
					}
				}

				if (!(qName.equalsIgnoreCase("p:IIDproxyValue")))
				{
					for (Object InpXls:InputdataXLS)
					{
						if (parenTagname)
						{

							if (qName.equalsIgnoreCase((InpXls.toString()).split("[//+]")[0].trim()) )
							{
								if (xmllist == null){
									xmllist = new ArrayList<>();
								}
								tagnamefnd=true;
								Tagname=qName + "+" + parenTagNM;
								break;
								//xmllist.add(InpXls.toString());

							}

						}
						else{
							if (qName.equalsIgnoreCase((InpXls.toString()).trim()) )
							{
								if (xmllist == null){
									xmllist = new ArrayList<>();
								}
								tagnamefnd=true;
								Tagname=qName;
								break;
								//xmllist.add(InpXls.toString());

							}
						}
					}
				}

			}}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL","Method startElement :Failed due to error : "+e.getMessage()); 			

		}
	}

	public void endElement(String uri, String localName, String qName)  
	{
		try
		{
			Boolean mapAlreadyExist= false;
			if((qName.equalsIgnoreCase(parenTagNM.toString()) && parenTagname==true))
			{
				parenTagname= false;
			}
			/*for (Object P_tags:ParenTagName)
	  		  {
	  			  if (qName.equalsIgnoreCase(parenTagNM.toString()) && parenTagname==true)
	  			  {
	  				  parenTagname= false;
	  				  break;
	  			  }
	  		  }*/

			if (/* tagnamefnd && */ xmllist!=null &&  (qName.equalsIgnoreCase(Xml_block_Start)))
				//qName.equalsIgnoreCase(InputdataXLS.get(InputdataXLS.size()-1).toString().split("[//+]")[0].trim())) {
			{
				//qName.equalsIgnoreCase(InputdataXLS.get(InputdataXLS.size()-1).toString().split("[//+]")[0].trim())) {
				ArrayList <String> temp= new ArrayList<String>();
				String tempo = null;
				/*if (xmllistMAP!= null){
				for (String keys:xmllistMAP.keySet()){
					if ( keys.equalsIgnoreCase(xmllist.get(0).split("[//,]")[1].trim()))
					{
						mapAlreadyExist= true;
						System.out.println(" Map  already exists for key set :"+ keys);
						break;
					}
				}
				}*/
				if (! (mapAlreadyExist) )
				{

					for ( int InpTag=0;InpTag<InputdataXLS.size();InpTag++)
					{
						tempo=" ";
						for (int k=0; k < xmllist.size(); k++)
						{
							if (InputdataXLS.get(InpTag).toString().split("[//+]")[0].trim().equalsIgnoreCase((xmllist.get(k).split("[//,]")[0].trim()).split("[//+]")[0].trim()))
								if ((xmllist.get(k).split("[//,]")[0].trim().split("[//+]").length)>1)
								{
									/*if(ParenTagName_Xls.get(InpTag).equalsIgnoreCase("ipp:communicationPreference"))
							{
								System.out.println(" COMM works");
							}*/
									if (ParenTagName_Xls.get(InpTag).equalsIgnoreCase(xmllist.get(k).split("[//,]")[0].trim().split("[//+]")[1].trim()))
									{

										tempo = xmllist.get(k).split("[//,]")[1].trim();
										break;

									}
								}
								else 

								{
									if (xmllist.get(0).contains("0000000001125693"))
									{
										System.out.println(" need to figure the issues");
									}
									// Logic for capturing the correct IID proxy id and pref ind incase of multiple values of V2
									if(xmllist.get(k).split("[//,]")[0].trim().equalsIgnoreCase("CON:idValue") && xmllist.get(k+1).split("[//,]")[0].trim().equalsIgnoreCase("CON:preferredInd"))
									{
										if(xmllist.get(k).split("[//,]")[1].trim().equalsIgnoreCase(temp.get(1).toString()))
										{
											tempo = xmllist.get(k).split("[//,]")[1].trim();
											temp.add(tempo);
											// increasing the iteration by  1 to capture the next value
											tempo = xmllist.get(++k).split("[//,]")[1].trim();
											InpTag++;
											break;
										}
									}
									else
									{
										if (!(xmllist.get(k).split("[//,]")[0].trim().equalsIgnoreCase("CON:preferredInd")))
										{
											tempo = xmllist.get(k).split("[//,]")[1].trim();
											break;
										}
									}
								}
						}
						if (tempo.trim().equalsIgnoreCase("".trim()))
						{
							//System.out.println("add space in arraylist");
						}
						temp.add(tempo);

					}
					if (xmllistMAP==null)
					{
						xmllistMAP=new HashMap <String, ArrayList<String>>();
					}
					if (xmllist.get(0).contains("0000000001125693"))
					{
						System.out.println(" need to figure the issues");
					}
					xmllistMAP.put(temp.get(0).toString(),temp);
					//System.out.println(++count);
				}
				xmllist.clear();
				Tagname="";
				parenTagNM="";
				parenTagname= false;
				//add Employee object to list
				//empList.add(emp);
			}
		}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL","Method endElement :Failed due to error : "+e.getMessage()); 			
		}
	}

	public void characters(char ch[], int start, int length) 
	{
		try
		{
			if (tagnamefnd) 
			{
				String indval=new String(ch, start, length);

				if (!(indval.contains("\n")|| indval.contains("\t")))
				{
					xmllist.add( Tagname+ "," + indval);
				}
			}
		}
		catch(Exception e)
		{
			objReport.setValidationMessageInReport("FAIL","Method characters :Failed due to error : "+e.getMessage()); 			

		}
	}
}


