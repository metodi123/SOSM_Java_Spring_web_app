<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Студенти</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <link href="<c:url value="/resources/data_tables/datatables.min.css" />" rel="stylesheet" type="text/css" />
	<script src="<c:url value="/resources/data_tables/datatables.min.js" />"></script>
	<script src="<c:url value="/resources/js/load-data-table-appearance.js" />"></script>
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
		<c:choose>
		    <c:when test="${students[0] != null}">
		      	<table id="dataTable" style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
					<col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <caption>
				    	Студенти
			        </caption>
			        <thead>
			             <tr>
				             <th>Факултетен номер</th>
				             <th>Име</th>
				             <th>Фамилия</th>
				             <th>Семестър</th>
				             <th>Успех</th>
				             <th>Група</th>
				             <th>Модул</th>
				             <th>email</th>
				             <th>Редактирай</th>
			             </tr>
			        </thead>
			        <tbody>
				    	<c:forEach var="student" items="${students}">
							<tr>
								<td><c:out value="${student.facultyNumber}"/></td>
								<td><c:out value="${student.firstName}"/></td>
								<td><c:out value="${student.lastName}"/></td>
								<td><c:out value="${student.currentSemester}"/></td>
								<td><c:out value="${student.GPA}"/></td>
								<td><c:out value="${student.group}"/></td>
								<td><c:out value="${student.module}"/></td>
								<td><c:out value="${student.email}"/></td>
								<td>
									<form action="/app/employee/editStudent" method="get">
										<button type="submit" class="btn btn-primary">
											<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
										</button>
										<input type="hidden" name="facultyNumber" value="${student.facultyNumber}">
									</form>
								</td>
							</tr>
						</c:forEach>
			        </tbody>
				</table>     
		    </c:when>
		    <c:otherwise>
		    	<div class="alert alert-warning" role="alert">
		       		Няма намерени резултати.
		       	</div>
		    </c:otherwise>
		</c:choose>
		<form action="/app/employee/createStudent" method="get">
	    	<label for="facultyNumber">Факултетен номер:</label>
			<input type="text" name="facultyNumber" id="facultyNumber" pattern="[0-9]{9}" title="Въведете 9 цифри" required><br>
	    	<button class="btn btn-primary" type="submit">Добави нов</button>
		</form>
		<c:if test="${message == 'UserAlreadyExists'}">
			<div class="alert alert-warning" role="alert">
				Този факултетен номер вече е зает.
			</div>
		</c:if>
		<c:if test="${message == 'InvalidFacultyNumber'}">
			<div class="alert alert-warning" role="alert">
				Този факултетен номер е невалиден.
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