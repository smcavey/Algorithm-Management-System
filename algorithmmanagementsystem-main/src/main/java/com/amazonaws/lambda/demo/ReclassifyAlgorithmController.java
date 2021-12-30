package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import db.AlgorithmsDAO;
import db.UsersDAO;
import http.DeleteImplementationResponse;
import http.ReclassifyAlgorithmRequest;
import http.ReclassifyAlgorithmResponse;

public class ReclassifyAlgorithmController implements RequestHandler<ReclassifyAlgorithmRequest,ReclassifyAlgorithmResponse>{
	
	public LambdaLogger logger = null;
	
	@Override
	public ReclassifyAlgorithmResponse handleRequest(ReclassifyAlgorithmRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to reclassify");

		ReclassifyAlgorithmResponse response = null;
		logger.log(req.toString());

		AlgorithmsDAO dao = new AlgorithmsDAO();

		try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new ReclassifyAlgorithmResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}
			if (dao.reclassifyAlgorithm(req)) {
				response = new ReclassifyAlgorithmResponse(req.algorithm, 201);
			} else {
				response = new ReclassifyAlgorithmResponse(req.algorithm, 400, "Unable to reclassify Algorithm.");
			}
		} catch (Exception e) {
			response = new ReclassifyAlgorithmResponse(req.algorithm, 400, "Unable to reclassify Algorithm: " + req.algorithm + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
