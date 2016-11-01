package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class StudentRequestsState {
	private boolean isRequestSendByStudentForCourses;
	private boolean isUnprocessedRequestSendByStudentForCourses;
	private boolean isProcessedRequestSendByStudentForCourses;
	private boolean isRequestSendByStudentForModules;
	private boolean isUnprocessedRequestSendByStudentForModules;
	private boolean isProcessedRequestSendByStudentForModules;
	
	public StudentRequestsState() {
		
	}

	public boolean isRequestSendByStudentForCourses() {
		return isRequestSendByStudentForCourses;
	}

	public void setRequestSendByStudentForCourses(boolean isRequestSendByStudentForCourses) {
		this.isRequestSendByStudentForCourses = isRequestSendByStudentForCourses;
	}

	public boolean isUnprocessedRequestSendByStudentForCourses() {
		return isUnprocessedRequestSendByStudentForCourses;
	}

	public void setUnprocessedRequestSendByStudentForCourses(boolean isUnprocessedRequestSendByStudentForCourses) {
		this.isUnprocessedRequestSendByStudentForCourses = isUnprocessedRequestSendByStudentForCourses;
	}

	public boolean isProcessedRequestSendByStudentForCourses() {
		return isProcessedRequestSendByStudentForCourses;
	}

	public void setProcessedRequestSendByStudentForCourses(boolean isProcessedRequestSendByStudentForCourses) {
		this.isProcessedRequestSendByStudentForCourses = isProcessedRequestSendByStudentForCourses;
	}

	public boolean isRequestSendByStudentForModules() {
		return isRequestSendByStudentForModules;
	}

	public void setRequestSendByStudentForModules(boolean isRequestSendByStudentForModules) {
		this.isRequestSendByStudentForModules = isRequestSendByStudentForModules;
	}

	public boolean isUnprocessedRequestSendByStudentForModules() {
		return isUnprocessedRequestSendByStudentForModules;
	}

	public void setUnprocessedRequestSendByStudentForModules(boolean isUnprocessedRequestSendByStudentForModules) {
		this.isUnprocessedRequestSendByStudentForModules = isUnprocessedRequestSendByStudentForModules;
	}

	public boolean isProcessedRequestSendByStudentForModules() {
		return isProcessedRequestSendByStudentForModules;
	}

	public void setProcessedRequestSendByStudentForModules(boolean isProcessedRequestSendByStudentForModules) {
		this.isProcessedRequestSendByStudentForModules = isProcessedRequestSendByStudentForModules;
	}

}
