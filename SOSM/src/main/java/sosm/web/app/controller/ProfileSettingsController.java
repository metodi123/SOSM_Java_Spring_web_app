package sosm.web.app.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.model.Admin;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Student;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.PasswordProcessingService;
import sosm.web.app.service.UpdateUserSettingsService;

@Controller
public class ProfileSettingsController {

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(HttpServletRequest request,
		@RequestParam("password") String password,
		@RequestParam("newPassword") String newPassword,
		@RequestParam("newPasswordRepeat") String newPasswordRepeat,
		@RequestParam("username") String username,
		@RequestParam("currentPassword") String currentPassword,
		RedirectAttributes redirectAttributes) {
		
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

		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(password, username);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		UpdateUserSettingsService updateUserSettingsService = new UpdateUserSettingsService();
		
		if(newPassword.equals(newPasswordRepeat) && hashedPassword.equals(currentPassword)) {
			try {
				updateUserSettingsService.updateUserPassword(username, newPassword);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
				return "redirect:/";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
				return "redirect:/employee";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
				return "redirect:/admin";
			}
		}
		else {
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/settings/changePassword";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/employee/settings/changePassword";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/admin/settings/changePassword";
			}
		}
		redirectAttributes.addAttribute("message", "DatabaseError");
		return "redirect:/error";
	}
	
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
	public String changeEmail(HttpServletRequest request,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("username") String username,
			@RequestParam("currentPassword") String currentPassword,
			RedirectAttributes redirectAttributes) {
		
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

		PasswordProcessingService passwordProcessingService = new PasswordProcessingService();
		
		String hashedPassword = null;
		
		try {
			hashedPassword = passwordProcessingService.getHashedPassword(password, username);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		UpdateUserSettingsService updateUserSettingsService = new UpdateUserSettingsService();
		
		if(hashedPassword.equals(currentPassword)) {
			try {
				if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
					updateUserSettingsService.updateStudentEmail(username, email);
					return "redirect:/";
				}
				else if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
					updateUserSettingsService.updateEmployeeEmail(username, email);
					return "redirect:/employee";
				}
				else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
					updateUserSettingsService.updateAdminEmail(username, email);
					return "redirect:/admin";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
		}
		else {
			if(request.getSession(false).getAttribute(User.currentUser) instanceof Student) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/settings/changeEmail";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Employee) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/employee/settings/changeEmail";
			}
			else if(request.getSession(false).getAttribute(User.currentUser) instanceof Admin) {
				redirectAttributes.addFlashAttribute("message", "InvalidDataEntered");
				return "redirect:/admin/settings/changeEmail";
			}
		}
		redirectAttributes.addAttribute("message", "DatabaseError");
		return "redirect:/error";
	}
	
}
