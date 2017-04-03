package hack.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import hack.pojo.MasterTransPojo;
import hack.pojo.Transaction;
import hack.constants.*;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReadStringFromFile {

	public static void main(String[] args) throws IOException {

		MasterTransPojo MasterTransLst = fileMaster();
		String transLine;
		List<String> itemsTrans = new ArrayList<String>();
		StringBuffer stringBuffer = new StringBuffer();
		List<Transaction> objTransLst = MasterTransLst.getTranDataLst();
		MasterTransPojo objMasTransDTO = getCreditAndDebitRecord(objTransLst);
		Map<String, List<Transaction>> transactionMap = MasterTransLst.getTransactionMap();
		/*
		File file = new File(Constants.LOW_SALARY_FILE);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		bufferedReader.readLine();
		while ((transLine = bufferedReader.readLine()) != null) {
			stringBuffer.append(transLine);
			stringBuffer.append("\n");
			itemsTrans = Arrays.asList(transLine.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));
			String customerId = itemsTrans.get(0);
			String salariedType = itemsTrans.get(3);
			boolean istransactedUser = transactionMap.containsKey(customerId);
			if (istransactedUser) {
				List<Transaction> objTransactionLst = transactionMap.get(customerId);
				int debitCount = 0;
				int creditCount = 0;
				double beforeDebitedAmount;
				double afterDebitedAmount;
				for (Transaction trans : objTransactionLst) {

				}

			}
		}*/
	}

	public static MasterTransPojo getCreditAndDebitRecord(List<Transaction> objTransLst) {
		MasterTransPojo objMasTransDTO = new MasterTransPojo();

		return objMasTransDTO;

	}

	public static Date convertStringtoDate(String DateVal) {

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			System.out.println(DateVal);
			date = formatter.parse(DateVal);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	public static MasterTransPojo fileMaster() throws IOException {

		// String fName = Constants.MASTER_DATA_INPUT;
		// String ftName = Constants.TRANS_DATA_INPUT;

		String fName = Constants.CUST_DATA_FILE_NAME;
		String ftName = Constants.TXN_DATA_FILE_NAME;

		String fileLowPath = Constants.LOW_SALARY_FILE;
		String fileMidPath = Constants.MID_SALARY_FILE;
		String fileHighPath = Constants.HIGH_SALARY_FILE;

		MasterTransPojo mtp = new MasterTransPojo();

		Transaction tc = new Transaction();
		HashMap<String, List<Transaction>> transactionMap = new HashMap<String, List<Transaction>>();
		List<String> items = new ArrayList<String>();
		List<String> itemsTrans = new ArrayList<String>();
		File file = new File(fName);
		File fileTrans = new File(ftName);
		
		//File fileLow = new File(fileLowPath);
		//File fileMedium = new File(fileMidPath);
		//File fileHigh = new File(fileHighPath);
		
		File fileLow = FileUtils.getFile(fileLowPath);
		File fileMedium = FileUtils.getFile(fileMidPath);
		File fileHigh = FileUtils.getFile(fileHighPath);
		
		//Added to remove the existing files before writing
		FileUtils.deleteQuietly(fileLow);
		FileUtils.deleteQuietly(fileMedium);
		FileUtils.deleteQuietly(fileHigh);

		FileReader fileReader = new FileReader(file);
		FileReader fileReaderTrans = new FileReader(fileTrans);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		BufferedReader bufferedReaderTrans = new BufferedReader(fileReaderTrans);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		String transLine;
		String salary = "";

		if (fName.equalsIgnoreCase(Constants.CUST_DATA_FILE_NAME)) {
			bufferedReader.readLine();

			while ((line = bufferedReader.readLine()) != null) {

				stringBuffer.append(line);
				stringBuffer.append("\n");

				items = Arrays.asList(line.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));
				salary = items.get(2);
				if (Integer.valueOf(salary) <= 500000) {
					FileUtils.writeStringToFile(fileLow, items.get(0) + "\t" + items.get(1) + "\t" + items.get(2) + "\t"
							+ Constants.LOW_SALARY_CODE + "\t" + "\n", true);
				} else if ((Integer.valueOf(salary) > 500000) && (Integer.valueOf(salary) <= 1000000)) {
					FileUtils.writeStringToFile(fileMedium, items.get(0) + "\t" + items.get(1) + "\t" + items.get(2)
							+ "\t" + Constants.MID_SALARY_CODE + "\t" + "\n", true);
				} else {
					FileUtils.writeStringToFile(fileHigh, items.get(0) + "\t" + items.get(1) + "\t" + items.get(2)
							+ "\t" + Constants.HIGH_SALARY_CODE + "\t" + "\n", true);
				}
			}

		}

		if (ftName.equalsIgnoreCase(Constants.TXN_DATA_FILE_NAME)) {
			bufferedReaderTrans.readLine();
			while ((transLine = bufferedReaderTrans.readLine()) != null) {
				stringBuffer.append(transLine);
				stringBuffer.append("\n");
				itemsTrans = Arrays.asList(transLine.split("\\s*" + Constants.FILE_DELIMITER + "\\s*"));

				tc.setTransDate(convertStringtoDate(itemsTrans.get(0)));
				tc.setCustId(Integer.parseInt(itemsTrans.get(1)));
				tc.setAmount(Float.valueOf(itemsTrans.get(2)));
				tc.setDebitrCredit(Integer.valueOf(itemsTrans.get(3)));
				boolean isExistingUser = transactionMap.containsKey(tc.getCustId());
				if (isExistingUser) {
					List<Transaction> objTransList = transactionMap.get(tc.getCustId());
					objTransList.add(tc);
					transactionMap.put(String.valueOf(tc.getCustId()), objTransList);
				} else {
					List<Transaction> objTransList = new ArrayList<Transaction>();
					objTransList.add(tc);
					transactionMap.put(String.valueOf(tc.getCustId()), objTransList);
				}

			}
		}
		// FileUtils.writeLines(file, masDataLst);
		mtp.setTransactionMap(transactionMap);

		fileReader.close();

		fileReaderTrans.close();

		return mtp;
	}

}