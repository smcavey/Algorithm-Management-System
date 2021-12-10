package http;

import java.util.ArrayList;
import java.util.List;

import model.Benchmark;

public class GetBenchmarksResponse {
	public final String response;
	public final List<Benchmark> list;
	public final int httpCode;
	public final String error;
	
	public GetBenchmarksResponse (String response, List<Benchmark> list, int code) {
		this.response = response;
		this.list = list;
		this.httpCode = code;
		this.error = "";
	}
	
	public GetBenchmarksResponse (String response, int code, String errorMessage) {
		this.response = response;
		this.list = new ArrayList<Benchmark>();
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyBenchmarks"; }
		return "AllBenchmarks(" + list.size() + ")";
	}
}