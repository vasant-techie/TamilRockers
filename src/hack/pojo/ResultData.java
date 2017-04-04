package hack.pojo;

public class ResultData {

	private Integer customer_Id;
	private Integer credit_transaction;
	private Integer debit_transaction;
	private Integer is_fraudulant;
	
	
	public Integer getCustomer_Id() {
		return customer_Id;
	}
	public void setCustomer_Id(Integer customer_Id) {
		this.customer_Id = customer_Id;
	}
	public Integer getCredit_transaction() {
		return credit_transaction;
	}
	public void setCredit_transaction(Integer credit_transaction) {
		this.credit_transaction = credit_transaction;
	}
	public Integer getDebit_transaction() {
		return debit_transaction;
	}
	public void setDebit_transaction(Integer debit_transaction) {
		this.debit_transaction = debit_transaction;
	}
	public Integer getIs_fraudulant() {
		return is_fraudulant;
	}
	public void setIs_fraudulant(Integer is_fraudulant) {
		this.is_fraudulant = is_fraudulant;
	}
	
	
	
}
