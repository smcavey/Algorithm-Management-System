package http;

public class DeleteImplementationResponse {
	
	public String response;
	public int httpCode;
	public String errorMsg;
	public String name;
	
	public DeleteImplementationResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteImplementationResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public DeleteImplementationResponse(String name, int httpCode, String errorMsg) {
		this.name = name;
		this.httpCode = httpCode;
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
