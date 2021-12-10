package com.amazonaws.lambda.demo;

import java.util.Collections;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

import db.UserActivityDAO;
import http.GetUserActivityRequest;
import http.GetUserActivityResponse;
import model.UserActivity;

public class GetUserActivityController implements RequestHandler<GetUserActivityRequest, GetUserActivityResponse> {

	private AmazonS3 s3 = null;

	LambdaLogger logger;

	List<UserActivity> getUserActivity() throws Exception { 
		if (logger != null) { logger.log("in getUserActivity"); }
		UserActivityDAO dao = new UserActivityDAO();

		List<UserActivity> allUserActivity = dao.getAllUserActivity();
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
			// get all user activity history
			List<UserActivity> list = getUserActivity();
			response = new GetUserActivityResponse("Success", list, 200);
		} catch (Exception e) {
			response = new GetUserActivityResponse("Error", 403, e.getMessage());
		}
		
		return response;
	}
}