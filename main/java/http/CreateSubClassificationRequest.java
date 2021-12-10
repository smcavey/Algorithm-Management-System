package http;

public class CreateSubClassificationRequest {
	
	public String name;
	public String description;
	public String classification;
	public String token;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getClassification() { return classification; }
	public void setClassification(String classification) { this.classification = classification; }
	
	public String getToken() { return token; }
	public void settoken(String token) { this.token = token; }
	
	public CreateSubClassificationRequest() {
	}
	
	public CreateSubClassificationRequest (String name, String description, String classification) {
		this.name = name;
		this.description = description;
		this.classification = classification;
	}
	
	public CreateSubClassificationRequest (String name, String description, String classification, String token) {
		this.name = name;
		this.description = description;
		this.classification = classification;
		this.token = token;
	}
	
	public String toString() {
		return "CreateSubClassification(" + name + ", " + description + ", " + classification + ", " + token + ")";
	}

}