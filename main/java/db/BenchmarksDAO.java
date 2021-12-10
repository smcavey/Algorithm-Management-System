package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Implementation;
import model.Benchmark;

public class BenchmarksDAO {
	
	java.sql.Connection conn;
	
	final String tblName = "benchmarks";

	public BenchmarksDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public boolean createBenchmark(Benchmark benchmark) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, benchmark.name);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Benchmark b = generateBenchmark(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,l1cache,l2cache,l3cache,ram,threads,cores,manufacturer,implementation) values(?,?,?,?,?,?,?,?,?);");
            ps.setString(1, benchmark.name);
            ps.setString(2, benchmark.l1cache);
            ps.setString(3, benchmark.l2cache);
            ps.setString(4, benchmark.l3cache);
            ps.setString(5, benchmark.ram);
            ps.setInt(6, benchmark.threads);
            ps.setInt(7, benchmark.cores);
            ps.setString(8, benchmark.manufacturer);
            ps.setString(9, benchmark.implementation);
            ps.execute();

            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity("", ps.toString()); // set token to empty, anonymous user

            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert benchmark: " + e.getMessage());
        }
    }

    public Benchmark getBenchmark(String name) throws Exception {
        
        try {
            Benchmark benchmark = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                benchmark = generateBenchmark(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return benchmark;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

    private Benchmark generateBenchmark(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("name");
        String l1cache = resultSet.getString("l1cache");
        String l2cache = resultSet.getString("l2cache");
        String l3cache = resultSet.getString("l3cache");
        String ram = resultSet.getString("ram");
        int threads = resultSet.getInt("threads");
        int cores = resultSet.getInt("cores");
        String manufacturer = resultSet.getString("manufacturer");
        String implementation = resultSet.getString("implementation");
        return new Benchmark (name, l1cache, l2cache, l3cache, ram, threads, cores, manufacturer, implementation);
    }
    
	public boolean searchForBenchmark(String name) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            
            Benchmark benchmark = null;
            while (resultSet.next()) {
                benchmark = generateBenchmark(resultSet);
            }
            resultSet.close();
            ps.close();

            if (name.equals(benchmark.name)) {
            	return true;
            } else {
            	return false;
            }

        } catch (Exception e) {
            throw new Exception("Failed to get benchmark: " + e.getMessage());
        }
    }
	
    public List<Benchmark> getAllBenchmarks(String implementation) throws Exception {
        
        List<Benchmark> allBenchmarks = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE implementation = ?;");
            ps.setString(1, implementation);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
            	Benchmark b = generateBenchmark(resultSet);
                allBenchmarks.add(b);
            }
            resultSet.close();
            statement.close();
            return allBenchmarks;

        } catch (Exception e) {
            throw new Exception("Failed in getting benchmarks: " + e.getMessage());
        }
    }
    
    public boolean deleteBenchmark(Benchmark benchmark) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, benchmark.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete benchmark: " + e.getMessage());
        }
    }

    public boolean deleteBenchmarkGivenImplementation(Benchmark benchmark) throws Exception{
    	try {
    		PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE implementation = ?;");
            ps.setString(1, benchmark.implementation);
            int numAffected = ps.executeUpdate();
            ps.close();

            // data modified, log query
    		UserActivityDAO ua = new UserActivityDAO();
    		ua.createUserActivity("", ps.toString()); // set token to empty, anonymous user

            return(numAffected == 1);
    	} catch (Exception e) {
    		throw new Exception("Failed to delete benchmark: " + e.getMessage());
    	}
    }

}