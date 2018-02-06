package CapstoneProject;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.toedter.calendar.JDateChooser;

/**
 * Represents a GUI object that creates the main container for the program
 * Extends JFrame to allow functionality
 *@author Douglas
 */
public class GUI extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	
	//declare needed variables for GUI objects
	private JButton submitButton;
	private JButton closeButton;
	private JLabel dateText;
	private JLabel contactNumber;
	private JButton maintenanceButton;
	private JTextField conNumberBox;
	private JComboBox storeNumBox;
	private JLabel storeNumLabel;
	private JComboBox shiftBox;
	private JLabel shiftLabel;
	private JLabel textRxlabel;
	private JButton clearButton;
	
	//declare needed variables for storing values
	private static String phoneNumber;
	private static Date date;
	private int dayOfweek;
	private static String shift;
	private String timeOfShift;
	private static String storeNumber;
	private JDateChooser dateChooser;
	public static String query;
	
	
	
	
	
	/**
	 * Basic constructor for GUI class
	 */
	public GUI(){
		super("TextRx");
		setLayout(new FlowLayout());
		
		
		
		theHandler handler = new theHandler();
		
		submitButton = new JButton("SUBMIT");
		submitButton.setToolTipText("Hit button to send SMS");
		submitButton.setBounds(220,400,150,50);
		submitButton.setActionCommand("submit");
		submitButton.addActionListener(handler);
		add(submitButton);
		
		closeButton = new JButton("CLOSE");
		closeButton.setToolTipText("Close Program");
		closeButton.setBounds(430,400,150,50);
		closeButton.setActionCommand("close");
		closeButton.addActionListener(handler);
		add(closeButton);
		
		clearButton = new JButton("CLEAR");
		clearButton.setToolTipText("Clear text fields for new request");
		clearButton.setBounds(430,350,150,50);
		clearButton.setActionCommand("clear");
		clearButton.addActionListener(handler);
		add(clearButton);
		
		maintenanceButton = new JButton("Maintenance");
		maintenanceButton.setToolTipText("Perform database maintenance");
		maintenanceButton.setBounds(20,400,120,50);
		maintenanceButton.setActionCommand("maintenance");
		maintenanceButton.addActionListener(handler);
		add(maintenanceButton);
		
		dateText = new JLabel("Choose date for shift");
		dateText.setBounds(235,150,200,70);
		add(dateText);
		
		contactNumber = new JLabel("Enter Contact Number");
		contactNumber.setBounds(240,210,150,70);
		add(contactNumber);
		
		textRxlabel = new JLabel("TextRx");
		textRxlabel.setBounds(230,20,150,70);
		textRxlabel.setFont(new Font("Serif", Font.PLAIN, 50));
		add(textRxlabel);
		
		conNumberBox = new JTextField();
		conNumberBox.setBounds(225, 258, 150, 30);
		add(conNumberBox);
		
		storeNumLabel = new JLabel("Select Store Number");
		storeNumLabel.setBounds(240,100,120,30);
		add(storeNumLabel);
		
		
		shiftLabel = new JLabel("Select Shift");
		shiftLabel.setBounds(259,285,100,30);
		add(shiftLabel);
		
		String[] shifts = {"6am-2pm", "2pm-10pm", "10pm-6am"};
		shiftBox = new JComboBox(shifts);
		shiftBox.setBounds(250,320,100,30);
		add(shiftBox);
		
		String[] storeNumbers = {"3930", "2802", "0998", "5815"};
		storeNumBox = new JComboBox(storeNumbers);
		storeNumBox.setBounds(245,130,100,30);
		add(storeNumBox);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(245,200,100,30);
		add(dateChooser);
		
	
	}
	
	/**
	 * 
	 * ActionListener class that handles action events
	 * 
	 *
	 */
	private class theHandler implements ActionListener{
		
		/**
		 * handles flow of action events
		 * @param e This is the action listener
		 */
		public void actionPerformed(ActionEvent e){
			
			if (e.getActionCommand().equals("close"))
			{
				dispose();
			}
			
			else if (e.getActionCommand().equals("maintenance"))
			{
				DatabaseMaintenance test = new DatabaseMaintenance();
				
			}
			
			else if(e.getActionCommand().equals("clear"))
			{
				conNumberBox.setText("");
				dateChooser.setCalendar(null);
			}
			
			else if (e.getActionCommand().equals("submit"))
				
			{
				/**
				 * Try catch block for valid user input
				 * 
				 */
				try{
				phoneNumber = conNumberBox.getText();
				storeNumber = (String) storeNumBox.getSelectedItem();
				shift = (String)shiftBox.getSelectedItem();
				date = dateChooser.getDate();
				getDayofWeek(date);
				getTimeOfShift(shift);
				queryBuilder(timeOfShift, dayOfweek);
				QueryChecker qc = new QueryChecker();
				
				}
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(null,"INVALID INPUT");
				}
			}
			
		}
	}
	
	/**
	 * Handles the conversion of date to int format to determine
	 * day of the week
	 * @param date The date of the shift that will need to be filled
	 * @return the day of the week as a value of 1-7 with 1 being Sunday
	 */
	
	public int getDayofWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		dayOfweek = calendar.get((Calendar.DAY_OF_WEEK));
		return dayOfweek;
		
	}
	
	/**
	 * Creates the scheduled date in a readable format
	 * @return the string of the scheduled date
	 */
	
	public static String getDate(){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		String scheduledDate = (String)dt.format(date);
		return scheduledDate;
		
	}
	
	/**
	 * Gets the contact number from text field
	 * @return the phone number as a string
	 */
	
	public static String getContactNumber(){
		return phoneNumber;
	}
	
	/**
	 * gets the store number for the combo box
	 * @return the store number as a string
	 */
	
	public static String getStoreNum(){
		return storeNumber;
	}
	
	/**
	 * Method to use the shift variable to determine what the time of the shift is
	 * @param shift The shift that determines if the shift is a morning, evening, or overnight
	 * @return timeOfShift as a string
	 */
	
	public String getTimeOfShift(String shift){
		if(shift.equals("6-2")){
			timeOfShift = "mrn";
			return timeOfShift;
		}
		else if(shift.equals("2-10")){
			timeOfShift = "evn";
			return timeOfShift;
		}
		else{
			timeOfShift = "ovrngt";
			return timeOfShift;
		}
	}
	
	/**
	 * Builds the query that will be used to access SQL database
	 * @param timeOfShift takes the timeOfShift to determine what time to query
	 * @param dayOfWeek used to determine what day of the week the request is for
	 * @return a built query to pass onto SQL database
	 */
	public String queryBuilder(String timeOfShift, int dayOfWeek){
		
		if ((dayOfweek == 1) && (timeOfShift.equals("mrn")) ){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SUNDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 1) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SUNDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 1) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SUNDAY_OVRNGT = 'Y'";
			return query;
		}
		else if((dayOfweek == 2) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE MONDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 2) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE MONDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 2) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE MONDAY_OVRNGT = 'Y';";
			return query;
		}
		else if((dayOfweek == 3) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE TUESDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 3) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE TUESDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 3) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE TUESDAY_OVRNGT = 'Y';";
			return query;
		}
		else if((dayOfweek == 4) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE WEDNESDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 4) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE WEDNESDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 4) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE WEDNESDAY_OVRNGT = 'Y';";
			return query;
		}
		else if((dayOfweek == 5) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE THURSDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 5) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE THURSDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 5) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE THURSDAY_OVRNGT = 'Y';";
			return query;
		}
		else if((dayOfweek == 6) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE FRIDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 6) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE FRIDAY_EVN = 'Y';";
			return query;
		}
		else if((dayOfweek == 6) && (timeOfShift.equals("ovrngt"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE FRIDAY_OVRNGT = 'Y';";
			return query;
		}
		else if((dayOfweek == 7) && (timeOfShift.equals("mrn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SATURDAY_MRN = 'Y';";
			return query;
		}
		else if((dayOfweek == 7) && (timeOfShift.equals("evn"))){
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SATURDAY_EVN = 'Y';";
			return query;
		}
		else{
			query = "SELECT FIRST_NAME, PHONE_NUMBER FROM EMPLOYEES WHERE SATURDAY_OVRNGT = 'Y';";
			return query;
		}
	}
	
	/**
	 * used to get the query for this request
	 * @return the query for this request as a string
	 */
	public static String getQuery(){
		return query;
	}
	
	/**
	 * gets the shift for the current request
	 * @return shift as a string
	 */
	public static String getShift(){
		return shift;
	}
	
	/**
	 * uses the GUI class to create the gui for the program
	 */
	static void createGUI(){
		GUI textRx = new GUI();
		textRx.setLayout(null);
		textRx.setDefaultCloseOperation(EXIT_ON_CLOSE);
		textRx.setSize(600, 500);
		textRx.setLocationRelativeTo(null);
		textRx.setResizable(false);
		textRx.setVisible(true);
		
	}

}
