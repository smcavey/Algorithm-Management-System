function processReclassifyResponse(result) {
	
	console.log("response: " + result);
	
	refreshClassificationList();
	//location.reload();
	
}

function reclassify() {
	
	console.log("testing reclassify algorithm here!")
	
	var algorithmName = document.getElementById("reclassifyAlgorithmName").value;
	
	var newClassification = document.getElementById("newReclassifyClassification").value;	
	
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["algorithm"] = algorithmName;
	data["classification"] = newClassification;
	data["token"] = document.getElementById("token").innerHTML;

	var js = JSON.stringify(data);
	console.log("JS in reclassify:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", reclassifyAlgorithmURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processReclassifyResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processReclassifyResponse("N/A");
    	}
  	};

	closeReclassifyForm();
}