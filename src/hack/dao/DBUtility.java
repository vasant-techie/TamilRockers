package hack.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hack.constants.Constants;
import hack.pojo.MasterPojo;
import hack.pojo.Transaction;
import hack.util.DateUtil;

public class DBUtility {


	public static void main(String[] args) throws IOException 
	{
		createConnection();
		insertCustomerDatatoDB();
		// insertTransactionDatatoDB();
		// insertRestaurants(5, "LaVals", "Berkeley");
		// selectRestaurants();
		//shutdown();
	}

	private static Connection createConnection() 
	{
		Connection conn = null;
		
		try 
		{
			Class.forName(Constants.DRIVER_CLASS_NAME).newInstance();
			// Get a connection
			conn = DriverManager.getConnection(Constants.DB_URL);
		} 
		catch (Exception except) 
		{
			except.printStackTrace();
		}
		
		return conn;
	}

	private static void closeConnection(Connection conn) 
	{
		try
		{
			if (conn != null) 
			{
				conn.close();
			}
		} 
		catch (SQLException sqlExcept) 
		{
			sqlExcept.printStackTrace();
		}
	}
	
	private static void closeStatement(Statement stmt) 
	{
		try
		{
			if (stmt != null) 
			{
				stmt.close();
			}
		} 
		catch (SQLException sqlExcept) 
		{
			sqlExcept.printStackTrace();
		}
	}

	// To fetch file and insert to db

	public static String insertTransactionDatatoDB() throws IOException 
	{

		String fileName = Constants.TXN_DATA_FILE_NAME;

		Transaction tc = new Transaction();
		List<String> items = new ArrayList<String>();
		File file = new File(fileName);

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;

		while ((line = bufferedReader.readLine()) != null) {

			stringBuffer.append(line);
			stringBuffer.append("\n");

			items = Arrays.asList(line.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));

			tc.setTransDate(DateUtil.parseDateInCustomFormat(items.get(0), Constants.TXN_FILE_DATE_FORMAT));
			tc.setCustId(Integer.valueOf(items.get(1)));
			tc.setAmount(Float.valueOf(items.get(2)));
			tc.setDebitrCredit(Integer.valueOf(items.get(3)));

			insertDatatoTranstoDB(tc.getTransDate(), tc.getCustId(), tc.getAmount(), tc.getDebitrCredit());

		}

		fileReader.close();

		return "SUCCESS";
	}

	// insert
	private static void insertDatatoTranstoDB(Date transDate, int custId, float amount, int debitorcredit) 
	{
		String tableName = Constants.TXN_TABLE_NAME;
		String sqlQry = null;
		
		Connection conn = null;
		Statement stmt = null;
		try {
			String formattedDate = DateUtil.formatDate(transDate, "MM/dd/yyyy");

			conn = createConnection();
			stmt = conn.createStatement();
			sqlQry = "insert into " + tableName + "(TransDate,Customer_ID,AMOUNT,CREDIT_DEBIT_FLAG)" + " values ('"
					+ formattedDate + "'," + custId + "," + amount + "," + debitorcredit + ")";
			System.out.println(sqlQry);
			stmt.execute(sqlQry);
		} 
		catch (SQLException sqlExcept) 
		{
			sqlExcept.printStackTrace();
		}
		finally
		{
			closeStatement(stmt);
			closeConnection(conn);
		}
	}

	// insert to cust db
	// To fetch file and insert to db

	public static String insertCustomerDatatoDB() throws IOException {

		String fileName = Constants.CUST_DATA_FILE_NAME;

		MasterPojo mp = new MasterPojo();
		List<String> items = new ArrayList<String>();
		File file = new File(fileName);

		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;

		while ((line = bufferedReader.readLine()) != null) {

			stringBuffer.append(line);
			stringBuffer.append("\n");

			items = Arrays.asList(line.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));

			mp.setCustId(Integer.valueOf(items.get(0)));
			mp.setFirstName(items.get(1));
			mp.setSalary(Float.valueOf(items.get(2)));

			insertDatatoCusttoDB(mp.getCustId(), mp.getFirstName(), mp.getSalary());

		}

		fileReader.close();

		return "SUCCESS";
	}

	private static void insertDatatoCusttoDB(int custId, String firstName, float salary) {
		String tableName = Constants.CUST_TABLE_NAME;
		String sqlQry = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = createConnection();
			stmt = conn.createStatement();
			sqlQry = "insert into " + tableName + "(Customer_ID,FIRSTNAME,SALARY)" + " values (" + custId + ",'"
					+ firstName + "'," + salary + ")";
			System.out.println(sqlQry);
			stmt.execute(sqlQry);
		} 
		catch (SQLException sqlExcept) 
		{
			sqlExcept.printStackTrace();
		}
		finally
		{
			closeStatement(stmt);
			closeConnection(conn);
		}
	}

	public static List<Integer> loadCustomerIdsOfSalBetween(int minSal, int maxSal) 
	{
		Connection conn = null;
		Statement stmt = null;
	
		List<Integer> custIds = new ArrayList<>();
		try
		{
			conn = createConnection();
			stmt = conn.createStatement();
			
			//SELECT Customer_ID
			
		}
		catch(SQLException sqlExcept)
		{
			sqlExcept.printStackTrace();
		}
		finally
		{
			closeStatement(stmt);
			closeConnection(conn);
		}
		
		return custIds;
	}
}
