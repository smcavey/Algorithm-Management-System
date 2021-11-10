package http;

public class CreateImplementationRequest {
	
	public String fileContent;
	public String fileName;
	public String description;
	public String algorithm;
	
	public String getFileName( ) { return fileName; }
	public void setName(String fileName) { this.fileName = fileName; }
	
	public String getAlgorithm() { return algorithm; }
	public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getFile() { return fileContent; }
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	
	public CreateImplementationRequest() {
	}
	
	public CreateImplementationRequest (String fileContent, String fileName, String description, String algorithm) {
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.description = description;
		this.algorithm = algorithm;
	}
	
	public String toString() {
		return "CreateImplementation(" + fileName + ", " + description + ", " + algorithm + ")";
	}

}