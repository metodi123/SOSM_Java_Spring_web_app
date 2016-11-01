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
import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;

@Controller
public class RemoveDatabaseRecordsController {

	@RequestMapping(value = "/employee/deleteStudentRecord", method = RequestMethod.POST)
	public String deleteStudentRecord(HttpServletRequest request,
		@RequestParam("facultyNumber") String facultyNumber,
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
		
		StudentDAO studentDAO = new StudentDAO();
		
		try {
			studentDAO.deleteUser(facultyNumber);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showStudents";
	}
	
	@RequestMapping(value = "/employee/deleteCourseRecord", method = RequestMethod.POST)
	public String deleteCourseRecord(HttpServletRequest request,
		@RequestParam("id") int id,
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
		
		CourseDAO courseDAO = new CourseDAO();
		
		try {
			courseDAO.deleteCourse(id);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showCourses";
	}
	
	@RequestMapping(value = "/employee/deleteModuleRecord", method = RequestMethod.POST)
	public String deleteModuleRecord(HttpServletRequest request,
		@RequestParam("id") int id,
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
		
		ModuleDAO moduleDAO = new ModuleDAO();
		
		try {
			moduleDAO.deleteModule(id);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee/showModules";
	}
	
	@RequestMapping(value = "/admin/deleteEmployeeRecord", method = RequestMethod.POST)
	public String deleteEmployeeRecord(HttpServletRequest request,
		@RequestParam("nickname") String nickname,
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
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employeeDAO.deleteUser(nickname);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/admin/showEmployees";
	}
	
	@RequestMapping(value = "/admin/deleteAdminRecord", method = RequestMethod.POST)
	public String deleteAdminRecord(HttpServletRequest request,
		@RequestParam("nickname") String nickname,
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
		
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			adminDAO.deleteUser(nickname);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/admin/showAdmins";
	}
	
}
