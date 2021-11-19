import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.DeleteBenchmarkController;
import com.google.gson.Gson;

import http.DeleteBenchmarkRequest;
import http.DeleteBenchmarkResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteBenchmarkControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	DeleteBenchmarkController handler = new DeleteBenchmarkController();
    	DeleteBenchmarkRequest req = new Gson().fromJson(incoming, DeleteBenchmarkRequest.class);
    	DeleteBenchmarkResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testDeleteBenchmarkSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testBenchmarkDelete\", \"token\": \"mtdll4k2cgflr3et82fgekdn3q\"}";
        String RESULT = "testBenchmarkDelete";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}