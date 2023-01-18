<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 08.01.2023
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <%--    <form method="post" action="/cinema/admin/saveMovie" enctype="multipart/form-data">--%>
            <%--        <input type="file" name="poster"/>--%>
            <%--        <input type="submit" value="Upload"/>--%>
            <%--    </form>--%>
        <div class="add_form_wrapper">
            <h1 class="add_form_header">Add Movie</h1>
            <form
                action="${pageContext.request.contextPath}/cinema/admin/addMovie"
                method="post"
                enctype="multipart/form-data"
                class="add_form"
            >
                <div class="add_form_controllers">
                    <div class="add_form_controller">
                        <label for="originalName" class="add_form_label"
                            >Original name</label
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
                            >Release date</label
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
                            >Available age</label
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
                        <label for="poster">Poster</label>
                        <input
                            type="file"
                            name="poster"
                            id="poster"
                            class="add_form_input-file"
                        />
                        <label for="poster" class="poster_btn">Select file</label>
                    </div>
                    <jsp:useBean
                        id="languages"
                        scope="request"
                        type="java.util.List"
                    />
                    <c:forEach var="language" items="${languages}">
                    <div class="add_form_controller">
                        <label for="${language.name}Title">${language.name} title</label>
                        <input
                            type="Text"
                            required
                            name="${language.name}Title"
                            id="${language.name}Title"
                            class="add_form_input"
                            placeholder="${language.name} Title"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="${language.name}Director"
                            >${language.name} director</label
                        >
                        <input
                            type="Text"
                            required
                            name="${language.name}Director"
                            id="${language.name}Director"
                            class="add_form_input"
                            placeholder="${language.name} Director"
                        />
                    </div>
                    </c:forEach>
                </div>
                <input type="submit" value="Submit" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>

