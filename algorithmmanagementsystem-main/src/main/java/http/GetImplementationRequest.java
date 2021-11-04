package http;

public class GetImplementationRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetImplementationRequest() {
	}
	
	public GetImplementationRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Implementation(" + name + ")";
	}
}