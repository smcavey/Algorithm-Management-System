function processDeleteProblemInstanceResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
  
  refreshClassificationList();
}

function requestDeleteProblemInstance(val) {
   if (confirm("Request to delete " + val)) {
     processDeleteProblemInstance(val);
   }
}

function processDeleteProblemInstance(name) {

  var xhr = new XMLHttpRequest();

  var data = {};
  data["name"] = name;
  data["token"] = document.getElementById("token").innerHTML;

  var jsData = JSON.stringify(data);
	console.log("JS in remove implementation:" + jsData);

  xhr.open("POST", deleteProblemInstanceURL, true);    // ISSUE with POST v. DELETE in CORS/API Gateway

  xhr.send(jsData);  //  NEED TO GET IT GOING


  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processDeleteProblemInstanceResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeleteProblemInstanceResponse("N/A");
	  }
  };
  
}

