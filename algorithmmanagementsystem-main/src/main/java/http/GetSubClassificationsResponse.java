package http;

import java.util.List;

import model.Classification;

public class GetSubClassificationsResponse {
	public final String response;
	public final int httpCode;
	public List<Classification> list;
	public String error;
	
	public GetSubClassificationsResponse(String response, int httpCode, List<Classification> list) {
		this.response = response;
		this.httpCode = httpCode;
		this.list = list;
	}
	
	public GetSubClassificationsResponse(String response, int httpCode, String error) {
		this.response = response;
		this.httpCode = httpCode;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
