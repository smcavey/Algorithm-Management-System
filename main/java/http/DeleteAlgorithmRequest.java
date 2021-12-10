package http;

public class DeleteAlgorithmRequest {
	public String name;
	public String token;
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	public void setToken(String token) {this.token = token;}
	public String getToken() {return token;}
	
	public DeleteAlgorithmRequest (String n, String token) {
		this.name = n;
		this.token = token;
	}

	public DeleteAlgorithmRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}
}
