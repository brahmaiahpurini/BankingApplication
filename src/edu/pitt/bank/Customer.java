package edu.pitt.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import edu.pitt.utilities.DbUtilities;

/** You can retrieve information about existing customers
 * or create a new customer
 * @author mattshirey
 *
 */

public class Customer {
	private String customerID;
	private String firstName;
	private String lastName;
	private String ssn;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String loginName;
	private String pin;
	
		
	/**Constructs a Customer object from Database
	 *
	 * @param customerID
	 */
	
	public Customer(String customerID){
		String sql = "SELECT * FROM bank1017.customer "; 
		sql += "WHERE customerID = '" + customerID + "'";
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.customerID = rs.getString("customerID");
				this.firstName = rs.getString("firstName");
				this.lastName = rs.getString("lastName");
				this.ssn = rs.getString("ssn");
				this.streetAddress = rs.getString("streetAddress");
				this.city = rs.getString("city");
				this.state = rs.getString("state");
				this.zip = rs.getString("zip");
				this.loginName = rs.getString("loginName");
				this.pin = rs.getString("pin");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  finally{

              db.closeDbConnection();
		  }
	}
	
	/**Constructs a new customerID and Inserts to database
	 * 
	 * @param lastName
	 * @param firstName
	 * @param ssn
	 * @param loginName
	 * @param pin
	 */
	
	// int pin??  I made it String, was that ok?
	
	public Customer(String lastName, String firstName, String ssn, String loginName, String pin, 
			String streetAddress, String state, int zip){
		this.customerID = UUID.randomUUID().toString();
		this.lastName = lastName;
		this.firstName = firstName;
		this.ssn = ssn;
		this.loginName = loginName;
		this.pin = pin;
		
		String sql = "INSERT INTO bank1017.customer ";
		sql += "(lastName, firstName, ssn, loginName, pin) ";
		sql += " VALUES ";
		sql += "('" + this.lastName + "', ";
		sql += "'" + this.firstName + "', ";
		sql += "'" + this.ssn + "', ";
		sql += "'" + this.loginName + "' ";
		sql += "'" + this.pin + "); ";
				
		DbUtilities db = new DbUtilities();
		db.executeQuery(sql);
	}


/**
 * @return the customerID
 */
public String getCustomerID() {
	return customerID;
}

/**
 * @param customerID the customerID to set
 */
public void setCustomerID(String customerID) {
	this.customerID = customerID;
}

/**
 * @return the firstName
 */
public String getFirstName() {
	return firstName;
}

/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * @return the city
 */
public String getCity() {
	return city;
}

/**
 * @param city the city to set
 */
public void setCity(String city) {
	this.city = city;
}


}