import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.GetImplementationController;
import com.google.gson.Gson;

import http.GetImplementationRequest;
import http.GetImplementationResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */

public class GetImplementationControllerTest extends LambdaTest {

    void testInput(String incoming, String outgoing) throws IOException {
    	GetImplementationController handler = new GetImplementationController();
    	GetImplementationRequest req = new Gson().fromJson(incoming, GetImplementationRequest.class);
        GetImplementationResponse response = handler.handleRequest(req, createContext("compute"));

        Assert.assertEquals(outgoing, response.implementation.filePath);
        Assert.assertEquals(200, response.httpCode);
    }

    @Test
    public void testGetImplementationSimple() {
        String SAMPLE_INPUT_STRING = "{\"fileName\": \"HelloWorld.java\"}";
        String RESULT = "LyogVGhpcyBpcyBhIHNpbXBsZSBKYXZhIHByb2dyYW0uCiAgIEZpbGVOYW1lIDogIkhlbGx"
                + "vV29ybGQuamF2YSIuICovCmNsYXNzIEhlbGxvV29ybGQKewogICAgLy8gWW91ciBwcm9ncmFt"
                + "IGJlZ2lucyB3aXRoIGEgY2FsbCB0byBtYWluKCkuCiAgICAvLyBQcmludHMgIkhlbGxvLCBXb"
                + "3JsZCIgdG8gdGhlIHRlcm1pbmFsIHdpbmRvdy4KICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYW"
                + "luKFN0cmluZyBhcmdzW10pCiAgICB7CiAgICAgICAgU3lzdGVtLm91dC5wcmludGxuKCJIZWx"
                + "sbywgV29ybGQiKTsKICAgIH0KfQ==";
        try {
        	testInput(SAMPLE_INPUT_STRING, RESULT);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}
