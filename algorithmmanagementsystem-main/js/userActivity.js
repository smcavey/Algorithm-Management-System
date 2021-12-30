
function requestUserActivity(username) {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListUserActivityURL, true);

   var data = {};
   data["name"] = username;
   data["token"] = "";
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processUserActivityResponse(xhr.responseText, username);
    } else {
      processUserActivityResponse("N/A", username);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processUserActivityResponse(result, username) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var userActivityLog = document.getElementById("userActivityLog");
  
  var output = "User Activity For: " + username + "<br></br>";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];

	
	var userName = constantJson["username"];
	var completion = constantJson["completedOn"];
	var query = constantJson["query"];
	
    output = output + "<div>Time: " + completion + ", Query: " + query + "</div>";
	
  }
  if (js.list.length === 0) {
	output = "No User Activity!";
  }

  // Update computation result
  showUserActivity();
  userActivityLog.innerHTML = output;
  
}

