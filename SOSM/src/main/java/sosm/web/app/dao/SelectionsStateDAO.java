package sosm.web.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import sosm.web.app.exception.InvalidSelectionsStateParametersException;
import sosm.web.app.model.SelectionsState;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.SelectionsStateValidationService;

@Repository
public class SelectionsStateDAO implements SelectionsStateDataAccess {

	@Override
	public SelectionsState getSelectionsState() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InvalidSelectionsStateParametersException {
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectionsState = connection.prepareStatement("SELECT * FROM app_database.selections_state WHERE id= '1'");
        
        ResultSet result = statementSelectionsState.executeQuery();
        
        SelectionsState selectionsState = new SelectionsState();
        
        while (result.next()) {
        	selectionsState.setSelectionOpenForSemester4(result.getBoolean("is_selection_open_semester_4"));
        	selectionsState.setSelectionOpenForSemester5(result.getBoolean("is_selection_open_semester_5"));
        	selectionsState.setSelectionOpenForSemester6(result.getBoolean("is_selection_open_semester_6"));
        	selectionsState.setSelectionOpenForSemester7(result.getBoolean("is_selection_open_semester_7"));
        	selectionsState.setSelectionOpenModules(result.getBoolean("is_selection_open_module"));
        }
        
        SelectionsStateValidationService selectionsStateValidationService = new SelectionsStateValidationService();
        selectionsStateValidationService.validateSelectionsState(selectionsState);
        
		return selectionsState;
	}
	
	@Override
	public void setSelectionsState(SelectionsState selectionsState) throws InvalidSelectionsStateParametersException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		SelectionsStateValidationService selectionsStateValidationService = new SelectionsStateValidationService();
		selectionsStateValidationService.validateSelectionsState(selectionsState);
		
		Connection connection = DatabaseConnectionService.getAppDatabaseConnection();
		
        PreparedStatement statementSelectionsState = connection.prepareStatement("UPDATE `app_database`.`selections_state` SET `is_selection_open_semester_4`= ?, `is_selection_open_semester_5`= ?, `is_selection_open_semester_6`= ?, `is_selection_open_semester_7`= ?, `is_selection_open_module`= ? WHERE `id`='1'");
        
        statementSelectionsState.setBoolean(1, selectionsState.isSelectionOpenForSemester4());
        statementSelectionsState.setBoolean(2, selectionsState.isSelectionOpenForSemester5());
        statementSelectionsState.setBoolean(3, selectionsState.isSelectionOpenForSemester6());
        statementSelectionsState.setBoolean(4, selectionsState.isSelectionOpenForSemester7());
        statementSelectionsState.setBoolean(5, selectionsState.isSelectionOpenModules());
        
        statementSelectionsState.executeUpdate();
	}
	
}
