package http;

public class DeleteClassificationRequest {
	public String name;
	public String token;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	
	public DeleteClassificationRequest (String n, String token) {
		this.name = n;
		this.token = token;
	}

	public DeleteClassificationRequest (String n) {
		this.name = n;
	}

	public DeleteClassificationRequest() {
		
	}
	
	public String toString() {
		return "DeleteClassification(" + name +", " + token + ")";
	}
}
