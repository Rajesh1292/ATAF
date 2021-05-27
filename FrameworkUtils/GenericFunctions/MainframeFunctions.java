package GenericFunctions;

//import Test.RumbaConnect.ehlapi32;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

import Reporting.Report;

public class MainframeFunctions {
	Report objReport=new Report();
	
	public interface ehlapi32 extends Library {
		ehlapi32 ehlapi32 = (ehlapi32) Native.loadLibrary((Platform.isWindows() ? "ehlapi32" : "ehlapi32"), ehlapi32.class);
		public int WD_ConnectPS(HWND hInstance , String ShortName);
		public int WD_SendKey(HWND hInstance, String KeyData);
		public int WD_SetCursor(HWND hInstance, int position );
		public int WD_DisconnectPS(HWND hInstance);
		public int WD_CopyStringToField(HWND hInstance, int position, String value);
		public int WD_CopyFieldToString(HWND hInstance, int position, byte[] screenData, int strLen);
		public int WD_CopyPSToString (HWND hInstance, int position, byte[] screenData, int strLen);
		public int WD_QueryCursorLocation (HWND hInstance, byte[] location);
	}

	public static final ehlapi32 ehlapi32 = (ehlapi32) Native.loadLibrary("ehlapi32", ehlapi32.class);
	WinDef.HWND hwnd = null;

	/**  
	 * @Name login_tso
	 * @param userID - UserID of user to login in mainframe
	 * @param passwd - Password of user to login in mainframe
	 * @param region - The region name in which user wants to login
	 * @param path - Local path of Rumba application
	 * @param shortName - 
	 * @description This method logs in to Rumba application                                               
	 */

	public void login_tso(String userID, String passwd, String region, String path, String shortName) {
		int status = 0;
		String chkString = null;
		int strLen;

		try {
			Runtime.getRuntime().exec(new String[] {"cmd.exe", "/C", path});
			Thread.sleep(8000);
			do {
			hwnd = User32.INSTANCE.FindWindow(null, shortName+"-WINNT - Micro Focus Rumba");
			status = ehlapi32.WD_ConnectPS(hwnd, shortName);
			System.out.println("Connection status " +status);
			System.out.println(hwnd);
			} while (hwnd==null || status!=0);

			status = ehlapi32.WD_SendKey(hwnd , "log "+region+",,"+userID);
			System.out.println("Sendkey status " +status);
			press_Enter();
			Thread.sleep(500);

			status = ehlapi32.WD_CopyStringToField(hwnd, 580, passwd);
			strLen = 13;

			//Wait for login process to complete and Start screen to appear
			do {
				press_Enter();
				Thread.sleep(2000);
				chkString=read_fromScreen(1, 32, strLen);
//				System.out.println("Return string is "+chkString);

			} while (!(chkString.equalsIgnoreCase("PRIMARY PANEL")));
			set_sessionparms();
			Thread.sleep(2000);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception occured in login_tso keyword : "+e.getMessage());
		}
	}

	/**  
	 * @Name login_CICS
	 * @param userID - UserID of user to login in mainframe
	 * @param passwd - Password of user to login in mainframe
	 * @param path - Local path of Rumba application
	 * @param shortName - 
	 * @description                                                
	 */

