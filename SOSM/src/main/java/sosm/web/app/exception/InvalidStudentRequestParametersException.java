package sosm.web.app.exception;

public class InvalidStudentRequestParametersException extends Exception {
	
	public InvalidStudentRequestParametersException(){  
		
	} 
	
	public InvalidStudentRequestParametersException(String message){  
		super(message);
	}
	
}
