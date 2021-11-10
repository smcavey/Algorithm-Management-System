package http;

import model.Implementation;

public class GetImplementationResponse {
	public final Implementation implementation;
	public final int httpCode;
	public final String error;
	
	public GetImplementationResponse (Implementation implementation, int code) {
		this.implementation = implementation;
		this.httpCode = code;
		this.error = "";
	}

	public GetImplementationResponse (int code, String errorMessage) {
		this.implementation = null; // doesn't matter since error
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		return "Response(" + implementation.fileName + ")";
	}
}