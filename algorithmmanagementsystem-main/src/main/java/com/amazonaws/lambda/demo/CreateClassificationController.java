package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

import db.ClassificationsDAO;
import db.UsersDAO;
import http.CreateClassificationRequest;
import http.CreateClassificationResponse;
import model.Classification;

public class CreateClassificationController implements RequestHandler<CreateClassificationRequest, CreateClassificationResponse> {

	LambdaLogger logger;

    private AmazonS3 s3 = null;
    
	boolean createClassification(CreateClassificationRequest req) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		// check if present
		Classification exist = dao.getClassification(req.name);
		if (exist == null) {
			return dao.createClassification(req);
		} else {
			return false;
		}
	}

    @Override
    public CreateClassificationResponse handleRequest(CreateClassificationRequest req, Context context) {
    	CreateClassificationResponse response;
    	try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new CreateClassificationResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}

			if (createClassification(req)) {
				response = new CreateClassificationResponse(req.name);
			} else {
				response = new CreateClassificationResponse(req.name, 400);
			}
		} catch (Exception e) {
			response = new CreateClassificationResponse("Unable to create classification: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}