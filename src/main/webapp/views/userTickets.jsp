<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
							Movie:
							${ticket.purchasedSeat.session.movie.movieDescriptionList.get(0).title}
						</p>
						<p>
							Time:
							${ticket.purchasedSeat.session.startTime.getHours()}:${ticket.purchasedSeat.session.startTime.getMinutes()}-
							${ticket.purchasedSeat.session.endTime.getHours()}:${ticket.purchasedSeat.session.endTime.getMinutes()}
						</p>
						<p>
							Date:
							${ticket.purchasedSeat.session.startTime.toLocalDateTime().getDayOfMonth()}
							${ticket.purchasedSeat.session.startTime.toLocalDateTime().getMonth()}
						</p>
						<p>Format: ${ticket.purchasedSeat.session.format}</p>
						<p>
							Available seats: ${ticket.purchasedSeat.session.availableSeats}
						</p>
						<p>Price: ${ticket.purchasedSeat.session.price}</p>
						<p>
							Place: Row: ${ticket.purchasedSeat.seat.row} - Number:
							${ticket.purchasedSeat.seat.number}
						</p>
						<c:if test="${ticket.purchasedSeat.session.movie.deleted==true}">
							<span class="canceled">CANCELED</span>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:if>
        <c:if test="${fn:length(requestScope.sessions)==0}">
            <p class="session_warning">There are no sessions yet</p>
        </c:if>
</body>
</html>

