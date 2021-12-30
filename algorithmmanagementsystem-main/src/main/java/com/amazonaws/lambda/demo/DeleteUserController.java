package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.UsersDAO;
import http.DeleteUserRequest;
import http.DeleteUserResponse;

/**
 * No more JSON parsing
 */
public class DeleteUserController implements RequestHandler<DeleteUserRequest, DeleteUserResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteUserResponse handleRequest(DeleteUserRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteUserResponse response = null;
		logger.log(req.toString());

		try {
    		// check for valid token
    		UsersDAO db = new UsersDAO();		
			if (db.deleteUser(req)) {
				response = new DeleteUserResponse(req.username, 200);
			} else {
				response = new DeleteUserResponse(req.username, 422, "Unable to delete user.");
			}
		} catch (Exception e) {
			response = new DeleteUserResponse(req.username, 403, "Unable to delete user: " + req.username + "(" + e.getMessage() + ")");
		}

		return response;
	}
}