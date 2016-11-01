package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidModuleParametersException;
import sosm.web.app.model.Module;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.ModuleParametersValidationService;

@Repository
public class ModuleDAO implements ModuleDataAccess {
	
	@Override
	public Module getModule(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectModule = connection.prepareStatement("SELECT * FROM app_database.modules WHERE id = ?");

        statementSelectModule.setInt(1, id);
        
        ResultSet result = statementSelectModule.executeQuery();
        
        Module module = new Module();
        
        while (result.next()) {
        	module.setId(result.getInt("id"));
	    	module.setName(result.getString("name"));
	    	module.setFullName(result.getString("full_name"));
        }
        
		return module;
	}

	@Override
	public List<Module> getAllModules() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    PreparedStatement statementSelectModules = connection.prepareStatement("SELECT * FROM app_database.modules");
	    
	    ResultSet result = statementSelectModules.executeQuery();
	    
	    List<Module> modules = new ArrayList<Module>();
	    
	    while (result.next()) {
	    	Module module = new Module();
	    	module.setId(result.getInt("id"));
	    	module.setName(result.getString("name"));
	    	module.setFullName(result.getString("full_name"));
	    	modules.add(module);
	    }
	    
		return modules;
	}

	@Override
	public void createModule(Module module) throws InvalidModuleParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
		
		moduleParametersValidationService.validateModuleParameters(module, true);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    PreparedStatement statementInsertIntoModules = connection.prepareStatement("INSERT INTO `app_database`.`modules` (`name`, `full_name`) VALUES (?, ?)");
	    
	    statementInsertIntoModules.setString(1, module.getName());
	    statementInsertIntoModules.setString(2, module.getFullName());
	    
	    statementInsertIntoModules.executeUpdate();
	}
	
	@Override
	public void updateModuleInfo(Module module) throws InvalidModuleParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
		
		moduleParametersValidationService.validateModuleParameters(module, false);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    PreparedStatement statementUpdateModule = connection.prepareStatement("UPDATE `app_database`.`modules` SET `name`= ?, `full_name`= ? WHERE `id`= ?");
	    
	    statementUpdateModule.setString(1, module.getName());
	    statementUpdateModule.setString(2, module.getFullName());
	    statementUpdateModule.setInt(3, module.getId());
	
		statementUpdateModule.executeUpdate();
	}
	
	@Override
	public void deleteModule(int id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    PreparedStatement statementDeleteModule = connection.prepareStatement("DELETE FROM `app_database`.`modules` WHERE `id`= ?");
	    
	    statementDeleteModule.setInt(1, id);
	    
	    statementDeleteModule.executeUpdate();
	}
	
}
