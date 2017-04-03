package hack.intelligence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import hack.constants.Constants;
import hack.dao.DBUtility;
import hack.util.Utility;

public class Intelligence {

	private static final Integer FRAUD_CUST_COUNT = (int)(Constants.NUM_OF_CUSTOMERS * (Constants.FRAUD_CUST_COUNT_PERCENTAGE / 100.0f));
	
	public static Set<Integer> calculateFraudCustomers() 
	{
		Set<Integer> fraudCustList = new HashSet<Integer>();
		
		List<Integer> custIdList = DBUtility.fetchCustomerIds();
		
		for(int iter = 0; iter < FRAUD_CUST_COUNT; iter++)
		{
			Integer randVal = Utility.generateRandomNumberBetween(1, custIdList.size());
			
			while(true)
			{
				if(!fraudCustList.contains(custIdList.get(randVal)))
				{
					fraudCustList.add(custIdList.get(randVal));
					break;
				}
			}
		}
		
		return fraudCustList;
	}
	
	public static void writeToFile(Set<Integer> fraudCustList)
	{
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
	}

	public static void main(String[] args) {
		calculateFraudCustomers();
	}

}
