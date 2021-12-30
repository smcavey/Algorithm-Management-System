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
import db.ClassificationsDAO;
import http.MergeClassificationRequest;
import http.MergeClassificationResponse;
import http.ReclassifyAlgorithmRequest;
import http.ReclassifyAlgorithmResponse;

public class MergeClassificationController implements RequestHandler<MergeClassificationRequest, MergeClassificationResponse> {
	
	public LambdaLogger logger = null;
	
	@Override
	public MergeClassificationResponse handleRequest(MergeClassificationRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to merge classifications");

		MergeClassificationResponse response = null;
		logger.log(req.toString());

		ClassificationsDAO dao = new ClassificationsDAO();

		try {
			if (dao.mergeClassifications(req)) {
				response = new MergeClassificationResponse(req.classificationNew, 201);
			} else {
				response = new MergeClassificationResponse(req.classificationNew, 400, "Unable to merge classifications.");
			}
		} catch (Exception e) {
			response = new MergeClassificationResponse(req.classificationNew, 400, "Unable to merge classifications: " + req.classificationNew + "(" + e.getMessage() + ")");
		}

		return response;
	}
}