package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidCourseParametersException;
import sosm.web.app.model.Course;
import sosm.web.app.model.RequestSelectionType;

@Service
public class CourseParametersValidationService {
	
	public static final int LONG_PARAMETERS_MAX_LENGTH = 78;
	public static final int SHORT_PARAMETERS_MAX_LENGTH = 38;
	
	public static final int ID_MIN_VALUE = 1;
	public static final int ID_MAX_VALUE = 1000000;
	public static final int SEMESTER_MIN_VALUE = 1;
	public static final int SEMESTER_MAX_VALUE = 8;

	public void validateCourseParameters(Course course, boolean ignoreIdValidation) throws InvalidCourseParametersException {
		if(ignoreIdValidation == false) {
			validateParameterRange(course.getId(), ID_MIN_VALUE, ID_MAX_VALUE);
		}
		validateParameter(course.getName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(course.getFullName(), LONG_PARAMETERS_MAX_LENGTH);
		validateParameterRange(course.getSemester(), SEMESTER_MIN_VALUE, SEMESTER_MAX_VALUE);
		validateParameterAllowEmpty(course.getModuleName(), SHORT_PARAMETERS_MAX_LENGTH);
	}
	
	public void validateParameter(String parameter, int length) throws InvalidCourseParametersException {
		if(parameter.length() > length) {
			throw new InvalidCourseParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidCourseParametersException("Empty parameter entered");
		}
	}
	
	public void validateParameterAllowEmpty(String parameter, int length) throws InvalidCourseParametersException {
		if(parameter.length() > length) {
			throw new InvalidCourseParametersException("Invalid parameter length");
		}
	}
	
	public void validateParameterRange(int parameter, int min, int max) throws InvalidCourseParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidCourseParametersException("Parameter value out of range");
		}
	}
	
	public boolean isCourseValid(Course course, RequestSelectionType selectionType, int semester) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
			
		if(selectionType == RequestSelectionType.electiveCourse) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.courses WHERE name = ? AND semester = ? AND is_elective = '1'");
		}
		else if(selectionType == RequestSelectionType.courseProject) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.courses WHERE name = ? AND semester = ? AND is_course_project = '1'");
		}
		else if(selectionType == RequestSelectionType.courseWork) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.courses WHERE name = ? AND semester = ? AND is_course_work = '1'");
		}
		else {
			return false;
		}
		
		statement.setString(1, course.getName());
	    statement.setInt(2, semester);
	    
	    ResultSet result = statement.executeQuery();
	        
	    int rowCount = 0;
	    while (result.next()) {
	    	rowCount = result.getInt("count");
	    }
	    
	    if(rowCount > 0){
        	return true;
        }
        else {
        	return false;
        }
	}

}
