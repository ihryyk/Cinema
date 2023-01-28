<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
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
                <fmt:message key="Recipient"/>: ${sessionScope.user.firstName} ${sessionScope.user.lastName}
            </p>
            <p> <fmt:message key="PhoneNumber"/>: ${sessionScope.user.phoneNumber}</p>
            <p>
                <fmt:message key="Movie"/>: ${requestScope.session.movie.movieDescriptionList.get(0).title}
            </p>
            <p>
                <fmt:message key="Time"/>:
                ${requestScope.session.startTime.getHours()}:${requestScope.session.startTime.getMinutes()}
                -
                ${requestScope.session.endTime.getHours()}:${requestScope.session.endTime.getMinutes()}
            </p>
            <p>
                <fmt:message key="Date"/>:
                ${requestScope.session.startTime.toLocalDateTime().getDayOfMonth()}
                ${requestScope.session.startTime.toLocalDateTime().getMonth()}
            </p>
            <p> <fmt:message key="Format"/>: ${requestScope.session.format}</p>
            <p> <fmt:message key="AvailableSeats"/>: ${requestScope.session.availableSeats}</p>
            <p><fmt:message key="Price"/>: ${requestScope.session.price} <fmt:message key="Money"/></p>
            <p>
                <fmt:message key="Place"/>:   <fmt:message key="Row"/>: ${requestScope.seat.row} -   <fmt:message key="Number"/>:
                ${requestScope.seat.number}
            </p>
            <form
                action="${pageContext.request.contextPath}/cinema?command=ACCEPT_TICKET"
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
                <input type="submit" value=" <fmt:message key="Submit"/>" class="ticket_btn" />
            </form>
        </div>
    </div>
</body>
</html>
