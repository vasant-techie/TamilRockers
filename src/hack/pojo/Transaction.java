package hack.pojo;

import java.util.Date;

public class Transaction {
	
	private Date transDate ;
	private int custId ;
	private float amount;
	private int debitrCredit;
	
	
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public int getDebitrCredit() {
		return debitrCredit;
	}
	public void setDebitrCredit(int debitrCredit) {
		this.debitrCredit = debitrCredit;
	}
}
