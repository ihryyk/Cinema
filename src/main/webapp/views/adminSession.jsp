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
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/cinema/admin/addSession" method="get">
    <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
    <input type="submit" value="Add session"/>
</form>
<c:if test="${fn:length(requestScope.sessions)>0}">
    <p>Visiting the movie: ${requestScope.numberSpectators}</p>
    <p>${requestScope.sessions.get(0).movie.movieDescriptionList.get(0).title}</p>
    <img src="data:image/png;base64, ${requestScope.sessions.get(0).movie.base64ImagePoster}" width="240" height="300"/>
    <jsp:useBean id="sessions" scope="request" type="java.util.List"/>
    <c:forEach var="session" items="${sessions}">
        <p>${session.startTime.getHours()}:${session.startTime.getMinutes()}
            - ${session.endTime.getHours()}:${session.endTime.getMinutes()}</p>
        <p>${session.startTime.toLocalDateTime().getDayOfMonth()} ${session.startTime.toLocalDateTime().getMonth()}</p>
        <p>Format: ${session.format}</p>
        <p>Available seats: ${session.availableSeats} </p>
        <p>Price: ${session.price}</p>
        <form action="${pageContext.request.contextPath}/cinema/admin/seat" method="get">
            <input type="hidden" required name="sessionId" value="${session.id}"/>
            <input type="submit" value="Seats"/>
        </form>
        <form action="${pageContext.request.contextPath}/cinema/admin/updateSession" method="get">
            <input type="hidden" required name="sessionId" value="${session.id}"/>
            <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
            <input type="submit" value="Update session"/>
        </form>
    </c:forEach>
</c:if>
<c:if test="${fn:length(requestScope.sessions)==0}">
<p>There are no sessions yet</p>
</c:if>
</body>
</html>
