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
    <link rel="stylesheet" href="../styles/auth.css">
    <link rel="stylesheet" href="../styles/index.css">
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/auth.css"%>
    </style>
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
    <div class="auth-wrapper">
        <h1 class="auth-wrapper_header">Login</h1>
        <form action="/cinema/login" method="post" class="auth-wrapper__form">
            <div class="form_block">
                <label for="login_email" class="form-label">Email address</label>
                <input type="text" required name="email" id="login_email"  class="form_input" placeholder="Email">
            </div>
            <div class="form_block">
                <label for="login_password" class="form-label">Password</label>
                <input type="password" required name="password" id="login_password" class="form_input" placeholder="Password"/>
            </div>
            <input type="submit" value="Submit" class="submit_btn"/>
        </form>
        <a href="/cinema/registration" class="auth-form__link">Dont have an account? Sign up</a>
	</div>
</body>
</html>
