package model;

public class Classification {
	
	public int classificationID;
	public final String name;
	public final String description;
	public String token;
	// Don't think this actually gets used
//	public Classification(String name, String description, String token, int classificationID){
//		this.name = name;
//		this.description = description;
//		this.token = token;
//		this.classificationID = classificationID;
//	}
//	
//	public Classification(String name, String description, String token) {
//		this.name = name;
//		this.description = description;
//		this.token = token;
//	}
	
	public Classification(String name, String description) {
		this.name = name;
		this.description = description;
	}
	//Don't think this actually gets used
//	public boolean equals(Object o) {
//		if(o == null) {
//			return false;
//		}
//		
//		if(o instanceof Classification) {
//			Classification other = (Classification) o;
//			return classificationID == other.classificationID;
//		}
//		return false;
//	}
}