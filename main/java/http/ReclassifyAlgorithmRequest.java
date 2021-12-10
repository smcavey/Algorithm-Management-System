package http;

public class ReclassifyAlgorithmRequest {
	public String token;
	public String algorithm;
	public String classification;
	
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	
	public String getAlgorithm() { return algorithm; }
	public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
	
	public String getClassification() { return classification; }
	public void setClassification(String classification) { this.classification = classification; }
	
	public ReclassifyAlgorithmRequest() {
		
	}
	
	public ReclassifyAlgorithmRequest(String token, String algorithm, String classification) {
		this.token = token;
		this.algorithm = algorithm;
		this.classification = classification;
	}
	
	public String toString() {
		return "ReclassifyAlgorithmRequest(" + token + ")";
	}

}
