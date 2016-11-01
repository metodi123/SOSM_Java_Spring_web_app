package sosm.web.app.service;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidInfoTextException;

@Service
public class InfoTextValidationService {

	public static final int MAX_LENGTH = 1000;

	public void validateInfoText(String infoText) throws InvalidInfoTextException {
		
		if(infoText.length() > MAX_LENGTH) {
			throw new InvalidInfoTextException("Parameter value out of range");
		}
		
	}
	
}
