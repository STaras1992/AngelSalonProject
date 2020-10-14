package model_tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.AngelDatabase;
import model.Database;
import model.User;
import model.UserFactory;
import view.AngelView;

class databaseClas_test {
	
	@Test
	void testAddUser() {
		Database testdata=new AngelDatabase();
		testdata.addUser(new UserFactory("Moshe","123").getUser("Customer"));
		testdata.addUser(new UserFactory("Angel","456").getUser("Business owner"));
		
		assertTrue(testdata.getCustomersList().contains("Moshe"),"user exist");
		assertTrue(testdata.getBusinessOwnersList().contains("Angel"),"user exist");
		assertFalse(testdata.getCustomersList().contains("asdfsaf"),"user not exist");
	}

	@Test
	void testRemoveUser() {
		Database testdata=new AngelDatabase();
		testdata.addUser(new UserFactory("Moshe","123").getUser("Customer"));
		testdata.addUser(new UserFactory("Angel","456").getUser("Business owner"));
		
		testdata.removeUser("Moshe");
		assertTrue(testdata.getUser("Moshe") == null);
		testdata.removeUser("Angel");
		assertTrue(testdata.getUser("456") == null);	
	}
	
	@Test
	void testConfirmLogin() {
		Database testdata=new AngelDatabase();
		testdata.addUser(new UserFactory("Moshe","123").getUser("Customer"));
		testdata.addUser(new UserFactory("Angel","456").getUser("Business owner"));
		
		assertTrue(testdata.confirmLogin("Moshe","123"));
		assertTrue(testdata.confirmLogin("Angel","456"));
		assertFalse(testdata.confirmLogin("Moshe","124"));
		assertFalse(testdata.confirmLogin("asdfas","123"));
	}
	
	@Test
	void testCreateReport() {
		AngelView.currentUser="Angel";
		Database testdata=new AngelDatabase();
		testdata.addUser(new UserFactory("Moshe","123").getUser("Customer"));
		testdata.addUser(new UserFactory("Angel","456").getUser("Business owner"));
		User user=testdata.getUser("Angel");
		HashMap<String,Integer> servs=new HashMap<String, Integer>();
		servs.put("Manicure",50);
		user.setServices(servs);
		user.scheduleAppointment("17.7.19 13:00","Manicure","Moshe");
		
		try {
		assertTrue(testdata.createReport(AngelView.angelFormatDateTime.parse("16.7.19 13:00"),AngelView.angelFormatDateTime.parse("18.7.19 13:00")));
		assertTrue(testdata.createReport(AngelView.angelFormatDateTime.parse("17.7.19 12:00"),AngelView.angelFormatDateTime.parse("17.7.19 14:00")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
	
