package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import db.ImplementationsDAO;
import http.GetImplementationsRequest;
import http.GetImplementationsResponse;
import model.Implementation;

public class GetImplementationsController implements RequestHandler<GetImplementationsRequest, GetImplementationsResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	public static final String REAL_BUCKET = "implementations";

	public static final String TOP_LEVEL_BUCKET = "galateabucket";
	
	List<Implementation> getImplementations(String algorithmName) throws Exception { 
		logger.log("in implementations");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		String bucket = REAL_BUCKET;
		
		// get file information from db
		ImplementationsDAO dao = new ImplementationsDAO();
		List<Implementation> implementations = dao.getAllImplementations(algorithmName);
		for (Implementation implementation : implementations) {
			implementation.setFilePath("https://" + TOP_LEVEL_BUCKET + ".s3.amazonaws.com/" + bucket + "/" + implementation.fileName);
		}
		
		return implementations;
	}
	
	@Override
	public GetImplementationsResponse handleRequest(GetImplementationsRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all classifications");

		GetImplementationsResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Implementation> list = getImplementations(req.name);
			response = new GetImplementationsResponse(list, 200);
		} catch (Exception e) {
			response = new GetImplementationsResponse(403, e.getMessage());
		}
		
		return response;
	}
}