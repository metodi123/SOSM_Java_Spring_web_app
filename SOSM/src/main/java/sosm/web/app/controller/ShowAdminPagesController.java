package sosm.web.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.AdminDAO;
import sosm.web.app.dao.EmployeeDAO;
import sosm.web.app.exception.InvalidUserParametersException;
import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.UserParametersValidationService;

@Controller
@RequestMapping(value = "/admin")
public class ShowAdminPagesController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String homeAdmin(HttpServletRequest request, RedirectAttributes redirectAttributes) {

		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "home-admin";
		}
		else {
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
				return "redirect:/";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
				return "redirect:/employee";
			}
			
			AdminDAO adminDAO = new AdminDAO();
			
			Admin admin = new Admin();
			
			admin = (Admin) request.getSession(false).getAttribute(User.currentUser);
			
			try {
				admin = adminDAO.getUser(admin.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			
			request.getSession().setAttribute(User.currentUser, admin);
			
			return "profile-main-admin";
		}
		
	}
	
	@RequestMapping(value = "/showEmployees", method = RequestMethod.GET)
	public String showEmployees(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		List<Employee> employees = new ArrayList<Employee>();
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employees = employeeDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("employees", employees);
		
		return "show-employees-admin";
	}
	
	@RequestMapping(value = "/showAdmins", method = RequestMethod.GET)
	public String showAdmins(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
	
		List<Admin> admins = new ArrayList<Admin>();
		
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			admins = adminDAO.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("admins", admins);
		
		return "show-admins-admin";
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
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
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
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
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
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		return "account-change-email";
	}
	
	@RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
	public String editEmployee(HttpServletRequest request, Model model,
		@RequestParam("nickname") String nickname,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
	
		Employee employee = new Employee();
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employee = employeeDAO.getUser(nickname);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("employee", employee);
		
		return "edit-employee-data-admin";
	}
	
	@RequestMapping(value = "/editAdmin", method = RequestMethod.GET)
	public String editAdmin(HttpServletRequest request, Model model,
		@RequestParam("nickname") String nickname,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
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
	
		Admin admin = new Admin();
		
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			admin = adminDAO.getUser(nickname);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		model.addAttribute("admin", admin);
		
		return "edit-admin-data-admin";
	}
	
	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
	public String createEmployee(HttpServletRequest request,
		@RequestParam("username") String username,
		RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if(request.getSession(false) == null) {
			return "redirect:/admin";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Admin)) {
			return "redirect:/error403";
		}
		
		Employee employee = new Employee();
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		
		try {
			employee = employeeDAO.getUser(username);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		} 
		
		if(employee.getUsername() != null) {
			redirectAttributes.addFlashAttribute("message", "UserAlreadyExists");
			return "redirect:/admin/showEmployees";
		}
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateParameter(username, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		} catch (InvalidUserParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidUsername");
			return "redirect:/admin/showEmployees";
		}
		
		return "create-employee-data-admin";
	}
	
	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public String createAdmin(HttpServletRequest request, @RequestParam("username") String username, RedirectAttributes redirectAttributes) {
		
		try {
			DatabaseConnectionService.connectToAppDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
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
		
		Admin admin = new Admin();
		
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			admin = adminDAO.getUser(username);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		} 
		
		if(admin.getUsername() != null) {
			redirectAttributes.addFlashAttribute("message", "UserAlreadyExists");
			return "redirect:/admin/showAdmins";
		}
		
		UserParametersValidationService userParametersValidationService = new UserParametersValidationService();
		
		try {
			userParametersValidationService.validateParameter(username, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		} catch (InvalidUserParametersException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "InvalidUsername");
			return "redirect:/admin/showAdmins";
		}
		
		return "create-admin-data-admin";
	}
	
}
