package sosm.web.app.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;
import sosm.web.app.service.UserParametersValidationService;

public class UserParametersValidationServiceTest {

	@Test
    public void testValidateUserParametersForStudent() {
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		Student student = new Student();
		
		student.setUsername("121212150");
		student.setPassword("123456");
		student.setFacultyNumber("121212150");
		student.setFirstName("Димитър");
		student.setLastName("Тодоров");
		student.setCurrentSemester(5);
		student.setGPA(5.10f);
		student.setGroup(52);
		student.setModule("КИ");
		student.setEmail("dt@mail.com");
		
        try {
			userParametersValidationService.validateUserParameters(student, false);
		} catch (InvalidUserParametersException e1) {
			fail();
		}
	}
	
	@Test
    public void testValidateUserParametersForEmployee() {
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		Employee employee = new Employee();
		
		employee.setUsername("employee10");
		employee.setPassword("123456");
		employee.setNickname("employee10");
		employee.setFirstName("Елена");
		employee.setLastName("Петкова");
		employee.setEmail("ep@mail.com");
		
        try {
			userParametersValidationService.validateUserParameters(employee, false);
		} catch (InvalidUserParametersException e1) {
			fail();
		}
	}
	
	@Test
    public void testValidateUserParametersForAdmin() {
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		Admin admin = new Admin();
		
		admin.setUsername("admin10");
		admin.setPassword("123456");
		admin.setNickname("admin10");
		admin.setFirstName("Христо");
		admin.setLastName("Василев");
		admin.setEmail("hv@mail.com");
		admin.setMasterAdmin(false);
		
        try {
			userParametersValidationService.validateUserParameters(admin, false);
		} catch (InvalidUserParametersException e1) {
			fail();
		}
	}

}
