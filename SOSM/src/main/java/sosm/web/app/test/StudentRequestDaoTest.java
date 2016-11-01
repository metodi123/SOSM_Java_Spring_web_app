package sosm.web.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import sosm.web.app.dao.StudentRequestDAO;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.StudentRequest;

public class StudentRequestDaoTest {
	
	@Test
    public void testGetAllStudentRequests() {
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		
		List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
		
		StudentRequest studentRequest = new StudentRequest();
		
		try {
			studentRequests = studentRequestDAO.getAllStudentRequests();
			studentRequest = studentRequests.get(0);
		} catch (Exception e) {
			fail();
		}
	
		Assert.assertEquals(116, studentRequest.getId());
		Assert.assertEquals("121212005", studentRequest.getFacutyNumber());
		Assert.assertEquals(5, studentRequest.getSemester());
	    Assert.assertEquals(5.71f, studentRequest.getScore(), 0.00f);
	    Assert.assertEquals(RequestSelectionType.courseProject, studentRequest.getSelectionType());
	    Assert.assertEquals("Œ—", studentRequest.getFirstChoice());
	    Assert.assertEquals("œ≈", studentRequest.getSecondChoice());
	    Assert.assertEquals(" ¿", studentRequest.getThirdChoice());
	    Assert.assertEquals("Œ—", studentRequest.getSelected());
	    Assert.assertEquals("2016-06-08 12:40:31.0", studentRequest.getTimestamp().toString());
	    Assert.assertEquals(true , studentRequest.isAccepted());
	}
	
	@Test
    public void testCreateStudentRequest() {
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		
		List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
		
		StudentRequest studentRequest = new StudentRequest();
		StudentRequest studentRequestActual = new StudentRequest();
		
		studentRequest.setFacutyNumber("121212003");
		studentRequest.setSemester(7);
		studentRequest.setScore(5.25f);
		studentRequest.setSelectionType(RequestSelectionType.courseProject);
		studentRequest.setFirstChoice(" Ã");
		studentRequest.setSecondChoice(" √");
		studentRequest.setThirdChoice("œœ");
		
		try {
			studentRequestDAO.createStudentRequest(studentRequest);
			
			studentRequests = studentRequestDAO.getAllStudentRequests();
			for(StudentRequest request : studentRequests) {
				if(request.getFacutyNumber().equals(studentRequest.getFacutyNumber()) &&
					request.getSemester() == studentRequest.getSemester() &&
					request.getSelectionType() == studentRequest.getSelectionType()) {
						
						studentRequestActual = request;
				}
			}
		} catch (Exception e) {
			fail();
		}
	
		Assert.assertEquals(studentRequest.getFacutyNumber(), studentRequestActual.getFacutyNumber());
		Assert.assertEquals(studentRequest.getSemester(), studentRequestActual.getSemester());
		Assert.assertEquals(studentRequest.getScore(), studentRequestActual.getScore(), 0.00f);
		Assert.assertEquals(studentRequest.getSelectionType(), studentRequestActual.getSelectionType());
		Assert.assertEquals(studentRequest.getFirstChoice(), studentRequestActual.getFirstChoice());
		Assert.assertEquals(studentRequest.getSecondChoice(), studentRequestActual.getSecondChoice());
		Assert.assertEquals(studentRequest.getThirdChoice(), studentRequestActual.getThirdChoice());
	}
	
	@Test
    public void testApproveStudentRequest() {
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		
		List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
		
		StudentRequest studentRequestActual = new StudentRequest();
		
		try {
			studentRequestDAO.approveStudentRequest(230, " √");
			
			studentRequests = studentRequestDAO.getAllStudentRequests();
			for(StudentRequest request : studentRequests) {
				if(request.getId() == 230) {
						
						studentRequestActual = request;
				}
			}
		} catch (Exception e) {
			fail();
		}
	
		Assert.assertEquals(" √", studentRequestActual.getSelected());
		Assert.assertEquals(true, studentRequestActual.isAccepted());
	}
	
}
