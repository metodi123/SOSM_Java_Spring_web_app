package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.Student;;

public interface StudentDataAccess {
	public Student getUser(String username) throws Exception;
	
	public List<Student> getAllUsers() throws Exception;

	public void createUser(Student student) throws Exception;
	
	public void updateStudentInfo(Student student) throws Exception;
	
	public void deleteUser(String username) throws Exception;
}
