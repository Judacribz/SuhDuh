//Class for applying the transactions to the accounts
//Interaces with Accounts and Transactions
//Requires data to be created from currentData and then passed

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionHandler {

//Working set of accounts
private HashMap<Integer, Account> accounts;
//The transactions to process
private ArrayList<Transaction> transactions;
//The account we are currently applying transactions to
private Account curAcc;
private boolean adminSession;
private int transfer = 1;

//constructor which takes in the transactions to process
public TransactionHandler(HashMap<Integer, Account> accs,ArrayList<Transaction> trans){
	//set our current working accounts to the ones passed in
	accounts = accs;
	//set current transactions to those passed in
	transactions = trans;
}

//Increment the transaction count on the current account
public void IncTrans(){
	//If admin it doesnt not count as a transaction
	if(!adminSession) {
		curAcc.totalTransactions++;
	}
}

//Charge accounts the transaction fee
public void ChargeAccount(){
	//check if session is admin if so no fees
	if(!adminSession) {
		if(curAcc.isStudent) {
			curAcc.currentBalance -= 0.05;
		}else{
			curAcc.currentBalance -= 0.10;
		}
	}
}

//Handle all transactions and update the accounts list
//Once complete return the new modified accounts list
public HashMap<Integer, Account> HandleTransactions(){
	//iterate over each transaction
	for(Transaction trans : transactions) {
		//Check if the transaction exists
		if(trans!=null) {
			//Check what type of transaction we are dealing with
			curAcc = accounts.get(trans.accountNum);
			System.out.println(curAcc.toString(true));
			switch(trans.transType) {
//End of session
			case 0:
				System.out.println(trans.accountName + " Session Ended");
				break;
//Withdrawal
			case 1:
				if(curAcc.currentBalance - trans.moneyInvolved > 0) {
					curAcc.currentBalance -= trans.moneyInvolved;
					IncTrans();
					ChargeAccount();
					System.out.println("Withdrawl Successful");
				}else{
					System.out.println("Withdrawl Failed");
				}

				break;
//Transfer --
			case 2:
				if(transfer==0) {
					System.out.println("Transactions Failed");
					break;
				}
				if ( (transfer % 2) != 0) {
					if((accounts.get(trans.accountNum).currentBalance - trans.moneyInvolved) > 0) {
						accounts.get(trans.accountNum).currentBalance -= trans.moneyInvolved;
						ChargeAccount();
						IncTrans();
					}else{
						System.out.println("Transaction Failed");
						transfer=-1;
						break;
					}
				}
				else {
					accounts.get(trans.accountNum).currentBalance += trans.moneyInvolved;
				}
				System.out.println("Money Transfered");
				transfer++;
				break;
//Paybill
			case 3:
				if((curAcc.currentBalance - trans.moneyInvolved) > 0 ) {
					curAcc.currentBalance -= trans.moneyInvolved;
					ChargeAccount();
					IncTrans();
					System.out.println("Bills Paid");
				}else{
					System.out.println("Transaction Failed");
				}
				break;
//Deposit
			case 4:
				curAcc.currentBalance+=trans.moneyInvolved;
				IncTrans();
				ChargeAccount();
				System.out.println("Deposit Successful");
				break;
//Create - Admin --
			case 5:
				if(adminSession) {
					int newNum = accounts.size();
					Account newAccount = new Account(newNum, trans.accountName, true, trans.moneyInvolved, 0, false);
					accounts.put(newNum, newAccount);
					System.out.println("Account Created");
				}else{
					System.out.println("Insufficent Priviledge");
				}
				break;
//Delete - Admin
			case 6:
				if(adminSession) {
					accounts.remove(trans.accountNum);
					System.out.println("Account Deleted");
				}else{
					System.out.println("Insufficent Priviledge");
				}
				break;
//Disable - Admin
			case 7:
				if(adminSession) {
					accounts.get(trans.accountNum).isActive = false;
					System.out.println("Account Disabled");
				}else{
					System.out.println("Insufficent Priviledge");
				}
				break;
//ChangePlan - Admin
			case 8:
				if(adminSession) {
					accounts.get(trans.accountNum).isStudent = !accounts.get(trans.accountNum).isStudent;
					System.out.println("Plan Changed");
				}else{
					System.out.println("Insufficent Priviledge");
				}
				break;
//Enable Account - Admin --
			case 9:
				if(adminSession) {
					accounts.get(trans.accountNum).isActive = true;
					System.out.println("Account Enabled");
				}else{
					System.out.println("Insufficent Priviledge");
				}
				break;
//Login
			case 10:

				//check if valid account
				if(curAcc!=null) {
					//check if the current session is to be an admin session
					if(trans.miscInfo.equals("A")) {
						adminSession = true;
					}else{
						adminSession = false;
					}
					//display which account is logged in
					System.out.println(trans.accountName + " Session Started "+((adminSession==true) ? "Admin" : "Standard"));
				}else{
					System.out.println("Invalid Account");
				}
				break;
			default:
				System.out.println("Unknown Transaction Type???");
				break;
			}
			System.out.println(curAcc.toString(true));
		}
	}
	return accounts;
}

}
