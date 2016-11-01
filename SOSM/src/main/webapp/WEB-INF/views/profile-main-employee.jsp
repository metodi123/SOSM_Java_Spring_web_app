<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Информация</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/profile-main-employee.js" />"></script>
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
						<li class="active"><a href="<c:url value="/employee" />">Информация</a></li>
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
    	<div id="user-info">
	    	Потребителско име: <c:out value="${sessionScope.currentUser.nickname}"/><br>
	        Име: <c:out value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}"/><br>
	        e-mail: <c:out value="${sessionScope.currentUser.email}"/><br>
	    </div>
		<h4>Състояние на записванията:</h4>
			<form action="/app/employee/changeSelectionsState" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="semester4" class="col-sm-2 control-label">Семестър IV:</label>
					<div class="col-sm-2">
						<select class="form-control input-sm" name="semester4" id="semester4" required onchange="onChange(this)">
							<option value="0"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester4 == false}">
										selected
								</c:if>
							>Неактивно</option>
							<option value="1"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester4 == true}">
										selected
								</c:if>
							>Активно</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="semester5" class="col-sm-2 control-label">Семестър V:</label>
					<div class="col-sm-2">
						<select class="form-control input-sm" name="semester5" id="semester5" required onchange="onChange(this)">
							<option value="0"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester5 == false}">
										selected
								</c:if>
							>Неактивно</option>
							<option value="1"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester5 == true}">
										selected
								</c:if>
							>Активно</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="semester6" class="col-sm-2 control-label">Семестър VI:</label>
					<div class="col-sm-2">
						<select class="form-control input-sm" name="semester6" id="semester6" required onchange="onChange(this)">
							<option value="0"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester6 == false}">
										selected
								</c:if>
							>Неактивно</option>
							<option value="1"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester6 == true}">
										selected
								</c:if>
							>Активно</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="semester7" class="col-sm-2 control-label">Семестър VII:</label>
					<div class="col-sm-2">
						<select class="form-control input-sm" name="semester7" id="semester7" required onchange="onChange(this)">
							<option value="0"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester7 == false}">
										selected
								</c:if>
							>Неактивно</option>
							<option value="1"
								<c:if test="${sessionScope.selectionsState.selectionOpenForSemester7 == true}">
										selected
								</c:if>
							>Активно</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="modules" class="col-sm-2 control-label">Модули:</label>
					<div class="col-sm-2">
						<select class="form-control input-sm" name="modules" id="modules" required onchange="onChange(this)">
							<option value="0"
								<c:if test="${sessionScope.selectionsState.selectionOpenModules == false}">
										selected
								</c:if>
							>Неактивно</option>
							<option value="1"
								<c:if test="${sessionScope.selectionsState.selectionOpenModules == true}">
										selected
								</c:if>
							>Активно</option>
						</select>
					</div>
				</div>
				<div class="form-group">
			    	<div class="col-sm-offset-2 col-sm-2">
			      		<button type="submit" id="submit" class="btn btn-primary btn-block">Запази</button>
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