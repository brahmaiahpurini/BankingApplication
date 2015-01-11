package edu.pitt.bank;

//on Feb 14, 2014, I added a closeDb connection at the end of load accounts (see line 50).  
// I was told the variable conn was unresolved.  So, I created an object conn (see line 24, 
// and imported Connection from the java.sql library (see line 8)  Is this ok?  If so I will continue on
// with my close connections.

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import edu.pitt.utilities.DbUtilities;

public class Bank {

	private ArrayList<Customer> customerList = new ArrayList<Customer>();
	private Hashtable<String, Account> accountList = new Hashtable<String, Account>();

	Customer authenticatedCustomer;

	public Bank(Customer authenticatedCustomer) {

		this.authenticatedCustomer = authenticatedCustomer;
		loadAccounts();
		setAccountOwners();
	}

	// loadAccounts has been updated to work with HashTable as per assignment 4
	private void loadAccounts() {
		String sql = "SELECT * FROM bank1017.customer JOIN bank1017.customer_account ON customerID = fk_customerID JOIN bank1017.account ON fk_accountID = accountID WHERE customerID =  '"
				+ this.authenticatedCustomer.getCustomerID() + "'";
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				String accountID = rs.getString("accountID");
				// Create account object here for each account ID
				Account a = new Account(accountID);
				accountList.put(accountID, a);
				// Add each account to customerList
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {

			db.closeDbConnection();

		}
	}

	private void setAccountOwners() {
		String sql = "SELECT * ";
		sql += "FROM bank1017.customer ";
		sql += "JOIN bank1017.customer_account ON customerID = fk_customerID ";
		sql += "JOIN bank1017.account ON fk_accountID = accountID ";
		sql += "WHERE customerID = '" + authenticatedCustomer.getCustomerID()
				+ "'";
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while (rs.next()) {
				// we need to find account ID to find the account object in the
				// array
				String accountID = rs.getString("accountID");

				// we need customerID so we can create customer object
				String customerID = rs.getString("customerID");

				// now that we have our customerID, we can create our customer
				// object

				// the customer constructor (in customer class) will retrieve
				// the data from the database
				Customer customer = new Customer(customerID);
				// Add each Customer object to the customerList Array
				customerList.add(customer);
				// Add each Customer object to accountOwners ArrayList via
				// addAccountOwner method

				// find account object reference with accountID
				Account account = this.findAccount(accountID);

				// if account is not found, it will be null
				if (account != null)
					account.addAccountOwner(customer);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			db.closeDbConnection();

		}

	}

	// this is from Assignment 1, Part 3 "Google is your friend" I apparently
	// did this wrong
	// or didnt do it at all, and my program wouldnt run. Dmitriy played with it
	// and got the program to run.
	// Study this.

	
	// public String[] getAccountNumberList(){
	// String[] accountNumberList = new String[accountList.size()];
	//
	// for(int i = 0; i<accountList.size(); i++){
	// accountNumberList[i] = accountList.get(i).getAccountID();
	// }
	//
	// return accountNumberList;  // (accountNumberList is an Array of Strings)

	// }

	// apparently that was not correct: here is what QieLie came up with:

	public String[] getAccountNumberList() {
		String[] accountNumberList = new String[accountList.size()];
		int i = 0;
		// return all the key IDS(accountID)
		Enumeration<String> e = accountList.keys();

		while (e.hasMoreElements()) {
			accountNumberList[i++] = e.nextElement();
		}

		return accountNumberList;   // (accountNumberList is an Array of Strings)
	}

	
	// in a HashTable, the "get" method performs a linear search for you.  That is why this code
	// looks different than the code for findCustomer
	public Account findAccount(String accountID) {
		return accountList.get(accountID);
	}

	private Customer findCustomer(String customerID) {
		for (Customer cust : customerList) {
			if (customerID.equalsIgnoreCase(cust.getCustomerID())) {
				return cust;
			}
		}
		return null;
	}

}