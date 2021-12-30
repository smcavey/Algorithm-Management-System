function processMergeResponse(result) {
	
	console.log("response: " + result);
	
	refreshClassificationList();
	//location.reload();
	
}

function merge() {
	
	console.log("testing merge classification here!")
	
	var classificationOne = document.getElementById("classification1Name").value;
	
	var classificationTwo = document.getElementById("classification2Name").value;
	
	var newClassification = document.getElementById("newClassificationName").value;
	
	
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["classificationOne"] = classificationOne;
	data["classificationTwo"] = classificationTwo;
	data["classificationNew"] = newClassification;
	data["token"] = document.getElementById("token").innerHTML;

	var js = JSON.stringify(data);
	console.log("JS in merge:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", mergeClassificationURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processMergeResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processMergeResponse("N/A");
    	}
  	};

	closeMergeForm();
}