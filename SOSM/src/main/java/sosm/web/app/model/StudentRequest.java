package sosm.web.app.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class StudentRequest {
	private int id;
	private String facutyNumber;
	private int semester;
	private float score;
	private RequestSelectionType selectionType;
	private String firstChoice;
	private String secondChoice;
	private String thirdChoice;
	private String selected;
	private Date timestamp;
	private boolean isAccepted;
	
	public StudentRequest() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacutyNumber() {
		return facutyNumber;
	}

	public void setFacutyNumber(String facutyNumber) {
		this.facutyNumber = facutyNumber;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public RequestSelectionType getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(RequestSelectionType selectionType) {
		this.selectionType = selectionType;
	}

	public String getFirstChoice() {
		return firstChoice;
	}

	public void setFirstChoice(String firstChoice) {
		this.firstChoice = firstChoice;
	}

	public String getSecondChoice() {
		return secondChoice;
	}

	public void setSecondChoice(String secondChoice) {
		this.secondChoice = secondChoice;
	}

	public String getThirdChoice() {
		return thirdChoice;
	}

	public void setThirdChoice(String thirdChoice) {
		this.thirdChoice = thirdChoice;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

}
