package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import db.ProblemInstancesDAO;
import http.GetProblemInstancesRequest;
import http.GetProblemInstancesResponse;
import model.ProblemInstance;

public class GetProblemInstancesController implements RequestHandler<GetProblemInstancesRequest, GetProblemInstancesResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	public static final String REAL_BUCKET = "probleminstances";

	public static final String TOP_LEVEL_BUCKET = "galateabucket";
	
	List<ProblemInstance> getProblemInstances(String algorithmName) throws Exception { 
		logger.log("in problem instances");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		String bucket = REAL_BUCKET;
		
		// get file information from db
		ProblemInstancesDAO dao = new ProblemInstancesDAO();
		List<ProblemInstance> probleminstances = dao.getAllProblemInstances(algorithmName);
		for (ProblemInstance probleminstance : probleminstances) {
			probleminstance.setFilePath("https://" + TOP_LEVEL_BUCKET + ".s3.amazonaws.com/" + bucket + "/" + probleminstance.filename);
		}
		
		return probleminstances;
	}
	
	@Override
	public GetProblemInstancesResponse handleRequest(GetProblemInstancesRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all classifications");

		GetProblemInstancesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<ProblemInstance> list = getProblemInstances(req.name);
			response = new GetProblemInstancesResponse(list, 200);
		} catch (Exception e) {
			response = new GetProblemInstancesResponse(403, e.getMessage());
		}
		
		return response;
	}
}