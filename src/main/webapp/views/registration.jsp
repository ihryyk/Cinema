<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.pageLanguage}"/>
<fmt:setBundle basename="message"/>
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
        <h1 class="auth-wrapper_header"><fmt:message key="Registration"/></h1>
        <form
            action="${pageContext.request.contextPath}/cinema?command=REGISTER"
            method="post"
            class="auth-wrapper__form"
        >
            <div class="form_controllers">
                <div>
                    <label for="login_email" class="form-label"><fmt:message key="EmailAddress"/></label>
                    <input
                        type="text"
                        required
                        name="email"
                        pattern="^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$"
                        title="<fmt:message key="TitleEmail"/>"
                        id="login_email"
                        class="form_input"
                        placeholder="Email"
                    />
                </div>
                <div>
                    <label for="phone" class="form-label"><fmt:message key="PhoneNumber"/></label>
                    <input
                        type="text"
                        required
                        name="phoneNumber"
                        pattern="^\+?3?8?(0\d{9})$"
                        title="<fmt:message key="TitlePhoneNumber"/>"
                        placeholder="Phone Number"
                        id="phone"
                        class="form_input"
                    />
                </div>
            </div>
            <div class="form_controllers">
                <div>
                    <label for="firstName" class="form-label"><fmt:message key="FirstName"/></label>
                    <input
                        type="text"
                        required
                        name="firstName"
                        pattern="^[A-Za-zА-Яа-яёЁЇїІіЄєҐґ]+$"
                        title="<fmt:message key="TitleName"/>"
                        placeholder="First name"
                        id="firstName"
                        class="form_input"
                    />
                </div>
                <div>
                    <label for="firstName" class="form-label"><fmt:message key="LastName"/></label>
                    <input
                        type="text"
                        required
                        name="lastName"
                        pattern="^[A-Za-zА-Яа-яёЁЇїІіЄєҐґ]+$"
                        title="<fmt:message key="TitleName"/>"
                        placeholder="Last name"
                        id="lastName"
                        class="form_input"
                    />
                </div>
            </div>
            <div class="form_block">
                <label for="login_password" class="form-label"><fmt:message key="Password"/></label>
                <input
                    type="password"
                    required
                    name="password"
                    pattern="^([a-zA-Z0-9@*#]{4,10})$"
                    title="<fmt:message key="TitlePassword"/>"
                    id="login_password"
                    class="form_input"
                    placeholder="Password"
                />
            </div>
            <input type="submit" value="Submit" class="submit_btn" />
        </form>
        <a href="${pageContext.request.contextPath}/cinema?command=LOG_IN_PAGE" class="auth-form__link"
            ><fmt:message key="HaveAccount"/></a
        >
    </div>
</body>
</html>
