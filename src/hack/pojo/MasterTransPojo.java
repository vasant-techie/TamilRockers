package hack.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterTransPojo {

	private List<MasterPojo> masDataLst = new ArrayList<MasterPojo>();
	private List<Transaction> tranDataLst = new ArrayList<Transaction>();
	private Map<String, List<Transaction>> transactionMap = new HashMap<>();

	public Map<String, List<Transaction>> getTransactionMap() {
		return transactionMap;
	}

	public void setTransactionMap(Map<String, List<Transaction>> transactionMap) {
		this.transactionMap = transactionMap;
	}

	public List<MasterPojo> getMasDataLst() {
		return masDataLst;
	}

	public void setMasDataLst(List<MasterPojo> masDataLst) {
		this.masDataLst = masDataLst;
	}

	public List<Transaction> getTranDataLst() {
		return tranDataLst;
	}

	public void setTranDataLst(List<Transaction> tranDataLst) {
		this.tranDataLst = tranDataLst;
	}

}
