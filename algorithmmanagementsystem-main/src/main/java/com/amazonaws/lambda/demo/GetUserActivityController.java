package com.amazonaws.lambda.demo;

import java.util.Collections;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.UserActivityDAO;
import http.GetUserActivityRequest;
import http.GetUserActivityResponse;
import model.UserActivity;

public class GetUserActivityController implements RequestHandler<GetUserActivityRequest, GetUserActivityResponse> {

	LambdaLogger logger;

	List<UserActivity> getUserActivity(String username) throws Exception { 
		if (logger != null) { logger.log("in getUserActivity"); }
		UserActivityDAO dao = new UserActivityDAO();

		List<UserActivity> allUserActivity = dao.getUserActivity(username);
		if(!allUserActivity.isEmpty()) {
			return allUserActivity;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public GetUserActivityResponse handleRequest(GetUserActivityRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all user activity");

		GetUserActivityResponse response;
		try {
			// get user activity for a specific user
			List<UserActivity> list = getUserActivity(req.name);
			response = new GetUserActivityResponse("Success", list, 200);
		} catch (Exception e) {
			response = new GetUserActivityResponse("Error", 403, e.getMessage());
		}
		
		return response;
	}
}