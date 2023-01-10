<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 10.01.2023
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:useBean id="tickets" scope="request" type="java.util.List"/>
<c:forEach var="ticket" items="${tickets}">
    <p>Movie: ${ticket.purchasedSeat.session.movie.movieDescriptionList.get(0).title}</p>
    <p>
        Time: ${ticket.purchasedSeat.session.startTime.getHours()}:${ticket.purchasedSeat.session.startTime.getMinutes()}- ${ticket.purchasedSeat.session.endTime.getHours()}:${ticket.purchasedSeat.session.endTime.getMinutes()}</p>
    <p>
        Date: ${ticket.purchasedSeat.session.startTime.toLocalDateTime().getDayOfMonth()} ${ticket.purchasedSeat.session.startTime.toLocalDateTime().getMonth()}</p>
    <p>Format: ${ticket.purchasedSeat.session.format}</p>
    <p>Available seats: ${ticket.purchasedSeat.session.availableSeats} </p>
    <p>Price: ${ticket.purchasedSeat.session.price}</p>
    <p>Place: Row: ${ticket.purchasedSeat.seat.row} - Number: ${ticket.purchasedSeat.seat.number}</p>
</c:forEach>
</body>
</html>
