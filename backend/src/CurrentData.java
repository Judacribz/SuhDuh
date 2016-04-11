//Class for reading in the current data from both accounts and transaction files
//Interacts with Transactions and Accounts
//Created from inside the main

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.String.*;

public class CurrentData {

//Arraylist used to store the accounts within the system
public HashMap<Integer, Account> accounts = new HashMap<Integer, Account>();
//Arraylist for storing all the transactions being processed today
public ArrayList<Transaction> transactions = new ArrayList<Transaction>();

//Reads in the accounts from the current master accounts file
public void getCurrentAccounts(){
	String line = null;
	accounts.put(0, new Account(00000,"NULL ACCOUNT",true,0,0,false));
	//begin the readin from accounts file
	try {
		FileReader fileReader = new FileReader("MasterBankAccounts.dat");
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		//tokenize each line of file into the data we need for the Account class
		String accountNum, accountName, activeStr, balance, totalTransaction, studentStr;
		boolean active, student;
		while((line = bufferedReader.readLine()) != null) {

			accountNum = line.substring(0,5);
			accountName = line.substring(6,26);
			activeStr = line.substring(27,28);
			balance = line.substring(29,37);
			totalTransaction = line.substring(38,42);
			studentStr = line.substring(43,44);

			//Trim account names trailing white space
			accountName = accountName.trim();

			if ( activeStr.equals("A")) {
				active = true;
			}
			else {
				active = false;
			}

			if ( studentStr.equals("S")) {
				student = true;
			}
			else {
				student = false;
			}

			//Create a new Account from the data
			//0 - AccountNum, 1 - AccountName, 3 - Balance, 4 - Total Transaction
			Account account = new Account(Integer.parseInt(accountNum), accountName, active,
			                              Double.parseDouble(balance), Integer.parseInt(totalTransaction), student);

			//Add new account to the accounts list
			accounts.put(Integer.parseInt(accountNum), account);
		}
		bufferedReader.close();
		System.out.println("Accounts Successfully Read In");
	}
	//Gracefully handle any errors
	catch(FileNotFoundException e) {
		System.out.println( "Unable to open file OldMasterBankAccounts.dat. Please make sure file is in the Project folder.");
	}
	catch(IOException e) {
		System.out.println("Error reading file MasterBankAccounts.dat");
	}
}

//Read in the transaction files and add them to the transactions log
public void getTransactions(){
	String line = null;
	transactions.add(0, null);
	//read transaction file and tokenize what we need from it
	try {
		FileReader fileReader = new FileReader("Transactions.trf");
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String transNum, name, accountNum, money, misc;
		while((line = bufferedReader.readLine()) != null) {

			transNum = line.substring(0,2);
			name = line.substring(3,23);
			accountNum = line.substring(24,29);
			money = line.substring(30,38);
			misc = line.substring(39,41);

			//Trim account names trailing white space
			name = name.trim();
			//Trim misc data
			misc = misc.trim();

			//Create a new transaction from the information read in
			//0 - TransNum, 1 - Name, 2 - AccountNum, 3 - Money, 4 - MiscInfo
			Transaction trans = new Transaction(Integer.parseInt(transNum), name,
			                                    Integer.parseInt(accountNum), Double.parseDouble(money), misc);

			//Add transaction to our transaction list
			transactions.add(trans);
		}
		bufferedReader.close();
		System.out.println("Transactions Succesfully Read In");
	}
	//Gracefully handle any errors
	catch(FileNotFoundException ex1) {
		System.out.println( "Unable to open file Transactions.trans. Please make sure file is in the Project folder.");
	}
	catch(IOException ex) {
		System.out.println("Error reading file Transactions.trans");
	}
}

//Helper function for displaying all of the currently read in accounts
public void printAccounts(){
	for(HashMap.Entry<Integer,Account> acc : accounts.entrySet()) {
		System.out.println(acc.getValue().toString(true));
	}
}

//helper function for displaying all the currently read in transactions
public void printTransactions(){
	int i = 1;
	while(i < transactions.size()) {
		System.out.println(transactions.get(i));
		i++;
	}
}
}
