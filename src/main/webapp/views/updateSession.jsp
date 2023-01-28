<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/addForm.css" />
    <title>Title</title>
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/addForm.css"%>
    </style>
</head>
<body>
    <main class="index_main">
        <div class="add_form_wrapper">
            <h1 class="add_form_header"><fmt:message key="UpdateSession"/></h1>
            <form
                action="${pageContext.request.contextPath}/cinema?command=UPDATE_SESSION"
                method="post"
                class="add_form"
            >
                <div class="add_form_controllers">
                    <div class="add_form_controller">
                        <label for="startTime" class="add_form_label"><fmt:message key="StartTime"/></label>
                        <input
                            type="datetime-local"
                            required
                            name="startTime"
                            id="startTime"
                            value="${requestScope.session.startTime}"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="endTime" class="add_form_label"><fmt:message key="EndTime"/></label>
                        <input
                            type="datetime-local"
                            required
                            name="endTime"
                            id="endTime"
                            value="${requestScope.session.endTime}"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="format" class="add_form_label"><fmt:message key="Format"/></label>
                        <select name="format" id="format" class="add_form_input">
                            <jsp:useBean
                                id="formats"
                                scope="request"
                                type="java.util.List"
                            />
                            <c:forEach var="format" items="${formats}">
                                <option
                                    value="${format}"
                                    selected="${requestScope.session.format}"
                                >
                                    ${format}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="add_form_controller">
                        <label for="price" class="add_form_label"><fmt:message key="Price"/></label>
                        <input
                            type="number"
                            required
                            name="price"
                            placeholder="Price"
                            value="${requestScope.session.price}"
                            class="add_form_input"
                        />
                    </div>
                </div>
                <input type="hidden" name="movieId" value="${requestScope.movieId}" />
                <input
                    type="hidden"
                    name="sessionId"
                    value="${requestScope.session.id}"
                />
                <input type="submit" value="<fmt:message key="Submit"/>" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>
