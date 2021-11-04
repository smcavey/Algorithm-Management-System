package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, implementation.fileName);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Implementation i = generateImplementation(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (fileName,description) values(?,?);");
            ps.setString(1, implementation.fileName);
            ps.setString( 2, implementation.description);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert implementation: " + e.getMessage());
        }
    }
	
	public boolean searchForImplementaiton(String fileName) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
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

    private Implementation generateImplementation(ResultSet resultSet) throws Exception {
        String fileName  = resultSet.getString("fileName");
        String description = resultSet.getString("description");
        return new Implementation (fileName, description);
    }

}