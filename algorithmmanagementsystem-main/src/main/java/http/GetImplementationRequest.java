package http;

public class GetImplementationRequest {
	public String fileName;
	
	public String getFileName( ) { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }

	public GetImplementationRequest() {
	}
	
	public GetImplementationRequest (String fileName) {
		this.fileName = fileName;
	}
	
	public String toString() {
		return "Implementation(" + fileName + ")";
	}
}