package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.Course;

public interface CourseDataAccess {
	public Course getCourse(int id) throws Exception;
	
	public List<Course> getAllCourses()  throws Exception;
	
	public void createCourse(Course course) throws Exception;
	
	public void updateCourseInfo(Course course) throws Exception;
	
	public void deleteCourse(int id) throws Exception;
}
