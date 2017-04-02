package hack.main.randomgen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import hack.constants.Constants;
import hack.constants.SalaryLevel;
import hack.util.DateUtil;
import hack.util.Utility;

public class TransactionsGenerator 
{

	List<Integer> fraudCustomers;
	
	public TransactionsGenerator(List<Integer> fraudCustomers)
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
				generateTransactions(Integer.parseInt(recVals[0]), Integer.parseInt(recVals[2]), fileWriter);
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

	private void generateTransactions(Integer customerId, Integer salary, BufferedWriter fileWriter) 
	{
		SalaryLevel salaryLevel = getSalaryLevel(salary);
		
		if(salaryLevel == SalaryLevel.LOW_SALARIED)
		{
			generateTxnsForLowSalaried(customerId, salary, fileWriter);
		}
	}

	private void generateTxnsForLowSalaried(Integer customerId, Integer salary, BufferedWriter fileWriter) 
	{
		//Normal Transaction
		generateTxn(customerId, Constants.LOW_SAL_NORMAL_CREDIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		generateTxn(customerId, Constants.LOW_SAL_NORMAL_DEBIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, Constants.CREDIT_TXN_CODE, fileWriter);
		
		//if(fraudCustomers.contains(customerId))
		//{
			//Threshold Transaction
			generateTxn(customerId, Constants.MID_SAL_NORMAL_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.MID_SAL_NORMAL_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		//}
		//else
		{
			//Fraudulant Transaction
			generateTxn(customerId, Constants.MID_SAL_NORMAL_CREDIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
			generateTxn(customerId, Constants.MID_SAL_NORMAL_DEBIT_VAL, Constants.THRESHOLD_DATE, Constants.TO_DATE, Constants.DEBIT_TXN_CODE, fileWriter);
		}
	}

	private void generateTxn(Integer customerId, Integer lowSalNormalCreditVal, String fromDateStr, String thresholdDateStr, Integer txnCode, BufferedWriter fileWriter) 
	{
		Date fromDate = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		Date thresholdDate = DateUtil.parseDateInCustomFormat(thresholdDateStr, Constants.DATE_FORMAT);
		Integer numberOfMonthsBetween = DateUtil.numberOfMonthsBetween(fromDate, thresholdDate);
		
		Date date = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		for(int iter = 0; iter < numberOfMonthsBetween; iter++)
		{
			generateTxnForAMonth(customerId, lowSalNormalCreditVal, date, fileWriter, txnCode);
			date = DateUtil.addOneMonthToDate(date);
		}
	}

	private void generateTxnForAMonth(Integer customerId, Integer lowSalNormalCreditVal, Date date, BufferedWriter fileWriter, Integer TXN_CODE) 
	{
		//TODO: To make <= lowSalNormalCreditVal
		StringBuilder record = new StringBuilder();
		for(int iter = 0; iter < lowSalNormalCreditVal; iter++)
		{
			record.setLength(0);
			Date beginDate = DateUtil.getBeginDate(date);
			Date endDate = DateUtil.getEndDate(date);
			
			//Transaction Date
			Date curDate = DateUtil.generateDateInBetween(beginDate, endDate);
			String dateFormatted = DateUtil.formatDate(curDate, Constants.DATE_FORMAT);
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

	private SalaryLevel getSalaryLevel(Integer salary) 
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
