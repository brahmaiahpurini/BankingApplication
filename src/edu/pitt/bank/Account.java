package edu.pitt.bank;

import java.awt.Component;		// added July 18, 2014



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JFrame;			// added July 18, 2014
import javax.swing.JOptionPane;

import edu.pitt.utilities.DbUtilities;

public class Account {
	public JFrame bankView;			// added July 18 2014
	Vector v = new Vector(10,10);  // line 174
	private String accountID;
	private String type;
	private double balance;
	private double interestRate;
	private double penalty;
	private String status;
	private Date dateOpen;
	private ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	private ArrayList<Customer> accountOwners = new ArrayList<Customer>();
	
//	public Account(String accountID){
//		String sql = "SELECT * FROM bank1017.account "; 
//		sql += "WHERE accountID = '" + accountID + "'";
//		DbUtilities db = new DbUtilities();
//		try {
//			ResultSet rs = db.getResultSet(sql);
//			while(rs.next()){
//				this.accountID = rs.getString("accountID");
//				this.type = rs.getString("type");
//				this.balance = rs.getDouble("balance");
//				this.interestRate = rs.getDouble("interestRate");
//				this.penalty = rs.getDouble("penalty");
//				this.status = rs.getString("status");
//				this.dateOpen = new Date();
//				
//			String sql3 = "SELECT * FROM bank1017.transaction "; 
//			sql3 += "WHERE accountID = '" + accountID + "'";
//			
//				ResultSet rs2 = db.getResultSet(sql3);
//				while(rs2.next()){
//					createTransaction(accountID, rs2.getString("type"), rs2.getDouble("amount"), rs2.getDouble("balance"));
//			}
//						
//		}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//everywhere you have a select, insert, delete, or update, with a try/catch, close the db connection by doing this!!!
//
//		finally{
//			db.closeDbConnection();
//		}		
	
		
	public Account(String accountID) {
        String sql = "SELECT * FROM bank1017.account ";
        sql += "WHERE accountID = '" + accountID + "'";
        DbUtilities db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet(sql);
            //while(rs.next()){
            rs.first();
            this.accountID = rs.getString("accountID");
            this.type = rs.getString("type");
            this.balance = rs.getDouble("balance");
            this.interestRate = rs.getDouble("interestRate");
            this.penalty = rs.getDouble("penalty");
            this.status = rs.getString("status");
            this.dateOpen = new Date();
            
            //System.out.println("Hello");

            String sql3 = "SELECT * FROM bank1017.transaction ";
            sql3 += "WHERE accountID = '" + accountID + "'";
		
            ResultSet rs2 = db.getResultSet(sql3);
            while (rs2.next()) {
                
                createTransaction(rs2.getString("transactionID"), rs2.getString("accountID"), rs2.getString("type"), rs2.getDouble("amount"), rs2.getDouble("balance"), rs2.getDate("transactionDate"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //everywhere you have a insert, delete, or update, with a try/catch, close the db connection by doing this!!!
        finally {
            db.closeDbConnection();
        }
    }

    public Account(String accountType, double initialBalance) {
        this.accountID = UUID.randomUUID().toString();
        this.type = accountType;
        this.balance = initialBalance;
        this.interestRate = 0;
        this.penalty = 0;
        this.status = "active";
        this.dateOpen = new Date();

        String sql = "INSERT INTO bank1017.account ";
        sql += "(accountID,type,balance,interestRate,penalty,status,dateOpen) ";
        sql += " VALUES ";
        sql += "('" + this.accountID + "', ";
        sql += "'" + this.type + "', ";
        sql += this.balance + ", ";
        sql += this.interestRate + ", ";
        sql += this.penalty + ", ";
        sql += "'" + this.status + "', ";
        sql += "CURDATE());";

        DbUtilities db = new DbUtilities();
        db.executeQuery(sql);
    }

    // 1 and 2 dont mean anything, YET
   
    public int withdraw(double amount) {
    	
        if (amount > this.balance) {  
        	JOptionPane.showMessageDialog(bankView, "You cannot withdraw more than you have available!");
            return 1;
            }
        
        
        this.balance -= amount;
        //call the createTransaction method, which will also add this trans instance to the TransactionList ArrayList
        createTransaction(this.accountID, this.type, amount, this.balance);
        updateDatabaseAccountBalance();

        return 2;

    }

    // here, if deposit is greater than $10,000.00, it returns true, but then does it continue?
    public boolean deposit(double amount) {

        if (amount < 0.00){
        	JOptionPane.showMessageDialog(bankView, "You cannot deposit a negative amount");
        	return false;
        }
    	
        else 
        	if (amount >= 10000.00) {
        	this.balance += amount;
        	createTransaction(this.accountID, this.type, amount, this.balance);
            updateDatabaseAccountBalance();  
        	JOptionPane.showMessageDialog(bankView, "Notifying IRS.......");
        	return true;        	
        	}
        	
        this.balance += amount;
        	// adds amount to the balance, returns true, creates a transaction, updates the account,  
            // PAY ATTENTION: I could have this out of order!!
            //call the createTransaction method, which will also add this trans instance to the TransactionList ArrayList
        createTransaction(this.accountID, this.type, amount, this.balance);
        updateDatabaseAccountBalance();           
       
        return false;        

    }

    private void updateDatabaseAccountBalance() {
        String sql = "UPDATE bank1017.account SET balance = " + this.balance + " ";
        sql += "WHERE accountID = '" + this.accountID + "';";

        DbUtilities db = new DbUtilities();
        db.executeQuery(sql);
    }

    // says "it is never used locally.  should I delete this method?
    private Transaction createTransaction(String transactionID) {
        Transaction t = new Transaction(transactionID);
        transactionList.add(t);
        return t;
    }

	private Transaction createTransaction(String accountID, String type, double amount, double balance){
		Transaction t = new Transaction(accountID, type, amount, balance);
		transactionList.add(t);
		return t;
	}
        
        
    private Transaction createTransaction(String transactionID, String accountID, String type, double amount, double balance, Date transactionDate) {
        Transaction t = new Transaction(transactionID, accountID, type, amount, balance, transactionDate);
        transactionList.add(t);
        return t;
    }
	
	public Vector getTransactionList(String accountID){
		String sql = "SELECT * FROM bank1017.transaction "; 
		sql += "WHERE accountID = '" + accountID + "'";
		DbUtilities db = new DbUtilities();
		return v;
		
	}
	
	public String getAccountID(){
		return this.accountID;
	}
	
	public double getBalance(){
		return this.balance;
	}
	
	
	// adds new Account Owner to accountOwners collection (arrayList)
	public void addAccountOwner(Customer accountOwner){
		this.accountOwners.add(accountOwner);	
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}


	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}


	/**
	 * @return the penalty
	 */
	public double getPenalty() {
		return penalty;
	}


	/**
	 * @param penalty the penalty to set
	 */
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the dateOpen
	 */
	public Date getDateOpen() {
		return dateOpen;
	}


	/**
	 * @param dateOpen the dateOpen to set
	 */
	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}


	/**
	 * @return the transactionList
	 */
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}


	/**
	 * @param transactionList the transactionList to set
	 */
	public void setTransactionList(ArrayList<Transaction> transactionList) {
		this.transactionList = transactionList;
	}


	/**
	 * @return the accountOwners
	 */
	public ArrayList<Customer> getAccountOwners() {
		return accountOwners;
	}


	/**
	 * @param accountOwners the accountOwners to set
	 */
	public void setAccountOwners(ArrayList<Customer> accountOwners) {
		this.accountOwners = accountOwners;
	}


	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}


	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	}

