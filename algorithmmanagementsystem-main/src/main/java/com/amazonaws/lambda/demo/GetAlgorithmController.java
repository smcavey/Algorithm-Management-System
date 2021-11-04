package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import db.AlgorithmsDAO;
import http.GetAlgorithmRequest;
import http.GetAlgorithmResponse;
import model.Algorithm;

public class GetAlgorithmController implements RequestHandler<GetAlgorithmRequest, GetAlgorithmResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	boolean getAlgorithm(String name) throws Exception { 
		if (logger != null) { logger.log("in getAlgorithm"); }
		AlgorithmsDAO dao = new AlgorithmsDAO();

		// check if present
		Algorithm exist = dao.getAlgorithm(name);
		if (exist != null) {
			return dao.searchForAlgorithm(name);
		} else {
			return false;
		}
	}
	
	@Override
    public GetAlgorithmResponse handleRequest(GetAlgorithmRequest req, Context context) {
    	
    	GetAlgorithmResponse response;
    	try {
			if (getAlgorithm(req.name)) {
				response = new GetAlgorithmResponse(req.name, "good name");
			} else {
				response = new GetAlgorithmResponse(req.name, "invalid algorithm", 400);
			}
		} catch (Exception e) {
			response = new GetAlgorithmResponse("Unable to get algorithm: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}