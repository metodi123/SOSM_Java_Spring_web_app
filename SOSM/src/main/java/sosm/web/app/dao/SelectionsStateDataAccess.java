package sosm.web.app.dao;

import sosm.web.app.model.SelectionsState;

public interface SelectionsStateDataAccess {
	public SelectionsState getSelectionsState() throws Exception;
	
	public void setSelectionsState(SelectionsState selectionsState) throws Exception;
}
