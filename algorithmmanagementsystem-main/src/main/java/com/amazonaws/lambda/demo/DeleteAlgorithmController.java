package com.amazonaws.lambda.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.BenchmarksDAO;
import db.ImplementationsDAO;
import db.AlgorithmsDAO;
import http.DeleteAlgorithmRequest;
import http.DeleteAlgorithmResponse;
import model.Benchmark;
import model.Implementation;

public class DeleteAlgorithmController implements RequestHandler<DeleteAlgorithmRequest,DeleteAlgorithmResponse> {

	public LambdaLogger logger = null;

	@Override
	public DeleteAlgorithmResponse handleRequest(DeleteAlgorithmRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteAlgorithmResponse response = null;
		logger.log(req.toString());

		AlgorithmsDAO dao = new AlgorithmsDAO();
		ImplementationsDAO idao = new ImplementationsDAO();
		BenchmarksDAO bdao = new BenchmarksDAO();

		try {
			if (dao.deleteAlgorithm(req)) {
		        List<Implementation> allImplementations = idao.getAllImplementations(req.name);
		        if(!allImplementations.isEmpty()) {
		        	for(Implementation imp : allImplementations) {
		        		if(idao.deleteImplementationGivenAlgorithm(imp)) {
				        	List<Benchmark> allBenchmarks = bdao.getAllBenchmarks(imp.fileName);
				        	if(!allBenchmarks.isEmpty()) {
				        		for(Benchmark ben : allBenchmarks) {
				        			bdao.deleteBenchmarkGivenImplementation(ben);
				        		}
				        	}
		        		}
		        	}
		        }
				response = new DeleteAlgorithmResponse(req.name, 200);
			} else {
				response = new DeleteAlgorithmResponse(req.name, 422, "Unable to delete Algorithm.");
			}
		} catch (Exception e) {
			response = new DeleteAlgorithmResponse(req.name, 403, "Unable to delete Algorithm: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}