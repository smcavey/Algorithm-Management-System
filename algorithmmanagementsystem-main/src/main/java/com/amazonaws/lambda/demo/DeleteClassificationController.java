package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.ClassificationsDAO;
import db.UsersDAO;
import http.DeleteClassificationRequest;
import http.DeleteClassificationResponse;

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

		try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new DeleteClassificationResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}
			
			if (dao.deleteClassification(req)) {
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
