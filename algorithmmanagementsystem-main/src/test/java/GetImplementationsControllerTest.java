import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.GetImplementationController;
import com.amazonaws.lambda.demo.GetImplementationsController;
import com.google.gson.Gson;

import http.GetImplementationRequest;
import http.GetImplementationResponse;
import http.GetImplementationsRequest;
import http.GetImplementationsResponse;
import model.Implementation;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */

public class GetImplementationsControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	GetImplementationsController handler = new GetImplementationsController();
    	GetImplementationsRequest req = new Gson().fromJson(incoming, GetImplementationsRequest.class);
        GetImplementationsResponse response = handler.handleRequest(req, createContext("compute"));

        for (Implementation implementation : response.list) {
        	Assert.assertEquals(outgoing + "/" + implementation.fileName, implementation.filePath);
        }
        Assert.assertEquals(200, response.statusCode);
    }

    @Test
    public void testGetImplementationsSimple() {
        String SAMPLE_INPUT_STRING = "{\"name\": \"testAlgorithm\"}";
        String RESULT = "file path";

        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
