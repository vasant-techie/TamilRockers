package hack.intelligence;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;
import hack.dao.DBUtility;
import hack.pojo.ResultData;
import hack.util.DateUtil;
import hack.util.Utility;

public class Intelligence 
{

	private static final Integer FRAUD_CUST_COUNT = (int)(Constants.NUM_OF_CUSTOMERS * (Constants.FRAUD_CUST_COUNT_PERCENTAGE / 100.0f));
	
	public static Set<Integer> calculateFraudCustomers() 
	{
		System.out.println("Pre-Calculating Fraud Customers..");
		Set<Integer> fraudCustList = new HashSet<>();
		
		List<Integer> custIdList = DBUtility.fetchCustomerIds();
		
		for(int iter = 0; iter < FRAUD_CUST_COUNT; iter++)
		{
			Integer randVal = Utility.generateRandomNumberBetween(0, custIdList.size()-1);
			//System.out.println("Random Value: " + randVal);
			
			while(true)
			{
				if(fraudCustList.contains(custIdList.get(randVal)))
				{
					System.out.println("Recalculating..");
					//fraudCustList.add(custIdList.get(randVal));
					//System.out.println("Fraud Customer Set Size: " + fraudCustList);
					randVal = Utility.generateRandomNumberBetween(0, custIdList.size()-1);
				}
				else
				{
					fraudCustList.add(custIdList.get(randVal));
					//System.out.println("Fraud Customer Set Size: " + fraudCustList);
					randVal = Utility.generateRandomNumberBetween(0, custIdList.size()-1);
					break;
				}
			}
		}
		System.out.println("Calculated fraud cust count - " + fraudCustList.size());
		
		writeToFile(fraudCustList);
		return fraudCustList;
	}
	
	private static void writeToFile(Set<Integer> fraudCustList)
	{
		System.out.println("Writing chosen Fraudulant customer to a file..");
		File fraudCustFile = FileUtils.getFile(Constants.SELECTED_FRAUD_CUST_FILE);
		FileUtils.deleteQuietly(fraudCustFile);
		
		try
		{
			FileUtils.writeLines(fraudCustFile, fraudCustList);
			System.out.println("Done with writing chosen fraudulant customers..");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("Done with writing fraudulant customer data into file..");
	}

	public static void generateFradulantCustomerData(){
		
		generateFraudAlertForLowSal();
		
	}
	
	
	
	private static void generateFraudAlertForLowSal() 
	{
		List<Integer> lowSalCustIds = DBUtility.getCustomersSalariedBetween(0.00f, Constants.LOW_SALARY_VAL);
		
		for(Integer custId : lowSalCustIds)
		{
			ResultData resultData = new ResultData();
			Integer numCreditTxnCountBeforeThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.CREDIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.FROM_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT))
					);
			
			System.out.println("# of Credit Txn Before Threshold: " + numCreditTxnCountBeforeThreshold);
			
			Integer numDebitTxnCountBeforeThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.DEBIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.FROM_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT))
					);
			
			System.out.println("# of Debit Txn Before Threshold: " + numDebitTxnCountBeforeThreshold);
			
			
			//karthik
			Integer betweenDates = DateUtil.fetchDateUtilbetTwoDates(DateUtil.convertStringToSQLDate(Constants.FROM_DATE, Constants.DATE_FORMAT),
					DateUtil.convertStringToSQLDate(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT));
			System.out.println(betweenDates);
			
			
			//average
			Float creditAvg = (float) (numCreditTxnCountBeforeThreshold/betweenDates);
			
			System.out.println(creditAvg);
			
			
			
		}
		
		
		
	}

	public static void main(String[] args) {
		calculateFraudCustomers();
	}

}
