<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Попълване на молба</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/send-request-student.js" />"></script>
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
					<a class="navbar-brand" href="<c:url value="/" />"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/" />">Информация</a></li>
						<li class="active"><a href="<c:url value="/sendRequest" />">Записване</a></li>
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
		<c:choose>
			<c:when test="${sessionScope.currentUser.currentSemester == 3}">
				<h4>Моля, попълнете молбата за разпределение по учебни дисциплини за IV семестър.</h4>
			</c:when>
			<c:when test="${sessionScope.currentUser.currentSemester == 4}">
				<h4>Моля, попълнете молбата за разпределение по учебни дисциплини за V семестър.</h4>
			</c:when>
			<c:when test="${sessionScope.currentUser.currentSemester == 5}">
				<c:choose>
					<c:when test="${selectionsState.selectionOpenModules == true && selectionsState.selectionOpenForSemester6 == false}">
						<h4>Моля, попълнете молбата за разпределение по учебни модули.</h4>
					</c:when>
					<c:when test="${selectionsState.selectionOpenForSemester6 == true && selectionsState.selectionOpenModules == false}">
						<h4>Моля, попълнете молбата за разпределение по учебни дисциплини за VI семестър.</h4>
					</c:when>
					<c:otherwise>
						<c:redirect url="/error">
		     				<c:param name="message" value="DatabaseError"></c:param>
						</c:redirect>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:when test="${sessionScope.currentUser.currentSemester == 6}">
				<h4>Моля, попълнете молбата за разпределение по учебни дисциплини за VII семестър.</h4>
			</c:when>
			<c:otherwise>
				<c:redirect url="/error">
     				<c:param name="message" value="DatabaseError"></c:param>
				</c:redirect>
			</c:otherwise>
		</c:choose>
		<form action="/app/sendStudentRequests" method="post" class="form-horizontal">
			<c:choose>
				<c:when test="${(sessionScope.currentUser.currentSemester == 5) && (selectionsState.selectionOpenModules == true) && (selectionsState.selectionOpenForSemester6 == false)}">
						<div class="form-group">
							<label for="module-first-choice" class="col-sm-4 control-label">Първо желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="module-first-choice" id="module-first-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="module" items="${modules}">
										<option value="${module.name}">
				       						<c:out value="${module.fullName}"/>
				       					</option>
								    </c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="module-second-choice" class="col-sm-4 control-label">Второ желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="module-second-choice" id="module-second-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="module" items="${modules}">
									   	<option value="${module.name}">
				       						<c:out value="${module.fullName}"/>
				       					</option>
								    </c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="module-third-choice" class="col-sm-4 control-label">Трето желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="module-third-choice" id="module-third-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="module" items="${modules}">
									   	<option value="${module.name}">
				       						<c:out value="${module.fullName}"/>
				       					</option>
								    </c:forEach>
								</select>
							</div>
						</div>
				</c:when>
				<c:otherwise>
					<c:if test="${((sessionScope.currentUser.currentSemester == 3) && (semestersContainingCoursesBySelectionType['semester4Elective'] == true)) ||
					((sessionScope.currentUser.currentSemester == 4) && (semestersContainingCoursesBySelectionType['semester5Elective'] == true)) ||
					(((sessionScope.currentUser.currentSemester == 5) && (semestersContainingCoursesBySelectionType['semester6Elective'] == true)) && (selectionsState.selectionOpenForSemester6 == true && selectionsState.selectionOpenModules == false)) ||
					((sessionScope.currentUser.currentSemester == 6) && (semestersContainingCoursesBySelectionType['semester7Elective'] == true))}">
						<center><h5>Избор на избираема дисциплина:</h5></center>
						<div class="form-group">
				       		<label for="elective-first-choice" class="col-sm-4 control-label">Първо желание: </label>
				       		<div class="col-sm-4">
								<select class="form-control input-sm" name="elective-first-choice" id="elective-first-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
								    	<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.elective}">
									   		<option value="${course.name}">
								       			<c:out value="${course.fullName}"/>
								       		</option>
										</c:if>
								    </c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="elective-second-choice" class="col-sm-4 control-label">Второ желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="elective-second-choice" id="elective-second-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
								    	<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.elective}">
									   		<option value="${course.name}">
								       			<c:out value="${course.fullName}"/>
								       		</option>
										</c:if>
								    </c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="elective-third-choice" class="col-sm-4 control-label">Трето желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="elective-third-choice" id="elective-third-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
								    	<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.elective}">
									   		<option value="${course.name}">
								       			<c:out value="${course.fullName}"/>
								       		</option>
										</c:if>
								    </c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<c:if test="${((sessionScope.currentUser.currentSemester == 3) && (semestersContainingCoursesBySelectionType['semester4CourseProject'] == true)) ||
					((sessionScope.currentUser.currentSemester == 4) && (semestersContainingCoursesBySelectionType['semester5CourseProject'] == true)) ||
					(((sessionScope.currentUser.currentSemester == 5) && (semestersContainingCoursesBySelectionType['semester6CourseProject'] == true)) && (selectionsState.selectionOpenForSemester6 == true && selectionsState.selectionOpenModules == false)) ||
					((sessionScope.currentUser.currentSemester == 6) && (semestersContainingCoursesBySelectionType['semester7CourseProject'] == true))}">
						<center><h5>Избор на дисциплина за разработка на курсов проект:</h5></center>
						<div class="form-group">
							<label for="course-project-first-choice" class="col-sm-4 control-label">Първо желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-project-first-choice" id="course-project-first-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${empty course.moduleName || course.moduleName == sessionScope.currentUser.module}">
										    <c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseProject}">
										   		<option value="${course.name}">
								       				<c:out value="${course.fullName}"/>
								       			</option>
											</c:if>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>					
						<div class="form-group">
							<label for="course-project-second-choice" class="col-sm-4 control-label">Второ желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-project-second-choice" id="course-project-second-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${empty course.moduleName || course.moduleName == sessionScope.currentUser.module}">
											<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseProject}">
												<option value="${course.name}">
								       				<c:out value="${course.fullName}"/>
								       			</option>
											</c:if>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>		
						<div class="form-group">
							<label for="course-project-third-choice" class="col-sm-4 control-label">Трето желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-project-third-choice" id="course-project-third-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${empty course.moduleName || course.moduleName == sessionScope.currentUser.module}">
											<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseProject}">
												<option value="${course.name}">
								       				<c:out value="${course.fullName}"/>
								       			</option>
											</c:if>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<c:if test="${((sessionScope.currentUser.currentSemester == 3) && (semestersContainingCoursesBySelectionType['semester4CourseWork'] == true)) ||
					((sessionScope.currentUser.currentSemester == 4) && (semestersContainingCoursesBySelectionType['semester5CourseWork'] == true)) ||
					(((sessionScope.currentUser.currentSemester == 5) && (semestersContainingCoursesBySelectionType['semester6CourseWork'] == true)) && (selectionsState.selectionOpenForSemester6 == true && selectionsState.selectionOpenModules == false)) ||
					((sessionScope.currentUser.currentSemester == 6) && (semestersContainingCoursesBySelectionType['semester7CourseWork'] == true))}">
						<center><h5>Избор на дисциплина за разработка на курсова работа:</h5></center>
						<div class="form-group">	
							<label for="course-work-first-choice" class="col-sm-4 control-label">Първо желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-work-first-choice" id="course-work-first-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseWork}">
											<option value="${course.name}">
								       			<c:out value="${course.fullName}"/>
								       		</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">	
							<label for="course-work-second-choice" class="col-sm-4 control-label">Второ желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-work-second-choice" id="course-work-second-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseWork}">
											<option value="${course.name}">
								       			<c:out value="${course.fullName}"/>
								       		</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="course-work-third-choice" class="col-sm-4 control-label">Трето желание: </label>
							<div class="col-sm-4">
								<select class="form-control input-sm" name="course-work-third-choice" id="course-work-third-choice" onchange="onChange()">
									<option value="">-Избери-</option>
									<c:forEach var="course" items="${courses}">
										<c:if test="${course.semester == (sessionScope.currentUser.currentSemester + 1) && course.courseWork}">
											<option value="${course.name}">
												<c:out value="${course.fullName}"/>
										    </option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
			<div class="col-sm-offset-4 col-sm-2">
				<input type="hidden" name="requestSemester" value="${sessionScope.currentUser.currentSemester + 1}">
				<button type="submit" id="submit" class="btn btn-primary">Изпрати</button>
			</div>
		</form>
		<br>
    </div>
    <div id="footer">
        <hr>
        Copyright © 2016 Metodi Metodiev&nbsp;&nbsp;&nbsp;All Rights Reserved
    </div>
</div>
</body>
</html>