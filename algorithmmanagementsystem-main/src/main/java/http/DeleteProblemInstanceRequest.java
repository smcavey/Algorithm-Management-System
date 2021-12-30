package http;

public class DeleteProblemInstanceRequest {
	
	public String name;
	public String token;
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	public void setToken(String token) {this.token = token;}
	public String getToken() {return token;}
	
	public DeleteProblemInstanceRequest() {
	}
	
	public DeleteProblemInstanceRequest (String n, String token) {
		this.name = n;
		this.token = token;
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}

}