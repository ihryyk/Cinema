<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 08.01.2023
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
                title: "Success",
                text: '${sessionScope.popUpsSuccess}'
            })
        </script>
        ${sessionScope.remove("popUpsSuccess")}
    </c:if>
    <header class="header">
			<ul class="header__list">
                <c:if test="${not empty sessionScope.user}">
                    <li class="header__list_item">
					    <a href=${pageContext.request.contextPath}/cinema/user/tickets class="header__list_link">Tickets </a>
				    </li>
				    <li class="header__list_item">
					    <a href=${pageContext.request.contextPath}/cinema/user/profile class="header__list_link">Profile</a>
				    </li>
				    <li class="header__list_item">
					    <a href=${pageContext.request.contextPath}/cinema/logout class="header__list_link">Log out</a>
				    </li>
                    <li class="header__list_item">
                        <span class="header_user">${sessionScope.user.firstName}  ${sessionScope.user.lastName}</span>
                    </li>
                </c:if>
				<c:if test="${empty sessionScope.user}">
                    <li class="header__list_item">
					    <a href=${pageContext.request.contextPath}/cinema/login class="header__list_link">Log in</a>
				    </li>
                </c:if>
				
			</ul>
			<div class="search_wrapper">
				<form
					action="${pageContext.request.contextPath}/cinema"
					method="get"
					class="search_form"
				>
					<input
						type="text"
						placeholder="Movie name"
						required
						name="movieName"
						class="search_input"
					/>
					<input
						type="submit"
						value="Search"
						class="search_btn"
						placeholder="Search"
					/>
				</form>
			</div>
			<a href=${pageContext.request.contextPath}/cinema class="header_lang"> ENG </a>
		</header>
        <main class="index_main">
            <div class="index_btns">
				<form
					action="${pageContext.request.contextPath}/cinema/user/getTodayMovies"
					method="get"
				>
					<input type="submit" value="Today's movies" class="index_btn" />
				</form>
				<form action="${pageContext.request.contextPath}/cinema" method="get">
					<input type="submit" value="Cinema" class="index_btn" />
				</form>
			</div>
            <div class="movies">
                <jsp:useBean id="movies" scope="request" type="java.util.List"/>
                <c:forEach var="movie" items="${movies}">
                    <div class="movie_wrapper">
                        <img
                            src="data:image/png;base64, ${movie.base64ImagePoster}"
                            width="240"
                            height="300"
                            class="movie_img"
                        />
                        <div class="movie_content">
                            <h3 class="movie_text movie_name">
                                Original name: ${movie.originalName}
                            </h3>
                            <p class="movie_text">
                                Title: ${movie.movieDescriptionList.get(0).title}
                            </p>
                            <div class="movie_age-director">
                                <p class="movie_text">Age: ${movie.availableAge}+</p>
                                <p class="movie_text">Director ${movie.movieDescriptionList.get(0).director}</p>
                            </div>
                            <form
                                action="${pageContext.request.contextPath}/cinema/movie/session"
                                method="get"
                            >
                                <input
                                    type="hidden"
                                    required
                                    name="movieId"
                                    value="${movie.id}"
                                />
                                <input type="submit" value="Sessions" class="sessions_btn" />
                            </form>
                        </div>
				    </div>
                </c:forEach>
            </div>
            <div class="index_paggination">
                <c:forEach var="i" begin="0" end="${requestScope.count/requestScope.movieOnPage}">
                    <c:if test="${not empty requestScope.movieName}">
                        <a href=${pageContext.request.contextPath}/cinema?page=${i}&movieName=${requestScope.movieName} class="index_paggination_link">
                            <c:out value="${i+1}"/>
                        </a>
                    </c:if>
                    <c:if test="${empty requestScope.movieName}">
                        <a href=${pageContext.request.contextPath}/cinema?page=${i} class="index_paggination_link">
                            <c:out value="${i+1}"/>
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </main>
</body>
</html>
