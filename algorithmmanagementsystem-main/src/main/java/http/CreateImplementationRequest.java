package http;

public class CreateImplementationRequest {
	
	public String fileContent;
	public String fileName;
	public String description;
	public boolean system;
	
	public String getFileName( ) { return fileName; }
	public void setName(String fileName) { this.fileName = fileName; }
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getFile() { return fileContent; }
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	
	public CreateImplementationRequest() {
	}
	
	public CreateImplementationRequest (String fileContent, String fileName, String description) {
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.description = description;
	}
	
	public CreateImplementationRequest (String fileContent, String fileName, String description, boolean system) {
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.description = description;
		this.system = system;
	}
	
	public String toString() {
		return "CreateImplementation(" + fileName + ", " + description + ")";
	}

}