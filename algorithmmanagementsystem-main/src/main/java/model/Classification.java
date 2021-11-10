package model;

public class Classification {
	
	public int classificationID;
	public final String name;
	public final String description;
	public String token;
	
	public Classification(String name, String description) {
		this.name = name;
		this.description = description;
	}

}