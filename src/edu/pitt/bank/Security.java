package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.pitt.utilities.DbUtilities;

/**
 * Provides methods for: 
 * 1. Validating users during login process 
 * @author Dmitriy Babichenko
 * @version 1.1
 */
public class Security {
	/**
	 * Checks a user's login name and password against a database table.
	 * @param loginName - user's login name
	 * @param pinNumber - user's pin number
	 * @return true if login name and password exist in the database and match, false if they don't exist or don't match
	 */
	public Customer validateLogin(String loginName, int pinNumber){
		// Instantiate DbUitilities object
		DbUtilities db = new DbUtilities();
		
		// Build an SQL query string - concatenate loginName and pinNumber parameters with the query statement
		String sql = "SELECT * FROM bank1017.customer WHERE loginName = '" + loginName + "' AND pin = " + pinNumber + ";";
		
		// Note the try/catch block - we use try/catch for error handling
		try {
			ResultSet rs = db.getResultSet(sql);
			/* By default, when a ResultSet object is created, the cursor is placed before the first record.
			 * First time you call rs.next(), it actually moves the cursor to the first record in the data set.
			 * In this case, we use rs.next() to check if the first record even exists.  If it doesn't,
			 * that means that we have an empty ResultSet - our query did not return any records/rows.
			 */			
			
			if(rs.next()){
				Customer authenicatedCustomer = new Customer(rs.getString("customerID"));
				return authenicatedCustomer;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
