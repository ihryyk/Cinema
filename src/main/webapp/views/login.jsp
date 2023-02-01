<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
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
            title: "<fmt:message key="Exception"/>",
            text: '<fmt:message key="${sessionScope.popUpsError}"/>'
        })
    </script>
    ${sessionScope.remove("popUpsError")}
</c:if>
<div class="auth-wrapper">
    <h1 class="auth-wrapper_header"><fmt:message key="Login"/></h1>
    <form action="${pageContext.request.contextPath}/cinema?command=LOG_IN" method="post" class="auth-wrapper__form">
        <div class="form_block">
            <label for="login_email" class="form-label"><fmt:message key="EmailAddress"/></label>
            <input type="text" required name="email" id="login_email"
                   pattern="^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$"
                   title="<fmt:message key="TitleEmail"/>"
                   class="form_input" placeholder="<fmt:message key="EmailAddress"/>">
        </div>
        <div class="form_block">
            <label for="login_password" class="form-label"><fmt:message key="Password"/></label>
            <input type="password" required name="password" id="login_password" class="form_input"
                   placeholder="Password"
                   pattern="^([a-zA-Z0-9@*#]{4,10})$"
                   title="<fmt:message key="TitlePassword"/>"/>

        </div>
        <input type="submit" value="<fmt:message key="Submit"/>" class="submit_btn"/>
    </form>
    <a href="/cinema?command=REGISTER_PAGE" class="auth-form__link"><fmt:message key="DontHaveAccount"/></a>
</div>
</body>
</html>
