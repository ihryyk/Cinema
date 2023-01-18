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
    <title>Title</title>
    <meta charset="utf-8">
    <title>Insert title here</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../styles/auth.css" />
	<link rel="stylesheet" href="../styles/index.css" />
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
    <div class="auth-wrapper_reg">
        <h1 class="auth-wrapper_header">Create an account</h1>
        <form
            action="/cinema/registration"
            method="post"
            class="auth-wrapper__form"
        >
            <div class="form_controllers">
                <div>
                    <label for="login_email" class="form-label">Email address</label>
                    <input
                        type="text"
                        required
                        name="email"
                        id="login_email"
                        class="form_input"
                        placeholder="Email"
                    />
                </div>
                <div>
                    <label for="phone" class="form-label">Phone number</label>
                    <input
                        type="text"
                        required
                        name="phoneNumber"
                        placeholder="Phone Number"
                        id="phone"
                        class="form_input"
                    />
                </div>
            </div>
            <div class="form_controllers">
                <div>
                    <label for="firstName" class="form-label">First name</label>
                    <input
                        type="text"
                        required
                        name="phoneNumber"
                        placeholder="First name"
                        id="firstName"
                        class="form_input"
                    />
                </div>
                <div>
                    <label for="firstName" class="form-label">Last name</label>
                    <input
                        type="text"
                        required
                        name="lastName"
                        placeholder="Last name"
                        id="lastName"
                        class="form_input"
                    />
                </div>
            </div>
            <div class="form_block">
                <label for="login_password" class="form-label">Password</label>
                <input
                    type="password"
                    required
                    name="password"
                    id="login_password"
                    class="form_input"
                    placeholder="Password"
                />
            </div>
            <input type="submit" value="Submit" class="submit_btn" />
        </form>
        <a href="/cinema/login" class="auth-form__link"
            >Do you have an account?</a
        >
    </div>
</body>
</html>
