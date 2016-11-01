function onPageLoad()
{
  	document.getElementById('submit').disabled = true;

  	if (document.getElementById('elective-first-choice') != null)
    {
	    if(document.getElementById("elective-first-choice").length == 2)
	    {
	    	document.getElementById("elective-second-choice").disabled = true;
	    	document.getElementById("elective-third-choice").disabled = true;
	    }
	    else if(document.getElementById("elective-first-choice").length == 3)
	    {
	    	document.getElementById("elective-third-choice").disabled = true;
	    }
    }
    
    
    if (document.getElementById('course-project-first-choice') != null)
    {
	    if(document.getElementById("course-project-first-choice").length == 2)
	    {
	    	document.getElementById("course-project-second-choice").disabled = true;
	    	document.getElementById("course-project-third-choice").disabled = true;
	    }
	    else if(document.getElementById("course-project-first-choice").length == 3)
	    {
	    	document.getElementById("course-project-third-choice").disabled = true;
	    }
    }
    
    
    if (document.getElementById('course-work-first-choice') != null)
    {
	    if(document.getElementById("course-work-first-choice").length == 2)
	    {
	    	document.getElementById("course-work-second-choice").disabled = true;
	    	document.getElementById("course-work-third-choice").disabled = true;
	    }
	    else if(document.getElementById("course-work-first-choice").length == 3)
	    {
	    	document.getElementById("course-work-third-choice").disabled = true;
	    }
    }
    
    
    if (document.getElementById('module-first-choice') != null)
    {
	    if(document.getElementById("module-first-choice").length == 2)
	    {
	    	document.getElementById("module-second-choice").disabled = true;
	    	document.getElementById("module-third-choice").disabled = true;
	    }
	    else if(document.getElementById("module-first-choice").length == 3)
	    {
	    	document.getElementById("module-third-choice").disabled = true;
	    }
    }
  	
}

var defaultValue = '';

function onChange()
{
  if (document.getElementById('elective-first-choice') != null &&
      document.getElementById('course-project-first-choice') === null &&
      document.getElementById('course-work-first-choice') === null &&
      document.getElementById('module-first-choice') === null) {
     			 onChangeWithOneSelectionType('elective-first-choice', 'elective-second-choice', 'elective-third-choice');
  }
  else if(document.getElementById('elective-first-choice') === null &&
      document.getElementById('course-project-first-choice') != null &&
      document.getElementById('course-work-first-choice') === null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithOneSelectionType('course-project-first-choice', 'course-project-second-choice', 'course-project-third-choice');
  }
  else if(document.getElementById('elective-first-choice') === null &&
      document.getElementById('course-project-first-choice') === null &&
      document.getElementById('course-work-first-choice') != null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithOneSelectionType('course-work-first-choice', 'course-work-second-choice', 'course-work-third-choice');
  }
   else if(document.getElementById('elective-first-choice') != null &&
      document.getElementById('course-project-first-choice') != null &&
      document.getElementById('course-work-first-choice') === null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithTwoSelectionTypes('elective-first-choice', 'elective-second-choice', 'elective-third-choice', 'course-project-first-choice', 'course-project-second-choice', 'course-project-third-choice');
  }
  else if(document.getElementById('elective-first-choice') != null &&
      document.getElementById('course-project-first-choice') === null &&
      document.getElementById('course-work-first-choice') != null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithTwoSelectionTypes('elective-first-choice', 'elective-second-choice', 'elective-third-choice', 'course-work-first-choice', 'course-work-second-choice', 'course-work-third-choice');
  }
  else if(document.getElementById('elective-first-choice') === null &&
      document.getElementById('course-project-first-choice') != null &&
      document.getElementById('course-work-first-choice') != null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithTwoSelectionTypes('course-project-first-choice', 'course-project-second-choice', 'course-project-third-choice', 'course-work-first-choice', 'course-work-second-choice', 'course-work-third-choice');
  }
  else if(document.getElementById('elective-first-choice') != null &&
      document.getElementById('course-project-first-choice') != null &&
      document.getElementById('course-work-first-choice') != null &&
      document.getElementById('module-first-choice') === null) {
  				onChangeWithThreeSelectionTypes('elective-first-choice', 'elective-second-choice', 'elective-third-choice', 'course-project-first-choice', 'course-project-second-choice', 'course-project-third-choice', 'course-work-first-choice', 'course-work-second-choice', 'course-work-third-choice');
  }
  else if(document.getElementById('elective-first-choice') === null &&
      document.getElementById('course-project-first-choice') === null &&
      document.getElementById('course-work-first-choice') === null &&
      document.getElementById('module-first-choice') != null) {
  				onChangeWithOneSelectionType('module-first-choice', 'module-second-choice', 'module-third-choice');
  }
  
}

