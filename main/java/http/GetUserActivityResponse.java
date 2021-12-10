package http;

import java.util.ArrayList;
import java.util.List;

import model.UserActivity;

public class GetUserActivityResponse {
	public final String response;
	public final List<UserActivity> list;
	public final int httpCode;
	public final String error;
	
	public GetUserActivityResponse (String response, List<UserActivity> list, int code) {
		this.response = response;
		this.list = list;
		this.httpCode = code;
		this.error = "";
	}
	
	public GetUserActivityResponse (String response, int code, String errorMessage) {
		this.response = response;
		this.list = new ArrayList<UserActivity>();
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyUserActivity"; }
		return "AllUserActivity(" + list.size() + ")";
	}
}