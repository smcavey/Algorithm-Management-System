package http;

public class CreateClassificationRequest {
	
	public String name;
	public String description;
	public boolean system;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public CreateClassificationRequest() {
	}
	
	public CreateClassificationRequest (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public CreateClassificationRequest (String name, String description, boolean system) {
		this.name = name;
		this.description = description;
		this.system = system;
	}
	
	public String toString() {
		return "CreateClassification(" + name + ", " + description + ")";
	}

}
