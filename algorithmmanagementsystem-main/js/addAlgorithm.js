function processAddAlgorithmResponse(result) {
	
	console.log("response: " + result);
	
	location.reload();
	
}
function createAlgorithm() {
	
	console.log("testing add algorithm here!")
	
	var a_classificationName = document.getElementById("a_classificationName").value;
	
	var algorithmName = document.getElementById("algorithmName").value;
	
	var algorithmDesc = document.getElementById("algorithmDesc").value;
	
	
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["classification"] = a_classificationName;
	data["name"] = algorithmName;
	data["description"] = algorithmDesc;
	
	var js = JSON.stringify(data);
	console.log("JS in algorithm:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", addAlgorithmURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processAddAlgorithmResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processAddAlgorithmResponse("N/A");
    	}
  	};

	closeAlgorithmForm();
}