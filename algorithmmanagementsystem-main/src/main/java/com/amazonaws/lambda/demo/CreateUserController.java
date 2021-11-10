package com.amazonaws.lambda.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import db.UsersDAO;
import model.User;
import http.CreateUserRequest;
import http.CreateUserResponse;

public class CreateUserController implements RequestHandler<CreateUserRequest, CreateUserResponse> {
	
	private static final String REAL_BUCKET = null;

	LambdaLogger logger;

    private AmazonS3 s3 = null;

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

	boolean createSystemUser(String username, String password) throws Exception {
		// TODO: how will this work?

		// if we ever get here, then whole thing was stored
		return true;
	}


    @Override
    public CreateUserResponse handleRequest(CreateUserRequest req, Context context) {
    	
    	CreateUserResponse response;
    	try {
			if (req.system) {
				if (createSystemUser(req.username, req.password)) {
					response = new CreateUserResponse(req.username);
				} else {
					response = new CreateUserResponse(req.username, 400);
				}
			} else {
				if (createUser(req.username, req.password)) {
					response = new CreateUserResponse(req.username);
				} else {
					response = new CreateUserResponse(req.username, 400);
				}
			}
		} catch (Exception e) {
			response = new CreateUserResponse("Unable to create user: " + req.username + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}