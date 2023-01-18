<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 10.01.2023
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/ticket.css" />
    <title>Title</title>
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/ticket.css"%>
    </style>
</head>
<body>
    <div class="ticket_wrapper">
        <div class="ticket_content">
            <p>
                Name: ${sessionScope.user.firstName} ${sessionScope.user.lastName}
            </p>
            <p>Phone number: ${sessionScope.user.phoneNumber}</p>
            <p>
                Movie: ${requestScope.session.movie.movieDescriptionList.get(0).title}
            </p>
            <p>
                Time:
                ${requestScope.session.startTime.getHours()}:${requestScope.session.startTime.getMinutes()}
                -
                ${requestScope.session.endTime.getHours()}:${requestScope.session.endTime.getMinutes()}
            </p>
            <p>
                Date:
                ${requestScope.session.startTime.toLocalDateTime().getDayOfMonth()}
                ${requestScope.session.startTime.toLocalDateTime().getMonth()}
            </p>
            <p>Format: ${requestScope.session.format}</p>
            <p>Available seats: ${requestScope.session.availableSeats}</p>
            <p>Price: ${requestScope.session.price}</p>
            <p>
                Place: Row: ${requestScope.seat.row} - Number:
                ${requestScope.seat.number}
            </p>
            <form
                action="${pageContext.request.contextPath}/cinema/user/ticket"
                method="post"
            >
                <input
                    type="hidden"
                    required
                    name="seatId"
                    value="${requestScope.seat.id}"
                />
                <input
                    type="hidden"
                    required
                    name="sessionId"
                    value="${requestScope.session.id}"
                />
                <input type="submit" value="Accept" class="ticket_btn" />
            </form>
        </div>
    </div>
</body>
</html>
