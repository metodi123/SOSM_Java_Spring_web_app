<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="<c:url value="/resources/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Класиране</title>
    <script src="<c:url value="/resources/js/jquery-2.2.4.min.js" />"></script>
    <script src="<c:url value="/resources/bootstrap/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/js/choose-requests-to-process-employee.js" />"></script>
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
						<li><a href="<c:url value="/employee" />">Информация</a></li>
						<li class="active"><a href="<c:url value="/employee/chooseRequestsToProcess" />">Класиране</a></li>
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
		<div class="panel-group" id="semesters">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#semesters" href="#semester4">Семестър IV</a>
					</h4>
				</div>
				<div id="semester4" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="/app/employee/getRequestsToProcess" method="post">
					    	<label for="selectionType">Изберете вид на класирането:</label><br>
						    <input type="radio" name="selectionType" value="electiveCourse" id="electiveCourseSemester4"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester4Elective'] == false}">
						    		disabled
						    	</c:if>
						    	onchange="onChangeSemester4(this)"
						    ><label for="electiveCourseSemester4">Избираема дисциплина</label><br>
						    <input type="radio" name="selectionType" value="courseProject" id="courseProjectSemester4"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester4CourseProject'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester4(this)"
							><label for="courseProjectSemester4">Курсови проекти</label><br>
						    <input type="radio" name="selectionType" value="courseWork" id="courseWorkSemester4"
							   	<c:if test="${semestersContainingCoursesBySelectionType['semester4CourseWork'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester4(this)"
							><label for="courseWorkSemester4">Курсови работи</label><br>
					  		<input type="hidden" name="semester" value="4">
					  		<button class="btn btn-primary btn-sm" id="semester4Submit" type="submit">Избери</button>
				  		</form>
		        	</div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#semesters" href="#semester5">Семестър V</a>
					</h4>
				</div>
				<div id="semester5" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="/app/employee/getRequestsToProcess" method="post">
					    	<label for="selectionType">Изберете вид на класирането:</label><br>
						    <input type="radio" name="selectionType" value="electiveCourse" id="electiveCourseSemester5"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester5Elective'] == false}">
						    		disabled
						    	</c:if>
						    	onchange="onChangeSemester5(this)"
						    ><label for="electiveCourseSemester5">Избираема дисциплина</label><br>
						    <input type="radio" name="selectionType" value="courseProject" id="courseProjectSemester5"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester5CourseProject'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester5(this)"
							><label for="courseProjectSemester5">Курсови проекти</label><br>
						    <input type="radio" name="selectionType" value="courseWork" id="courseWorkSemester5"
							   	<c:if test="${semestersContainingCoursesBySelectionType['semester5CourseWork'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester5(this)"
							><label for="courseWorkSemester5">Курсови работи</label><br>
					  		<input type="hidden" name="semester" value="5">
					  		<button class="btn btn-primary btn-sm" id="semester5Submit" type="submit">Избери</button>
				  		</form>
        			</div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#semesters" href="#semester6">Семестър VI</a>
					</h4>
				</div>
				<div id="semester6" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="/app/employee/getRequestsToProcess" method="post">
					    	<label for="selectionType">Изберете вид на класирането:</label><br>
						    <input type="radio" name="selectionType" value="electiveCourse" id="electiveCourseSemester6"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester6Elective'] == false}">
						    		disabled
						    	</c:if>
						    	onchange="onChangeSemester6(this)"
						    ><label for="electiveCourseSemester6">Избираема дисциплина</label><br>
						    
						    <c:forEach var="module" items="${modules}">
						    	<input type="radio" name="selectionType" value="courseProject-${module.id}" id="courseProjectSemester6-${module.id}"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester6CourseProject'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester6(this)"
								><label for="courseProjectSemester6-${module.id}">Курсови проекти (за модул ${module.fullName})</label><br>
						    </c:forEach>
						    
						    <input type="radio" name="selectionType" value="courseWork" id="courseWorkSemester6"
							   	<c:if test="${semestersContainingCoursesBySelectionType['semester6CourseWork'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester6(this)"
							><label for="courseWorkSemester6">Курсови работи</label><br>
							<input type="radio" name="selectionType" value="module" id="moduleSemester6"
							   	<c:if test="${semestersContainingCoursesBySelectionType['semester6CourseWork'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester6(this)"
							><label for="moduleSemester6">Модули</label><br>
					  		<input type="hidden" name="semester" value="6">
					  		<button class="btn btn-primary btn-sm" id="semester6Submit" type="submit">Избери</button>
				  		</form>
        			</div>
      			</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#semesters" href="#semester7">Семестър VII</a>
					</h4>
				</div>
				<div id="semester7" class="panel-collapse collapse">
					<div class="panel-body">
						<form action="/app/employee/getRequestsToProcess" method="post">
					    	<label for="selectionType">Изберете вид на класирането:</label><br>
						    <input type="radio" name="selectionType" value="electiveCourse" id="electiveCourseSemester7" 
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester7Elective'] == false}">
						    		disabled
						    	</c:if>
						    	onchange="onChangeSemester7(this)"
						    ><label for="electiveCourseSemester7">Избираема дисциплина</label><br>
							
							<c:forEach var="module" items="${modules}">
						    	<input type="radio" name="selectionType" value="courseProject-${module.id}" id="courseProjectSemester7-${module.id}"
						    	<c:if test="${semestersContainingCoursesBySelectionType['semester7CourseProject'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester7(this)"
								><label for="courseProjectSemester7-${module.id}">Курсови проекти (за модул ${module.fullName})</label><br>
						    </c:forEach>
						    
						    <input type="radio" name="selectionType" value="courseWork" id="courseWorkSemester7"
							   	<c:if test="${semestersContainingCoursesBySelectionType['semester7CourseWork'] == false}">
									disabled
								</c:if>
								onchange="onChangeSemester7(this)"
							><label for="courseWorkSemester7">Курсови работи</label><br>
					  		<input type="hidden" name="semester" value="7">
					  		<button class="btn btn-primary btn-sm" id="semester7Submit" type="submit">Избери</button>
				  		</form>
        			</div>
      			</div>
			</div>
		</div>
		<c:if test="${message == 'SelectionIsOpen'}">
			<div class="alert alert-warning" role="alert">
				Записването все още не е приключило.
			</div>
		</c:if>
		<c:if test="${message == 'ModuleSelectionHasNotBeenProcessedYet'}">
			<div class="alert alert-warning" role="alert">
				Молбите за модули все още не са обработени.
			</div>
		</c:if>
		<c:if test="${message == 'ElectiveCourseSelectionHasNotBeenProcessedYet'}">
			<div class="alert alert-warning" role="alert">
				Молбите за избираеми дисциплини все още не са обработени.
			</div>
		</c:if>
		<c:if test="${message == 'CourseProjectSelectionHasNotBeenProcessedYet'}">
			<div class="alert alert-warning" role="alert">
				Молбите за курсови проекти все още не са обработени.
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