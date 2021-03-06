package db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import http.DeleteAlgorithmRequest;
import http.DeleteUserRequest;
import model.Classification;
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

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (username,salt,hash,token) values(?,?,?,?);");
            ps.setString(1, user.username);
            ps.setBytes( 2, user.salt);
            ps.setString(3, user.hash);
            ps.setNString(4, user.token);
            ps.execute();

            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity("", ps.toString()); // set token to empty, anonymous user

            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert user: " + e.getMessage());
        }
    }
	
	public String loginUser(String username, String password) throws Exception {
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
            String newHash = getSecurePassword(password, user.salt);
            if (newHash.equals(user.hash)) {
            	return user.token;
            } else {
            	throw new Exception("Failed to login user: incorrect password");
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

    public List<User> getUsers() throws Exception {

    	List<User> allUsers = new ArrayList<>();
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + ";");
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                User u = generateUser(resultSet);
                allUsers.add(u);
            }
            
            resultSet.close();
            ps.close();
            
            return allUsers;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting user: " + e.getMessage());
        }
    }
    
    public String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            // FIXME: should probably use SHA-512
        	MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    public boolean validToken(String token) throws Exception {
    	boolean validToken = false;
    	
        try {
            User user = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE token=?;");
            ps.setString(1, token);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                user = generateUser(resultSet);
                validToken = true;
            }
            resultSet.close();
            ps.close();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in validating token: " + e.getMessage());
        }

    	return validToken;
    }

    public String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
		Random random = new SecureRandom();
		String token = new BigInteger(130, random).toString(32);
		return token;
    }
    
    private User generateUser(ResultSet resultSet) throws Exception {
    	int idusers = resultSet.getInt("idusers");
        String username  = resultSet.getString("username");
        byte[] salt = resultSet.getBytes("salt");
        String hash = resultSet.getString("hash");
        String token = resultSet.getString("token");
        return new User (username, salt, hash, token, idusers);
    }
    
    public boolean deleteUser(DeleteUserRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE username = ?;");
            ps.setString(1, req.username);
            int numAffected = ps.executeUpdate();
            
            // data modified, log query
            
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete user: " + e.getMessage());
        }
    }

}
