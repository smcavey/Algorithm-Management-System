package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import http.CreateClassificationRequest;
import http.CreateSubClassificationRequest;
import http.DeleteClassificationRequest;
import model.Classification;
import model.User;

public class ClassificationsDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "classifications";

	public ClassificationsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createClassification(CreateClassificationRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, req.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Classification c = generateClassification(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description) values(?,?);");
            ps.setString(1, req.name);
            ps.setString( 2, req.description);
            ps.execute();
            
            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert classification: " + e.getMessage());
        }
    }
	
	public boolean createSubClassification(CreateSubClassificationRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, req.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Classification c = generateSubClassification(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description,classification) values(?,?,?);");
            ps.setString(1, req.name);
            ps.setString( 2, req.description);
            ps.setString(3, req.classification);
            ps.execute();
            
            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert classification: " + e.getMessage());
        }
    }
	
	public boolean searchForClassification(String name) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            Classification classification = null;
            while (resultSet.next()) {
                classification = generateClassification(resultSet);
            }
            resultSet.close();
            ps.close();

            if (name.equals(classification.name)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to get classification: " + e.getMessage());
        }
    }

    public Classification getClassification(String name) throws Exception {
        
        try {
            Classification classification = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                classification = generateClassification(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return classification;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

    private Classification generateClassification(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("name");
        String description = resultSet.getString("description");
        return new Classification (name, description);
    }
    
    private Classification generateSubClassification(ResultSet resultSet) throws Exception{
    	String name = resultSet.getString("name");
    	String description = resultSet.getString("description");
    	String classification = resultSet.getString("classification");
    	return new Classification(name, description, classification);
    }

    public List<Classification> getAllClassifications() throws Exception {
        
        List<Classification> allClassifications = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Classification c = generateClassification(resultSet);
                allClassifications.add(c);
            }
            resultSet.close();
            statement.close();
            return allClassifications;

        } catch (Exception e) {
            throw new Exception("Failed in getting classifications: " + e.getMessage());
        }
    }

    public boolean deleteClassification(DeleteClassificationRequest req) throws Exception {
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
            throw new Exception("Failed to delete classification: " + e.getMessage());
        }
    }
 
}
