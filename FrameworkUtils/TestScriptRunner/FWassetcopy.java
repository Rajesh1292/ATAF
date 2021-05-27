package TestScriptRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FWassetcopy {
	public static String baseBDDrep="";
	public static String baserep="";
	public static String chromeDriver="";
	public static String geckodriver="";
	public static String IEdriver="";
	public static String mswebdriver="";
	public static String suitesummrep="";
	public static String outputdirforres=Runner.resdir+"\\Framework\\";
	public FWassetcopy(){
		baseBDDrep="BaseBDDReportFormat.html";
		baserep="BaseReportFormat.html";
		chromeDriver="chromedriver.exe";
		geckodriver="geckodriver.exe";
		IEdriver="IEDriverServer.exe";
		mswebdriver="MicrosoftWebDriver.exe";
		suitesummrep="Test_exec_summary_Template.html";
	}
	
	public static Boolean extract(String jarFilePath){
		String extratcedFile="";
		try {
            // Read the file we're looking for
            InputStream fileStream = FWassetcopy.class.getClassLoader().getResourceAsStream("Resources/"+jarFilePath);

            // Was the resource found?
            if(fileStream == null)
                return false;

            // Grab the file name
            String[] chopped = jarFilePath.split("\\/");
            String fileName = chopped[chopped.length-1];

            // Create our temp file (first param is just random bits)
            extratcedFile = outputdirforres+fileName;
            if(new File(extratcedFile).exists())
            	return true;
            // Create an output stream to barf to the temp file
            OutputStream out = new FileOutputStream(extratcedFile);

            // Write the file to the desired directory
            byte[] buffer = new byte[1024];
            int len = fileStream.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = fileStream.read(buffer);
            }
            // Close the streams
            fileStream.close();
            out.close();
            return true;

        } catch (IOException e) {
            if(extratcedFile.contains("driver") || extratcedFile.contains("Driver"))
            	return true;
        	return false;
        }
    }
	
	public Boolean addmissingfiles(){
		Boolean overallextractionflag=true;
		String filetocheck="";
		//check and add bddreptemp
		filetocheck=outputdirforres+baseBDDrep;
		File tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(baseBDDrep);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add basereptemp
		filetocheck=outputdirforres+baserep;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(baserep);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add chromeDriver
		filetocheck=outputdirforres+chromeDriver;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(chromeDriver);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add geckodriver
		filetocheck=outputdirforres+geckodriver;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(geckodriver);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add IEdriver
		filetocheck=outputdirforres+IEdriver;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(IEdriver);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add mswebdriver
		filetocheck=outputdirforres+mswebdriver;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(mswebdriver);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		//check and add suitesummrep
		filetocheck=outputdirforres+suitesummrep;
		tmpfil=new File(filetocheck);
		if(!tmpfil.exists()){
			Boolean fileextracted=extract(suitesummrep);
			overallextractionflag=overallextractionflag && fileextracted;
		}
		return overallextractionflag;
	}
}
