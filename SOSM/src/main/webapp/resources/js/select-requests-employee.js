function onPageLoad()
{
	document.getElementById('module').disabled = true;
  	document.getElementById('submit').disabled = true;
}

function onChange()
{
	document.getElementById('submit').disabled = false;
  
	if(document.getElementById('semester6').checked == true) {
		document.getElementById('module').disabled = false;
	}
	else {
		document.getElementById('module').checked = false;
		document.getElementById('module').disabled = true;
	}
	
	if(document.getElementById('unprocessed').checked == false &&
		document.getElementById('processed').checked == false) {
	  	document.getElementById('submit').disabled = true;
  }
  if(document.getElementById('semester4').checked == false &&
		  document.getElementById('semester5').checked == false &&
		  document.getElementById('semester6').checked == false &&
		  document.getElementById('semester7').checked == false) {
  		  document.getElementById('submit').disabled = true;
  }
  if(document.getElementById('electiveCourse').checked == false &&
	  	document.getElementById('courseProject').checked == false &&
	    document.getElementById('courseWork').checked == false &&
	    document.getElementById('module').checked == false) {
	  	document.getElementById('submit').disabled = true;
  }
}