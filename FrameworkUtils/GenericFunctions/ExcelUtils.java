package GenericFunctions;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class ExcelUtils {
	
	/**
	 * @Name findCol   
	 * @param String sheet- Contains the worksheet name 
	 *        String colName- contains the Column name 
	 * @description - Returns the column index  of the Column(specified by colName) in the sheet(specified by sheet)
	 */

	public static int findCol(Sheet sheet, String colName) 
	{
		Row row = null;		 
		int colCount=0;

		row=sheet.getRow(0);
		if(!(row== null))
		{
			colCount=row.getLastCellNum();
		}
		else
		{				  
			colCount=0;		
		}
		for(int j=0;j<colCount;j++)
		{
			if(!( row.getCell(j)==null)){
				if(row.getCell(j).toString().trim().equalsIgnoreCase(colName)){
					return j;
				}
			}
		}
		return -1;
	}

}
