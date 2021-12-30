package http;

public class MergeClassificationRequest {
	public String token;
	public String classificationOne;
	public String classificationTwo;
	public String classificationNew;
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	public String getClassificationOne() { return classificationOne; }
	public void setClassificationOne(String classificationOne) { this.classificationOne = classificationOne; }
	
	public String getClassificationTwo() { return classificationTwo; }
	public void setClassificationTwo(String classificationTwo) { this.classificationTwo = classificationTwo; }
	
	public String getClassificationNew() { return classificationNew; }
	public void setClassificationNew(String classificationNew) { this.classificationNew = classificationNew; }
	
	public MergeClassificationRequest() {
		
	}
	
	public MergeClassificationRequest(String token, String classificationOne, String classificationTwo, String classificationNew) {
		this.token = token;
		this.classificationOne = classificationOne;
		this.classificationTwo = classificationTwo;
		this.classificationNew = classificationNew;
	}

}
