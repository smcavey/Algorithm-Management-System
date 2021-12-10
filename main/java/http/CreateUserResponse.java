package http;

public class CreateUserResponse {
	public final String response;
	public final int httpCode;
	
	public CreateUserResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 201 means successful creation
	public CreateUserResponse (String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
