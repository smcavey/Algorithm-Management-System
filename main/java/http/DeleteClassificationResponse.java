package http;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class DeleteClassificationResponse {
	public final String name;
	public final int httpCode;
	public final String error;
	
	public DeleteClassificationResponse (String name, int statusCode) {
		this.name = name;
		this.httpCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeleteClassificationResponse (String name, int statusCode, String errorMessage) {
		this.httpCode = statusCode;
		this.error = errorMessage;
		this.name = name;
	}
	
	public String toString() {
		if (httpCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + name + ")";
		} else {
			return "ErrorResult(" + name + ", httpCode=" + httpCode + ", err=" + error + ")";
		}
	}
}
