package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.AlgorithmsDAO;
import http.GetAlgorithmsRequest;
import http.GetAlgorithmsResponse;
import model.Algorithm;

public class GetAlgorithmsController implements RequestHandler<GetAlgorithmsRequest,GetAlgorithmsResponse> {
	
	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	List<Algorithm> getAlgorithms(String name) throws Exception { 
		if (logger != null) { logger.log("in getAlgorithms"); }
		AlgorithmsDAO dao = new AlgorithmsDAO();

		List<Algorithm> allAlgorithms = dao.getAllAlgorithms(name);
		if(!allAlgorithms.isEmpty()) {
			return allAlgorithms;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public GetAlgorithmsResponse handleRequest(GetAlgorithmsRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all algorithms");

		GetAlgorithmsResponse response;
		try {
			List<Algorithm> list = getAlgorithms(req.name);
			response = new GetAlgorithmsResponse("good classification", list, 200);
		} catch (Exception e) {
			response = new GetAlgorithmsResponse("invalid classification", 403, e.getMessage());
		}
		
		return response;
	}
}