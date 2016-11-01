function onPageLoad()
{
	document.getElementById('submit').disabled = true;
}
function onChange(currentElement)
{
	if((currentElement == document.getElementById('semester6')) && (currentElement.value == 1))
	{
		document.getElementById('modules').value = 0;
	}
	else if((currentElement == document.getElementById('modules')) && (currentElement.value == 1))
	{
		document.getElementById('semester6').value = 0;
	}
	document.getElementById('submit').disabled = false;
}