package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class BusinessOwner implements User,Serializable {
	
	private static final long serialVersionUID = 1L;
	private final String type;
	private final String password;
	private final String username;
	private String business_name;
	private String region;
	private String adress;
	private String mail;
	private String phone_number;
	private String openning_hours;
	private HashMap<String,Integer> services;           
	private String social;
	private ArrayList<String> reviews;
	private double rating; 
	private int numOfRates;
	private HashMap<String,String> appointments=new HashMap<String,String>();//date,service
	private HashMap<String,String> appointmentsMap=new HashMap<String,String>();//date,username(of business)
	/*
	 * Constructor.set final username and password,and put empty values to other fields of info..
	 */
	public BusinessOwner(String username,String pass) {
		this.type="Business owner";
		this.password=pass;
		this.username=username;
		business_name="";
		region="";
		adress="";
		phone_number="";
		openning_hours="";
		services=new HashMap<String, Integer>();
		social="";
		reviews=new ArrayList<String>();
		rating=0;
		numOfRates=0;
		appointments=new HashMap<String,String>();
		appointmentsMap=new HashMap<String,String>();
	}
	
	/*
     * Business owner getters:
	 */
	
	public String getUsername() {
		return username;
	}

	public String getType() {
		return type;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getAdress() {
		return adress;
	}
	
	public String getBusiness_name() {
		return business_name;
	}
	
	public String getOpenning_hours() {
		return openning_hours;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	
	public HashMap<String,Integer> getServices() {
		return services;
	}
	
	public String getSocial() {
		return social;
	}
	
	public String getMail() {
		return mail;
	}
	
	public ArrayList<String> getReviews() {
		return reviews;
	}
	
	public double getRating() {
		return rating;
	}
	
	public HashMap<String,String> getAppointments(){
		return appointments;
	}
	
	public HashMap<String,String> getAppointmentsMap(){
		return appointmentsMap;
	}
	
	
	
    /*
     * Business owner setters:
     */
	public void setRegion(String region) {
		this.region=region;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	
	public void setOpenning_hours(String openning_hours) {
		this.openning_hours = openning_hours;
	}
	
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public void setServices(HashMap<String,Integer> services) {
		this.services = new HashMap<String,Integer>(services);
	}
	
	public void setSocial(String social) {
		this.social = social;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Override
	public void setReview(String review) {
		reviews.add(review);
		
	}
	
	public void setRating(double rating) {
		++numOfRates;
		this.rating =(this.rating+rating)/numOfRates;
	}
	
	/*
     * Getters and setters of Customer instance.Not in use for Business owner!
	 */
	
	@Override
	public int getAge() {
		return 0;
	}
	
	@Override
	public String getCity() {
		return null;
	}
	
	@Override
	public String getFirst_name() {
		return null;
	}
	
	@Override
	public String getLast_name() {
		return null;
	}
	
	@Override
	public void setAge(int age) {
		
	}
	@Override
	public void setCity(String city) {
		
	}
	@Override
	public void setFirst_name(String first_name) {
		
	}
	@Override
	public void setLast_name(String last_name) {
		
	}
     
	/*
     *Other methods:
	 */
	
	//confirm password for current user.Database use that method for login check.
	@Override
	public boolean confirmPassword(String pass) {
		if(pass.equals(this.password))
			return true;
		else
		    return false;
	}
	
	/*
	 * Check if appointment date is free
	 */
	public boolean checkAppointmentFree(String date) {
		if(appointments.containsKey(date))
			return false;
		else
			return true;
		
	}
	
	/*
	 * write appointment to data
	 */
	public void scheduleAppointment(String date,String service,String user) {
		appointments.put(date,service);
		appointmentsMap.put(date,user);
	}
	

	/*
	 * remove appointment from data
	 */
	public void removeAppointment(String date) {
		appointments.remove(date);
		appointmentsMap.remove(date);
	}
	
	/*
	 * get price of service
	 */
	public Integer getServicePrice(String service) {
		return services.get(service);
	}

}
