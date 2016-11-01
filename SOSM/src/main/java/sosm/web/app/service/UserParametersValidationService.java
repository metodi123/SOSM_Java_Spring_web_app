package sosm.web.app.service;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;

@Service
public class UserParametersValidationService {
	
	public static final int LONG_PARAMETERS_MAX_LENGTH = 33;
	public static final int SHORT_PARAMETERS_MAX_LENGTH = 23;
	public static final int FACULTY_NUMBER_PARAMETERS_LENGTH = 9;
	
	public static final int SEMESTER_MIN_VALUE = 1;
	public static final int SEMESTER_MAX_VALUE = 8;
	public static final int GPA_MIN_VALUE = 2;
	public static final int GPA_MAX_VALUE = 6;
	public static final int GROUP_MIN_VALUE = 1;
	public static final int GROUP_MAX_VALUE = 1000;
	
	public void validateUserParameters(Student student, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(student.getUsername(), FACULTY_NUMBER_PARAMETERS_LENGTH);
			validateParameter(student.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(student.getFacultyNumber(), FACULTY_NUMBER_PARAMETERS_LENGTH);
		validateParameter(student.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(student.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameterAllowEmpty(student.getModule(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(student.getEmail(), LONG_PARAMETERS_MAX_LENGTH);
		
		if(ignoreUsernameAndPasswordValidation == false) {
			if(!student.getUsername().equals(student.getFacultyNumber())) {
				throw new InvalidUserParametersException("Invalid username parameter");
			}
		}
		validateParameterRange(student.getCurrentSemester(), SEMESTER_MIN_VALUE, SEMESTER_MAX_VALUE);
		validateParameterRange(student.getGPA(), GPA_MIN_VALUE, GPA_MAX_VALUE);
		validateParameterRange(student.getGroup(), GROUP_MIN_VALUE, GROUP_MAX_VALUE);
	}
	
	public void validateUserParameters(Employee employee, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(employee.getUsername(), LONG_PARAMETERS_MAX_LENGTH);
			validateParameter(employee.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(employee.getNickname(), LONG_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(employee.getEmail(), LONG_PARAMETERS_MAX_LENGTH);
	}
	
	public void validateUserParameters(Admin admin, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(admin.getUsername(), LONG_PARAMETERS_MAX_LENGTH);
			validateParameter(admin.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(admin.getNickname(), LONG_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getEmail(), LONG_PARAMETERS_MAX_LENGTH);
	}
	
	public void validateParameter(String parameter, int maxLength) throws InvalidUserParametersException {
		if(maxLength == FACULTY_NUMBER_PARAMETERS_LENGTH) {
			if(parameter.length() != maxLength) {
				throw new InvalidUserParametersException("Invalid parameter length");
			}
			checkIfNumber(parameter);
		}
		if(parameter.length() > maxLength) {
			throw new InvalidUserParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidUserParametersException("Empty parameter entered");
		}
	}
	
	public void validateParameterAllowEmpty(String parameter, int maxLength) throws InvalidUserParametersException {
		if(maxLength == FACULTY_NUMBER_PARAMETERS_LENGTH) {
			if(parameter.length() != maxLength) {
				throw new InvalidUserParametersException("Invalid parameter length");
			}
		}
		if(parameter.length() > maxLength) {
			throw new InvalidUserParametersException("Invalid parameter length");
		}
	}
	
	public void validateParameterRange(int parameter, int min, int max) throws InvalidUserParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidUserParametersException("Parameter value out of range");
		}
	}
	
	public void validateParameterRange(float parameter, float min, float max) throws InvalidUserParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidUserParametersException("Parameter value out of range");
		}
	}
	
	private void checkIfNumber(String string) throws NumberFormatException {
		int i = Integer.parseInt(string);
	}
	
}
