package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import http.CreateProblemInstanceRequest;
import http.DeleteProblemInstanceRequest;
import model.ProblemInstance;

public class ProblemInstancesDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "probleminstances";

	public ProblemInstancesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createProblemInstance(CreateProblemInstanceRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, req.filename);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                ProblemInstance i = generateProblemInstance(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (filename,algorithm) values(?,?);");
            ps.setString(1, req.filename);
            ps.setString(2, req.algorithm);
            ps.execute();
            
            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert problem instance: " + e.getMessage());
        }
    }
	
	public boolean searchForProblemInstance(String filename) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, filename);
            ResultSet resultSet = ps.executeQuery();
            
            ProblemInstance pi = null;
            while (resultSet.next()) {
                pi = generateProblemInstance(resultSet);
            }
            resultSet.close();
            ps.close();

            if (filename.equals(pi.filename)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to get filename: " + e.getMessage());
        }
    }

    public ProblemInstance getProblemInstance(String filename) throws Exception {
        
        try {
            ProblemInstance pi = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename=?;");
            ps.setString(1, filename);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                pi = generateProblemInstance(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return pi;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting problem instance: " + e.getMessage());
        }
    }
    
    public List<ProblemInstance> getAllProblemInstances(String algorithm) throws Exception {
        
    	List<ProblemInstance> allProblemInstances = new ArrayList<>();
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=?;");
            ps.setString(1, algorithm);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                ProblemInstance pi = generateProblemInstance(resultSet);
                allProblemInstances.add(pi);
            }
            resultSet.close();
            ps.close();
            
            return allProblemInstances;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting problem instance: " + e.getMessage());
        }
    }


    private ProblemInstance generateProblemInstance(ResultSet resultSet) throws Exception {
        String filename  = resultSet.getString("filename");
        String algorithm = resultSet.getString("algorithm");
        return new ProblemInstance (filename, algorithm);
    }
    
    public boolean deleteProblemInstance(DeleteProblemInstanceRequest req) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, req.name);
            int numAffected = ps.executeUpdate();
            
            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity(req.token, ps.toString());
            
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete problem instance: " + e.getMessage());
        }
    }
    
    public boolean deleteProblemInstanceGivenAlgorithm(ProblemInstance pi) throws Exception{
    	try {
    		PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, pi.filename);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return(numAffected == 1);
    	} catch (Exception e) {
    		throw new Exception("Failed to delete problem instance: " + e.getMessage());
    	}
    }

}