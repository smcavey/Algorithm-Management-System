import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.lambda.demo.GetClassificationsController;

import http.GetClassificationsResponse;
import model.Classification;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetClassificationsControllerTest extends LambdaTest {
	
    @Test
    public void testGetList() throws IOException {
    	GetClassificationsController controller = new GetClassificationsController();

        GetClassificationsResponse resp = controller.handleRequest(null, createContext("list"));
        
        boolean hasTestClassificationName2 = false;
        for (Classification c : resp.list) {
        	System.out.println("found classification " + c);
        	if (c.name.equals("testClassificationName2")) { hasTestClassificationName2 = true; }
        }
        Assert.assertTrue("testClassificationName2 needs to exist in the classifications table for this test case to work.", hasTestClassificationName2);
        Assert.assertEquals(200, resp.statusCode);
    }

}
