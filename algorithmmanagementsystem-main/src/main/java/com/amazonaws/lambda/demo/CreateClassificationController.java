package com.amazonaws.lambda.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import db.ClassificationsDAO;
import db.UsersDAO;
import http.CreateClassificationRequest;
import http.CreateClassificationResponse;
import model.Classification;

public class CreateClassificationController implements RequestHandler<CreateClassificationRequest, CreateClassificationResponse> {

	private static final String REAL_BUCKET = null;

	LambdaLogger logger;

    private AmazonS3 s3 = null;

    // Test purpose only.
    /*CreateClassificationController(AmazonS3 s3) {
        this.s3 = s3;
    }*/
    
	boolean createClassification(String name, String description) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		// check if present
		Classification exist = dao.getClassification(name);
		if (exist == null) {
			Classification classification = new Classification (name, description);
			return dao.createClassification(classification);
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

			if (createClassification(req.name, req.description)) {
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