package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import http.CreateClassificationRequest;
import http.CreateSubClassificationRequest;
import http.DeleteClassificationRequest;
import http.MergeClassificationRequest;
import model.Algorithm;
import model.Classification;
import model.User;
import db.AlgorithmsDAO;

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
            
            if(req.classification != null) {
                ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description,classification) values(?,?,?);");
                ps.setString(1, req.name);
                ps.setString(2, req.description);
                ps.setString(3, req.classification);
                ps.execute();
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description) values(?,?);");
            ps.setString(1, req.name);
            ps.setString(2, req.description);
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
                Classification c = generateClassification(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,description,classification) values(?,?,?);");
            ps.setString(1, req.name);
            ps.setString(2, req.description);
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
    	String classification = resultSet.getString("classification");
    	return new Classification(name, description, classification);
    }

    public List<Classification> getAllClassifications() throws Exception {
        
        List<Classification> allClassifications = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + ";");
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Classification c = generateClassification(resultSet);
                allClassifications.add(c);
            }
            resultSet.close();
            ps.close();
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
    
    public List<Classification> getAllSubClassifications(String name) throws Exception {
        
    	List<Classification> allSubClassifications = new ArrayList<Classification>();
        try {
            Classification subclassification = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE classification=?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                subclassification = generateClassification(resultSet);
                allSubClassifications.add(subclassification);
            }
            resultSet.close();
            ps.close();
            
            return allSubClassifications;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting subclassification: " + e.getMessage());
        }
    }
    
    public boolean mergeClassifications(MergeClassificationRequest req) throws Exception {
    	AlgorithmsDAO a = new AlgorithmsDAO();
		List<Classification> classificationOneSubClassifications = getAllSubClassifications(req.classificationOne);
		List<Classification> classificationTwoSubClassifications = getAllSubClassifications(req.classificationTwo);
		List<Algorithm> classificationOneAlgorithms = a.getAllAlgorithms(req.classificationOne);
		List<Algorithm> classificationTwoAlgorithms = a.getAllAlgorithms(req.classificationTwo);
		int numAffected = 0;
    	try {
    		for(Classification sc1 : classificationOneSubClassifications) { // point sub classifications to new classification
        		PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET classification = ? WHERE name = ?;");
        		ps.setString(1, req.classificationNew);
        		ps.setString(2, sc1.name);
        		ps.executeUpdate();
        		numAffected++;
        		UserActivityDAO ua = new UserActivityDAO();
        		ua.createUserActivity(req.token, ps.toString());
        		ps.close();
    		}
    		for(Classification sc2 : classificationTwoSubClassifications) { // point sub classifications to new classification
        		PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET classification = ? WHERE name = ?;");
        		ps.setString(1, req.classificationNew);
        		ps.setString(2, sc2.name);
        		ps.executeUpdate();
        		numAffected++;
        		UserActivityDAO ua = new UserActivityDAO();
        		ua.createUserActivity(req.token, ps.toString());
        		ps.close();
    		}
    		for(Algorithm a1 : classificationOneAlgorithms) {
        		PreparedStatement ps = conn.prepareStatement("UPDATE algorithms" + " SET classification = ? WHERE name = ?;");
        		ps.setString(1, req.classificationNew);
        		ps.setString(2, a1.name);
        		ps.executeUpdate();
        		numAffected++;
        		UserActivityDAO ua = new UserActivityDAO();
        		ua.createUserActivity(req.token, ps.toString());
        		ps.close();
    		}
    		for(Algorithm a2 : classificationTwoAlgorithms) {
        		PreparedStatement ps = conn.prepareStatement("UPDATE algorithms" + " SET classification = ? WHERE name = ?;");
        		ps.setString(1, req.classificationNew);
        		ps.setString(2, a2.name);
        		ps.executeUpdate();
        		numAffected++;
        		UserActivityDAO ua = new UserActivityDAO();
        		ua.createUserActivity(req.token, ps.toString());
        		ps.close();
    		}
    		PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (name) VALUES (?);");
    		ps.setString(1, req.classificationNew);
    		ps.execute();
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
    		ps.close();
    		
    		PreparedStatement ps2 = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name in (?,?);");
    		ps2.setString(1, req.classificationOne);
    		ps2.setString(2, req.classificationTwo);
    		ps2.execute();
    		ua.createUserActivity(req.token, ps2.toString());
    		ps2.close();
    		return (numAffected == classificationOneSubClassifications.size() + classificationTwoSubClassifications.size() + classificationOneAlgorithms.size() + classificationTwoAlgorithms.size());
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Failed in merging classifications: " + e.getMessage());
    	}
    }
 
}
