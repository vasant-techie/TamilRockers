package hack.main.randomgen;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;

public class CustomerDataGen 
{
			
	private Integer customerId = Constants.CUST_ID_BEGIN_VAL;
	private Integer count = 0;
	
	public void generateCustomerMasterData()
	{
		File custMasterFile = FileUtils.getFile(Constants.CUST_DATA_FILE_NAME);
		FileUtils.deleteQuietly(custMasterFile);
		
		try 
		{
			int lowSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.LOW_SAL_CUST_PERCENTAGE/100.0f));
			int midSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.MID_SAL_CUST_PERCENTAGE/100.0f));
			int highSalCount = (int)(Constants.NUM_OF_CUSTOMERS*(Constants.HIGH_SAL_CUST_PERCENTAGE/100.0f));

			generateData(custMasterFile, lowSalCount, 1, Constants.LOW_SALARY_VAL);
			generateData(custMasterFile, midSalCount, Constants.LOW_SALARY_VAL + 1, Constants.MID_SALARY_VAL);
			generateData(custMasterFile, highSalCount, Constants.MID_SALARY_VAL + 1, Constants.HIGH_SALARY_VAL);
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}

	private void generateData(File custMasterFile, int lowSalCount, float minSalary, float maxSalary) throws IOException {
		StringBuilder custRecord = new StringBuilder();
		for(int iter = 0; iter < lowSalCount; iter++)
		{
			count++;
			custRecord.setLength(0);
			//Customer Id
			customerId = generateCustId(customerId);
			custRecord.append(customerId);
			custRecord.append(Constants.FILE_DELIMITER);
			//Customer Name
			String custName = generateCustomerName(customerId);
			custRecord.append(custName);
			custRecord.append(Constants.FILE_DELIMITER);
			//Salary
			float salary = generateSalary(minSalary, maxSalary);
			custRecord.append(salary);
			
			//Inserting new line
			custRecord.append("\n");
			
			FileUtils.writeStringToFile(custMasterFile, custRecord.toString(), true);
		}
	}
	
	private float generateSalary(float min, float max)
	{
		return Double.valueOf(ThreadLocalRandom.current().nextDouble(min, max + 1)).floatValue();
	}

	private int generateCustId(Integer customerId)
	{
		customerId = customerId + 1;
		return customerId;
	}
	
	private String generateCustomerName(int custId)
	{
		return Constants.CUSTOMER_NAME + count;
	}
}
