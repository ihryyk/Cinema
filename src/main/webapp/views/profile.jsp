<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 11.01.2023
  Time: 21:11
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
<c:if test="${not empty sessionScope.popUpsError}">
    <script>
        Swal.fire({
            icon: 'error',
            title: "Exception",
            text: '${sessionScope.popUpsError}'
        })
    </script>
    ${sessionScope.remove("popUpsError")}
</c:if>

<p>Contact information</p>
<form action="${pageContext.request.contextPath}/cinema/user/profile/email" method="post">
    <table>
          <input type="hidden" required name="id" value="${sessionScope.user.id}"/>
        <tr>
            <td>Email address</td>
            <td><input type="text" required name="email" value="${sessionScope.user.emailAddress}"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
<form action="${pageContext.request.contextPath}/cinema/user/profile/phoneNumber" method="post">
    <table>
       <input type="hidden" required name="id" value="${sessionScope.user.id}"/>
        <tr>
            <td>Phone number</td>
            <td><input type="text" required name="phoneNumber" value="${sessionScope.user.phoneNumber}"/></td>
        </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
<p>Personal information</p>
<form action="${pageContext.request.contextPath}/cinema/user/profile/personalInformation" method="post">
    <table>
       <input type="hidden" required name="id" value="${sessionScope.user.id}"/>
    <tr>
        <td>Password</td>
        <td><input type="password" required name="password" value="${sessionScope.user.password}"/></td>
    </tr>
    <tr>
        <td>First name</td>
        <td><input type="text" required name="firstName" value="${sessionScope.user.firstName}"/></td>
    </tr>
    <tr>
        <td>Last name</td>
        <td><input type="text" required name="lastName" value="${sessionScope.user.lastName}"/></td>
    </tr>
    </table>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
