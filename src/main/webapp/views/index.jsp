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
    <%--    <link rel="stylesheet" type="text/css" href="styles/index.css">--%>
    <%--    <style>--%>
    <%--        <%@include file="styles/index.css"%>--%>
    <%--    </style>--%>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<c:if test="${not empty sessionScope.popUps}">
    <script>
        Swal.fire({
            icon: 'ok',
            title: "Exception",
            text: '${sessionScope.popUps}'
        })
    </script>
    ${sessionScope.remove("popUps")}
</c:if>
<c:if test="${not empty sessionScope.user}">
    ${sessionScope.user.firstName}  ${sessionScope.user.lastName}
    <a href=${pageContext.request.contextPath}/cinema/user/tickets>
        Tickets
    </a>
    <a href=${pageContext.request.contextPath}/cinema/user/profile>
       Profile
    </a>
</c:if>
<c:if test="${empty sessionScope.user}">
    <a href=${pageContext.request.contextPath}/cinema/login>
        Login
    </a>
</c:if>
<div>
    <form action="${pageContext.request.contextPath}/cinema" method="get">
        <input type="text" placeholder="Movie name" required name="movieName">
        <input type="submit" value="search" class="btn_submit"/>
    </form>
</div>
<jsp:useBean id="movies" scope="request" type="java.util.List"/>
<c:forEach var="movie" items="${movies}">
    <p>Original name: ${movie.originalName}</p>
    <img src="data:image/png;base64, ${movie.base64ImagePoster}" width="240" height="300"/>
    <p>Age: ${movie.availableAge}+</p>
    <p>Title: ${movie.movieDescriptionList.get(0).title}</p>
    <p>Director ${movie.movieDescriptionList.get(0).director}</p>
    <form action="${pageContext.request.contextPath}/cinema/movie/session"  method="get">
        <input type="hidden" required name="movieId" value="${movie.id}"/>
        <input type="submit" value="sessions" />
    </form>
</c:forEach>
</body>
</html>
