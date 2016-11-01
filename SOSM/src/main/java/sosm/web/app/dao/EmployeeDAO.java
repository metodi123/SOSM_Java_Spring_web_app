package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Employee;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.UserParametersValidationService;

@Repository
public class EmployeeDAO implements EmployeeDataAccess {

	@Override
	public Employee getUser(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUser = connection.prepareStatement("SELECT * FROM app_database.employees_view WHERE username = ?");

        statementSelectUser.setString(1, username);
        
        ResultSet result = statementSelectUser.executeQuery();
        
        Employee employee = new Employee();
        
        while (result.next()) {
        	employee.setUsername(result.getString("username"));
        	employee.setPassword(result.getString("password"));
        	employee.setNickname(result.getString("nickname"));
        	employee.setFirstName(result.getString("first_name"));
        	employee.setLastName(result.getString("last_name"));
        	employee.setEmail(result.getString("email"));
        }
        
		return employee;
	}

	@Override
	public List<Employee> getAllUsers() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUsers = connection.prepareStatement("SELECT * FROM app_database.employees_view");
        
        ResultSet result = statementSelectUsers.executeQuery();
        
        List<Employee> employees = new ArrayList<Employee>();
        
        while (result.next()) {
        	Employee employee = new Employee();
        	employee.setUsername(result.getString("username"));
        	employee.setPassword(result.getString("password"));
        	employee.setNickname(result.getString("nickname"));
        	employee.setFirstName(result.getString("first_name"));
        	employee.setLastName(result.getString("last_name"));
        	employee.setEmail(result.getString("email"));
        	employees.add(employee);
        }
		return employees;
	}

	@Override
	public void createUser(Employee employee) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
			
		userParametersValidation.validateUserParameters(employee, false);
			
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
			
	    PreparedStatement statementInsertIntoUsers = connection.prepareStatement("INSERT INTO `app_database`.`users` (`username`, `password`) VALUES (?, ?)");
	        
	    statementInsertIntoUsers.setString(1, employee.getUsername());
	    statementInsertIntoUsers.setString(2, employee.getPassword());
	        
	    statementInsertIntoUsers.executeUpdate();
	    
	    PreparedStatement statementInsertIntoEmployees = connection.prepareStatement("INSERT INTO `app_database`.`employees` (`nickname`, `first_name`, `last_name`, `email`) VALUES (?, ?, ?, ?)");
	        
	    statementInsertIntoEmployees.setString(1, employee.getNickname());
	    statementInsertIntoEmployees.setString(2, employee.getFirstName());
	    statementInsertIntoEmployees.setString(3, employee.getLastName());
	    statementInsertIntoEmployees.setString(4, employee.getEmail());
	        
	    statementInsertIntoEmployees.executeUpdate();
	}

	@Override
	public void updateEmployeeInfo(Employee employee) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateUserParameters(employee, true);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementUpdateEmployee = connection.prepareStatement("UPDATE `app_database`.`employees` SET `first_name`= ?, `last_name`= ?, `email`= ? WHERE `nickname`= ?");
        
        statementUpdateEmployee.setString(1, employee.getFirstName());
        statementUpdateEmployee.setString(2, employee.getLastName());
        statementUpdateEmployee.setString(3, employee.getEmail());
        statementUpdateEmployee.setString(4, employee.getNickname());
        
        statementUpdateEmployee.executeUpdate();
	}

	@Override
	public void deleteUser(String username) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(username, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementDeleteUser = connection.prepareStatement("DELETE FROM `app_database`.`users` WHERE `username`= ?");
        
        statementDeleteUser.setString(1, username);
        
        statementDeleteUser.executeUpdate();
	}

}
