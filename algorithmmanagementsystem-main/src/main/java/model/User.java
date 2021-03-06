package model;

public class User {
	
	public int userID;
	public final String username;
	public final byte[] salt;
	public final String hash;
	public String token;
	
	public User(String username, byte[] salt, String hash, String token, int userID) {
		this.username = username;
		this.salt = salt;
		this.hash = hash;
		this.token = token;
		this.userID = userID;
	}
	
	public User(String username, byte[] salt, String hash) {
		this.username = username;
		this.salt = salt;
		this.hash = hash;
	}
}
