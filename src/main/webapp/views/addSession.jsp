<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/addForm.css" />
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="styles/index.css">
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/addForm.css"%>
    </style>
</head>
<body>
    <main class="index_main">
        <div class="add_form_wrapper">
            <h1 class="add_form_header">Add Session</h1>
            <form action="${pageContext.request.contextPath}/cinema?command=ADD_SESSION" method="post" class="add_form">
                <div class="add_form_controllers">
                    <div class="add_form_controller">
                        <label for="startTime" class="add_form_label"><fmt:message key="StartTime"/></label>
                        <input
                            type="datetime-local"
                            required
                            name="startTime"
                            id="startTime"
                            placeholder="<fmt:message key="StartTime"/>"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="endTime" class="add_form_label"><fmt:message key="EndTime"/></label>
                        <input
                            type="datetime-local"
                            required
                            name="endTime"
                            placeholder="<fmt:message key="EndTime"/>"
                            id="endTime"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="format"><fmt:message key="Format"/></label>
                        <select name="format" id="format" class="add_form_input">
                            <jsp:useBean
                                id="formats"
                                scope="request"
                                type="java.util.List"
                            />
                            <c:forEach var="format" items="${formats}">
                                <option value="${format}">${format}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="add_form_controller">
                        <label for="price"><fmt:message key="Price"/></label>
                        <input
                            type="number"
                            required
                            name="price"
                            placeholder="<fmt:message key="Price"/>"
                            id="price"
                            class="add_form_input"
                        />
                    </div>
                    <div>
                        <input
                            type="hidden"
                            name="movieId"
                            value="${requestScope.movieId}"
                        />
                    </div>
                </div>
                <input type="submit" value="<fmt:message key="Submit"/>" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>
