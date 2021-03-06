/**
 * Access to AWS API Gateway 
 * Remember --> You can get the baseURL by accessing API gateway 
 * and then the dashboard tab
 */

var baseURL = "https://bvc90rtxf1.execute-api.us-east-1.amazonaws.com/beta/"; //get your base URL

var registerURL = baseURL + "createUser"; // POST for users
var loginURL = baseURL + "loginUser"; // POST for users
var ListUsersURL = baseURL + "getUsers"; // POST for users
var ListUserActivityURL = baseURL + "getUserActivity"; // POST for users
var deleteUserURL = baseURL + "deleteUser"; // POST for users

var addClassificationURL = baseURL + "createClassification"; // POST for classification
var ListClassificationURL = baseURL + "getClassification"; // GET for classification
var deleteClassificationURL = baseURL + "deleteClassification"; // POST for delete classification
var mergeClassificationURL = baseURL + "mergeClassification "; // POST for delete classification

var addAlgorithmURL = baseURL + "createAlgorithm"; // POST for algorithms
var ListAlgorithmURL = baseURL + "getAlgorithm"; // POST for algorithms
var deleteAlgorithmURL = baseURL + "deleteAlgorithm "; // DELETE (POST) for algorithms
var reclassifyAlgorithmURL = baseURL + "reclassifyAlgorithm  "; // DELETE (POST) for algorithms

var addImplementationURL = baseURL + "createImplementation"; // POST for Implementatio
var ListImplementationURL = baseURL + "getImplementation"; // POST for Implementatio
var deleteImplementationURL = baseURL + "deleteImplementation"; // DELETE (POST) for implemenetations

var addBenchmarkURL = baseURL + "createBenchmark"; // POST for Benchmark
var ListBenchmarkURL = baseURL + "getBenchmarks"; // POST for Benchmark
var deleteBenchmarkURL = baseURL + "deleteBenchmark"; // DELETE (POST) for Benchmark

var addProblemInstanceURL = baseURL + "createProblemInstance"; // POST for Problem Instance
var ListProblemInstanceURL = baseURL + "getProblemInstances"; // POST for Problem Instance
var deleteProblemInstanceURL = baseURL + "deleteProblemInstance"; // DELETE (POST) for Problem Instance

