package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

import db.AlgorithmsDAO;
import db.UsersDAO;
import http.CreateAlgorithmRequest;
import http.CreateAlgorithmResponse;
import model.Algorithm;

public class CreateAlgorithmController implements RequestHandler<CreateAlgorithmRequest, CreateAlgorithmResponse> {

	LambdaLogger logger;

    private AmazonS3 s3 = null;

	boolean createAlgorithm(CreateAlgorithmRequest req) throws Exception { 
		if (logger != null) { logger.log("in createAlgorithm"); }
		AlgorithmsDAO dao = new AlgorithmsDAO();

		// check if present
		Algorithm exist = dao.getAlgorithm(req.name);
		if (exist == null) {
			return dao.createAlgorithm(req);
		} else {
			return false;
		}
	}

    @Override
    public CreateAlgorithmResponse handleRequest(CreateAlgorithmRequest req, Context context) {
    	
    	CreateAlgorithmResponse response;
    	try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new CreateAlgorithmResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}
    		
			if (createAlgorithm(req)) {
				response = new CreateAlgorithmResponse(req.name);
			} else {
				response = new CreateAlgorithmResponse(req.name, 400);
			}
		} catch (Exception e) {
			response = new CreateAlgorithmResponse("Unable to create algorithm: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}