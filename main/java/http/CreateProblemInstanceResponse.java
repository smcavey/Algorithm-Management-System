package http;

public class CreateProblemInstanceResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateProblemInstanceResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateProblemInstanceResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}