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
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/profile.css" />
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/profile.css"%>
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

    <div class="profile_wrapper">
        <div>
            <h2 class="profile_header">Contact information</h2>
            <div class="form_section">
                <form
                    action="${pageContext.request.contextPath}/cinema?command=UPDATE_EMAIL"
                    method="post"
                >
                    <input
                        type="hidden"
                        required
                        name="id"
                        value="${sessionScope.user.id}"
                    />
                    <div class="profile_controller">
                        <label for="email"><fmt:message key="EmailAddress"/></label>
                        <input
                            type="text"
                            required
                            name="email"
                            id="email"
                            value="${sessionScope.user.emailAddress}"
                            pattern="^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$"
                            title="<fmt:message key="TitleEmail"/>"
                            class="profile_input"
                        />
                    </div>
                    <input type="submit" value="<fmt:message key="Submit"/>" class="profile_btn" />
                </form>
                <form
                    action="${pageContext.request.contextPath}/cinema?command=UPDATE_PHONE_NUMBER"
                    method="post"
                >
                    <input
                        type="hidden"
                        required
                        name="id"
                        value="${sessionScope.user.id}"
                        id=""
                    />
                    <div class="profile_controller">
                        <label for="phoneNumber"><fmt:message key="PhoneNumber"/></label>
                        <input
                            type="text"
                            required
                            name="phoneNumber"
                            pattern="^\+?3?8?(0\d{9})$"
                            title="<fmt:message key="TitlePhoneNumber"/>"
                            id="phoneNumber"
                            value="${sessionScope.user.phoneNumber}"
                            class="profile_input"
                        />
                    </div>
                    <input type="submit" value="<fmt:message key="Submit"/>" class="profile_btn" />
                </form>
            </div>
        </div>
        <div>
            <h2 class="profile_header"><fmt:message key="PersonalInformation"/></h2>
            <form
                action="${pageContext.request.contextPath}/cinema?command=UPDATE_PERSONAL_INFORMATION"
                method="post"
                class="profile_form"
            >
                <input
                    type="hidden"
                    required
                    name="id"
                    value="${sessionScope.user.id}"
                />
                <div class="profile_controller">
                    <label for="firstName"><fmt:message key="FirstName"/></label>
                    <input
                        type="text"
                        required
                        name="firstName"
                        pattern="^[A-Za-zА-Яа-яёЁЇїІіЄєҐґ]+$"
                        title="<fmt:message key="TitleName"/>"
                        id="firstName"
                        value="${sessionScope.user.firstName}"
                        class="profile_input"
                    />
                </div>
                <div class="profile_controller">
                    <label for="lastName"><fmt:message key="LastName"/></label>
                    <input
                        type="text"
                        required
                        name="lastName"
                        pattern="^[A-Za-zА-Яа-яёЁЇїІіЄєҐґ]+$"
                        title="<fmt:message key="TitleName"/>"
                        id="lastName"
                        value="${sessionScope.user.lastName}"
                        class="profile_input"
                    />
                </div>
                <div class="profile_controller">
                    <label for="password"><fmt:message key="Password"/></label>
                    <input
                        type="password"
                        required
                        name="password"
                        pattern="^([a-zA-Z0-9@*#]{4,10})$"
                        title="<fmt:message key="TitlePassword"/>"
                        id="password"
                        value="${sessionScope.user.password}"
                        class="profile_input"
                    />
                    <input type="submit" value="Submit" class="profile_btn last_btn" />
                </div>
                <div class="profile_controller"></div>
            </form>
        </div>
	</div>
</body>
</html>
