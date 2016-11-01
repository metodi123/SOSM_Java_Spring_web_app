package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidUserException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;

@Service
public class UserValidationService {

	public boolean isUserValid(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidUserException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
        statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `students_view` WHERE `username`= ? AND `password`= ?");
        
        statement.setString(1, student.getUsername());
        statement.setString(2, student.getPassword());
        
        ResultSet result = statement.executeQuery();
        
        int rowCount = 0;
        while (result.next()) {
        	rowCount = result.getInt("count");
        }
        
        if(rowCount == 1){
        	return true;
        }
        else {
        	throw new InvalidUserException("User is not in database");
        }
	}

	public boolean isUserValid(Employee employee) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidUserException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
        statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `employees_view` WHERE `username`= ? AND `password`= ?");
        
        statement.setString(1, employee.getUsername());
        statement.setString(2, employee.getPassword());
        
        ResultSet result = statement.executeQuery();
        
        int rowCount = 0;
        while (result.next()) {
        	rowCount = result.getInt("count");
        }
        
        if(rowCount == 1){
        	return true;
        }
        else {
        	throw new InvalidUserException("User is not in database");
        }
	}

	public boolean isUserValid(Admin admin) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidUserException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
	    statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `admins_view` WHERE `username`= ? AND `password`= ?");
	    
	    statement.setString(1, admin.getUsername());
	    statement.setString(2, admin.getPassword());
	    
	    ResultSet result = statement.executeQuery();
	    
	    int rowCount = 0;
	    while (result.next()) {
	    	rowCount = result.getInt("count");
	    }
	    
	    if(rowCount == 1){
	    	return true;
	    }
	    else {
	    	throw new InvalidUserException("User is not in database");
	    }
	}
	
}
