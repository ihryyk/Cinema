<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
    <link rel="stylesheet" href="../styles/adminMovie.css">
    <title>Title</title>
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/adminMovie.css"%>
    </style>
</head>
<body>
    <div class="index_btns admin_movie_btn">
        <form action="${pageContext.request.contextPath}/cinema/admin/addSession" method="get">
            <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
            <input type="submit" value="Add session"  class="index_btn"/>
        </form>
    </div>
    <main class="admin_movie_main">
        <c:if test="${fn:length(requestScope.sessions)>0}">
            <div class="admin-movie_wrapper">
                <div>
                    <img src="data:image/png;base64, ${requestScope.sessions.get(0).movie.base64ImagePoster}" width="240" height="300"/>
                </div>
                <div class="admin-movie_content">
                    <p class="admin-movie_text">${requestScope.sessions.get(0).movie.movieDescriptionList.get(0).title}</p>
                    <p class="admin-movie_text">Visiting the movie: ${requestScope.numberSpectators}</p>
                </div>
            </div>
            <div class="admin_movie_sessions">
                <h2 class="admin-movie_header">Sessions</h2>
                <div class="sessions_list">
                    <jsp:useBean id="sessions" scope="request" type="java.util.List"/>
                    <c:forEach var="session" items="${sessions}">
                        <div class="admin_movie_session">
                            <p class="session_text">${session.startTime.getHours()}:${session.startTime.getMinutes()} - ${session.endTime.getHours()}:${session.endTime.getMinutes()}</p>
                            <p class="session_text">${session.startTime.toLocalDateTime().getDayOfMonth()} ${session.startTime.toLocalDateTime().getMonth()}</p>
                            <p class="session_text">Format: ${session.format}</p>
                            <p class="session_text">Available seats: ${session.availableSeats}</p>
                            <p class="session_text">Price: ${session.price}</p>
                            <div class="session_btns">
                                <form action="${pageContext.request.contextPath}/cinema/admin/seat" method="get">
                                    <input type="hidden" required name="sessionId" value="${session.id}"/>
                                    <input type="submit" value="Seats" class="session_btn"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/cinema/admin/updateSession" method="get">
                                    <input type="hidden" required name="sessionId" value="${session.id}"/>
                                    <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
                                    <input type="submit" value="Update session" class="session_btn"/>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.sessions)==0}">
            <p class="session_text_no">There are no sessions yet</p>
        </c:if>
    </main>
</body>
</html>

