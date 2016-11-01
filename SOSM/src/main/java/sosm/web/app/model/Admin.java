package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class Admin extends User {
	
	private String nickname;
	private String firstName;
	private String lastName;
	private String email;
	private boolean isMasterAdmin;
	
	public Admin() {
		
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMasterAdmin() {
		return isMasterAdmin;
	}

	public void setMasterAdmin(boolean isMasterAdmin) {
		this.isMasterAdmin = isMasterAdmin;
	}

}
