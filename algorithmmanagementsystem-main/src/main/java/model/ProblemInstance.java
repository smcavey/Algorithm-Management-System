package model;

public class ProblemInstance {

	public String filename;
	public String algorithm;
	public String fileContent; // only used on creation
	public String filePath; // only used on return
	
	public ProblemInstance(String filename, String algorithm) {
		this.filename = filename;
		this.algorithm = algorithm;
	}
	
	public ProblemInstance(String filename) {
		this.filename = filename;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
}