package http;

public class CreateClassificationRequest {
	
	public String name;
	public String description;
	public String token;
	public String classification;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	public CreateClassificationRequest() {
	}
	
	public CreateClassificationRequest(String name) {
		this.name = name;
	}
	
	public CreateClassificationRequest (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public CreateClassificationRequest (String name, String description, String token) {
		this.name = name;
		this.description = description;
		this.token = token;
	}
	
	public CreateClassificationRequest (String name, String description, String classification, String token) {
		this.name = name;
		this.description = description;
		this.classification = classification;
		this.token = token;
	}
	
	public String toString() {
		return "CreateClassification(" + name + ", " + description + ", " + token + ")";
	}

}
