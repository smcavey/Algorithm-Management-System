/**
 * Access to AWS API Gateway 
 * Remember --> You can get the baseURL by accessing API gateway 
 * and then the dashboard tab
 */

var baseURL = "https://bvc90rtxf1.execute-api.us-east-1.amazonaws.com/beta/"; //get your base URL

var registerURL = baseURL + "createUser"; // POST for users
var addAlgorithm = baseURL + ""; // POST for algorithms
var removeAlgorithm = baseURL + ""; // DELETE (POST) for algorithms