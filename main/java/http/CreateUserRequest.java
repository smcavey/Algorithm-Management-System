package http;

public class CreateUserRequest {
	public String username;
	public String password;
	public boolean system;
	
	public String getUserame( ) { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public CreateUserRequest() {
	}
	
	public CreateUserRequest (String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public CreateUserRequest (String username, String password, boolean system) {
		this.username = username;
		this.password = password;
		this.system = system;
	}
	
	public String toString() {
		return "CreateUser(" + username + "," + password + ")";
	}
}
