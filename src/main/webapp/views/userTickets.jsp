<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/userTickets.css" />
    <title>Title</title>
	<style>
		<%@include file="styles/index.css"%>
		<%@include file="styles/userTickets.css"%>
	</style>
</head>
<body>
    <c:if test="${fn:length(requestScope.tickets)>0}">
			<div class="tickets_list">
				<jsp:useBean id="tickets" scope="request" type="java.util.List" />
				<c:forEach var="ticket" items="${tickets}">
					<div class="ticket">
						<p>
							<fmt:message key="Movie"/>:
							${ticket.purchasedSeat.session.movie.movieDescriptionList.get(0).title}
						</p>
						<p>
							<fmt:message key="Time"/>:
							${ticket.purchasedSeat.session.startTime.getHours()}:${ticket.purchasedSeat.session.startTime.getMinutes()}-
							${ticket.purchasedSeat.session.endTime.getHours()}:${ticket.purchasedSeat.session.endTime.getMinutes()}
						</p>
						<p>
							<fmt:message key="Date"/>:
							${ticket.purchasedSeat.session.startTime.toLocalDateTime().getDayOfMonth()}
							${ticket.purchasedSeat.session.startTime.toLocalDateTime().getMonth()}
						</p>
						<p><fmt:message key="Format"/>: ${ticket.purchasedSeat.session.format}</p>
						<p>
							<fmt:message key="AvailableSeats"/>: ${ticket.purchasedSeat.session.availableSeats}
						</p>
						<p><fmt:message key="Price"/>: ${ticket.purchasedSeat.session.price} <fmt:message key="Money"/></p>
						<p>
							<fmt:message key="PhoneNumber"/>: <fmt:message key="Row"/>: ${ticket.purchasedSeat.seat.row} - <fmt:message key="Number"/>:
							${ticket.purchasedSeat.seat.number}
						</p>
						<c:if test="${ticket.purchasedSeat.session.movie.deleted==true}">
							<span class="canceled"><fmt:message key="CANCELLED"/></span>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:if>
        <c:if test="${fn:length(requestScope.sessions)==0}">
            <p class="session_warning"><fmt:message key="NullSessions"/></p>
        </c:if>
</body>
</html>

