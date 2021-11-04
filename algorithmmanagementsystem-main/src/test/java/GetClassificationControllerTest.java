import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import com.amazonaws.lambda.demo.GetClassificationController;
import com.google.gson.Gson;
import http.GetClassificationRequest;
import http.GetClassificationResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetClassificationControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	GetClassificationController handler = new GetClassificationController();
    	GetClassificationRequest req = new Gson().fromJson(incoming, GetClassificationRequest.class);
        GetClassificationResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testGetClassificationSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"testClassificationName1\", \"description\": \"testClassificationDescription\"}";
        String RESULT = "testClassificationName1";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}