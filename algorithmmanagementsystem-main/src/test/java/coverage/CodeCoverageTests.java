package coverage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;

import com.amazonaws.lambda.demo.CreateAlgorithmController;
import com.amazonaws.lambda.demo.CreateBenchmarkController;
import com.amazonaws.lambda.demo.CreateClassificationController;
import com.amazonaws.lambda.demo.CreateImplementationController;
import com.amazonaws.lambda.demo.CreateProblemInstanceController;
import com.amazonaws.lambda.demo.CreateSubClassificationController;
import com.amazonaws.lambda.demo.CreateUserController;
import com.amazonaws.lambda.demo.DeleteAlgorithmController;
import com.amazonaws.lambda.demo.DeleteBenchmarkController;
import com.amazonaws.lambda.demo.DeleteClassificationController;
import com.amazonaws.lambda.demo.DeleteImplementationController;
import com.amazonaws.lambda.demo.DeleteProblemInstanceController;
import com.amazonaws.lambda.demo.DeleteUserController;
import com.amazonaws.lambda.demo.GetAlgorithmsController;
import com.amazonaws.lambda.demo.GetBenchmarksController;
import com.amazonaws.lambda.demo.GetClassificationsController;
import com.amazonaws.lambda.demo.GetImplementationsController;
import com.amazonaws.lambda.demo.GetProblemInstancesController;
import com.amazonaws.lambda.demo.GetSubClassificationsController;
import com.amazonaws.lambda.demo.GetUserActivityController;
import com.amazonaws.lambda.demo.GetUsersController;
import com.amazonaws.lambda.demo.LoginController;
import com.amazonaws.lambda.demo.MergeClassificationController;
import com.amazonaws.lambda.demo.ReclassifyAlgorithmController;
import com.google.gson.Gson;

