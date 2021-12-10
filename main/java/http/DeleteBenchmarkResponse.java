package http;

public class DeleteBenchmarkResponse {
	
	public String response;
	public int httpCode;
	public String errorMsg;
	public String name;
	
	public DeleteBenchmarkResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public DeleteBenchmarkResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public DeleteBenchmarkResponse(String name, int httpCode, String errorMsg) {
		this.name = name;
		this.httpCode = httpCode;
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}
