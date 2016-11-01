package sosm.web.app.service;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidUserParametersException;

@Service
public class UpdateUserSettingsService {
	
	public void updateUserPassword(String username, String password) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(password, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		hashedPassword = passwordProcessingService.getHashedPassword(password, username);
	
        PreparedStatement statementUpdatePassword = connection.prepareStatement("UPDATE `app_database`.`users` SET `password`= ? WHERE `username`= ?");
        
        statementUpdatePassword.setString(1, hashedPassword);
        statementUpdatePassword.setString(2, username);
        
        statementUpdatePassword.executeUpdate();
	}
	
	public void updateStudentEmail(String username, String email) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(email, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
	
        PreparedStatement statementUpdatePassword = connection.prepareStatement("UPDATE `app_database`.`students` SET `email`= ? WHERE `faculty_number`= ?");
        
        statementUpdatePassword.setString(1, email);
        statementUpdatePassword.setString(2, username);
        
        statementUpdatePassword.executeUpdate();
	}
	
	public void updateEmployeeEmail(String username, String email) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(email, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
	
        PreparedStatement statementUpdatePassword = connection.prepareStatement("UPDATE `app_database`.`employees` SET `email`= ? WHERE `nickname`= ?");
        
        statementUpdatePassword.setString(1, email);
        statementUpdatePassword.setString(2, username);
        
        statementUpdatePassword.executeUpdate();
	}
	
	public void updateAdminEmail(String username, String email) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(email, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
	
        PreparedStatement statementUpdatePassword = connection.prepareStatement("UPDATE `app_database`.`admins` SET `email`= ? WHERE `nickname`= ?");
        
        statementUpdatePassword.setString(1, email);
        statementUpdatePassword.setString(2, username);
        
        statementUpdatePassword.executeUpdate();
	}

}
