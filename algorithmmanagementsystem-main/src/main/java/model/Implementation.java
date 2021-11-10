package model;

public class Implementation {

	public final String fileName;
	public final String description;
	public final String algorithm;
	public String fileContent; // only used on creation
	public String filePath; // only used on return
	
	public Implementation(String fileName, String description, String algorithm) {
		this.fileName = fileName;
		this.description = description;
		this.algorithm = algorithm;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
