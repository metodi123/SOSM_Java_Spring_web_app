package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class Module {
	private int id;
	private String name;
	private String fullName;
	
	public Module() {

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
	
	@Override
	public String toString() {
		return name;
	}

}
