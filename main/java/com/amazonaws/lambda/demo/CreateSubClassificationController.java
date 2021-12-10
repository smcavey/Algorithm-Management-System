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
import http.CreateSubClassificationRequest;
import http.CreateSubClassificationResponse;
import model.Classification;

public class CreateSubClassificationController implements RequestHandler<CreateSubClassificationRequest, CreateSubClassificationResponse> {

	LambdaLogger logger;

    private AmazonS3 s3 = null;
    
	boolean createSubClassification(CreateSubClassificationRequest req) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		// check if present
		Classification exist = dao.getClassification(req.name);
		if (exist == null) {
			return dao.createSubClassification(req);
		} else {
			return false;
		}
	}

    @Override
    public CreateSubClassificationResponse handleRequest(CreateSubClassificationRequest req, Context context) {
    	CreateSubClassificationResponse response;
    	try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new CreateSubClassificationResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}

			if (createSubClassification(req)) {
				response = new CreateSubClassificationResponse(req.name);
			} else {
				response = new CreateSubClassificationResponse(req.name, 400);
			}
		} catch (Exception e) {
			response = new CreateSubClassificationResponse("Unable to create classification: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}