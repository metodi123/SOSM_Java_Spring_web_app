package sosm.web.app.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.CourseDAO;
import sosm.web.app.dao.ModuleDAO;
import sosm.web.app.dao.SelectionsStateDAO;
import sosm.web.app.dao.StudentDAO;
import sosm.web.app.dao.StudentRequestDAO;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Course;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Module;
import sosm.web.app.model.SelectionsState;
import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequest;
import sosm.web.app.model.StudentRequestsState;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.InfoTextService;
import sosm.web.app.service.StudentProcessingService;
import sosm.web.app.service.StudentRequestProcessingService;
import sosm.web.app.service.StudentRequestsStateService;

@Controller
public class ShowStudentPagesController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "home";
		}
		else {
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
				return "redirect:/employee";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
				return "redirect:/admin";
			}
			
			StudentDAO studentDAO = new StudentDAO();
			
			Student student = new Student();
			
			student = (Student) request.getSession(false).getAttribute(User.currentUser);
			
			try {
				student = studentDAO.getUser(student.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute(User.currentUser, student);
			
			StudentRequestsState studentRequestsState = new StudentRequestsState();
			
			StudentRequestsStateService studentRequestsStateService = new StudentRequestsStateService();
			
			try {
				studentRequestsState = studentRequestsStateService.getStudentRequestsState(student);
			} catch (Exception e1) {
				e1.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			List<StudentRequest> studentsRequests = new ArrayList<StudentRequest>();
			
			StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
			
			try {
				studentsRequests = studentRequestDAO.getAllStudentRequests();
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
			
			InfoTextService infoTextService = new InfoTextService();
			
			String infoText = "";
			
			try {
				infoText = infoTextService.getInfoText();
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			model.addAttribute("infoText", infoText);
			model.addAttribute("studentRequestsState", studentRequestsState);
			model.addAttribute("studentsRequests", studentsRequests);
			
			return "profile-main-student";
		}
		
	}
	
	@RequestMapping(value = "/sendRequest", method = RequestMethod.GET)
	public String sendRequest(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Student)) {
			return "redirect:/error403";
		}
		
		Student student = new Student();
		student = (Student) request.getSession().getAttribute(User.currentUser);
			
		StudentProcessingService studentProcessingService = new StudentProcessingService();
			
		try {
			if(!studentProcessingService.isStudentAvailableForSendingRequest(student)) {
				if(student.getCurrentSemester() == 1 || student.getCurrentSemester() == 2 || student.getCurrentSemester() == 7 || student.getCurrentSemester() == 8) {
					redirectAttributes.addFlashAttribute("message", "NoRequestsForNextSemester");
					return "redirect:/";
				}		
				redirectAttributes.addFlashAttribute("message", "StudentUnavaliableToSendRequest");
				return "redirect:/";
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
			
		SelectionsState selectionsState = new SelectionsState();
			
		SelectionsStateDAO selectionsStateDAO = new SelectionsStateDAO();
			
		try {
			selectionsState = selectionsStateDAO.getSelectionsState();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
			
		model.addAttribute("selectionsState", selectionsState);
		
		StudentRequestsState studentRequestsState = new StudentRequestsState();
		
		StudentRequestsStateService studentRequestsStateService = new StudentRequestsStateService();
		
		try {
			studentRequestsState = studentRequestsStateService.getStudentRequestsState(student);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(student.getCurrentSemester() == 5) {
			if((studentRequestsState.isRequestSendByStudentForModules() == true) && (selectionsState.isSelectionOpenForSemester6() == false)) {
				redirectAttributes.addFlashAttribute("message", "RequestAlreadySent");
				return "redirect:/";
			}
			else if((studentRequestsState.isRequestSendByStudentForModules() == true) && (studentRequestsState.isRequestSendByStudentForCourses() == true)) {
				redirectAttributes.addFlashAttribute("message", "RequestAlreadySent");
				return "redirect:/";
			}
		}
		else {
			if(studentRequestsState.isRequestSendByStudentForCourses() == true) {
				redirectAttributes.addFlashAttribute("message", "RequestAlreadySent");
				return "redirect:/";
			}
		}
		
		List<Course> courses = new ArrayList<Course>();
			
		CourseDAO courseDAO = new CourseDAO();
			
		try {
			courses = courseDAO.getAllCourses();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
			
		model.addAttribute("courses", courses);
			
		List<Module> modules = new ArrayList<Module>();
			
		ModuleDAO moduleDAO = new ModuleDAO();
			
		try {
			modules = moduleDAO.getAllModules();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
			
		model.addAttribute("modules", modules);
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();

		HashMap<String, Boolean> semestersContainingCoursesBySelectionType = new HashMap<String, Boolean>();
		
		try {
			semestersContainingCoursesBySelectionType = studentRequestProcessingService.semestersContainingCoursesBySelectionType();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("semestersContainingCoursesBySelectionType", semestersContainingCoursesBySelectionType);
			
		return "send-request-student";
		
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settings(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Student)) {
			return "redirect:/error403";
		}
		
		return "account-settings";
	}
	
	@RequestMapping(value = "/settings/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Student)) {
			return "redirect:/error403";
		}
	
		return "account-change-password";
	}
	
	@RequestMapping(value = "/settings/changeEmail", method = RequestMethod.GET)
	public String changeEmail(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Student)) {
			return "redirect:/error403";
		}
		
		return "account-change-email";
	}
	
}
