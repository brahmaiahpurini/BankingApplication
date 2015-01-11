package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;

public class Transaction {
	private String transactionID;
	private String accountID;
	private String type;
	private double amount;
	private double balance;
	private Date transactionDate; 
	
	public String getTransactionID()
    {
        return this.transactionID;
    }
    
    public Transaction(String transactionID, String accountID, String type, double amount, double balance, Date transactionDate)
    {
        this.accountID = accountID;
        this.transactionID = transactionID;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.transactionDate = transactionDate;
    }

	
	/** Retrieves data from a previous transaction from the Database
	 * 
	 * @param transactionID
	 * 
	 */
	
	public Transaction(String transactionID){
		String sql = "SELECT * FROM bank1017.transaction "; 
		sql += "WHERE transactionID = '" + transactionID + "'";
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.transactionID = rs.getString("transactionID");
				this.accountID = rs.getString("accountID");
				this.type = rs.getString("type");
				this.amount = rs.getDouble("amount");
				this.balance = rs.getDouble("balance");
				this.transactionDate = new Date();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  finally{

              db.closeDbConnection();

		  }

}
	
	/** Creates a new transaction and Inserts the values to the database
	 * 
	 * @param accountID
	 * @param type
	 * @param amount
	 * @param balance
	 */
	
	public Transaction(String accountID, String type, double amount, double balance){
		this.transactionID = UUID.randomUUID().toString();
		this.type = type;
		this.amount = amount;
		this.accountID = accountID;
		this.balance = balance;
		
		String sql = "INSERT INTO bank1017.transaction ";
		sql += "(transactionID, accountID, amount, transactionDate, type, balance) ";
		sql += " VALUES ";
		sql += "('" + this.transactionID + "', ";
		sql += "'" + this.accountID + "', ";
		sql += amount + ", ";
		sql += "CURDATE(), ";
		sql += "'" + this.type + "', ";
		sql += balance + ");";
		
				
		DbUtilities db = new DbUtilities();
		db.executeQuery(sql);
	}	

}
