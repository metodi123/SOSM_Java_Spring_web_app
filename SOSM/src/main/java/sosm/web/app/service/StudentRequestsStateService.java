package sosm.web.app.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequestsState;

@Service
public class StudentRequestsStateService {

	public StudentRequestsState getStudentRequestsState(Student student) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		StudentRequestsState studentRequestsState = new StudentRequestsState();
		
		StudentProcessingService studentProcessingService = new StudentProcessingService();

		studentRequestsState.setRequestSendByStudentForCourses(studentProcessingService.isRequestSendByStudentForCourses(student));
		studentRequestsState.setUnprocessedRequestSendByStudentForCourses(studentProcessingService.isRequestSendByStudentForCourses(student, false));
		studentRequestsState.setProcessedRequestSendByStudentForCourses(studentProcessingService.isRequestSendByStudentForCourses(student, true));
		
		studentRequestsState.setRequestSendByStudentForModules(studentProcessingService.isRequestSendByStudentForModules(student));
		studentRequestsState.setUnprocessedRequestSendByStudentForModules(studentProcessingService.isRequestSendByStudentForModules(student, false));
		studentRequestsState.setProcessedRequestSendByStudentForModules(studentProcessingService.isRequestSendByStudentForModules(student, true));
		
		return studentRequestsState;
	}
	
}
