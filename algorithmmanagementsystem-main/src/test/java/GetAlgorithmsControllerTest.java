import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.GetAlgorithmController;
import com.amazonaws.lambda.demo.GetAlgorithmsController;
import com.google.gson.Gson;

import http.GetAlgorithmsResponse;
import http.GetAlgorithmRequest;
import http.GetAlgorithmResponse;
import http.GetAlgorithmsRequest;
import model.Algorithm;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetAlgorithmsControllerTest extends LambdaTest {
	
//    @Test
//    public void testGetList() throws IOException {
//    	GetAlgorithmsController controller = new GetAlgorithmsController();
//
//        GetAlgorithmsResponse resp = controller.handleRequest(null, createContext("list"));
//        
//        boolean hasTestAlgorithm = false;
//        for (Algorithm c : resp.list) {
//        	System.out.println("found algorithm " + c);
//        	if (c.name.equals("testAlgorithm")) { hasTestAlgorithm = true; }
//        }
//        Assert.assertTrue("testAlgorithm needs to exist in the algorithms table for this test case to work.", hasTestAlgorithm);
//        Assert.assertEquals(200, resp.httpCode);
//    }

	
    public void testInput(String incoming, String outgoing) throws IOException {
    	GetAlgorithmsController handler = new GetAlgorithmsController();
    	GetAlgorithmsRequest req = new Gson().fromJson(incoming, GetAlgorithmsRequest.class);
        GetAlgorithmsResponse response = handler.handleRequest(req, createContext("compute"));
        for(Algorithm alg : response.list) {
        	System.out.println(alg.toString());
        }

//        Assert.assertEquals(outgoing, response.list);
        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testGetAlgorithmSimple() {
    	String SAMPLE_INPUT_STRING = "{\"name\": \"Quick Sort\"}";
        String RESULT = "Quick Sort";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}