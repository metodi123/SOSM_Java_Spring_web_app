package sosm.web.app.test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import sosm.web.app.dao.CourseDAO;
import sosm.web.app.model.Course;

public class CourseDaoTest {
	
	@Test
    public void testGetCourse() {
		CourseDAO courseDAO = new CourseDAO();
		
		Course course = new Course();
		
		try {
			course = courseDAO.getCourse(11);
		} catch (Exception e) {
			fail();
		}
		
		Assert.assertEquals(11, course.getId());
		Assert.assertEquals("ОС", course.getName());
        Assert.assertEquals("Операционни системи", course.getFullName());
        Assert.assertEquals(5, course.getSemester());
        Assert.assertEquals(null, course.getModuleName());
        Assert.assertEquals(false, course.isElective());
        Assert.assertEquals(false, course.isCourseWork());
        Assert.assertEquals(true, course.isCourseProject());
    }
	
	@Test
	public void testCreateCourse() {
		CourseDAO courseDAO = new CourseDAO();
		
		Course course = new Course();
		Course courseActual = new Course();
		
		course.setName("ООП");
		course.setFullName("Обектно-ориентираното програмиране");
		course.setSemester(6);
		course.setModuleName("ПС");
		course.setElective(false);
		course.setCourseWork(false);
		course.setCourseProject(true);
		
		try {
			courseDAO.createCourse(course);
			courseActual = courseDAO.getCourse(100);
		} catch (Exception e1) {
			fail();
		}
		
		Assert.assertEquals(course.getName(), courseActual.getName());
		Assert.assertEquals(course.getFullName(), courseActual.getFullName());
		Assert.assertEquals(course.getSemester(), courseActual.getSemester());
		Assert.assertEquals(course.getModuleName(), courseActual.getModuleName());
		Assert.assertEquals(course.isElective(), courseActual.isElective());
		Assert.assertEquals(course.isCourseWork(), courseActual.isCourseWork());
		Assert.assertEquals(course.isCourseProject(), courseActual.isCourseProject());
	}
	
	@Test
	public void testUpdateCourseInfo() {
		CourseDAO courseDAO = new CourseDAO();
		
		Course course = new Course();
		Course courseActual = new Course();
		
		course.setId(100);
		course.setName("ООП");
		course.setFullName("Обектно-ориентираното програмиране");
		course.setSemester(5);
		course.setModuleName("");
		course.setElective(false);
		course.setCourseWork(false);
		course.setCourseProject(true);
		
		try {
			courseDAO.updateCourseInfo(course);
			courseActual = courseDAO.getCourse(100);
		} catch (Exception e) {
			fail();
		}

		Assert.assertEquals(course.getName(), courseActual.getName());
		Assert.assertEquals(course.getFullName(), courseActual.getFullName());
		Assert.assertEquals(course.getSemester(), courseActual.getSemester());
		Assert.assertEquals(null, courseActual.getModuleName());
		Assert.assertEquals(course.isElective(), courseActual.isElective());
		Assert.assertEquals(course.isCourseWork(), courseActual.isCourseWork());
		Assert.assertEquals(course.isCourseProject(), courseActual.isCourseProject());
	}
	
	@Test
	public void testDeleteCourse() {
		CourseDAO courseDAO = new CourseDAO();
		
		Course courseActual = new Course();
		
		try {
			courseDAO.deleteCourse(100);
			courseActual = courseDAO.getCourse(100);
		} catch (Exception e1) {
			fail();
		}
		
		Assert.assertEquals(null, courseActual.getName());
		Assert.assertEquals(null, courseActual.getFullName());
		Assert.assertNotNull(courseActual.getSemester());
		Assert.assertEquals(null, courseActual.getModuleName());
		Assert.assertNotNull(courseActual.isElective());
		Assert.assertNotNull(courseActual.isCourseWork());
		Assert.assertNotNull(courseActual.isCourseProject());
	}

}
