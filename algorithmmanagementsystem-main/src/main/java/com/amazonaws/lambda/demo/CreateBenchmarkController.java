package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;

import db.BenchmarksDAO;
import http.CreateBenchmarkRequest;
import http.CreateBenchmarkResponse;
import model.Benchmark;

public class CreateBenchmarkController implements RequestHandler<CreateBenchmarkRequest, CreateBenchmarkResponse> {

	LambdaLogger logger;

    private AmazonS3 s3 = null;
    
	boolean createBenchmark(String name, String l1cache, String l2cache, String l3cache, String ram, int threads, int cores, String manufacturer, String implementation) throws Exception { 
		if (logger != null) { logger.log("in createBenchmark"); }
		BenchmarksDAO dao = new BenchmarksDAO();

		// check if present
		Benchmark exist = dao.getBenchmark(name);
		if (exist == null) {
			Benchmark benchmark = new Benchmark (name, l1cache, l2cache, l3cache, ram, threads, cores, manufacturer, implementation);
			return dao.createBenchmark(benchmark);
		} else {
			return false;
		}
	}

    @Override
    public CreateBenchmarkResponse handleRequest(CreateBenchmarkRequest req, Context context) {
    	
    	CreateBenchmarkResponse response;
    	try {
			if (createBenchmark(req.name, req.l1cache, req.l2cache, req.l3cache, req.ram, req.threads, req.cores, req.manufacturer, req.implementation)) {
				response = new CreateBenchmarkResponse(req.name);
			} else {
				response = new CreateBenchmarkResponse(req.name, 400);
			}
		} catch (Exception e) {
			response = new CreateBenchmarkResponse("Unable to create benchmark: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
}