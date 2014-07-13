/**
 * 
 * @author rajat
 * frontend interact with backend using ajax call
 */


function createQueryString(){
	var origin = document.getElementById("origin").value;
	var startDate = document.getElementById("startdate").value;
	var endDate = document.getElementById("enddate").value;
	var numDays= (new Date(endDate)-new Date(startDate))/(1000*60*60*24); 
	var budget = document.getElementById("range").value;
	var tastes = document.getElementsByName('category');
	var userTastes=[];var j=0;
	for (var i=0, n=tastes.length;i<n;i++) {
	  if (tastes[i].checked) 
	  {
		 userTastes[j] = tastes[i].value;
		 j++;
	  }
	}
	var query="origin="+origin+"&"+"numDays="+numDays+"&"+"taste="+userTastes+"&"+"budget="+budget;
	return query;
}

function suggestDest()
{
	var xmlhttp;
	var query = createQueryString();
	var Sender = window.event.srcElement;
	if(Sender.id=="dest")
	{
		query=query+"&next=0";
	}
	else
	{
		query=query+"&next=1";
	}

	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }

	xmlhttp.onreadystatechange=function()
	  {
	  if(xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
			  if(Sender.id=="dest"){
			  	var scrollDown = document.createElement('script');
			  	scrollDown.setAttribute('src','js/scroll.js');
			  	document.head.appendChild(scrollDown);
			  	//document.getElementById("suggestedDest").innerHTML="";
			  }

			  //makediv(xmlhttp.responseText,appendResults);
			  //console.log(div.text);
			  //alert(div.text);
			  //document.getElementById("suggestedDest").appendChild(div);
			  //document.getElementById("suggestedDest").appendChild(document.createElement('div').innerHTML=xmlhttp.responseText);
			  var div = document.createElement('div');
			  div.innerHTML=xmlhttp.responseText;
			  //div.id="destinationAdded";
			  //div.style="cursor:pointer";
			  document.getElementById("suggestedDest").appendChild(div);
			  $(window).data('ajaxready', true);
			 //console.log("test "+ document.getElementById("suggestedDest").appendChild(div));
	    }
	  }
		xmlhttp.open("GET","/suggestDest?"+query,true);
		xmlhttp.send();
}

function makediv(response,callback)
{
	var div = document.createElement('div');
	div.innerHTML=response;
	callback(div);
}

function appendResults(responseDiv)
{
	document.getElementById("suggestedDest").appendChild(responseDiv);
}

function suggestGroups()
{
	var xmlhttp;
	var query = createQueryString();
	var Sender = window.event.srcElement;
	if(Sender.id=="dest")
	{
		query=query+"&next=0";
	}
	else
	{
		query=query+"&next=1";
	}

	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }

	xmlhttp.onreadystatechange=function()
	  {
	  if(xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
		  if(Sender.id=="dest"){
			  	var scrollDown = document.createElement('script');
			  	scrollDown.setAttribute('src','js/scroll.js');
			  	document.head.appendChild(scrollDown);
			  	//document.getElementById("suggestedDest").innerHTML="";
			  }
			  makediv(xmlhttp.responseText,appendResults);
			  //var div = document.createElement('div');
			  //div.innerHTML=xmlhttp.responseText;
			  //document.getElementById("suggestedDest").appendChild(div);
	    }
	  }
		xmlhttp.open("GET","/suggestGroups",true);
		xmlhttp.send();
}