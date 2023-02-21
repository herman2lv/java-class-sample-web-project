<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>User</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>User Personal Info</h1>
<p>${requestScope.message}</p>
<table>
    <tr>
        <th>Field</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>ID</td>
        <td>${user.id}</td>
    </tr>
    <tr>
        <td>Email</td>
        <td><c:out value="${user.email}"/></td>
    </tr>
    <tr>
        <td>Role</td>
        <td>${user.role.toString().toLowerCase()}</td>
    </tr>
</table>
</body>
</html>
