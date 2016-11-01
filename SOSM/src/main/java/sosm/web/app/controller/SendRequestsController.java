package sosm.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.StudentRequestDAO;
import sosm.web.app.exception.InvalidStudentRequestParametersException;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequest;
import sosm.web.app.model.User;
import sosm.web.app.service.StudentRequestParametersValidationService;

@Controller
public class SendRequestsController {
	
	@RequestMapping(value = "/sendStudentRequests", method = RequestMethod.POST)
	public String sendStudentRequests(HttpServletRequest request,
		@RequestParam(value = "elective-first-choice", required = false) String electiveFirstChoice,
		@RequestParam(value = "elective-second-choice", required = false) String electiveSecondChoice,
		@RequestParam(value = "elective-third-choice", required = false) String electiveThirdChoice,
		@RequestParam(value = "course-project-first-choice", required = false) String courseProjectFirstChoice,
		@RequestParam(value = "course-project-second-choice", required = false) String courseProjectSecondChoice,
		@RequestParam(value = "course-project-third-choice", required = false) String courseProjectThirdChoice,
		@RequestParam(value = "course-work-first-choice", required = false) String courseWorkFirstChoice,
		@RequestParam(value = "course-work-second-choice", required = false) String courseWorkSecondChoice,
		@RequestParam(value = "course-work-third-choice", required = false) String courseWorkThirdChoice,
		@RequestParam(value = "module-first-choice", required = false) String moduleFirstChoice,
		@RequestParam(value = "module-second-choice", required = false) String moduleSecondChoice,
		@RequestParam(value = "module-third-choice", required = false) String moduleThirdChoice,
		@RequestParam(value = "requestSemester", required = false) String semester,
		RedirectAttributes redirectAttributes) {

		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Student)) {
			return "redirect:/error403";
		}
		
		if(courseWorkThirdChoice == null) {
			courseWorkThirdChoice = "";
		}
		
		Student student = new Student();
		
		student = (Student) request.getSession(false).getAttribute(User.currentUser);
		
		StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
		
		StudentRequestParametersValidationService studentRequestParametersValidationService = new StudentRequestParametersValidationService();
		
		StudentRequest studentRequestForElective = new StudentRequest();
		StudentRequest studentRequestForCourseProject = new StudentRequest();
		StudentRequest studentRequestForCourseWork = new StudentRequest();
		StudentRequest studentRequestForModule = new StudentRequest();
		
		if(electiveFirstChoice != null) {
			studentRequestForElective.setFacutyNumber(student.getFacultyNumber());
			studentRequestForElective.setSemester(student.getCurrentSemester() + 1);
			studentRequestForElective.setScore(student.getGPA());
			studentRequestForElective.setSelectionType(RequestSelectionType.electiveCourse);
			studentRequestForElective.setFirstChoice(electiveFirstChoice);
			studentRequestForElective.setSecondChoice(electiveSecondChoice);
			studentRequestForElective.setThirdChoice(electiveThirdChoice);
			
			try {
				studentRequestParametersValidationService.validateStudentRequestParameters(studentRequestForElective);
			} catch (InvalidStudentRequestParametersException e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.isNotSelected(electiveFirstChoice)) {
				redirectAttributes.addAttribute("message", "EmptyFirstChoice");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.containsEqualStrings(electiveFirstChoice, electiveSecondChoice, electiveThirdChoice)) {
				redirectAttributes.addAttribute("message", "ChoicesEquality");
				return "redirect:/error";
			}
		}
		
		if(courseProjectFirstChoice != null) {
			studentRequestForCourseProject.setFacutyNumber(student.getFacultyNumber());
			studentRequestForCourseProject.setSemester(student.getCurrentSemester() + 1);
			studentRequestForCourseProject.setScore(student.getGPA());
			studentRequestForCourseProject.setSelectionType(RequestSelectionType.courseProject);
			studentRequestForCourseProject.setFirstChoice(courseProjectFirstChoice);
			studentRequestForCourseProject.setSecondChoice(courseProjectSecondChoice);
			studentRequestForCourseProject.setThirdChoice(courseProjectThirdChoice);
			
			try {
				studentRequestParametersValidationService.validateStudentRequestParameters(studentRequestForCourseProject);
			} catch (InvalidStudentRequestParametersException e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.isNotSelected(courseProjectFirstChoice)) {
				redirectAttributes.addAttribute("message", "EmptyFirstChoice");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.containsEqualStrings(courseProjectFirstChoice, courseProjectSecondChoice, courseProjectThirdChoice)) {
				redirectAttributes.addAttribute("message", "ChoicesEquality");
				return "redirect:/error";
			}
		}
		
		if(courseWorkFirstChoice != null) {
			studentRequestForCourseWork.setFacutyNumber(student.getFacultyNumber());
			studentRequestForCourseWork.setSemester(student.getCurrentSemester() + 1);
			studentRequestForCourseWork.setScore(student.getGPA());
			studentRequestForCourseWork.setSelectionType(RequestSelectionType.courseWork);
			studentRequestForCourseWork.setFirstChoice(courseWorkFirstChoice);
			studentRequestForCourseWork.setSecondChoice(courseWorkSecondChoice);
			studentRequestForCourseWork.setThirdChoice(courseWorkThirdChoice);
			
			try {
				studentRequestParametersValidationService.validateStudentRequestParameters(studentRequestForCourseWork);
			} catch (InvalidStudentRequestParametersException e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.isNotSelected(courseWorkFirstChoice)) {
				redirectAttributes.addAttribute("message", "EmptyFirstChoice");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.containsEqualStrings(courseWorkFirstChoice, courseWorkSecondChoice, courseWorkThirdChoice)) {
				redirectAttributes.addAttribute("message", "ChoicesEquality");
				return "redirect:/error";
			}
		}
		
		if(moduleFirstChoice != null) {
			studentRequestForModule.setFacutyNumber(student.getFacultyNumber());
			studentRequestForModule.setSemester(student.getCurrentSemester() + 1);
			studentRequestForModule.setScore(student.getGPA());
			studentRequestForModule.setSelectionType(RequestSelectionType.module);
			studentRequestForModule.setFirstChoice(moduleFirstChoice);
			studentRequestForModule.setSecondChoice(moduleSecondChoice);
			studentRequestForModule.setThirdChoice(moduleThirdChoice);
			
			try {
				studentRequestParametersValidationService.validateStudentRequestParameters(studentRequestForModule);
			} catch (InvalidStudentRequestParametersException e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			if(studentRequestParametersValidationService.isNotSelected(moduleFirstChoice)) {
				redirectAttributes.addAttribute("message", "EmptyFirstChoice");
				return "redirect:/error";
			}
			if(studentRequestParametersValidationService.containsEqualStrings(moduleFirstChoice, moduleSecondChoice, moduleThirdChoice)) {
				redirectAttributes.addAttribute("message", "ChoicesEquality");
				return "redirect:/error";
			}
		}

		if(electiveFirstChoice != null) {
			try {
				studentRequestDAO.createStudentRequest(studentRequestForElective);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		
		if(courseProjectFirstChoice != null) {
			try {
				studentRequestDAO.createStudentRequest(studentRequestForCourseProject);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		
		if(courseWorkFirstChoice != null) {
			try {
				studentRequestDAO.createStudentRequest(studentRequestForCourseWork);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		
		if(moduleFirstChoice != null) {
			try {
				studentRequestDAO.createStudentRequest(studentRequestForModule);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		
		return "redirect:/";
	}
	
}
