package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class SelectionsState {
	
	private boolean isSelectionOpenForSemester4;
	private boolean isSelectionOpenForSemester5;
	private boolean isSelectionOpenForSemester6;
	private boolean isSelectionOpenForSemester7;
	private boolean isSelectionOpenModules;
	
	public SelectionsState() {
			
	}
	
	public boolean isSelectionOpenForSemester4() {
		return isSelectionOpenForSemester4;
	}

	public void setSelectionOpenForSemester4(boolean isSelectionOpenForSemester4) {
		this.isSelectionOpenForSemester4 = isSelectionOpenForSemester4;
	}

	public boolean isSelectionOpenForSemester5() {
		return isSelectionOpenForSemester5;
	}

	public void setSelectionOpenForSemester5(boolean isSelectionOpenForSemester5) {
		this.isSelectionOpenForSemester5 = isSelectionOpenForSemester5;
	}

	public boolean isSelectionOpenForSemester6() {
		return isSelectionOpenForSemester6;
	}

	public void setSelectionOpenForSemester6(boolean isSelectionOpenForSemester6) {
		this.isSelectionOpenForSemester6 = isSelectionOpenForSemester6;
	}

	public boolean isSelectionOpenForSemester7() {
		return isSelectionOpenForSemester7;
	}

	public void setSelectionOpenForSemester7(boolean isSelectionOpenForSemester7) {
		this.isSelectionOpenForSemester7 = isSelectionOpenForSemester7;
	}

	public boolean isSelectionOpenModules() {
		return isSelectionOpenModules;
	}

	public void setSelectionOpenModules(boolean isSelectionOpenModules) {
		this.isSelectionOpenModules = isSelectionOpenModules;
	}
	
}
