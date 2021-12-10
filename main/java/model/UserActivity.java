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
	
	public UserActivity(String username, Timestamp completedOn, String query) {
		this.username = username;
		this.completedOn = completedOn;
		this.query = query;
	}

	// anonymous user
	public UserActivity(Timestamp completedOn, String query) {
		this.completedOn = completedOn;
		this.query = query;
	}

	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}

		if(o instanceof UserActivity) {
			UserActivity other = (UserActivity) o;
			return userActivityId == other.userActivityId;
		}
		return false;
	}
}
