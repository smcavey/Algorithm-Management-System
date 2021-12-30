package http;

import java.util.ArrayList;
import java.util.List;

import model.User;

public class GetUsersResponse {
	public final String response;
	public final List<User> list;
	public final int httpCode;
	public final String error;
	
	public GetUsersResponse (String response, List<User> list, int code) {
		this.response = response;
		this.list = list;
		this.httpCode = code;
		this.error = "";
	}
	
	public GetUsersResponse (String response, int code, String errorMessage) {
		this.response = response;
		this.list = new ArrayList<User>();
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyUsers"; }
		return "AllUsers(" + list.size() + ")";
	}
}