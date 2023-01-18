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
                    action="${pageContext.request.contextPath}/cinema/user/profile/email"
                    method="post"
                >
                    <input
                        type="hidden"
                        required
                        name="id"
                        value="${sessionScope.user.id}"
                    />
                    <div class="profile_controller">
                        <label for="email">Email address</label>
                        <input
                            type="text"
                            required
                            name="email"
                            id="email"
                            value="${sessionScope.user.emailAddress}"
                            class="profile_input"
                        />
                    </div>
                    <input type="submit" value="Submit" class="profile_btn" />
                </form>
                <form
                    action="${pageContext.request.contextPath}/cinema/user/profile/phoneNumber"
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
                        <label for="phoneNumber">Phone number</label>
                        <input
                            type="text"
                            required
                            name="phoneNumber"
                            id="phoneNumber"
                            value="${sessionScope.user.phoneNumber}"
                            class="profile_input"
                        />
                    </div>
                    <input type="submit" value="Submit" class="profile_btn" />
                </form>
            </div>
        </div>
        <div>
            <h2 class="profile_header">Personal information</h2>
            <form
                action="${pageContext.request.contextPath}/cinema/user/profile/personalInformation"
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
                    <label for="firstName">First name</label>
                    <input
                        type="text"
                        required
                        name="firstName"
                        id="firstName"
                        value="${sessionScope.user.firstName}"
                        class="profile_input"
                    />
                </div>
                <div class="profile_controller">
                    <label for="lastName">Last name</label>
                    <input
                        type="text"
                        required
                        name="lastName"
                        id="lastName"
                        value="${sessionScope.user.lastName}"
                        class="profile_input"
                    />
                </div>
                <div class="profile_controller">
                    <label for="password">Password</label>
                    <input
                        type="password"
                        required
                        name="password"
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
