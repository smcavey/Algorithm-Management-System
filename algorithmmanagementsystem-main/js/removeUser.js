function processDeleteUserResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("deleted :" + result);
  
  refreshUsersList();
}

function requestDeleteUser(val) {
   if (confirm("Request to delete " + val)) {
     processDeleteUser(val);
   }
}

function processDeleteUser(name) {

  var xhr = new XMLHttpRequest();

  var data = {};
  data["username"] = name;

  var jsData = JSON.stringify(data);
	console.log("JS in remove user:" + jsData);

  xhr.open("POST", deleteUserURL, true);    // ISSUE with POST v. DELETE in CORS/API Gateway

  xhr.send(jsData);  //  NEED TO GET IT GOING


  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processDeleteUserResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processDeleteUserResponse("N/A");
	  }
  };
  
}

