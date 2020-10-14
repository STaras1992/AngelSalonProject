package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Customer implements User,Serializable {
	
	private final String type;
	private final String username;
	private final String password;
	private String first_name;
	private String last_name;
	private int age;
	private String region;
	private String city;
	private String mail;
	private HashMap<String,String> appointments;//date,service
	private HashMap<String,String> appointmentsMap;//date,username (business)
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructor.set final username and password,and put empty values to other fields of info..
	 */
	public  Customer(String username,String pass) {
		this.type="Customer";
		this.username=username;
		this.password=pass;
		first_name="";
		last_name="";
		age=0;
		region="";
		city="";	
		appointments=new HashMap<String,String>();
		appointmentsMap=new HashMap<String,String>();
	}
	
	/*
     * Customer getters:
	 */
	
	public String getUsername() {
		return username;
	}
	
	public String getType() {
		return type;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getMail() {
		return mail;
	}
	
	public HashMap<String,String> getAppointments(){
		return appointments;
	}
	
	@Override
	public HashMap<String, String> getAppointmentsMap() {
		return appointmentsMap;
	}
	
	
    /*
     * Customer setters:
     */
	
	public void setAge(int age) {
		this.age = age;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	@Override
	public void setMail(String mail) {
		this.mail=mail;		
	}

	/*
     * Getters and setters of Business owner instance.Not in use for Customer!
	 */
	@Override
	public String getAdress() {
		return null;
	}
	@Override
	public String getBusiness_name() {
		return null;
	}
	@Override
	public String getOpenning_hours() {
		return null;
	}
	@Override
	public String getPhone_number() {
		return null;
	}

	@Override
	public HashMap<String,Integer> getServices() {
		return null;
	}
	@Override
	public String getSocial() {
		return null;
	}
	@Override
	public void setAdress(String adress) {
		
	}
	@Override
	public void setBusiness_name(String business_name) {
		
	}
	@Override
	public void setOpenning_hours(String openning_hours) {
		
	}
	@Override
	public void setPhone_number(String phone_number) {
		
	}
	
	@Override
	public void setServices(HashMap<String,Integer> services) {
		
	}

	public void setSocial(String social) {
		
	}
	

	public ArrayList<String> getReviews() {
		return null;
	}
	@Override
	public void setReview(String review) {		
	}
	@Override
	public double getRating() {
		return 0;
	}
	@Override
	public void setRating(double rating) {
		
	}
	
	public Integer getServicePrice(String service) {
		return null;
	}
	
	
	/*
     *
     *Other methods:
     *
	 */
	
	/*
	 * confirm password for current user.Database use that method for login check.
	 */
	public boolean confirmPassword(String pass) {
		if(pass.equals(this.password))
			return true;
		else
		    return false;
	}

	/*
	 * Check if appointemt date is free
	 */
	public boolean checkAppointmentFree(String date) {
		if(appointments.containsKey(date))
			return false;
		else
			return true;
	}

	/*
	 * Schedule appointment,save to data 
	 */
	public void scheduleAppointment(String date, String service,String user) {
		appointments.put(date,service);	
		appointmentsMap.put(date, user);
	}

	/*
	 * Remove appointment from data.
	 */
	public void removeAppointment(String date) {
		appointments.remove(date);	
		appointmentsMap.remove(date);
	}
	
	
	
	

}
