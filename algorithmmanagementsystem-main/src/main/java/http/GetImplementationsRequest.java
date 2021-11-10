package http;

public class GetImplementationsRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetImplementationsRequest() {
	}
	
	public GetImplementationsRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Implementations(" + name + ")";
	}
}