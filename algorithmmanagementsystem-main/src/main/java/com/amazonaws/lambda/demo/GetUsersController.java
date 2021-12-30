package com.amazonaws.lambda.demo;

import java.util.Collections;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.UsersDAO;
import http.GetUsersRequest;
import http.GetUsersResponse;
import model.User;

public class GetUsersController implements RequestHandler<GetUsersRequest, GetUsersResponse> {

	LambdaLogger logger;

	List<User> getUsers() throws Exception { 
		if (logger != null) { logger.log("in getUserActivity"); }
		UsersDAO dao = new UsersDAO();

		List<User> allUsers = dao.getUsers();
		if(!allUsers.isEmpty()) {
			return allUsers;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public GetUsersResponse handleRequest(GetUsersRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all user activity");

		GetUsersResponse response;
		try {
			// get user activity for a specific user
			List<User> list = getUsers();
			response = new GetUsersResponse("Success", list, 200);
		} catch (Exception e) {
			response = new GetUsersResponse("Error", 403, e.getMessage());
		}
		
		return response;
	}
}