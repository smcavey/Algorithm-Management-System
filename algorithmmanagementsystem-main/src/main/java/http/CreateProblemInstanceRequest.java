package http;

public class CreateProblemInstanceRequest {
	
	public String fileContent;
	public String filename;
	public String algorithm;
	public String token;
	
	public String getFileName( ) { return filename; }
	public void setFileName(String filename) { this.filename = filename; }
	
	public String getAlgorithm() { return algorithm; }
	public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
	
	public String getFileContent() { return fileContent; }
	public void setFileContent(String fileContent) { this.fileContent = fileContent; }
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	public CreateProblemInstanceRequest() {
	}
	
	public CreateProblemInstanceRequest (String fileContent, String filename, String algorithm, String token) {
		this.fileContent = fileContent;
		this.filename = filename;
		this.algorithm = algorithm;
		this.token = token;
	}
	
	public String toString() {
		return "CreateProblemInstanceRequest(" + filename + ", " + algorithm + ", " + token + ")";
	}

}