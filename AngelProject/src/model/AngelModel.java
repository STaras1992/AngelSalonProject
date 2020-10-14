package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import view.AngelView;

public class AngelModel implements Model,Serializable {
	
	private PropertyChangeSupport property;//observer 
	private Database data;            //database
	private  String filename;    //file name of database saved in project folder.
	
	private static final long serialVersionUID = 1L;
	/*
	 * Constructor
	 */
	public AngelModel() {
		property=new PropertyChangeSupport(this);
		data=new AngelDatabase();
		filename="testDatabase";
	}
	
	/*
	 * add listeners to observer
	 */
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		property.addPropertyChangeListener(propertyChangeListener);
		
	}
	
	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	public String getFileName() {
		return filename;
	}
		
	/*
	 * Create new user with username and password you enter on Create user window.And add user to database.
	 */
	public boolean newUser(String type,String username,String password) {
		//if username exist false
			for (String user : data.getCustomersList()) {
				if(user.equals(username))
					return false;
			}		
			for (String user : data.getBusinessOwnersList()) {
				if(user.equals(username))
					return false;		
			}
		//if username not exist
		User user=new UserFactory(username,password).getUser(type);//create user whit username and password
		if(user==null) return false;//illegal type
		data.addUser(user);//add to database
		saveData(filename);//save edited database
		return true;
	}
  
	/*
	 * Check username and password we enter on login.
	 */
	public User confirmSignIn(String username, String password) {	
        if(data.confirmLogin(username,password))//if exist username and pass correct
			return data.getUser(username);
		else
			return null;	
	}
	
	/*
	 * Setup  user fields information.New info come from data we enter on edit user window.
	 */
	public boolean editUserInfo(User info) {

		User user=data.getUser(info.getUsername());//get instance of user ,if exist.	
		
		if(user==null)
			return false;
		
		//copy new information to user data
		if(info.getType().equals("Customer")) {
			user.setFirst_name(info.getFirst_name());
			user.setLast_name(info.getLast_name());
			user.setAge(info.getAge());
			user.setRegion(info.getRegion());
			user.setCity(info.getCity());
			user.setMail(info.getMail());
			saveData(filename);
			return true;
		}
		else
			if(info.getType().equals("Business owner")) {
				user.setBusiness_name(info.getBusiness_name());
				user.setRegion(info.getRegion());
				user.setAdress(info.getAdress());
				user.setPhone_number(info.getPhone_number());
				user.setSocial(info.getSocial());
				user.setOpenning_hours(info.getOpenning_hours());
				user.setServices(info.getServices());
				user.setMail(info.getMail());
				saveData(filename);
				return true;				
			}	
			else
		        return false;		
	}
    
    /*
     * find all users(businesses) that match all sort chooses (region,price,service),and return list of businesses
     */
	public ArrayList<User> refreshServicesList(String region, String price, String service) {
		ArrayList<User> listOfBusinesses=new ArrayList<User>();//list for return
		ArrayList<String> listOfAllBusinessOwners=data.getBusinessOwnersList();//list of all businesses in database
		
		String[] split;
		int from=0;
		int to=300;
		//if price equals "all" so there was no matches for sort user choose.Else split string of price limits.
		if(!price.equals("all")) {
		split=price.split("-");
		from=Integer.parseInt(split[0]);
		to=Integer.parseInt(split[1]);
		}
		//for each business check if all parameters match user choice.
		for (String username : listOfAllBusinessOwners) {
			User user=data.getUser(username);
			HashMap<String,Integer> userServices=user.getServices();
			if(user.getRegion().equals(region)) {
				if(price.equals("all")&&service.equals("all"))//if matched was not found,find business match only region.
					listOfBusinesses.add(user);
				else {//check for service matches
				for (Map.Entry<String, Integer> entry : userServices.entrySet()) {
				    if(entry.getKey().equals(service)||service.equals("all"))
				    	if((entry.getValue()>=from && entry.getValue()<=to) || price.equals("all")) {
				    		listOfBusinesses.add(user);
				    		break;
				    	}
				}
				}
				    		
			}
		}		
		return listOfBusinesses;	
	}

	/*
	 * Create report and save in project folder
	 */
	public boolean createReport(Date[] dates) {
		if(data.createReport(dates[0], dates[1]))
			return true;
		else
			return false;
		
	}
	/*
	 * save review on business database
	 */
	public boolean saveReview(String[] reviewToUser) {
		User user=data.getUser(reviewToUser[0]);
		if(user==null)
			return false;
		user.setReview(reviewToUser[1]);
		return true;
		
	}
	
	/*
	 * calculate new rating for business
	 */
	public boolean changeRating(String[] rateUser) {
		User user=data.getUser(rateUser[0]);
		if(user==null)
			return false;
		user.setRating((double)Integer.parseInt(rateUser[1]));
		return true;
	}
	
	/*
	 * Schedule appointment,write it in user database..app[4]: [0]=business,[1]=date,[2]service,[3]=customer
	 */
	public boolean scheduleAppointment(String[] app) {
		User business=data.getUser(app[0]);
		User customer=data.getUser(app[3]);
		//schedule appointments for choosed business and for customer that choose the appointment.
		if(business!=null&&customer!=null) {
		business.scheduleAppointment(app[1],app[2],app[3]);
		customer.scheduleAppointment(app[1],app[2],app[0]);	
		saveData(filename);//save edited data
		return true;
		}
		else
			return false;
	}
	
	/*
	 * cancel appointment for customer and business also.app[2]:[0]=username,[1]=date.
	 */
	public boolean cancelAppointment(String[] app) {
		User user=data.getUser(app[0]);//get user (business /customer) was click the cancel appointment button
		HashMap<String,String> list=user.getAppointmentsMap();//get appointments list (date-business)
		User user2=data.getUser(list.get(app[1]));//get user from list of apppointments whit same date.
         //remove appointment for both business and customer
		if(user!=null && user2!=null) {
		    user.removeAppointment(app[1]);
		    user2.removeAppointment(app[1]);
		    return true;
		}
		else
			return false;
	}
	/*
	 * return appointments list
	 */
	public HashMap<String,String> showAppointments(String username){
		return data.getUser(username).getAppointments();
	}
	/*
	 * edit information for specific user
	 */
	public boolean editInfo(HashMap<String,String> newInfo) {
		User user=data.getUser(AngelView.currentUser);//user that click the edit button (user logged in).
		
		if (user==null)
			return false;
		//edit information 
		for (Map.Entry<String,String> entry : newInfo.entrySet()) {
			if(entry.getKey().equals("business name"))
				user.setBusiness_name(entry.getValue());
			else
			if(entry.getKey().equals("adress"))
				user.setAdress(entry.getValue());
			else
			if(entry.getKey().equals("mail"))
				user.setMail(entry.getValue());
			else
			if(entry.getKey().equals("social"))
				user.setSocial(entry.getValue());
			else
			if(entry.getKey().equals("openning hours"))
				user.setOpenning_hours(entry.getValue());
			else
			if(entry.getKey().equals("phone number"))
				user.setPhone_number(entry.getValue());				
		}
		return true;
	}
	/*
	 * Save new services for specific user.added,removed,changed price.
	 */
	public boolean editServices(HashMap<String,Integer> newServices) {
		User user=data.getUser(AngelView.currentUser);
		if(user==null)
			return false;
		user.setServices(newServices);
		return true;		
	}
    /*
     * save database object to "Angel_database" file.
     */
	public boolean saveData(String filename) {
		ObjectOutputStream objOutput=null;
		try {
			objOutput=new ObjectOutputStream(new FileOutputStream(filename));
			objOutput.writeObject(data);  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
    finally
    {
 	   if (objOutput!= null)
			try {
				objOutput.close();
			} catch (IOException e) {					
				e.printStackTrace();
				return false;
			}  
    }
		return true;
	}

	//Load Database from file "Angel_Database"
	public boolean loadData(String filename) {
		ObjectInputStream objInput=null;
		 try {
			objInput = new ObjectInputStream(new FileInputStream(filename));
			this.data=(AngelDatabase)objInput.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	      finally
	      {
	    	  if (objInput != null)
				try {
					objInput.close();
				} catch (IOException e) {				
					e.printStackTrace();
					return false;
				}
	      }
		 return true;
	}
}
