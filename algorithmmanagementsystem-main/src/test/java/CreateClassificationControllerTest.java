import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import com.amazonaws.lambda.demo.CreateClassificationController;
import com.google.gson.Gson;

import http.CreateClassificationRequest;
import http.CreateClassificationResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateClassificationControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	CreateClassificationController handler = new CreateClassificationController();
    	CreateClassificationRequest req = new Gson().fromJson(incoming, CreateClassificationRequest.class);
        CreateClassificationResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testCreateClassificationSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"unitTestTest\", \"description\": \"testClassificationDescription\", \"token\": \"mtdll4k2cgflr3et82fgekdn3q\"}";
        String RESULT = "unitTestTest";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}