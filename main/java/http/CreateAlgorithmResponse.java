package http;

public class CreateAlgorithmResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateAlgorithmResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateAlgorithmResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}