function processDeleteClassificationResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
  
  refreshClassificationList();
}

function requestDeleteClassification(val) {
   if (confirm("Request to delete " + val)) {
     processDeleteClassification(val);
   }
}

function processDeleteClassification(name) {

  var xhr = new XMLHttpRequest();
  xhr.open("POST", deleteClassificationURL + "/" + name, true);    // ISSUE with POST v. DELETE in CORS/API Gateway

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processDeleteClassificationResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeleteClassificationResponse("N/A");
	  }
  };
  
  xhr.send(null);  //  NEED TO GET IT GOING
}

