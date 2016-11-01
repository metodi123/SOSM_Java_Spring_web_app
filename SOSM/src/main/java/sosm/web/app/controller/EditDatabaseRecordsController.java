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
import sosm.web.app.service.UserParametersValidationService;

@Controller
public class EditDatabaseRecordsController {

	@RequestMapping(value = "/employee/editStudentRecord", method = RequestMethod.POST)
	public String editStudentRecord(HttpServletRequest request,
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
		
		Student student = new Student();
		
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
			userParametersValidationService.validateUserParameters(student, true);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			redirectAttributes.addAttribute("facultyNumber", facultyNumber);
			return "redirect:/employee/editStudent";
		}
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			studentDAO.updateStudentInfo(student);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showStudents";
	}
	
	@RequestMapping(value = "/employee/editCourseRecord", method = RequestMethod.POST)
	public String editCourseRecord(HttpServletRequest request,
		@RequestParam("id") int id,
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
		
		course.setId(id);
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
			courseParametersValidationService.validateCourseParameters(course, false);
		} catch (InvalidCourseParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			redirectAttributes.addAttribute("id", id);
			return "redirect:/employee/editCourse";
		}
		
		CourseDAO courseDAO = new CourseDAO();
		
		try {
			courseDAO.updateCourseInfo(course);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showCourses";
	}
	
	@RequestMapping(value = "/employee/editModuleRecord", method = RequestMethod.POST)
	public String editModuleRecord(HttpServletRequest request,
		@RequestParam("id") int id,
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
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		Module module = new Module();
		
		module.setId(id);
		module.setName(name);
		module.setFullName(fullName);
		
		ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
		
		try {
			moduleParametersValidationService.validateModuleParameters(module, false);
		} catch (InvalidModuleParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			redirectAttributes.addAttribute("id", id);
			return "redirect:/employee/editModule";
		}
			
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			moduleDAO.updateModuleInfo(module);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		} 
		
		return "redirect:/employee/showModules";
	}
	
	@RequestMapping(value = "/admin/editEmployeeRecord", method = RequestMethod.POST)
	public String editEmployeeRecord(HttpServletRequest request,
		@RequestParam("nickname") String nickname,
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
		
		Employee employee = new Employee();
		
		employee.setNickname(nickname);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateUserParameters(employee, true);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			redirectAttributes.addAttribute("nickname", nickname);
			return "redirect:/employee/editEmployee";
		}
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employeeDAO.updateEmployeeInfo(employee);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/editAdminRecord", method = RequestMethod.POST)
	public String editAdminRecord(HttpServletRequest request,
		@RequestParam("nickname") String nickname,
		@RequestParam("firstName") String firstName,
		@RequestParam("lastName") String lastName,
		@RequestParam("email") String email,
		@RequestParam("masterAdmin") boolean masterAdmin,
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
		
		Admin admin = new Admin();
		
		admin.setNickname(nickname);
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setEmail(email);
		admin.setMasterAdmin(masterAdmin);
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateUserParameters(admin, true);
		} catch (InvalidUserParametersException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
			redirectAttributes.addAttribute("nickname", nickname);
			return "redirect:/employee/editAdmin";
		}

		AdminDAO adminDAO = new AdminDAO();
		
		try {
			adminDAO.updateAdminInfo(admin);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		return "redirect:/admin/showAdmins";
	}
	
}
