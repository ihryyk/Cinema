<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <title>Title</title>
    <meta charset="utf-8">
    <title>Insert title here</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/main.css" />
    <link rel="stylesheet" type="text/css" href="styles/index.css">
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/main.css"%>
    </style>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <c:if test="${not empty sessionScope.popUpsSuccess}">
			<script>
				Swal.fire({
					icon: 'success',
					title: 'Success',
					text: '${sessionScope.popUpsSuccess}',
				});
			</script>
			${sessionScope.remove("popUpsSuccess")}
		</c:if>
        <header class="header">
			<ul class="header__list">
				<li class="header__list_item">
                    <a href=${pageContext.request.contextPath}/cinema/admin/addMovie class="header__list_link">
                        Add movie
                    </a>
                </li>
                <li class="header__list_item">
                    <a href=${pageContext.request.contextPath}/cinema/logout class="header__list_link">
                        Log out
                    </a>
                </li>
			</ul>
            <div class="search_wrapper">
                <form action="${pageContext.request.contextPath}/cinema/admin" method="get" class="search_form" class="search_form">
                    <input type="text" placeholder="Movie name" required name="movieName" class="search_input">
                    <input type="submit" value="Search" class="search_btn"/>
                </form>
            </div>
            <span class="header_user">${sessionScope.user.firstName}  ${sessionScope.user.lastName}</span>
            <a href=${pageContext.request.contextPath}/cinema/admin class="header_lang">
                ENG
            </a>
		</header>
        <main class="index_main">
            <div class="movies">
                <jsp:useBean id="movies" scope="request" type="java.util.List"/>
                <c:forEach var="movie" items="${movies}">
                    <div class="movie_wrapper">
                        <img src="data:image/png;base64, ${movie.base64ImagePoster}" width="240" height="300" class="movie_img"/>
                        <div class="movie_content">
                            <h3 class="movie_text movie_name">
                                <!-- Original name: -->
							    ${movie.originalName}
                            </h3>
                            <p class="movie_text">
                                <!-- Title:  -->
                                ${movie.movieDescriptionList.get(0).title}
                            </p>
                            <div class="movie_age-director">
                                <p class="movie_text">Age: ${movie.availableAge}+</p>
                                <p class="movie_text">Director ${movie.movieDescriptionList.get(0).director}</p>
                            </div>
                            <div class="movie_btns_wrapper">
                                <form action="${pageContext.request.contextPath}/cinema/admin/session"  method="get">
                                    <input type="hidden" required name="movieId" value="${movie.id}"/>
                                    <input type="submit" value="Sessions" class="sessions_btn"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/cinema/admin/updateMovie"  method="get">
                                    <input type="hidden" required name="movieId" value="${movie.id}"/>
                                    <input type="submit" value="Update" class="sessions_btn"/>
                                </form>
                                <c:if test="${movie.deleted==false}">
                                    <form action="${pageContext.request.contextPath}/cinema/admin/cancelMovie"  method="post">
                                        <input type="hidden" required name="movieId" value="${movie.id}"/>
                                        <input type="submit" value="Cancel" class="sessions_btn"/>
                                    </form>
                                </c:if>
                                <c:if test="${movie.deleted==true}">
                                   <span class="canceled">CANCELED</span>
                                  </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>
</body>
</html>

