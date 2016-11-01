package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.Admin;

public interface AdminDataAccess {
	public Admin getUser(String username) throws Exception;
	
	public List<Admin> getAllUsers() throws Exception;

	public void createUser(Admin admin) throws Exception;
	
	public void updateAdminInfo(Admin admin) throws Exception;
	
	public void deleteUser(String username) throws Exception;
}
