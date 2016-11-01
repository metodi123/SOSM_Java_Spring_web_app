function onPageLoad()
{
  	document.getElementById('submit').disabled = true;
}

function onInput()
{
  var sum = 0;
  $('.places').each(function()
  {
      sum += parseInt(this.value);
  });
  if(sum == document.getElementById('totalNumberOfRequests').innerHTML)
  {
 	 document.getElementById('submit').disabled = false;
  }
  else
  {
  document.getElementById('submit').disabled = true;
  }
}
