function processAddProblemInstanceResponse(result) {
	
	console.log("response: " + result);
	
	refreshClassificationList();
	//location.reload();
	
}
function createProblemInstance() {
	
	console.log("testing add problem instance here!")
	
	var pi_algorithmName = document.getElementById("pi_algorithmName").value;
	console.log(pi_algorithmName);
	
	var pi_FileName = document.getElementById("piFileName").value;
	console.log(pi_FileName);
	
	var problemInstanceUpload = document.getElementById("sourceCode").innerHTML;
	console.log(problemInstanceUpload);
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["fileName"] = pi_FileName;
	data["algorithm"] = pi_algorithmName;
	data["fileContent"] = problemInstanceUpload;


	data["token"] = document.getElementById("token").innerHTML;

	var js = JSON.stringify(data);
	console.log("JS in create problem instance:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", addProblemInstanceURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processAddProblemInstanceResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processAddProblemInstanceResponse("N/A");
    	}
  	};

	closeProblemInstanceForm();
}