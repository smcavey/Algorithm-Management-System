import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.CreateImplementationController;
import com.google.gson.Gson;

import http.CreateImplementationRequest;
import http.CreateImplementationResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */

public class CreateImplementationControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	CreateImplementationController handler = new CreateImplementationController();
    	CreateImplementationRequest req = new Gson().fromJson(incoming, CreateImplementationRequest.class);
        CreateImplementationResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.response);
        Assert.assertEquals(201, response.httpCode);
    }
    
    @Test
    public void testCreateImplementationSimple() {
        String SAMPLE_INPUT_STRING = "{\"filename\": \"garbage.java\", \"algorithm\": \"GCD\", \"description\": \"test description\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\", \"token\" : \"mtdll4k2cgflr3et82fgekdn3q\"}";
        String RESULT = "garbage.java";
        
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}

/*public class CreateImplementationControllerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	CreateImplementationController handler = new CreateImplementationController();
    	CreateImplementationRequest req = new Gson().fromJson(incoming, CreateImplementationRequest.class);
       
        CreateImplementationResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming, int failureCode) throws IOException {
    	CreateImplementationController handler = new CreateImplementationController();
    	CreateImplementationRequest req = new Gson().fromJson(incoming, CreateImplementationRequest.class);

    	CreateImplementationResponse resp = handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(failureCode, resp.httpCode);
    }*/

   
//    // NOTE: this proliferates large number of constants! Be mindful
/*    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateConstantRequest ccr = new CreateConstantRequest(var, 18.293);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }*/
    
    
/*    @Test
    public void testGarbageInput() {
    	String SAMPLE_INPUT_STRING = "{\"sdsd\": \"e3\", \"hgfgdfgdfg\": \"this is not a number\"}";
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }*/
    
    // overwrites into it
/*    @Test
    public void testCreateSystemConstant() {
    	// create constant
        String SAMPLE_INPUT_STRING = "{\"fileName\": \"HelloWorld.java\","
                + " \"fileContent\": "
                + "\"LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==\"}";
        CreateImplementationRequest cir = new CreateImplementationRequest(SAMPLE_INPUT_STRING, "test.java", "sample implementation");
        
        CreateImplementationResponse resp = new CreateImplementationController().handleRequest(cir, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }*/
/*    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAway" + rndNum;
    	
    	CreateConstantRequest ccr = new CreateConstantRequest(var, 18.293);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

}*/
