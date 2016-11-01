package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.StudentRequest;

public interface StudentRequestDataAccess {
	public List<StudentRequest> getAllStudentRequests() throws Exception;
	
	public void createStudentRequest(StudentRequest studentRequest) throws Exception;
	
	public void approveStudentRequest(int id, String selectedCourse) throws Exception;
}
