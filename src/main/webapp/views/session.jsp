<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/session.css" />
    <title>Title</title>
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/session.css"%>
    </style>
</head>
<body>
    <main class="session_main">
        <c:if test="${fn:length(requestScope.sessions)>0}">
            <div class="sort_wrapper">
                <form
                    action="${pageContext.request.contextPath}/cinema/movie/session"
                    method="get"
                    class="sort_form"
                >
                    <div class="sort_controller">
                        <input
                            type="radio"
                            id="contactChoice2"
                            name="groupBy"
                            value="start_time"
                            class="sort_input"
                        />
                        <label for="contactChoice2"> Date </label>
                    </div>
                    <div class="sort_controller">
                        <input type="radio" id="contactChoice3" name="groupBy" value="available_seats" class="sort_input">
                        <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
                        <label for="contactChoice3">
                            Available seats
                        </label>
                    </div>
                    <input type="submit" value="Sort" class="sort_btn"/>
                </form>
            </div>

            <div class="session_wrapper">
                <div class="session_movie">
                   <div>
                        <img src="data:image/png;base64, ${requestScope.sessions.get(0).movie.base64ImagePoster}" width="240" height="300"/>
                   </div>
                   <p class="session_movie_text">${requestScope.sessions.get(0).movie.movieDescriptionList.get(0).title}</p>
                </div>

                <div class="sessions_container">
                    <jsp:useBean id="sessions" scope="request" type="java.util.List"/>
                    <c:forEach var="session" items="${sessions}">
                        <div class="session">
                            <div class="session_content">
                                <p class="session_text">${session.startTime.toLocalDateTime().getDayOfMonth()} ${session.startTime.toLocalDateTime().getMonth()}</p>
                                <p class="session_text">Format: ${session.format}</p>
                                <p class="session_text">Available seats: ${session.availableSeats}</p>
                                <p class="session_text">Price: ${session.price}</p>
                                <form action="${pageContext.request.contextPath}/cinema/movie/session/seat" method="get">
                                    <input type="hidden" required name="sessionId" value="${session.id}"/>
                                    <input type="submit" value="Seats" class="session_btn"/>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.sessions)==0}">
            <p class="session_warning">There are no sessions yet</p>
        </c:if>
    </main>
</body>
</html>
