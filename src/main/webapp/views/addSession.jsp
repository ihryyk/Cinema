<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../styles/index.css" />
	<link rel="stylesheet" href="../styles/addForm.css" />
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="styles/index.css">
    <style>
        <%@include file="styles/index.css"%>
        <%@include file="styles/addForm.css"%>
    </style>
</head>
<body>
    <main class="index_main">
        <div class="add_form_wrapper">
            <h1 class="add_form_header">Add Session</h1>
            <form action="/cinema/admin/addSession" method="post" class="add_form">
                <div class="add_form_controllers">
                    <div class="add_form_controller">
                        <label for="startTime" class="add_form_label">Start time</label>
                        <input
                            type="datetime-local"
                            required
                            name="startTime"
                            id="startTime"
                            placeholder="Start time"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="endTime" class="add_form_label">End time</label>
                        <input
                            type="datetime-local"
                            required
                            name="endTime"
                            placeholder="End time"
                            id="endTime"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="availableSeats" class="add_form_label"
                            >Available seats</label
                        >
                        <input
                            type="number"
                            required
                            name="availableSeats"
                            id="availableSeats"
                            placeholder="Available Seats"
                            class="add_form_input"
                        />
                    </div>
                    <div class="add_form_controller">
                        <label for="format">Format</label>
                        <select name="format" id="format" class="add_form_input">
                            <jsp:useBean
                                id="formats"
                                scope="request"
                                type="java.util.List"
                            />
                            <c:forEach var="format" items="${formats}">
                                <option value="${format}">${format}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="add_form_controller">
                        <label for="price">Price</label>
                        <input
                            type="number"
                            required
                            name="price"
                            placeholder="Price"
                            id="price"
                            class="add_form_input"
                        />
                    </div>
                    <div>
                        <input
                            type="hidden"
                            name="movieId"
                            value="${requestScope.movieId}"
                        />
                    </div>
                </div>
                <input type="submit" value="Submit" class="add_form_submit" />
            </form>
        </div>
    </main>
</body>
</html>
