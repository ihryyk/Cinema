<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 04.01.2023
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Library</title>
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
<div>
    <h1>Registration form</h1>
    <form action="/cinema/registration" method="post">
        <table>
            <tr>
                <td>Email address</td>
                <td><input type="text" required name="email" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" required name="password"/></td>
            </tr>
            <tr>
                <td>First name</td>
                <td><input type="text" required name="firstName"/> </td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><input type="text" required name="lastName"/></td>
            </tr>
            <tr>
                <td>Phone number</td>
                <td><input type="text" required name="phoneNumber"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/>
    </form>
</div>
<a href="/cinema/login">Do you have an account?</a>
</body>
</html>
