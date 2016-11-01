<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Редактиране на дисциплина</title>
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
		<form action="/app/employee/editCourseRecord" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">Име:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="name" id="name" value="${course.name}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="fullName" class="col-sm-4 control-label">Пълно име:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="fullName" id="fullName" value="${course.fullName}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="semester" class="col-sm-4 control-label">Семестър:</label>
				<div class="col-sm-4">
					<input type="number" class="form-control" name="semester" id="semester" min="1" max="8" value="${course.semester}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="module" class="col-sm-4 control-label">Модул:</label>
				<div class="col-sm-4">
					<select class="form-control" name="module" id="module" required>
						<option value="noModule"></option>
						<c:forEach var="module" items="${modules}">
							<option value="${module.name}"
								<c:if test="${module.name == course.moduleName}">
									selected
								</c:if>
							>
								<c:out value="${module.fullName}"/>
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="electiveCourse" class="col-sm-4 control-label">Избираема дисциплина:</label>
				<div class="col-sm-4">
					<select class="form-control" name="electiveCourse" id="electiveCourse" required>
						<option value="0"
							<c:if test="${course.elective == false}">
								selected
							</c:if>
						>Не</option>
						<option value="1"
							<c:if test="${course.elective == true}">
								selected
							</c:if>
						>Да</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="courseWork" class="col-sm-4 control-label">Курсова работа:</label>
				<div class="col-sm-4">
					<select class="form-control" name="courseWork" id="courseWork" required>
						<option value="0"
							<c:if test="${course.courseWork == false}">
								selected
							</c:if>
						>Не</option>
						<option value="1"
							<c:if test="${course.courseWork == true}">
								selected
							</c:if>
						>Да</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="courseProject" class="col-sm-4 control-label">Курсов проект:</label>
				<div class="col-sm-4">
					<select class="form-control" name="courseProject" id="courseProject" required>
						<option value="0"
							<c:if test="${course.courseProject == false}">
								selected
							</c:if>
						>Не</option>
						<option value="1"
							<c:if test="${course.courseProject == true}">
								selected
							</c:if>
						>Да</option>
					</select>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		    		<input type="hidden" name="id" value="${course.id}" required>
		      		<button type="submit" class="btn btn-success btn-block">Запази</button>
		    	</div>
		  	</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Бяха въведени невалидни данни.
			</div>
		</c:if>
		<br>
		<form action="/app/employee/deleteCourseRecord" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		    		<input type="hidden" name="id" value="${course.id}">
		    		<button type="submit" class="btn btn-danger btn-block">Премахни</button>
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