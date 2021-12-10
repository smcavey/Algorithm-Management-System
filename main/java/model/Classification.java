package model;

public class Classification {
	
	public int classificationID;
	public final String name;
	public final String description;
	public String token;
	public String parent;
	
	public Classification(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Classification(String name, String description, String parent) { // sub classification...provide it's parent's name
		this.name = name;
		this.description = description;
		this.parent = parent;
	}

}