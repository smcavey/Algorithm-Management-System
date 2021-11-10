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

	String loginUser(String username, String password) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		UsersDAO dao = new UsersDAO();

		// check if present
		User exist = dao.getUser(username);
		if (exist != null) {
			return dao.loginUser(username, password);
		} else {
			throw new Exception("Failed to login user: user " + username + " does not exist");
		}
	}
	
	@Override
    public LoginResponse handleRequest(LoginRequest req, Context context) {
    	
    	LoginResponse response;
    	try {
    		String token = loginUser(req.username, req.password);
			if (loginUser(req.username, req.password) != null) {
				response = new LoginResponse(req.username, token);
			} else {
				response = new LoginResponse("Unable to login user: " + req.username, "", 400);
			}
		} catch (Exception e) {
			response = new LoginResponse("Unable to login user: " + req.username + "(" + e.getMessage() + ")", "", 400);
		}

		return response;
    }
}