package sosm.web.app.service;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidStudentRequestParametersException;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.StudentRequest;

@Service
public class StudentRequestParametersValidationService {
	
	public static final int PARAMETERS_MAX_LENGTH = 38;
	public static final int FACULTY_NUMBER_PARAMETERS_LENGTH = 9;
	
	public static final int SEMESTER_MIN_VALUE = 1;
	public static final int SEMESTER_MAX_VALUE = 8;
	public static final int SCORE_MIN_VALUE = 2;
	public static final int SCORE_MAX_VALUE = 6;
	public static final int GROUP_MIN_VALUE = 1;
	public static final int GROUP_MAX_VALUE = 1000;
	public static final String selectionDefaultValue = "";
	
	public void validateStudentRequestParameters(StudentRequest studentRequest) throws InvalidStudentRequestParametersException {
		validateParameter(studentRequest.getFacutyNumber(), FACULTY_NUMBER_PARAMETERS_LENGTH);
		validateParameterRange(studentRequest.getSemester(), SEMESTER_MIN_VALUE, SEMESTER_MAX_VALUE);
		validateParameterRange(studentRequest.getScore(), SCORE_MIN_VALUE, SCORE_MAX_VALUE);
		validateParameter(studentRequest.getFirstChoice(), PARAMETERS_MAX_LENGTH);
		validateParameterAllowEmpty(studentRequest.getSecondChoice(), PARAMETERS_MAX_LENGTH);
		
		if(studentRequest.getFirstChoice().equals(null)) {
			throw new InvalidStudentRequestParametersException("First choice value is null");
		}
		else if(studentRequest.getFirstChoice().equals(selectionDefaultValue)) {
			throw new InvalidStudentRequestParametersException("First choice value is not selected");
		}
		if(studentRequest.getSecondChoice().equals(selectionDefaultValue)) {
			if(studentRequest.getThirdChoice() != selectionDefaultValue) {
				studentRequest.setSecondChoice(studentRequest.getThirdChoice());
				studentRequest.setThirdChoice(selectionDefaultValue);
			}
		}
		else {
			validateParameterAllowEmpty(studentRequest.getThirdChoice(), PARAMETERS_MAX_LENGTH);
		}
	}
	
	public void validateParameter(String parameter, int maxLength) throws InvalidStudentRequestParametersException {
		if(maxLength == FACULTY_NUMBER_PARAMETERS_LENGTH) {
			if(parameter.length() != maxLength) {
				throw new InvalidStudentRequestParametersException("Invalid parameter length");
			}
		}
		if(parameter.length() > maxLength) {
			throw new InvalidStudentRequestParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidStudentRequestParametersException("Empty parameter entered");
		}
	}
	
	public void validateParameterAllowEmpty(String parameter, int maxLength) throws InvalidStudentRequestParametersException {
		if(maxLength == FACULTY_NUMBER_PARAMETERS_LENGTH) {
			if(parameter.length() != maxLength) {
				throw new InvalidStudentRequestParametersException("Invalid parameter length");
			}
		}
		if(parameter.length() > maxLength) {
			throw new InvalidStudentRequestParametersException("Invalid parameter length");
		}
	}
	
	public void validateParameterRange(int parameter, int min, int max) throws InvalidStudentRequestParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidStudentRequestParametersException("Parameter value out of range");
		}
	}
	
	public void validateParameterRange(float parameter, float min, float max) throws InvalidStudentRequestParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidStudentRequestParametersException("Parameter value out of range");
		}
	}
	
	public void validateSelectionType(String selectionType) throws InvalidStudentRequestParametersException {
		if(!selectionType.equals(RequestSelectionType.electiveCourse.toString())) {
			throw new InvalidStudentRequestParametersException("Invalid selection type");
		}
		else if (!selectionType.equals(RequestSelectionType.courseWork.toString())) {
			throw new InvalidStudentRequestParametersException("Invalid selection type");
		}
		else if (!selectionType.equals(RequestSelectionType.courseProject.toString())) {
			throw new InvalidStudentRequestParametersException("Invalid selection type");
		}
	}
	
	public boolean containsEqualStrings(String string1, String string2, String string3) {
		if(string1.equals(string2) && !string1.equals(selectionDefaultValue) && !string2.equals(selectionDefaultValue)) {
			return true;
		}
		else if(string1.equals(string3) && !string1.equals(selectionDefaultValue) && !string3.equals(selectionDefaultValue)) {
			return true;
		}
		else if(string2.equals(string3) && !string2.equals(selectionDefaultValue) && !string3.equals(selectionDefaultValue)) {
			return true;
		}	
		else {
			return false;
		}
	}
	
	public boolean isNotSelected(String string) {
		if(string.equals(selectionDefaultValue)) {
			return true;
		}	
		else {
			return false;
		}
	}
	
}
