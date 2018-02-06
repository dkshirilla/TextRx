package CapstoneProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

/**
 * Creates a gui window to show selected employees and allows the sending
 * of SMS message
 * @author Douglas
 *
 */
public class TextWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static String firstName;
	private static String phoneNumber;


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
 * Constructor for building TextWindow object
 */
public TextWindow() {
	
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	contentPane.setLayout(new BorderLayout(0, 0));
	setContentPane(contentPane);
		
		JButton btnSendText = new JButton("Send Text");
		btnSendText.addActionListener(new ActionListener() {
			/**
			 * method to filter through action events
			 */
			public void actionPerformed(ActionEvent arg0) {
				TableModel tm = table.getModel();
				for(int i = 0;i < tm.getRowCount();i++){
					for(int j=0;j<tm.getColumnCount();j++){
						
						Object o = tm.getValueAt(i,j);
						if(o instanceof String && (j%2==0)){
							firstName = (String)o;
						}
						else {
							phoneNumber = //Long.toString((long) o);
									(String)o;
									
						}
						
					}
				
					SendSMS sendMessage = new SendSMS();
				}
				JOptionPane.showMessageDialog(null, "TextRx has been sent!");
				
			}
		});
		contentPane.add(btnSendText, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String query = GUI.getQuery();
		try{
			connection = DriverManager.getConnection(db);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

/**
 * gets the first name from the database
 * @return database object as a string
 */
	
	public static String getFirstName(){
		return firstName;
	}
	
	/**
	 * gets employee phone number from database
	 * @return phone number as a string
	 */
	
	public static String getPhoneNumber(){
		return phoneNumber;
	}

}
