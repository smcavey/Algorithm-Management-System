package http;

public class ReclassifyAlgorithmResponse {
	
	public String response;
	public final int httpCode;
	public String error;
	public String algorithm;
	
	public ReclassifyAlgorithmResponse(String response, int httpCode, String error) {
		this.response = response;
		this.httpCode = httpCode;
		this.error = error;
	}
	
	public ReclassifyAlgorithmResponse(String algorithm, int httpCode) {
		this.algorithm = algorithm;
		this.httpCode = httpCode;
	}
	
	public String toString() {
		return "response: " + response;
	}

}
