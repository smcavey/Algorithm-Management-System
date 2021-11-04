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
import model.Algorithm;

public class CreateAlgorithmController implements RequestHandler<CreateAlgorithmRequest, CreateAlgorithmResponse> {

	private static final String REAL_BUCKET = null;

	LambdaLogger logger;

    private AmazonS3 s3 = null;

    // Test purpose only.
    /*CreateClassificationController(AmazonS3 s3) {
        this.s3 = s3;
    }*/
    
	boolean createAlgorithm(String name, String description, String classification) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
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
    
	boolean createSystemAlgorithm(String name, String description, String classification) throws Exception {
		if (logger != null) { logger.log("in createSystemAlgorithm"); }
		
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
    public CreateAlgorithmResponse handleRequest(CreateAlgorithmRequest req, Context context) {
    	
    	CreateAlgorithmResponse response;
    	try {
			if (req.system) {
				if (createSystemAlgorithm(req.name, req.description, req.classification)) {
					response = new CreateAlgorithmResponse(req.name);
				} else {
					response = new CreateAlgorithmResponse(req.name, 400);
				}
			} else {
				if (createAlgorithm(req.name, req.description, req.classification)) {
					response = new CreateAlgorithmResponse(req.name);
				} else {
					response = new CreateAlgorithmResponse(req.name, 400);
				}
			}
		} catch (Exception e) {
			response = new CreateAlgorithmResponse("Unable to create algorithm: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}