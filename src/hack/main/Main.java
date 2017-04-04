package hack.main;

import java.io.IOException;

import hack.dao.DBUtility;
import hack.intelligence.Intelligence;
import hack.main.randomgen.CustomerDataGen;
import hack.main.randomgen.TransactionsGenerator;

public class Main {

	public static void main(String[] args) 
	{
		try
		{
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
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

}
