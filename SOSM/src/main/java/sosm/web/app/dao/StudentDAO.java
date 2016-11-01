package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Student;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.UserParametersValidationService;

@Repository
public class StudentDAO implements StudentDataAccess {
	
	@Override
	public Student getUser(String username) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUser = connection.prepareStatement("SELECT * FROM app_database.students_view WHERE username = ?");

        statementSelectUser.setString(1, username);
        
        ResultSet result = statementSelectUser.executeQuery();
        
        Student student = new Student();
        
        while (result.next()) {
	        student.setUsername(result.getString("username"));
	        student.setPassword(result.getString("password"));
	        student.setFacultyNumber(result.getString("faculty_number"));
	        student.setFirstName(result.getString("first_name"));
	        student.setLastName(result.getString("last_name"));
	        student.setCurrentSemester(result.getInt("current_semester"));
	        student.setGPA(result.getFloat("gpa"));
	        student.setGroup(result.getInt("group"));
	        student.setModule(result.getString("module"));
	        student.setEmail(result.getString("email"));
        }
        
		return student;
	}

	@Override
	public List<Student> getAllUsers() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectUsers = connection.prepareStatement("SELECT * FROM app_database.students_view");
        
        ResultSet result = statementSelectUsers.executeQuery();
        
        List<Student> students = new ArrayList<Student>();
        
        while (result.next()) {
        	Student student = new Student();
	        student.setUsername(result.getString("username"));
	        student.setPassword(result.getString("password"));
	        student.setFacultyNumber(result.getString("faculty_number"));
	        student.setFirstName(result.getString("first_name"));
	        student.setLastName(result.getString("last_name"));
	        student.setCurrentSemester(result.getInt("current_semester"));
	        student.setGPA(result.getFloat("gpa"));
	        student.setGroup(result.getInt("group"));
	        student.setModule(result.getString("module"));
	        student.setEmail(result.getString("email"));
	        students.add(student);
        }
        
		return students;
	}

	@Override
	public void createUser(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidUserParametersException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateUserParameters(student, false);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementInsertIntoUsers = connection.prepareStatement("INSERT INTO `app_database`.`users` (`username`, `password`) VALUES (?, ?)");
        
        statementInsertIntoUsers.setString(1, student.getUsername());
        statementInsertIntoUsers.setString(2, student.getPassword());
        
        statementInsertIntoUsers.executeUpdate();
    
        PreparedStatement statementInsertIntoStudents = connection.prepareStatement("INSERT INTO `app_database`.`students` (`faculty_number`, `first_name`, `last_name`, `current_semester`, `gpa`, `group`, `module`, `email`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        
        statementInsertIntoStudents.setString(1, student.getFacultyNumber());
        statementInsertIntoStudents.setString(2, student.getFirstName());
        statementInsertIntoStudents.setString(3, student.getLastName());
        statementInsertIntoStudents.setInt(4, student.getCurrentSemester());
        statementInsertIntoStudents.setFloat(5, student.getGPA());
        statementInsertIntoStudents.setInt(6, student.getGroup());
   		if(student.getModule().isEmpty()) {
   			statementInsertIntoStudents.setString(7, null);
   		}
   		else {
   			statementInsertIntoStudents.setString(7, student.getModule());
   		}
   		statementInsertIntoStudents.setString(8, student.getEmail());
        
   		statementInsertIntoStudents.executeUpdate();
	}
	
	@Override
	public void updateStudentInfo(Student student) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateUserParameters(student, true);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementUpdateStudent = connection.prepareStatement("UPDATE `app_database`.`students` SET `first_name`= ?, `last_name`= ?, `current_semester`= ?, `gpa`= ?, `group`= ?, `module`= ?, `email`= ? WHERE `faculty_number`= ?");
        
        statementUpdateStudent.setString(1, student.getFirstName());
        statementUpdateStudent.setString(2, student.getLastName());
        statementUpdateStudent.setInt(3, student.getCurrentSemester());
        statementUpdateStudent.setFloat(4, student.getGPA());
        statementUpdateStudent.setInt(5, student.getGroup());
        if(student.getModule().isEmpty()) {
        	statementUpdateStudent.setString(6, null);
   		}
   		else {
   			statementUpdateStudent.setString(6, student.getModule());
   		}
        statementUpdateStudent.setString(7, student.getEmail());
        statementUpdateStudent.setString(8, student.getFacultyNumber());
        
        statementUpdateStudent.executeUpdate();
	}

	@Override
	public void deleteUser(String username) throws InvalidUserParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		UserParametersValidationService userParametersValidation = new UserParametersValidationService();
		
		userParametersValidation.validateParameter(username, UserParametersValidationService.FACULTY_NUMBER_PARAMETERS_LENGTH);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementDeleteUser = connection.prepareStatement("DELETE FROM `app_database`.`users` WHERE `username`= ?");
        
        statementDeleteUser.setString(1, username);
        
        statementDeleteUser.executeUpdate();
	}

}
