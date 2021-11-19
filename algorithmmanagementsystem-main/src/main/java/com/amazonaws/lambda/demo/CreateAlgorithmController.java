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

import db.AlgorithmsDAO;
import db.DatabaseUtil;
import db.UsersDAO;
import http.CreateAlgorithmRequest;
import http.CreateAlgorithmResponse;
import http.CreateClassificationResponse;
import model.Algorithm;

public class CreateAlgorithmController implements RequestHandler<CreateAlgorithmRequest, CreateAlgorithmResponse> {

	private static final String REAL_BUCKET = null;

	LambdaLogger logger;

    private AmazonS3 s3 = null;
    
	boolean createAlgorithm(String name, String description, String classification) throws Exception { 
		if (logger != null) { logger.log("in createAlgorithm"); }
		AlgorithmsDAO dao = new AlgorithmsDAO();

		// check if present
		Algorithm exist = dao.getAlgorithm(name);
		if (exist == null) {
			Algorithm algorithm = new Algorithm (name, description, classification);
			return dao.createAlgorithm(algorithm);
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
    		
			if (createAlgorithm(req.name, req.description, req.classification)) {
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