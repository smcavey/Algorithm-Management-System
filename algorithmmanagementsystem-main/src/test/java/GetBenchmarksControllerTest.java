import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.GetBenchmarksController;
import com.google.gson.Gson;

import http.GetBenchmarksResponse;
import http.GetBenchmarksRequest;
import model.Benchmark;

public class GetBenchmarksControllerTest extends LambdaTest {
	
	
    public void testInput(String incoming, String outgoing) throws IOException {
    	GetBenchmarksController handler = new GetBenchmarksController();
    	GetBenchmarksRequest req = new Gson().fromJson(incoming, GetBenchmarksRequest.class);
        GetBenchmarksResponse response = handler.handleRequest(req, createContext("compute"));
        for(Benchmark ben : response.list) {
        	System.out.println(ben.toString());
        }

        Assert.assertEquals(200, response.httpCode);
    }
    
    @Test
    public void testGetBenchmarkSimple() {
    	String SAMPLE_INPUT_STRING = "{\"filename\": \"QuickSort.java\"}";
        String RESULT = "QuickSort.java";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}