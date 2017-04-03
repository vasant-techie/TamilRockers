package hack.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hack.constants.Constants;
import hack.util.DateUtil;

public class DBUtility {


	private DBUtility()
	{
	}

	private static Connection getConnection() 
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

	public static void insertTransactionDatatoDB() throws IOException 
	{

		String fileName = Constants.TXN_DATA_FILE_NAME;

		List<String> items = new ArrayList<String>();
		File file = new File(fileName);

		Connection conn = null;
		
		PreparedStatement pstmt = null;
		BufferedReader bufferedReader = null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));
			String line;

			conn = getConnection();
			
			String sqlQuery = "INSERT INTO " + Constants.TXN_TABLE_NAME 
					+ " (TRANSDATE, CUSTOMER_ID, AMOUNT, CREDIT_DEBIT_FLAG) "
					+ " VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sqlQuery);
			
			while ((line = bufferedReader.readLine()) != null) 
			{
				items = Arrays.asList(line.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));
	
				pstmt.setDate(1, DateUtil.convertStringToSQLDate(items.get(0), Constants.TXN_FILE_DATE_FORMAT));
				pstmt.setInt(2, Integer.valueOf(items.get(1)));
				pstmt.setFloat(3, Float.valueOf(items.get(2)));
				pstmt.setInt(4, Integer.valueOf(items.get(3)));
				
				pstmt.addBatch();
			}

			pstmt.executeBatch();
		}
		catch(SQLException sqlException)
		{
			sqlException.printStackTrace();
		}
		finally
		{
			closeStatement(pstmt);
			closeConnection(conn);
			
			if(null != bufferedReader)
				bufferedReader.close();
		}
	}



	// insert to cust db
	// To fetch file and insert to db

	public static void insertCustomerDatatoDB() throws IOException 
	{
		List<String> items = new ArrayList<String>();
		File file = new File(Constants.CUST_DATA_FILE_NAME);

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		BufferedReader bufferedReader = null;
		
		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));
			String line;
		
			conn = getConnection();
			String sqlQuery = "INSERT INTO " + Constants.CUST_TABLE_NAME + " (CUSTOMER_ID, NAME, SALARY) " +
								" VALUES (?,?,?)";
			

			pstmt = conn.prepareStatement(sqlQuery);
			
			while ((line = bufferedReader.readLine()) != null) 
			{
				items = Arrays.asList(line.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));

				pstmt.setInt(1, Integer.valueOf(items.get(0)));
				pstmt.setString(2, items.get(1));
				pstmt.setFloat(3, Float.valueOf(items.get(2)));

				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			closeStatement(pstmt);
			closeConnection(conn);
			if(null != bufferedReader)
				bufferedReader.close();
		}
	}

	public static List<Integer> loadCustomerIdsOfSalBetween(int minSal, int maxSal) 
	{
		Connection conn = null;
		Statement stmt = null;
	
		List<Integer> custIds = new ArrayList<>();
		try
		{
			conn = getConnection();
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
	
	public static List<Integer> fetchCustomerIds()
	{
		List<Integer> custIdList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		
		try
		{
			conn = getConnection();
			stmt = conn.createStatement();
			
			String sqlQuery = "SELECT CUSTOMER_ID FROM " + Constants.CUST_TABLE_NAME;
			ResultSet results = stmt.executeQuery(sqlQuery);
			
			while(results.next())
			{
				custIdList.add(results.getInt(1));
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			closeStatement(stmt);
			closeConnection(conn);
		}
		
		return custIdList;
	}
	
	public static void refreshDatabase()
	{
		Connection conn = getConnection();
		Statement stmt = null;
		
		try
		{
			stmt = conn.createStatement();
			stmt.execute("DELETE FROM " + Constants.CUST_TABLE_NAME);
			stmt.execute("DELETE FROM " + Constants.TXN_TABLE_NAME);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			closeStatement(stmt);
			closeConnection(conn);
		}
	}
}
