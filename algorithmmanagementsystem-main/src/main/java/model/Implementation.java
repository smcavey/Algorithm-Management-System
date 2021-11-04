package model;

public class Implementation {
	
	public String fileContent;
	public final String fileName;
	public final String description;
	
	public Implementation(String fileContent, String fileName, String description) {
		this.fileContent = fileContent;
		this.fileName = fileName;
		this.description = description;
	}
	
	public Implementation(String fileName, String description) {
		this.fileName = fileName;
		this.description = description;
	}

}
