<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Смяна на парола</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h1>Система за обработка на студентски молби</h1>
    </div>
    <div id="menu">
    	<c:choose>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Student'}">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
						<div class="navbar-header">
							<a class="navbar-brand" href="<c:url value="/" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
						</div>
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li><a href="<c:url value="/" />">Информация</a></li>
								<li><a href="<c:url value="/sendRequest" />">Записване</a></li>
								<li class="active"><a href="<c:url value="/settings" />">Настройки</a></li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<form class="navbar-form" action="/app/logout" method="post">
									<button class="btn btn-default navbar-right" type="submit">Изход</button>
								</form>
							</ul>
						</div>
					</div>
				</nav>
			</c:when>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Employee'}">
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
								<li class="active"><a href="<c:url value="/employee/settings" />">Настройки</a></li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<form class="navbar-form" action="/app/logout" method="post">
									<button class="btn btn-default navbar-right" type="submit">Изход</button>
								</form>
							</ul>
						</div>
					</div>
				</nav>
			</c:when>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Admin'}">
				<nav class="navbar navbar-default">
					<div class="container-fluid">
						<div class="navbar-header">
							<a class="navbar-brand" href="<c:url value="/employee" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
						</div>
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav">
								<li><a href="<c:url value="/admin" />">Информация</a></li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Избери<span class="caret"></span></a>
									<ul class="dropdown-menu">
										<li><a href="<c:url value="/admin/showEmployees" />">Служители</a></li>
										<li><a href="<c:url value="/admin/showAdmins" />">Администратори</a></li>
									</ul>
								</li>
								<li class="active"><a href="<c:url value="/admin/settings" />">Настройки</a></li>
							</ul>
							<ul class="nav navbar-nav navbar-right">
								<form class="navbar-form" action="/app/logout" method="post">
									<button class="btn btn-default navbar-right" type="submit">Изход</button>
								</form>
							</ul>
						</div>
					</div>
				</nav>
			</c:when>
			<c:otherwise>
				<c:redirect url="/error403" />
			</c:otherwise>
		</c:choose>
    </div>
    <div id="textbox">
		<form action="/app/changePassword" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="password" class="col-sm-4 control-label">Парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="password" id="password" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{6,30}" title="Въведете между 6 и 30 символа" required>
				</div>
			</div>
			<div class="form-group">
				<label for="newPassword" class="col-sm-4 control-label">Нова парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="newPassword" id="newPassword" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{6,30}" title="Въведете между 6 и 30 символа" required>
				</div>
			</div>
			<div class="form-group">
				<label for="newPasswordRepeat" class="col-sm-4 control-label">Повторете новата парола: </label>
				<div class="col-sm-4">
					<input type="password" class="form-control" name="newPasswordRepeat" id="newPasswordRepeat" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{6,30}" title="Въведете между 6 и 30 символа" required>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
			    	<input type="hidden" name="username" value="${sessionScope.currentUser.username}">
					<input type="hidden" name="currentPassword" value="${sessionScope.currentUser.password}">
		      		<button type="submit" class="btn btn-success btn-block">Изпрати</button>
		    	</div>
		  	</div>
		</form>
		<c:if test="${message == 'InvalidDataEntered'}">
			<div class="alert alert-warning" role="alert">
				Данните са невалидни.
			</div>
		</c:if>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>