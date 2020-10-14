package model;

import java.util.ArrayList;
import java.util.Date;

public interface Database {
	public void addUser(User user);
	public User getUser(String key);
	public void removeUser(String key);
	public ArrayList<String> getCustomersList();
	public ArrayList<String> getBusinessOwnersList();
	public boolean confirmLogin(String username,String password);
	public boolean createReport(Date from,Date until);
	public int getNumberOfUsers();
}
