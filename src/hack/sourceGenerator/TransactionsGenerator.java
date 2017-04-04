package hack.sourceGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import hack.constants.Constants;
import hack.constants.SalaryLevel;
import hack.util.DateUtil;
import hack.util.Utility;

public class TransactionsGenerator 
{

	Set<Integer> fraudCustomers;
	
	public TransactionsGenerator(Set<Integer> fraudCustomers)
	{
		this.fraudCustomers = fraudCustomers;
	}
	
	public void generateTransData()
	{
		BufferedReader fileReader = null;
		BufferedWriter fileWriter = null;
		try 
		{
			fileReader = new BufferedReader(new FileReader(new File(Constants.CUST_DATA_FILE_NAME)));
			fileWriter = new BufferedWriter(new FileWriter(new File(Constants.TXN_DATA_FILE_NAME)));
			
			String line = null;
			while((line = fileReader.readLine()) != null)
			{
				String[] recVals = line.split(Constants.FILE_DELIMITER);
				generateTransactions(Integer.parseInt(recVals[0]), Float.parseFloat(recVals[2]), fileWriter);
			}
						
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(fileReader != null)
			{	
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(fileWriter != null)
			{
				try {
					fileWriter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}
	}

	private void generateTransactions(Integer customerId, Float salary, BufferedWriter fileWriter) 
	{
		SalaryLevel salaryLevel = getSalaryLevel(salary);
		
		if(salaryLevel == SalaryLevel.LOW_SALARIED)
		{
			generateTxnsForLowSalaried(customerId, salary, fileWriter);
		}
		else if(salaryLevel == SalaryLevel.MID_SALARIED)
		{
			generateTxnsForMidSalaried(customerId, salary, fileWriter);
		}
		else
		{
			generateTxnsForHighSalaried(customerId, salary, fileWriter);
		}
	}

	private void generateTxnsForLowSalaried(Integer customerId, Float salary, BufferedWriter fileWriter) 
	{
		//Normal Transaction
		generateTxn(customerId, Constants.LOW_SAL_NORMAL_CREDIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
		generateTxn(customerId, Constants.LOW_SAL_NORMAL_DEBIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		
		if(fraudCustomers.contains(customerId))
		{
			//Fraudulant Transaction
			generateTxn(customerId, Constants.LOW_SAL_FRAUD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.LOW_SAL_FRAUD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
		else
		{
			//Threshold Transaction
			generateTxn(customerId, Constants.LOW_SAL_THRESHOLD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.LOW_SAL_THRESHOLD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
	}

	private void generateTxnsForMidSalaried(Integer customerId, Float salary, BufferedWriter fileWriter) 
	{
		//Normal Transaction
		generateTxn(customerId, Constants.MID_SAL_NORMAL_CREDIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
		generateTxn(customerId, Constants.MID_SAL_NORMAL_DEBIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		
		if(fraudCustomers.contains(customerId))
		{
			//Fraudulant Transaction
			generateTxn(customerId, Constants.MID_SAL_FRAUD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.MID_SAL_FRAUD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
		else
		{
			//Threshold Transaction
			generateTxn(customerId, Constants.MID_SAL_THRESHOLD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.MID_SAL_THRESHOLD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
	}
	
	private void generateTxnsForHighSalaried(Integer customerId, Float salary, BufferedWriter fileWriter) 
	{
		//Normal Transaction
		generateTxn(customerId, Constants.HIGH_SAL_NORMAL_CREDIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		generateTxn(customerId, Constants.HIGH_SAL_NORMAL_DEBIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
		
		if(fraudCustomers.contains(customerId))
		{
			//Fraudulant Transaction
			generateTxn(customerId, Constants.HIGH_SAL_FRAUD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.HIGH_SAL_FRAUD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
		else
		{
			//Threshold Transaction
			generateTxn(customerId, Constants.HIGH_SAL_THRESHOLD_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.HIGH_SAL_THRESHOLD_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
	}
	
	private void generateTxn(Integer customerId, Integer txnCount, String fromDateStr, String thresholdDateStr, Integer txnCode, BufferedWriter fileWriter) 
	{
		Date fromDate = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		Date thresholdDate = DateUtil.parseDateInCustomFormat(thresholdDateStr, Constants.DATE_FORMAT);
		Integer numberOfMonthsBetween = DateUtil.numberOfMonthsBetween(fromDate, thresholdDate);
		
		Date date = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		for(int iter = 0; iter < numberOfMonthsBetween; iter++)
		{
			generateTxnForAMonth(customerId, txnCount, date, fileWriter, txnCode);
			date = DateUtil.addOneMonthToDate(date);
		}
	}

	private void generateTxnForAMonth(Integer customerId, Integer txnCount, Date date, BufferedWriter fileWriter, Integer TXN_CODE) 
	{
		//TODO: To make <= lowSalNormalCreditVal
		StringBuilder record = new StringBuilder();
		for(int iter = 0; iter < txnCount; iter++)
		{
			record.setLength(0);
			Date beginDate = DateUtil.getBeginDate(date);
			Date endDate = DateUtil.getEndDate(date);
			
			//Transaction Date
			Date curDate = DateUtil.generateDateInBetween(beginDate, endDate);
			String dateFormatted = DateUtil.formatDate(curDate, Constants.TXN_FILE_DATE_FORMAT);
			record.append(dateFormatted);
			record.append(Constants.FILE_DELIMITER);
			
			//Customer Id
			record.append(customerId);
			record.append(Constants.FILE_DELIMITER);
			
			//Transaction Amount
			record.append(Utility.generateRandomNumberBetween(Constants.MIN_TXN_AMOUNT, Constants.MAX_TXN_AMOUNT));
			record.append(Constants.FILE_DELIMITER);
			
			//Transaction Code
			record.append(TXN_CODE);
			record.append("\n");
			
			try 
			{
				fileWriter.write(record.toString());
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	private SalaryLevel getSalaryLevel(Float salary) 
	{
		if(salary > 0 && salary <= Constants.LOW_SALARY_VAL)
		{
			return SalaryLevel.LOW_SALARIED;
		}
		else if(salary > Constants.LOW_SALARY_VAL && salary <= Constants.MID_SALARY_VAL)
		{
			return SalaryLevel.MID_SALARIED;
		}
		else
		{
			return SalaryLevel.HIGH_SALARIED;
		}
		
	}

	
}
