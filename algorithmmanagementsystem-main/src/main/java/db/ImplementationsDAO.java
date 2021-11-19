package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Algorithm;
import model.Benchmark;
import model.Implementation;

public class ImplementationsDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "implementations";

	public ImplementationsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createImplementation(Implementation implementation) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, implementation.fileName);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Implementation i = generateImplementation(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (filename,description,algorithm) values(?,?,?);");
            ps.setString(1, implementation.fileName);
            ps.setString(2, implementation.description);
            ps.setString(3, implementation.algorithm);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert implementation: " + e.getMessage());
        }
    }
	
	public boolean searchForImplementation(String fileName) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, fileName);
            ResultSet resultSet = ps.executeQuery();
            
            Implementation implementation = null;
            while (resultSet.next()) {
                implementation = generateImplementation(resultSet);
            }
            resultSet.close();
            ps.close();

            if (fileName.equals(implementation.fileName)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to get fileName: " + e.getMessage());
        }
    }

    public Implementation getImplementation(String fileName) throws Exception {
        
        try {
            Implementation implementation = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename=?;");
            ps.setString(1, fileName);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                implementation = generateImplementation(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return implementation;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting implementation: " + e.getMessage());
        }
    }
    
    public List<Implementation> getAllImplementations(String algorithmName) throws Exception {
        
    	List<Implementation> allImplementations = new ArrayList<>();
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=?;");
            ps.setString(1, algorithmName);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                Implementation i = generateImplementation(resultSet);
                allImplementations.add(i);
            }
            resultSet.close();
            ps.close();
            
            return allImplementations;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting implementation: " + e.getMessage());
        }
    }


    private Implementation generateImplementation(ResultSet resultSet) throws Exception {
        String fileName  = resultSet.getString("fileName");
        String description = resultSet.getString("description");
        String algorithm = resultSet.getString("algorithm");
        return new Implementation (fileName, description, algorithm);
    }
    
    public boolean deleteImplementation(Implementation implementation) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE filename = ?;");
            ps.setString(1, implementation.fileName);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert implementation: " + e.getMessage());
        }
    }

}