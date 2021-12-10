package com.amazonaws.lambda.demo;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import db.ProblemInstancesDAO;
import db.UsersDAO;
import http.CreateProblemInstanceRequest;
import http.CreateProblemInstanceResponse;
import model.ProblemInstance;

public class CreateProblemInstanceController implements RequestHandler<CreateProblemInstanceRequest,CreateProblemInstanceResponse> {

	LambdaLogger logger;

	// To access S3 storage
	private AmazonS3 s3 = null;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "probleminstances/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createProblemInstance(CreateProblemInstanceRequest req) throws Exception { 
		if (logger != null) { logger.log("in createProblemInstance"); }
		ProblemInstancesDAO dao = new ProblemInstancesDAO();
		
		// check if present
		ProblemInstance exist = dao.getProblemInstance(req.filename);
		if (exist == null) {
			return dao.createProblemInstance(req);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean createSystemProblemInstance(String fileContent, String filename) throws Exception {
		if (logger != null) { logger.log("in createSystemProblemInstance"); }
		
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
		PutObjectResult res = s3.putObject(new PutObjectRequest("galateabucket", bucket + filename, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		// if we ever get here, then whole thing was stored
		return true;
	}
	
	@Override 
	public CreateProblemInstanceResponse handleRequest(CreateProblemInstanceRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());
		CreateProblemInstanceResponse response;
		try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new CreateProblemInstanceResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}
			if (createSystemProblemInstance(req.fileContent, req.filename)) {
				response = new CreateProblemInstanceResponse(req.filename);
			} else {
				response = new CreateProblemInstanceResponse(req.filename, 422);
			}
			if (createProblemInstance(req)) {
				response = new CreateProblemInstanceResponse(req.filename);
			} else {
				response = new CreateProblemInstanceResponse(req.filename, 422);
			}
		} catch (Exception e) {
			response = new CreateProblemInstanceResponse("Unable to create ProblemInstance: " + req.filename + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
}