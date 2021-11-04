package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;

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

import db.ImplementationsDAO;
import http.CreateImplementationRequest;
import http.CreateImplementationResponse;
import model.Implementation;

public class CreateImplementationController implements RequestHandler<CreateImplementationRequest,CreateImplementationResponse> {

	LambdaLogger logger;
	
	// To access S3 storage
	private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "implementations/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createImplementation(String fileContent, String fileName, String description) throws Exception { 
		if (logger != null) { logger.log("in createImplementation"); }
		ImplementationsDAO dao = new ImplementationsDAO();
		
		// check if present
		Implementation exist = dao.getImplementation(fileName);
		Implementation implementation = new Implementation (fileContent, fileName, description);
		if (exist == null) {
			return dao.createImplementation(implementation);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean createSystemImplementation(String fileContent, String fileName, String description) throws Exception {
		if (logger != null) { logger.log("in createSystemImplementation"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = REAL_BUCKET;
		
		byte[] contents = Base64.getDecoder().decode(fileContent);
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest("galateabucket", bucket + fileName, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
	@Override 
	public CreateImplementationResponse handleRequest(CreateImplementationRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateImplementationResponse response;
		try {
				if (createSystemImplementation(req.fileContent, req.fileName, req.description )) {
					response = new CreateImplementationResponse(req.fileName);
				} else {
					response = new CreateImplementationResponse(req.fileName, 422);
				}
				if (createImplementation(req.fileContent, req.fileName, req.description)) {
					response = new CreateImplementationResponse(req.fileName);
				} else {
					response = new CreateImplementationResponse(req.fileName, 422);
				}
		} catch (Exception e) {
			response = new CreateImplementationResponse("Unable to create implementation: " + req.fileName + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}
