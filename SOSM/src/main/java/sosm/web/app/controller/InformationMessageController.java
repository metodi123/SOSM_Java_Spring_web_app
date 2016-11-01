package sosm.web.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.model.Employee;
import sosm.web.app.model.User;
import sosm.web.app.service.InfoTextService;

@Controller
@RequestMapping(value = "/employee")
public class InformationMessageController {

	@RequestMapping(value = "/saveInfoMessage", method = RequestMethod.POST)
	public String saveInfoMessage(HttpServletRequest request, @RequestParam("infoText") String infoText, RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		InfoTextService infoTextService = new InfoTextService();
		
		try {
			infoTextService.setInfoText(infoText);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		return "redirect:/employee";
	}
	
}
