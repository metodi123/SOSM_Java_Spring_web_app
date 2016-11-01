package sosm.web.app.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.CourseDAO;
import sosm.web.app.dao.EmployeeDAO;
import sosm.web.app.dao.ModuleDAO;
import sosm.web.app.dao.SelectionsStateDAO;
import sosm.web.app.dao.StudentDAO;
import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Course;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Module;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.SelectionsState;
import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequest;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.InfoTextService;
import sosm.web.app.service.StudentRequestProcessingService;
import sosm.web.app.service.UserParametersValidationService;

@Controller
@RequestMapping(value = "/employee")
public class ShowEmployeePagesController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String homeEmployee(HttpServletRequest request, RedirectAttributes redirectAttributes) {
	
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "home-employee";
		}
		else {
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
				return "redirect:/";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
				return "redirect:/admin";
			}
			
			EmployeeDAO employeeDAO = new EmployeeDAO();
			
			Employee employee = new Employee();
			
			employee = (Employee) request.getSession(false).getAttribute(User.currentUser);
			
			try {
				employee = employeeDAO.getUser(employee.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute(User.currentUser, employee);
			
			SelectionsState selectionsState = new SelectionsState();
			
			SelectionsStateDAO selectionsStateDAO = new SelectionsStateDAO();
				
			try {
				selectionsState = selectionsStateDAO.getSelectionsState();
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute("selectionsState", selectionsState);
			
			return "profile-main-employee";
		}
	}
	
	@RequestMapping(value = "/selectRequests", method = RequestMethod.GET)
	public String selectRequests(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "select-requests-employee";
	}
	
	@RequestMapping(value = "/showRequests", method = RequestMethod.GET)
	public String showRequests(HttpServletRequest request, Model model,
		@ModelAttribute("requestsState") String requestsState,
		@ModelAttribute("requestsSemester") List<String> requestsSemester,
		@ModelAttribute("requestsType") List<String> requestsType,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();
		
		try {
			studentRequests = studentRequestProcessingService.selectStudentRequests(requestsState, requestsSemester, requestsType);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("studentsRequests", studentRequests);

		return "show-requests-employee";
	}
	
	@RequestMapping(value = "/chooseRequestsToProcess", method = RequestMethod.GET)
	public String chooseRequestsToProcess(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();

		HashMap<String, Boolean> semestersContainingCoursesBySelectionType = new HashMap<String, Boolean>();
		
		try {
			semestersContainingCoursesBySelectionType = studentRequestProcessingService.semestersContainingCoursesBySelectionType();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		List<Module> modules = new ArrayList<Module>();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			modules = moduleDAO.getAllModules();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("semestersContainingCoursesBySelectionType", semestersContainingCoursesBySelectionType);
		model.addAttribute("modules", modules);
		
		return "choose-requests-to-process-employee";
	}
	
	@RequestMapping(value = "/allocateRequests", method = RequestMethod.GET)
	public String allocateRequests(HttpServletRequest request, Model model,
		@ModelAttribute("selectionType") String selectionType,
		@ModelAttribute("semester") int semester,
		@ModelAttribute("moduleId") int moduleId,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();
		
		int numberOfRequests = 0;
		
		HashMap<String, Integer> numberOfRequestsForCourseFirstChoice = new HashMap<String, Integer>();
		HashMap<String, Integer> numberOfRequestsForModuleFirstChoice = new HashMap<String, Integer>();
		
		Module module = new Module();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		if(moduleId != 0) {
			try {
				module = moduleDAO.getModule(moduleId);
			} catch (Exception e1) {
				e1.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
		}
		else {
			module.setId(0);
		}
		
		try {
			numberOfRequests = studentRequestProcessingService.getNumberOfRequests(RequestSelectionType.valueOf(selectionType), semester, moduleId);
			numberOfRequestsForCourseFirstChoice = studentRequestProcessingService.getNumberOfRequestsForCourseFirstChoice(RequestSelectionType.valueOf(selectionType), semester, moduleId);
			numberOfRequestsForModuleFirstChoice = studentRequestProcessingService.getNumberOfRequestsForModuleFirstChoice(RequestSelectionType.valueOf(selectionType), semester);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("selectionType", selectionType);
		model.addAttribute("semester", semester);
		model.addAttribute("moduleId", module.getId());
		model.addAttribute("numberOfRequestsForCourseFirstChoice", numberOfRequestsForCourseFirstChoice);
		model.addAttribute("numberOfRequestsForModuleFirstChoice", numberOfRequestsForModuleFirstChoice);
		model.addAttribute("numberOfRequests", numberOfRequests);
		
		return "process-requests-employee";
	}
	
	@RequestMapping(value = "/showStudents", method = RequestMethod.GET)
	public String showStudents(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
		List<Student> students = new ArrayList<Student>();
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			students = studentDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("students", students);
		
		return "show-students-employee";
	}
	
	@RequestMapping(value = "/showCourses", method = RequestMethod.GET)
	public String showCourses(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
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
		
		return "show-courses-employee";
	}
	
	@RequestMapping(value = "/showModules", method = RequestMethod.GET)
	public String showModules(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
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
		
		return "show-modules-employee";
	}
	
	@RequestMapping(value = "/writeInformationMessage", method = RequestMethod.GET)
	public String writeInformationMessage(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
		InfoTextService infoTextService = new InfoTextService();
		
		String infoText = null;
		
		try {
			infoText = infoTextService.getInfoText();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("infoText", infoText);
		
		return "info-message-employee";
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
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
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
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
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
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "account-change-email";
	}
	
	@RequestMapping(value = "/editStudent", method = RequestMethod.GET)
	public String editStudent(HttpServletRequest request, Model model,
		@RequestParam("facultyNumber") String facultyNumber,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Student student = new Student();
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			student = studentDAO.getUser(facultyNumber);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
	
		List<Module> modules = new ArrayList<Module>();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			modules = moduleDAO.getAllModules();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("student", student);
		model.addAttribute("modules", modules);
		
		return "edit-student-data-employee";
	}
	
	@RequestMapping(value = "/editCourse", method = RequestMethod.GET)
	public String editCourse(HttpServletRequest request, Model model,
		@RequestParam("id") int courseId,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
		Course course = new Course();
		
		CourseDAO courseDAO = new CourseDAO();
		
		try {
			course = courseDAO.getCourse(courseId);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
	
		List<Module> modules = new ArrayList<Module>();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			modules = moduleDAO.getAllModules();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("course", course);
		model.addAttribute("modules", modules);
		
		return "edit-course-data-employee";
	}
	
	@RequestMapping(value = "/editModule", method = RequestMethod.GET)
	public String editModule(HttpServletRequest request, Model model,
		@RequestParam("id") int moduleId,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
		Module module = new Module();
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			module = moduleDAO.getModule(moduleId);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		model.addAttribute("module", module);
		
		return "edit-module-data-employee";
	}
	
	@RequestMapping(value = "/createStudent", method = RequestMethod.GET)
	public String createStudent(HttpServletRequest request, Model model,
		@RequestParam("facultyNumber") String facultyNumber,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Student student = new Student();
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			student = studentDAO.getUser(facultyNumber);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		} 
		
		if(student.getUsername() != null) {
			redirectAttributes.addFlashAttribute("message", "UserAlreadyExists");
			return "redirect:/employee/showStudents";
		}
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateParameter(facultyNumber, UserParametersValidationService.FACULTY_NUMBER_PARAMETERS_LENGTH);
		}catch (InvalidUserParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidFacultyNumber");
			return "redirect:/employee/showStudents";
		}
		catch (NumberFormatException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidFacultyNumber");
			return "redirect:/employee/showStudents";
		}
	
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
		
		return "create-student-data-employee";
	}
	
	@RequestMapping(value = "/createCourse", method = RequestMethod.GET)
	public String createCourse(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
	
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
		
		return "create-course-data-employee";
	}
	
	@RequestMapping(value = "/createModule", method = RequestMethod.GET)
	public String createModule(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		return "create-module-data-employee";
	}
	
}
