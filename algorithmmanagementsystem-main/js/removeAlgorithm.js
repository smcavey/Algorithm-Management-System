function processDeleteAlgorithmResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
  
  refreshClassificationList();
}

function requestDeleteAlgorithm(val) {
   if (confirm("Request to delete " + val)) {
     processDeleteAlgorithm(val);
   }
}

function processDeleteAlgorithm(name) {

  var xhr = new XMLHttpRequest();
  xhr.open("POST", deleteAlgorithmURL + "/" + name, true);    // ISSUE with POST v. DELETE in CORS/API Gateway

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processDeleteAlgorithmResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeleteAlgorithmResponse("N/A");
	  }
  };
  
  xhr.send(null);  //  NEED TO GET IT GOING
}

