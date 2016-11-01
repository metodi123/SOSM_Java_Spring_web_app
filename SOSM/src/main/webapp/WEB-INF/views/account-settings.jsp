<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Настройки</title>
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
		<c:choose>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Student'}">
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/settings/changePassword">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на парола</button>
						</form>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/settings/changeEmail">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на е-поща</button>
						</form>
					</div>
				</div>
			</c:when>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Employee'}">
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/employee/settings/changePassword">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на парола</button>
						</form>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/employee/settings/changeEmail">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на е-поща</button>
						</form>
					</div>
				</div>
			</c:when>
			<c:when test="${sessionScope.currentUser.getClass().name =='sosm.web.app.model.Admin'}">
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/admin/settings/changePassword">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на парола</button>
						</form>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-3">
						<form method="get" action="/app/admin/settings/changeEmail">
						    <button class="btn btn-primary btn-block" type="submit">Смяна на е-поща</button>
						</form>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:redirect url="/error403" />
			</c:otherwise>
		</c:choose>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>