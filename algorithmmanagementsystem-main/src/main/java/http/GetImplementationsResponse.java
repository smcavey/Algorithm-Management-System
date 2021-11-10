package http;

import java.util.ArrayList;
import java.util.List;

import model.Implementation;

public class GetImplementationsResponse {
	public final List<Implementation> list;
	public final int statusCode;
	public final String error;
	
	public GetImplementationsResponse (List<Implementation> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public GetImplementationsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Implementation>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyConstants"; }
		return "AllConstants(" + list.size() + ")";
	}
}
