<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ihorb
  Date: 12.01.2023
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/cinema/admin/updateMovie" method="post" enctype="multipart/form-data">
  <table>
    <tr>
      <td>Original name</td>
      <td><input type="text" required name="originalName" value="${requestScope.movie.originalName}"/></td>
    </tr>
    <tr>
      <td>Release date</td>
      <td><input type="datetime-local" required name="releaseDate" value="${requestScope.movie.releaseDate}"/></td>
    </tr>
    <tr>
      <td>Available age</td>
      <td><input type="text" required name="availableAge" value="${requestScope.movie.availableAge}"/></td>
    </tr>
    <tr>
      <td>Poster</td>
      <img src="data:image/png;base64, ${requestScope.movie.base64ImagePoster}" width="240" height="300"/>
      <td><input type="file" name="poster"/></td>
    </tr>
    <td><input type="text" hidden name="oldPoster" value="${requestScope.movie.base64ImagePoster}"/></td>
    <c:forEach var="movieDescription" items="${requestScope.movie.movieDescriptionList}">
      <tr>
        <td>${movieDescription.language.name} title</td>
        <td><input type="Text" required name="${movieDescription.language.name}Title" value="${movieDescription.title}"/></td>
      </tr>
      <tr>
        <td>${movieDescription.language.name} director</td>
        <td><input type="Text" required name="${movieDescription.language.name}Director" value="${movieDescription.director}"/></td>
      </tr>
    </c:forEach>
    <input type="hidden" name="movieId" value="${requestScope.movie.id}"/></td>
  </table>
  <input type="submit" value="Submit"/>
</form>
</body>
</html>
