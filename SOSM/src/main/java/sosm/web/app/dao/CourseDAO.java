package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidCourseParametersException;
import sosm.web.app.model.Course;
import sosm.web.app.service.CourseParametersValidationService;
import sosm.web.app.service.DatabaseConnectionService;

@Repository
public class CourseDAO implements CourseDataAccess {

	@Override
	public Course getCourse(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectCourse = connection.prepareStatement("SELECT * FROM app_database.courses WHERE id = ?");

        statementSelectCourse.setInt(1, id);
        
        ResultSet result = statementSelectCourse.executeQuery();
        
        Course course = new Course();
        
        while (result.next()) {
        	course.setId(result.getInt("id"));
        	course.setName(result.getString("name"));
        	course.setFullName(result.getString("full_name"));
        	course.setSemester(result.getInt("semester"));
        	course.setModuleName(result.getString("module_name"));
        	course.setElective(result.getBoolean("is_elective"));
        	course.setCourseWork(result.getBoolean("is_course_work"));
        	course.setCourseProject(result.getBoolean("is_course_project"));
        }
        
		return course;
	}

	@Override
	public List<Course> getAllCourses() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectCourses = connection.prepareStatement("SELECT * FROM app_database.courses");
        
        ResultSet result = statementSelectCourses.executeQuery();
        
        List<Course> courses = new ArrayList<Course>();
        
        while (result.next()) {
        	Course course = new Course();
        	course.setId(result.getInt("id"));
        	course.setName(result.getString("name"));
        	course.setFullName(result.getString("full_name"));
        	course.setSemester(result.getInt("semester"));
        	course.setModuleName(result.getString("module_name"));
        	course.setElective(result.getBoolean("is_elective"));
        	course.setCourseWork(result.getBoolean("is_course_work"));
        	course.setCourseProject(result.getBoolean("is_course_project"));
	        courses.add(course);
        }
		return courses;
	}

	@Override
	public void createCourse(Course course) throws InvalidCourseParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		CourseParametersValidationService courseParametersValidationService = new CourseParametersValidationService();
		
		courseParametersValidationService.validateCourseParameters(course, true);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementInsertIntoCourses = connection.prepareStatement("INSERT INTO `app_database`.`courses` (`name`, `full_name`, `semester`, `module_name`, `is_elective`, `is_course_work`, `is_course_project`) VALUES (?, ?, ?, ?, ?, ?, ?)");
        
		statementInsertIntoCourses.setString(1, course.getName());
		statementInsertIntoCourses.setString(2, course.getFullName());
		statementInsertIntoCourses.setInt(3, course.getSemester());
		if(course.getModuleName().isEmpty()) {
			statementInsertIntoCourses.setString(4, null);
		}
		else {
			statementInsertIntoCourses.setString(4, course.getModuleName());
		}
		if(!course.isElective()) {
			statementInsertIntoCourses.setNull(5, java.sql.Types.TINYINT);
		}
		else {
			statementInsertIntoCourses.setBoolean(5, course.isElective());
		}
		if(!course.isCourseWork()) {
			statementInsertIntoCourses.setNull(6, java.sql.Types.TINYINT);
		}
		else {
			statementInsertIntoCourses.setBoolean(6, course.isCourseWork());
		}
		if(!course.isCourseProject()) {
			statementInsertIntoCourses.setNull(7, java.sql.Types.TINYINT);
		}
		else {
			statementInsertIntoCourses.setBoolean(7, course.isCourseProject());
		}
        statementInsertIntoCourses.executeUpdate();
	}
	
	@Override
	public void updateCourseInfo(Course course) throws InvalidCourseParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		CourseParametersValidationService courseParametersValidationService = new CourseParametersValidationService();
		
		courseParametersValidationService.validateCourseParameters(course, false);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementUpdateCourse = connection.prepareStatement("UPDATE `app_database`.`courses` SET `name`= ?, `full_name`= ?, `semester`= ?, `module_name`= ?, `is_elective`= ?, `is_course_work`= ?, `is_course_project`=? WHERE `id`= ?");
        
		statementUpdateCourse.setString(1, course.getName());
		statementUpdateCourse.setString(2, course.getFullName());
		statementUpdateCourse.setInt(3, course.getSemester());
		if(course.getModuleName().isEmpty()) {
			statementUpdateCourse.setString(4, null);
		}
		else {
			statementUpdateCourse.setString(4, course.getModuleName());
		}
		if(!course.isElective()) {
			statementUpdateCourse.setNull(5, java.sql.Types.TINYINT);
		}
		else {
			statementUpdateCourse.setBoolean(5, course.isElective());
		}
		if(!course.isCourseWork()) {
			statementUpdateCourse.setNull(6, java.sql.Types.TINYINT);
		}
		else {
			statementUpdateCourse.setBoolean(6, course.isCourseWork());
		}
		if(!course.isCourseProject()) {
			statementUpdateCourse.setNull(7, java.sql.Types.TINYINT);
		}
		else {
			statementUpdateCourse.setBoolean(7, course.isCourseProject());
		}
		statementUpdateCourse.setInt(8, course.getId());

		statementUpdateCourse.executeUpdate();
	}

	@Override
	public void deleteCourse(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementDeleteCourse = connection.prepareStatement("DELETE FROM `app_database`.`courses` WHERE `id`= ?");
        
        statementDeleteCourse.setInt(1, id);
        
        statementDeleteCourse.executeUpdate();
	}
	
}
