<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Грешка</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
</head>
<body>
<div id="wrapper">
	<h1>Грешка!</h1>
	<c:if test="${param.message == 'DatabaseError'}">
		<div class="alert alert-danger" role="alert">
			Възникна проблем с базата данни.
		</div>
	</c:if>
	<c:if test="${param.message == 'EmptyFirstChoice'}">
		<div class="alert alert-danger" role="alert">
		 	Не е избрано първо желание.
		</div>
	</c:if>
	<c:if test="${param.message == 'ChoicesEquality'}">
		<div class="alert alert-danger" role="alert">
			Избрани са дублиращи се стойности.
		</div>
	</c:if>
	<c:if test="${param.message == 'EnteredInvalidNumberOfPlaces'}">
		<div class="alert alert-danger" role="alert">
			Въведен е невалиден брой места за разпределяне.
		</div>
	</c:if>
	<button class="btn btn-info" onclick="history.back();" type="button">Обратно</button>
</div>
</body>
</html>