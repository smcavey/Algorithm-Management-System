package http;

public class DeleteUserRequest {
	public String username;
	
	public void setName(String username) {this.username = username; }
	public String getUserName() {return username; }
	
	public DeleteUserRequest (String n) {
		this.username = n;
	}

	public DeleteUserRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + username + ")";
	}
}