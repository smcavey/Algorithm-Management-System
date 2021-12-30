package http;

public class GetUserActivityRequest {
	public String name;
	public String token;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getToken( ) { return token; }
	public void setToken(String token) { this.token = token; }

	public GetUserActivityRequest(String name, String token) {
		this.name = name;
		this.token = token;
	}
	
	public GetUserActivityRequest(String name) {
		this.name = name;
	}
	
	public GetUserActivityRequest() {
	}

	public String toString() {
		return "GetUserActivity(" + name + ", " + token + ")";
	}
}