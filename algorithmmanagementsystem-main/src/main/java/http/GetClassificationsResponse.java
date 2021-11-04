package http;

import java.util.ArrayList;
import java.util.List;

import model.Classification;

public class GetClassificationsResponse {
	public final List<Classification> list;
	public final int statusCode;
	public final String error;
	
	public GetClassificationsResponse (List<Classification> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public GetClassificationsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Classification>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyConstants"; }
		return "AllConstants(" + list.size() + ")";
	}
}
