package http;

public class DeleteImplementationRequest {
	public String filename;
	public String token;
	
	public void setName(String name) {this.filename = name; }
	public String getName() {return filename; }
	public void setToken(String token) {this.token = token;}
	public String getToken() {return token;}
	
	public DeleteImplementationRequest (String n, String token) {
		this.filename = n;
		this.token = token;
	}

	public DeleteImplementationRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + filename + ")";
	}
}
