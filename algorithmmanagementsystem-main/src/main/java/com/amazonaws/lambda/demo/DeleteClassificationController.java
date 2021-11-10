package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.ClassificationsDAO;
import http.DeleteClassificationRequest;
import http.DeleteClassificationResponse;
import model.Classification;

/**
 * No more JSON parsing
 */
public class DeleteClassificationController implements RequestHandler<DeleteClassificationRequest,DeleteClassificationResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteClassificationResponse handleRequest(DeleteClassificationRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteClassificationResponse response = null;
		logger.log(req.toString());

		ClassificationsDAO dao = new ClassificationsDAO();
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Classification classification = new Classification(req.name, "");
		try {
			if (dao.deleteClassification(classification)) {
				response = new DeleteClassificationResponse(req.name, 200);
			} else {
				response = new DeleteClassificationResponse(req.name, 422, "Unable to delete classification.");
			}
		} catch (Exception e) {
			response = new DeleteClassificationResponse(req.name, 403, "Unable to delete classification: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
