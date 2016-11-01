package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class Course {
	private int id;
	private String name;
	private String fullName;
	private int semester;
	private String moduleName;
	private boolean isElective;
	private boolean isCourseWork;
	private boolean isCourseProject;
	
	public Course() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public boolean isElective() {
		return isElective;
	}

	public void setElective(boolean isElective) {
		this.isElective = isElective;
	}

	public boolean isCourseWork() {
		return isCourseWork;
	}

	public void setCourseWork(boolean isCourseWork) {
		this.isCourseWork = isCourseWork;
	}

	public boolean isCourseProject() {
		return isCourseProject;
	}

	public void setCourseProject(boolean isCourseProject) {
		this.isCourseProject = isCourseProject;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
