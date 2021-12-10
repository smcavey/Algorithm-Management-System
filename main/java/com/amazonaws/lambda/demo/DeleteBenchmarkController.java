package com.amazonaws.lambda.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.BenchmarksDAO;
import http.DeleteBenchmarkRequest;
import http.DeleteBenchmarkResponse;
import model.Benchmark;

public class DeleteBenchmarkController implements RequestHandler<DeleteBenchmarkRequest,DeleteBenchmarkResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteBenchmarkResponse handleRequest(DeleteBenchmarkRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteBenchmarkResponse response = null;
		logger.log(req.toString());

		BenchmarksDAO dao = new BenchmarksDAO();

		Benchmark benchmark = new Benchmark(req.name);
		try {
			if (dao.deleteBenchmark(benchmark)) {
				response = new DeleteBenchmarkResponse(req.name, 200);
			} else {
				response = new DeleteBenchmarkResponse(req.name, 422, "Unable to delete benchmark.");
			}
		} catch (Exception e) {
			response = new DeleteBenchmarkResponse(req.name, 403, "Unable to delete benchmark: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
