package GenericFunctions;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import Reporting.Report;
import TestScriptRunner.Runner;
import atafsecurity.AES;


public class DatabaseUtils {

	private static DatabaseUtils instance;
	private static  Connection connection;
	public ResultSet resultset = null;
	public Statement statement = null;
	static Report objReport = new Report();

	private DatabaseUtils(String uname,String pwd) throws Exception {
		Driver driver = new com.ibm.db2.jcc.DB2Driver();
		DriverManager.registerDriver(driver);
		String url = Runner.properties.getProperty("connectionString");
		connection = DriverManager.getConnection(url, uname.trim(), pwd.trim());                                           
		connection.setAutoCommit(true);
		System.out.println("Successfully Connected to DB2...");
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
	}

	public Connection getConnection(){
		return connection;
	}

	public static DatabaseUtils getInstance(String userName, String password){
		try{

			if (instance == null){
				instance = new DatabaseUtils(userName, password);
			}else if (instance.getConnection().isClosed()){
				instance = new DatabaseUtils(userName, password);

			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return instance;
	}

	public static DatabaseUtils getInstance(){
		if (instance == null ){
			return null;
		} else {
			return instance;
		}
	}

	public String getDB2ResultsString(String Query){
		String queryOutput = null;
		try{
			statement = instance.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs4=statement.executeQuery(Query);
			while(rs4.next()){
				queryOutput=rs4.getString(1);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return queryOutput;
	}

	public ArrayList<ArrayList<String>> getDB2ResultsArray(String Query){
		ArrayList<ArrayList<String>> rsData=new ArrayList<ArrayList<String>>();
		try{
			statement = instance.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=statement.executeQuery(Query);

			ResultSetMetaData metaData=rs.getMetaData();
			int columnCount=metaData.getColumnCount();
			System.out.println("Number of columns in result set is "+columnCount);

			if(rs!=null){
				while(rs.next()){
					ArrayList<String> colData=new ArrayList<String>();
					for(int colIndex=1;colIndex<columnCount+1;colIndex++){
						Object value=rs.getObject(colIndex);
						if(value!=null){
							String data=value.toString().replaceAll("\\s{2,}"," ").trim();
							if(colIndex==4){
								String[] name=value.toString().replaceAll("\\s{2,}"," ").trim().split("\\s");
								data=name[0]+" "+name[1];
							}
							colData.add(data);
						}
					}
					rsData.add(colData);
					rs.next();
				}
			}

		}catch (Exception e){
			System.out.println("Query not executed properly");
			e.printStackTrace();
		}
		return rsData;
	}

	public static String[] executeHiveQuery(Session session, String query) {
		// TODO Auto-generated method stub

		String status = "";
		String data = "";
		String[] results = {data,status};
		try {
			// String command = "beeline --showHeader=false --outputformat=csv2 -u \"jdbc:hive2://xhadhivem1p.aetna.com:10000/$HIVE_DB;principal=hive/xhadhivem1p.aetna.com@AETNSD.AETH.AETNA.COM\" -e \"" + query + ";\"";
			String command = "beeline --showHeader=false --outputformat=csv2 -u \'jdbc:hive2://xhadhbasem1p.aetna.com:2181,xhadhivem1p.aetna.com:2181,xhadnmgrm1p.aetna.com:2181,xhadnnm1p.aetna.com:2181,xhadnnm2p.aetna.com:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2-lb;user=.;password=.' --showHeader=false -e \""+query+";";
			//	String command1="beeline --showHeader=false --outputformat=csv2 -u \'jdbc:hive2://xhadhbasem1p.aetna.com:2181,xhadhivem1p.aetna.com:2181,xhadnmgrm1p.aetna.com:2181,xhadnnm1p.aetna.com:2181,xhadnnm2p.aetna.com:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2-lb;useServerPrepStmts=false&rewriteBatchedStatements=true;user=.;password=.' --showHeader=false -e \""+query+";";
			Channel channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);

			InputStream in=channel.getInputStream();

			channel.connect();

			byte[] tmp=new byte[1048576];

			while(true) {

				while(in.available()>0) {

					int i=in.read(tmp, 0, 1048576);

					if(i<0) break;
					System.out.print(new String(tmp, 0, i));
					data = data + new String(tmp, 0, i);
				}

				if(channel.isClosed()) {
					System.out.println("exit-status: "+channel.getExitStatus());

					if (channel.getExitStatus()!=0) {
						data = "Incorrect Query";
						status = "Fail";
						objReport.setValidationMessageInReport("FAIL", "Incorrect Query: "+query);
					} else {
						status = "Pass";
						objReport.setValidationMessageInReport("PASS", "Successfully Executed the Query: "+query);
					}
					break;
				}
				try {Thread.sleep(1000);}catch(Exception ee){}
			}

			System.out.println("Results: "+data);
			channel.disconnect();
			System.out.println("DONE");

		} catch (Exception e) {
			objReport.setValidationMessageInReport("FAIL", "Exception in Executing the query: "+e.getMessage());
			e.printStackTrace();
		}
		results[0]=data;
		results[1]=status;
		return results;
	}

	public static Session connectHiveTable(Workbook wb) {
		// TODO Auto-generated method stub
		Session session = null;
		try {
			Sheet credentialSheet = wb.getSheetAt(0);
			String username = credentialSheet.getRow(0).getCell(1).toString().trim();
			String password = AES.decrypt(credentialSheet.getRow(1).getCell(1).toString().trim());
			String hostname = credentialSheet.getRow(2).getCell(1).toString().trim();

			java.util.Properties config = new java.util.Properties(); 
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session=jsch.getSession(username , hostname , 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			objReport.setValidationMessageInReport("PASS", "Connected to Hive Table.");
		} catch (Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Failed to connect Hive Table. Exception: "+e.getMessage());
		}
		return session;
	}

	public Connection connectMSSql(String userName, String password, String hostName, String dbName) {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://"+hostName+":1433"+";databaseName="+dbName;
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("Connected");
			objReport.setValidationMessageInReport("PASS", "Connected to SQL Server.");
		} catch(Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Failed to connect SQL Server. Exception: "+e.getMessage());
		}
		return connection;
	}

	public String[] executeSqlQuery(Connection connection, String query) {
		String[] result = new String[20];
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			if(rs != null){
				while (rs.next()){
					for(int i = 1; i <result.length ;i++) {
						for(int j = 0; j <result.length;j++) {
							result[j] = rs.getString(i);
							System.out.println(result[j]);
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//sqljdbc_auth.dll is required in java library path to execute this method
	public Connection connectMSSqlWindowsAuth(String hostName, String dbName) {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://"+hostName+":1433"+";databaseName="+dbName+";integratedSecurity=true;";
			connection = DriverManager.getConnection(url);
			System.out.println("Connected");
			objReport.setValidationMessageInReport("PASS", "Connected to SQL Server.");
		} catch(Exception e) {
			e.printStackTrace();
			objReport.setValidationMessageInReport("FAIL", "Failed to connect SQL Server. Exception: "+e.getMessage());
		}
		return connection;
	}
}
