function processAddImplementationResponse(result) {
	
	console.log("response: " + result);
	
	location.reload();
	
}
function createImplementation(sourceCode) {
	
	console.log("testing add implementation here!")
	
	var i_classificationName = document.getElementById("i_classificationName").value;
	console.log(i_classificationName);
	
	var i_algorithmName = document.getElementById("i_algorithmName").value;
	console.log(i_algorithmName);
	
	var ImplementationName = document.getElementById("ImplementationFileName").value;
	console.log(ImplementationName);
	
	var ImplementationDesc = document.getElementById("ImplementationDesc").value;
	console.log(ImplementationDesc);
	
	var ImplementationUpload = sourceCode;
	console.log(ImplementationUpload);
	
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["name"] = ImplementationName;
	data["description"] = ImplementationDesc;
	data["algorithm"] = i_algorithmName;
	data["fileContent"] = ImplementationUpload;
	
	var js = JSON.stringify(data);
	console.log("JS in implementation:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", addImplementationURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processAddImplementationResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processAddImplementationResponse("N/A");
    	}
  	};

	closeImplementationForm();

}