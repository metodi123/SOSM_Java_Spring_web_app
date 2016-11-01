package sosm.web.app.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;
import sosm.web.app.service.PasswordProcessingService;
import sosm.web.app.service.UserValidationService;

public class UserValidationServiceTest {

	@Test
    public void testIsUserValidForStudent() {
		UserValidationService userValidationService = new UserValidationService();
		
		Student student = new Student();
		
		student.setUsername("121212001");
		student.setPassword("121212001");
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		String hashedPassword = null;
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(student.getPassword(), student.getUsername());
		} catch (Exception e1) {
			fail();
		}
		student.setPassword(hashedPassword);
		try {
			userValidationService.isUserValid(student);
		} catch (Exception e) {
			fail();
		}
    }
	
	@Test
    public void testIsUserValidForEmployee() {
		UserValidationService userValidationService = new UserValidationService();
		
		Employee employee = new Employee();
		
		employee.setUsername("employee1");
		employee.setPassword("employee1");
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		String hashedPassword = null;
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(employee.getPassword(), employee.getUsername());
		} catch (Exception e1) {
			fail();
		}
		employee.setPassword(hashedPassword);
		try {
			userValidationService.isUserValid(employee);
		} catch (Exception e) {
			fail();
		}
    }
	
	@Test
    public void testIsUserValidForAdmin() {
		UserValidationService userValidationService = new UserValidationService();
		
		Admin admin = new Admin();
		
		admin.setUsername("admin1");
		admin.setPassword("admin1");
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		String hashedPassword = null;
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(admin.getPassword(), admin.getUsername());
		} catch (Exception e1) {
			fail();
		}
		admin.setPassword(hashedPassword);
		try {
			userValidationService.isUserValid(admin);
		} catch (Exception e) {
			fail();
		}
    }

}
