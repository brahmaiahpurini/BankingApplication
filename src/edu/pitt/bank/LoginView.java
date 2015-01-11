package edu.pitt.bank;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.pitt.utilities.StringUtilities;

/**
 * This class handles GUI for the login screen
 * @author Dmitriy Babichenko
 * @version 1.1
 *
 */
public class LoginView {

	private JFrame frmLoginScreen;
	private JTextField txtLoginName;
	private JTextField txtPin;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView window = new LoginView();
					window.frmLoginScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Default constructor - initializes the first screen.
	 */
	public LoginView() {
		initialize();
		//throw new ShireyException("I just got thrown!");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoginScreen = new JFrame();
		frmLoginScreen.setBounds(100, 100, 330, 180);
		frmLoginScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginScreen.setTitle("Login");
		frmLoginScreen.getContentPane().setLayout(null);
		
		JLabel lblLoginName = new JLabel("Login name:");
		lblLoginName.setBounds(19, 23, 91, 16);
		frmLoginScreen.getContentPane().add(lblLoginName);
		
		txtLoginName = new JTextField();
		txtLoginName.setBounds(112, 17, 186, 28);
		frmLoginScreen.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Pin:");
		lblPassword.setBounds(19, 68, 91, 16);
		frmLoginScreen.getContentPane().add(lblPassword);
		
		txtPin = new JTextField();
		txtPin.setBounds(112, 62, 186, 28);
		frmLoginScreen.getContentPane().add(txtPin);
		txtPin.setColumns(10);
		
		btnLogin = new JButton("Login");
		// Event handler for "Login" button
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// see video http://youtu.be/9JUrlINjv08    @ 1:00  for this code
				// halfway through the video talks about parent child views - know this - watch it over and over
				Security security = new Security();
				
				// Get user input from text fields
				String loginName = txtLoginName.getText();
				String pinString = txtPin.getText();
				
				// Check to make sure that user actually entered something in the Login name and Pin fields
				if(!loginName.equals("") && !pinString.equals("")){
					// Make sure that user entered a numeric pin number.  Note that we are calling
					// isNumeric method of StringUtilities class */
					if(StringUtilities.isNumeric(pinString)){
						// Validate user credentials 
						Customer authCustomer = security.validateLogin(txtLoginName.getText(), Integer.parseInt(pinString));
						if(authCustomer != null){
							// Create a variable/object that will store the next screen (BankView)
							// Look through the code of BankView in detail - see if you can find differences
							// between BankView constructor and LoginView constructor
							
							
							//System.out.println(authCustomer.getCustomerID());
							BankView bank = new BankView(frmLoginScreen, authCustomer);
							// Hide this screen
							frmLoginScreen.setVisible(false);
						}
						else{
							// If login name / pin combination does not exist in the database, pop-up a warning message
							JOptionPane.showMessageDialog(frmLoginScreen, "Invalid credentials - please try again!");
						}						
					}
					else{
						// If pin number is not numeric, pop-up a warning message
						JOptionPane.showMessageDialog(frmLoginScreen, "Pin number must be numeric.");
					}
				}
				else{
					// If either login name or pin fields are blank, pop-up a warning message
					JOptionPane.showMessageDialog(frmLoginScreen, "You must provide both login name and pin number in order to login");
				}
			}
		});
		btnLogin.setBounds(181, 109, 117, 29);
		frmLoginScreen.getContentPane().add(btnLogin);
	}
}