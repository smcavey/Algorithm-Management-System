package http;

public class DeleteUserResponse {
	
	public String response;
	public int httpCode;
	public String errorMsg;
	public String username;
	
	public DeleteUserResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteUserResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public DeleteUserResponse(String username, int httpCode, String errorMsg) {
		this.username = username;
		this.httpCode = httpCode;
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}