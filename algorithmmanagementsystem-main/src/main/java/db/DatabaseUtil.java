package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

	// These should never be stored directly in code.  I am doing this quickly complete the 
	// demonstration code. The appropriate solution is to store these values in environment
	// variables that are accessed by the Lambda function at run time like this
	//
	//  dbUsername = System.getenv("calcAdmin");
	//  dbPassword = System.getenv("password");
	//  rdsMySqlDatabaseUrl = System.getenv("rdsMySqlDatabaseUrl");
	//
	// https://docs.aws.amazon.com/lambda/latest/dg/env_variables.html
	//
	// The above link shows how to do that.
	public static String rdsMySqlDatabaseUrl;
	public static String dbUsername;
	public static String dbPassword;

	public final static String jdbcTag = "jdbc:mysql://";
	public final static String rdsMySqlDatabasePort = "3306";
	public final static String multiQueries = "?allowMultiQueries=true";
	   
	public final static String dbName = "algorithmdb";  // Whatever database you created in tutorial.
	public final static String productionSchemaName = "algorithm";  // Whatever schema you created in tutorial.
	public final static String testingSchemaName = "coverage";      // used for testing / code coverage

	// pooled across all usages.
	static Connection conn;
	static boolean useTestDB = false;

	/**
	 * Singleton access to DB connection to share resources effectively across multiple accesses.
	 */
	public static Connection connect() throws Exception {
		if (conn != null) { return conn; }
		
		// this is resistant to any SQL-injection attack since we choose one of two possible ones.
		String schemaName = productionSchemaName;
		if (useTestDB) { 
			schemaName = testingSchemaName;
			System.out.println("USE TEST DB: " + testingSchemaName);
		}

		dbUsername = "calcAdmin";
		if (dbUsername == null) {
			System.err.println("Environment variable dbUsername is not set!");
		}

		dbPassword = "password";
		if (dbPassword == null) {
			System.err.println("Environment variable dbPassword is not set!");
		}

		rdsMySqlDatabaseUrl = dbName + ".c5huclabzubo.us-east-1.rds.amazonaws.com";
		if (rdsMySqlDatabaseUrl == null) {
			System.err.println("Environment variable rdsMySqlDatabaseUrl is not set!");
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection(
					jdbcTag + rdsMySqlDatabaseUrl + ":" + rdsMySqlDatabasePort + "/" + schemaName + multiQueries,
					dbUsername,
					dbPassword);
			return conn;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Failed in database connection");
		}
	}

	public static void setUseTestDb(boolean useTestDb) {
		useTestDB = useTestDb;
	}
}
