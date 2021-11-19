package http;

public class GetBenchmarksRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetBenchmarksRequest() {
	}
	
	public GetBenchmarksRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Benchmarks(" + name + ")";
	}
}