function onChangeWithOneSelectionType(selectionTypeFirstChoice, selectionTypeSecondChoice, selectionTypeThirdCoice)
{
  	document.getElementById('submit').disabled = true;

	if(document.getElementById(selectionTypeFirstChoice).value == document.getElementById(selectionTypeSecondChoice).value ||
  		document.getElementById(selectionTypeFirstChoice).value == document.getElementById(selectionTypeThirdCoice).value ||
      document.getElementById(selectionTypeSecondChoice).value == document.getElementById(selectionTypeThirdCoice).value
  ) {
   document.getElementById('submit').disabled = true;
   if(document.getElementById(selectionTypeFirstChoice).value != defaultValue &&
     	document.getElementById(selectionTypeSecondChoice).value == defaultValue &&
    	document.getElementById(selectionTypeThirdCoice).value == defaultValue){
								document.getElementById('submit').disabled = false;
    }
  }
  else {
      if(document.getElementById(selectionTypeFirstChoice).value != defaultValue) {
          document.getElementById('submit').disabled = false;
      }
      if(document.getElementById(selectionTypeSecondChoice).value == defaultValue && document.getElementById(selectionTypeThirdCoice).value != defaultValue) {
          document.getElementById('submit').disabled = true;
      }
  }
}

function onChangeWithTwoSelectionTypes(selectionType1FirstChoice, selectionType1SecondChoice, selectionType1ThirdCoice, selectionType2FirstChoice, selectionType2SecondChoice, selectionType2ThirdCoice)
{
  	document.getElementById('submit').disabled = true;

	if(((document.getElementById(selectionType1FirstChoice).value != defaultValue) && (document.getElementById(selectionType1FirstChoice).value == document.getElementById(selectionType1SecondChoice).value)) ||
  		((document.getElementById(selectionType1FirstChoice).value != defaultValue) && (document.getElementById(selectionType1FirstChoice).value == document.getElementById(selectionType1ThirdCoice).value)) ||
      ((document.getElementById(selectionType1SecondChoice).value != defaultValue) && (document.getElementById(selectionType1SecondChoice).value == document.getElementById(selectionType1ThirdCoice).value)) ||
      ((document.getElementById(selectionType2FirstChoice).value != defaultValue) && (document.getElementById(selectionType2FirstChoice).value == document.getElementById(selectionType2SecondChoice).value)) ||
      ((document.getElementById(selectionType2FirstChoice).value != defaultValue) && (document.getElementById(selectionType2FirstChoice).value == document.getElementById(selectionType2ThirdCoice).value)) ||
      ((document.getElementById(selectionType2SecondChoice).value != defaultValue) && (document.getElementById(selectionType2SecondChoice).value == document.getElementById(selectionType2ThirdCoice).value))) 
  {
         document.getElementById('submit').disabled = true;

  }
  else if(document.getElementById(selectionType1FirstChoice).value != defaultValue &&
            document.getElementById(selectionType1SecondChoice).value == defaultValue &&
            document.getElementById(selectionType1ThirdCoice).value == defaultValue &&
            document.getElementById(selectionType2FirstChoice).value != defaultValue &&
            document.getElementById(selectionType2SecondChoice).value == defaultValue &&
            document.getElementById(selectionType2ThirdCoice).value == defaultValue){
                      document.getElementById('submit').disabled = false;
  }
  else {
      if(document.getElementById(selectionType1FirstChoice).value != defaultValue &&
      	document.getElementById(selectionType2FirstChoice).value != defaultValue) {
        			
              document.getElementById('submit').disabled = false;
      }
      if((document.getElementById(selectionType1SecondChoice).value == defaultValue && document.getElementById(selectionType1ThirdCoice).value != defaultValue) || (document.getElementById(selectionType2SecondChoice).value == defaultValue && document.getElementById(selectionType2ThirdCoice).value != defaultValue)) {
          document.getElementById('submit').disabled = true;
      }
  }
}

