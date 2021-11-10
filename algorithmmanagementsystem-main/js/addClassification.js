function processAddClassificationResponse(result) {
	
	console.log("response: " + result);
	
	refreshClassificationList();
	location.reload();	
}


function createClassification() {
	console.log("testing add classification here!")
	var classificationName = document.getElementById("classificationName").value;	
	
	var classificationDesc = document.getElementById("classificationDesc").value;
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["name"] = classificationName;
	data["description"] = classificationDesc;
	
	var js = JSON.stringify(data);
	console.log("JS in classification:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", addClassificationURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processAddClassificationResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processAddClassificationResponse("N/A");
    	}
  	};

	closeClassificationForm();
	refreshClassificationList();

}