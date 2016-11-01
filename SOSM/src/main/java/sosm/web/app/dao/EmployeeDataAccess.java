package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.Employee;

public interface EmployeeDataAccess {
	public Employee getUser(String username) throws Exception;
	
	public List<Employee> getAllUsers() throws Exception;

	public void createUser(Employee employee) throws Exception;
	
	public void updateEmployeeInfo(Employee employee) throws Exception;
	
	public void deleteUser(String username) throws Exception;
}