function onChangeWithThreeSelectionTypes(selectionType1FirstChoice, selectionType1SecondChoice, selectionType1ThirdCoice, selectionType2FirstChoice, selectionType2SecondChoice, selectionType2ThirdCoice, selectionType3FirstChoice, selectionType3SecondChoice, selectionType3ThirdCoice)
{
  	document.getElementById('submit').disabled = true;

	if(((document.getElementById(selectionType1FirstChoice).value != defaultValue) && (document.getElementById(selectionType1FirstChoice).value == document.getElementById(selectionType1SecondChoice).value)) ||
  		((document.getElementById(selectionType1FirstChoice).value != defaultValue) && (document.getElementById(selectionType1FirstChoice).value == document.getElementById(selectionType1ThirdCoice).value)) ||
      ((document.getElementById(selectionType1SecondChoice).value != defaultValue) && (document.getElementById(selectionType1SecondChoice).value == document.getElementById(selectionType1ThirdCoice).value)) ||
      ((document.getElementById(selectionType2FirstChoice).value != defaultValue) && (document.getElementById(selectionType2FirstChoice).value == document.getElementById(selectionType2SecondChoice).value)) ||
      ((document.getElementById(selectionType2FirstChoice).value != defaultValue) && (document.getElementById(selectionType2FirstChoice).value == document.getElementById(selectionType2ThirdCoice).value)) ||
      ((document.getElementById(selectionType2SecondChoice).value != defaultValue) && (document.getElementById(selectionType2SecondChoice).value == document.getElementById(selectionType2ThirdCoice).value)) ||
      ((document.getElementById(selectionType3FirstChoice).value != defaultValue) && (document.getElementById(selectionType3FirstChoice).value == document.getElementById(selectionType3SecondChoice).value)) ||
      ((document.getElementById(selectionType3FirstChoice).value != defaultValue) && (document.getElementById(selectionType3FirstChoice).value == document.getElementById(selectionType3ThirdCoice).value)) ||
      ((document.getElementById(selectionType3SecondChoice).value != defaultValue) && (document.getElementById(selectionType3SecondChoice).value == document.getElementById(selectionType3ThirdCoice).value))) 
  {
         document.getElementById('submit').disabled = true;

  }
  else if(document.getElementById(selectionType1FirstChoice).value != defaultValue &&
            document.getElementById(selectionType1SecondChoice).value == defaultValue &&
            document.getElementById(selectionType1ThirdCoice).value == defaultValue &&
            document.getElementById(selectionType2FirstChoice).value != defaultValue &&
            document.getElementById(selectionType2SecondChoice).value == defaultValue &&
            document.getElementById(selectionType2ThirdCoice).value == defaultValue &&
            document.getElementById(selectionType3FirstChoice).value != defaultValue &&
            document.getElementById(selectionType3SecondChoice).value == defaultValue &&
            document.getElementById(selectionType3ThirdCoice).value == defaultValue){
                      document.getElementById('submit').disabled = false;
  }
  else {
      if(document.getElementById(selectionType1FirstChoice).value != defaultValue &&
      	document.getElementById(selectionType2FirstChoice).value != defaultValue &&
        document.getElementById(selectionType3FirstChoice).value != defaultValue) {
        			
              document.getElementById('submit').disabled = false;
      }
      if((document.getElementById(selectionType1SecondChoice).value == defaultValue && document.getElementById(selectionType1ThirdCoice).value != defaultValue) || (document.getElementById(selectionType2SecondChoice).value == defaultValue && document.getElementById(selectionType2ThirdCoice).value != defaultValue) ||   (document.getElementById(selectionType3SecondChoice).value == defaultValue && document.getElementById(selectionType3ThirdCoice).value != defaultValue)) {
          document.getElementById('submit').disabled = true;
      }
  }
}