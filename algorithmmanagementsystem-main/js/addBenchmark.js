function processAddBenchmarkResponse(result) {
	
	console.log("response: " + result);
	
	refreshClassificationList();
	//location.reload();
	
}
function createBenchmark() {
	
	console.log("testing add algorithm here!")
	
	var b_ImplementationFilename = document.getElementById("b_ImplementationFileName").value;
	
	var benchmarkName = document.getElementById("benchmarkName").value;
	
	var l1Cache = document.getElementById("l1cache").value;
	
	var l2Cache = document.getElementById("l2cache").value;

	var l3Cache = document.getElementById("l3cache").value;
	
	var ram = document.getElementById("ram").value;
	
	var threads = document.getElementById("threads").value;
	
	var cores = document.getElementById("cores").value;
	
	var manufacturer = document.getElementById("manufacturer").value;
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["implementation"] = b_ImplementationFilename;
	data["name"] = benchmarkName;
	data["l1cache"] = l1Cache;
	data["l2cache"] = l2Cache;
	data["l3cache"] = l3Cache;
	data["ram"] = ram;
	data["threads"] = threads;
	data["cores"] = cores;
	data["manufacturer"] = manufacturer;


	data["token"] = document.getElementById("token").innerHTML;

	var js = JSON.stringify(data);
	console.log("JS in create benchmark:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", addBenchmarkURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processAddBenchmarkResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processAddBenchmarkResponse("N/A");
    	}
  	};

	closeBenchmarkForm();
}