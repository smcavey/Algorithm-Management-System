package http;

public class CreateClassificationResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateClassificationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateClassificationResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
