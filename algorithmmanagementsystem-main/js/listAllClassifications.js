
function refreshClassificationList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", ListClassificationURL, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllClassificationResponse(xhr.responseText);
    } else {
      processListAllClassificationResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllClassificationResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var classificationList = document.getElementById('classificationList');
  
  var output = "";
  var subClassOutput = "";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];

    var classificationName = constantJson["name"];
	
	var classificationDesc = constantJson["description"];
	
	var parentClassification = constantJson["parent"];
	/*
	if (constantJson.hasOwnProperty("parent")) {
		
		console.log("parent Classification is: " + parentClassification);
		
		var subClassificationList = document.getElementById('c' + parentClassification);
		
		subClassOutput = "<div id=\"c" + classificationName + "\"><b>" + classificationName + ": </b>" + classificationDesc 
		+ "<img src='deleteIcon.png'></img> <br></div>"
		+ "<div id=\"" + classificationName + "algorithmList" + "\"" + "></div>";
		
		refreshAlgorithmList(classificationName + "algorithmList", classificationName);
		
		// FIXME
		//alert('subclassification ' + classificationName + ' not added as subClassificationList.innerHTML for its parent is null: ' + 'c' + parentClassification);
		//subClassificationList.innerHTML = subClassificationList.innerHTML + subClassOutput; //FIXME: 
	subClassificationList.innerHTML = subClassificationList.innerHTML+ subClassOutput;
		
    } else {*/
	
		output = output + "<div id=\"c" + classificationName + "\"><b>" + classificationName + ": </b>" + classificationDesc 
		+ "<a href='javascript:requestDeleteClassification(\"" + classificationName + "\")'><img src='deleteIcon.png'></img></a> <br></div>"
		+ "<div id=\"" + classificationName + "algorithmList" + "\"" + "></div>";
		
		refreshAlgorithmList(classificationName + "algorithmList", classificationName);
		classificationList.innerHTML = output;	


	//style=\'padding-left: 100px;\'
	
	}

  // Update computation result
}
