<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 09.01.2023
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <form action="${pageContext.request.contextPath}/cinema/movie/session" method="get">
        <input type="radio" id="contactChoice2" name="groupBy" value="start_time">
        <label for="contactChoice2">
            Date
        </label>
        <input type="radio" id="contactChoice3" name="groupBy" value="available_seats">
        <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
        <label for="contactChoice2">
            Available seats
        </label>
        <input type="submit" value="Sort"/>
    </form>
</div>
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
    <form action="${pageContext.request.contextPath}/cinema/user/seat" method="get">
        <input type="hidden" required name="sessionId" value="${session.id}"/>
        <input type="submit" value="Seats"/>
    </form>
</c:forEach>
</body>
</html>
