package http;

public class MergeClassificationResponse {
	public final String response;
	public final int httpCode;
	public String error;
	
	public MergeClassificationResponse(String response, int httpCode, String error) {
		this.response = response;
		this.httpCode = httpCode;
		this.error = error;
	}
	
	public MergeClassificationResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "response: " + response;
	}

}
