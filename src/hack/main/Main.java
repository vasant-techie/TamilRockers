package hack.main;

import hack.main.randomgen.CustomerDataGen;
import hack.main.randomgen.TransactionsGenerator;

public class Main {

	public static void main(String[] args) {
		CustomerDataGen custGen = new CustomerDataGen();
		custGen.generateCustomerMasterData();
		
		TransactionsGenerator txnGen = new TransactionsGenerator();
		txnGen.generateTransData();

	}

}
