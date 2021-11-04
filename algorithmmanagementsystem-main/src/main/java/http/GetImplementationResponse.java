package http;

public class GetImplementationResponse {
	public final String response;
	public final int httpCode;
	public String sourceCode;
	
	public GetImplementationResponse (String s, String sourceCode, int code) {
		this.response = s;
		this.sourceCode = sourceCode;
		this.httpCode = code;
	}
	
	// 200 means successful login
	public GetImplementationResponse (String s, String sourceCode) {
		this.response = s;
		this.sourceCode = s;
		this.httpCode = 200;
	}
	
	public GetImplementationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}