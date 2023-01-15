<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 13.01.2023
  Time: 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>${requestScope.session.movie.movieDescriptionList.get(0).title}</p>
<img src="data:image/png;base64, ${requestScope.session.movie.base64ImagePoster}" width="240" height="300"/>
<p>${requestScope.session.startTime.getHours()}:${requestScope.session.startTime.getMinutes()}- ${requestScope.session.endTime.getHours()}:${requestScope.session.endTime.getMinutes()}</p>
<p>${requestScope.session.startTime.toLocalDateTime().getDayOfMonth()} ${requestScope.session.startTime.toLocalDateTime().getMonth()}</p>
<p>Format: ${requestScope.session.format}</p>
<p>Available seats: ${requestScope.session.availableSeats} </p>
<p>Price: ${requestScope.session.price}</p>
<jsp:useBean id="busySeats" scope="request" type="java.util.List"/>
<c:forEach var="seat" items="${busySeats}">
<%--        червоним--%>
    <p>${seat.row}/${seat.number}</p>
</c:forEach>
<jsp:useBean id="freeSeats" scope="request" type="java.util.List"/>
<c:forEach var="seat" items="${freeSeats}">
        <%--       зеленим--%>
        <p>${seat.row}/${seat.number}</p>
</c:forEach>
</body>
</html>
