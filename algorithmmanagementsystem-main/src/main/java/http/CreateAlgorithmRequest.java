package http;

public class CreateAlgorithmRequest {
	
	public String name;
	public String description;
	public String classification;
	public boolean system;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getClassification() { return classification; }
	public void setClassification(String classification) { this.classification = classification; }
	
	public CreateAlgorithmRequest() {
	}
	
	public CreateAlgorithmRequest (String name, String description, String classification) {
		this.name = name;
		this.description = description;
		this.classification = classification;
	}
	
	public CreateAlgorithmRequest (String name, String description, String classification, boolean system) {
		this.name = name;
		this.description = description;
		this.classification = classification;
		this.system = system;
	}
	
	public String toString() {
		return "CreateAlgortihm(" + name + ", " + description + "," + classification + ")";
	}

}