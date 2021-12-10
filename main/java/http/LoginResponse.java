package http;

public class LoginResponse {
	public final String response;
	public final String token;
	public final int httpCode;
	
	public LoginResponse (String s, String token, int code) {
		this.response = s;
		this.token = token;
		this.httpCode = code;
	}
	
	// 200 means successful login
	public LoginResponse (String s, String token) {
		this.response = s;
		this.token = token;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
