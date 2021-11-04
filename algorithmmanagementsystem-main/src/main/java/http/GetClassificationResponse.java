package http;

public class GetClassificationResponse {
	public final String response;
	public final int httpCode;
	public String description;
	
	public GetClassificationResponse (String s, String description, int code) {
		this.response = s;
		this.description = description;
		this.httpCode = code;
	}
	
	// 200 means successful login
	public GetClassificationResponse (String s, String description) {
		this.response = s;
		this.description = description;
		this.httpCode = 200;
	}
	
	public GetClassificationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}