
function refreshProblemInstanceList(htmlID, aName) {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListProblemInstanceURL, true);

   var data = {};
   data["name"] = aName;
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllProblemInstanceResponse(xhr.responseText, htmlID);
    } else {
      processListAllProblemInstanceResponse("N/A", htmlID);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllProblemInstanceResponse(result, id) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var problemInstanceList = document.getElementById(id);
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];

	var pi_FileName = constantJson["filename"];
	
	var pi_FilePath = constantJson["filePath"];

		
    output = output + "<div style=\"text-indent:2em;\"" + "id=\"a" + pi_FileName + "\"><b>" + pi_FileName + ": </b>" 
	+ "<a href=" + pi_FilePath + ">Download</a>"
	+ "<a href='javascript:requestDeleteProblemInstance(\"" + pi_FileName + "\")'><img src='deleteIcon.png'></img></a> <br></div>"
  }

  // Update computation result
  problemInstanceList.innerHTML = output;
  
}

