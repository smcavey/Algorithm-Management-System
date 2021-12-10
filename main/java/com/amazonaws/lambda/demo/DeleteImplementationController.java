package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.BenchmarksDAO;
import db.ImplementationsDAO;
import http.DeleteImplementationRequest;
import http.DeleteImplementationResponse;
import model.Benchmark;

public class DeleteImplementationController implements RequestHandler<DeleteImplementationRequest,DeleteImplementationResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteImplementationResponse handleRequest(DeleteImplementationRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteImplementationResponse response = null;
		logger.log(req.toString());

		ImplementationsDAO dao = new ImplementationsDAO();
		BenchmarksDAO bdao = new BenchmarksDAO();

		try {
			if (dao.deleteImplementation(req)) {
		        List<Benchmark> allBenchmarks = bdao.getAllBenchmarks(req.filename);
		        if(!allBenchmarks.isEmpty()) {
		        		for(Benchmark ben : allBenchmarks) {
		        		//delete benchmarks
		        		bdao.deleteBenchmarkGivenImplementation(ben);
		        	}
		        }
				response = new DeleteImplementationResponse(req.filename, 200);
			} else {
				response = new DeleteImplementationResponse(req.filename, 422, "Unable to delete implementation.");
			}
		} catch (Exception e) {
			response = new DeleteImplementationResponse(req.filename, 403, "Unable to delete implementation: " + req.filename + "(" + e.getMessage() + ")");
		}

		return response;
	}
}