<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 08.01.2023
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <title>Insert title here</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <%--    <link rel="stylesheet" type="text/css" href="styles/index.css">--%>
    <%--    <style>--%>
    <%--        <%@include file="styles/index.css"%>--%>
    <%--    </style>--%>
    <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<c:if test="${not empty sessionScope.popUps}">
    <script>
        Swal.fire({
            icon: 'error',
            title: "Exception",
            text: '${sessionScope.popUps}'
        })
    </script>
    ${sessionScope.remove("popUps")}
</c:if>
<form action="/cinema/login" method="post">
    <table>
        <tr>
            <td>Email address</td>
            <td><input type="text" required name="email"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" required name="password"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
