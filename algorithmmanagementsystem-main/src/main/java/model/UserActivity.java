package model;

import java.sql.Timestamp;

public class UserActivity {

	public int userActivityId;
	public String username;
	public final Timestamp completedOn;
	public final String query;

	public UserActivity(int userActivityId, String username, Timestamp completedOn, String query) {
		this.userActivityId = userActivityId;
		this.username = username;
		this.completedOn = completedOn;
		this.query = query;
	}
}
