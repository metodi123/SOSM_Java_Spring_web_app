<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Информация</title>
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
					<a class="navbar-brand" href="<c:url value="/" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="active"><a href="<c:url value="/" />">Информация</a></li>
						<li><a href="<c:url value="/sendRequest" />">Записване</a></li>
						<li><a href="<c:url value="/settings" />">Настройки</a></li>
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
            Факултетен номер: <c:out value="${sessionScope.currentUser.facultyNumber}"/><br>
            Име: <c:out value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}"/><br>
            Семестър: <c:out value="${sessionScope.currentUser.currentSemester}"/><br>
            Среден успех: <c:out value="${sessionScope.currentUser.GPA}"/><br>
            Група: <c:out value="${sessionScope.currentUser.group}"/><br>
            <c:choose>
			    <c:when test="${sessionScope.currentUser.module == null}">
			       Модул: -<br>
			    </c:when>
			    <c:otherwise>
			        Модул: <c:out value="${sessionScope.currentUser.module}"/><br>
			    </c:otherwise>
			</c:choose>
            e-mail: <c:out value="${sessionScope.currentUser.email}"/><br>
		</div>
		<c:choose>
			<c:when test="${sessionScope.currentUser.currentSemester == 5}">
				<c:choose>
					<c:when test="${(studentRequestsState.requestSendByStudentForModules == false) && (studentRequestsState.requestSendByStudentForCourses == false)}">
						Няма изпратени молби.
					</c:when>
					<c:when test="${(studentRequestsState.requestSendByStudentForModules == true) && (studentRequestsState.requestSendByStudentForCourses == false)}">
						<c:choose>
							<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForModules == true) && (studentRequestsState.processedRequestSendByStudentForModules == false)}">
								Молбата за модули все още не е обработена.
								<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
							</c:when>
							<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForModules == false) && (studentRequestsState.processedRequestSendByStudentForModules == true)}">
								Класиране за модули:
								<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${(studentRequestsState.requestSendByStudentForModules == true) && (studentRequestsState.requestSendByStudentForCourses == true)}">
						<c:choose>
							<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForCourses == true) && (studentRequestsState.processedRequestSendByStudentForCourses == false) ||
											(studentRequestsState.unprocessedRequestSendByStudentForCourses == true) && (studentRequestsState.processedRequestSendByStudentForCourses == true)}">
								Молбата за дисциплини все още не е обработена.
								<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
							</c:when>
							<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForCourses == false) && (studentRequestsState.processedRequestSendByStudentForCourses == true)}">
								Класиране за дисциплини:
								<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
							</c:when>
						</c:choose>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${studentRequestsState.requestSendByStudentForCourses == false}">
						Няма изпратени молби.
					</c:when>
					<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForCourses == true) && (studentRequestsState.processedRequestSendByStudentForCourses == false) ||
									(studentRequestsState.unprocessedRequestSendByStudentForCourses == true) && (studentRequestsState.processedRequestSendByStudentForCourses == true)}">
							Молбата за дисциплини все още не е обработена.
							<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
					</c:when>
					<c:when test="${(studentRequestsState.unprocessedRequestSendByStudentForCourses == false) && (studentRequestsState.processedRequestSendByStudentForCourses == true)}">
							Класиране за дисциплини:
							<table style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="auto">
							        <col width="80px">
							        <thead>
							             <tr>
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
											<c:if test="${(studentsRequest.facutyNumber == sessionScope.currentUser.facultyNumber) && (studentsRequest.semester == sessionScope.currentUser.currentSemester + 1)}">
												<tr>
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
											</c:if>
										</c:forEach>
							        </tbody>
								</table>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<br>
		<br>
        <c:if test="${message == 'NoRequestsForNextSemester'}">
	    	<div class="alert alert-warning" role="alert">
	   			Няма записване за следващия семестър.
	   		</div>
		</c:if>
        <c:if test="${message == 'StudentUnavaliableToSendRequest'}">
	        <div class="alert alert-warning" role="alert">
	   			В момента не се извършва записване за следващия семестър.
	   		</div>
		</c:if>
		<c:if test="${message == 'RequestAlreadySent'}">
	    	<div class="alert alert-warning" role="alert">
	   			Заявка вече е изпратена.
	   		</div>
		</c:if>
		<h4>Съобщение:</h4>
		<p style="white-space: pre-wrap"><c:out value="${infoText}"/></p>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>