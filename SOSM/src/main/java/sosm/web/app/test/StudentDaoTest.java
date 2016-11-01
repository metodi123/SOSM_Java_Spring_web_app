package sosm.web.app.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sosm.web.app.dao.StudentDAO;
import sosm.web.app.model.Student;

import org.junit.Assert;

public class StudentDaoTest {

	@Test
    public void testGetUser() {
		StudentDAO studentDAO = new StudentDAO();
		
		Student student = new Student();
		
		try {
			student = studentDAO.getUser("121212001");
		} catch (Exception e) {
			fail();
		}
		
		Assert.assertEquals("121212001", student.getFacultyNumber());
		Assert.assertEquals("Иван", student.getFirstName());
        Assert.assertEquals("Георгиев", student.getLastName());
        Assert.assertEquals(6, student.getCurrentSemester());
        Assert.assertEquals(5.11f, student.getGPA(), 0.00f);
        Assert.assertEquals(54, student.getGroup());
        Assert.assertEquals("ПС", student.getModule());
        Assert.assertEquals("ig@mail.com", student.getEmail());
    }
	
	@Test
	public void testCreateUser() {
		StudentDAO studentDAO = new StudentDAO();
		
		Student student = new Student();
		Student studentActual = new Student();
		
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
			studentDAO.createUser(student);
			studentActual = studentDAO.getUser("121212150");
		} catch (Exception e1) {
			fail();
		}
		
		Assert.assertEquals(student.getFacultyNumber(), studentActual.getFacultyNumber());
        Assert.assertEquals(student.getFirstName(), studentActual.getFirstName());
        Assert.assertEquals(student.getLastName(), studentActual.getLastName());
        Assert.assertEquals(student.getGPA(), studentActual.getGPA(), 0.00f);
        Assert.assertEquals(student.getGroup(), studentActual.getGroup());
        Assert.assertEquals(student.getModule(), studentActual.getModule());
        Assert.assertEquals(student.getEmail(), studentActual.getEmail());
	}
	
	@Test
	public void testUpdateStudentInfo() {
		StudentDAO studentDAO = new StudentDAO();
		
		Student student = new Student();
		Student studentActual = new Student();
		
		student.setFacultyNumber("121212150");
		student.setFirstName("Димитър");
		student.setLastName("Тодоров");
		student.setCurrentSemester(6);
		student.setGPA(5.30f);
		student.setGroup(53);
		student.setModule("КИ");
		student.setEmail("dt@mail.com");
		
		try {
			studentDAO.updateStudentInfo(student);
			studentActual = studentDAO.getUser("121212150");
		} catch (Exception e1) {
			fail();
		}
		
		Assert.assertEquals(student.getFacultyNumber(), studentActual.getFacultyNumber());
        Assert.assertEquals(student.getFirstName(), studentActual.getFirstName());
        Assert.assertEquals(student.getLastName(), studentActual.getLastName());
        Assert.assertEquals(student.getGPA(), studentActual.getGPA(), 0.00f);
        Assert.assertEquals(student.getGroup(), studentActual.getGroup());
        Assert.assertEquals(student.getModule(), studentActual.getModule());
        Assert.assertEquals(student.getEmail(), studentActual.getEmail());
	}
	
	@Test
	public void testDeleteUser() {
		StudentDAO studentDAO = new StudentDAO();
		
		Student studentActual = new Student();
		
		try {
			studentDAO.deleteUser("121212150");
			studentActual = studentDAO.getUser("121212150");
		} catch (Exception e1) {
			fail();
		}
		
		Assert.assertEquals(null, studentActual.getFacultyNumber());
		Assert.assertEquals(null, studentActual.getFirstName());
        Assert.assertEquals(null, studentActual.getLastName());
        Assert.assertNotNull(studentActual.getGPA());
        Assert.assertNotNull(studentActual.getGroup());
        Assert.assertEquals(null, studentActual.getModule());
        Assert.assertEquals(null, studentActual.getEmail());
	}
	
}
