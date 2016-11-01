package sosm.web.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import sosm.web.app.model.DatabaseConnection;

@Service
public class DatabaseConnectionService {
	DatabaseConnection dbConnection;
	
	public DatabaseConnectionService() {
		
	}
	
	public DatabaseConnectionService(DatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	public static void createConnection(DatabaseConnectionService dbConnectionService) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection connection = DriverManager.getConnection(dbConnectionService.getDbConnection().getUrl(), dbConnectionService.getDbConnection().getUsername(), dbConnectionService.getDbConnection().getPassword());

		if(connection == null) {
			throw new SQLException("You cannot connect to the server!");
		}

	}
	
	public static Connection getConnection(DatabaseConnectionService dbConnectionService) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection connection = DriverManager.getConnection(dbConnectionService.getDbConnection().getUrl(), dbConnectionService.getDbConnection().getUsername(), dbConnectionService.getDbConnection().getPassword());
	
		return connection;
	}
	
	public static void connectToAppDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/Spring/beans.xml");
	
		DatabaseConnectionService dbConnectionService = (DatabaseConnectionService) context.getBean("dbConnectionService");
		
		createConnection(dbConnectionService);
	}
	
	public static Connection getAppDatabaseConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/Spring/beans.xml");
		
		DatabaseConnectionService dbConnectionService = (DatabaseConnectionService) context.getBean("dbConnectionService");
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection connection = DriverManager.getConnection(dbConnectionService.getDbConnection().getUrl(), dbConnectionService.getDbConnection().getUsername(), dbConnectionService.getDbConnection().getPassword());
	
		return connection;
	}
	
	public DatabaseConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
}
