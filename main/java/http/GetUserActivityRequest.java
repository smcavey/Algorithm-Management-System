package http;

public class GetUserActivityRequest {
	public String token;

	public String getToken( ) { return token; }
	public void setToken(String token) { this.token = token; }

	public GetUserActivityRequest() {
	}

	public String toString() {
		return "GetUserActivity(" + token + ")";
	}
}