package model_tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import model.User;
import model.UserFactory;
import view.AngelView;
/*
 * 
 * User interface test.Only methods are not get/set.
 * 
 */
class userClass_test {

	@Test
	void testCheckAppointmentFree() {
		HashMap<String,Integer> service = new HashMap<String,Integer>();
		service.put("Menicure", 100);
		String date =AngelView.angelFormatTime.format(new Date());
		User businessowner = new UserFactory("Angel","123").getUser("Business owner");
		businessowner.setServices(service);
		businessowner.scheduleAppointment(date, "Manicure", "Moshe");
		assertFalse(businessowner.checkAppointmentFree(date));
		assertTrue(businessowner.checkAppointmentFree("20.03.2020 08:00"));
	}
	
	void testRemoveAppointment() {
		HashMap<String,Integer> service = new HashMap<String,Integer>();
		service.put("Menicure", 100);
		String date =AngelView.angelFormatTime.format(new Date());
		User businessowner = new UserFactory("Angel","123").getUser("Business owner");
		businessowner.setServices(service);
		businessowner.scheduleAppointment(date, "Manicure", "Moshe");
		
		businessowner.removeAppointment(date);
		assertTrue(businessowner.getAppointments().isEmpty());
		
	}
	
	@Test
	void testConfirmPassword() {
		User customer = new UserFactory("Moshe","123").getUser("Customer");
		User businessowner = new UserFactory("Angel","1234").getUser("Business owner");
		assertTrue(customer.confirmPassword("123"));
		assertTrue(businessowner.confirmPassword("1234"));
		assertFalse(customer.confirmPassword("456"));
		assertFalse(businessowner.confirmPassword("1"));
	}
	
}