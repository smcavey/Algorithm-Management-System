package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.Base64;
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
import com.amazonaws.util.IOUtils;

import db.ImplementationsDAO;
import http.GetImplementationRequest;
import http.GetImplementationResponse;
import model.Implementation;

@Deprecated
public class GetImplementationController implements RequestHandler<GetImplementationRequest, GetImplementationResponse> {

	// To access S3 storage
	private AmazonS3 s3 = null;
	
	LambdaLogger logger;
	
	public static final String REAL_BUCKET = "implementations";

	public static final String TOP_LEVEL_BUCKET = "galateabucket";

	Implementation getImplementation(String fileName) throws Exception {
		logger.log("in implementations");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		// get file description from db
		ImplementationsDAO dao = new ImplementationsDAO();
		Implementation implementation = dao.getImplementation(fileName);
		implementation.setFilePath(""); // FIXME: this will be fileContent

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

		    // If name ends with fileName we've found the right file
		    if (name.endsWith(fileName)) { 
			    S3Object obj = s3.getObject(TOP_LEVEL_BUCKET, name);
	
		    	try (S3ObjectInputStream implementationStream = obj.getObjectContent()) {
					byte[] contents = IOUtils.toByteArray(implementationStream);
					String fileContent = Base64.getEncoder().encodeToString(contents);
					
					// set the content
					implementation.setFilePath(fileContent); // FIXME: this will be fileContent
				} catch (Exception e) {
					logger.log("Unable to parse contents of " + name);
				}
		    }
	    }

		return implementation;
	}
	
	@Override
    public GetImplementationResponse handleRequest(GetImplementationRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

    	GetImplementationResponse response;
    	try {
    		Implementation implementation = getImplementation(req.fileName);
			if (implementation != null) {
				response = new GetImplementationResponse(implementation, 200);
			} else {
				response = new GetImplementationResponse(400, "invalid implementation: " + req.fileName);
			}
		} catch (Exception e) {
			response = new GetImplementationResponse(400, "Unable to get implementation: " + req.fileName + "(" + e.getMessage() + ")");
		}

		return response;
    }
}