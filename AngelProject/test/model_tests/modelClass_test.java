package model_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.AngelModel;
import model.Model;
import model.User;
import model.UserFactory;
import view.AngelView;

class modelClass_test {

	@Test
	void testNewUser() {
		Model testModel=new AngelModel();
		assertFalse(testModel.newUser("Illegal type","Angel", "123"),"incorrect type of user");
		assertTrue(testModel.newUser("Customer","Moshe","123"),"correct business user");
		assertTrue(testModel.newUser("Business owner","Angel","123"),"correct customer user");
		assertFalse(testModel.newUser("Customer","Angel","123"),"username already exist");	
	}
	
	@Test
	void testConfirmSignIn() {
		Model testModel = new AngelModel();
		testModel.newUser("Customer", "Moshe", "123");
		
        assertNotNull(testModel.confirmSignIn("Moshe", "123"),"correct login ");
        assertNull(testModel.confirmSignIn("Moshe", "124"),"incorrect password ");
        assertNull(testModel.confirmSignIn("Mosh", "123"),"incorrect username ");
	}
	
	@Test
	void testEditUserInfo() { 
         Model testModel =new AngelModel();
         testModel.newUser("Business owner","Angel","123");
         testModel.newUser("Customer","Moshe","123");
         User newInfoBus=new UserFactory("Angel","123").getUser("Business owner");
         User newInfoCus=new UserFactory("Moshe","123").getUser("Customer");
 		 HashMap<String,Integer> service = new HashMap<String,Integer>();
 		 service.put("Manicure", 50);
 		 
 		newInfoBus.setBusiness_name("Business_name");
 		newInfoBus.setRegion("Region");
 		newInfoBus.setAdress("Address");
 		newInfoBus.setPhone_number("Phone_number");
 		newInfoBus.setSocial("Social");
 		newInfoBus.setOpenning_hours("Openning_hours");
 		newInfoBus.setServices(service);
 		newInfoBus.setMail("Mail");
 		
 		newInfoCus.setFirst_name("First_name");
 		newInfoCus.setLast_name("Last_name");
 		newInfoCus.setAge(100);
 		newInfoCus.setRegion("Region");
 		newInfoCus.setCity("City");
 		newInfoCus.setMail("Mail");
 		
 		assertTrue(testModel.editUserInfo(newInfoBus),"correct business username");
 		assertTrue(testModel.editUserInfo(newInfoCus),"correct customer username");
 		
 		User newInfoBus2=new UserFactory("Angela").getUser("Business owner");
 		User newInfoCus2=new UserFactory("Moshele").getUser("Customer");
 		assertFalse(testModel.editUserInfo(newInfoBus2),"incorrect business username,not exist");
 		assertFalse(testModel.editUserInfo(newInfoCus2),"incorrect customer username,not exist");      
		
	}
	
	@Test
	void testRefreshServicesList() {
		Model testModel = new AngelModel();
		User user1  = new UserFactory("Angel", "123").getUser("Business owner");
		testModel.newUser("Business owner","Angel","123");
		user1.setRegion("Mercaz");
        HashMap<String,Integer> servs=new HashMap<String,Integer>();
        servs.put("Manicure",70);
        user1.setServices(servs);
        testModel.editUserInfo(user1);
        
		ArrayList<User> listOfMatches = new ArrayList<User>();
		listOfMatches=testModel.refreshServicesList("Mercaz", "51-100", "Manicure");	
		assertTrue(listOfMatches.get(0).getUsername().equals("Angel"));
		
		listOfMatches=testModel.refreshServicesList("Mercaz", "0-50", "Manicure");
		assertTrue(listOfMatches.isEmpty());
		
		listOfMatches=testModel.refreshServicesList("Mercaz", "51-100", "Build");
		assertTrue(listOfMatches.isEmpty());
		
		listOfMatches=testModel.refreshServicesList("Mercaz", "all", "Manicure");
		assertTrue(listOfMatches.get(0).getUsername().equals("Angel"));
		
		listOfMatches=testModel.refreshServicesList("Mercaz", "51-100", "all");
		assertTrue(listOfMatches.get(0).getUsername().equals("Angel"));
		
		listOfMatches=testModel.refreshServicesList("Mercaz", "all", "all");
		assertTrue(listOfMatches.get(0).getUsername().equals("Angel"));	
	}
	
