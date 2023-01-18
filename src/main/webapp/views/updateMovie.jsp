<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
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
            <h1 class="add_form_header">Update Movie</h1>
            <form
                action="${pageContext.request.contextPath}/cinema/admin/updateMovie"
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
                            id="originalName"
                            value="${requestScope.movie.originalName}"
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
                            value="${requestScope.movie.releaseDate}"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="availableAge" class="add_form_label"
                            >Available age</label
                        >
                        <input
                            type="text"
                            required
                            name="availableAge"
                            id="availableAge"
                            value="${requestScope.movie.availableAge}"
                            class="add_form_input"
                        />
                    </div>
                    
                    <c:forEach var="movieDescription" items="${requestScope.movie.movieDescriptionList}">
                    <div class="add_form_controller">
                        <label
                            for="${movieDescription.language.name}Title"
                            class="add_form_label"
                            >${movieDescription.language.name} title</label
                        >
                        <input
                            type="Text"
                            required
                            name="${movieDescription.language.name}Title"
                            id="${movieDescription.language.name}Title"
                            value="${movieDescription.title}"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label
                            for="${movieDescription.language.name}Director"
                            class="add_form_label"
                            >${movieDescription.language.name} director</label
                        >
                        <input
                            type="Text"
                            required
                            name="${movieDescription.language.name}Director"
                            id="${movieDescription.language.name}Director"
                            value="${movieDescription.director}"
                            class="add_form_input"
                        />
                    </div>
                    </c:forEach>
                    <div class="add_form_controller-poster">
                        <div class="add_form_controlle">
                            <label for="poster" class="add_form_label">Poster</label>
                            <input
                                type="file"
                                name="poster"
                                id="poster"
                                class="add_form_input-file"
                            />
                            <label for="poster" class="poster_btn">Select file</label>
                            <input
                                type="text"
                                hidden
                                name="oldPoster"
                                value="${requestScope.movie.base64ImagePoster}"
                            />
                        </div>
                        <img
                            src="data:image/png;base64, ${requestScope.movie.base64ImagePoster}"
                            width="240"
                            height="300"
                            class="update_img"
                        />
                    </div>
                    <input type="hidden" name="movieId" value="${requestScope.movie.id}"/></td>
                </div>
                <input type="submit" value="Submit" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>
