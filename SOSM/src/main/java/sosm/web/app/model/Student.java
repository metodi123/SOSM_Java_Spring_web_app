package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class Student extends User {
	private String facultyNumber;
	private String firstName;
	private String lastName;
	private int currentSemester;
	private float gpa;
	private int group;
	private String module;
	private String email;

	public Student() {
		
	}
	
	@Override
	public String getUsername() {
		return super.getUsername();
	}
	
	@Override
	public void setUsername(String username) {
		super.setUsername(username);
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}
	
	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	public String getFacultyNumber() {
		return facultyNumber;
	}

	public void setFacultyNumber(String facultyNumber) {
		this.facultyNumber = facultyNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(int currentSemester) {
		this.currentSemester = currentSemester;
	}

	public float getGPA() {
		return gpa;
	}

	public void setGPA(float gpa) {
		this.gpa = gpa;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
