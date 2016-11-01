<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Разпределяне</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/process-requests-employee.js" />"></script>
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
						<li class="active"><a href="<c:url value="/employee/chooseRequestsToProcess" />">Класиране</a></li>
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
		<c:choose>
			<c:when test="${selectionType == 'electiveCourse'}">
				<h4>Разпределяне на студентите по избираеми дисциплини.</h4>
			</c:when>
			<c:when test="${selectionType == 'courseProject'}">
				<h4>Разпределяне на студентите по курсови проекти.</h4>
			</c:when>
			<c:when test="${selectionType == 'courseWork'}">
				<h4>Разпределяне на студентите по курсови работи.</h4>
			</c:when>
			<c:when test="${selectionType == 'module'}">
				<h4>Разпределяне на студентите по модули.</h4>
			</c:when>
			<c:otherwise>
				<c:redirect url="/error">
		     		<c:param name="message" value="DatabaseError"></c:param>
				</c:redirect>
			</c:otherwise>
		</c:choose>
    	<form action="/app/employee/processingStudentsRequests" method="post" class="form-horizontal">
	    	<c:if test="${selectionType != 'module'}">
	    		<center><h5>Изберете по колко студенти да бъдат разпределени за всяка дисциплина.</h5></center>
				<c:forEach var="item" items="${numberOfRequestsForCourseFirstChoice}">
					<div class="form-group">
						<label for="${item.key}" class="col-sm-4 control-label">${item.key}</label>
						<div class="col-sm-2">
							<input type="number" class="places form-control input-sm" name="${item.key}" id="${item.key}" min="0" max="${numberOfRequests}" value="0" oninput="onInput()">
				    	</div>	
				    	<c:out value=" : ${item.value} броя молби за първо желание"/>
		    		</div>
				</c:forEach>
			</c:if>
			<c:if test="${selectionType == 'module'}">
				<center><h5>Изберете по колко студенти да бъдат разпределени за всеки модул.</h5></center>
				<c:forEach var="item" items="${numberOfRequestsForModuleFirstChoice}">
					<div class="form-group">
						<label for="${item.key}" class="col-sm-4 control-label">${item.key}</label>
						<div class="col-sm-2">
							<input type="number" class="places form-control input-sm" name="${item.key}" id="${item.key}" min="0" max="${numberOfRequests}" value="0" oninput="onInput()">
			    		</div>
			    		<c:out value=" : ${item.value} броя молби за първо желание"/>
		    		</div>
				</c:forEach>
			</c:if>
			<div class="col-sm-offset-4 col-sm-2">
				<input type="hidden" name="semester" value="${semester}">
				<input type="hidden" name="selectionType" value="${selectionType}">
				<input type="hidden" name="numberOfRequests" value="${numberOfRequests}">
				<input type="hidden" name="moduleId" value="${moduleId}">
				<button type="submit" id="submit" class="btn btn-primary btn-block">Избери</button>
			</div>
			<div class="row">
				<div class="col-sm-offset-4 col-sm-4">
					Общ брой молби: <span id="totalNumberOfRequests"><c:out value="${numberOfRequests}"/></span>
				</div>
			</div>
    	</form>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>