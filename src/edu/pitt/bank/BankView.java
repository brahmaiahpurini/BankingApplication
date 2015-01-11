package edu.pitt.bank;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class handles GUI for the bank screen
 * @author Dmitriy Babichenko
 * @version 1.1
 *
 */
public class BankView {

	public JFrame bankView;
	private JFrame loginView;  // parent window
	private Customer authenticatedCustomer;
	/*
	 * This variable references LoginView - the screen that actually launches BankView
	 */
	private JFrame parentFrame;
	private JLabel lblSelectAccount;
	private JComboBox acctNumberDropdownBox;
	private JLabel lblIWouldLike;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnDisplayTransactions;

	private Bank bank;
	
	
	/*
	 * Default constructor - accepts the variables/object that represents parent screen
	 * (in this case it is the Login screen)
	 */
	public BankView(JFrame parentFrame, Customer authCustomer) {
		bank = new Bank(authCustomer);
		
		//this.loginView = loginView;
		System.out.println(authCustomer.getCustomerID());
		this.parentFrame = parentFrame; // set the parent application window class-level variable
		this.authenticatedCustomer = authCustomer; // set authenticatedCustomer class-level variable
		
		initialize(); // initialize the contents of the frame
		
		this.bankView.setVisible(true); // make this application window visible
		
	}	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bankView = new JFrame();
		bankView.setBounds(100, 100, 450, 300);
		bankView.setTitle("Welcome to INFSCI 1017 Bank");
		bankView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bankView.getContentPane().setLayout(null);
		
		lblSelectAccount = new JLabel("Select Account:");
		lblSelectAccount.setBounds(36, 27, 91, 23);
		bankView.getContentPane().add(lblSelectAccount);
		
		System.out.println(this.authenticatedCustomer.getCustomerID());
		
		Bank tempBank = new Bank(this.authenticatedCustomer);
		acctNumberDropdownBox = new JComboBox(bank.getAccountNumberList());
		acctNumberDropdownBox.setBounds(146, 28, 121, 23);
		bankView.getContentPane().add(acctNumberDropdownBox);	
		
		lblIWouldLike = new JLabel("I would like to: ");
		lblIWouldLike.setBounds(36, 114, 91, 14);
		bankView.getContentPane().add(lblIWouldLike);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String amount = JOptionPane.showInputDialog("Enter Deposit Amount");
				double depositAmount = Double.parseDouble(amount);	
				String accountNumber = (String) acctNumberDropdownBox.getSelectedItem();
				Account account = bank.findAccount(accountNumber);
				account.deposit(depositAmount);				
			}
		});
		btnDeposit.setBounds(146, 110, 89, 23);
		bankView.getContentPane().add(btnDeposit);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String amount = JOptionPane.showInputDialog("Enter Withdrawal Amount");
				double withdrawalAmount = Double.parseDouble(amount);	
				String accountNumber = (String) acctNumberDropdownBox.getSelectedItem();
				Account account = bank.findAccount(accountNumber);
				account.withdraw(withdrawalAmount);
				//Account a = new Account(//link accountID to Customer then code: authenticatedCustomer.getAccountID());
				//a.withdraw(withdrawalAmount);
			}	
			
		});		
		btnWithdraw.setBounds(146, 151, 89, 23);
		bankView.getContentPane().add(btnWithdraw);
		
		btnDisplayTransactions = new JButton("Display Transactions");
		btnDisplayTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TransactionView tw = new TransactionView(bankView, bank, acctNumberDropdownBox.getSelectedItem().toString());
			}
		});
		btnDisplayTransactions.setBounds(146, 213, 167, 23);
		bankView.getContentPane().add(btnDisplayTransactions);
		
		
	}
}
