
function refreshAlgorithmList(htmlID, cName) {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListAlgorithmURL, true);

   var data = {};
   data["name"] = cName;
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllAlgorithmResponse(xhr.responseText, htmlID);
    } else {
      processListAllAlgorithmResponse("N/A", htmlID);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllAlgorithmResponse(result, id) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var algorithmList = document.getElementById(id);
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];
    
    var algorithmName = constantJson["name"];
	
	var algorithmDesc = constantJson["description"];
	
	var classificationName = constantJson["classification"];
	
    output = output + "<div style=\"text-indent:1em;\"" + "id=\"c" + algorithmName + "\"><b>" + algorithmName + ": </b>" + algorithmDesc 
	+ "<img src='deleteIcon.png'></img><br></div>"
	+ "<div id=\"" + algorithmName + "implementationList" + "\"" + "></div>";
	
	refreshImplementationList(algorithmName + "implementationList", algorithmName);
  }

  // Update computation result
  algorithmList.innerHTML = output;
  
}

