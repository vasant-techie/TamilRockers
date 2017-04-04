package hack.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {
	
	private static final String CONFIG_FILE = "config" + File.separator + "master.properties";
	private static Properties props = new Properties();
	static
	{
		loadProperties();
	}
	
	public static final String CUST_DATA_FILE_NAME = props.getProperty("input_cust_file_name");
	public static final String TXN_DATA_FILE_NAME = props.getProperty("input_txn_file_name");
	public static final String FILE_DELIMITER = props.getProperty("file_delimiter");
	public static final Float LOW_SALARY_VAL = Float.parseFloat(props.getProperty("lowsal"));
	public static final Float MID_SALARY_VAL = Float.parseFloat(props.getProperty("midsal"));
	public static final Float HIGH_SALARY_VAL = Float.parseFloat(props.getProperty("highsal"));
	
	public static final Integer LOW_SAL_NORMAL_CREDIT_VAL = Integer.parseInt(props.getProperty("lowsal_normal_credit"));
	public static final Integer LOW_SAL_NORMAL_DEBIT_VAL = Integer.parseInt(props.getProperty("lowsal_normal_debit"));
	public static final Integer LOW_SAL_THRESHOLD_CREDIT_VAL = Integer.parseInt(props.getProperty("lowsal_threshold_credit"));
	public static final Integer LOW_SAL_THRESHOLD_DEBIT_VAL = Integer.parseInt(props.getProperty("lowsal_threshold_debit"));
	public static final Integer LOW_SAL_FRAUD_CREDIT_VAL = Integer.parseInt(props.getProperty("lowsal_fraud_credit"));
	public static final Integer LOW_SAL_FRAUD_DEBIT_VAL = Integer.parseInt(props.getProperty("lowsal_fraud_debit"));
	
	public static final Integer MID_SAL_NORMAL_CREDIT_VAL = Integer.parseInt(props.getProperty("midsal_normal_credit"));
	public static final Integer MID_SAL_NORMAL_DEBIT_VAL = Integer.parseInt(props.getProperty("midsal_normal_debit"));
	public static final Integer MID_SAL_THRESHOLD_CREDIT_VAL = Integer.parseInt(props.getProperty("midsal_threshold_credit"));
	public static final Integer MID_SAL_THRESHOLD_DEBIT_VAL = Integer.parseInt(props.getProperty("midsal_threshold_debit"));
	public static final Integer MID_SAL_FRAUD_CREDIT_VAL = Integer.parseInt(props.getProperty("midsal_fraud_credit"));
	public static final Integer MID_SAL_FRAUD_DEBIT_VAL = Integer.parseInt(props.getProperty("midsal_fraud_debit"));
	
	public static final Integer HIGH_SAL_NORMAL_CREDIT_VAL = Integer.parseInt(props.getProperty("highsal_normal_credit"));
	public static final Integer HIGH_SAL_NORMAL_DEBIT_VAL = Integer.parseInt(props.getProperty("highsal_normal_debit"));
	public static final Integer HIGH_SAL_THRESHOLD_CREDIT_VAL = Integer.parseInt(props.getProperty("highsal_threshold_credit"));
	public static final Integer HIGH_SAL_THRESHOLD_DEBIT_VAL = Integer.parseInt(props.getProperty("highsal_threshold_debit"));
	public static final Integer HIGH_SAL_FRAUD_CREDIT_VAL = Integer.parseInt(props.getProperty("highsal_fraud_credit"));
	public static final Integer HIGH_SAL_FRAUD_DEBIT_VAL = Integer.parseInt(props.getProperty("highsal_fraud_debit"));
	
	
	public static final Integer LOW_SAL_CUST_PERCENTAGE = Integer.parseInt(props.getProperty("lowsal_cust_count_percentage"));
	public static final Integer MID_SAL_CUST_PERCENTAGE = Integer.parseInt(props.getProperty("midsal_cust_count_percentage"));
	public static final Integer HIGH_SAL_CUST_PERCENTAGE = Integer.parseInt(props.getProperty("highsal_cust_count_percentage"));
	public static final Integer NUM_OF_CUSTOMERS = Integer.parseInt(props.getProperty("number_of_customers"));
	public static final Integer CUST_ID_BEGIN_VAL = Integer.parseInt(props.getProperty("cust_id_begin_value"));
	public static final String FROM_DATE = props.getProperty("fromDate");
	public static final String TO_DATE = props.getProperty("toDate"); 
	public static final String THRESHOLD_DATE = props.getProperty("thresholdDate");
	public static final String DATE_FORMAT = props.getProperty("dateFormat");
	public static final Integer DEBIT_TXN_CODE = Integer.parseInt(props.getProperty("debit_txn_code"));
	public static final Integer CREDIT_TXN_CODE = Integer.parseInt(props.getProperty("credit_txn_code"));
	
	public static final String LOW_SALARY_FILE = props.getProperty("lowSalaryFilePath");
	public static final String MID_SALARY_FILE =props.getProperty("MediumSalaryFilePath");
	public static final String HIGH_SALARY_FILE = props.getProperty("HighSalaryFilePath"); 
	
	public static final String LOW_SALARY_CODE = props.getProperty("lowSalaryCode"); 
	public static final String MID_SALARY_CODE = props.getProperty("midSalaryCode"); 
	public static final String HIGH_SALARY_CODE = props.getProperty("highSalaryCode");
	
	public static final Double MIN_TXN_AMOUNT = 0.01;
	public static final Double MAX_TXN_AMOUNT = 50000.00; 
	
	public static final Integer FRAUD_CUST_COUNT_PERCENTAGE = Integer.parseInt(props.getProperty("fraud_cust_percentage"));
	//public static final Integer FRAUD_LOW_SAL_CUST_COUNT_PERCENTAGE = Integer.parseInt(props.getProperty("lowsal_fraud_percentage"));
	//public static final Integer FRAUD_MID_SAL_CUST_COUNT_PERCENTAGE = Integer.parseInt(props.getProperty("midsal_fraud_percentage"));
	//public static final Integer FRAUD_HIGH_SAL_CUST_COUNT_PERCENTAGE = Integer.parseInt(props.getProperty("highsal_fraud_percentage"));
	
	public static final String CUSTOMER_NAME = props.getProperty("customerName");
	
	public static final String TXN_FILE_DATE_FORMAT = props.getProperty("txnFileDateFormat");
	
	public static final String TXN_TABLE_NAME = props.getProperty("txnTableName");
	
	public static final String CUST_TABLE_NAME = props.getProperty("custTableName");
	
	//Database config
	public static final String DRIVER_CLASS_NAME = props.getProperty("driverClassName");
	public static final String DB_URL = props.getProperty("dbURL");
	public static final String SELECTED_FRAUD_CUST_FILE = props.getProperty("chosenFraudCustFile");
	public static final String CUST_SALARIED_BETWEEN =  props.getProperty("custSalariedBetween");
	public static final String TXN_CODE_COUNT_CUSTOMER =  props.getProperty("txnCodeCountofCustomer");
	public static final String COUNT_OF_TXN_CODE_BETWEEN = props.getProperty("countOfTxnBetweenDate");
	public static final String INSERT_RESULT = props.getProperty("insertResult");
	
	
	
	private static void loadProperties() 
	{
		try 
		{
			props.load(new FileInputStream(CONFIG_FILE));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
