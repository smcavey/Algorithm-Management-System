import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.CreateUserController;
import com.google.gson.Gson;

import http.CreateUserRequest;
import http.CreateUserResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateUserControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	CreateUserController handler = new CreateUserController();
    	CreateUserRequest req = new Gson().fromJson(incoming, CreateUserRequest.class);
        CreateUserResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testCalculatorSimple() {
    	String SAMPLE_INPUT_STRING = "{\"username\": \"testToken\", \"password\": \"token\"}";
        String RESULT = "testToken";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
