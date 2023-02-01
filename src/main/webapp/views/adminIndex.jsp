<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
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
    <link rel="stylesheet" href="../styles/index.css"/>
    <link rel="stylesheet" href="../styles/main.css"/>
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
            title: ' <fmt:message key="Success"/>',
            text: '<fmt:message key="${sessionScope.popUpsSuccess}"/>',
        });
    </script>
    ${sessionScope.remove("popUpsSuccess")}
</c:if>
<header class="header">
    <ul class="header__list">
        <li class="header__list_item">
            <a href=${pageContext.request.contextPath}/cinema?command=ADD_MOVIE_PAGE class="header__list_link">
                <fmt:message key="AddMovie"/>
            </a>
        </li>
        <li class="header__list_item">
            <form method="post" action="${pageContext.request.contextPath}/cinema?command=LOG_OUT" class="header__list_item">
                <button type="submit" name="submit_param" value="submit_value" class="link-button">
                    <fmt:message key="Logout"/>
                </button>
            </form>
        </li>
    </ul>
    <div class="search_wrapper">
        <form action="${pageContext.request.contextPath}/cinema" method="get" class="search_form"
        >
            <input type="text" placeholder="<fmt:message key="MovieName"/>" required name="movieName" class="search_input">
            <input type="hidden" value="ADMIN_PAGE" name="command" class="index_btn"/>
            <input type="submit" value="<fmt:message key="Search"/>" class="search_btn"/>
        </form>
    </div>
    <span class="header_user">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
    <c:if test="${sessionScope.pageLanguage == 'ua'}">
        <a href=${pageContext.request.contextPath}/cinema?command=ADMIN_PAGE&changeLanguage=eng class="header_lang"> <fmt:message key="Eng"/> </a>
    </c:if>
    <c:if test="${sessionScope.pageLanguage == 'eng'}">
        <a href=${pageContext.request.contextPath}/cinema?command=ADMIN_PAGE&changeLanguage=ua class="header_lang"><fmt:message key="Ua"/> </a>
    </c:if>
</header>
<main class="index_main">
    <div class="movies">
        <c:if test="${fn:length(requestScope.movies)!=0}">
            <jsp:useBean id="movies" scope="request" type="java.util.List"/>
            <c:forEach var="movie" items="${movies}">
                <div class="movie_wrapper">
                    <img src="data:image/png;base64, ${movie.base64ImagePoster}" width="240" height="300"
                         class="movie_img"/>
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
                            <p class="movie_text"><fmt:message key="Age"/>: ${movie.availableAge}+</p>
                            <p class="movie_text"><fmt:message key="Director"/> ${movie.movieDescriptionList.get(0).director}</p>
                        </div>
                        <div class="movie_btns_wrapper">
                            <form action="${pageContext.request.contextPath}/cinema" method="get">
                                <input type="hidden" required name="movieId" value="${movie.id}"/>
                                <input type="hidden" value="ADMIN_SESSIONS_PAGE" name="command" class="index_btn"/>
                                <input type="submit" value="<fmt:message key="Sessions"/>" class="sessions_btn"/>
                            </form>
                            <form action="${pageContext.request.contextPath}/cinema" method="get">
                                <input type="hidden" required name="movieId" value="${movie.id}"/>
                                <input type="hidden" value="UPDATE_MOVIE_PAGE" name="command" class="index_btn"/>
                                <input type="submit" value="<fmt:message key="Update"/>" class="sessions_btn"/>
                            </form>
                            <c:if test="${movie.deleted==false}">
                                <form action="${pageContext.request.contextPath}/cinema?command=CANCEL_MOVIE"
                                      method="post">
                                    <input type="hidden" required name="movieId" value="${movie.id}"/>
                                    <input type="submit" value="<fmt:message key="Cancel"/>" class="sessions_btn"/>
                                </form>
                            </c:if>
                            <c:if test="${movie.deleted==true}">
                                <span class="canceled"><fmt:message key="CANCELLED"/></span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${fn:length(requestScope.movies)==0}">
        <p class="movie_warning"><fmt:message key="NullMovie"/></p>
    </c:if>
</main>
</body>
</html>


