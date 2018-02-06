package CapstoneProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

/**
 * Creates the maintenance window that deals with database maintenance
 * @author Douglas
 *
 */
public class Maintenance extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Object[] options = {"Query Database", "Show Database"};
	private Object[] databaseOptions = {"Viewing", "Insert/Drop"};


	
	String connectionString =  
            "jdbc:sqlserver://shirillacapstone.database.windows.net:1433;"
            + "database=Employees;user=shirilla@shirillacapstone;"
            + "password=Javadatabase12;"
            + "encrypt=true;"
            + "trustServerCertificate=false;"
            + "hostNameInCertificate=*.database.windows.net;"
            + "loginTimeout=30;";
	
	String db = "jdbc:ucanaccess://C:\\Users\\Douglas\\Documents\\database.accdb";
	Connection connection = null;  
	Statement statement = null;   
	ResultSet resultSet = null;  
	PreparedStatement prepsInsertProduct = null;
	
	/**
	 * Constructs the window
	 */
	public Maintenance() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnDatabase = new JButton("Database");
		btnDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showOptionDialog(contentPane, "Please make a selection below",
						"Database Options",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[1]);
				if(choice == JOptionPane.NO_OPTION){
				try{
					//connection = DriverManager.getConnection(connectionString);
					
					connection = DriverManager.getConnection(db);
					statement = connection.createStatement();
	                String query = "select * from employees;";
	                //statement = connection.createStatement();
	                resultSet = statement.executeQuery(query);
	                table.setModel(DbUtils.resultSetToTableModel(resultSet));
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
				else
				{
					int choiceTwo = JOptionPane.showOptionDialog(contentPane, "Are you viewing/adding information, or dropping information?",
							"Database Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							databaseOptions, databaseOptions[1]);
					if(choiceTwo == JOptionPane.NO_OPTION){
					try{
					String queryDatabase = JOptionPane.showInputDialog("Enter the query");
					connection = DriverManager.getConnection(db);
					statement = connection.createStatement();
					statement.executeUpdate(queryDatabase);
					JOptionPane.showMessageDialog(contentPane, "Query executed!");
					}
					catch(Exception e){
						e.printStackTrace();
						JOptionPane.showMessageDialog(contentPane,"Not a valid SQL query!", "ERROR!",
								JOptionPane.WARNING_MESSAGE);
					}
				}
					else
					{
						try{
							String qDatabase = JOptionPane.showInputDialog("Enter the query");
							connection = DriverManager.getConnection(db);
							statement = connection.createStatement();
							resultSet = statement.executeQuery(qDatabase);
							table.setModel(DbUtils.resultSetToTableModel(resultSet));
						}
						catch(Exception e){
							e.printStackTrace();
							JOptionPane.showMessageDialog(contentPane,"Not a valid SQL query!", "ERROR!",
									JOptionPane.WARNING_MESSAGE);
						}
					}
			
			}}});
		contentPane.add(btnDatabase, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
