<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Редактиране на служител</title>
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
						<li><a href="<c:url value="/admin" />">Информация</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Избери<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<c:url value="/admin/showEmployees" />">Служители</a></li>
								<li><a href="<c:url value="/admin/showAdmins" />">Администратори</a></li>
							</ul>
						</li>
						<li><a href="<c:url value="/admin/settings" />">Настройки</a></li>
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
		<form action="/app/admin/editEmployeeRecord" method="post" class="form-horizontal">	
			<div class="form-group">
				<label for="firstName" class="col-sm-4 control-label">Име:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="firstName" id="firstName" value="${employee.firstName}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="lastName" class="col-sm-4 control-label">Фамилия:</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" name="lastName" id="lastName" value="${employee.lastName}" required>
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-4 control-label">Поща:</label>
				<div class="col-sm-4">
					<input type="email" class="form-control" name="email" id="email" value="${employee.email}" required>
				</div>
			</div>
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
			    	<input type="hidden" name="nickname" value="${employee.nickname}" required>
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
		<form action="/app/admin/deleteEmployeeRecord" method="post" class="form-horizontal" onsubmit="return confirm('Наистина ли искате да изтриете този запис от системата?');">
			<div class="form-group">
		    	<div class="col-sm-offset-4 col-sm-2">
		    		<input type="hidden" name="nickname" value="${employee.nickname}">
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