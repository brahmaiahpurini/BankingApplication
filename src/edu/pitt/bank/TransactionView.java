package edu.pitt.bank;
import java.awt.Color;

// This is all taken from http://www.youtube.com/watch?v=fjX-EnqRzDM&feature=youtu.be
import java.awt.EventQueue;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.utilities.DbUtilities;

public class TransactionView {

	private JFrame frmTransactionView;
	private JScrollPane scrollPane;
	private JTable tblTransactionData;
	private JFrame parentFrame;
	
	private Bank bank;
	private String accountID;
	

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransactionView window = new TransactionView(JFrame parentFrame, Bank bank, String accountID);
					window.frmTransactionView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		}
		*/

	/**
	 * Create the application.
	 */
	public TransactionView(JFrame parentFrame, Bank bank, String accountID) {
		this.parentFrame = parentFrame;
		this.bank = bank;
		this.accountID = accountID;
		
		initialize();
		
		this.frmTransactionView.setVisible(true); // make this application window visible
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTransactionView = new JFrame();
		frmTransactionView.setBounds(100, 100, 650, 300);
		frmTransactionView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTransactionView.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, frmTransactionView.getWidth(), frmTransactionView.getHeight());
		
		DbUtilities db = new DbUtilities();
				
		String [] columnNames = new String[6];
		columnNames[0] = "Transaction ID";
		columnNames[1] = "Account Number";
		columnNames[2] = "Transaction Amount";
		columnNames[3] = "Date";
		columnNames[4] = "Remaining Balance";
		columnNames[5] = "Account Type";

		try {
			
			//right now, this SQL is going to give every transaction.  for final assignment, we will only get
			//specific transaction list (Array?) for the account that was selected
			
			DefaultTableModel dtm = db.getDataTable("SELECT * from bank1017.transaction WHERE accountID = '" + this.accountID + "';", columnNames);
			tblTransactionData = new JTable(dtm);
			tblTransactionData.setGridColor(Color.black);
			tblTransactionData.setBounds(0, 0, scrollPane.getWidth(), scrollPane.getHeight());
			tblTransactionData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			scrollPane.setViewportView(tblTransactionData);
			
			frmTransactionView.add(scrollPane);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
