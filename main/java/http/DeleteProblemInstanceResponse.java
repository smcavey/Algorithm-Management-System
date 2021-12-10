package http;

public class DeleteProblemInstanceResponse {
	
	public final String response;
	public final int httpCode;
	
	public DeleteProblemInstanceResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteProblemInstanceResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}