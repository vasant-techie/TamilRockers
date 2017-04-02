package hack.pojo;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class MasterPojo {
	
	private String firstName ;
    private int salary;
	private int custId;
	private int salariedType;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getSalariedType() {
		return salariedType;
	}
	public void setSalariedType(int salariedType) {
		this.salariedType = salariedType;
	}
}