import db.DatabaseUtil;
import helpers.LambdaTest;
import http.CreateAlgorithmRequest;
import http.CreateAlgorithmResponse;
import http.CreateBenchmarkRequest;
import http.CreateBenchmarkResponse;
import http.CreateClassificationRequest;
import http.CreateClassificationResponse;
import http.CreateImplementationRequest;
import http.CreateImplementationResponse;
import http.CreateProblemInstanceRequest;
import http.CreateProblemInstanceResponse;
import http.CreateSubClassificationRequest;
import http.CreateSubClassificationResponse;
import http.CreateUserRequest;
import http.CreateUserResponse;
import http.DeleteAlgorithmRequest;
import http.DeleteAlgorithmResponse;
import http.DeleteBenchmarkRequest;
import http.DeleteBenchmarkResponse;
import http.DeleteClassificationRequest;
import http.DeleteClassificationResponse;
import http.DeleteImplementationRequest;
import http.DeleteImplementationResponse;
import http.DeleteProblemInstanceRequest;
import http.DeleteProblemInstanceResponse;
import http.DeleteUserRequest;
import http.DeleteUserResponse;
import http.GetAlgorithmsRequest;
import http.GetAlgorithmsResponse;
import http.GetBenchmarksRequest;
import http.GetBenchmarksResponse;
import http.GetClassificationsResponse;
import http.GetImplementationsRequest;
import http.GetImplementationsResponse;
import http.GetProblemInstancesRequest;
import http.GetProblemInstancesResponse;
import http.GetSubClassificationsRequest;
import http.GetSubClassificationsResponse;
import http.GetUserActivityRequest;
import http.GetUserActivityResponse;
import http.GetUsersRequest;
import http.GetUsersResponse;
import http.LoginRequest;
import http.LoginResponse;
import http.MergeClassificationRequest;
import http.MergeClassificationResponse;
import http.ReclassifyAlgorithmRequest;
import http.ReclassifyAlgorithmResponse;
import model.Benchmark;
import model.Classification;
import model.Implementation;
import model.ProblemInstance;
import model.User;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CodeCoverageTests extends LambdaTest {
	
	static String validToken;

    @BeforeClass
    // runs once before all tests
    public static void truncateTables() {
    	DatabaseUtil.setUseTestDb(true);
    	Connection conn;
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}

    	String[] tables = {"algorithms", "benchmarks", "classifications", "implementations", "probleminstances", "user_activity", "users"};
    	PreparedStatement ps;
		try {
			for (String table : tables) {
				ps = conn.prepareStatement("DELETE FROM " + table + ";");
		        int rowsModified = ps.executeUpdate();
		        System.out.println("Truncated table " + table + ". Removed " + rowsModified + " rows.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Before
    // runs before every single test
    public void initDbConnection() {
    	// TODO: what do we need (or want) to do here??
    }

    void testCreateUser(String incoming, String outgoing) throws IOException {
    	CreateUserController handler = new CreateUserController();
    	CreateUserRequest req = new Gson().fromJson(incoming, CreateUserRequest.class);
        CreateUserResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testa1_CreateUser() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testCoverage\", \"password\": \"token\"}";
        String RESULT = "testCoverage";
        
        try {
        	testCreateUser(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testa2_CreateUser() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testCoverage2\", \"password\": \"token2\"}";
        String RESULT = "testCoverage2";
        
        try {
        	testCreateUser(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testa3_CreateUser() { // duplicate of a2 to create a 400 code
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testCoverage2\", \"password\": \"token2\"}";
        String RESULT = "testCoverage2";
        
        try {
        	testCreateUser(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testLoginUser(String incoming, String outgoing) throws IOException {
    	LoginController handler = new LoginController();
    	LoginRequest req = new Gson().fromJson(incoming, LoginRequest.class);
        LoginResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();
        validToken = response.token;
        
        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testa4_LoginUser() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testCoverage\", \"password\": \"token\"}";
        String RESULT = "testCoverage";
        
        try {
        	testLoginUser(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateClassification(String incoming, String outgoing) throws IOException {
    	CreateClassificationController handler = new CreateClassificationController();
    	CreateClassificationRequest req = new Gson().fromJson(incoming, CreateClassificationRequest.class);
        CreateClassificationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testa5_CreateClassification() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationOne\", \"description\": \"testDescriptionOne\", \"token\": %s}", validToken);
        String RESULT = "testClassificationOne";
        
        try {
        	testCreateClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testa6_CreateClassification() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationTwo\", \"description\": \"testDescriptionTwo\", \"token\": %s}", validToken);
        String RESULT = "testClassificationTwo";
        
        try {
        	testCreateClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testa7_CreateClassification() { // duplicate of a6 to catch a 400 code
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationTwo\", \"description\": \"testDescriptionTwo\", \"token\": %s}", validToken);
        String RESULT = "testClassificationTwo";
        
        try {
        	testCreateClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateSubClassification(String incoming, String outgoing) throws IOException {
    	CreateSubClassificationController handler = new CreateSubClassificationController();
    	CreateSubClassificationRequest req = new Gson().fromJson(incoming, CreateSubClassificationRequest.class);
        CreateSubClassificationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testa8_CreateSubClassification() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationThree\", \"description\": \"testDescriptionThree\", \"classification\": \"testClassificationOne\", \"token\": %s}", validToken);
        String RESULT = "testClassificationThree";
        
        try {
        	testCreateSubClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testa9_CreateSubClassification() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationFour\", \"description\": \"testDescriptionFour\", \"classification\": \"testClassificationTwo\", \"token\": %s}", validToken);
        String RESULT = "testClassificationFour";
        
        try {
        	testCreateSubClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb1_CreateSubClassification() { // duplicate of a9 to catch a 400 code
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationFour\", \"description\": \"testDescriptionFour\", \"classification\": \"testClassificationTwo\", \"token\": %s}", validToken);
        String RESULT = "testClassificationFour";
        
        try {
        	testCreateSubClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateAlgorithm(String incoming, String outgoing) throws IOException {
    	CreateAlgorithmController handler = new CreateAlgorithmController();
    	CreateAlgorithmRequest req = new Gson().fromJson(incoming, CreateAlgorithmRequest.class);
        CreateAlgorithmResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testb2_CreateAlgorithm() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testAlgorithmOne\", \"description\": \"testDescriptionOne\", \"classification\": \"testClassificationOne\", \"token\": %s}", validToken);
        String RESULT = "testAlgorithmOne";
        
        try {
        	testCreateAlgorithm(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb3_CreateAlgorithm() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testAlgorithmTwo\", \"description\": \"testDescriptionTwo\", \"classification\": \"testClassificationTwo\", \"token\": %s}", validToken);
        String RESULT = "testAlgorithmTwo";
        
        try {
        	testCreateAlgorithm(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb4_CreateAlgorithm() { // duplicate of b3 to catch a 400 code
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testAlgorithmTwo\", \"description\": \"testDescriptionTwo\", \"classification\": \"testClassificationTwo\", \"token\": %s}", validToken);
        String RESULT = "testAlgorithmTwo";
        
        try {
        	testCreateAlgorithm(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateImplementation(String incoming, String outgoing) throws IOException {
    	CreateImplementationController handler = new CreateImplementationController();
    	CreateImplementationRequest req = new Gson().fromJson(incoming, CreateImplementationRequest.class);
        CreateImplementationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testb5_CreateImplementation() {
        String SAMPLE_INPUT_STRING = String.format("{\"fileName\": \"testImplementationOne.java\", \"algorithm\": \"testAlgorithmOne\", \"description\": \"test\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\", \"token\" : %s}", validToken);
        String RESULT = "testImplementationOne.java";
        
        try {
        	testCreateImplementation(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb6_CreateImplementation() {
        String SAMPLE_INPUT_STRING = String.format("{\"fileName\": \"testImplementationTwo.java\", \"algorithm\": \"testAlgorithmTwo\", \"description\": \"test\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\", \"token\" : %s}", validToken);
        String RESULT = "testImplementationTwo.java";
        
        try {
        	testCreateImplementation(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb7_CreateImplementation() { // duplicate of b6 to generate a fail
        String SAMPLE_INPUT_STRING = String.format("{\"fileName\": \"testImplementationTwo.java\", \"algorithm\": \"testAlgorithmTwo\", \"description\": \"test\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\", \"token\" : %s}", validToken);
        String RESULT = "testImplementationTwo.java";
        
        try {
        	testCreateImplementation(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateProblemInstance(String incoming, String outgoing) throws IOException {
    	CreateProblemInstanceController handler = new CreateProblemInstanceController();
    	CreateProblemInstanceRequest req = new Gson().fromJson(incoming, CreateProblemInstanceRequest.class);
        CreateProblemInstanceResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testb8_CreateProblemInstance() {
    	String SAMPLE_INPUT_STRING = String.format("{\"filename\": \"testFileNameOne.txt\", \"algorithm\": \"testAlgorithmOne\", \"fileContent\": \"bWFueSBwcm9ibGVtLCBzdWNoIGluc3RhbmNl\", \"token\": %s}", validToken);
        String RESULT = "testFileNameOne.txt";
        
        try {
        	testCreateProblemInstance(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testb9_CreateProblemInstance() {
    	String SAMPLE_INPUT_STRING = String.format("{\"filename\": \"testFileNameTwo.txt\", \"algorithm\": \"testAlgorithmTwo\", \"fileContent\": \"bWFueSBwcm9ibGVtLCBzdWNoIGluc3RhbmNl\", \"token\": %s}", validToken);
        String RESULT = "testFileNameTwo.txt";
        
        try {
        	testCreateProblemInstance(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testc1_CreateProblemInstance() { // duplicate of b9 to catch a 400 code
    	String SAMPLE_INPUT_STRING = String.format("{\"filename\": \"testFileNameTwo.txt\", \"algorithm\": \"testAlgorithmTwo\", \"fileContent\": \"bWFueSBwcm9ibGVtLCBzdWNoIGluc3RhbmNl\", \"token\": %s}", validToken);
        String RESULT = "testFileNameTwo.txt";
        
        try {
        	testCreateProblemInstance(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateBenchmark(String incoming, String outgoing) throws IOException {
    	CreateBenchmarkController handler = new CreateBenchmarkController();
    	CreateBenchmarkRequest req = new Gson().fromJson(incoming, CreateBenchmarkRequest.class);
        CreateBenchmarkResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testc2_CreateBenchmark() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testBenchmarkOne\", \"l1cache\": \"8 MB\", \"l2cache\": \"16 MB\", \"l3cache\": \"16 MB\", \"threads\": \"16\", \"cores\": \"8\", \"manufacturer\": \"intel\", \"implementation\": \"testImplementationOne\"}";
        String RESULT = "testBenchmarkOne";
        
        try {
        	testCreateBenchmark(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testc3_CreateBenchmark() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testBenchmarkTwo\", \"l1cache\": \"8 MB\", \"l2cache\": \"16 MB\", \"l3cache\": \"16 MB\", \"threads\": \"16\", \"cores\": \"8\", \"manufacturer\": \"intel\", \"implementation\": \"testImplementationTwo\"}";
        String RESULT = "testBenchmarkTwo";
        
        try {
        	testCreateBenchmark(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testc4_CreateBenchmark() { // duplicate of 21 to catch a 400 code
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testBenchmarkOne\", \"l1cache\": \"8 MB\", \"l2cache\": \"16 MB\", \"l3cache\": \"16 MB\", \"threads\": \"16\", \"cores\": \"8\", \"manufacturer\": \"intel\", \"implementation\": \"testImplementationOne\"}";
        String RESULT = "testBenchmarkOne";
        
        try {
        	testCreateBenchmark(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    @Test
    public void testc5_GetClassifications() throws IOException {
    	GetClassificationsController handler = new GetClassificationsController();
    	GetClassificationsResponse response = handler.handleRequest(null, createContext("compute"));
    	response.toString();

        Assert.assertEquals(200, response.httpCode);
    }
    
    void testGetAlgorithms(String incoming, String outgoing) throws IOException {
    	GetAlgorithmsController handler = new GetAlgorithmsController();
    	GetAlgorithmsRequest req = new Gson().fromJson(incoming, GetAlgorithmsRequest.class);
        GetAlgorithmsResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testc6_GetAlgorithms() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testClassificationOne\"}";
        String RESULT = "testClassificationOne";
        
        try {
        	testGetAlgorithms(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testc7_GetAlgorithms() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"nothing\"}";
        String RESULT = "nothing";
        
        try {
        	testGetAlgorithms(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testGetImplementations(String incoming, String outgoing) throws IOException {
    	GetImplementationsController handler = new GetImplementationsController();
    	GetImplementationsRequest req = new Gson().fromJson(incoming, GetImplementationsRequest.class);
        GetImplementationsResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        for (Implementation implementation : response.list) {
        	Assert.assertEquals(outgoing + "/" + implementation.fileName, implementation.filePath);
        }
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testc8_GetImplementations() {
        String SAMPLE_INPUT_STRING = "{\"name\": \"testAlgorithmOne\"}";
        String RESULT = "file path";

        try {
        	testGetImplementations(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testGetBenchmarks(String incoming, String outgoing) throws IOException {
    	GetBenchmarksController handler = new GetBenchmarksController();
    	GetBenchmarksRequest req = new Gson().fromJson(incoming, GetBenchmarksRequest.class);
        GetBenchmarksResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testc9_GetBenchmarks() {
    	String SAMPLE_INPUT_STRING = "{\"filename\": \"QuickSort.java\"}";
        String RESULT = "QuickSort.java";
        
        try {
        	testGetBenchmarks(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testGetProblemInstances(String incoming, String outgoing) throws IOException {
    	GetProblemInstancesController handler = new GetProblemInstancesController();
    	GetProblemInstancesRequest req = new Gson().fromJson(incoming, GetProblemInstancesRequest.class);
        GetProblemInstancesResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(200, response.httpCode);
    }

    @Test
    public void testd1_GetProblemInstances() {
        String SAMPLE_INPUT_STRING = "{\"name\": \"testAlgorithmOne\"}";
        String RESULT = "file path";

        try {
        	testGetProblemInstances(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testReclassifyAlgorithm(String incoming, String outgoing) throws IOException {
    	ReclassifyAlgorithmController handler = new ReclassifyAlgorithmController();
    	ReclassifyAlgorithmRequest req = new Gson().fromJson(incoming, ReclassifyAlgorithmRequest.class);
    	ReclassifyAlgorithmResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(201, response.httpCode);
    }

	@Test
    public void testd2_ReclassifyAlgorithm() {
    	String SAMPLE_INPUT_STRING = String.format("{\"algorithm\": \"testAlgorithmOne\", \"classification\": \"testAlgorithmTwo\", \"token\": %s}", validToken);
        String RESULT = "abcdefghijkl";
        
        try {
        	testReclassifyAlgorithm(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testMergeClassifications(String incoming, String outgoing) throws IOException {
    	MergeClassificationController handler = new MergeClassificationController();
    	MergeClassificationRequest req = new Gson().fromJson(incoming, MergeClassificationRequest.class);
    	MergeClassificationResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(201, response.httpCode);
    }

	@Test
    public void testd3_MergeClassifications() {
    	String SAMPLE_INPUT_STRING = String.format("{\"classificationOne\": \"testClassificationOne\", \"classificationTwo\": \"testClassificationTwo\", \"classificationNew\": \"testClassificationThree\", \"token\": %s}", validToken);
        String RESULT = "testClassificationThree";
        
        try {
        	testMergeClassifications(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
	
    void testDeleteBenchmark(String incoming, String outgoing) throws IOException {
    	DeleteBenchmarkController handler = new DeleteBenchmarkController();
    	DeleteBenchmarkRequest req = new Gson().fromJson(incoming, DeleteBenchmarkRequest.class);
    	DeleteBenchmarkResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testd4_DeleteBenchmark() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testBenchmarkOne\", \"token\": %s}", validToken);
        String RESULT = "testBenchmarkOne";
        
        try {
        	testDeleteBenchmark(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testd5_DeleteBenchmark() { // copy of d4 to try to delete something that doesn't exist
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testBenchmarkOne\", \"token\": %s}", validToken);
        String RESULT = "testBenchmarkOne";
        
        try {
        	testDeleteBenchmark(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testDeleteImplementation(String incoming, String outgoing) throws IOException {
    	DeleteImplementationController handler = new DeleteImplementationController();
    	DeleteImplementationRequest req = new Gson().fromJson(incoming, DeleteImplementationRequest.class);
    	DeleteImplementationResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testd6_DeleteImplementation() { // failing because the createImplementation unit test isn't working for some reason
    	String SAMPLE_INPUT_STRING = String.format("{\"filename\": \"testImplementationOne.java\", \"token\": %s}", validToken);
        String RESULT = "testImplementationOne.java";
        
        try {
        	testDeleteImplementation(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testDeleteProblemInstance(String incoming, String outgoing) throws IOException {
    	DeleteProblemInstanceController handler = new DeleteProblemInstanceController();
    	DeleteProblemInstanceRequest req = new Gson().fromJson(incoming, DeleteProblemInstanceRequest.class);
    	DeleteProblemInstanceResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testd7_DeleteProblemInstance() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testFileNameOne.txt\", \"token\": %s}", validToken);
        String RESULT = "testFileNameOne.txt";
        
        try {
        	testDeleteProblemInstance(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testDeleteAlgorithm(String incoming, String outgoing) throws IOException {
    	DeleteAlgorithmController handler = new DeleteAlgorithmController();
    	DeleteAlgorithmRequest req = new Gson().fromJson(incoming, DeleteAlgorithmRequest.class);
    	DeleteAlgorithmResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testd8_DeleteAlgorithm() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testAlgorithmOne\", \"token\": %s}", validToken);
        String RESULT = "testAlgorithmOne";
        
        try {
        	testDeleteAlgorithm(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testDeleteClassification(String incoming, String outgoing) throws IOException {
    	DeleteClassificationController handler = new DeleteClassificationController();
    	DeleteClassificationRequest req = new Gson().fromJson(incoming, DeleteClassificationRequest.class);
    	DeleteClassificationResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testd9_DeleteClassification() {
    	String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testClassificationThree\", \"token\": %s}", validToken);
        String RESULT = "testClassificationThree";
        
        try {
        	testDeleteClassification(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void teste1_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest("test");
    	Assert.assertEquals("test", ccr.getName());
    }
    
    @Test
    public void teste2_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest("test", "description");
    	Assert.assertEquals("description", ccr.getDescription());
    }
    
    @Test
    public void teste3_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest("test", "description", "token");
    	Assert.assertEquals("token", ccr.getToken());
    }
    
    @Test
    public void teste4_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest();
    	ccr.setName("test");
    	Assert.assertEquals("test", ccr.getName());
    }
    
    @Test
    public void teste5_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest();
    	ccr.setDescription("description");
    	Assert.assertEquals("description", ccr.getDescription());
    }
    
    @Test
    public void teste6_CreateClassificationRequest() {
    	CreateClassificationRequest ccr = new CreateClassificationRequest();
    	ccr.setToken("token");
    	Assert.assertEquals("token", ccr.getToken());
    }
    
    @Test
    public void teste7_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest();
    	car.setName("name");
    	Assert.assertEquals("name", car.getName());
    }
    
    @Test
    public void teste8_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest();
    	car.setDescription("description");
    	Assert.assertEquals("description", car.getDescription());
    }
    
    @Test
    public void teste9_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest();
    	car.setClassification("classification");
    	Assert.assertEquals("classification", car.getClassification());
    }
    
    @Test
    public void testf1_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest();
    	car.settoken("token");
    	Assert.assertEquals("token", car.getToken());
    }
    
    @Test
    public void testf2_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest("name", "description", "classification");
    	Assert.assertEquals("classification", car.getClassification());
    }
    
    @Test
    public void testf3_CreateAlgorithmRequest() {
    	CreateAlgorithmRequest car = new CreateAlgorithmRequest("name", "description", "classification", "token");
    	Assert.assertEquals("classification", car.getClassification());
    }
    
    @Test
    public void testf4_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest("content", "fileName", "description", "algorithm", "token");
    	Assert.assertEquals("content", cir.getFileContent());
    }
    
    @Test
    public void testf5_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest();
    	cir.setFileName("name");
    	Assert.assertEquals("name", cir.getFileName());
    }
    
    @Test
    public void testf6_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest();
    	cir.setFileContent("content");
    	Assert.assertEquals("content", cir.getFileContent());
    }
    
    @Test
    public void testf7_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest();
    	cir.setAlgorithm("algorithm");
    	Assert.assertEquals("algorithm", cir.getAlgorithm());
    }
    
    @Test
    public void testf8_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest();
    	cir.setDescription("description");
    	Assert.assertEquals("description", cir.getDescription());
    }
    
    @Test
    public void testf9_CreateImplementationRequest() {
    	CreateImplementationRequest cir = new CreateImplementationRequest();
    	cir.setToken("token");
    	Assert.assertEquals("token", cir.getToken());
    }
    
    @Test
    public void testg1_CreateProblemInstanceRequest() {
    	CreateProblemInstanceRequest cpir = new CreateProblemInstanceRequest("content", "name", "algorithm", "token");
    	Assert.assertEquals("token", cpir.getToken());
    }
    
    @Test
    public void testg2_CreateProblemInstanceRequest() {
    	CreateProblemInstanceRequest cpir = new CreateProblemInstanceRequest();
    	cpir.setFileContent("content");
    	Assert.assertEquals("content", cpir.getFileContent());
    }
    
    @Test
    public void testg3_CreateProblemInstanceRequest() {
    	CreateProblemInstanceRequest cpir = new CreateProblemInstanceRequest();
    	cpir.setAlgorithm("algorithm");
    	Assert.assertEquals("algorithm", cpir.getAlgorithm());
    }
    
    @Test
    public void testg4_CreateProblemInstanceRequest() {
    	CreateProblemInstanceRequest cpir = new CreateProblemInstanceRequest();
    	cpir.setFileName("name");
    	Assert.assertEquals("name", cpir.getFileName());
    }
    
    @Test
    public void testg5_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest("name", "l1cache", "l2cache", "l3cache", "ram", 8,  4, "manufacturer", "implementation");
    	Assert.assertEquals(4, cbr.getCores());
    }
    
    @Test
    public void testg6_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setName("name");
    	Assert.assertEquals("name", cbr.getName());
    }
    
    @Test
    public void testg7_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setl1cache("l1cache");
    	Assert.assertEquals("l1cache", cbr.getl1cache());
    }
    
    @Test
    public void testg8_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setl2cache("l2cache");
    	Assert.assertEquals("l2cache", cbr.getl2cache());
    }
    
    @Test
    public void testg9_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setl3cache("l3cache");
    	Assert.assertEquals("l3cache", cbr.getl3cache());
    }
    
    @Test
    public void testh1_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setManufacturer("Intel");
    	Assert.assertEquals("Intel", cbr.getManufacturer());
    }
    
    @Test
    public void testh2_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setRam("8 GB");
    	Assert.assertEquals("8 GB", cbr.getRam());
    }
    
    @Test
    public void testh3_CreateBenchmarkRequest() {
    	CreateBenchmarkRequest cbr = new CreateBenchmarkRequest();
    	cbr.setThreads(16);
    	Assert.assertEquals(16, cbr.getThreads());
    }
    
    @Test
    public void testh4_MergeClassificationRequest() {
    	MergeClassificationRequest mcr = new MergeClassificationRequest("token", "one", "two", "three");
    	Assert.assertEquals("token", mcr.getToken());
    }
    
    @Test
    public void testh5_MergeClassificationRequest() {
    	MergeClassificationRequest mcr = new MergeClassificationRequest();
    	mcr.setClassificationOne("one");
    	Assert.assertEquals("one", mcr.getClassificationOne());
    }
    
    @Test
    public void testh6_MergeClassificationRequest() {
    	MergeClassificationRequest mcr = new MergeClassificationRequest();
    	mcr.setClassificationTwo("two");
    	Assert.assertEquals("two", mcr.getClassificationTwo());
    }
    
    @Test
    public void testh7_MergeClassificationRequest() {
    	MergeClassificationRequest mcr = new MergeClassificationRequest();
    	mcr.setClassificationNew("new");
    	Assert.assertEquals("new", mcr.getClassificationNew());
    }
    
    @Test
    public void testh8_ReclassifyAlgorithmRequest() {
    	ReclassifyAlgorithmRequest rar = new ReclassifyAlgorithmRequest("token", "algorithm", "classification");
    	Assert.assertEquals("classification", rar.getClassification());
    }
    
    @Test
    public void testh9_ReclassifyAlgorithmRequest() {
    	ReclassifyAlgorithmRequest rar = new ReclassifyAlgorithmRequest();
    	rar.setToken("token");
    	Assert.assertEquals("token", rar.getToken());
    }
    
    @Test
    public void testi1_ReclassifyAlgorithmRequest() {
    	ReclassifyAlgorithmRequest rar = new ReclassifyAlgorithmRequest();
    	rar.setAlgorithm("algorithm");
    	Assert.assertEquals("algorithm", rar.getAlgorithm());
    }

    // NOTE:SKIPPING A LETTER
    
    void testGetUsers(String incoming, String outgoing) throws IOException {
    	GetUsersController handler = new GetUsersController();
    	GetUsersRequest req = new Gson().fromJson(incoming, GetUsersRequest.class);
    	GetUsersResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
        response.toString();

        Assert.assertEquals(200, response.httpCode);
    }

    @Test
    public void testj1_GetUsers() {
        String SAMPLE_INPUT_STRING = "{}";
        String RESULT = "dynamic";

        try {
        	testGetUsers(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testGetUserActivity(String incoming, String outgoing) throws IOException {
    	GetUserActivityController handler = new GetUserActivityController();
    	GetUserActivityRequest req = new Gson().fromJson(incoming, GetUserActivityRequest.class);
    	GetUserActivityResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
        response.toString();

        Assert.assertEquals(200, response.httpCode);
    }

    @Test
    public void testj2_GetUserActivity() {
        String SAMPLE_INPUT_STRING = String.format("{\"name\": \"testCoverage\", \"token\": %s}", validToken);
        String RESULT = "dynamic";

        try {
        	testGetUserActivity(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testj3_LoginRequest() {
    	LoginRequest lr = new LoginRequest("username", "password");
    	Assert.assertEquals("username", lr.getUserame());
    }
    
    @Test
    public void testj4_LoginRequest() {
    	LoginRequest lr = new LoginRequest("username", "password");
    	Assert.assertEquals("password", lr.getPassword());
    }
    
    @Test
    public void testj5_LoginRequest() {
    	LoginRequest lr = new LoginRequest();
    	lr.setUsername("username");
    	Assert.assertEquals("username", lr.getUserame());
    }
    
    @Test
    public void testj6_LoginRequest() {
    	LoginRequest lr = new LoginRequest();
    	lr.setPassword("password");
    	Assert.assertEquals("password", lr.getPassword());
    }
    
    @Test
    public void testj7_GetUsersRequest() {
    	GetUsersRequest gur = new GetUsersRequest("token");
    	Assert.assertEquals("token", gur.getToken());
    }
    
    @Test
    public void testj8_GetUserActivityRequest() {
    	GetUserActivityRequest guar = new GetUserActivityRequest("name");
    	Assert.assertEquals("name", guar.getName());
    }
    
    @Test
    public void testj9_GetUserActivityRequest() {
    	GetUserActivityRequest guar = new GetUserActivityRequest("name", "token");
    	Assert.assertEquals("token", guar.getToken());
    }
    
    @Test
    public void testk1_GetSubClassificationsRequest() {
    	GetSubClassificationsRequest gscr = new GetSubClassificationsRequest("name");
    	Assert.assertEquals("name", gscr.getName());
    }
    
    @Test
    public void testk2_GetSubClassificationsRequest() {
    	GetSubClassificationsRequest gscr = new GetSubClassificationsRequest();
    	gscr.setName("name");
    	Assert.assertEquals("name", gscr.getName());
    }
    
    @Test
    public void testk3_GetProblemInstancesRequest() {
    	GetProblemInstancesRequest gpir = new GetProblemInstancesRequest("name");
    	Assert.assertEquals("name", gpir.getName());
    }
    
    @Test
    public void testk4_GetImplementationsRequest() {
    	GetImplementationsRequest gir = new GetImplementationsRequest("name");
    	Assert.assertEquals("name", gir.getName());
    }
    
    @Test
    public void testk4_GetBenchmarksRequest() {
    	GetBenchmarksRequest gbr = new GetBenchmarksRequest("name");
    	Assert.assertEquals("name", gbr.getName());
    }
    
    @Test
    public void testk5_GetAlgorithmsRequest() {
    	GetAlgorithmsRequest gar = new GetAlgorithmsRequest("name");
    	Assert.assertEquals("name", gar.getName());
    }
    
    @Test
    public void testk6_DeleteUserRequest() {
    	DeleteUserRequest dur = new DeleteUserRequest("name");
    	Assert.assertEquals("name", dur.getUserName());
    }
    
    @Test
    public void testk7_DeleteUserRequest() {
    	DeleteUserRequest dur = new DeleteUserRequest();
    	dur.setName("name");
    	Assert.assertEquals("name", dur.getUserName());
    }
    
    @Test
    public void testk8_DeleteProblemInstanceRequest() {
    	DeleteProblemInstanceRequest dpir = new DeleteProblemInstanceRequest();
    	dpir.setName("name");
    	Assert.assertEquals("name", dpir.getName());
    }
    
    @Test
    public void testk9_DeleteProblemInstanceRequest() {
    	DeleteProblemInstanceRequest dpir = new DeleteProblemInstanceRequest("name", "token");
    	Assert.assertEquals("token", dpir.getToken());
    }
    
    @Test
    public void testl1_DeleteImplementationRequest() {
    	DeleteImplementationRequest dir = new DeleteImplementationRequest();
    	dir.setName("name");
    	Assert.assertEquals("name", dir.getName());
    }
    
    @Test
    public void testl2_DeleteImplementationRequest() {
    	DeleteImplementationRequest dir = new DeleteImplementationRequest("name", "token");
    	Assert.assertEquals("token", dir.getToken());
    }
    
    @Test
    public void testl3_DeleteClassificationRequest() {
    	DeleteClassificationRequest dcr = new DeleteClassificationRequest();
    	dcr.setName("name");
    	Assert.assertEquals("name", dcr.getName());
    }
    
    @Test
    public void testl4_DeleteClassificationRequest() {
    	DeleteClassificationRequest dcr = new DeleteClassificationRequest("name", "token");
    	Assert.assertEquals("token", dcr.getToken());
    }
    
    @Test
    public void testl5_DeleteBenchmarkRequest() {
    	DeleteBenchmarkRequest dbr = new DeleteBenchmarkRequest();
    	dbr.setName("name");
    	Assert.assertEquals("name", dbr.getName());
    }
    
    @Test
    public void testl6_DeleteBenchmarkRequest() {
    	DeleteBenchmarkRequest dbr = new DeleteBenchmarkRequest("name", "token");
    	Assert.assertEquals("token", dbr.getToken());
    }
    
    @Test
    public void testl7_DeleteAlgorithmRequest() {
    	DeleteAlgorithmRequest dar = new DeleteAlgorithmRequest();
    	dar.setName("name");
    	Assert.assertEquals("name", dar.getName());
    }
    
    @Test
    public void testl8_DeleteAlgorithmRequest() {
    	DeleteAlgorithmRequest dar = new DeleteAlgorithmRequest("name", "token");
    	Assert.assertEquals("token", dar.getToken());
    }
    
    @Test
    public void testl9_CreateUserRequest() {
    	CreateUserRequest cur = new CreateUserRequest();
    	cur.setUsername("name");
    	Assert.assertEquals("name", cur.getUsername());
    }
    
    @Test
    public void testm1_CreateUserRequest() {
    	CreateUserRequest cur = new CreateUserRequest("name", "password");
    	Assert.assertEquals("password", cur.getPassword());
    }
    
    @Test
    public void testm2_CreateSubClassificationRequest() {
    	CreateSubClassificationRequest cscr = new CreateSubClassificationRequest("name", "description", "classification");
    	Assert.assertEquals("name", cscr.getName());
    }
    
    @Test
    public void testm3_CreateSubClassificationRequest() {
    	CreateSubClassificationRequest cscr = new CreateSubClassificationRequest("name", "description", "classification", "token");
    	Assert.assertEquals("token", cscr.getToken());
    }
    
    @Test
    public void testm4_CreateSubClassificationRequest() {
    	CreateSubClassificationRequest cscr = new CreateSubClassificationRequest();
    	cscr.setDescription("description");
    	Assert.assertEquals("description", cscr.getDescription());
    }
    
    void testDeleteUser(String incoming, String outgoing) throws IOException {
    	DeleteUserController handler = new DeleteUserController();
    	DeleteUserRequest req = new Gson().fromJson(incoming, DeleteUserRequest.class);
    	DeleteUserResponse response = handler.handleRequest(req, createContext("compute"));
    	req.toString();
    	response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testm5_testDeleteUser() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testCoverage2\"}";
        String RESULT = "testCoverage2";
        
        try {
        	testDeleteUser(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testGetSubClassifications(String incoming, String outgoing) throws IOException {
    	GetSubClassificationsController handler = new GetSubClassificationsController();
    	GetSubClassificationsRequest req = new Gson().fromJson(incoming, GetSubClassificationsRequest.class);
        GetSubClassificationsResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();
        
        for(Classification c : response.list) {
        	System.out.println(c.toString());
        }

        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void m6_testGetSubClassificationsSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testClassificationThree\"}";
        String RESULT = "testClassificationThree";
        
        try {
        	testGetSubClassifications(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    void testCreateClassificationFail(String incoming, String outgoing) throws IOException {
    	CreateClassificationController handler = new CreateClassificationController();
    	CreateClassificationRequest req = new Gson().fromJson(incoming, CreateClassificationRequest.class);
        CreateClassificationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(400, response.httpCode);
    }
    
    @Test
    public void testm7_CreateClassification() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testClassificationFail\", \"description\": \"testDescriptionFail\", \"token\": \"failingtoken\"}";
        String RESULT = "testClassificationFail";
        
        try {
        	testCreateClassificationFail(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateSubClassificationFail(String incoming, String outgoing) throws IOException {
    	CreateSubClassificationController handler = new CreateSubClassificationController();
    	CreateSubClassificationRequest req = new Gson().fromJson(incoming, CreateSubClassificationRequest.class);
        CreateSubClassificationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(400, response.httpCode);
    }
    
    @Test
    public void testm8_CreateSubClassificationFail() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testSubClassificationFail\", \"description\": \"testDescriptionFail\", \"classification\": \"testClassificationOne\", \"token\": \"failingtoken\"}";
        String RESULT = "testSubClassificationFail";
        
        try {
        	testCreateSubClassificationFail(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateImplementationFail(String incoming, String outgoing) throws IOException {
    	CreateImplementationController handler = new CreateImplementationController();
    	CreateImplementationRequest req = new Gson().fromJson(incoming, CreateImplementationRequest.class);
        CreateImplementationResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(400, response.httpCode);
    }
    
    @Test
    public void testm9_CreateImplementationFail() {
        String SAMPLE_INPUT_STRING = "{\"fileName\": \"testImplementationOne.java\", \"algorithm\": \"testAlgorithmOne\", \"description\": \"test\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\", \"token\" : \"badtoken\"}";
        String RESULT = "testImplementationOne.java";
        
        try {
        	testCreateImplementationFail(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
    
    void testCreateProblemInstanceFail(String incoming, String outgoing) throws IOException {
    	CreateProblemInstanceController handler = new CreateProblemInstanceController();
    	CreateProblemInstanceRequest req = new Gson().fromJson(incoming, CreateProblemInstanceRequest.class);
        CreateProblemInstanceResponse response = handler.handleRequest(req, createContext("compute"));
        req.toString();
        response.toString();

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testn1_CreateProblemInstanceFail() {
    	String SAMPLE_INPUT_STRING = "{\"filename\": \"testFileNameOne.txt\", \"algorithm\": \"testAlgorithmOne\", \"fileContent\": \"bWFueSBwcm9ibGVtLCBzdWNoIGluc3RhbmNl\", \"token\": \"badtoken\"}";
        String RESULT = "testFileNameOne.txt";
        
        try {
        	testCreateProblemInstanceFail(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid: " + ioe.getMessage());
        }
    }
}
