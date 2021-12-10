package http;

public class DeleteAlgorithmResponse {
	
	public String response;
	public int httpCode;
	public String errorMsg;
	public String name;
	
	public DeleteAlgorithmResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteAlgorithmResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public DeleteAlgorithmResponse(String name, int httpCode, String errorMsg) {
		this.name = name;
		this.httpCode = httpCode;
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
