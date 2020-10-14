package model;

public class UserFactory {
	private final String password;
	private final String username;
	/*
	 * Constructors
	 */
	public UserFactory(String username,String pass) {
		this.username=username;
		this.password=pass;	
	}
	public UserFactory(String username) {
		this.username=username;
		this.password=null;
	}
	
	public UserFactory() {
		
		this.username=null;
		this.password=null;
	}
	
    /*
     * Methods
     */
	
	public User getUser(String user_type) {
		if(user_type.equals("Business owner")) {//return Business owner instance.
			return new BusinessOwner(username,password);
		}
		if(user_type.equals("Customer")) { //return customer instance.
			return new Customer(username,password);
		}	
		System.out.println("Sorry, no such user type.");
		return null;
	}

}
