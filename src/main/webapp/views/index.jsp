<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
<html>
<head>
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
            title: "<fmt:message key="Success"/>",
            text: '<fmt:message key="${sessionScope.popUpsSuccess}"/>'
        })
    </script>
    ${sessionScope.remove("popUpsSuccess")}
</c:if>
<c:if test="${not empty sessionScope.popUpsError}">
    <script>
        Swal.fire({
            icon: 'error',
            title: "<fmt:message key="Exception"/>",
            text: '<fmt:message key="${sessionScope.popUpsError}"/>'
        })
    </script>
    ${sessionScope.remove("popUpsError")}
</c:if>
<header class="header">
    <ul class="header__list">
        <c:if test="${empty sessionScope.user or sessionScope.user.role!='USER'}">
            <li class="header__list_item">
                <a href=${pageContext.request.contextPath}/cinema?command=LOG_IN_PAGE
                   class="header__list_link"><fmt:message key="Login"/></a>
            </li>
        </c:if>
        <c:if test="${not empty sessionScope.user and sessionScope.user.role=='USER'}">
            <li class="header__list_item">
                <span class="header_user">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
            </li>
            <form method="post" action="${pageContext.request.contextPath}/cinema?command=LOG_OUT" class="header__list_item">
                <button type="submit" name="submit_param" value="submit_value" class="link-button">
                        <fmt:message key="Logout"/>
                </button>
            </form>
            <li class="header__list_item">
                <a href=${pageContext.request.contextPath}/cinema?command=TICKETS_PAGE
                   class="header__list_link"> <fmt:message key="Tickets"/> </a>
            </li>
            <li class="header__list_item">
                <a href=${pageContext.request.contextPath}/cinema?command=PROFILE_PAGE
                   class="header__list_link"> <fmt:message key="Profile"/></a>
            </li>
        </c:if>
    </ul>
    <div class="search_wrapper">
        <form action="${pageContext.request.contextPath}/cinema" method="get" class="search_form">
            <input type="text" name="movieName" placeholder="<fmt:message key="MovieName"/>" class="search_input">
            <input type="hidden" value="INDEX_PAGE" name="command"/>
            <input type="submit" value="<fmt:message key="Search"/>" class="search_btn"/>
        </form>
    </div>
    <c:if test="${sessionScope.pageLanguage == 'ua'}">
        <a href=${pageContext.request.contextPath}/cinema?changeLanguage=eng&command=INDEX_PAGE class="header_lang">
            <fmt:message key="Eng"/> </a>
    </c:if>
    <c:if test="${sessionScope.pageLanguage == 'eng'}">
        <a href=${pageContext.request.contextPath}/cinema?changeLanguage=ua&command=INDEX_PAGE
           class="header_lang"><fmt:message key="Ua"/> </a>
    </c:if>
</header>
<main class="index_main">
    <div class="index_btns">
        <form
                action="${pageContext.request.contextPath}/cinema"
                method="get"
        >
            <input type="hidden" value="true" name="todayMovies"/>
            <input type="hidden" value="INDEX_PAGE" name="command"/>
            <input type="submit" value="<fmt:message key="TodayMovies"/>" class="todays_btn"/>
        </form>
        <form action="${pageContext.request.contextPath}/cinema" method="get">
            <input type="hidden" value="INDEX_PAGE" name="command"/>
            <input type="submit" value="<fmt:message key="Cinema"/>" class="index_btn"/>
        </form>
    </div>
    <div class="movies">
        <c:if test="${fn:length(requestScope.movies)!=0}">
            <jsp:useBean id="movies" scope="request" type="java.util.List"/>
            <c:forEach var="movie" items="${movies}">
                <div class="movie_wrapper">
                    <div class="image_wrapper">
                        <img
                                src="data:image/png;base64, ${movie.base64ImagePoster}"
                                width="240"
                                height="300"
                                class="movie_img"
                        />
                    </div>
                    <div class="movie_content">
                        <h3 class="movie_text movie_name">
                                ${movie.originalName}
                        </h3>
                        <p class="movie_text">
                            <fmt:message key="Title"/>: ${movie.movieDescriptionList.get(0).title}
                        </p>
                        <div class="movie_age-director">
                            <p class="movie_text"><fmt:message key="Age"/>: ${movie.availableAge}+</p>
                            <p class="movie_text"><fmt:message
                                    key="Director"/>: ${movie.movieDescriptionList.get(0).director}</p>
                        </div>
                        <form
                                action="${pageContext.request.contextPath}/cinema"
                                method="get"
                        >
                            <input
                                    type="hidden"
                                    required
                                    name="movieId"
                                    value="${movie.id}"
                            />
                            <input
                                    type="hidden"
                                    required
                                    name="command"
                                    value="SESSIONS_PAGE"
                            />
                            <input type="submit" value="<fmt:message key="Sessions"/>" class="sessions_btn"/>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${fn:length(requestScope.movies)==0}">
        <p class="movie_warning"><fmt:message key="NullMovie"/></p>
    </c:if>
    <div class="index_paggination">
        <c:forEach var="i" begin="0" end="${requestScope.count/requestScope.movieOnPage}">
            <c:if test="${not empty requestScope.movieName}">
            <c:if test="${i!=requestScope.currentPage}">
            <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
               class="index_paggination_link">
                    <c:out value="${i+1}"/>
                </c:if>

                <c:if test="${i==requestScope.currentPage}">
                <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
                   class="index_current_page_paggination_link">
                    <c:out value="${i+1}"/>

                </a>
                </c:if>
        </c:if>
        <c:if test="${not empty requestScope.todayMovies}">
            <c:if test="${i!=requestScope.currentPage}">
            <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
               class="index_paggination_link">
                    <c:out value="${i+1}"/>
                </c:if>

                <c:if test="${i==requestScope.currentPage}">
                <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
                   class="index_current_page_paggination_link">
                    <c:out value="${i+1}"/>

                </a>
                </c:if>
        </c:if>
        <c:if test="${empty requestScope.movieName and empty requestScope.todayMovies}">
        <c:if test="${i!=requestScope.currentPage}">
        <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
           class="index_paggination_link">
                <c:out value="${i+1}"/>
            </c:if>

            <c:if test="${i==requestScope.currentPage}">
            <a href=${pageContext.request.contextPath}/cinema?page=${i}&command=INDEX_PAGE
               class="index_current_page_paggination_link">
                <c:out value="${i+1}"/>

            </a>
            </c:if>
            </c:if>
            </c:forEach>
    </div>

</main>
</body>
</html>
