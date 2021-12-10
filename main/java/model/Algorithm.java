package model;

public class Algorithm {
	
	public String name;
	public String description;
	public String classification;
	
	public Algorithm(String name, String description, String classification) {
		this.name = name;
		this.description = description;
		this.classification = classification;
	}
	
	public Algorithm(String name) {
		this.name = name;
	}
	
	public String toString() {
		return ("Name: " + name + " Description: " + description + "Classification: " + classification);
	}

}
