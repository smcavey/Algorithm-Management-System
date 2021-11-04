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

import db.ClassificationsDAO;
import http.GetClassificationsResponse;
import model.Classification;

/**
 * Eliminated need to work with JSON
 */
public class GetClassificationsController implements RequestHandler<Object,GetClassificationsResponse> {
	
	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	List<Classification> getClassifications() throws Exception { 
		if (logger != null) { logger.log("in getClassifications"); }
		ClassificationsDAO dao = new ClassificationsDAO();

		List<Classification> allClassifications = dao.getAllClassifications();
		if(!allClassifications.isEmpty()) {
			return allClassifications;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public GetClassificationsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all classifications");

		GetClassificationsResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Classification> list = getClassifications();
			response = new GetClassificationsResponse(list, 200);
		} catch (Exception e) {
			response = new GetClassificationsResponse(403, e.getMessage());
		}
		
		return response;
	}
}
