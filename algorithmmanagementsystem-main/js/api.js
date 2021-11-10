/**
 * Access to AWS API Gateway 
 * Remember --> You can get the baseURL by accessing API gateway 
 * and then the dashboard tab
 */

var baseURL = "https://bvc90rtxf1.execute-api.us-east-1.amazonaws.com/beta/"; //get your base URL

var registerURL = baseURL + "createUser"; // POST for users

var addClassificationURL = baseURL + "createClassification"; // POST for classification
var ListClassificationURL = baseURL + "getClassification"; // GET for classification
var deleteClassificationURL = baseURL + ""; // POST for delete classification

var addAlgorithmURL = baseURL + "createAlgorithm"; // POST for algorithms
var ListAlgorithmURL = baseURL + "getAlgorithm"; // POST for algorithms
var deleteAlgorithmURL = baseURL + ""; // DELETE (POST) for algorithms

var addImplementationURL = baseURL + "createImplementation"; // POST for algorithms
var ListImplementationURL = baseURL + "getImplementation"; // POST for algorithms
var deleteImplementationURL = baseURL + ""; // DELETE (POST) for algorithms