/*package GenericFunctions;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Reporting.Report;


public class PDFImageValidation extends PDFStreamEngine
{

	
	
	public static int imgNumInPdfPagToBeSaved = 1;
	public static int imgNum = 1;
	public static String toBeSavedImgLocPath = "";
	public static String toBeSavedImgName = "";
	public static String toBeSavedImgType = "";
	
	public static void extractImageFromPDFDoc(String pdfDocPath, String pdfPageNum, String imgPositionInPage,String imgLocPath,String imgName, String imgType ) throws Exception 
    {
	PDDocument PDFDoc = null;
		try
		{
		PDFDoc = PDDocument.load( new File(pdfDocPath) );
			int pdfPagNum=Integer.parseInt(pdfPageNum);
			imgNumInPdfPagToBeSaved=Integer.parseInt(imgPositionInPage);
			toBeSavedImgLocPath = imgLocPath;
			toBeSavedImgName = imgName;
			toBeSavedImgType = imgType;

			PDFImageValidation pdfImgVald = new PDFImageValidation();

			int pageNum = 0;
			for( PDPage page : PDFDoc.getPages() )
			{
				pageNum++;

				imgNum=1;
				//System.out.println( "Processing page: " + pageNum );
				if (pageNum==pdfPagNum)
				{
					pdfImgVald.processPage(page);
					break;
				}
			}
		}
		
		catch (Exception e)
		{
			Report objReport=new Report();
			objReport.setValidationMessageInReport("FAIL", "Method extractImageFromPDFDoc : Failed due to exception "+e.toString());
		}
		
		finally
		{
			if( PDFDoc != null )
			{
				try{
					PDFDoc.close();
				}
				catch (Exception e)
				{
                    e.printStackTrace();
				}
			}
		}

    }

	
	public void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject =  getResources().getXObject( objectName );
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
 
                // same image to local
                BufferedImage bImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
                bImage = image.getImage();
                
                if(imgNum==imgNumInPdfPagToBeSaved)
                {  
                ImageIO.write(bImage,toBeSavedImgType,new File(toBeSavedImgLocPath+"\\"+toBeSavedImgName+"."+toBeSavedImgType));
                }
               
                System.out.println("Image saved.");
                imgNum++;               
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }

	public static void compareImage(String Image1Name, String Image2Name) {

		Report objReport=new Report();
	    int percentage = 0;
	    try {
	    	// take buffer data from both image files //
	    	
	    	File Image1= new File (Image1Name);
	    	File Image2= new File (Image2Name);
	    	BufferedImage biA = ImageIO.read(Image1);
	    	DataBuffer dbA = biA.getData().getDataBuffer();
	    	int sizeA = dbA.getSize();
	    	BufferedImage biB = ImageIO.read(Image2);
	    	DataBuffer dbB = biB.getData().getDataBuffer();
	    	int sizeB = dbB.getSize();
	    	int count = 0;
	    	// compare data-buffer objects //
	    	if (sizeA == sizeB) 
	    	{
	    		for (int i = 0; i < sizeA; i++) 
	    		{
	    			if (dbA.getElem(i) == dbB.getElem(i))
	    			{
	    				count = count + 1;
	    			}
	    		}
	    		percentage = (count * 100) / sizeA;
	    		//Comparison
	    		if (percentage==100)
	    		{
	    			System.out.println("Both the images are same");
	    			objReport.setValidationMessageInReport("PASS", "Images Comparison Verification : Both Images '"+Image1+"' and '"+Image2+"' are same");
	    		}
	    		else
	    		{
	    			System.out.println("Both the images are not same");
	    			objReport.setValidationMessageInReport("FAIL", "Images Comparison Verification : Both Images '"+Image1+"' and '"+Image2+"' are not same");
	    		}
	    	} 
	    	
	    	else
	    	{
	    		System.out.println("Both the images are not of same size");
    			objReport.setValidationMessageInReport("FAIL", "Images Comparison Verification : Both Images '"+Image1+"' and '"+Image2+"' are not same");
	    	}

	    } catch (Exception e) 
	    {
	        System.out.println("Failed to compare image files ...");
			objReport.setValidationMessageInReport("FAIL", "Method compareImage : Failed due to exception "+e.toString());

	    }
	    
	}

	
}
*/