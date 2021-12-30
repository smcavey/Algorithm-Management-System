package http;

public class GetUsersRequest {
	public String token;
	
	public String getToken( ) { return token; }
	public void setToken(String token) { this.token = token; }
	
	public GetUsersRequest(String token) {
		this.token = token;
	}
	
	public GetUsersRequest() {
	}

	public String toString() {
		return "GetUsers(" + token + ")";
	}
}