package CapstoneProject;

import javax.swing.JOptionPane;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 * Class that deals with connection with twilio API to send SMS message. See twilio.com for more information
 * @author Douglas
 * 
 *
 */
public class SendSMS {
		
		
			  public static final String ACCOUNT_SID = "ACce570720823abccbd904a82fd45173aa";
			  public static final String AUTH_TOKEN = "e613509a4d5f9def3ee624a06d2dcb48";
			  
			  /**
			   * constructor of SendSMS that sends the SMS
			   */
			  public SendSMS(){
			    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			    String firstName = TextWindow.getFirstName();
			    String phoneNumber = TextWindow.getPhoneNumber();
			    String contactNumber = GUI.getContactNumber();
			    String shift = GUI.getShift();
			    String date = GUI.getDate();
			    String store = GUI.getStoreNum();
			    System.out.println(phoneNumber + " " + firstName
			    		+ " " + contactNumber + " " + shift
			    		+ " " + date + " " + store);

			    
			    Message message = Message.creator(new PhoneNumber("+1"+phoneNumber),
			        new PhoneNumber("+13059129887"), 
			        "Hello " + firstName + " are you able to fill a " + shift + " shift on " + date 
			        + " at CVS store #" + store + "? If so, please call " + contactNumber + ".").create();
			   
			  }
			}


