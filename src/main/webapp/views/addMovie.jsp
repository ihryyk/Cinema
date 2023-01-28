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
            <h1 class="add_form_header"><fmt:message key="AddMovie"/></h1>
            <form
                action="${pageContext.request.contextPath}/cinema?command=ADD_MOVIE" method="post"
                enctype="multipart/form-data"
                class="add_form"
            >
                <div class="add_form_controllers">
                    <div class="add_form_controller">
                        <label for="originalName" class="add_form_label"
                            > <fmt:message key="OriginalName"/></label
                        >
                        <input
                            type="text"
                            required
                            name="originalName"
                            placeholder="Original Name"
                            id="originalName"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="releaseDate" class="add_form_label"
                            > <fmt:message key="ReleaseDate"/></label
                        >
                        <input
                            type="datetime-local"
                            required
                            name="releaseDate"
                            id="releaseDate"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="availableAge" class="add_form_label"
                            > <fmt:message key="AvailableAge"/></label
                        >
                        <input
                            type="number"
                            required
                            name="availableAge"
                            id="availableAge"
                            class="add_form_input"
                            placeholder="Age"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="poster"> <fmt:message key="Poster"/></label>
                        <input
                            type="file"
                            name="poster"
                            id="poster"
                            class="add_form_input-file"
                        />
                        <label for="poster" class="poster_btn"><fmt:message key="SelectFile"/></label>
                    </div>
                    <jsp:useBean
                        id="languages"
                        scope="request"
                        type="java.util.List"
                    />
                    <c:forEach var="language" items="${languages}">
                    <div class="add_form_controller">
                        <label for="${language.name}Title">${language.name} <fmt:message key="Title"/></label>
                        <input
                            type="Text"
                            required
                            name="${language.name}Title"
                            id="${language.name}Title"
                            class="add_form_input"
                            placeholder="${language.name} <fmt:message key="Title"/>"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="${language.name}Director"
                            >${language.name} <fmt:message key="Director"/></label
                        >
                        <input
                            type="Text"
                            required
                            name="${language.name}Director"
                            id="${language.name}Director"
                            class="add_form_input"
                            placeholder="${language.name}<fmt:message key="Director"/>"
                        />
                    </div>
                    </c:forEach>
                </div>
                <input type="submit" value="<fmt:message key="Submit"/>" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>

