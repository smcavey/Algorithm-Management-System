package http;

public class CreateImplementationResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateImplementationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateImplementationResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
