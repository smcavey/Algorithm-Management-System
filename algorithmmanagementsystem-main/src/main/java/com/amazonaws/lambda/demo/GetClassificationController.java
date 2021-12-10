package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import db.ClassificationsDAO;
import db.UsersDAO;
import http.GetClassificationRequest;
import http.GetClassificationResponse;
import http.LoginRequest;
import http.LoginResponse;
import model.Classification;
import model.User;

@Deprecated
public class GetClassificationController implements RequestHandler<GetClassificationRequest, GetClassificationResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	boolean getClassification(String name) throws Exception { 
		if (logger != null) { logger.log("in getClassification"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		// check if present
		Classification exist = dao.getClassification(name);
		if (exist != null) {
			return dao.searchForClassification(name);
		} else {
			return false;
		}
	}
	
	@Override
    public GetClassificationResponse handleRequest(GetClassificationRequest req, Context context) {
    	
    	GetClassificationResponse response;
    	try {
			if (getClassification(req.name)) {
				response = new GetClassificationResponse(req.name, "good name");
			} else {
				response = new GetClassificationResponse(req.name, "invalid classification", 400);
			}
		} catch (Exception e) {
			response = new GetClassificationResponse("Unable to get classification: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}