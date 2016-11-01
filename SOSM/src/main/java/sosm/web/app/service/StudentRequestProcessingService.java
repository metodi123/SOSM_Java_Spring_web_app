package sosm.web.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.springframework.stereotype.Service;

import sosm.web.app.dao.CourseDAO;
import sosm.web.app.dao.ModuleDAO;
import sosm.web.app.dao.StudentDAO;
import sosm.web.app.dao.StudentRequestDAO;
import sosm.web.app.model.Course;
import sosm.web.app.model.Module;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequest;

@Service
public class StudentRequestProcessingService {

	public void processRequests(List<StudentRequest> studentRequests, Map<String,String> selectionUnits) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		String courseName = "";
		int coursePlaces = 0;
		
		int iteration = 1;
        int choiceIteration = 1;
        while(iteration <= 2) {
	        for(StudentRequest studentRequest : studentRequests) {
	        	choiceIteration = 1;
	        	try {
	        		if(studentRequests.size() == 0) {
		        		break;
		        	}
			        if(iteration == 1) {
			        	while(choiceIteration <= 3) {
			        		if(studentRequest.isAccepted()) {
			        			break;
			        		}
			        		else {
				        		if(choiceIteration == 1) {
				        			studentRequest.setSelected(studentRequest.getFirstChoice());
				        		}
				        		else if(choiceIteration == 2) {
				        			studentRequest.setSelected(studentRequest.getSecondChoice());
				        		}
				        		else if(choiceIteration == 3) {
				        			studentRequest.setSelected(studentRequest.getThirdChoice());
				        		}
					        	if(!studentRequest.getSelected().equals("")) {				        		
					        		for (Map.Entry<String, String> entry : selectionUnits.entrySet()) {	
					        			courseName = entry.getKey();
					        			coursePlaces = Integer.parseInt(entry.getValue());
					        			
					        			if((studentRequest.getSelected().equals(courseName)) && (coursePlaces > 0)) {
							        		studentRequest.setAccepted(true);
							        		
											studentRequestDAO.approveStudentRequest(studentRequest.getId(), studentRequest.getSelected());
											
							        		coursePlaces--;
							        		entry.setValue(String.valueOf(coursePlaces));
							        	}
					        		}
					        	}
			        		}
			        		choiceIteration++;
			        	}
			        }
			        else if(iteration == 2) {
			        	if(studentRequest.isAccepted()) {
		        			continue;
		        		}
			        	else {
			        		for (Map.Entry<String, String> entry : selectionUnits.entrySet()) {	
			        			courseName = entry.getKey();
			        			coursePlaces = Integer.parseInt(entry.getValue());
			        			
			        			if(coursePlaces > 0) {
					        		studentRequest.setAccepted(true);
					        		studentRequest.setSelected(courseName);
					        		
									studentRequestDAO.approveStudentRequest(studentRequest.getId(), studentRequest.getSelected());
									
					        		coursePlaces--;
					        		entry.setValue(String.valueOf(coursePlaces));
					        		break;
					        	}
			        		}
			        	}
			        }
	        		
		        }
	        	catch(NullPointerException e) {
	        		continue;
	        	}
	        }
	        iteration++;
        }
	}
	
	public List<StudentRequest> selectStudentRequests (String requestsState, List<String> requestsSemester, List<String> requestsType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, UnsupportedDataTypeException, SQLException {
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		
		List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
		
		List<StudentRequest> studentRequestsProcessFiltered = new ArrayList<StudentRequest>();
		List<StudentRequest> studentRequestsSemesterFiltered = new ArrayList<StudentRequest>();
		List<StudentRequest> studentRequestsFullyFiltered = new ArrayList<StudentRequest>();
		
		studentRequests = studentRequestDAO.getAllStudentRequests();
		
		boolean unprocessedRequestsSelected = false;
		boolean processedRequestsSelected = false;
		
		if(requestsState.equals("unprocessed")) {
			unprocessedRequestsSelected = true;
		}
		else if(requestsState.equals("processed")) {
			processedRequestsSelected = true;
		}
		
		boolean fourthSemesterSelected = false;
		boolean fifthSemesterSelected = false;
		boolean sixthSemesterSelected = false;
		boolean seventhSemesterSelected = false;
		
		for(String value : requestsSemester) {
			if(value.equals("fourth")) {
				fourthSemesterSelected = true;
			}
			else if (value.equals("fifth")) {
				fifthSemesterSelected = true;
			}
			else if (value.equals("sixth")) {
				sixthSemesterSelected = true;
			}
			else if (value.equals("seventh")) {
				seventhSemesterSelected = true;
			}
		}
		
		boolean electiveSelected = false;
		boolean courseProjectSelected = false;
		boolean courseWorkSelected = false;
		boolean moduleSelected = false;
		
		for(String value : requestsType) {
			if(value.equals("electiveCourse")) {
				electiveSelected = true;
			}
			else if (value.equals("courseProject")) {
				courseProjectSelected = true;
			}
			else if (value.equals("courseWork")) {
				courseWorkSelected = true;
			}
			else if (value.equals("module")) {
				moduleSelected = true;
			}
		}
		
		for(StudentRequest studentRequest : studentRequests) {
			if(unprocessedRequestsSelected == true && studentRequest.isAccepted() == false) {
				studentRequestsProcessFiltered.add(studentRequest);
			}
			else if(processedRequestsSelected == true && studentRequest.isAccepted() == true) {
				studentRequestsProcessFiltered.add(studentRequest);
			}
		}
		
		for(StudentRequest studentRequest : studentRequestsProcessFiltered) {
			if(fourthSemesterSelected == true && studentRequest.getSemester() == 4) {
				studentRequestsSemesterFiltered.add(studentRequest);
			}
			else if(fifthSemesterSelected == true && studentRequest.getSemester() == 5) {
				studentRequestsSemesterFiltered.add(studentRequest);
			}
			else if(sixthSemesterSelected == true && studentRequest.getSemester() == 6) {
				studentRequestsSemesterFiltered.add(studentRequest);
			}
			else if(seventhSemesterSelected == true && studentRequest.getSemester() == 7) {
				studentRequestsSemesterFiltered.add(studentRequest);
			}
		}
		
		for(StudentRequest studentRequest : studentRequestsSemesterFiltered) {
			if(electiveSelected == true && studentRequest.getSelectionType().equals(RequestSelectionType.electiveCourse)) {
				studentRequestsFullyFiltered.add(studentRequest);
			}
			else if(courseProjectSelected == true && studentRequest.getSelectionType().equals(RequestSelectionType.courseProject)) {
				studentRequestsFullyFiltered.add(studentRequest);
			}
			else if(courseWorkSelected == true && studentRequest.getSelectionType().equals(RequestSelectionType.courseWork)) {
				studentRequestsFullyFiltered.add(studentRequest);
			}
			else if(moduleSelected == true && studentRequest.getSelectionType().equals(RequestSelectionType.module)) {
				studentRequestsFullyFiltered.add(studentRequest);
			}
		}
		
		return studentRequestsFullyFiltered;
	}
	
	public Boolean isSemesterContainingCoursesForSelectionType(int semester, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		if(selectionType == RequestSelectionType.electiveCourse) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `courses` WHERE `semester`= ? AND `is_elective` = '1'");
		}
		else if(selectionType == RequestSelectionType.courseWork) {
			statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `courses` WHERE `semester`= ? AND `is_course_work` = '1'");
        }
        else if(selectionType == RequestSelectionType.courseProject) {
        	statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `courses` WHERE `semester`= ? AND `is_course_project` = '1'");
        }
        else {
        	return false;
        }
		
        statement.setInt(1, semester);
        
        ResultSet result = statement.executeQuery();
        
        int rowCount = 0;
        while (result.next()) {
        	rowCount = result.getInt("count");
        }
        
        if(rowCount > 0){
        	return true;
        }
        else {
        	return false;
        }
	}
	
	public HashMap<String, Boolean> semestersContainingCoursesBySelectionType() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		HashMap<String, Boolean> semestersContainingCoursesBySelectionType = new HashMap<String, Boolean>();
		
		semestersContainingCoursesBySelectionType.put("semester4Elective", this.isSemesterContainingCoursesForSelectionType(4, RequestSelectionType.electiveCourse));
		semestersContainingCoursesBySelectionType.put("semester4CourseProject",this.isSemesterContainingCoursesForSelectionType(4, RequestSelectionType.courseProject));
		semestersContainingCoursesBySelectionType.put("semester4CourseWork", this.isSemesterContainingCoursesForSelectionType(4, RequestSelectionType.courseWork));
		
		semestersContainingCoursesBySelectionType.put("semester5Elective", this.isSemesterContainingCoursesForSelectionType(5, RequestSelectionType.electiveCourse));
		semestersContainingCoursesBySelectionType.put("semester5CourseProject",this.isSemesterContainingCoursesForSelectionType(5, RequestSelectionType.courseProject));
		semestersContainingCoursesBySelectionType.put("semester5CourseWork", this.isSemesterContainingCoursesForSelectionType(5, RequestSelectionType.courseWork));
		
		semestersContainingCoursesBySelectionType.put("semester6Elective", this.isSemesterContainingCoursesForSelectionType(6, RequestSelectionType.electiveCourse));
		semestersContainingCoursesBySelectionType.put("semester6CourseProject",this.isSemesterContainingCoursesForSelectionType(6, RequestSelectionType.courseProject));
		semestersContainingCoursesBySelectionType.put("semester6CourseWork", this.isSemesterContainingCoursesForSelectionType(6, RequestSelectionType.courseWork));
		
		semestersContainingCoursesBySelectionType.put("semester7Elective", this.isSemesterContainingCoursesForSelectionType(7, RequestSelectionType.electiveCourse));
		semestersContainingCoursesBySelectionType.put("semester7CourseProject",this.isSemesterContainingCoursesForSelectionType(7, RequestSelectionType.courseProject));
		semestersContainingCoursesBySelectionType.put("semester7CourseWork", this.isSemesterContainingCoursesForSelectionType(7, RequestSelectionType.courseWork));
			
		return semestersContainingCoursesBySelectionType;
	}
	
	public int getNumberOfRequests(RequestSelectionType selectionType, int semester, int moduleId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		Module module = new Module();
		
		ModuleDAO moduleDAO = new ModuleDAO();
			
		module = moduleDAO.getModule(moduleId);
		
		PreparedStatement statement = null;
		
		if(moduleId == 0) {
			statement = connection.prepareStatement("SELECT students.faculty_number, students.module, requests.semester, requests.selection_type, requests.is_accepted, COUNT(*) AS `count` FROM students INNER JOIN requests ON students.faculty_number=requests.student_faculty_number WHERE selection_type = ? AND semester = ? AND is_accepted IS NULL");
			  
			statement.setString(1, selectionType.toString());
			statement.setInt(2, semester);
		}
		else {
			statement = connection.prepareStatement("SELECT students.faculty_number, students.module, requests.semester, requests.selection_type, requests.is_accepted, COUNT(*) AS `count` FROM students INNER JOIN requests ON students.faculty_number=requests.student_faculty_number WHERE module = ? AND selection_type = ? AND semester = ? AND is_accepted IS NULL");
			  
			statement.setString(1, module.getName());
			statement.setString(2, selectionType.toString());
			statement.setInt(3, semester);
		}
	    
	    ResultSet result = statement.executeQuery();
	        
	    int rowCount = 0;
	    while (result.next()) {
	    	rowCount = result.getInt("count");
	    }
	    
		return rowCount;
	}
	
	public HashMap<String, Integer> getNumberOfRequestsForCourseFirstChoice(RequestSelectionType selectionType, int semester, int moduleId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		HashMap<String, Integer> numberOfRequestsForFirstChoice = new HashMap<String, Integer>();
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		List<Course> courses = new ArrayList<>();
		
		CourseDAO courseDAO = new CourseDAO();
		
		courses = courseDAO.getAllCourses();
		
		Module module = new Module();
		
		ModuleDAO moduleDAO = new ModuleDAO();
			
		module = moduleDAO.getModule(moduleId);

		for(Course course : courses) {
			CourseParametersValidationService courseParametersValidationService = new CourseParametersValidationService();
			
			if((courseParametersValidationService.isCourseValid(course, selectionType, semester) == true) && (course.getModuleName() == null || course.getModuleName().equals(module.getName()))) {
				PreparedStatement statement = null;
				
				if(moduleId == 0) {
					statement = connection.prepareStatement("SELECT students.faculty_number, students.module, requests.semester, requests.selection_type, requests.first_choice, requests.is_accepted, COUNT(*) AS `count` FROM students INNER JOIN requests ON students.faculty_number=requests.student_faculty_number WHERE selection_type = ? AND semester = ? AND first_choice = ? AND is_accepted IS NULL");
					  
					statement.setString(1, selectionType.toString());
					statement.setInt(2, semester);
					statement.setString(3, course.getName());
				}
				else {
					statement = connection.prepareStatement("SELECT students.faculty_number, students.module, requests.semester, requests.selection_type, requests.first_choice, requests.is_accepted, COUNT(*) AS `count` FROM students INNER JOIN requests ON students.faculty_number=requests.student_faculty_number WHERE module = ? AND selection_type = ? AND semester = ? AND first_choice = ? AND is_accepted IS NULL");
					  
					statement.setString(1, module.getName());
					statement.setString(2, selectionType.toString());
					statement.setInt(3, semester);
					statement.setString(4, course.getName());
				}
		        
		        ResultSet result = statement.executeQuery();
		        
		        int rowCount = 0;
		        while (result.next()) {
		        	rowCount = result.getInt("count");
		        }
		        
		        numberOfRequestsForFirstChoice.put(course.getName(), rowCount);
			}
		}
		
		return numberOfRequestsForFirstChoice;
	}
	
	public HashMap<String, Integer> getNumberOfRequestsForModuleFirstChoice(RequestSelectionType selectionType, int semester) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		HashMap<String, Integer> numberOfRequestsForFirstChoice = new HashMap<String, Integer>();
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		List<Module> modules = new ArrayList<>();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		modules = moduleDAO.getAllModules();

		for(Module module : modules) {
			
			ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
			
			if(moduleParametersValidationService.isModuleValid(module, selectionType) == true) {
				PreparedStatement statement = null;
				
		        statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE selection_type = ? AND semester = ? AND first_choice = ? AND is_accepted IS NULL");
		        
		        statement.setString(1, selectionType.toString());
		        statement.setInt(2, semester);
		        statement.setString(3, module.getName());
		        
		        ResultSet result = statement.executeQuery();
		        
		        int rowCount = 0;
		        while (result.next()) {
		        	rowCount = result.getInt("count");
		        }
		        
		        numberOfRequestsForFirstChoice.put(module.getName(), rowCount);
			}
		}
		
		return numberOfRequestsForFirstChoice;
	}
	
	public void updateRequestsToMeetSelectedCriteria(int semester, RequestSelectionType processedSelectionType, RequestSelectionType unprocessedSelectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, UnsupportedDataTypeException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
	    List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
	    List<StudentRequest> processedStudentRequests = new ArrayList<StudentRequest>();
	    List<StudentRequest> unprocessedStudentRequests = new ArrayList<StudentRequest>();
	    
	    StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
	    
	    studentRequests = studentRequestDAO.getAllStudentRequests();
	    
	    for(StudentRequest studentRequest : studentRequests) {
	    	if((studentRequest.getSemester() == semester) && (studentRequest.getSelectionType() == processedSelectionType)) {
	    		processedStudentRequests.add(studentRequest);
	    	}
	    	else if((studentRequest.getSemester() == semester) && (studentRequest.getSelectionType() == unprocessedSelectionType)) {
	    		unprocessedStudentRequests.add(studentRequest);
	    	}
	    }
	    
	    List<Course> courses = new ArrayList<Course>();
	    List<Course> selectableCourses = new ArrayList<Course>();
	    
	    CourseDAO courseDAO = new CourseDAO();
	    
	    courses = courseDAO.getAllCourses();
	    
	    if(processedSelectionType == RequestSelectionType.electiveCourse) {
	    	for(Course course : courses) {
		    	if((course.getSemester() == semester) && (course.isElective() == true)) {
		    		selectableCourses.add(course);
		    	}
		    }
	    	
	    	for(StudentRequest processedStudentRequest : processedStudentRequests) {
		    	for(StudentRequest unprocessedStudentRequest : unprocessedStudentRequests) {
		    		if(processedStudentRequest.getFacutyNumber().equals(unprocessedStudentRequest.getFacutyNumber())) {
		    			for(Course course : selectableCourses) {
		    		    	if(course.getName().equals(unprocessedStudentRequest.getFirstChoice()) && (!processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getFirstChoice()))) {
		    		    		unprocessedStudentRequest.setFirstChoice(unprocessedStudentRequest.getSecondChoice());
					    		unprocessedStudentRequest.setSecondChoice(unprocessedStudentRequest.getThirdChoice());
					    		unprocessedStudentRequest.setThirdChoice(null);
		    		    	}
		    		    	else if(course.getName().equals(unprocessedStudentRequest.getSecondChoice()) && (!processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getSecondChoice()))) {
		    		    		unprocessedStudentRequest.setSecondChoice(unprocessedStudentRequest.getThirdChoice());
					    		unprocessedStudentRequest.setThirdChoice(null);
		    		    	}
		    		    	else if(course.getName().equals(unprocessedStudentRequest.getThirdChoice()) && (!processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getThirdChoice()))) {
		    		    		unprocessedStudentRequest.setThirdChoice(null);
		    		    	}
		    		    }
		    		}
			    }
		    }
	    }
	    else if(processedSelectionType == RequestSelectionType.courseProject) {
	    	for(StudentRequest processedStudentRequest : processedStudentRequests) {
		    	for(StudentRequest unprocessedStudentRequest : unprocessedStudentRequests) {
		    		if(processedStudentRequest.getFacutyNumber().equals(unprocessedStudentRequest.getFacutyNumber())) {
		    		    if(processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getFirstChoice())) {
		    		    	unprocessedStudentRequest.setFirstChoice(unprocessedStudentRequest.getSecondChoice());
					    	unprocessedStudentRequest.setSecondChoice(unprocessedStudentRequest.getThirdChoice());
					    	unprocessedStudentRequest.setThirdChoice(null);
		    		    }
		    		    else if(processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getSecondChoice())) {
		    		    	unprocessedStudentRequest.setSecondChoice(unprocessedStudentRequest.getThirdChoice());
					    	unprocessedStudentRequest.setThirdChoice(null);
		    		    }
		    		    else if(processedStudentRequest.getSelected().equals(unprocessedStudentRequest.getThirdChoice())) {
		    		    	unprocessedStudentRequest.setThirdChoice(null);
		    		    }
		    		}
			    }
		    }
	    }
	    
	    for(StudentRequest unprocessedStudentRequest : unprocessedStudentRequests) {
	    	PreparedStatement statementUpdateRequests = connection.prepareStatement("UPDATE `app_database`.`requests` SET `first_choice`= ?, `second_choice`= ?, `third_choice`= ? WHERE `id`= ?");
		    
	    	if(unprocessedStudentRequest.getFirstChoice() == null) {
	    		statementUpdateRequests.setString(1, null);
	    	}
	    	else {
	    		statementUpdateRequests.setString(1, unprocessedStudentRequest.getFirstChoice());
	    	}
	    	
	    	if(unprocessedStudentRequest.getSecondChoice() == null) {
	    		statementUpdateRequests.setString(2, null);
	    	}
	    	else {
	    		statementUpdateRequests.setString(2, unprocessedStudentRequest.getSecondChoice());
	    	}
	    	
	    	if(unprocessedStudentRequest.getThirdChoice() == null) {
	    		statementUpdateRequests.setString(3, null);
	    	}
	    	else {
	    		statementUpdateRequests.setString(3, unprocessedStudentRequest.getThirdChoice());
	    	}
	    	
	    	statementUpdateRequests.setInt(4, unprocessedStudentRequest.getId());
		    
		    statementUpdateRequests.executeUpdate();
	    }
	}
	
	public boolean semesterContainsUnprocessedRequests(int semester, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
		PreparedStatement statement = null;
		
		statement = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM app_database.requests WHERE `semester` = ? AND `selection_type` IN (?) AND is_accepted IS NULL");
	    
	    statement.setInt(1, semester);
	    statement.setString(2, selectionType.toString());
	    
	    ResultSet result = statement.executeQuery();
	    
	    int rowCount = 0;
	    while (result.next()) {
	    	rowCount = result.getInt("count");
	    }
	    
	    if(rowCount > 0){
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	public void sendEmptyStudentRequest(Student student, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementApproveStudentRequest = connection.prepareStatement("INSERT INTO `app_database`.`requests` (`student_faculty_number`, `semester`, `score`, `selection_type`) VALUES (?, ?, ?, ?)");
        
        statementApproveStudentRequest.setString(1, student.getFacultyNumber());
        statementApproveStudentRequest.setInt(2, student.getCurrentSemester() + 1);
        statementApproveStudentRequest.setFloat(3, student.getGPA());
        statementApproveStudentRequest.setString(4, selectionType.toString());
        
        statementApproveStudentRequest.executeUpdate();
	}
	
	public void loadNotProvidedRequests(int semester, int moduleId, RequestSelectionType selectionType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Student> students = new ArrayList<Student>();
		
		StudentDAO studentDAO = new StudentDAO();
		
		
		students = studentDAO.getAllUsers();

		
		StudentProcessingService studentProcessingService = new StudentProcessingService();
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();
		
		if(moduleId != 0) {
			Module module = new Module();
			
			ModuleDAO moduleDAO = new ModuleDAO();
			
			module = moduleDAO.getModule(moduleId);
			
			for(Student student : students) {
				if((student.getCurrentSemester() + 1) == semester) {
					if(!studentProcessingService.isRequestSendByStudent(student, selectionType)) {
						if(student.getModule().equals(module.getName())) {
							studentRequestProcessingService.sendEmptyStudentRequest(student, selectionType);
						}
					}
				}
			}
		}
		else {
			for(Student student : students) {
				if((student.getCurrentSemester() + 1) == semester) {
					if(!studentProcessingService.isRequestSendByStudent(student, selectionType)) {
							studentRequestProcessingService.sendEmptyStudentRequest(student, selectionType);
					}
				}
			}
		}
	}
	
}
