package http;

public class GetClassificationRequest {
	public String name;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public GetClassificationRequest() {
	}
	
	public GetClassificationRequest (String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Classification(" + name + ")";
	}
}