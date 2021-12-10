package http;

public class CreateBenchmarkResponse {
	
	public final String response;
	public final int httpCode;
	
	public CreateBenchmarkResponse(String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	public CreateBenchmarkResponse(String s) {
		this.response = s;
		this.httpCode = 201;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
}