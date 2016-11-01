package sosm.web.app.service;

import org.springframework.stereotype.Service;

import sosm.web.app.exception.InvalidSelectionsStateParametersException;
import sosm.web.app.model.SelectionsState;

@Service
public class SelectionsStateValidationService {

	public void validateSelectionsState(SelectionsState selectionsState) throws InvalidSelectionsStateParametersException {
		if((selectionsState.isSelectionOpenForSemester6() == true) && (selectionsState.isSelectionOpenModules() == true)) {
			throw new InvalidSelectionsStateParametersException("Invalid parameters entered");
		}
	}
	
}
