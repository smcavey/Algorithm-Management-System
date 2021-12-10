package http;

public class CreateImplementationRequest {
	
	public String fileContent;
	public String fileName;
	public String description;
	public String algorithm;
	public String token;
	
	public String getFileName( ) { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	
	public String getAlgorithm() { return algorithm; }
	public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getFileContent() { return fileContent; }
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	public CreateImplementationRequest() {
	}
	
	public CreateImplementationRequest (String fileContent, String fileName, String description, String algorithm, String token) {
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.description = description;
		this.algorithm = algorithm;
		this.token = token;
	}
	
	public String toString() {
		return "CreateImplementation(" + fileName + ", " + description + ", " + algorithm + ", " + token + ")";
	}

}