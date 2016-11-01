package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidInfoTextException;
import sosm.web.app.model.Student;

@Service
public class InfoTextService {

	public String getInfoText() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String infoText = "";
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
	
	    PreparedStatement statement;

		statement = connection.prepareStatement("SELECT * FROM app_database.info_message WHERE id = '1'");
		
		ResultSet result = statement.executeQuery();
	        
	    while (result.next()) {
	        infoText = result.getString("text");
	    }
		
		return infoText;
	}
	
	public void setInfoText(String infoText) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidInfoTextException {
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    PreparedStatement statement;
	    
	    InfoTextValidationService infoTextValidationService = new InfoTextValidationService();
	    
	    infoTextValidationService.validateInfoText(infoText);
	    
		statement = connection.prepareStatement("UPDATE `app_database`.`info_message` SET `text`= ? WHERE `id`='1'");
		statement.setString(1, infoText);
		statement.executeUpdate();	
	}

}
