package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.UserParametersValidationService;

@Repository
public class AdminDAO implements AdminDataAccess {

	@Override
	public Admin getUser(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUser = connection.prepareStatement("SELECT * FROM app_database.admins_view WHERE username = ?");

        statementSelectUser.setString(1, username);
        
        ResultSet result = statementSelectUser.executeQuery();
        
        Admin admin = new Admin();
        
        while (result.next()) {
        	admin.setUsername(result.getString("username"));
        	admin.setPassword(result.getString("password"));
        	admin.setNickname(result.getString("nickname"));
        	admin.setFirstName(result.getString("first_name"));
        	admin.setLastName(result.getString("last_name"));
        	admin.setEmail(result.getString("email"));
        	admin.setMasterAdmin(result.getBoolean("is_master_admin"));
        }
        
		return admin;
	}

	@Override
	public List<Admin> getAllUsers() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUsers = connection.prepareStatement("SELECT * FROM app_database.admins_view;");
        
        ResultSet result = statementSelectUsers.executeQuery();
        
        List<Admin> admins = new ArrayList<Admin>();
        
        while (result.next()) {
        	Admin admin = new Admin();
        	admin.setUsername(result.getString("username"));
        	admin.setPassword(result.getString("password"));
        	admin.setNickname(result.getString("nickname"));
        	admin.setFirstName(result.getString("first_name"));
        	admin.setLastName(result.getString("last_name"));
        	admin.setEmail(result.getString("email"));
        	admin.setMasterAdmin(result.getBoolean("is_master_admin"));
        	admins.add(admin);
        }
		return admins;
	}

	@Override
	public void createUser(Admin admin) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateUserParameters(admin, false);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementInsertIntoUsers = connection.prepareStatement("INSERT INTO `app_database`.`users` (`username`, `password`) VALUES (?, ?)");
        
        statementInsertIntoUsers.setString(1, admin.getUsername());
        statementInsertIntoUsers.setString(2, admin.getPassword());
        
        statementInsertIntoUsers.executeUpdate();
    
        PreparedStatement statementInsertIntoAdmins = connection.prepareStatement("INSERT INTO `app_database`.`admins` (`nickname`, `first_name`, `last_name`, `email`, `is_master_admin`) VALUES (?, ?, ?, ?, ?)");
        
        statementInsertIntoAdmins.setString(1, admin.getNickname());
        statementInsertIntoAdmins.setString(2, admin.getFirstName());
        statementInsertIntoAdmins.setString(3, admin.getLastName());
        statementInsertIntoAdmins.setString(4, admin.getEmail());
        if(admin.isMasterAdmin() == false) {
        	statementInsertIntoAdmins.setString(5, null);
        }
        else {
        	statementInsertIntoAdmins.setBoolean(5, admin.isMasterAdmin());
        }
        
        statementInsertIntoAdmins.executeUpdate();
	}

	@Override
	public void updateAdminInfo(Admin admin) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateUserParameters(admin, true);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementUpdateAdmin = connection.prepareStatement("UPDATE `app_database`.`admins` SET `first_name`= ?, `last_name`= ?, `email`= ?, `is_master_admin`= ? WHERE `nickname`= ?");
        
        statementUpdateAdmin.setString(1, admin.getFirstName());
        statementUpdateAdmin.setString(2, admin.getLastName());
        statementUpdateAdmin.setString(3, admin.getEmail());
        if(admin.isMasterAdmin() == false) {
        	statementUpdateAdmin.setString(4, null);
        }
        else {
        	statementUpdateAdmin.setBoolean(4, admin.isMasterAdmin());
        }
        statementUpdateAdmin.setString(5, admin.getNickname());
        
        statementUpdateAdmin.executeUpdate();
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
