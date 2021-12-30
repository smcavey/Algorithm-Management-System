package http;

import java.util.ArrayList;
import java.util.List;

import model.ProblemInstance;

public class GetProblemInstancesResponse {
	public final List<ProblemInstance> list;
	public final int httpCode;
	public final String error;
	
	public GetProblemInstancesResponse (List<ProblemInstance> list, int code) {
		this.list = list;
		this.httpCode = code;
		this.error = "";
	}
	
	public GetProblemInstancesResponse (int code, String errorMessage) {
		this.list = new ArrayList<ProblemInstance>();
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyConstants"; }
		return "AllImplementations(" + list.size() + ")";
	}
}
