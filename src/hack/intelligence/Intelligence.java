package hack.intelligence;

import java.util.ArrayList;
import java.util.List;

import hack.constants.Constants;
import hack.dao.DBUtility;

public class Intelligence {

	public static List<Integer> calculateFraudCustomers() 
	{
		List<Integer> chosenFraudCustomerIds = new ArrayList<>();
		
		System.out.println("Total Num of Customers: " + Constants.NUM_OF_CUSTOMERS);
		int lowSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.LOW_SAL_CUST_PERCENTAGE/100.0f));
		int midSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.MID_SAL_CUST_PERCENTAGE/100.0f));
		int highSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.HIGH_SAL_CUST_PERCENTAGE/100.0f));
		
		System.out.println("Low Sal Count: " + lowSalCount);
		System.out.println("Mid Sal Count: " + midSalCount);
		System.out.println("High Sal Count: " + highSalCount);
		
		int lowSalFraudCustCount = (int)(lowSalCount*(Constants.FRAUD_LOW_SAL_CUST_COUNT_PERCENTAGE/100.0f));
		int midSalFraudCustCount = (int)(midSalCount*(Constants.FRAUD_MID_SAL_CUST_COUNT_PERCENTAGE/100.0f));
		int highSalFraudCustCount = (int)(highSalCount*(Constants.FRAUD_HIGH_SAL_CUST_COUNT_PERCENTAGE/100.0f));
		
		System.out.println("Low Sal Fraud Cust Count" + lowSalFraudCustCount);
		System.out.println("Mid Sal Fraud Cust Count" + midSalFraudCustCount);
		System.out.println("High Sal Fraud Cust Count" + highSalFraudCustCount);

		List<Integer> losSalCustomerIds = DBUtility.loadCustomerIdsOfSalBetween(0, Constants.LOW_SALARY_VAL);
		chosenFraudCustomerIds.addAll(losSalCustomerIds);

		return chosenFraudCustomerIds;
	}
	
	
	


	public static void main(String[] args) {
		calculateFraudCustomers();
	}

}
