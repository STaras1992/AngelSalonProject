package model;

import java.util.ArrayList;
import java.util.HashMap;

public interface User {
	
	/*
	 * Common getters:
	 */
	public String getUsername();
	public String getType();
	public String getRegion();
	public String getMail();
	public HashMap<String,String> getAppointmentsMap();
	public HashMap<String,String> getAppointments();
	
	/*
	 * Common setters:
	 */
	
	public void setRegion(String region);
	public void setMail(String mail);
	/*
	 * Customer getters/setters:
	 */
	public int getAge() ;
	public String getCity();
	public String getFirst_name();
	public String getLast_name();
	public void setAge(int age);
	public void setCity(String city);
	public void setFirst_name(String first_name);
	public void setLast_name(String last_name);

	/*
	 * Business owner getters/setters
	 */
	public ArrayList<String> getReviews();
	public String getAdress();
	public String getBusiness_name();
	public String getOpenning_hours();
	public String getPhone_number();
	public HashMap<String,Integer> getServices();
	public Integer getServicePrice(String service);
	public String getSocial();
	public double getRating();
	public void setReview(String review);
	public void setAdress(String adress);
	public void setBusiness_name(String business_name); 
	public void setOpenning_hours(String openning_hours); 
	public void setPhone_number(String phone_number);
	public void setServices(HashMap<String,Integer> services); 
	public void setSocial(String social); 
	public void setRating(double rating);
	
	/*
	 * methods for user.
	 */
	public boolean checkAppointmentFree(String date);
	public boolean confirmPassword(String pass);
	public void scheduleAppointment(String date,String service,String user);
	public void removeAppointment(String date);
	
}
