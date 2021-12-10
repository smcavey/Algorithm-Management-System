package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import http.CreateAlgorithmRequest;
import http.DeleteAlgorithmRequest;
import http.ReclassifyAlgorithmRequest;
import model.Algorithm;

public class AlgorithmsDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "algorithms";

	public AlgorithmsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createAlgorithm(CreateAlgorithmRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, req.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Algorithm a = generateAlgorithm(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description,classification) values(?,?,?);");
            ps.setString(1, req.name);
            ps.setString(2, req.description);
            ps.setNString(3, req.classification);
            ps.execute();

            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());

            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert algorithm: " + e.getMessage());
        }
    }

    public Algorithm getAlgorithm(String name) throws Exception {
        
        try {
            Algorithm algorithm = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                algorithm = generateAlgorithm(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return algorithm;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

    private Algorithm generateAlgorithm(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("name");
        String description = resultSet.getString("description");
        String classification = resultSet.getString("classification");
        return new Algorithm (name, description, classification);
    }
    
	public boolean searchForAlgorithm(String name) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            Algorithm algorithm = null;
            while (resultSet.next()) {
                algorithm = generateAlgorithm(resultSet);
            }
            resultSet.close();
            ps.close();

            if (name.equals(algorithm.name)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to get algorithm: " + e.getMessage());
        }
    }
	
    public List<Algorithm> getAllAlgorithms(String name) throws Exception {
        
        List<Algorithm> allAlgorithms = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE classification = ?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Algorithm a = generateAlgorithm(resultSet);
                allAlgorithms.add(a);
            }
            resultSet.close();
            statement.close();
            return allAlgorithms;

        } catch (Exception e) {
            throw new Exception("Failed in getting algorithms: " + e.getMessage());
        }
    }
    
    public boolean deleteAlgorithm(DeleteAlgorithmRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, req.name);
            int numAffected = ps.executeUpdate();
            
            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
            
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete algorithm: " + e.getMessage());
        }
    }
    
    public boolean reclassifyAlgorithm(ReclassifyAlgorithmRequest req) throws Exception {
    	try {
    		PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET classification = ? WHERE name = ?;");
    		ps.setString(1, req.classification);
    		ps.setString(2, req.algorithm);
    		
    		int numAffected = ps.executeUpdate();
    		
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
    		
    		ps.close();
    		
    		return (numAffected == 1);
    	} catch (Exception e) {
    		throw new Exception("Failed to reclassify algorithm: " + e.getMessage());
    	}
    }

}