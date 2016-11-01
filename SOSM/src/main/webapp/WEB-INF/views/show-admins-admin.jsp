<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Администратори</title>
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
		<c:choose>
		    <c:when test="${admins[0] != null}">
		      	<table id="dataTable" style="background-color:white" class="table table-striped table-bordered table-hover table-condensed">
					<col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <col width="auto">
			        <c:if test="${sessionScope.currentUser.masterAdmin}">
			        	<col width="auto">
			        </c:if>
			        <caption>
				    	Администратори
			        </caption>
			        <thead>
			             <tr>
							<th>Потребителско име</th>
				            <th>Име</th>
				            <th>Фамилия</th>
				            <th>email</th>
				            <c:if test="${sessionScope.currentUser.masterAdmin}">
				            	<th>Главен администратор</th>
				            	<th>Редактирай</th>
			            	</c:if>
			             </tr>
			        </thead>
			        <tbody>
				    	<c:forEach var="admin" items="${admins}">
							<tr>
								<td><c:out value="${admin.nickname}"/></td>
								<td><c:out value="${admin.firstName}"/></td>
								<td><c:out value="${admin.lastName}"/></td>
								<td><c:out value="${admin.email}"/></td>
								<c:if test="${sessionScope.currentUser.masterAdmin}">
									<td>
										<c:choose>
										    <c:when test="${admin.masterAdmin == true}">
										       <span class="glyphicon glyphicon-ok positive-green" aria-hidden="true"></span>
										    </c:when>
										    <c:otherwise>
										        <span class="glyphicon glyphicon-remove negative-red" aria-hidden="true"></span>
										    </c:otherwise>
										</c:choose>		
									</td>
									<td>
										<c:if test="${admin.masterAdmin == false || admin.nickname == sessionScope.currentUser.nickname}">
											<form action="/app/admin/editAdmin" method="get">
												<button type="submit" class="btn btn-primary">
													<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
												</button>
												<input type="hidden" name="nickname" value="${admin.nickname}">
											</form>
										</c:if>
									</td>
								</c:if>
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
		<c:if test="${sessionScope.currentUser.masterAdmin}">
			<form action="/app/admin/createAdmin" method="get">
				<label for="username">Потребителско име:</label>
				<input type="text" name="username" id="username" pattern="[a-zA-Z0-9-_\.]{6,30}" title="Въведете от 6 до 30 символа" required><br>
		    	<button class="btn btn-primary" type="submit">Добави нов</button>
			</form>
		</c:if>
		<c:if test="${message == 'UserAlreadyExists'}">
			<div class="alert alert-warning" role="alert">
				Потребителското име е вече заето.
			</div>
		</c:if>
		<c:if test="${message == 'InvalidUsername'}">
			<div class="alert alert-warning" role="alert">
				Потребителското име е невалидно.
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