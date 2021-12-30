package http;

public class GetSubClassificationsRequest {
	public String name;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public GetSubClassificationsRequest() {
		
	}
	
	public GetSubClassificationsRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Subclassification(" + name + ")";
	}

}
