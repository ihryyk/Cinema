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
<form action="/cinema/admin/addSession" method="post">
    <table>
        <tr>
            <td>Start time</td>
            <td><input type="datetime-local" required name="startTime"/></td>
        </tr>
        <tr>
            <td>End time</td>
            <td><input type="datetime-local" required name="endTime"/></td>
        </tr>
        <tr>
            <td>Available seats</td>
            <td><input type="number" required name="availableSeats"/></td>
        </tr>
        <tr>
            <td>Format</td>
            <td> <select name="format">
                <jsp:useBean id="formats" scope="request" type="java.util.List"/>
                <c:forEach var="format" items="${formats}">
                    <option value="${format}">${format}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td>Price</td>
            <td><input type="number" required name="price"/></td>
        </tr>
        <td><input type="hidden" name="movieId" value="${requestScope.movieId}"/></td>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
