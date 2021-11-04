package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;

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
import db.DatabaseUtil;
import db.UsersDAO;
import http.CreateClassificationRequest;
import http.CreateClassificationResponse;
import http.CreateUserRequest;
import http.CreateUserResponse;
import model.Classification;
import model.User;

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
    
	boolean createSystemClassification(String name, String description) throws Exception {
		if (logger != null) { logger.log("in createSystemClassification"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = REAL_BUCKET;
		
		byte[] contents = ("" + description).getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest("calculator-aws-example", bucket + name, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}

    @Override
    public CreateClassificationResponse handleRequest(CreateClassificationRequest req, Context context) {
    	
    	CreateClassificationResponse response;
    	try {
			if (req.system) {
				if (createSystemClassification(req.name, req.description)) {
					response = new CreateClassificationResponse(req.name);
				} else {
					response = new CreateClassificationResponse(req.name, 400);
				}
			} else {
				if (createClassification(req.name, req.description)) {
					response = new CreateClassificationResponse(req.name);
				} else {
					response = new CreateClassificationResponse(req.name, 400);
				}
			}
		} catch (Exception e) {
			response = new CreateClassificationResponse("Unable to create classification: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}