
function refreshBenchmarkList(htmlID, iFileName) {
   var xhr = new XMLHttpRequest();
   xhr.open("POST", ListBenchmarkURL, true);

   var data = {};
   data["name"] = iFileName;
   var js = JSON.stringify(data);
   xhr.send(js);
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListAllBenchmarkResponse(xhr.responseText, htmlID);
    } else {
      processListAllBenchmarkResponse("N/A", htmlID);
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListAllBenchmarkResponse(result, id) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var benchmarkList = document.getElementById(id);
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
	
    var constantJson = js.list[i];
	    
    var benchmarkName = constantJson["name"];
	
	var l1Cache = constantJson["l1cache"];
	
	var l2Cache = constantJson["l2cache"];
	
	var l3Cache = constantJson["l3cache"];
	
	var ram = constantJson["ram"];
	
	var threads = constantJson["threads"];
	
	var cores = constantJson["cores"];
	
	var manufacturer = constantJson["manufacturer"];
		
	var implementationName = constantJson["implementation"];
	
	
    output = output + "<div style=\"text-indent:3em;\"" + "id=\"i" + benchmarkName + "\"><b>" + benchmarkName + ": </b>"
	+ "L1 Cache is " + l1Cache + ", L2 Cache is " + l2Cache + ", L3 Cache is " + l3Cache + ", RAM is " + ram + ", # threads is " 
	+ threads + ", # cores is " + cores + ", manufacturer is " + manufacturer
	+ "<a href='javascript:requestDeleteBenchmark(\"" + benchmarkName + "\")'><img src='deleteIcon.png'></img></a> <br></div>";  

  }

  // Update computation result
  benchmarkList.innerHTML = output;
  
}

