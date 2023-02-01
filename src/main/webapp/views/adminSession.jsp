<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>

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
        <form action="${pageContext.request.contextPath}/cinema" method="get">
            <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
            <input type="hidden" value="ADD_SESSION_PAGE" name="command" class="index_btn"/>
            <input type="submit" value="<fmt:message key="AddSession"/>"  class="index_btn"/>
        </form>
    </div>
    <main class="admin_movie_main">
        <c:if test="${fn:length(requestScope.sessions)!=0}">
            <div class="admin-movie_wrapper">
                <div>
                    <img src="data:image/png;base64, ${requestScope.movie.base64ImagePoster}" width="240" height="300"/>
                </div>
                <div class="admin-movie_content">
                    <p class="admin-movie_text">${requestScope.movie.movieDescriptionList.get(0).title}</p>
                    <jsp:useBean id="numberSpectators" scope="request" type="java.util.Map"/>
                    <c:forEach var="entry" items="${numberSpectators.entrySet()}">
                        <p class="admin-movie_text"> <fmt:message key="Session"/>: ${entry.getKey().startTime.getMinutes()} - ${entry.getKey().startTime.getHours()}:${entry.getKey().endTime.getMinutes()} - ${entry.getKey().endTime.getHours()}</p>
                    <p class="admin-movie_text"><fmt:message key="MovieVisiting"/>: ${entry.getValue()}</p>
                    </c:forEach>
                </div>
            </div>
            <div class="admin_movie_sessions">
                <h2 class="admin-movie_header"><fmt:message key="Sessions"/></h2>
                <div class="sessions_list">
                    <jsp:useBean id="sessions" scope="request" type="java.util.List"/>
                    <c:forEach var="session" items="${sessions}">
                        <div class="admin_movie_session">
                            <p class="session_text">${session.startTime.getHours()}:${session.startTime.getMinutes()} - ${session.endTime.getHours()}:${session.endTime.getMinutes()}</p>
                            <p class="session_text">${session.startTime.toLocalDateTime().getDayOfMonth()} ${session.startTime.toLocalDateTime().getMonth()}</p>
                            <c:choose>
                                <c:when test="${session.format == 'D3'}">
                                    <p class="session_text"><fmt:message key="Format"/>: 3D</p>
                                </c:when>
                                <c:when test="${session.format == 'D2'}">
                                    <p class="session_text"><fmt:message key="Format"/>: 2D</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="session_text"><fmt:message key="Format"/>: LUX</p>
                                </c:otherwise>
                            </c:choose>
                            <p class="session_text"><fmt:message key="AvailableSeats"/>: ${session.availableSeats}</p>
                            <p class="session_text"><fmt:message key="Price"/>: ${session.price} <fmt:message key="Money"/></p>
                            <div class="session_btns">
                                <form action="${pageContext.request.contextPath}/cinema" method="get">
                                    <input type="hidden" required name="sessionId" value="${session.id}"/>
                                    <input type="hidden" value="ADMIN_SEATS_PAGE" name="command" class="index_btn"/>
                                    <input type="submit" value="<fmt:message key="Seats"/>" class="session_btn"/>
                                </form>
                                <form action="${pageContext.request.contextPath}/cinema" method="get">
                                    <input type="hidden" required name="sessionId" value="${session.id}"/>
                                    <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
                                    <input type="hidden" value="UPDATE_SESSION_PAGE" name="command" class="index_btn"/>
                                    <input type="submit" value="<fmt:message key="Update"/>" class="session_btn"/>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.sessions)==0}">
            <p class="session_text_no"><fmt:message key="NullSessions"/></p>
        </c:if>
    </main>
</body>
</html>

