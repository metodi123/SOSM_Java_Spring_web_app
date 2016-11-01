package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.dao.StudentDAO;
import sosm.web.app.exception.InvalidModuleParametersException;
import sosm.web.app.model.Module;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.Student;

@Service
public class ModuleParametersValidationService {
	
	public static final int LONG_PARAMETERS_MAX_LENGTH = 78;
	public static final int SHORT_PARAMETERS_MAX_LENGTH = 38;
	
	public static final int ID_MIN_VALUE = 1;
	public static final int ID_MAX_VALUE = 1000000;

	public void validateModuleParameters(Module module, boolean ignoreIdValidation) throws InvalidModuleParametersException {
		if(ignoreIdValidation == false) {
			validateParameterRange(module.getId(), ID_MIN_VALUE, ID_MAX_VALUE);
		}
		validateParameter(module.getName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(module.getFullName(), LONG_PARAMETERS_MAX_LENGTH);
	}
	
	public void validateParameter(String parameter, int length) throws InvalidModuleParametersException {
		if(parameter.length() > length) {
			throw new InvalidModuleParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidModuleParametersException("Empty parameter entered");
		}
	}
	
	public void validateParameterRange(int parameter, int min, int max) throws InvalidModuleParametersException {
		if(!(parameter >= min && parameter <= max)) {
			throw new InvalidModuleParametersException("Parameter value out of range");
		}
	}
	
	public boolean isModuleValid(Module module, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		if(selectionType == RequestSelectionType.module) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.modules WHERE name = ?");
		}
		else {
			return false;
		}
		
		statement.setString(1, module.getName());
	    
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
	
	public boolean isModuleValidForStudent(Module module, String facultyNumber) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		Student student = new Student();
		
		StudentDAO studentDAO = new StudentDAO();
		
		student = studentDAO.getUser(facultyNumber);
		
		if(module.getName().equals(student.getModule())) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
