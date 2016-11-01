package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.dao.SelectionsStateDAO;
import sosm.web.app.exception.InvalidSelectionsStateParametersException;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.SelectionsState;
import sosm.web.app.model.Student;

@Service
public class StudentProcessingService {

	public boolean isStudentAvailableForSendingRequest(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidSelectionsStateParametersException {
		
		if((student.getCurrentSemester() >= 3) || (student.getCurrentSemester() <= 6)) {
			SelectionsState selectionsState = new SelectionsState();
			
			SelectionsStateDAO selectionsStateDAO = new SelectionsStateDAO();
			
			selectionsState = selectionsStateDAO.getSelectionsState();
			
			if((student.getCurrentSemester() == 3) && (selectionsState.isSelectionOpenForSemester4() == true)) {
				return true;
			}
			else if((student.getCurrentSemester() == 4) && (selectionsState.isSelectionOpenForSemester5() == true)) {
				return true;
			}
			else if((student.getCurrentSemester() == 5) && (selectionsState.isSelectionOpenForSemester6() == true)) {
				return true;
			}
			else if((student.getCurrentSemester() == 5) && (selectionsState.isSelectionOpenModules() == true)) {
				return true;
			}
			else if((student.getCurrentSemester() == 6) && (selectionsState.isSelectionOpenForSemester7() == true)) {
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean isRequestSendByStudent(Student student, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN (?)");
	    
	    statement.setString(1, student.getFacultyNumber());
	    statement.setInt(2, student.getCurrentSemester() + 1);
	    statement.setString(3, selectionType.toString());
	    
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
	
	public boolean isRequestSendByStudentForCourses(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('избираема дисциплина','курсов проект','курсова работа')");
	    
	    statement.setString(1, student.getFacultyNumber());
	    statement.setInt(2, student.getCurrentSemester() + 1);
	    
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
	
	public boolean isRequestSendByStudentForCourses(Student student, boolean isRequestProcessed) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		if(isRequestProcessed == false) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('избираема дисциплина','курсов проект','курсова работа') AND `is_accepted` IS NULL");
		}
		else if(isRequestProcessed == true) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('избираема дисциплина','курсов проект','курсова работа') AND `is_accepted` IS NOT NULL");
		}
	    
	    statement.setString(1, student.getFacultyNumber());
	    statement.setInt(2, student.getCurrentSemester() + 1);
	    
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
	
	public boolean isRequestSendByStudentForModules(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('модул')");
	    
	    statement.setString(1, student.getFacultyNumber());
	    statement.setInt(2, student.getCurrentSemester() + 1);
	    
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
	
	public boolean isRequestSendByStudentForModules(Student student, boolean isRequestProcessed) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		if(isRequestProcessed == false) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('модул') AND `is_accepted` IS NULL");
		}
		else if(isRequestProcessed == true) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `student_faculty_number` = ? AND `semester` = ? AND `selection_type` IN ('модул') AND `is_accepted` IS NOT NULL");
		}
	    
	    statement.setString(1, student.getFacultyNumber());
	    statement.setInt(2, student.getCurrentSemester() + 1);
	    
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
