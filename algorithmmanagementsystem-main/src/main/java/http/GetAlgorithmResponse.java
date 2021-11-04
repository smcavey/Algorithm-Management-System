package http;

public class GetAlgorithmResponse {
	public final String response;
	public final int httpCode;
	public String description;
	
	public GetAlgorithmResponse (String s, String description, int code) {
		this.response = s;
		this.description = description;
		this.httpCode = code;
	}
	
	// 200 means successful algorithm get
	public GetAlgorithmResponse (String s, String description) {
		this.response = s;
		this.description = description;
		this.httpCode = 200;
	}
	
	public GetAlgorithmResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}