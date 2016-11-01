<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Молби</title>
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
		    <c:when test="${studentsRequests[0] != null}">
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
			        <col width="80px">
			        <caption>
				    	<c:if test="${studentsRequests[0].accepted == false}">
							<c:out value="Необработени молби"/>
						</c:if>
			            <c:if test="${studentsRequests[0].accepted == true}">
							<c:out value="Обработени молби"/>
						</c:if>
			        </caption>
			        <thead>
			             <tr>
				             <th>Номер</th>
				             <th>Факултетен номер</th>
				             <th>Семестър</th>
				             <th>Успех</th>
				             <th>Тип на молбата</th>
				             <th>Първо желание</th>
				             <th>Второ желание</th>
				             <th>Трето желание</th>
				             <th>Приет</th>
				             <th>Дата</th>
			             </tr>
			        </thead>
			        <tbody>
				    	<c:forEach var="studentsRequest" items="${studentsRequests}">
							<tr>
								<td><c:out value="${studentsRequest.id}"/></td>
								<td><c:out value="${studentsRequest.facutyNumber}"/></td>
								<td><c:out value="${studentsRequest.semester}"/></td>
								<td><c:out value="${studentsRequest.score}"/></td>
								<td><c:out value="${studentsRequest.selectionType}"/></td>
								<td><c:out value="${studentsRequest.firstChoice}"/></td>
								<td><c:out value="${studentsRequest.secondChoice}"/></td>
								<td><c:out value="${studentsRequest.thirdChoice}"/></td>
								<td><c:out value="${studentsRequest.selected}"/></td>
								<td><fmt:formatDate pattern="dd.MM.yyyy HH:mm ч." value="${studentsRequest.timestamp}" /></td>
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
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>