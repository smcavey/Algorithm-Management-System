package http;

public class GetProblemInstancesRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetProblemInstancesRequest() {
	}
	
	public GetProblemInstancesRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "GetImplementations(" + name + ")";
	}
}