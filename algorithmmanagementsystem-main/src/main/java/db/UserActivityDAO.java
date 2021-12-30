package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.UserActivity;

public class UserActivityDAO {
	
	java.sql.Connection conn;
	
	final String tblUserActivity = "user_activity";
	final String tblUsers = "users";

	public UserActivityDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}

    public List<UserActivity> getUserActivity(String username) throws Exception {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT iduser_activity, username, completed_on, query FROM " + tblUserActivity
            		+ " LEFT JOIN users on " + tblUsers + ".idusers = " + tblUserActivity + ".iduser " 
            		+ " WHERE username=?;");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            List<UserActivity> userActivity = new ArrayList<UserActivity>();
            while (resultSet.next()) {
            	userActivity.add(generateUserActivity(resultSet));
            }
            resultSet.close();
            ps.close();

            return userActivity;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting user activity: " + e.getMessage());
        }
    }

	public boolean createUserActivity(String token, String query) throws Exception {
		
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblUsers + " WHERE token = ?;");
            ps.setString(1, token);
            ResultSet resultSet = ps.executeQuery();

            // user present?
            int userId = 0;
            while (resultSet.next()) {
            	userId = resultSet.getInt("idusers");
            }

        	ps = conn.prepareStatement("INSERT INTO " + tblUserActivity + " (iduser,completed_on,query) values(?,?,?);");
            ps.setInt(1, userId);
            ps.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            ps.setString(3, query);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert user activity: " + e.getMessage());
        }
    }

    private UserActivity generateUserActivity(ResultSet resultSet) throws Exception {
    	int userActivityId = resultSet.getInt("iduser_activity");
        String username  = resultSet.getString("username");
        Timestamp completedOn = resultSet.getTimestamp("completed_on");
        String query = resultSet.getString("query");
        return new UserActivity (userActivityId, username, completedOn, query);
    }

}
