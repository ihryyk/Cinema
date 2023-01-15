<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/cinema/admin/updateSession" method="post">
    <table>
        <tr>
            <td>Start time</td>
            <td><input type="datetime-local" required name="startTime" value="${requestScope.session.startTime}"/></td>
        </tr>
        <tr>
            <td>End time</td>
            <td><input type="datetime-local" required name="endTime" value="${requestScope.session.endTime}"/></td>
        </tr>
        <tr>
            <td>Available seats</td>
            <td><input type="number" required name="availableSeats" value="${requestScope.session.availableSeats}"/></td>
        </tr>
        <tr>
            <td>Format</td>
            <td> <select name="format">
                <jsp:useBean id="formats" scope="request" type="java.util.List"/>
                <c:forEach var="format" items="${formats}">
                    <option value="${format}" selected="${requestScope.session.format}">${format}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input type="number" required name="price" value="${requestScope.session.price}"/></td>
        </tr>
        <td><input type="hidden" name="movieId" value="${requestScope.movieId}"/></td>
        <td><input type="hidden" name="sessionId" value="${requestScope.session.id}"/></td>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
