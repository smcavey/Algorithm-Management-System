package http;

public class GetAlgorithmsRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetAlgorithmsRequest() {
	}
	
	public GetAlgorithmsRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Algorithms(" + name + ")";
	}
}