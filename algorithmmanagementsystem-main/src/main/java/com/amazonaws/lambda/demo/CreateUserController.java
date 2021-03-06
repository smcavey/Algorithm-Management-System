package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import db.UsersDAO;
import model.User;
import http.CreateUserRequest;
import http.CreateUserResponse;

public class CreateUserController implements RequestHandler<CreateUserRequest, CreateUserResponse> {

	LambdaLogger logger;

	boolean createUser(String username, String password) throws Exception { 
		if (logger != null) { logger.log("in createUser"); }
		UsersDAO dao = new UsersDAO();

		// check if present
		User exist = dao.getUser(username);
		if (exist == null) {
			byte[] newSalt = dao.getSalt();
			User user = new User (username, newSalt, dao.getSecurePassword(password, newSalt));
			user.token = dao.issueToken(username);
			return dao.createUser(user);
		} else {
			return false;
		}
	}

    @Override
    public CreateUserResponse handleRequest(CreateUserRequest req, Context context) {
    	
    	CreateUserResponse response;
    	try {
			if (createUser(req.username, req.password)) {
				response = new CreateUserResponse(req.username);
			} else {
				response = new CreateUserResponse(req.username, 400);
			}
		} catch (Exception e) {
			response = new CreateUserResponse("Unable to create user: " + req.username + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}