	public void login_CICS(String userID, String passwd, String path, String shortName) {
		int status = 0;
		int strLen;
		String chkString = null;
		try {
			Runtime.getRuntime().exec(new String[] {"cmd.exe", "/C", path});
			Thread.sleep(8000);
			hwnd = User32.INSTANCE.FindWindow(null, shortName+"-WINNT - Micro Focus Rumba");
			//System.out.println(hwnd);

			status = ehlapi32.WD_ConnectPS(hwnd, shortName);
			//System.out.println("Connection Status: " + status);

			ehlapi32.WD_SendKey(hwnd, "C@E");
			Thread.sleep(500);

			status = ehlapi32.WD_CopyStringToField(hwnd, 1010, userID);
			press_Tab();
			Thread.sleep(500);

			status = ehlapi32.WD_CopyStringToField(hwnd, 1090, passwd);
			press_Enter();
			Thread.sleep(500);

			strLen = 8;
			chkString = read_fromScreen(9, 11, strLen);
			//System.out.println("Value "+chkString);

			if (chkString.equalsIgnoreCase("Password")) {
				//System.out.println("inside if");
				press_Enter();
			}
			Thread.sleep(200);
			return;
		} 
		catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception occured in login_CICS keyword : "+e.getMessage());
		}
	}

	public int getCursor(HWND hwnd) {
		int pos =0;
		byte[] location = new byte[20];
		int status = ehlapi32.WD_QueryCursorLocation(hwnd, location);
//		System.out.println("Status of cursor"+status);        

		int lsb = location[0] & 0xFF; // "least significant byte"
		int msb = location[1] & 0xFF; // "most significant byte"
		pos = (msb << 8) + lsb;

		return(pos);
	}

	public int getrow( ){

		int rowval;

		int pos = 0;
		pos = getCursor(hwnd);

		rowval= (Math.round(pos/80))+1;
		return(rowval);
	}

	public int getcol() {

		int colval;

		int pos = 0;
		pos = getCursor(hwnd);

		colval= pos - Math.round(pos/80)*80;
		return(colval);
	}


	/**  
	 * @Name set_cursor
	 * @param row - Row no of mainframe screen on which the cursor to be set
	 * @param col - Column no of mainframe screen on which the cursor to be set
	 * @description This method sets the cursor on required position on mainframe screen                                               
	 */

	public void set_cursor(int row, int col) {

		int pos;
		int status;
		pos = (row-1)*80 + col;

		status = ehlapi32.WD_SetCursor(hwnd, pos);
		//System.out.println("Status is "+status);

		return;
	}

	/**  
	 * @Name write_onScreen
	 * @param row - Row no of mainframe screen on which the data to be written
	 * @param col - Column no of mainframe screen on which the data to be written
	 * @param strVal - The data to be written on mainframe screen
	 * @description This method writes data on mainframe screen                                               
	 */

	public void write_onScreen(int row, int col, String strVal) {

		int pos;
		int status;

		pos = (row-1)*80 + col;
		//System.out.println(pos);
		status = ehlapi32.WD_CopyStringToField(hwnd, pos, strVal);

		return;
	}

	/*public int sample() {
		WORD lpwLocation = new WORD(); 
		WORD wPSP = new WORD();
		LPSTR szCode = new LPSTR();

		Pointer p = "T^";
		szCode.set
		DWORD status = ehlapi32.WD_FindFieldPosition(hwnd, lpwLocation, wPSP, szCode);
		if (status.intValue()==0){
			return wPSP.intValue();
		}else{
			return 0;
		}
	}*/

	/**  
	 * @Name read_fromScreen
	 * @param row - Row no of mainframe screen from which the data to be read
	 * @param col - Column no of mainframe screen from which the data to be read
	 * @param strLen - Length of the screen from which the data to be read
	 * @return String
	 * @description This method reads data from mainframe screen                                               
	 */

	public String read_fromScreen(int row, int col, int strLen) {

		int pos;
		int status;
		String retString= "";
		byte[] screenData = new byte[320];

		pos = (row-1)*80 + col;
		//System.out.println(pos);

		status = ehlapi32.WD_CopyPSToString(hwnd, pos, screenData, strLen);
		//System.out.println("Reading status is "+status);

		for (int i=0; i<strLen; i++) {
			retString = retString+(char)screenData[i];
		}

		return (retString);
	}

	/**  
	 * @Name send_keys
	 * @param strVal - String name of the key user want to press
	 * @description This method pass the key user want to press on mainframe screen                                               
	 */

	public void send_keys(String strVal) {
		ehlapi32.WD_SendKey(hwnd, strVal);
		return;
	}


	//***********************CTRL+E, ENTER, TAB, F3, F7, F8, F9, F10, F11, F12************************//*

	public void press_CTRL_E(int row, int col)
	{
		int status;
		int pos;

		pos = (row-1)*80 + col;

		set_cursor(row, col);
		ehlapi32.WD_SendKey(hwnd, "@F");

		return;

	}

	public void press_CTRL_R()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@A@Q");

		return;

	}

	public void backtab()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@B");

		return;

	}


	public void press_Enter()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@E");

		return;

	}


	public void press_Tab()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@T");


		return;

	}


	public void press_F3()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@3");

		return;

	}


	public void press_F7()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@7");

		return;

	}

	public void press_F8()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@8");

		return;

	}

	public void press_F9()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@9");

		return;

	}


	public void press_F10()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@a");

		return;

	}


	public void press_F11()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@b");

		return;

	}


	public void press_F12()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd, "@c");

		return;
	}

	public void press_F5()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd , "@5");

		return;
	}

	public void Cursor_Down()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd , "@V");

		return;
	}

	public void Cursor_up()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd , "@U");

		return;
	}

	public void Cursor_left()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd , "@L");

		return;
	}

	public void Cursor_Right()
	{
		int status;

		status= ehlapi32.WD_SendKey(hwnd , "@Z");

		return;
	}


	/**  
	 * @Name set_sessionparms
	 * @description                                                
	 */

	public void set_sessionparms() {
		String chkString = null;
		String str = null;
		byte[] screenData = new byte[320];
		int strLen=6;

		try {

			//    		chkString = read_fromScreen(24, 4, strLen);
			//          	
			//       	  	Thread.sleep(200);
			//    		
			//        	 if (chkString.equalsIgnoreCase("*AETNATS"))
			//        	 {
			write_onScreen (2, 14, "swapbar off");
			press_Enter();
			// }
			Thread.sleep(200);

			//        	 strLen = 2;
			//        	 chkString = read_fromScreen(23, 3, strLen);
			//        	 
			//        	 if (!(chkString.equalsIgnoreCase("*F1")))
			//        	 {
			Thread.sleep(2000);
			write_onScreen (2, 14, "pfshow off");
			press_Enter();
			Thread.sleep(2000);
			press_CTRL_E(2, 14);
			// }
			Thread.sleep(2000);
			send_keys("3.4@E");
			Thread.sleep(2000);

			set_cursor(1, 4);    //Open Menu to setup Command Line
			Thread.sleep(2000);
			press_Enter();
			Thread.sleep(2000);

			send_keys("1@E");
			Thread.sleep(1000);

			strLen = 1;
			chkString = read_fromScreen(7, 4, strLen);

			if (chkString.equalsIgnoreCase("/")) {
				Thread.sleep(2000);
				press_F3();
			} else {
				str= "/";
				Thread.sleep(100);
				write_onScreen (8, 4, str);
				Thread.sleep(100);
				press_F3();
			}

			Thread.sleep(100);
			press_F3();
			//System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception occured in set_sessionparms keyword : "+e.getMessage());
		}
	}

	/**  
	 * @Name logout_TSO
	 * @description This method logs out from Rumba application                                               
	 */

	public void logout_TSO() throws InterruptedException {
		int strLen = 19;
		byte[] screenData = new byte[20];
		String chkString =null;

		try{
			do {
				press_F3();
				Thread.sleep(500);
				chkString = read_fromScreen(1, 23, strLen);
//				System.out.println(chkString);

			} while(!(chkString.equalsIgnoreCase("Specify Disposition")));

			//press_F3();
			send_keys("2");
			Thread.sleep(300);
			send_keys("2");
			Thread.sleep(300);
			press_Enter();
			Thread.sleep(300);
			send_keys("bye@E");
			press_Enter();
			Thread.sleep(300);

			int status = ehlapi32.WD_DisconnectPS(hwnd);
			Runtime.getRuntime().exec(new String[] {"cmd.exe", "/C", "TASKKILL /IM RumbaPage.exe"});
			System.out.println("Disconnected");

		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception occured in logout_TSO keyword : "+e.getMessage());
		}
	}

	/**  
	 * @Name open_STARTOOL
	 * @param filename - 
	 * @param pds - 
	 * @param copybook - 
	 * @description This method opens a file in startool using a copybook                                               
	 */

	public void open_STARTOOL(String filename, String pds, String copybook) {

		String chkString = null;
		byte[] screenData = new byte[20];
		int strLen=13;

		try {
			chkString = read_fromScreen(1, 32, strLen);
			Thread.sleep(1000);

			if (!(chkString.equalsIgnoreCase("PRIMARY PANEL"))) {
				write_onScreen(24, 14, "start");
				Thread.sleep(200);
				press_Enter();
			}	

			Thread.sleep(300);
			send_keys("STARTOOL");
			press_Enter();
			Thread.sleep(500);

			send_keys("4");
			press_Tab();
			Thread.sleep(100);

			press_CTRL_E(4, 23);
			Thread.sleep(100);

			send_keys("'"+filename);
			strLen = 24;

			do {
				press_Enter();
				chkString = read_fromScreen(1, 30, strLen);
				Thread.sleep(1000);

			} while(!(chkString.equalsIgnoreCase("StarTool Pedit selection")));

			Thread.sleep(100);
			write_onScreen(16, 25, "YES");
			press_Enter();
			Thread.sleep(200);
			write_onScreen(6, 28, "COPYBOOK");
			Thread.sleep(200);
			//press_Tab();
			write_onScreen(8, 28, "'"+pds);
			//send_keys("'"+pds);
			Thread.sleep(200);
			//press_Tab();

			write_onScreen(9, 28, copybook);
			Thread.sleep(200);
			write_onScreen(10, 28, "MVC");
			Thread.sleep(200);
			write_onScreen(13, 28, "EITHER");
			Thread.sleep(200);
			write_onScreen(15, 28, "1");
			Thread.sleep(200);
			write_onScreen(20, 28, "PROFILE");
			Thread.sleep(200);
			press_Enter();

		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Exception occured in open_STARTOOL keyword : "+e.getMessage());
		}
	}
}