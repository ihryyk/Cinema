<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css"/>
    <link rel="stylesheet" href="../styles/session.css"/>
    <title>Title</title>
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/session.css"%>
    </style>
</head>
<body>
<main class="session_main">
    <c:if test="${fn:length(requestScope.sessions)!=0}">
        <div class="sort_wrapper">
            <form
                    action="${pageContext.request.contextPath}/cinema"
                    method="get"
                    class="sort_form"
            >
                <div class="sort_controller">
                    <input type="hidden" required name="command" value="SESSIONS_PAGE"/>
                    <input
                            type="radio"
                            id="contactChoice2"
                            name="groupBy"
                            value="start_time"
                            class="sort_input"
                    />
                    <label for="contactChoice2"> <fmt:message key="Date"/> </label>
                </div>
                <div class="sort_controller">
                    <input type="radio" id="contactChoice3" name="groupBy" value="available_seats" class="sort_input">
                    <input type="hidden" required name="movieId" value="${requestScope.movieId}"/>
                    <label for="contactChoice3">
                        <fmt:message key="AvailableSeats"/>
                    </label>
                </div>
                <input type="submit" value=" <fmt:message key="Sort"/>" class="sort_btn"/>
            </form>
        </div>

        <div class="session_wrapper">
            <div class="session_movie">
                <div>
                    <img src="data:image/png;base64, ${requestScope.movie.base64ImagePoster}" width="240" height="300"/>
                </div>
                <p class="session_movie_text">${requestScope.movie.movieDescriptionList.get(0).title}</p>
            </div>

            <div class="sessions_container">
                <jsp:useBean id="sessions" scope="request" type="java.util.List"/>
                <c:forEach var="session" items="${sessions}">
                    <div class="session">
                        <div class="session_content">
                            <p class="session_text">${session.startTime.getHours()}:${session.startTime.getMinutes()}
                                - ${session.endTime.getHours()}:${session.endTime.getMinutes()}</p>
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
                            <p class="session_text"><fmt:message key="Price"/>: ${session.price} <fmt:message
                                    key="Money"/></p>
                                <%--                                <c:if test="${session.startTime < }">--%>
                            <form action="${pageContext.request.contextPath}/cinema" method="get">
                                <input type="hidden" required name="sessionId" value="${session.id}"/>
                                <input type="hidden" required name="command" value="SEATS_PAGE"/>
                                <input type="submit" value="<fmt:message key="Seats"/>" class="session_btn"/>
                            </form>
                                <%--                                </c:if>--%>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>
    <c:if test="${fn:length(requestScope.sessions)==0}">
        <p class="session_warning"><fmt:message key="NullSessions"/></p>
    </c:if>
</main>
</body>
</html>
