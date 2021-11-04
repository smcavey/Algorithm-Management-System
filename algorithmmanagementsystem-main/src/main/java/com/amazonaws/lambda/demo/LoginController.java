package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import db.UsersDAO;
import http.LoginRequest;
import http.LoginResponse;
import model.User;

public class LoginController implements RequestHandler<LoginRequest, LoginResponse> {

	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	boolean loginUser(String username, String password) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		UsersDAO dao = new UsersDAO();

		// check if present
		User exist = dao.getUser(username);
		if (exist != null) {
			return dao.loginUser(username, password);
		} else {
			return false;
		}
	}
	
	@Override
    public LoginResponse handleRequest(LoginRequest req, Context context) {
    	
    	LoginResponse response;
    	try {
			if (loginUser(req.username, req.password)) {
				response = new LoginResponse(req.username, "goodtoken");
			} else {
				response = new LoginResponse(req.username, "invalidtoken", 400);
			}
		} catch (Exception e) {
			response = new LoginResponse("Unable to login user: " + req.username + "(" + e.getMessage() + ")", "invalidtoken", 400);
		}

		return response;
    }
}