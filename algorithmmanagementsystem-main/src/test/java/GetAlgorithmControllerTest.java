import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import com.amazonaws.lambda.demo.GetAlgorithmController;
import com.google.gson.Gson;
import http.GetAlgorithmRequest;
import http.GetAlgorithmResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetAlgorithmControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	GetAlgorithmController handler = new GetAlgorithmController();
    	GetAlgorithmRequest req = new Gson().fromJson(incoming, GetAlgorithmRequest.class);
        GetAlgorithmResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testGetAlgorithmSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testAlgorithm\"}";
        String RESULT = "testAlgorithm";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}