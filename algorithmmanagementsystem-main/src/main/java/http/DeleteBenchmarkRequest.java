package http;

public class DeleteBenchmarkRequest {
	public String name;
	public String token;
	
	public void setName(String name) {this.name = name; }
	public String getName() {return name; }
	public void setToken(String token) {this.token = token;}
	public String getTokenf() {return token;}
	
	public DeleteBenchmarkRequest (String n, String token) {
		this.name = n;
		this.token = token;
	}

	public DeleteBenchmarkRequest() {
		
	}
	
	public String toString() {
		return "Delete(" + name + ")";
	}
}
