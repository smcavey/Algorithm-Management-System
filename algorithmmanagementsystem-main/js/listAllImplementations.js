
function refreshImplementationList(htmlID, aName) {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListImplementationURL, true);

   var data = {};
   data["name"] = aName;
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllImplementationResponse(xhr.responseText, htmlID);
    } else {
      processListAllImplementationResponse("N/A", htmlID);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllImplementationResponse(result, id) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var implementationList = document.getElementById(id);
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];
    
    var implementationFileName = constantJson["fileName"];
	
	var implementationFilePath = constantJson["filePath"];
	
	var implementationDesc = constantJson["description"];
	
	var algorithmName = constantJson["algorithm"];
	
	
    output = output + "<div style=\"text-indent:2em;\"" + "id=\"a" + implementationFileName + "\"><b>" + implementationFileName + ": </b>" + implementationDesc 
	+ "<a href=" + implementationFilePath + ">....Download</a>"
	+ "<a href='javascript:requestDeleteImplementation(\"" + implementationFileName + "\")'><img src='deleteIcon.png'></img></a> <br></div>"
	+ "<div id=\"" + implementationFileName + "benchmarkList" + "\"" + "></div>";

	refreshBenchmarkList(implementationFileName + "benchmarkList", implementationFileName);

  }

  // Update computation result
  implementationList.innerHTML = output;
  
}

