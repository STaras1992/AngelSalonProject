package view;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import model.User;

public interface View {

	public void start();
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);
	public void response(String action,boolean response);
	public void refreshList(ArrayList<User> businesses);
	public void signInResponse(User user);
	public void viewAppointments(HashMap<String,String> apps);
}
