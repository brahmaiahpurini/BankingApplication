package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.DbUtilities;



public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbUtilities db = new DbUtilities();
		
		/* Test connection to your database */
		System.out.println("*****************************************");
		System.out.println("Testing database connection");
		System.out.println("*****************************************");
		String sql = "SELECT * FROM bank1017.account;";
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				System.out.println(rs.getString("accountID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Security s = new Security();
//		System.out.println("verify that security properly validates account login");
//		
//		System.out.println(s.validateLogin("nmarcus", 8125));
//		
//		System.out.println("verify that security properly INvalidates account login");
//		
//		System.out.println(s.validateLogin("Newham", 1234));
		
		
		/* Test creating transaction */
		System.out.println("*****************************************");
		System.out.println("Testing Transactions constructor");
		System.out.println("*****************************************");
		Transaction t = new Transaction("00ae9c2a-5d43-11e3-94ef-97beef767f1d", "checking", 100, 1000);
		
		/* Test creating accounts */
		System.out.println("*****************************************");
		System.out.println("Test Account constructor");
		System.out.println("*****************************************");
		Account a = new Account("690bf64c-5d42-11e3-94ef-97beef767f1d");
		System.out.println(a.getBalance());
		
		a.withdraw(50);
		System.out.println(a.getBalance());
		
		Account b = new Account("savings", 1000);
		
		
		
	}

}
