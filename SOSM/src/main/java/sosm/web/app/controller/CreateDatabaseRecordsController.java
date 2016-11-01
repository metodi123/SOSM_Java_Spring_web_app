package sosm.web.app.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.AdminDAO;
import sosm.web.app.dao.CourseDAO;
import sosm.web.app.dao.EmployeeDAO;
import sosm.web.app.dao.ModuleDAO;
import sosm.web.app.dao.StudentDAO;
import sosm.web.app.exception.InvalidCourseParametersException;
import sosm.web.app.exception.InvalidModuleParametersException;
import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Course;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Module;
import sosm.web.app.model.Student;
import sosm.web.app.model.User;
import sosm.web.app.service.CourseParametersValidationService;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.ModuleParametersValidationService;
import sosm.web.app.service.PasswordProcessingService;
import sosm.web.app.service.UserParametersValidationService;

@Controller
public class CreateDatabaseRecordsController {

	@RequestMapping(value = "/employee/createStudentRecord", method = RequestMethod.POST)
	public String createStudentRecord(HttpServletRequest request,
		@RequestParam("facultyNumber") String facultyNumber,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("currentSemester") int currentSemester,
		@RequestParam("gpa") float gpa,
		@RequestParam("group") int group,
		@RequestParam("module") String module,
		@RequestParam("email") String email,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(facultyNumber, facultyNumber);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Student student = new Student();
		
		student.setUsername(facultyNumber);
		student.setPassword(hashedPassword);
		student.setFacultyNumber(facultyNumber);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setCurrentSemester(currentSemester);
		student.setGPA(gpa);
		student.setGroup(group);
		if(module.equals("noModule")) {
			student.setModule("");
		}
		else {
			student.setModule(module);
		}
		student.setEmail(email);
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateUserParameters(student, false);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createStudent";
		}
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			studentDAO.createUser(student);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showStudents";
	}
	
	@RequestMapping(value = "/employee/createCourseRecord", method = RequestMethod.POST)
	public String createCourseRecord(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("fullName") String fullName,
		@RequestParam("semester") int semester,
		@RequestParam("module") String module,
		@RequestParam("electiveCourse") boolean electiveCourse,
		@RequestParam("courseWork") boolean courseWork,
		@RequestParam("courseProject") boolean courseProject,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Course course = new Course();
		
		course.setName(name);
		course.setFullName(fullName);
		course.setSemester(semester);
		if(module.equals("noModule")) {
			course.setModuleName("");
		}
		else {
			course.setModuleName(module);
		}
		course.setElective(electiveCourse);
		course.setCourseWork(courseWork);
		course.setCourseProject(courseProject);
		
		CourseParametersValidationService courseParametersValidationService = new CourseParametersValidationService();
		
		try {
			courseParametersValidationService.validateCourseParameters(course, true);
		} catch (InvalidCourseParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createCourse";
		}
		
		CourseDAO courseDAO = new CourseDAO();
		
		try {
			courseDAO.createCourse(course);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showCourses";
	}
	
	@RequestMapping(value = "/employee/createModuleRecord", method = RequestMethod.POST)
	public String createModuleRecord(HttpServletRequest request,
		@RequestParam("name") String name,
		@RequestParam("fullName") String fullName,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Module module = new Module();
		
		module.setName(name);
		module.setFullName(fullName);
		
		ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
		
		try {
			moduleParametersValidationService.validateModuleParameters(module, true);
		} catch (InvalidModuleParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createModule";
		}
			
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			moduleDAO.createModule(module);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		} 
		
		return "redirect:/employee/showModules";
	}
	
	@RequestMapping(value = "/admin/createEmployeeRecord", method = RequestMethod.POST)
	public String createEmployeeRecord(HttpServletRequest request,
		@RequestParam("username") String username,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("email") String email,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(username, username);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Employee employee = new Employee();
		
		employee.setUsername(username);
		employee.setPassword(hashedPassword);
		employee.setNickname(username);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateUserParameters(employee, false);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createEmployee";
		}
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employeeDAO.createUser(employee);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/createAdminRecord", method = RequestMethod.POST)
	public String createAdminRecord(HttpServletRequest request,
		@RequestParam("username") String username,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("email") String email,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Admin currentUser = new Admin();
		currentUser = (Admin) request.getSession(false).getAttribute(User.currentUser);
		
		if(!currentUser.isMasterAdmin()) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(username, username);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Admin admin = new Admin();
		
		admin.setUsername(username);
		admin.setPassword(hashedPassword);
		admin.setNickname(username);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setEmail(email);
		admin.setMasterAdmin(false);
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		
		try {
			userParametersValidationService.validateUserParameters(admin, false);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			return "redirect:/employee/createAdmin";
		}

		AdminDAO adminDAO = new AdminDAO();
		
		
		try {
			adminDAO.createUser(admin);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		return "redirect:/admin/showAdmins";
	}
	
}
