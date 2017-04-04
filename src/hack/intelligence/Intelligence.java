package hack.intelligence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;
import hack.constants.SalaryLevel;
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
		
		identifyFraudCustomers(SalaryLevel.LOW_SALARIED);
		identifyFraudCustomers(SalaryLevel.MID_SALARIED);
		identifyFraudCustomers(SalaryLevel.HIGH_SALARIED);
		System.out.println("Completed");
	}
	
	
	
	private static void identifyFraudCustomers(SalaryLevel salaryLevel) 
	{
		Float minSal = 0.0f;
		Float salVal = 0.0f;

		Double monthlyCreditAvgBeforeThreshold = 0.0d;
		Double monthlyDebitAvgBeforeThreshold =0.0d;
		Double monthlyCreditAvgAfterThreshold =0.0d;
		Double monthlyDebitAvgAfterThreshold = 0.0d;
		Double percentOfCreditDiff= 0.0d;
		Double percentOfDebitDiff=0.0d;
			
		
		if(salaryLevel == SalaryLevel.LOW_SALARIED)
		{
			minSal = 0.0f;
			salVal = Constants.LOW_SALARY_VAL;
		}
		else if(salaryLevel == SalaryLevel.MID_SALARIED)
		{
			minSal = Constants.LOW_SALARY_VAL + 1;
			salVal = Constants.MID_SALARY_VAL;
		}
		else
		{
			minSal = Constants.MID_SALARY_VAL + 1;
			salVal = Constants.HIGH_SALARY_VAL;
		}
		
		List<Integer> custIdList = DBUtility.getCustomersSalariedBetween(minSal, salVal);
		
		List<ResultData> resultDataList = new ArrayList<>();
		
		for(Integer custId : custIdList)
		{
			//System.out.println("Processing for Customer Id - " + custId);
			ResultData resultData = new ResultData();
			
			resultData.setCustomer_Id(custId);
			
			//Calculating # of Credit Txns before Threshold date
			Integer numCreditTxnCountBeforeThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.CREDIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.FROM_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT))
					);
			
			//System.out.println("# of Credit Txn Before Threshold: " + numCreditTxnCountBeforeThreshold);

			//Calculating # of Debit Txns before Threshold date
			Integer numDebitTxnCountBeforeThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.DEBIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.FROM_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT))
					);
			
			//System.out.println("# of Debit Txn Before Threshold: " + numDebitTxnCountBeforeThreshold);
		
			//Calculating # of Credit Txns after Threshold date
			Integer numCreditTxnCountAfterThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.CREDIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.TO_DATE, Constants.DATE_FORMAT))
					);
			
			//System.out.println("# of Credit Txn After Threshold: " + numCreditTxnCountAfterThreshold);
			resultData.setCredit_transaction(numCreditTxnCountAfterThreshold);
			
			//Calculating # of Debit Txns after Threshold date
			Integer numDebitTxnCountAfterThreshold = DBUtility.getTxnCountofCustomer(custId, 
					Constants.DEBIT_TXN_CODE,
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT)),
					DateUtil.convertUtilDateToSQLDate(DateUtil.parseDateInCustomFormat(Constants.TO_DATE, Constants.DATE_FORMAT))
					);
			
			//System.out.println("# of Debit Txn After Threshold: " + numDebitTxnCountAfterThreshold);
			resultData.setDebit_transaction(numDebitTxnCountAfterThreshold);
			
			Integer betweenDatesBeforeThreshold = DateUtil.fetchNumOfDaysBetween(DateUtil.convertStringToSQLDate(Constants.FROM_DATE, Constants.DATE_FORMAT),
					DateUtil.convertStringToSQLDate(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT));

			Integer betweenDatesAfterThreshold = DateUtil.fetchNumOfDaysBetween(DateUtil.convertStringToSQLDate(Constants.THRESHOLD_DATE, Constants.DATE_FORMAT),
					DateUtil.convertStringToSQLDate(Constants.TO_DATE, Constants.DATE_FORMAT));

			
			//Calculating monthly Average of Credit & Debit transactions before & after threshold
			 monthlyCreditAvgBeforeThreshold = (double) ((double)numCreditTxnCountBeforeThreshold/betweenDatesBeforeThreshold)*31;
			 monthlyCreditAvgBeforeThreshold = Utility.roundUpDecimalVal(monthlyCreditAvgBeforeThreshold);
			 
			 monthlyDebitAvgBeforeThreshold = (double) ((double)numDebitTxnCountBeforeThreshold/betweenDatesBeforeThreshold)*31;
			 monthlyDebitAvgBeforeThreshold = Utility.roundUpDecimalVal(monthlyDebitAvgBeforeThreshold);
			 
			 monthlyCreditAvgAfterThreshold = (double) ((double)numCreditTxnCountAfterThreshold/betweenDatesAfterThreshold)*31;
			 monthlyCreditAvgAfterThreshold = Utility.roundUpDecimalVal(monthlyCreditAvgAfterThreshold);
			 
			 monthlyDebitAvgAfterThreshold = (double) ((double)numDebitTxnCountAfterThreshold/betweenDatesAfterThreshold)*31;
			 monthlyDebitAvgAfterThreshold = Utility.roundUpDecimalVal(monthlyDebitAvgAfterThreshold);

			 //Calculating Percentage of difference - Credit Txn
			 if(monthlyCreditAvgAfterThreshold > monthlyCreditAvgBeforeThreshold)
			 {
				 percentOfCreditDiff = ((monthlyCreditAvgAfterThreshold - monthlyCreditAvgBeforeThreshold)/((monthlyCreditAvgAfterThreshold + monthlyCreditAvgBeforeThreshold)/2))*100;
			 }
			 else
			 {
				 percentOfCreditDiff = ((monthlyCreditAvgBeforeThreshold - monthlyCreditAvgAfterThreshold)/((monthlyCreditAvgAfterThreshold + monthlyCreditAvgBeforeThreshold)/2))*100;
			 }
			 
			 //Calculating Percentage of difference - Debit Txn
			 if(monthlyDebitAvgAfterThreshold > monthlyDebitAvgBeforeThreshold)
			 {
				 //percentOfDebitDiff = ((monthlyDebitAvgAfterThreshold - monthlyDebitAvgBeforeThreshold)/((monthlyDebitAvgAfterThreshold + monthlyDebitAvgBeforeThreshold)/2))*100;
				 percentOfDebitDiff = ((monthlyDebitAvgAfterThreshold - monthlyDebitAvgBeforeThreshold)/((monthlyDebitAvgAfterThreshold + monthlyDebitAvgBeforeThreshold)/2))*31;
			 }
			 else
			 {
				 //percentOfDebitDiff = ((monthlyDebitAvgBeforeThreshold - monthlyDebitAvgAfterThreshold)/((monthlyDebitAvgAfterThreshold + monthlyDebitAvgBeforeThreshold)/2))*100;
				 percentOfDebitDiff = ((monthlyDebitAvgBeforeThreshold - monthlyDebitAvgAfterThreshold)/((monthlyDebitAvgAfterThreshold + monthlyDebitAvgBeforeThreshold)/2))*31;
			 }
			
			
			 //Identifying the fraudulant customer
			if(percentOfCreditDiff > Constants.THRESHOLD_CREDIT_VARIANCE_PERCENTAGE || percentOfDebitDiff > Constants.THRESHOLD_DEBIT_VARIANCE_PERCENTAGE )
			{
				resultData.setIs_fraudulant(Constants.FRAUD_CUST_CODE);
			}
			else
			{
				resultData.setIs_fraudulant(Constants.SAFE_CUST_CODE);
			}
			
			resultDataList.add(resultData);
		}
		
		//Write the analysis findings into database
		DBUtility.insertResult(resultDataList);
		
	}
}
