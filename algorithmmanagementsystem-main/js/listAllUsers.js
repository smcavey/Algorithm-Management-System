
function refreshUsersList() {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListUsersURL, true);

   var data = {};
   data["token"] = "";
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllUsersResponse(xhr.responseText);
    } else {
      processListAllUsersResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllUsersResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var usersList = document.getElementById('usersList');
  
  var output = "";

  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];

    var userName = constantJson["username"];
	
		output = output + "<div id=\"" + userName + "\"><b>" + userName + ": </b>" 
		+ "<a href='javascript:requestUserActivity(\"" + userName + "\")'>View User Activity</a>"
		+ "<a href='javascript:requestDeleteUser(\"" + userName + "\")'><img src='deleteIcon.png'></img></a> <br></div>";
		
	}

	usersList.innerHTML = output;


  // Update computation result
}
