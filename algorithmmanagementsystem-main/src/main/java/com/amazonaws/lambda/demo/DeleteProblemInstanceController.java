package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.ProblemInstancesDAO;
import db.UsersDAO;
import http.DeleteClassificationResponse;
import http.DeleteProblemInstanceRequest;
import http.DeleteProblemInstanceResponse;
import model.ProblemInstance;

public class DeleteProblemInstanceController implements RequestHandler<DeleteProblemInstanceRequest,DeleteProblemInstanceResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteProblemInstanceResponse handleRequest(DeleteProblemInstanceRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteProblemInstanceResponse response = null;
		logger.log(req.toString());

		ProblemInstancesDAO dao = new ProblemInstancesDAO();

		ProblemInstance pi = new ProblemInstance(req.name);
		try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();
    		if (!db.validToken(req.token)) {
    			return new DeleteProblemInstanceResponse("The token passed in (" + req.token + ") is not valid", 400);
    		}
			if (dao.deleteProblemInstance(req)) {
				response = new DeleteProblemInstanceResponse(req.name, 200);
			} else {
				response = new DeleteProblemInstanceResponse(req.name, 422, "Unable to delete problem instance.");
			}
		} catch (Exception e) {
			response = new DeleteProblemInstanceResponse(req.name, 403, "Unable to delete problem instance: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}