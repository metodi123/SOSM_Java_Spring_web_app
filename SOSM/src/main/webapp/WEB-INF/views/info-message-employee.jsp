<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Съобщение</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
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
		<form action="/app/employee/saveInfoMessage" method="post">
		    <textarea rows ="5" cols="1" name="infoText" class="form-control" maxlength="1000"><c:out value="${infoText}"/></textarea>
			<br>
			<center><button class="btn btn-primary" type="submit">Запази</button></center>
		</form>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>