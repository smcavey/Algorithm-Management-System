package http;

import java.util.ArrayList;
import java.util.List;

import model.Algorithm;

public class GetAlgorithmsResponse {
	public final String response;
	public final List<Algorithm> list;
	public final int httpCode;
	public final String error;
	
	public GetAlgorithmsResponse (String response, List<Algorithm> list, int code) {
		this.response = response;
		this.list = list;
		this.httpCode = code;
		this.error = "";
	}
	
	public GetAlgorithmsResponse (String response, int code, String errorMessage) {
		this.response = response;
		this.list = new ArrayList<Algorithm>();
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyAlgorithms"; }
		return "AllAlgorithms(" + list.size() + ")";
	}
}