import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.CreateClassificationController;
import com.amazonaws.lambda.demo.DeleteClassificationController;

import http.CreateClassificationRequest;
import http.CreateClassificationResponse;
import http.DeleteClassificationRequest;
import http.DeleteClassificationResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteClassificationControllerTest extends LambdaTest {

    @Test
    public void testCreateAndDeleteConstant() {
    	// create classification
        CreateClassificationRequest ccr = new CreateClassificationRequest("testDeleteClassification");
        
        CreateClassificationResponse resp = new CreateClassificationController().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("testDeleteClassification", resp.response);
        
        // now delete
        DeleteClassificationRequest dcr = new DeleteClassificationRequest("testDeleteClassification");
        DeleteClassificationResponse d_resp = new DeleteClassificationController().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("testDeleteClassification", d_resp.name);
        
        // try again and this should fail
        d_resp = new DeleteClassificationController().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.httpCode);
    }
   
}
