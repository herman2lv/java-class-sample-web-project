<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Register new user</title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1>Edit user</h1>
<form method="post" action="controller">
    <input name="command" type="hidden" value="edit_user"/>
    <input name="id" type="hidden" value="${requestScope.user.id}"/>
    <label for="email-input">Email: </label>
    <input id="email-input" name="email" type="text" value="${requestScope.user.email}"/>
    <br/>

    <input id="role-input-user" name="role" type="radio"
           value="USER" ${requestScope.user.role=='USER' ? 'checked' : ''}>
    <label for="role-input-user">User</label>
    <input id="role-input-manager" name="role" type="radio" value="MANAGER" <c:if test="${requestScope.user.role=='MANAGER'}">checked</c:if>>
    <label for="role-input-manager">Manager</label>
    <input id="role-input-admin" name="role" type="radio"
           value="ADMIN" ${requestScope.user.role=='ADMIN' ? 'checked' : ''}>
    <label for="role-input-admin">Admin</label>
    <br/>

    <input type="submit" value="SAVE">
</form>
</body>
</html>
