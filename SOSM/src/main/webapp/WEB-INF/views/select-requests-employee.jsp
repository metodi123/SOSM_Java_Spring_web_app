<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Избор на молби</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/select-requests-employee.js" />"></script>
</head>
<body onload="onPageLoad()">
<div id="wrapper">
    <div id="header">
        <h1>Система за обработка на студентски молби</h1>
    </div>
    <div id="menu">
        <nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="<c:url value="/employee" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/employee" />">Информация</a></li>
						<li><a href="<c:url value="/employee/chooseRequestsToProcess" />">Класиране</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Избери<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/employee/selectRequests" />">Молби</a></li>
								<li><a href="<c:url value="/employee/showStudents" />">Студенти</a></li>
								<li><a href="<c:url value="/employee/showCourses" />">Дисциплини</a></li>
								<li><a href="<c:url value="/employee/showModules" />">Модули</a></li>
								<li role="separator" class="divider"></li>
								<li><a href="<c:url value="/employee/writeInformationMessage" />">Съобщение</a></li>
							</ul>
						</li>
						<li><a href="<c:url value="/employee/settings" />">Настройки</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<form class="navbar-form" action="/app/logout" method="post">
							<button class="btn btn-default navbar-right" type="submit">Изход</button>
						</form>
					</ul>
				</div>
			</div>
		</nav>
    </div>
    <div id="textbox">
    	<form action="/app/employee/findRequests" method="post">
    		<label for="requestsState"><h4>Състояние на молбите:</h4></label>	
    		<br>
	    	<input type="radio" name="requestsState" id="unprocessed" value="unprocessed" onchange="onChange()" checked><label for="unprocessed">Необработени</label><br>
	  		<input type="radio" name="requestsState" id="processed" value="processed" onchange="onChange()"><label for="processed">Обработени</label><br>
    		<br>
    		<label for="requestsSemester"><h4>Семестър:</h4></label>
    		<br>
    		<input type="checkbox" name="requestsSemester" id="semester4" value="fourth" onchange="onChange()"><label for="semester4">Семестър IV</label><br>
  			<input type="checkbox" name="requestsSemester" id="semester5" value="fifth" onchange="onChange()"><label for="semester5">Семестър V</label><br>
  			<input type="checkbox" name="requestsSemester" id="semester6" value="sixth" onchange="onChange()"><label for="semester6">Семестър VI</label><br>
  			<input type="checkbox" name="requestsSemester" id="semester7" value="seventh" onchange="onChange()"><label for="semester7">Семестър VII</label><br>
    		<br>
    		<label for="requestsType"><h4>Тип на молбата:</h4></label>
    		<br>
    		<input type="checkbox" name="requestsType" id="electiveCourse" value="electiveCourse" onchange="onChange()"><label for="electiveCourse">Записване за избираема дисциплина</label><br>
  			<input type="checkbox" name="requestsType" id="courseProject" value="courseProject" onchange="onChange()"><label for="courseProject">Записване за курсов проект</label><br>
  			<input type="checkbox" name="requestsType" id="courseWork" value="courseWork" onchange="onChange()"><label for="courseWork">Записване за курсова работа</label><br>
  			<input type="checkbox" name="requestsType" id="module" value="module" onchange="onChange()"><label for="module">Записване за модул</label><br>
    		<br>
    		<button class="btn btn-primary" id="submit" type="submit">Избери</button>
		</form>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>