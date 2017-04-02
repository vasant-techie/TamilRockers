package hack.pojo;

import java.util.Date;

public class Transaction {
	
	private Date transDate ;
	private String custId ;
	private int amount;
	private int debitrCredit;
	
	
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getDebitrCredit() {
		return debitrCredit;
	}
	public void setDebitrCredit(int debitrCredit) {
		this.debitrCredit = debitrCredit;
	}
}
