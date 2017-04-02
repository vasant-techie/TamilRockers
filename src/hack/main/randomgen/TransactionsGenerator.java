package hack.main.randomgen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;
import hack.constants.SalaryLevel;
import hack.util.DateUtil;

public class TransactionsGenerator 
{

	
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
		generateCreditTxn(customerId, Constants.LOW_SAL_NORMAL_CREDIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, fileWriter);
		generateDebitTxn(customerId, Constants.LOW_SAL_NORMAL_DEBIT_VAL, Constants.FROM_DATE, Constants.THRESHOLD_DATE, fileWriter);
		
		
	}

	private void generateCreditTxn(Integer customerId, Integer lowSalNormalCreditVal, String fromDateStr, String thresholdDateStr, BufferedWriter fileWriter) 
	{
		Date fromDate = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		Date thresholdDate = DateUtil.parseDateInCustomFormat(thresholdDateStr, Constants.DATE_FORMAT);
		Integer numberOfMonthsBetween = DateUtil.numberOfMonthsBetween(fromDate, thresholdDate);
		
		Date date = DateUtil.parseDateInCustomFormat(fromDateStr, Constants.DATE_FORMAT);
		for(int iter = 0; iter < numberOfMonthsBetween; iter++)
		{
			generateTxnForAMonth(customerId, lowSalNormalCreditVal, date, fileWriter, Constants.CREDIT_TXN_CODE);
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
			record.append(curDate);
			record.append(Constants.FILE_DELIMITER);
			
			//Customer Id
			record.append(customerId);
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

	private void generateDebitTxn(Integer customerId, Integer lowSalNormalDebitVal, String toDate, String thresholdDate, BufferedWriter fileWriter) 
	{
		
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
