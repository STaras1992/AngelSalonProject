package controller;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.Model;
import model.User;
import view.AngelView;
import view.View;

public class AngelController implements Controller {
	
	private Model model;
	private View view;
	/*
	 * Constructor.set model and view for Controller.
	 */
	public AngelController(Model model,View view) {
		this.model=model;
		this.view=view;
	}

    /*
     * Controller is Observer listen for model and view.When we call method firePropertyChange( PropertyChangeEvent),then we come here 
     * and do some work whit event we had.
     */

	public void propertyChange(PropertyChangeEvent evt) {
		String event=evt.getPropertyName();
		
		switch (event) {
		
		    //add new user to database.
		case "New user":
			String[] str=(String[]) evt.getNewValue(); //get data we sent in fireProperty()
			if(model.newUser(str[0],str[1],str[2])) {	//str[3]: 0=type,1=username,2=password
				view.response("New user",true);	
			}
			else
				view.response("New user",false);
			break;
			//check if login that user enters exist in database and correctness of password.
		case "Confirm sign in":
			String[] strn=(String[])evt.getNewValue();//get data we sent in fireProperty()
			User user=model.confirmSignIn(strn[0],strn[1]);  //strn[2]:[0]=user name,[1]=password
			AngelView.currentUser=strn[0];   //username signed in
			view.signInResponse(user);
			break;
			//change some users fields in database.
		case "User edit":
			if(model.editUserInfo((User)evt.getNewValue())) { //get users template with new information from view.
				view.response("User edit",true);	
			}
			else
				view.response("User edit",false);
			break;
			
			//find users that match sort criteria
		case "Refresh list":
			String[] sortFields=(String[])evt.getNewValue();//sortFields[]:[0]=region.[1]=price,[2]=service.
			ArrayList<User> businesses= model.refreshServicesList(sortFields[0],sortFields[1],sortFields[2]);
			view.refreshList(businesses);
			break;
			
			//add review to business
		case "Add review":
		   if(model.saveReview((String[])evt.getNewValue())) {
			  model.saveData(model.getFileName()); 
			  view.response("Add review",true);
		   }
		   else
			   view.response("Add review",false);
		   break;
		   
		 //Calculate rating of business 
		case "Change rating":
			if(model.changeRating((String[])evt.getNewValue())){
				model.saveData(model.getFileName());
				view.response("Change rating",true);
			}
			else
				view.response("Change rating",false);
			break;
			
			//add appointment for business and customer
		case "Schedule appointment": 
			if(model.scheduleAppointment((String[])evt.getNewValue())) {
				view.response("Schedule appointment", true);
			}
			else
				view.response("Schedule appointment", false);
			break;
			
			//remove appointment for business and customer
		case "Cancel appointment":
			if(model.cancelAppointment((String[])evt.getNewValue())) {
				model.saveData(model.getFileName());
				view.response("Cancel appointment", true);
			}
			else
				view.response("Cancel appointment", false);
			break;
			
			//Create financial report for choosed dates
		case "Create report":
			if(model.createReport((Date[])evt.getNewValue())) {
				view.response("Create report", true);
			}
			else
				view.response("Create report", false);
		   break;
		   
		   //Edit information of user
		case "Edit info":
			if(model.editInfo((HashMap<String,String>)evt.getNewValue())) {
				model.saveData(model.getFileName());
				view.response("Edit info",true);
			}
			else
				view.response("Edit info",false);
			break;
			
			//Edit services information
		case "Edit services":
			if(model.editServices((HashMap<String,Integer>)evt.getNewValue())) {
				model.saveData(model.getFileName());
				view.response("Edit servicesEdit info",true);
			}
			else
				view.response("Edit services",false);
			break;
			
			//Show appointments for user or business
		case "Show appointments":
				view.viewAppointments(model.showAppointments((String)evt.getNewValue()));
			break;
					
		default:
			break;
		   
		}		
	}
}
