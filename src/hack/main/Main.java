package hack.main;

import java.io.IOException;

import hack.dao.DBUtility;
import hack.intelligence.Intelligence;
import hack.sourceGenerator.CustomerDataGen;
import hack.sourceGenerator.TransactionsGenerator;

public class Main {

	public static void main(String[] args) 
	{
		try
		{
			DBUtility.createDatabase();
			System.out.println("Table Created Succesfully");
						
			DBUtility.refreshDatabase();
			System.out.println("Done with refreshing database..");
			
			CustomerDataGen custGen = new CustomerDataGen();
			custGen.generateCustomerMasterData();
			System.out.println("Done with generating customer data..");
			
			DBUtility.insertCustomerDatatoDB();
			System.out.println("Done with inserting Customer data into database..");
		
			TransactionsGenerator txnGen = new TransactionsGenerator(Intelligence.calculateFraudCustomers());
			txnGen.generateTransData();
			System.out.println("Done with generating Transaction data..");
			
			DBUtility.insertTransactionDatatoDB();
			System.out.println("Done with inserting transaction data into database..");
			
			Intelligence.generateFradulantCustomerData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			
		}
	}

}
