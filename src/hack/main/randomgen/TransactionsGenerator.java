package hack.main.randomgen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;
import hack.constants.SalaryLevel;

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
			generateTxnsForLowSalaried();
		}
	}

	private void generateTxnsForLowSalaried() 
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
