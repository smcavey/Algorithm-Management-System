package com.amazonaws.lambda.demo;

import java.util.List;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.ImplementationsDAO;
import http.GetImplementationRequest;
import http.GetImplementationResponse;
import http.LoginRequest;
import http.LoginResponse;
import model.Implementation;

public class GetImplementationController implements RequestHandler<GetImplementationRequest, GetImplementationResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;
	
	public static final String REAL_BUCKET = "implementations";

	public static final String TOP_LEVEL_BUCKET = "galateabucket";


	boolean getImplementation(String fileName) throws Exception { 
		if (logger != null) { logger.log("in getImplementation"); }
		ImplementationsDAO dao = new ImplementationsDAO();

		// check if present
		Implementation exist = dao.getImplementation(fileName);
		if (exist != null) {
			return dao.searchForImplementaiton(fileName);
		} else {
			return false;
		}
	}
	String systemConstants(String fileName) throws Exception {
		logger.log("in implementations");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		String sourceCode;
	    
		String bucket = REAL_BUCKET;
		// retrieve listing of all objects in the designated bucket
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				  .withBucketName(TOP_LEVEL_BUCKET)    // top-level bucket
				  .withPrefix(bucket);            		  // sub-folder declarations here (i.e., a/b/c)
												  
		
		// request the s3 objects in the s3 bucket 'cs3733wpi/constants' -- change based on your bucket name
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects) {
	      String name = os.getKey();
		  logger.log("S3 found:" + name);

	      // If name ends with slash it is the 'constants/' bucket itself so you skip
	      if (name.endsWith("/")) { continue; }
			
	      S3Object obj = s3.getObject(TOP_LEVEL_BUCKET, name);
	    	
	    	try (S3ObjectInputStream implementationStream = obj.getObjectContent()) {
				Scanner sc = new Scanner(implementationStream);
				String val = sc.nextLine();
				sc.close();
				
				// just grab name *after* the slash. Note this is a SYSTEM constant
				int postSlash = name.indexOf('/');
			} catch (Exception e) {
				logger.log("Unable to parse contents of " + name);
			}
	    }
		
		return sourceCode;
	}

	
	@Override
    public GetImplementationResponse handleRequest(GetImplementationRequest req, Context context) {
    	
    	GetImplementationResponse response;
    	try {
			if (getImplementation(req.name)) {
				response = new GetImplementationResponse(req.name, "good file name");
			} else {
				response = new GetImplementationResponse(req.name, "invalid implementation", 400);
			}
		} catch (Exception e) {
			response = new GetImplementationResponse("Unable to get implementation: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}