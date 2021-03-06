import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.CreateAlgorithmController;
import com.amazonaws.lambda.demo.CreateClassificationController;
import com.google.gson.Gson;

import http.CreateAlgorithmRequest;
import http.CreateAlgorithmResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateAlgorithmControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	CreateAlgorithmController handler = new CreateAlgorithmController();
    	CreateAlgorithmRequest req = new Gson().fromJson(incoming, CreateAlgorithmRequest.class);
        CreateAlgorithmResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testCreateAlgorithmSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"unitTestTest\", \"description\": \"testAlgorithmDescription\", \"classification\": \"Sorting\", \"token\": \"mtdll4k2cgflr3et82fgekdn3q\"}";
        String RESULT = "unitTestTest";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}