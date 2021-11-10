package http;

public class DeleteClassificationRequest {
	public String name;
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	
	public DeleteClassificationRequest (String n) {
		this.name = n;
	}

	public DeleteClassificationRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}
}
