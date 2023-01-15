<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 08.01.2023
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
<%--    <form method="post" action="/cinema/admin/saveMovie" enctype="multipart/form-data">--%>
<%--        <input type="file" name="poster"/>--%>
<%--        <input type="submit" value="Upload"/>--%>
<%--    </form>--%>
    <form action="${pageContext.request.contextPath}/cinema/admin/addMovie" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Original name</td>
                <td><input type="text" required name="originalName"/></td>
            </tr>
            <tr>
                <td>Release date</td>
                <td><input type="datetime-local" required name="releaseDate"/></td>
            </tr>
            <tr>
                <td>Available age"</td>
                <td><input type="number" required name="availableAge"/></td>
            </tr>
            availableAge"
            <tr>
                <td>Poster</td>
                <td><input type="file" name="poster"/></td>
            </tr>
            <jsp:useBean id="languages" scope="request" type="java.util.List"/>
            <c:forEach var="language" items="${languages}">
                <tr>
                    <td>${language.name} title</td>
                    <td><input type="Text" required name="${language.name}Title"/></td>
                </tr>
                <tr>
                    <td>${language.name} director</td>
                    <td><input type="Text" required name="${language.name}Director"/></td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="Submit"/>
    </form>
</div>
</body>
</html>