	@Test
	void testSaveReview() {
		Model testModel= new AngelModel();
		testModel.newUser("Business owner", "Angel", "123");

		String[] review = new String[2];
		review[0]="Angel";
		review[1]="test review string";
		
		assertTrue(testModel.saveReview(review));
		review[0]="asdsad";
		assertFalse(testModel.saveReview(review));
	}
	
	@Test
	void testChangeRating() {
		Model testModel= new AngelModel();
		testModel.newUser("Business owner", "Angel", "123");
		
		String[] rating = new String[2];
		rating[0]="Angel";
		rating[1]="5";
		assertTrue(testModel.changeRating(rating));
		rating[0]="sadasdf";
		assertFalse(testModel.changeRating(rating));
	}
	
	@Test
	void testScheduleAppointment() {//[0]=business,[1]=date,[2]service,[3]=customer
		Model testModel = new AngelModel();
		String[] app = new String[4];
		testModel.newUser("Customer", "Moshe", "123");
		testModel.newUser("Business owner", "Angel", "1234");
		app[0]="Angel";
		app[1]="31.12.2019 12:00";
		app[2]="Manicure";
		app[3]="Moshe";
		
		assertTrue(testModel.scheduleAppointment(app));
		app[0]="asdsda";
		assertFalse(testModel.scheduleAppointment(app));
		
	}
	
	@Test
	void testcancelAppointment() {
		Model testModel = new AngelModel();
		String[] app = new String[4];
		testModel.newUser("Customer", "Moshe", "123");
		testModel.newUser("Business owner", "Angel", "1234");
		app[0]="Angel";
		app[1]="31.12.2019 12:00";
		app[2]="Manicure";
		app[3]="Moshe";
		testModel.scheduleAppointment(app);

		String[] str=new String[2];
		str[0]="Angel";
		str[1]="31.12.2019 12:00";
		assertTrue(testModel.cancelAppointment(str));
	}
	
	@Test
	void testEditInfo() {
		Model testModel = new AngelModel();
		testModel.newUser("Business owner","Angel","123");
		AngelView.currentUser="Angel";
		User user = new UserFactory("Angel","123").getUser("Business owner");
		HashMap<String,String> info = new HashMap<String,String>();
		info.put("business name", "testName");
		info.put("adress", "testAddress");
		info.put("mail", "testMail");
		info.put("social", "testSocial");
		info.put("openning hours", "testHours");
		info.put("phone number", "phone number");
		
		assertTrue(testModel.editInfo(info));
		AngelView.currentUser="sadasd173";
		assertFalse(testModel.editInfo(info));
	}
	
	@Test
	void testEditServices() {
		Model testModel = new AngelModel();
		testModel.newUser("Business owner","Angel","123");
		AngelView.currentUser="Angel";
		HashMap<String,Integer> newServices=new HashMap<String, Integer>();
		newServices.put("Manicure", 50);
		
		assertTrue(testModel.editServices(newServices));
		AngelView.currentUser="Angelasdasd";
		assertFalse(testModel.editServices(newServices));
		
	}
	
	
	@Test
	void testSaveData() {
		Model testModel = new AngelModel();
		testModel.newUser("Customer","Moshe","123");
		
		assertTrue(testModel.saveData(testModel.getFileName()));
	}
	
	@Test
	void testLoadData() {
		Model testModel = new AngelModel();
		testModel.newUser("Customer","Moshe","123");
		testModel.saveData(testModel.getFileName());
		Model testModel2=new AngelModel();
		
		assertTrue(testModel2.loadData(testModel.getFileName()));
	}
		
	@Test
	void testcreateReport() {
		Model testModel = new AngelModel();
		testModel.newUser("Business owner","Angel","123");
		AngelView.currentUser="Angel";
		Date[] date = new Date[2];
		date[0] = new Date();
		date[1] = new Date(); 
		
		assertTrue(testModel.createReport(date));
	}

}
