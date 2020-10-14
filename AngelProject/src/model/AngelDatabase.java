package model;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import view.AngelView;

public class AngelDatabase implements Database,Serializable {
	private int numberOfUsers; //number of users in current database
	private Map<String,User> users;//map of all users instances in database
	private ArrayList<String> customers_list;//list of all user names they customers 
	private ArrayList<String> business_owners_list;//list of all user names they business owners
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * AngelDatabase constructor
	 */
	public AngelDatabase() {
		numberOfUsers=0;
		users=new HashMap<String,User>();
		customers_list=new ArrayList<String>();
		business_owners_list=new ArrayList<String>();
	}

	/*
	 * add user to database map,where username is key.
	 */
	public void addUser(User user) {
		++numberOfUsers;
		users.put(user.getUsername(),user);	
		if(user.getType().equals("Customer"))
		    customers_list.add(user.getUsername());
		else
			business_owners_list.add(user.getUsername());
	}
	
	/*
	 * remove user from database
	 */
	public void removeUser(String key) {
		users.remove(key);
		if(customers_list.contains(key))
			customers_list.remove(key);
		if(business_owners_list.contains(key))
			business_owners_list.remove(key);
	}
	
	/*
	 * return user from database,where key is username of user we want.
	 */
	public User getUser(String key) {
		return users.get(key);		
	}
	
	/*
	 * return list of customers in database.
	 */
	public ArrayList<String> getCustomersList() {
		return customers_list;
	}
	
	/*
	 * return list of business owners in database.
	 */
	public ArrayList<String> getBusinessOwnersList() {		
		return business_owners_list;
	}
	
	/*
	 * Check login we entered in login window,for user whit username we enter.if exist and password correct return true,else false.
	 */
	public boolean confirmLogin(String username,String password) {
		User user=users.get(username);
		if(user==null) //user not exist
			return false;
		if(user.confirmPassword(password))//if user exist confirm pass
			return true;
		else
			return false;		
	}
	
	/*
	 * return number of users in database
	 */
	public int getNumberOfUsers() {
		return numberOfUsers;
	}
	
	/*
	 * Create report of past appointments, for selected dates.
	 */
	public boolean createReport(Date from,Date until) {
		int total=0; //total cash
		
		String filename=new String(""+AngelView.angelFormatDate.format(from)+"-"+AngelView.angelFormatDate.format(until)+"_"+AngelView.currentUser+".txt");//"from-untill.txt"
		
		try {
		    File file=new File(filename);
			PrintWriter writer=new PrintWriter(file);
			User user=users.get(AngelView.currentUser);	//get user instance of user that call to get report.			
		    HashMap<String,String> appointments=user.getAppointments();
                //if user have appointments
				if(appointments!=null) {
				    for (Map.Entry<String,String> app: appointments.entrySet()) {//check all appointments match report dates 
				       //if appointment between report dates or equal ,write appointment information to file and add cost of service price.
					   if((AngelView.angelFormatDateTime.parse(app.getKey()).after(from) && AngelView.angelFormatDateTime.parse(app.getKey()).before(until))) {
						     writer.println("Date: "+app.getKey()+",   Price:"+user.getServicePrice(app.getValue()) );
						     total+=user.getServicePrice(app.getValue());
					   }
					   else
							if(app.getKey().equals(AngelView.angelFormatDateTime.format(from)) ||  (app.getKey().equals(AngelView.angelFormatDateTime.format(from))) ) {
						       writer.println("Date: "+app.getKey()+",   Price: "+user.getServicePrice(app.getValue())  );
						       total+=user.getServicePrice(app.getValue());
							}
					   
				    }
				}
			writer.println();
			writer.println("Total: "+total);
			writer.close();
			return true;
		} 
			catch (Exception e) {
			e.printStackTrace();
			return false;
		}	 
			
	}
}
