package com.amazonaws.lambda.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.BenchmarksDAO;
import http.GetBenchmarksRequest;
import http.GetBenchmarksResponse;
import model.Benchmark;

public class GetBenchmarksController implements RequestHandler<GetBenchmarksRequest,GetBenchmarksResponse> {
	
	private AmazonS3 s3 = null;
	
	LambdaLogger logger;

	List<Benchmark> getBenchmarks(String name) throws Exception { 
		if (logger != null) { logger.log("in getAlgorithms"); }
		BenchmarksDAO dao = new BenchmarksDAO();

		List<Benchmark> allBenchmarks = dao.getAllBenchmarks(name);
		if(!allBenchmarks.isEmpty()) {
			return allBenchmarks;
		}
		else {
			return Collections.EMPTY_LIST;
		}
	}
	
	@Override
	public GetBenchmarksResponse handleRequest(GetBenchmarksRequest req, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all Benchmarks");

		GetBenchmarksResponse response;
		try {
			List<Benchmark> list = getBenchmarks(req.name);
			response = new GetBenchmarksResponse("good implementation", list, 200);
		} catch (Exception e) {
			response = new GetBenchmarksResponse("invalid implementation", 403, e.getMessage());
		}
		
		return response;
	}
}