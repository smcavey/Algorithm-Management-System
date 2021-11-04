

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.LoginController;
import com.google.gson.Gson;

import http.LoginRequest;
import http.LoginResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	LoginController handler = new LoginController();
    	LoginRequest req = new Gson().fromJson(incoming, LoginRequest.class);
        LoginResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
	
    void testFailInput(String incoming, String outgoing) throws IOException {
    	LoginController handler = new LoginController();
    	LoginRequest req = new Gson().fromJson(incoming, LoginRequest.class);

    	LoginResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(400, response.httpCode);
    }
    
    @Test
    public void testLoginSimple() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testUser\", \"password\": \"testPass\"}";
        String RESULT = "testUser";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testLoginFailSimple() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testUser\", \"password\": \"wrongPassword\"}";
        String RESULT = "testUser";
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
