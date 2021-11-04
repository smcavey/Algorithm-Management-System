package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UsersDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "users";

	public UsersDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createUser(User user) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE username = ?;");
            ps.setString(1, user.username);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                User u = generateUser(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (username,salt,hash) values(?,?,?);");
            ps.setString(1, user.username);
            ps.setBytes( 2, user.salt);
            ps.setString(3, user.hash);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert user: " + e.getMessage());
        }
    }
	
	public boolean loginUser(String username, String password) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE username = ?;");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            
            User user = null;
            while (resultSet.next()) {
                user = generateUser(resultSet);
            }
            resultSet.close();
            ps.close();

            // verify password matches salt/hash in database
            String newHash = DatabaseUtil.getSecurePassword(password, user.salt);
            if (newHash.equals(user.hash)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to login user: " + e.getMessage());
        }
    }


    public User getUser(String username) throws Exception {
        
        try {
            User user = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE username=?;");
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                user = generateUser(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return user;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting user: " + e.getMessage());
        }
    }

    private User generateUser(ResultSet resultSet) throws Exception {
        String username  = resultSet.getString("username");
        byte[] salt = resultSet.getBytes("salt");
        String hash = resultSet.getString("hash");
        return new User (username, salt, hash);
    }

}
