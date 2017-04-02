package hack.main;

import java.util.List;

import hack.intelligence.Intelligence;
import hack.main.randomgen.CustomerDataGen;
import hack.main.randomgen.TransactionsGenerator;

public class Main {

	public static void main(String[] args) {
		CustomerDataGen custGen = new CustomerDataGen();
		custGen.generateCustomerMasterData();
		
		List<Integer> customerId = Intelligence.calculateFraudCustomers();
		
		TransactionsGenerator txnGen = new TransactionsGenerator(customerId);
		txnGen.generateTransData();

	}

}
