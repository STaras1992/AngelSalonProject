package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface Model {
	
	public boolean newUser(String type,String username,String password);
	public User confirmSignIn(String username,String password);
	public boolean editUserInfo(User info);
	public ArrayList<User> refreshServicesList(String region,String price,String service);
	public void  setFileName(String filename);
	public String getFileName();
	public boolean saveReview(String[] reviewToUser);
	public boolean changeRating(String[] rateUser);
	public boolean scheduleAppointment(String[] app);
	public boolean cancelAppointment(String[] app);
	public boolean createReport(Date[] dates);
	public boolean editInfo(HashMap<String,String> newInfo);
	public boolean editServices(HashMap<String,Integer> newServices);
	public boolean saveData(String filename);
	public boolean loadData(String filename);
	public HashMap<String,String> showAppointments(String newValue);

}
