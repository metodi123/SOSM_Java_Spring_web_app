<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Вход - служител</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
</head>
<body>
<div id="wrapper">
	<div id="header">
        <h1>Система за обработка на студентски молби</h1>
    </div>
    <div id="loginbox">
		<form action="/app/login" method="post">
			<div class="row">
				<div class="col-sm-9">
					<div class="row">
						<div class="col-sm-offset-6 col-sm-4">
							<label for="username">Потребителско име: </label><br>
							<input type="text" class="form-control" name="username" pattern="[a-zA-Z0-9-_\.]{1,30}" title="Въведете до 30 символа" required>
						</div>
					</div>
					<div class="row">
					<br>
						<div class="col-sm-offset-6 col-sm-4">
							<label for="password">Парола: </label><br>
							<input type="password" class="form-control" name="password" pattern="[a-zA-Z0-9-_!@#$%^&*\.]{6,30}" title="Въведете между 6 и 30 символа" required>
						</div>
					</div>
					<div class="row">
					<br>
						<div class="col-sm-offset-7 col-sm-2">
							<input type="hidden" name="userType" value="employee">
							<button type="submit" class="btn btn-primary btn-block">Вход</button>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<c:if test="${message == 'InvalidUser'}">
		    			<div class="alert alert-warning" role="alert">
		   					Въведено е грешно потребителско име или парола.
		   				</div>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<div id="footer">
		<a href="/app/">Вход за студент</a>
		<br>
		<a href="/app/admin">Вход за администратор</a>
		<br>
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>
