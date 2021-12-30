package http;

public class DeleteProblemInstanceResponse {
	
	public final String response;
	public final int httpCode;
	public String error;
	
	public DeleteProblemInstanceResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteProblemInstanceResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public DeleteProblemInstanceResponse(String s, int code, String error) {
		this.response = s;
		this.httpCode = code;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}