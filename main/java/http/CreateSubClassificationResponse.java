package http;

public class CreateSubClassificationResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateSubClassificationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateSubClassificationResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}