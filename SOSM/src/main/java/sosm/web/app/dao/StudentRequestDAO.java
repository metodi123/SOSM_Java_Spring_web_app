package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidStudentRequestParametersException;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.StudentRequest;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.StudentRequestParametersValidationService;

@Repository
public class StudentRequestDAO implements StudentRequestDataAccess {
	
	@Override
	public List<StudentRequest> getAllStudentRequests() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, UnsupportedDataTypeException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectStudentRequests = connection.prepareStatement("SELECT * FROM `requests` ORDER BY `score` DESC");
        
        ResultSet result = statementSelectStudentRequests.executeQuery();
        
        List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
        
        while (result.next()) {
        	StudentRequest studentRequest = new StudentRequest();
        	
        	studentRequest.setId(result.getInt("id"));
        	studentRequest.setFacutyNumber(result.getString("student_faculty_number"));
        	studentRequest.setSemester(result.getInt("semester"));
        	studentRequest.setScore(result.getFloat("score"));
			studentRequest.setSelectionType(RequestSelectionType.getEnum(result.getString("selection_type")));
        	studentRequest.setFirstChoice(result.getString("first_choice"));
        	studentRequest.setSecondChoice(result.getString("second_choice"));
        	studentRequest.setThirdChoice(result.getString("third_choice"));
        	studentRequest.setSelected(result.getString("selected"));
        	studentRequest.setTimestamp(result.getTimestamp("current_time"));
        	studentRequest.setAccepted(result.getBoolean("is_accepted"));
        	
	        studentRequests.add(studentRequest);
        }
        
		return studentRequests;
	}
	
	@Override
	public void createStudentRequest(StudentRequest studentRequest) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidStudentRequestParametersException {

		StudentRequestParametersValidationService studentRequestParametersValidationService = new StudentRequestParametersValidationService();
		
		studentRequestParametersValidationService.validateStudentRequestParameters(studentRequest);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementCreateStudentRequest = connection.prepareStatement("INSERT INTO `app_database`.`requests` (`student_faculty_number`, `semester`, `score`, `selection_type`, `first_choice`, `second_choice`, `third_choice`) VALUES (?, ?, ?, ?, ?, ?, ?)");
        
		statementCreateStudentRequest.setString(1, studentRequest.getFacutyNumber());
		statementCreateStudentRequest.setInt(2, studentRequest.getSemester());
		statementCreateStudentRequest.setFloat(3, studentRequest.getScore());
		statementCreateStudentRequest.setString(4, studentRequest.getSelectionType().toString());
		statementCreateStudentRequest.setString(5, studentRequest.getFirstChoice());
		if(studentRequest.getSecondChoice().isEmpty()) {
			statementCreateStudentRequest.setString(6, null);
		}
		else {
			statementCreateStudentRequest.setString(6, studentRequest.getSecondChoice());
		}
		if(studentRequest.getThirdChoice().isEmpty()) {
			statementCreateStudentRequest.setString(7, null);
		}
		else{
			statementCreateStudentRequest.setString(7, studentRequest.getThirdChoice());
		}
		
		statementCreateStudentRequest.executeUpdate();
	}
	
	@Override
	public void approveStudentRequest(int id, String selectedCourse) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementApproveStudentRequest = connection.prepareStatement("UPDATE `app_database`.`requests` SET `selected`= ?, `is_accepted`='1' WHERE `id`= ?");
        
        statementApproveStudentRequest.setString(1, selectedCourse);
        statementApproveStudentRequest.setInt(2, id);
        
        statementApproveStudentRequest.executeUpdate();
	}
	
}
