package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;

import db.ClassificationsDAO;
import http.GetSubClassificationsRequest;
import http.GetSubClassificationsResponse;
import model.Classification;

public class GetSubClassificationsController implements RequestHandler<GetSubClassificationsRequest,GetSubClassificationsResponse> {
	
	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	List<Classification> getSubClassifications(String name) throws Exception { 
		if (logger != null) { logger.log("in getSubClassifications"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		List<Classification> allSubClassifications = dao.getAllSubClassifications(name);
		if(!allSubClassifications.isEmpty()) {
			return allSubClassifications;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public GetSubClassificationsResponse handleRequest(GetSubClassificationsRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all classifications");

		GetSubClassificationsResponse response;
		try {
			List<Classification> list = getSubClassifications(req.name);
			response = new GetSubClassificationsResponse("good classification", 200, list);
		} catch (Exception e) {
			response = new GetSubClassificationsResponse("invalid classification", 403, e.getMessage());
		}
		
		return response;
	}
}