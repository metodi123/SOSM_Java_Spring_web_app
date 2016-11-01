package sosm.web.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "error";
	}
	
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Resource is forbidden")
	@RequestMapping(value = "/error403", method = RequestMethod.GET)
	public void error403() {
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Resource not found")
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public void error404() {
		
	}
	
}
