function onPageLoad()
{
	document.getElementById('semester4Submit').disabled = true;
	document.getElementById('semester5Submit').disabled = true;
	document.getElementById('semester6Submit').disabled = true;
	document.getElementById('semester7Submit').disabled = true;
}

function onChangeSemester4(currentElement)
{
	if(currentElement.checked == true) {
  		document.getElementById('semester4Submit').disabled = false;
	}
}

function onChangeSemester5(currentElement)
{
	if(currentElement.checked == true) {
  		document.getElementById('semester5Submit').disabled = false;
	}
}

function onChangeSemester6(currentElement)
{
	if(currentElement.checked == true) {
  		document.getElementById('semester6Submit').disabled = false;
	}
}

function onChangeSemester7(currentElement)
{
	if(currentElement.checked == true) {
  		document.getElementById('semester7Submit').disabled = false;
	}
}