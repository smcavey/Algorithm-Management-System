function processRegisterResponse(result) {
	
	console.log("response: " + result);
	
	//we may need a refreshUserList() --> This might be written when we get all users
	
}


function registerUser() {
	console.log("testing register user here!")
	var username = document.getElementById("name").value;
	console.log(username);
	
	
	var pw = document.getElementById("psw").value;
	console.log(pw);
	
	
	//Should we take in the username and then generate the hash here and salt?
	//Needs a username and userpassword
	
	var data = {};
	
	//not sur why this is throwing errors...could be because it doesn't know format of JSON yet defined in API
	data["username"] = username;
	data["password"] = pw;
	
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
 	xhr.open("POST", registerURL, true);

  	// send the collected data as JSON
  	xhr.send(js);

  	// This will process results and update HTML as appropriate. 
 	xhr.onloadend = function () {
    	console.log(xhr);
    	console.log(xhr.request);
    	if (xhr.readyState == XMLHttpRequest.DONE) {
    		if (xhr.status == 200) {
	      		console.log ("XHR:" + xhr.responseText);
	      		processRegisterResponse(xhr.responseText);
    	 	} else {
    			console.log("actual:" + xhr.responseText)
			  	var js = JSON.parse(xhr.responseText);
			  	var err = js["response"];
			  	alert (err);
    	 	}
    	} else {
      		processCreateResponse("N/A");
    	}
  	};
}