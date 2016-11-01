package sosm.web.app.model;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {

	private String url;
	private String username;
	private String password;
	
	public DatabaseConnection() {

	}
	
	public DatabaseConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
}
